import java.util.*;
import java.io.*;


public class User {

	private String username;
	private String firstName;
	private String lastName;
	private String hash;

	public User(String line) {
		String[] info = line.split(":");
		this.username = info[0];
		this.hash = info[1];
		String[] fullName = info[4].split("\\s+");
		this.firstName = fullName[0];
		this.lastName = fullName[1];
	}

	public String getUsername() {
		return this.username;
	}

	public String getHash() {
		return this.hash;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getSalt() {
		return this.hash.substring(0,2);
	}
}