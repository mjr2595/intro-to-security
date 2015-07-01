import java.io.*;
import java.util.*;


public class Decryptor implements AESConstants {

	private static String keyFile;
	private static String filename;
	private static Scanner scanner;
	
	public Decryptor(String keyFile, String filename) throws FileNotFoundException {
		this.keyFile = keyFile;
		this.filename = filename;

		scanner = new Scanner(new File(filename));
		//printKeyArray();
		
	}
}