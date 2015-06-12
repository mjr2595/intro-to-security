public enum InstructionObject {
	READ, WRITE, BAD;
	private int value;
	private String subjectName, objectName;

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
}
