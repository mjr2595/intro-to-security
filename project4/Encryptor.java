import java.io.*;
import java.util.*;


public class Encryptor implements AESConstants {

	private static char option;
	private static String key;
	private static String filename;
	private static Scanner scanner;
	
	public Encryptor(char option, String key, String filename) throws FileNotFoundException {
		this.option = option;
		this.key = key;
		this.filename = filename;

		scanner = new Scanner(new File(filename));
		printKeyArray();
		
	}

	private void printKeyArray() {
		String currentLine = "";
		while (scanner.hasNextLine()) {
			currentLine = scanner.nextLine();
			String[] keyArray = inputToArray(currentLine);
			System.out.println(Arrays.toString(keyArray));
		}
	}
	
	private String[] inputToArray(String input) {
		String[] array=new String[input.length()/2];
		for(int i=0, z=0; i<input.length(); i+=2, z++){
			array[z]=hexToBinary(""+input.charAt(i)+input.charAt(i+1));
		}
		
		for(int i=0; i<array.length; i++){
			System.out.println(array[i]);
		}
	 	return array;
	}  
	
	private String hexToBinary(String hex) {
	    int hexInt = Integer.parseInt(hex, 16);
	    String binary = Integer.toBinaryString(hexInt);
	    return binary;
	}
}