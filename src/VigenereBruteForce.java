import java.util.Scanner;

public class VigenereBruteForce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the encrypted text: ");
        String ciphertext = scanner.nextLine();

        System.out.print("Enter the minimum key length: ");
        int minKeyLength = scanner.nextInt();

        System.out.print("Enter the maximum key length (or enter the length of the ciphertext to use it as the maximum): ");
        int maxKeyLength = scanner.nextInt();
        if (maxKeyLength == ciphertext.length()) {
            maxKeyLength = Integer.MAX_VALUE;
        }

        String plaintext = vigenereBruteForce(ciphertext, minKeyLength, maxKeyLength); // bruteforce Vigenere encryption
        System.out.println("The decrypted text is: " + plaintext);
    }

    // Bruteforce Vigenere encryption
    public static String vigenereBruteForce(String ciphertext, int minKeyLength, int maxKeyLength) {
        String plaintext = "";

        // Try all possible key lengths from the minimum to the maximum
        for (int keyLength = minKeyLength; keyLength <= maxKeyLength && keyLength <= ciphertext.length(); keyLength++) {
            // Try all possible keys of the current length
            for (int i = 0; i < Math.pow(26, keyLength); i++) {
                String key = intToKey(i, keyLength); // Convert integer to key string
                String decrypted = vigenereDecrypt(ciphertext, key);
                if (isEnglish(decrypted)) { // Check if decrypted text is English
                    plaintext = decrypted;
                    break;
                }
            }
            if (!plaintext.equals("")) {
                break;
            }
        }

        return plaintext;
    }

    // Convert integer to key string
    public static String intToKey(int n, int length) {
        String key = "";
        for (int i = 0; i < length; i++) {
            int index = n % 26;
            char c = (char) (index + 'A');
            key += c;
            n /= 26;
        }
        return key;
    }

    // Vigenere decryption
    public static String vigenereDecrypt(String ciphertext, String key) {
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            int keyIndex = i % key.length();
            char c = ciphertext.charAt(i);
            char k = key.charAt(keyIndex);
            if (c >= 'A' && c <= 'Z') {
                char decrypted = (char) ((c - k + 26) % 26 + 'A');
                plaintext += decrypted;
            } else if (c >= 'a' && c <= 'z') {
                char decrypted = (char) ((c - k + 26) % 26 + 'a');
                plaintext += decrypted;
            } else {
                plaintext += c;
            }
        }
        return plaintext;
    }

    // Check if the input string is English
    public static boolean isEnglish(String str) {
        double[] englishLetterFrequency = {0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.02, 0.061, 0.07, 0.002, 0.008, 0.04, 0.024, 0.067, 0.075, 0.019, 0.001, 0.06, 0.063, 0.091, 0.028, 0.01, 0.023, 0.001, 0.02, 0.001};
        double englishIC = 1.73;

        double freqSum = 0.0;
        int lengthSum = 0;
        int[] observedFrequencies = new int[26];
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                observedFrequencies[c - 'A']++;
                freqSum++;
                lengthSum++;
            } else if (c >= 'a' && c <= 'z') {
                observedFrequencies[c - 'a']++;
                freqSum++;
                lengthSum++;
            } else if (c == ' ' || c == '\n' || c == '\t') {
                lengthSum++;
            }
        }

        double[] expectedFrequencies = new double[26];
        double icSum = 0.0;
        for (int i = 0; i < 26; i++) {
            expectedFrequencies[i] = englishLetterFrequency[i] * lengthSum;
            icSum += Math.pow(observedFrequencies[i] * (observedFrequencies[i] - 1), 2);
        }

        double ic = icSum / (freqSum * (freqSum - 1));
        double freqDiffSum = 0.0;
        for (int i = 0; i < 26; i++) {
            freqDiffSum += Math.pow(observedFrequencies[i] - expectedFrequencies[i], 2) / expectedFrequencies[i];
        }

        double freqDiff = freqDiffSum / 26;

        if (ic > englishIC && freqDiff < 6.6) {
            return true;
        } else {
            return false;
        }
    }
}