import java.util.Scanner;

public class CaesarCipher {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter a text:");
        String text = userInput.nextLine();
        System.out.println("Choose an option:");
        System.out.println("[1] Encrypt");
        System.out.println("[2] Decrypt");
        System.out.println("[3] Brut-Force");
        System.out.println("[4] Crack");
        int option = userInput.nextInt();
        if (option == 1) {
            System.out.println("Enter the key:");
            int key = userInput.nextInt();
            String resultText = encrypt(text, key);
            System.out.println("The encrypted text is: " + resultText);
        } else if (option == 2) {
            System.out.println("Enter the key:");
            int key = userInput.nextInt();
            String resultText = decrypt(text, key);
            System.out.println("The decrypted text is: " + resultText);
        } else if (option == 3) {
            System.out.println("Cracking the Caesar cipher might take a while. Please be patient...");
            for (int i = 1; i <= 26; i++) {
                String resultText = decrypt(text, i);
                System.out.println("Key " + i + ": " + resultText);
            }
        } else if (option == 4) {
            System.out.println("Cracking the Caesar cipher on an intelligent way. Please be patient... ");
            String[] result = crack(text);
            String plaintext = result[0];
            int key = Integer.parseInt(result[1]);
            System.out.println("The decrypted plaintext is: " + plaintext);
            System.out.println("The key used for encryption is: " + key);
        } else {
            System.out.println("Invalid option. Please choose 1, 2 or 3.");
        }
        userInput.close();
    }

    public static String encrypt(String plaintext, int key) {
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                char encrypted = (char) ((c + key - 'A') % 26 + 'A');
                ciphertext += encrypted;
            } else if (c >= 'a' && c <= 'z') {
                char encrypted = (char) ((c + key - 'a') % 26 + 'a');
                ciphertext += encrypted;
            } else {
                ciphertext += c;
            }
        }
        return ciphertext;
    }

    public static String decrypt(String ciphertext, int key) {
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                char decrypted = (char) ((c - key - 'A' + 26) % 26 + 'A');
                plaintext += decrypted;
            } else if (c >= 'a' && c <= 'z') {
                char decrypted = (char) ((c - key - 'a' + 26) % 26 + 'a');
                plaintext += decrypted;
            } else {
                plaintext += c;
            }
        }
        return plaintext;
    }

    public static String decryptCrack(String ciphertext, int key, int validLetterCount) {
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

    public static String[] crack(String ciphertext) {
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
        String plaintext = decryptCrack(ciphertext, key, validLetterCount);
        String[] result = {plaintext, Integer.toString(key)};
        return result;
    }
}

