package console;

import i18n.Messages;

public class ConsoleOutput {

    public void show(String key) {
        System.out.println(Messages.get(key));
    }

    public void show(String key, Object... args) {
        System.out.println(Messages.get(key, args));
    }

    public void showError(String key) {
        System.out.println("[!] " + Messages.get(key));
    }
}