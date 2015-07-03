import java.io.*;
import java.util.*;


public class Decryptor implements AESConstants {

	private static String keyFileName;
	private static String inputFilename;
	private static char[][] inputKey;
	private static char[][] expandedKey;
	
	public Decryptor(String keyFileName, String inputFilename) throws FileNotFoundException {
		
		Decryptor.keyFileName=keyFileName;
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
		getKey();
		expandKey();
		printInputKey(inputKey);
		//System.out.printf("%x \n",0x00);
		printExpanded();
		//System.out.println(Arrays.deepToString(expandedKey));
		}

	
	
	public static void getKey() throws FileNotFoundException{
		Scanner keyScanner =new Scanner(new File(keyFileName));
		String key="";
		while(keyScanner.hasNextLine()){
			key=keyScanner.nextLine().toUpperCase();
			//System.out.print(key);
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
				i+=2;
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
				newState[row][col]=INV_SBOX[nibble0][nibble1];
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
		for(int row=BLOCK_SIZE-1,count=1; row>0; row--,count++){
			for (int col=0; col<BLOCK_SIZE; col++){
				temp[col]=state[row][(col+count)%BLOCK_SIZE];
			}
			for (int col=0; col<BLOCK_SIZE; col++){
			newState[row][col]=temp[col];
			}
		}
		for (int col=0; col<BLOCK_SIZE; col++){
			newState[0][col]=state[0][col];
		}
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
		//int count = 1;
		while (i < 60) {
			if (i%4 == 0) {
				System.out.println(i);
				rotWord(i);
				subColBytes(i);
				xor(i, i/KEY_COLUMNS);
				//count++;
			} else {
				xor(i, 0);
			}
			i++;
		}
	}

	private static void xor(int col, int rcon) {
		// every 8 cols xor with rcon
		if (rcon !=0) {
			expandedKey[0][col] ^= (expandedKey[0][col-KEY_COLUMNS] ^ RCON[rcon]);
			// equal to xoring zero
			for (int row = 1; row < BLOCK_SIZE; row++) {
				expandedKey[row][col] ^= (expandedKey[row][col-KEY_COLUMNS]);
			}

		} else {
			for (int row = 0; row < BLOCK_SIZE; row++) {
				expandedKey[row][col] = (char)((expandedKey[row][col-KEY_COLUMNS]) ^ (expandedKey[row][col-1]));
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

	private static void printInputKey(char[][] state){
		System.out.println("\nThe CipherKey is:");
		for(int row=0; row<state.length; row++){
			for (int col=0; col<state[row].length; col++){
				char temp = state[row][col];
				if (temp == 0)
					System.out.printf("0%h ",temp);
				else System.out.printf("%h ",temp);
			}
			System.out.println();
		}
	}
	
	private void printExpanded() {
		System.out.println("\nThe expanded key is:");
		for(int row=0; row<expandedKey.length; row++){
			for (int col=0; col<expandedKey[row].length; col++){
				char temp = expandedKey[row][col];
				if (temp == 0)
					System.out.printf("0%h",temp);
				else System.out.printf("%h",temp);

				if (col != 0 && (col-3)%4 == 0)
					System.out.print(" ");

			}
			System.out.println();
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

	// DR. YOUNG's CODE

	private char mul (int a, char b) {
		// int inda = (a < 0) ? (a + 256) : a;
		// int indb = (b < 0) ? (b + 256) : b;

		if ( (a != 0) && (b != 0) ) {
		    int index = (LOG_TABLE[a] + LOG_TABLE[b]);
		    char val = (char)(A_LOG_TABLE[ index % 255 ] );
		    return val;
		}
		else {
		    return 0;
		
	    } // mul

	}
	public void mixColumn (int c, char state[][]) {
		// This is another alternate version of mixColumn, using the 
		// logtables to do the computation.
		
		char a[] = new char[4];
		
		// note that a is just a copy of st[.][c]
		for (int i = 0; i < 4; i++) 
		    a[i] = state[i][c];
		
		// This is exactly the same as mixColumns1, if 
		// the mul columns somehow match the b columns there.
		state[0][c] = (char)(mul(2,a[0]) ^ a[2] ^ a[3] ^ mul(3,a[1]));
		state[1][c] = (char)(mul(2,a[1]) ^ a[3] ^ a[0] ^ mul(3,a[2]));
		state[2][c] = (char)(mul(2,a[2]) ^ a[0] ^ a[1] ^ mul(3,a[3]));
		state[3][c] = (char)(mul(2,a[3]) ^ a[1] ^ a[2] ^ mul(3,a[0]));
    } // mixColumn2
}