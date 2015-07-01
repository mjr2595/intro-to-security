import java.io.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;


public class AES {
	
	public static void main(String[] args) throws FileNotFoundException {
		char option = args[0].charAt(0);
		String keyFileName = args[1];
		String inputFileName = args[2];

		if (option == 'e' || option == 'E') {
			Encryptor enc = new Encryptor(keyFileName, inputFileName);
		}
		else if (option == 'd' || option == 'D') {
			Decryptor dec = new Decryptor(keyFileName, inputFileName);
		} else throw new IllegalArgumentException("[option] must be 'e' or 'd'");
		// String t=hexToBinary("f2");
		// System.out.println(t);
	}
}
