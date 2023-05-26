import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class KasiskiMethod {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the encrypted text: ");
        String encryptedText = scanner.nextLine();
        String decryptedText = decrypt(encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public static String decrypt(String text) {
        ArrayList<Integer> distances = kasiskiMethod(text);
        int keyLength = distances.get(0);
        String key = "";
        for (int i = 0; i < keyLength; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < text.length(); j += keyLength) {
                sb.append(text.charAt(j));
            }
            int maxCount = 0;
            char maxChar = 'A';
            for (char c = 'A'; c <= 'Z'; c++) {
                int count = countOccurrences(sb.toString(), c);
                if (count > maxCount) {
                    maxCount = count;
                    maxChar = c;
                }
            }
            int shift = ((maxChar - 'E') % 26);
            if (shift < 0) {
                shift += 26;
            }
            key += (char) (shift + 'A');
        }

        System.out.println("Key: " + key);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int shift = ((c - key.charAt(i % keyLength)) % 26);
            if (shift < 0) {
                shift += 26;
            }
            char decryptedChar = (char) (shift + 'A');
            result.append(decryptedChar);
        }
        return result.toString();
    }

    public static ArrayList<Integer> kasiskiMethod(String text) {
        ArrayList<Integer> distances = new ArrayList<>();
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < text.length() - 2; i++) {
            String subString = text.substring(i, i + 3);
            if (map.containsKey(subString)) {
                ArrayList<Integer> list = map.get(subString);
                for (int j = 0; j < list.size(); j++) {
                    int distance = i - list.get(j);
                    if (distance > 0) {
                        distances.add(distance);
                    }
                }
                list.add(i);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(subString, list);
            }
        }
        return distances;
    }

    public static int countOccurrences(String str, char c) {
        int count = 0;
        for (int i=0; i<str.length(); i++) {
            if (str.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
}