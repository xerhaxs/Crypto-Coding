import java.util.Scanner;

public class CryptoCalculator {
    private int module;

    public CryptoCalculator(int module) {
        this.module = module;
    }

    public int getModule() {
        return module;
    }

    public int getSumm(int number1, int number2) {
        return (number1 + number2) % module;
    }

    public int getProduct(int number1, int number2) {
        return (number1 * number2) % module;
    }

    public int getPotency(int base, int exponent) {
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result = result * base % module;
        }
        return result;
    }

    public boolean isCongruent(int number1, int number2) {
        return number1 % module == number2 % module;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Geben Sie das Modul ein: ");
        int module = scanner.nextInt();
        CryptoCalculator calculator = new CryptoCalculator(module);

        System.out.print("Geben Sie die erste Zahl ein: ");
        int number1 = scanner.nextInt();

        System.out.print("Geben Sie die zweite Zahl ein: ");
        int number2 = scanner.nextInt();

        System.out.println("Summe: " + calculator.getSumm(number1, number2));
        System.out.println("Produkt: " + calculator.getProduct(number1, number2));

        System.out.print("Geben Sie die Basis ein: ");
        int base = scanner.nextInt();

        System.out.print("Geben Sie den Exponenten ein: ");
        int exponent = scanner.nextInt();
        System.out.println("Potenz: " + calculator.getPotency(base, exponent));

        System.out.print("Geben Sie die erste Zahl für die Kongruenzprüfung ein: ");
        int number3 = scanner.nextInt();
        System.out.print("Geben Sie die zweite Zahl für die Kongruenzprüfung ein: ");
        int number4 = scanner.nextInt();
        if (calculator.isCongruent(number3, number4)) {
            System.out.println("Die Zahlen sind kongruent modulo " + calculator.getModule() + "!");
        } else {
            System.out.println("Die Zahlen sind nicht kongruent modulo " + calculator.getModule() + "!");
        }
    }
}