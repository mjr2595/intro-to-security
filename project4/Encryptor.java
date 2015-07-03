import java.io.*;
import java.util.*;


public class Encryptor implements AESConstants {
	private static String keyFileName;
	private static String inputFilename;
	private static char[][] inputKey;
	private static char[][] expandedKey;
	
	public Encryptor(String keyFileName, String inputFilename) throws FileNotFoundException {
		
		Encryptor.keyFileName=keyFileName;
//		
////		keyScanner = new Scanner(new File(keyFile));
////		inputScanner = new Scanner(new File(inputFilename));
		expandedKey = new char[4][60];
//		//char[][] newState=subBytes(TEST_STATE);
//		print(TEST_STATE);
//		System.out.println();
//		print(shiftRows(TEST_STATE));
//		print(roundKey(TEST_STATE,shiftRows(TEST_STATE)));
//		
		//System.out.println((char) );
		key();
		expandKey();
		print(inputKey);
		print(expandedKey);
		//System.out.println(Arrays.deepToString(expandedKey));
		}

	
	
	public static void key() throws FileNotFoundException{
		Scanner keyScanner =new Scanner(new File(keyFileName));
		String key=null;
		while(keyScanner.hasNextLine()){
			key=keyScanner.nextLine().toUpperCase();
			if(validHex(key)){
				break;
			}
			else if(!keyScanner.hasNextLine()){
				System.out.println("Bad key input");
				return;
			}
		}
		
		//System.out.println(key);
		inputKey=new char[BLOCK_SIZE][KEY_COLUMNS];
		int i=0;
		for(int row=0; row<BLOCK_SIZE; row++){
			for (int col=0; col<KEY_COLUMNS; col++){
				if(i<key.length()-1){
					int hex=Integer.decode("0x"+key.charAt(i)+key.charAt(i+1));
					inputKey[row][col]=(char)hex;
				} else {
					if(i<key.length()){
						int hex=Integer.decode("0x"+key.charAt(i)+"0");
						inputKey[row][col]=(char)hex;
					}
					inputKey[row][col]=0x00;
				}
				i++;
			}
		}
		
	}

	
	private static boolean validHex(String line){
		String hex="0123456789ABCDEF";
		for(int i=0; i<line.length(); i++){
			String temp=""+line.charAt(i);
			if(!hex.contains(temp)){
				return false;
			}
		}
		return true;
	}
	
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
	
	
	//Assuming state and roundkey are same size... we need to pass in only the key for
	//this specific round. (this can be changed if needed depending on how expanded key works.
	public static char[][] roundKey(char[][] state, char[][] key){
		char[][] newState=new char[BLOCK_SIZE][BLOCK_SIZE];
		for(int row=0; row<BLOCK_SIZE; row++){
			for (int col=0; col<BLOCK_SIZE; col++){
		newState[row][col]=(char) (state[row][col] ^ key[row][col]);
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
	//	print(newState);
		return newState;
	}
	
	public static void expandKey() {
		
		for(int row=0; row<BLOCK_SIZE; row++){
			for (int col=0; col<KEY_COLUMNS; col++){
				//System.out.println(row+" "+ col+ " "+inputKey[row][col]);
				expandedKey[row][col] = inputKey[row][col];


			}
		}
		int i = KEY_COLUMNS;
		while (i < 60) {
			if (i%8 == 0) {
				//System.out.println(i);
				rotWord(i);
				subColBytes(i);
				xor(i, i/KEY_COLUMNS);
			} else {
				xor(i, 0);
			}
			i++;
		}
	}

	private static void xor(int col, int rcon) {
		// every 8 cols xor with rcon
		if (rcon !=0) {
			expandedKey[0][col] ^= (expandedKey[0][col-4] ^ RCON[rcon]);
			// equal to xoring zero
			for (int row = 1; row < BLOCK_SIZE; row++) {
				expandedKey[row][col] ^= (expandedKey[row][col-KEY_COLUMNS]);
			}

		} else {
			for (int row = 0; row < BLOCK_SIZE; row++) {
				expandedKey[row][col] ^= (expandedKey[row][col-KEY_COLUMNS]);
			}
		}
	}

	private static void subColBytes(int col) {
		for (int row=0; row<BLOCK_SIZE; row++){
			char value=expandedKey[row][col];
			int nibble0=(value & 0xf0)>>4;
			int nibble1=value & 0x0f;
			expandedKey[row][col]=SBOX[nibble0][nibble1];
			//System.out.printf("[*subCOLBytes* Value: %h, Nibble0: %h, Nibble1: %h, NewState: %h\n",value,nibble0,nibble1,(expandedKey[row][col]), (expandedKey[row][col]));
		}
	}


	private static void rotWord(int colIndex) {
		for (int row = 0; row < BLOCK_SIZE-1; row++) {
			expandedKey[row][colIndex] = expandedKey[row+1][colIndex-1];
		}
		expandedKey[BLOCK_SIZE-1][colIndex] = expandedKey[0][colIndex-1];
	}

	private static void print(char[][] state){
		System.out.println("--begin print--");
		for(int row=0; row<state.length; row++){
			for (int col=0; col<state[row].length; col++){
				
				System.out.printf("%h",(state[row][col]));
			}
		//	System.out.println();
			}
		System.out.println("--end print--\n");
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