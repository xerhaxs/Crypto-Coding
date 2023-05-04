import java.util.ArrayList;
import java.util.Scanner;

public class Kasiski {

	private static final double[] letterFreq = {    8.20, 1.07, 3.44, 3.64, 12.42, 2.35, 1.81, 3.50, 7.68,  //A B C D E F G H I     //http://cs.wellesley.edu/~fturbak/codman/letterfreq.html
			0.20, 0.39, 4.48, 2.82, 7.64, 7.14, 2.03, 0.09, 6.68,   //J K L M N O P Q R
			7.07, 9.69, 2.88, 1.25, 1.35, 0.22, 1.89, 0.06};        //S T U V W X Y Z

	private static final char[] letters = {     'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',    //  1  2  3  4  5  6  7  8  9       <- subtract 1 for index
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',    // 10 11 12 13 14 15 16 17 18
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};        // 19 20 21 22 23 24 25 26

	private static final char[][] vigenereTable = { {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'},
			{'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a'},
			{'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b'},
			{'d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c'},
			{'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd'},
			{'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e'},
			{'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f'},
			{'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g'},
			{'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'},
			{'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'},
			{'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'},
			{'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'},
			{'m', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l'},
			{'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'},
			{'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'},
			{'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'},
			{'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'},
			{'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q'},
			{'s', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r'},
			{'t', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's'},
			{'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't'},
			{'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u'},
			{'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v'},
			{'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w'},
			{'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x'},
			{'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y'}};

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter your Cipher Text: ");
		String cipherText = scanner.nextLine().toLowerCase();

		ArrayList<Pattern> patterns = getPatterns(cipherText);      // scan cipher text and find patterns and distances
//        printRepeats(patterns);

		// determine and set the factors for each patterns
		for (int i = 0; i < patterns.size(); i++){
			ArrayList<Integer> factors = determineFactors(patterns.get(i));
			patterns.get(i).setFactors(factors);
		}

		// determine the count of each factor and the most common one we will assume it is keyword length
		ArrayList<Integer> commonFactors = getCommonFactors(patterns);
		ArrayList<Integer> factorCounts = countFactors(patterns, commonFactors);
		bubbleSort(factorCounts, commonFactors);    // sort both arrays based on factor counts
//        System.out.println(commonFactors);
//        System.out.println(factorCounts);

		// display relevant information of decrypted message - will take the 5 highest matches for key length
		int maxKeys = 5;
		for (int key = commonFactors.size() - maxKeys; key < commonFactors.size(); key++){
			int keyLength = commonFactors.get(key);

			// build a table of frequencies for comparison
			char[][] cipherTable = getCipherTable(cipherText, keyLength);
			double[][] frequencies = getFrequency(cipherTable, keyLength);
			double[][] freqTable = getFrequencyTable(keyLength, frequencies);

			// determine the shifts by averaging frequencies - if average is <150, we take this index
			int[] shifts = new int[keyLength];
			int index = 0;
			for (int i = 0; i < freqTable.length; i++) {
				double sum = 0;
				double average = 0;
				for (int j = 0; j < freqTable[i].length; j++) {
					sum = sum + freqTable[i][j];
				}
				average = sum / freqTable[i].length;

				if (average <= 150){
//                System.out.println(i % 26 + " \t " + letters[i%26] + " \t " + sum / freqTable[i].length);
					shifts[index] = i % 26;
					index++;
				}
			}

			// get keyword and build a key stream
			String keyword = getKeyWord(shifts);
			String keyStream = getKeyStream(cipherText, keyword);
			String decryptedMsg = decipherMessage(shifts, cipherText);

			System.out.println("decrypted message if keylength is: " + keyLength);

			System.out.println("keyword: " + keyword);
			System.out.println();

			System.out.println("keystream: ");
			System.out.println(keyStream);
			System.out.println();


			System.out.println("cipherText: ");
			System.out.println(cipherText);
			System.out.println();

			System.out.println("decrypted message: ");
			System.out.println(decryptedMsg);

			System.out.println();
			System.out.println("|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		}

	} // end of main method

	public static String decipherMessage(int[] shift, String cipherText){
		String plainText = "";
		int[] plainIndex = new int[cipherText.length()];

		for (int i = 0; i < cipherText.length(); i++){

			for (int j = 0; j < letters.length; j++){
				if (cipherText.charAt(i) == vigenereTable[shift[i%shift.length]][j]){
					plainIndex[i] = j;
				}
			}
		}

		for (int i = 0; i < cipherText.length(); i++){
			plainText = plainText + letters[plainIndex[i]];
		}

		return plainText;
	}

	public static String getKeyStream(String cipherText, String keyword){
		String keyStream = "";
		for (int i = 0; i < cipherText.length(); i++){
			keyStream = keyStream + keyword.charAt(i % keyword.length());
		}
		return keyStream;
	}

	public static String getKeyWord(int[] shifts){
		// print shifts
//        for (int i = 0; i < shifts.length; i++){
//            System.out.println(shifts[i]);
//        }

		String str = "";
		for (int i = 0; i < shifts.length; i++){
			str = str + letters[shifts[i]];
		}
//        System.out.println(str);
		return str;
	}

	public static double[][] getFrequencyTable(int keyLength, double[][] frequencies){
		double[][] freqTable = new double[keyLength * 26][26];
		double[] tempTable;

		int count = 0, count2 = 0;
		while(count < keyLength){
			tempTable = frequencies[count];
			for (int i = 0; i < 26; i++){
				freqTable[count2] = tempTable;
				tempTable = shiftCipher(tempTable);
//                printDoubleArr(tempTable);
				count2++;
			}
			count++;
		}

		// get percent difference compared to common letter frequencies
		for (int i = 0; i < freqTable.length; i++){
			for (int j = 0; j < freqTable[i].length; j++){
				freqTable[i][j] = freqTable[i][j] / letterFreq[j] * 100;
			}
		}

		// print out the table
//        for (int i = 0; i < freqTable.length; i++){     // at i = 0, this will be the original
//            System.out.print(i%26 + ": ");
//            for (int j = 0; j < freqTable[i].length; j++){
//                System.out.printf("%.3f, ", freqTable[i][j]);
//            }
//            System.out.println();
//        }

		return freqTable;
	}

	// used to shift cipher until match with common letter frequencies
	public static double[] shiftCipher(double[] freq){

		double[] shiftFreq = new double[26];
		for (int i = 0; i < shiftFreq.length; i++){
			if (i < shiftFreq.length -1) {
				shiftFreq[i] = freq[i+1];
			}
			else
				shiftFreq[i] = freq[0];
		}
		return shiftFreq;
	}

	// get the frequency of letter counts from cipher table
	public static double[][] getFrequency(char[][] cipherTable, int keyLength){

		double[][] frequencies = new double[keyLength][26];

		int count = 0;
		while (count < keyLength){
			for (int i = 0; i < letters.length; i++){
				double tally = 0;
				for (int j = 0; j < cipherTable.length; j++){
					if (letters[i] == cipherTable[j][count]){
						tally++;
					}
				}
				frequencies[count][i] = tally / cipherTable.length * 100;
			}
			count++;
		}

//        for (int i = 0; i < frequencies.length; i++){
//            for (int j = 0; j < frequencies[i].length; j++){
//                System.out.printf("%.3f ", frequencies[i][j]);
//            }
//            System.out.println();
//        }
		System.out.println();
		return frequencies;
	}

	// build table of cipher text based on key length - used to determine frequencies
	public static char[][] getCipherTable(String cipherText, int keyLength){

		int row = cipherText.length()/keyLength, col = keyLength;
		char[][] cipherTable = new char[row][col];

		for(int i = 0; i < cipherText.length(); i = i + keyLength){
			if (i + keyLength > cipherText.length()){
				break;
			}
			for (int j = 0; j < keyLength; j++){
				cipherTable[i/keyLength][j] = cipherText.charAt(i + j);
			}
		}
		// print cipher table
//        for(int i = 0; i < cipherText.length()/keyLength; i++){
//            for(int j = 0; j < keyLength; j++){
//                System.out.print(cipherTable[i][j]);
//                System.out.println("[" + i + ", " + j + "]");
//            }
//            System.out.println();
//        }
		return cipherTable;
	}

	public static ArrayList<Integer> countFactors(ArrayList<Pattern> patterns, ArrayList<Integer> factorCounts){

		ArrayList<Integer> counts = new ArrayList<>();

		for (int i = 0; i < factorCounts.size(); i++){
			int tally = 0;

			for (int j = 0; j < patterns.size(); j++){
				if (patterns.get(j).getFactors().contains(factorCounts.get(i))){
					tally++;
				}
			}
			counts.add(tally);
		}
//        System.out.println(counts);
		return counts;
	}

	public static ArrayList<Integer> getCommonFactors(ArrayList<Pattern> patterns){
		ArrayList<Integer> common = new ArrayList<Integer>();

		for (int i = 0; i < patterns.size(); i++){
			for (int j = 0; j < patterns.get(i).getFactors().size(); j++){
				if (!common.contains(patterns.get(i).getFactors().get(j)) && patterns.get(i).getFactors().get(j) <= 96){    //--- allow only factors up to 96
					common.add(patterns.get(i).getFactors().get(j));
				}
			}
		}
//        System.out.println(common);
		return common;
	}

	public static void bubbleSort(ArrayList<Integer> factorArr, ArrayList<Integer> countArr) {
		int temp;
		int num;
		for (int i = 0; i < factorArr.size()-1; i++) {
			for (int j = 0; j < factorArr.size()-i-1; j++) {
				if (factorArr.get(j) > factorArr.get(j+1)){
					temp = factorArr.get(j);
					factorArr.set(j, factorArr.get(j+1));
					factorArr.set(j+1, temp);

					num = countArr.get(j);
					countArr.set(j, countArr.get(j+1));
					countArr.set(j+1, num);
				}
			}
		}
	}


	public static ArrayList<Integer> determineFactors(Pattern pattern){
		ArrayList<Integer> factors = new ArrayList<Integer>();

		for (int i = 3; i <= pattern.getDistance(); i++){
			if (pattern.getDistance() % i == 0){
				factors.add(i);
			}
		}
//        System.out.println(factors);
		return factors;
	}

	public static ArrayList<Pattern> getPatterns(String cipherText){
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> repeatPattern = new ArrayList<String>();  // used to control while loop to not duplicate patterns

		// generate sub-strings of cipherText and determine distances
		for (int i = 0; i < cipherText.length(); i++){

			for (int k = 3; k <= 4; k++) {            // ----------  set to find repeat patterns of 3 and 4 lengths
				int length = i + k;

				if (length > cipherText.length()){
					break;
				}
				else {
					String subCipher = cipherText.substring(i, length);

					int firstIndex = cipherText.indexOf(subCipher);
					int lastIndex = 0, count = 0, distance = 0, secondIndex = 0;
					while (lastIndex != -1){

						lastIndex = cipherText.indexOf(subCipher, lastIndex);

						if (lastIndex != -1){
							count++;
							lastIndex += subCipher.length();
						}

						if (count == 2){
							secondIndex = lastIndex;
							distance = secondIndex - (firstIndex + subCipher.length());
							if (!repeatPattern.contains(subCipher)){
								repeatPattern.add(subCipher);
								Pattern tempPattern = new Pattern(subCipher, distance, null);
								patterns.add(tempPattern);
							}
							break;
						}
					} // end of while loop
				} // end of if else statement
			} // end of inner nested for loop
		} // end of outer nested for loop

		return patterns;
	}

	public static void printRepeats(ArrayList<Pattern> pattern){
		int count = 0;
		for (int i = 0; i < pattern.size(); i++){
			System.out.println(pattern.get(i).getRepeat() + ": " + pattern.get(i).getDistance());
			count++;
		}
//        System.out.println("there are " + count + " patterns found");
	}


	public static void printDoubleArr(double[] arr){
		for (int i = 0; i < arr.length; i++){
			if (i < arr.length-1) {
				System.out.printf("%.2f, ", arr[i]);
			}
			else {
				System.out.printf("%.2f\n", arr[i]);
			}
		}
	}

} // end of kasiski class