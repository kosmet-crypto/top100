# Top 100

Lične muzičke top-liste: poeni po godinama, krugovi unošenja, kretanje na listi i istorija.
Radi na telefonu kao Android aplikacija (APK) ili u pregledaču, bez interneta. Podaci ostaju na uređaju.

## Šta radi
* Više listi (npr. World 100, ExYu 100), svaka sa svojim brojem mesta (podrazumevano 100) i načinom rangiranja:
  zbir svih godina, samo tekuća godina ili poslednjih N godina.
* **Krug unošenja:** otvoriš listu, redosled je zamrznut, dodaješ poene dugmićima (+1, +2, +5, podesivo) ili ih upišeš,
  pa klikneš **Završio sam**. Dobiješ novi raspored i pregled: novi broj 1, ušle i ispale pesme, skokovi i padovi.
  Nezavršen krug se čuva. Poslednji krug može da se poništi.
* Kretanje kao na pravim listama: ▲ ▼, NEW, RE. Lista po svakoj godini posebno.
* Pesma: pozicija, najviša pozicija, broj krugova na #1, grafikon kretanja i poeni po godinama (mogu da se menjaju).
* Lista izvođača (zbir poena njihovih pesama), pretraga, pesme ispod crte.
* Uvoz iz Excel-a (.xlsx, svi listovi odjednom) ili CSV-a, izvoz u Excel, rezervna kopija i vraćanje.
* Srpski (ćirilica i latinica) i engleski, tamna i svetla tema.

## Uvoz iz tabele
Tabela ne mora da se menja. Preuzmi je kao `.xlsx` (Google Sheets: *Datoteka → Preuzmi → Microsoft Excel*) i izaberi
**Uvezi iz Excel-a**. Svaki list postaje jedna lista. Kolone se prepoznaju po nazivu:

| Kolona | Prepoznaje se po |
|---|---|
| Pesma | Pesma, Naziv, Song, Title… |
| Izvođač | Grupa, Izvođač, Artist, Band… |
| Poeni po godinama | naslov je godina (2014, 2015…) |
| Prethodna pozicija | Pozicija, Mesto, Rank… (kretanje se računa od nje) |
| Zbir | Zbir, Ukupno, Total… (samo provera, aplikacija sama sabira) |
| Plejlista | Spotify, Plejlista ili kolona sa „Da“ |

Pre uvoza se vidi pregled, i svaka kolona može ručno da se promeni. Ako se zbir iz tabele ne slaže sa godinama, ti redovi se pokažu.
Uvoz u postojeću listu spaja pesme po nazivu i izvođaču.

## Instalacija
* **Android:** https://github.com/kosmet-crypto/top100/releases/latest/download/top100.apk
  Otvori fajl na telefonu, dozvoli instalaciju jednom i instaliraj. Nova verzija se instalira preko stare i čuva podatke.
* **Pregledač:** otvori GitHub Pages link repozitorijuma, pa *Instaliraj aplikaciju / Dodaj na početni ekran*.

Ažuriranja: aplikacija pri pokretanju preuzme najnoviji `index.html` sa `main` grane i koristi ga od sledećeg pokretanja.
Za promene u Android delu proverava nova izdanja najviše dva puta dnevno (ili odmah preko *Podešavanja → Proveri ažuriranja*).

## Za razvoj
* Cela aplikacija je u `index.html` (bez biblioteka). Posle promene `index.html` ili ikonica povećaj `VERSION` u `sw.js`.
* Svaki push na `main` pravi APK (GitHub Actions) i objavljuje ga kao release.
* Android omotač je u `android/` (WebView). Lokalno: `cd android && ./gradlew assembleRelease`.
* Kad stranica počne da koristi novu `Top100Android` metodu, povećaj `<meta name="top100-native-api">` u `index.html`
  i `WebUpdater.NATIVE_API` u aplikaciji.
