import java.util.Scanner;

public class VigenereCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the text you want to encrypt/decrypt: ");
        String plainText = scanner.nextLine();

        System.out.print("Enter the keyword: ");
        String keyword = scanner.nextLine();

        System.out.print("Do you want to encrypt or decrypt the text? (e/d): ");
        String mode = scanner.nextLine();

        if (mode.equalsIgnoreCase("e")) {
            String ciphertext = vigenereEncrypt(plainText, keyword);
            System.out.println("The encrypted text is: " + ciphertext);
        } else if (mode.equalsIgnoreCase("d")) {
            String plaintext = vigenereDecrypt(plainText, keyword);
            System.out.println("The decrypted text is: " + plaintext);
        } else {
            System.out.println("Invalid mode selected.");
        }
    }

    public static String vigenereEncrypt(String plainText, String keyword) {
        plainText = plainText.toUpperCase().replaceAll("[^A-Z]", "");
        String ciphertext = "";
        int keywordIndex = 0;
        for (int i = 0; i < plainText.length(); i++) {
            char currentChar = plainText.charAt(i);
            int shift = keyword.charAt(keywordIndex) - 'A';
            char encrypted = (char) ((currentChar + shift - 'A') % 26 + 'A');
            ciphertext += encrypted;
            keywordIndex = (keywordIndex + 1) % keyword.length();
        }
        return ciphertext;
    }

    public static String vigenereDecrypt(String ciphertext, String keyword) {
        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        String plaintext = "";
        int keywordIndex = 0;
        for (int i = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);
            int shift = keyword.charAt(keywordIndex) - 'A';
            char decrypted = (char) ((currentChar - shift - 'A' + 26) % 26 + 'A');
            plaintext += decrypted;
            keywordIndex = (keywordIndex + 1) % keyword.length();
        }
        return plaintext;
    }
}