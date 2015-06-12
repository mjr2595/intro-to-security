public enum SecurityLevel {

	LOW(0), HIGH(1);

	private final int val;

	private SecurityLevel(int val){
		this.val = val;	
	}

	public int getVal(){
		return this.val;	
	}

	public boolean dominates(SecurityLevel other){
	
		return this.val >= other.val;
	}
}
