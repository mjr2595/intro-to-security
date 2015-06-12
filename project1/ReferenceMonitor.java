import java.util.ArrayList;
import java.util.TreeMap;


public class ReferenceMonitor {

	private ObjectManager manager;
	private static TreeMap<String,SecurityLevel> objectLevelMap, subjectLevelMap;

	public ReferenceMonitor () {

		manager = new ObjectManager();
		objectLevelMap = new TreeMap<String,SecurityLevel>(); 
		subjectLevelMap = new TreeMap<String,SecurityLevel>();		
	}

	public void createNewObject(String name, SecurityLevel level){

		manager.getObjectList().add(new SecureObject(name));
		objectLevelMap.put(name.toUpperCase(), level);
	}

	//this should only be called from the SecureSystem's instance of a ReferenceMonitor
	public void createNewSubject(String name, SecurityLevel level){
		
		subjectLevelMap.put(name.toUpperCase(), level);
	}

	public int executeRead(String subjectName, String objectName){
	
		SecurityLevel	subjectLevel = subjectLevelMap.get(subjectName.toUpperCase());
		SecurityLevel	objectLevel = objectLevelMap.get(objectName.toUpperCase());
		
		if(subjectLevel == null || objectLevel == null)
			return 0;
		
		if(subjectLevel.dominates(objectLevel))
			return manager.read(objectName);

		return 0;
	}

	public void executeWrite(String subjectName, String objectName, int value){
		
		SecurityLevel	subjectLevel = subjectLevelMap.get(subjectName.toUpperCase());
		SecurityLevel	objectLevel = objectLevelMap.get(objectName.toUpperCase());
		
		if(subjectLevel == null || objectLevel == null)
			return;
		
		if(objectLevel.dominates(subjectLevel))
			manager.write(objectName, value);
	}


	public ObjectManager getObjectManager(){
		return manager;
	}

	class ObjectManager {	

		private ArrayList<SecureObject> objectList;

		public ObjectManager() {

			objectList = new ArrayList<SecureObject>();
		}

		public void write(String name, int val) {

			for(int i=0; i<objectList.size(); i++){
			
				if(objectList.get(i).getName().equalsIgnoreCase(name))
					objectList.get(i).setValue(val);
			}
		}

		public int read(String name) {
			for(int i=0; i<objectList.size(); i++){
			
				if(objectList.get(i).getName().equalsIgnoreCase(name))
					return objectList.get(i).getValue();
			}

			return -1;
		}

		public ArrayList<SecureObject> getObjectList(){

			return objectList;
		}
	}
}
