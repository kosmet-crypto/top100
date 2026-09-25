package app.top100;

import java.util.Locale;

/** Native messages in Serbian (Latin) on phones set to a Serbo-Croatian language, English otherwise. */
final class L {
    private static final boolean SR = Locale.getDefault().getLanguage().matches("sr|hr|bs|sh|cnr");

    private L() {
    }

    static String tr(String sr, String en) {
        return SR ? sr : en;
    }
}
