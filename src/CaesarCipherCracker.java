import java.util.Scanner;

public class CaesarCipherCracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ciphertext: ");
        String ciphertext = scanner.nextLine();

        String[] result = crackCaesarCipher(ciphertext);
        String plaintext = result[0];
        int key = Integer.parseInt(result[1]);
        System.out.println("The decrypted plaintext is: " + plaintext);
        System.out.println("The key used for encryption is: " + key);
    }

    public static String[] crackCaesarCipher(String ciphertext) {
        int[] letterFrequencies = new int[26];
        int validLetterCount = 0;
        for (int i = 0; i < ciphertext.length(); i++) {
            char letter = ciphertext.charAt(i);
            if (letter >= 'A' && letter <= 'Z') {
                letterFrequencies[letter - 'A']++;
                validLetterCount++;
            } else if (letter >= 'a' && letter <= 'z') {
                letterFrequencies[letter - 'a']++;
                validLetterCount++;
            }
        }
        int mostFrequentLetterIndex = 0;
        for (int i = 1; i < 26; i++) {
            if (letterFrequencies[i] > letterFrequencies[mostFrequentLetterIndex]) {
                mostFrequentLetterIndex = i;
            }
        }
        int key = (mostFrequentLetterIndex - 4 + 26) % 26;
        String plaintext = decryptCaesarCipher(ciphertext, key, validLetterCount);
        String[] result = {plaintext, Integer.toString(key)};
        return result;
    }

    public static String decryptCaesarCipher(String ciphertext, int key, int validLetterCount) {
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            char letter = ciphertext.charAt(i);
            if (letter >= 'A' && letter <= 'Z') {
                char decrypted = (char) ((letter - key - 'A' + 26) % 26 + 'A');
                plaintext += decrypted;
            } else if (letter >= 'a' && letter <= 'z') {
                char decrypted = (char) ((letter - key - 'a' + 26) % 26 + 'a');
                plaintext += decrypted;
            } else {
                plaintext += letter;
            }
        }
        int invalidCharCount = ciphertext.length() - validLetterCount;
        plaintext = plaintext.substring(0, plaintext.length() - invalidCharCount);
        return plaintext;
    }
}