import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.io.PrintWriter;


public class CovertChannel {

	private static ReferenceMonitor monitor;
	private static ArrayList<SecureSubject> subjectList;
	private static PrintWriter writer;
	private static boolean verbose;
	private static String filename;

	public static final int TIMING = 0;

	

	public static void main(String[] args){

		CovertChannel sys = new CovertChannel();

		SecurityLevel low = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;

		

		filename = "";

		if(args.length == 1){
			filename = args[0];
			//just the input file
			//System.err.println("Invalid number of arguments. You may only pass one argument after 'java CovertChannel' - the name of the instructionList file.\nYou passed: " + args.length + " arguments.");
			//return;
		}
		else if(args.length == 2){
			if(args[0].equalsIgnoreCase("V") || args[0].equalsIgnoreCase("-V"))	{
				verbose = true;
				try{	
					writer = new PrintWriter("log");
				} catch (FileNotFoundException e) {
					e.printStackTrace();	
				}
				filename = args[1];
			}
			else System.err.println("Your first argument must be V or v.");
		}
		else {
			System.err.println("Your arguments are invalid.");
			return;
		}
		sys.createNewSubject("Lyle", low);
		sys.createNewSubject("Hal", high);

		System.out.println("\nReading from file: " + filename + "\n");
		
		if(TIMING == 1){
			long duration = 0;

			for (int i = 0; i < 10; i++) {
				long start = System.nanoTime();
				executeFileCommands(filename);
				long stop = System.nanoTime();
				duration += (stop - start)/1000000;
				
			}
			duration = duration/10;
			System.out.println("Average Total time: " + duration + " milliseconds");
		} else {
			executeFileCommands(filename);
		}
	}

	private CovertChannel () {

		monitor = new ReferenceMonitor();
		subjectList = new ArrayList<SecureSubject>();
		verbose = false;
	}

	private static void executeFileCommands(String path) {
		File file = new File(path);	
		try {
			Scanner reader = new Scanner(file);
			SecureSubject lyle = null;

			//save lyle for later
			for(SecureSubject s: subjectList)
				if(s.getName().equals("Lyle"))
					lyle = s;

			if(lyle == null){
				System.err.println("Lyle is missing.");
				return;
			}

			while(reader.hasNextLine()){
				byte val;
				byte[] currentBytes = reader.nextLine().getBytes();

				//current line is only a newline character (edge case)
				if(currentBytes.length == 0) {
					currentBytes = new byte[]{0xA};
					byteMagic(lyle, 0, currentBytes);
				}

				//regular case - line that's not empty
				else {
					for(int i=0; i<currentBytes.length; i++) 
						byteMagic(lyle, i, currentBytes);

					//we have reached the end of the line. time for a newline character.
					//but we don't want to assume there is a new line.. edge case on the last line
					if(reader.hasNextLine()){

						//0xA = '\n' in ascii in linux
						//we can't just send in currentBytes[0] because what about empty lines?
						currentBytes = new byte[]{0xA};
						byteMagic(lyle, 0, currentBytes);
					}
				}
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

	//helper method that separates bits from a byte[] and sends them through the covert channel to Lyle 
	private static void byteMagic(SecureSubject lyle, int i, byte[] currentBytes) {
		byte mask = 0x01;
		byte val = 0x00;

		while(mask != 0x00){
			//we need this cast because in java when you & two bytes, you get an int. stupid.
			val = (byte)(currentBytes[i] & mask);
			//val is equal to a single bit which we will run and pass to Lyle
			
			if(val == 0){
				//hal makes the object			
				executeInstruction(getInstructionObject("CREATE Hal Secrets"));
				if(verbose)
					writer.println(getState(getInstructionObject("CREATE Hal Secrets")));
			}

			if(verbose){

				executeInstruction(getInstructionObject("CREATE Lyle Secrets"));	
				writer.println(getState(getInstructionObject("CREATE Lyle Secrets")));

				executeInstruction(getInstructionObject("WRITE Lyle Secrets 1"));	
				writer.println(getState(getInstructionObject("WRITE Lyle Secrets 1")));

				executeInstruction(getInstructionObject("READ Lyle Secrets"));	
				writer.println(getState(getInstructionObject("READ Lyle Secrets")));

				executeInstruction(getInstructionObject("DESTROY Lyle Secrets"));	
				writer.println(getState(getInstructionObject("DESTROY Lyle Secrets")));
			}
			
			else {
			
				executeInstruction(getInstructionObject("CREATE Lyle Secrets"));	
				executeInstruction(getInstructionObject("WRITE Lyle Secrets 1"));	
				executeInstruction(getInstructionObject("READ Lyle Secrets"));	
				executeInstruction(getInstructionObject("DESTROY Lyle Secrets"));	
			}

			//lyle is going to store the value that hal just sent him and possibly write it to file
			lyle.run();
			mask <<= 1;
		}
	}

	public static void executeInstruction(InstructionObject obj) {

		if(obj.equals(InstructionObject.READ)){
			int result = monitor.executeRead(obj.getSubjectName(), obj.getObjectName());

			for(int i=0; i<subjectList.size(); i++){

				if(subjectList.get(i).getName().equals(obj.getSubjectName()))
					subjectList.get(i).setTEMP(result);
			}
		}

		//not sure if this .equals is going to work.
		else if(obj.equals(InstructionObject.WRITE)){
			monitor.executeWrite(obj.getSubjectName(), obj.getObjectName(), obj.getValue());
		}
		else if(obj.equals(InstructionObject.CREATE)){
			monitor.executeCreate(obj.getSubjectName(), obj.getObjectName());
		}
		else if(obj.equals(InstructionObject.DESTROY)){
			monitor.executeDestroy(obj.getSubjectName(), obj.getObjectName());
		}
		else 
			System.err.println("Internal failure. Abort!");

		//note you cannot execute a run from here. You must do so from the CovertChannel calling the SecureSubject's run function from there.
		//you don't have access to the SecureSubject list here
	}
	public void createNewSubject(String name, SecurityLevel level){
		if (name.equalsIgnoreCase("Lyle"))
			subjectList.add(new SecureSubject(name, filename));
		else 
			subjectList.add(new SecureSubject(name));

		monitor.createNewSubject(name, level);
	}

	private static InstructionObject getInstructionObject(String input) {

		//split the string into words with whitespace as the delimiter.
		String[] split = input.split("\\s+");

		if(split[0].equals("READ") || split[0].equals("CREATE") || split[0].equals("DESTROY")) {
			InstructionObject obj = InstructionObject.valueOf(split[0]);
			obj.setSubjectName(split[1]);
			obj.setObjectName(split[2]);
			return obj;
		}

		else if(split[0].equals("WRITE")) {	
			InstructionObject write = InstructionObject.WRITE;
			write.setSubjectName(split[1]);
			write.setObjectName(split[2]);
			write.setValue(Integer.parseInt(split[3]));
			return write;
		}

		return InstructionObject.BAD;
	}

	public static boolean isNumber(String num) {

		try {
			Integer.parseInt(num);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

	private static String getState(InstructionObject instruction) {

		String output = "";
		output += instruction.getPrintText();
		return output;
	}
}
