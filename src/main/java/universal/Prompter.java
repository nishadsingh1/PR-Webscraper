package universal;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import functions.DateManager;
import functions.StringFormatter;

public class Prompter {
	Scanner s;
	private static final String prompter = ">>> ";
	
	public Prompter(Scanner s){
		this.s=s;
		startup();
	}
	
	public void startup(){
		System.out.println("This application will execute Google and Bing news searches for specified companies between a date range. \nIt will save information about these searches in an Excel doc.");
	}
	
	public String prompt(String prompt) {
		
		System.out.println(prompt);
		System.out.print(prompter);
		String returnvalue = s.nextLine();
		
		return returnvalue;
	}

	public Date getDate(String message) {
		String response = prompt(message);
		try {
			return DateManager.getDateFromString(response);
		} catch (ParseException e) {
			System.out.println("Invalid date format");
			return getDate(message);
		}
	}
	
	public String getString(String message){
		return prompt(message);
	}
	
	public List<String> getStringList(String message){
		return StringFormatter.fromStringToList(getString(message));
	}

	public File getFile(String message) {
		String response = prompt(message);
		File file = new File(response);
		
		String parentPathName = file.getParent();
		if(parentPathName==null|| !(new File(parentPathName).exists())){
			System.out.println("That parent directory does not exist");
			return getFile(message);
		}
		if(!isValidFile(file.getName())){
			System.out.println("That is not a valid file name! Make sure you use the .xls extension");
			return getFile(message);
		}
		if(file.exists()){
			String answer = prompt("You will be overwriting an existing file. Is this okay?");
			String[] list = {"yes","y","yeah"};
			for (String str: list){
				if(answer.toLowerCase().contains(str)){
					break;
				} else {
					return getFile(message);
				}
			}
		}
		return new File(response);
	}

	private boolean isValidFile(String name) {
		if(".xls".equals(name.substring(name.length()-4,name.length()))){
			return true;
		}
		return false;
	}

}
