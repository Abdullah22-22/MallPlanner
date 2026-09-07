package console;

import i18n.Messages;
import java.util.Scanner;

public class ConsoleInput {

    private final Scanner scanner = new Scanner(System.in);

    public String readText(String key) {
        System.out.print(Messages.get(key) + ": ");
        return scanner.nextLine().trim();
    }

    public double readNumber(String key) {
        while (true) {
            System.out.print(Messages.get(key) + ": ");

            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(Messages.get("error.not.a.number"));
            }
        }
    }

    public int readInt(String key) {
        return (int) readNumber(key);
    }
}