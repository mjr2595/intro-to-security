public enum InstructionObject {
	READ, WRITE, BAD, CREATE, DESTROY, RUN;
	private int value;
	private String subjectName, objectName, printText;

	public void setValue(int value){
		this.value = value;
	}

	public void setSubjectName(String subjectName){
		this.subjectName = subjectName;
	}

	public void setObjectName(String objectName){
		this.objectName = objectName;
	}

	public int getValue(){
		return this.value;
	}

	public String getSubjectName(){
		return this.subjectName;
	}

	public String getObjectName(){
		return this.objectName;	
	}

	public String getPrintText(){

		String output = "";
		if(this.name().equals("BAD"))
			return "Bad Instruction\n";
		else 
			output = subjectName.toLowerCase() + " " + this.name().toLowerCase() + "s ";
		if(this.equals(InstructionObject.WRITE))
			output += "value " + this.value + " to ";

		output += this.objectName.toLowerCase() + "\n"; 
		return output;
	}	
}
