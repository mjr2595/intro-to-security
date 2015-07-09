import java.util.*;
import java.io.*;


public class PasswordCrack {

	private static ArrayList<String> dictionaryList;
	private static ArrayList<String> dictionaryListBuilder;
	private static ArrayList<User> userList;
	private static double beginTime;
	private static boolean addToList = true;
	private static Map<Character, Character> leetMap;
	
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
		fillUserArray(passwordFile);
		fillLeetMap();
		//NAMES		
			
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
		}
		
		//level 3 User
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
		}
		
		// DICTIONARY
		for(int i=0; i<dictionaryList.size(); i++){
			String word=dictionaryList.get(i);
			for(int z=0; z<userList.size(); z++){
				User user= userList.get(z);
				if(level1(user,word)){
					z--;
					userList.remove(user);
				}
			}
			
			for(int z=0; z<userList.size(); z++){
				User user= userList.get(z);
				if(level2(user,word)){
					z--;
					userList.remove(user);
				}
			}
			
			for(int z=0; z<userList.size(); z++){
				User user= userList.get(z);
				if(level3(user,word)){
					z--;
					userList.remove(user);
				}
			}
		}
		// Don't add mangled words during last round
		addToList=false;
		
		for(int i=0; i<dictionaryListBuilder.size(); i++){
			String word=dictionaryListBuilder.get(i);
			//dictionaryListBuilder.remove(i);
			for(int z=0; z<userList.size(); z++){
				User user= userList.get(z);
				if(level1(user,word)){
					z--;
					userList.remove(user);
				}
			}
			
			for(int z=0; z<userList.size(); z++){
				User user= userList.get(z);
				if(level2(user,word)){
					z--;
					userList.remove(user);
				}
			}
	
			for(int z=0; z<userList.size(); z++){
				User user= userList.get(z);
				if(level3(user,word)){
					z--;
					userList.remove(user);
				}
			}
		}
	}

	private static void fillDictionaryArray(String filename) {
		dictionaryList = new ArrayList<String>();
		dictionaryListBuilder = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(new File(filename));
			while (scanner.hasNextLine()) {
				String word = scanner.nextLine();
				if (word.length() > 8)
					word = word.substring(0,8);
				
				dictionaryList.add(word.toLowerCase());
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
				userList.add(current);
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

	private static void fillLeetMap() {
		leetMap = new HashMap<Character, Character>();
		char letter[] = {'a', 'b', 'e', 'i', 'o', 's', 't', 'z'};
		char symbol[] = {'@', '8', '3', '1', '0', '$', '7', '2'};
		for (int i = 0; i < letter.length; i++) {
			leetMap.put(letter[i], symbol[i]);
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
		else if(isCracked(user, reflect1(word))) return true;
		else if(isCracked(user, reflect2(word))) return true;
		else if(isCracked(user, reverse(word))) return true;
		else if(isCracked(user, replaceAllE(word))) return true;
		else if(isCracked(user, replaceAllA(word))) return true;
		else if(isCracked(user, replaceAllS(word))) return true;
		else if(isCracked(user, leet(word))) return true;
		else return false;
	}
	
	private static boolean level3(User user, String word){
		for(int i=33; i<127; i++){
			if(isCracked(user, prepend(word, (char)i))) return true;
			else if(isCracked(user, append(word, (char)i))) return true;
		}
		return false;
	}
	
	//will return true if the test password is the ACTUAL user's password 
	private static boolean isCracked(User u, String password){
		if((jcrypt.crypt(u.getSalt(), password)).equals(u.getHash())){
			System.out.println("Username: " + u.getUsername() + " Password: " + password);
			System.out.printf("   Cracked in %.2fms\n",((double)System.currentTimeMillis()-beginTime));
			return true;
		}else
			return false;
		
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
		if(addToList) dictionaryListBuilder.add(input.substring(1));
		return input.substring(1);
	}
	
	// Deletes the last character from the word
	private static String dlast(String input){
		if(addToList) dictionaryListBuilder.add(input.substring(0, input.length()-1));
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
	    if(addToList)   dictionaryListBuilder.add(String.valueOf(newArray));
		return String.valueOf(newArray);	
	}
		
	// duplicates string: hellohello
	private static String duplicate(String input){
		if(addToList) 	dictionaryListBuilder.add(input+input);
		return input+input;	
	}
	
	//reflects string: helloolleh
	private static String reflect1(String input){
		String s=input+reverse(input);
		if(addToList) dictionaryListBuilder.add(s);
		return s;	}
	
	private static String reflect2(String input){
		String s=reverse(input)+input;
		if(addToList) dictionaryListBuilder.add(s);
		return s;
	}
	
	private static String upper(String input){
		String s = input.toUpperCase();
		if(addToList) dictionaryListBuilder.add(s);
		return s;	
	}
	
	private static String lower(String input){
		String s = input.toLowerCase();
		if(addToList) dictionaryListBuilder.add(s);
		return s;
	}
	
	//only first letter capitalize
	private static String capitalize(String input){
		String s = input.toUpperCase().charAt(0)+dfirst(input);	
		if(addToList) dictionaryListBuilder.add(s);
		return s;
	}
	
	//first letter lowercase, rest of letters uppercase
	private static String ncapitalize(String input){
		String s = input.toLowerCase().charAt(0)+dfirst(input).toUpperCase();
		if(addToList) dictionaryListBuilder.add(s);
		return s;
	}

	private static String leet(String input) {
		for (int i = 0; i < input.length(); i++) {
			char currentChar = input.charAt(i);
			if (leetMap.containsKey(currentChar)) {
				 input = input.replace(currentChar, leetMap.get(currentChar));
			}
		}
		if(addToList) dictionaryListBuilder.add(input);
		return input;
	}

	private static String replaceAllE(String input) {
		String s = input.replaceAll("e", "3");
		if(addToList) dictionaryListBuilder.add(s);
		return s;
	}
	
	private static String replaceAllA(String input) {
		String s = input.replaceAll("a", "@");
		if(addToList) dictionaryListBuilder.add(s);
		return s;
	}

	private static String replaceAllS(String input) {
		String s = input.replaceAll("s", "\\$");
		if(addToList) dictionaryListBuilder.add(s);
		return s;
	}
}