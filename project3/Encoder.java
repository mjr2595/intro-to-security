import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Integer;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Encoder {
	
	private static String filename;
	private static Scanner scanner;
	private static Map<Character, Double> frequencyMap;
	private static double[] freqArray;
	private static Map<Character, Integer> charCountMap;
	private static Map<Character, String> encryptionMap;
	private static Map<String, Character> decryptionMap;
	private static int total;
	private static int k;

	// constant for alphabet
	private static final String ALPH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


	public Encoder(String filename, int k) throws FileNotFoundException {
		this.filename = filename;
		this.k = k;
		total = 0;
		frequencyMap = new HashMap<Character, Double>();
		charCountMap = new HashMap<Character, Integer>();
		
		scanner = new Scanner(new File(filename));
		
		getFrequencies();
		getEncryptionMap();
		getDecryptionMap();
		printResults();
		createVolume(1);
		decryptVolume(1);
	}

	private void getFrequencies() {
		// total is sum of all numbers
		int total = 0;
		// count is number of lines
		int count = 0;

		// First go through file and fill count map
		String currLine = "";
		int currNumber = 0;
		while (scanner.hasNextLine()) {
			currLine = scanner.nextLine();
			currNumber = Integer.parseInt(currLine);
			charCountMap.put(ALPH.charAt(count), currNumber);
			total += currNumber;
			count++;
		}
		this.total = total;

		// Now fill frequency map and array
		freqArray = new double[count];
		count = 0;
		double freq = 0;
		for (Character c : charCountMap.keySet()) {
			freq = ((double)charCountMap.get(c) / total);
			frequencyMap.put(c, freq);
			if (count > 0)
				freqArray[count] = freqArray[count - 1];

			freqArray[count] += frequencyMap.get(c);
			count++;
		}
	}

	private void getEncryptionMap() {
		encryptionMap = new HashMap<Character, String>();
		encryptionMap = Huffman.getEncryptionCodes(frequencyMap);
	}

	private void getDecryptionMap() {
		decryptionMap = new HashMap<String, Character>();
		for(Map.Entry<Character, String> entry : encryptionMap.entrySet()){
    		decryptionMap.put(entry.getValue(), entry.getKey());
		}
	}

	private double getEntropy() {
		double entropy = 0;
		double currFreq = 0;
		for (Character c : frequencyMap.keySet()) {
			currFreq = frequencyMap.get(c);
			entropy += currFreq * log_2(currFreq);
		}
		return -entropy;
	}

	// Helper method for computing log_2
	private double log_2(double n) {
    	return (Math.log(n) / Math.log(2));
	}

	private void printResults() {
		System.out.println("\n\n\t+---------+");
		System.out.println("\t| RESULTS |");
		System.out.println("\t+---------+\n");

		// print each char and its encoding
		for (Character c : charCountMap.keySet()) {
			System.out.print("Character: " + c + ", Encoding: " + encryptionMap.get(c));
			System.out.println(", Probability: " + charCountMap.get(c) + "/" + total);
		}
		// print entropy
		System.out.println("________________________________________________\n");
		System.out.println("Entropy = " + getEntropy() + "\n");
	}

	private void createVolume(int num) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("testText.enc" + num);
			for (int i = 0; i < this.k; i++) {
				writer.println(getRandomCharCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
		}
	}
	// helper method that generates a random number 0-1 then compares that to 
	// the frequencies, if the random number is less than or equal to that frequency
	// then use that charCode
	private String getRandomCharCode() {
		double rand = Math.random();
		int index = 0;
		int i = 0;
		while (i < freqArray.length) {
			if (rand < freqArray[i]) {
				index = i;
				i = freqArray.length;
			}
			i++;
		}
		return encryptionMap.get(ALPH.charAt(index));
	}

	private void decryptVolume(int num) {
		int bits = 0;
		Scanner scanner = null;
		PrintWriter writer = null;
		try {
			scanner = new Scanner(new File("testText.enc" + num));
			writer = new PrintWriter("testText.dec" + num);
			String currLine = "";
			while (scanner.hasNextLine()) {
				currLine = scanner.nextLine();
				writer.println(decryptionMap.get(currLine));
				bits += currLine.length();
			}
			printEfficiency(bits, num);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	private void printEfficiency(int bits, int num) {
		double bitsPerSymbol = bits / ((double)this.k);
		System.out.println("Encryption " + num + " has " + bitsPerSymbol + " bits per symbol");
		double ratio = (bitsPerSymbol / getEntropy()) * 10;
		System.out.println("The percent difference from entropy is " + ratio);
		System.out.println("\n");
	}

	public static void main(String[] args) throws FileNotFoundException {
		int k = Integer.parseInt(args[1]);
		String filename = args[0];
		
		Encoder encoder = new Encoder(filename, k);
	}
}