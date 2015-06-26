import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Integer;


public class Encoder {
	
	private static String filename;
	private static int numChars;
	private static int[] alphabet;
	private static double[] probabilities;
	private static int totalNumLetters;
	//private double entropy;

	public static void main(String[] args){

		Encoder enc = new Encoder();

		filename = "";

		if(args.length == 2){
			filename = args[0];
			numChars = Integer.parseInt(args[1]);
		} else {
			System.err.println("Your arguments are invalid.");
			return;
		}
		double entropy = 0;
		entropy = enc.getEntropy(filename);
		System.out.println("Entropy = " + entropy);
	}

	public Encoder() {
		alphabet = new int[26];
		probabilities = new double[26];
		totalNumLetters = 0;
	}

	public static double getEntropy(String path) {
		double entropy = 0;
		File file = new File(path);	
		try {
			Scanner reader = new Scanner(file);

			
			int i = 0;
			// read in frequency counts and keep track of totalNumLetters

			while (reader.hasNextLine()) {
				alphabet[i] = Integer.parseInt(reader.nextLine());
				totalNumLetters += alphabet[i];
				i++;
			}
			System.out.println("alphabet = " + Arrays.toString(alphabet));
			System.out.println("total number of characters = " + totalNumLetters);
			// go back and calculate each letter's probability
			for (int j = 0; j < 27; j++) {
				probabilities[j] = alphabet[j]/totalNumLetters;
				entropy += probabilities[j]*log2(probabilities[j]);
			}
			
			

		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		return entropy * -1;
	}

	public static double log2(double n) {
    	return (Math.log(n) / Math.log(2));
	}
}