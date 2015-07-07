import java.util.*;
import java.io.*;


public class PasswordCrack {


	private static ArrayList<String> dictionaryList;
	private static ArrayList<User> userList;

	private PasswordCrack() {}
	
	public static void main(String[] args) throws FileNotFoundException {

		if (args.length != 2)
			System.out.println("Usage: java PasswordCrack dictionary passwords");
		else {
			String dictionary = args[0];
			String passwords = args[1];
			// get crackin'
			PasswordCrack.crack(dictionary, passwords);
		}
	}

	public static void crack(String dictionaryFile, String passwordFile) {
		fillDictionaryArray(dictionaryFile);
		// make user class
		fillUserArray(passwordFile);
		// iterate over each user by 
		// 	mangle name
		//	mangle dictionary
		// 	brute force
		// remove user when password cracked

	}

	private static void fillDictionaryArray(String filename) {
		dictionaryList = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(new File(filename));
			while (scanner.hasNextLine()) {
				String word = scanner.nextLine();
				dictionaryList.add(word);
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

	private static void fillUserArray(String filename) {
		userList = new ArrayList<User>();
		try {
			Scanner scanner = new Scanner(new File(filename));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				User current = new User(line);

				// System.out.println(current.getUsername());
				// System.out.println(current.getHash());
				// System.out.println(current.getFirstName());
				// System.out.println(current.getLastName());

				userList.add(current);
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
} 
/*
game plan:

loop levels
	
	loop users
		name

	loop users
		dictionary (map(word, mangled state))

loop 8->1 (password length)
	brute

*/