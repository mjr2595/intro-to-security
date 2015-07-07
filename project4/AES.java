import java.io.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;


public class AES implements AESConstants {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		char option = args[0].charAt(0);
		String keyFileName = args[1];
		String inputFileName = args[2];
		File inputFile = new File(inputFileName);

		if (option == 'e' || option == 'E') {

			if (TIMING == true) {
				long encStart = System.currentTimeMillis();
				Encryptor enc = new Encryptor(keyFileName, inputFileName);
				long encStop = System.currentTimeMillis();

				long encDuration = (encStop - encStart);
				System.out.println("\nEncryption total time: " + encDuration + " milliseconds");
				long encBandwidth = (long)(inputFile.length() * 8) / (encDuration/1000);
				System.out.println("Bandwidth: " + encBandwidth + " bits/second");
			} else {
				Encryptor enc = new Encryptor(keyFileName, inputFileName);
			}

			
		}
		else if (option == 'd' || option == 'D') {

			if (TIMING == true){
				long decStart = System.nanoTime();
				Decryptor dec = new Decryptor(keyFileName, inputFileName);
				long decStop = System.nanoTime();

				long decDuration = (decStop - decStart);
				System.out.println("\nDecryption total time: " + decDuration + " milliseconds");
				long decBandwidth = (long)(inputFile.length() * 8) / (decDuration/1000);
				System.out.println("Bandwidth: " + decBandwidth + " bits/second");
			} else {
				Decryptor dec = new Decryptor(keyFileName, inputFileName);
			}

		} else throw new IllegalArgumentException("[option] must be 'e' or 'd'");
	}
}