import java.io.*;
import java.util.*;


public class Encryptor implements AESConstants {

//	private static String keyFile;
//	private static String filename;
	private static Scanner keyScanner;
	private static Scanner inputScanner;
	
	public Encryptor(String keyFile, String inputFilename) throws FileNotFoundException {
//		this.keyFile = keyFile;
//		this.filename = filename;

		keyScanner = new Scanner(new File(keyFile));
		inputScanner = new Scanner(new File(inputFilename));
		char[][] newState=subBytes(TEST_STATE);
		print(TEST_STATE);
		System.out.println();
		shiftRows(TEST_STATE);
		
		
		System.out.println(Arrays.deepToString(newState));
		
		
		
		//printKeyArray();
		
	}

//	private char[] (byte key[4*Nk], word w[Nb*(Nr+1)], Nk){
//		
//		
//	}
//	
	
	
	
	
	
	
	
	
	
	
	//ex: value=f2 , nible0=f , nible1=2
	public static char[][] subBytes(char[][] state){
		char[][] newState=new char[BLOCK_SIZE][BLOCK_SIZE];
		
		for(int row=0; row<BLOCK_SIZE; row++){
			for (int col=0; col<BLOCK_SIZE; col++){
				char value=state[row][col];
				int nibble0=(value & 0xf0)>>4;
				int nibble1=value & 0x0f;
				
				newState[row][col]=SBOX[nibble0][nibble1];
				System.out.printf("Value: %h, Nibble0: %h, Nibble1: %h, NewState: %h\n",value,nibble0,nibble1,(newState[row][col]), (state[row][col]));
			}
		}
		return newState;
	}
	
	
	public static char[][] shiftRows(char[][] state){
		char[][] newState=new char[BLOCK_SIZE][BLOCK_SIZE];
		char[] temp=new char[BLOCK_SIZE];
		//print(state);
		for(int row=0; row<BLOCK_SIZE; row++){
			for (int col=0; col<BLOCK_SIZE; col++){
				temp[col]=state[row][(col+row)%BLOCK_SIZE];
			}
			for (int col=0; col<BLOCK_SIZE; col++){
			newState[row][col]=temp[col];
			}
			}
		print(newState);
		return newState;
	}
	
	private static void print(char[][] state){
		System.out.println("--begin print--");
		for(int row=0; row<BLOCK_SIZE; row++){
			for (int col=0; col<BLOCK_SIZE; col++){
				
				System.out.printf("%h ",(state[row][col]));
			}
			System.out.println();
			}
		System.out.println("--end print--");
	}
	
	private void printKeyArray() {
		String currentLine = "";
		while (keyScanner.hasNextLine()) {
			currentLine = keyScanner.nextLine();
			System.out.println("");
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