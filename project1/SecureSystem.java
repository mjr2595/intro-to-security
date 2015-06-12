import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class SecureSystem {

	private static ReferenceMonitor monitor;
	private static ArrayList<SecureSubject> subjectList;

	public static void main(String[] args){

		SecureSystem sys = new SecureSystem();

		SecurityLevel low = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;

		sys.createNewSubject("Lyle", low);
		sys.createNewSubject("Hal", high);

		sys.monitor.createNewObject("LObj", low);
		sys.monitor.createNewObject("HObj", high);

		String filename = args[0];

		System.out.println("Reading from file: " + filename + "\n");

		executeFileCommands(filename);
	}

	public SecureSystem () {

		monitor = new ReferenceMonitor();
		subjectList = new ArrayList<SecureSubject>();
	}

	private static void executeFileCommands(String path) {
		File file = new File(path);	
		try{
			Scanner reader = new Scanner(file);

			while(reader.hasNextLine()){

				String nextLine = reader.nextLine();
				InstructionObject obj = checkStringInput(nextLine);		

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

				printState(obj);
			}
		} catch(FileNotFoundException e){
			System.err.println("The file " + path + " could not be found.");	
		}
	}

	public void createNewSubject(String name, SecurityLevel level){

		subjectList.add(new SecureSubject(name));
		getReferenceMonitor().createNewSubject(name, level);
	}

	public ReferenceMonitor getReferenceMonitor(){
		return monitor;
	}

	private static InstructionObject checkStringInput(String input) {

		if(input.length() == 0)
			return InstructionObject.BAD;

		//split the string into words with whitespace as the delimiter.
		String[] split = input.split("\\s+");

		//checking if the first word was "read"
		if(split[0].equalsIgnoreCase(InstructionObject.READ.name())) {
			//correct number of args
			if(split.length == 3){
				InstructionObject read = InstructionObject.READ;
				read.setSubjectName(split[1]);
				read.setObjectName(split[2]);
				return read;
			}
		}

		//checking if the first word was "write"
		else if(split[0].equalsIgnoreCase(InstructionObject.WRITE.name())) {	
			//correct number of args and the last argument is a number
			if(split.length == 4 && isNumber(split[3])){
				InstructionObject write = InstructionObject.WRITE;
				write.setSubjectName(split[1]);
				write.setObjectName(split[2]);
				write.setValue(Integer.parseInt(split[3]));
				return write;
			}
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

	public static void printState(InstructionObject instruction){

		String output = "";
		if(instruction.equals(InstructionObject.BAD))
			output += "Bad Instruction\n";

		else if(instruction.equals(InstructionObject.WRITE))
			output += instruction.getSubjectName().toLowerCase() + " writes value " + instruction.getValue() + " to " + instruction.getObjectName().toLowerCase() + "\n"; 

		else if(instruction.equals(InstructionObject.READ))
			output += instruction.getSubjectName().toLowerCase() + " reads " + instruction.getObjectName().toLowerCase() + "\n";

		output += "The current state is: \n";

		for(SecureObject o: monitor.getObjectManager().getObjectList())
			output += "   " + o.getName() + " has value: " + o.getValue() + "\n";

		for(SecureSubject s: subjectList)
			output += "   " + s.getName() + " has recently read: " + s.getTEMP() + "\n";

		System.out.println(output);
	}
}
