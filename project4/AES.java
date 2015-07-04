import java.io.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;


public class AES {
	
	public static void main(String[] args) throws FileNotFoundException {
		long startTime = System.currentTimeMillis();
		char option = args[0].charAt(0);
		String keyFileName = args[1];
		String inputFileName = args[2];

		if (option == 'e' || option == 'E') {
			Encryptor enc = new Encryptor(keyFileName, inputFileName);
		}
		else if (option == 'd' || option == 'D') {
			Decryptor dec = new Decryptor(keyFileName, inputFileName);
		} else throw new IllegalArgumentException("[option] must be 'e' or 'd'");

		
		File inputFile=new File(inputFileName);;
		long endTime   = System.currentTimeMillis();
		long totalTime = (endTime - startTime)/1000;
		System.out.println("totalTime: "+totalTime+"sec");
		
		long bandwidth=(inputFile.length()*8)/totalTime;
		System.out.println("Bandwidth: "+bandwidth+ "mb/s");
	}
}
