import java.util.*;
import java.io.*;


public class PasswordCrack {


	private static ArrayList<String> dictionaryList;
	private static ArrayList<User> userList;
	private static double beginTime;
	private static final int numLevels = 3;
	
	private PasswordCrack() {}
	
	public static void main(String[] args) throws FileNotFoundException {
		beginTime = System.currentTimeMillis();
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
		
		
		
		
		//for (int i=0; i < numLevels; i++){
		
			
			//level 1 User
			for(int i=0; i<userList.size(); i++){
				User user=userList.get(i);
				if(level1(user,user.getFirstName())){
					i--;
					userList.remove(user);
				}
				else if(level1(user,user.getLastName())){
					i--;
					userList.remove(user);
				}
				else if(level1(user,user.getUsername())){
					i--;
					userList.remove(user);
				}
				//System.out.println(user.getFirstName());
				//System.out.println(i);
			}
			
			//level 2 User
			for(int i=0; i<userList.size(); i++){
				User user=userList.get(i);
				if(level2(user,user.getFirstName())){
					i--;
					userList.remove(user);
				}
				else if(level2(user,user.getLastName())){
					i--;
					userList.remove(user);
				}
				else if(level2(user,user.getUsername())){
					i--;
					userList.remove(user);
				}
				//System.out.println(user.getFirstName());
				//System.out.println(i);
			}
			
			//level 3 User TODO  not working
			for(int i=0; i<userList.size(); i++){
				User user=userList.get(i);
				if(level3(user,user.getFirstName())){
					i--;
					userList.remove(user);
				}
				else if(level3(user,user.getLastName())){
					i--;
					userList.remove(user);
				}
				else if(level3(user,user.getUsername())){
					i--;
					userList.remove(user);
				}
				//System.out.println(user.getFirstName());
				//System.out.println(i);
			}
			
			
			
			
			
			
		//}
		
		
		
		
		
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

//				 System.out.println(current.getUsername());
//				 System.out.println(current.getHash());
//				 System.out.println(current.getFirstName());
//				 System.out.println(current.getLastName());

				userList.add(current);
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private static boolean level1(User user, String word){
		if(isCracked(user, dfirst(word))) return true;
		else if(isCracked(user, dlast(word))) return true;
		else if(isCracked(user, upper(word))) return true;
		else if(isCracked(user, lower(word))) return true;
		else if(isCracked(user, duplicate(word))) return true;
		else return false;
	}
	
	private static boolean level2(User user, String word){
		if(isCracked(user, (word))) return true;
		else if(isCracked(user, capitalize(word))) return true;
		else if(isCracked(user, ncapitalize(word))) return true;
		else if(isCracked(user, reflect(word))) return true;
		else if(isCracked(user, reverse(word))) return true;
		else return false;
	}
	
	private static boolean level3(User user, String word){
		///WORK ON Prepend and append
		return false;
	}
	
	//will return true if the test password is the ACTUAL user's password 
	private static boolean isCracked(User u, String password){
		if((jcrypt.crypt(u.getSalt(), password)).equals(u.getHash())){
			System.out.println(password);
			System.out.printf("%.2fms\n",((double)System.currentTimeMillis()-beginTime));
			return true;
		}else
			return false;
		
	}
	
	
	
	//FOR DEBUG ONLY... Delete before turning in.
	private static void printHelper(){
		String test ="hello";
		System.out.println(prepend(test,'c'));
		System.out.println(append(test,'c'));
		System.out.println(dfirst(test));
		System.out.println(dlast(test));
		System.out.println(reverse(test));
		System.out.println(duplicate(test));
		System.out.println(reflect(test));
		System.out.println(capitalize(test));
		System.out.println(ncapitalize(test));
	}
	
	// adds character into word: level 3, will need to loop over every character
	private static String prepend(String input, char letter){
		return letter+input;
	}
	
	// adds character to end of word, will need to loop over every character
	private static String append(String input, char letter){
		return input+letter;
	}
	
	// Deletes the first character from the word
	private static String dfirst(String input){
		return input.substring(1);
	}
	
	// Deletes the last character from the word
	private static String dlast(String input){
		return input.substring(0, input.length()-1);	
	}
		
	// reverses string: ex: hello will be olleh
	private static String reverse(String input){
	    char[] newArray = input.toCharArray();
	    char letter;
	    for(int i=0; i<newArray.length/2; i++) {
	    	letter = newArray[i];
	    	newArray[i] = newArray[newArray.length-i-1];
	    	newArray[newArray.length-i-1] = letter;
	    }
		return String.valueOf(newArray);	
	}
		
	// duplicates string: hellohello
	private static String duplicate(String input){
		return input+input;	
	}
	
	//reflects string: helloolleh
	private static String reflect(String input){
		return input+reverse(input);	
	}
	
	
	private static String upper(String input){
		return input.toUpperCase();	
	}
	
	private static String lower(String input){
		return input.toLowerCase();	
	}
	
	//only first letter capitalize
	private static String capitalize(String input){
		
		return input.toUpperCase().charAt(0)+dfirst(input);	
	}
	
	//first letter lowercase, rest of letters uppercase
	private static String ncapitalize(String input){
		
		return input.toLowerCase().charAt(0)+dfirst(input).toUpperCase();	
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