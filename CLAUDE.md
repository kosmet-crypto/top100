# Top 100

Personal music charts: lists of songs with points per year, rounds of entering points, movement, yearly history and stats.
Used as an Android APK (WebView wrapper) and as a PWA. All data lives on the device (`localStorage`, key `top100`).
The owner writes in Serbian; reply in Serbian (Cyrillic) unless asked otherwise.

## Layout
- `index.html`: the whole app (HTML, CSS, vanilla JS, no libraries, no build step). Sections are marked
  `/* ===== name ===== */`: i18n, data, UI state, render, stats, yearly history, round, sheets, songs,
  lists & settings, import, export, events.
- `sw.js`: service worker for the web version. **Bump `VERSION` on every change to `index.html` or `icons/`.**
- `manifest.json`, `icons/`: PWA manifest and icons.
- `android/`: WebView wrapper (`app.top100`): `MainActivity` (bridge `Top100Android`, file pick/save,
  back button via `window.t100Back()`), `WebUpdater` (downloads `index.html` from `main` over the air),
  `ApkInstaller` (installs newer releases), `L` (native strings, Serbian or English). Day/night theme in
  `res/values*` so the page's automatic theme follows the phone.
- `.github/workflows/android.yml`: builds the APK on pushes to `main` and `claude/**`; on `main` it publishes
  release `v1.0.<run number>` with `top100.apk`.

## Data model (`data` in index.html)
- `lists[]`: `{id, name, size (top N, default 100), rank: 'total'|'year'|'last', lastN, showPl, base, created}`
- `songs[]`: `{id, list, title, artist, pts: {year: points}, extra?, pl, added}`.
  `extra` = points from the sheet's total that no year column explains; `total()` includes it.
- `rounds[]`: `{id, list, at, year, adds: {songId: points}, prev: [ids], order: [ids], auto?, sheet?}`.
  `auto` rounds are the yearly history (standing at the end of each finished year, built by
  `buildYearHistory`, or taken from an older yearly sheet by `applySnapshot`, then `relinkHistory`).
  Own rounds come after them; only the last own round can be undone.
- `draft`: the round in progress (frozen order, adds, year).
- Year chips show the cumulative chart at the end of that year (`scoreUpTo`, or the saved `auto` round).

## Import (the part that needs the most care)
The owner's real workbooks have one sheet per year ("World 100 2025", "YU 100 2022", "YU 100 2021-2023"…),
each holding all years up to then, with columns such as `Do 2011`, `6. 2012`, `1 - 3 2012`, `2013`,
`do 2013` (subtotal), `18/19`, `2014`…, `Zbir` (total), a playlist column with `Da`.
- `yearOf`: a header with one year and a short prefix counts for that year; two-year headers
  (`18/19`, `2018/19`) count for the later year.
- `markSubtotals`: a points column equal to the sum of the columns just before it is a subtotal and is skipped,
  **unless** the sheet's `Zbir` counts it (then it is counted, so totals match the spreadsheet).
- If year columns still don't add up to `Zbir`, the difference goes to `song.extra` (option on by default).
- Sheets named `<base> <year>` are grouped: the newest becomes the list `<base>`; older ones never become
  lists, they give the real end-of-year standing for the history.
- PDF and other binary files are rejected with a message; old `.xls` too.
Totals in the app must match the sheet's `Zbir`; the preview shows "Zbir se slaže u svim redovima ✓".

## UI text
Strings are written in Serbian Latin inside `T('...')`. Cyrillic is transliterated automatically; English comes
from the `EN` table (add every new string there). Wrap text that must not be transliterated (file extensions,
brand names) in `[[ ]]`; placeholders are `{0}`, `{1}`.

## Checking changes
- Syntax check: extract the `<script>` and run it through `new Function(...)` in Node.
- Run the page with `npx http-server . -p 8123` and drive it with Playwright (Chromium is preinstalled):
  import a test `.xlsx` (build it with openpyxl), start a round, check history, year chips and stats,
  and take phone-size screenshots (390×844) in light and dark.
- Keep test spreadsheets out of the repo: the owner's data is private and each user imports their own.
- An Android change is only verified once the GitHub Actions build passes.

## Workflow
- Work on a branch, open a PR to `main`, merge after checks. Merging to `main` updates installed apps:
  the page over the air on the next launches, the APK through a new release.
- When the page starts calling a new `Top100Android` method, raise `<meta name="top100-native-api">` in
  `index.html` and `WebUpdater.NATIVE_API`, so older APKs keep their bundled page.
