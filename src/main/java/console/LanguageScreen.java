package console;

import i18n.Messages;

public class LanguageScreen {

    private final ConsoleInput input;

    public LanguageScreen(ConsoleInput input) {
        this.input = input;
    }

    public void show() {

        while (true) {

            int choice = input.readInt("language.menu");

            if (choice == 1) {
                Messages.setLanguage("en");
                return;
            }

            if (choice == 2) {
                Messages.setLanguage("fi");
                return;
            }

            System.out.println(Messages.get("error.language.choice"));
        }
    }
}