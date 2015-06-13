public class SecureSubject {

	private final String name;
	//TEMP is the most recently read value
	private int TEMP;

	public SecureSubject(String name){
		this.name = name;	
		this.TEMP = 0;
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
}
