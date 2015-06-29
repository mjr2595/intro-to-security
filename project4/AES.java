import java.io.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;
public class AES{
	
	public static void main(String[] args) throws FileNotFoundException{
	char option= args[0].charAt(0);
	String keyFileName=args[1];
	String inputFileName=args[2];
	String t=hexToBinary("f2");
	System.out.println(t);
	
	File keyFile = new File("keyFile");
	Scanner keyFileReader = new Scanner(keyFile);
	String[] key= inputToArray(keyFileReader.nextLine());
	
	
	//int[] key2= inputToArray(keyFileReader.nextLine());
	
	
	}
	
	public static String[] inputToArray(String input) {
		String[] array=new String[input.length()/2];
		for(int i=0, z=0; i<input.length(); i+=2, z++){
			array[z]=hexToBinary(""+input.charAt(i)+input.charAt(i+1));
			}
		
		for(int i=0; i<array.length; i++){
			System.out.println(array[i]);
		}
	 return array;
	}
	
	
	
	public static String hexToBinary(String hex) {
	    int hexInt = Integer.parseInt(hex, 16);
	    String binary = Integer.toBinaryString(hexInt);
	    return binary;
	}
}