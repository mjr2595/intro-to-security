import java.util.*;
import java.io.*;


public class User {

	private String username;
	private String firstName;
	private String lastName;
	private String hash;

	public User(String line) {
		int i = 0;
		while (line.charAt(i) != ':') {
			i++;
		}
		this.username = line.substring(0, i);
		i++;
		int temp = i;
		while (line.charAt(i) != ':') {
			i++;
		}
		this.hash = line.substring(temp, i);
		// skip this junk -> "519:519"
		i += 9;
		temp = i;
		while (line.charAt(i) != ':') {
			i++;
		}
		String fullName = line.substring(temp, i);
		String[] split = fullName.split("\\s+");
		this.firstName = split[0];
		this.lastName = split[1];
	}

	// public User(String username, String first, String last, String hash) {
	// 	this.username = username;
	// 	this.firstName = first;
	// 	this.lastName = last;
	// 	this.hash = hash;
	// }

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

	public String getSeed() {
		return this.hash.substring(0,2);
	}
}