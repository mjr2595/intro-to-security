import java.io.*;
import java.util.*;


public class Decryptor implements AESConstants {

	private static char option;
	private static String key;
	private static String filename;
	private static Scanner scanner;
	
	public Decryptor(char option, String key, String filename) throws FileNotFoundException {
		this.option = option;
		this.key = key;
		this.filename = filename;

		scanner = new Scanner(new File(filename));
		//printKeyArray();
		
	}
}