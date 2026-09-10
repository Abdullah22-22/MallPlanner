package i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    private static ResourceBundle bundle =
            ResourceBundle.getBundle("messages", new Locale("en"));

    public static void setLanguage(String code) {
        bundle = ResourceBundle.getBundle("messages", new Locale(code));
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static String get(String key, Object... args) {
        return MessageFormat.format(bundle.getString(key), args);
    }
}
