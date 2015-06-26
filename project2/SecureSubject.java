import java.io.FileOutputStream;
import java.io.IOException;


public class SecureSubject {

	private final String name;
	//TEMP is the most recently read value
	private int TEMP;
	private byte secret;
	private int count;
	private FileOutputStream out;

	public SecureSubject(String name){
		this(name, "");
	}

	public SecureSubject(String name, String file){
		this.name = name;	
		this.TEMP = 0;

		if(name.equals("Lyle")){
			this.secret = 0;
			this.count = 0;
			try{
				// check if file has .txt extension
				// if it does, delete it.
				if (file.length() > 4 && file.substring(file.length()-4, file.length()).equals(".txt"))
					file = file.substring(0, file.length()-4);

				out = new FileOutputStream(file + ".out");
			} catch (IOException e) {
				e.printStackTrace();	
			}
		}
	}

	public String getName(){
		return this.name;
	}

	public void setTEMP(int value){
		this.TEMP = value;
	}

	public int getTEMP(){
		return this.TEMP;
	}

	public void run(){
		if(this.name.equals("Lyle")){

			//shift saved TEMP value over the number of bits looked at in the current byte then append that bit from TEMP onto secret with |
			secret |= (TEMP << count);

			count++;

			if(count == 8){
				//write secret to file (secret is now a full byte passed from Hal)
				try{	
					out.write(secret);
				} catch (IOException e) {
					e.printStackTrace();	
				}

				count = 0;
				secret = 0;
			}
		}
	}
}
