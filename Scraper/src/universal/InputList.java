package universal;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import functions.DateManager;
import functions.StringFormatter;

public class InputList {
	String location;
	String sheetName;
	List<String> companyList;
	Date start;
	Date end;
	private static final String prompter = ">>> ";

	public static String prompt(String prompt, Scanner s) {
		System.out.println(prompt);
		System.out.print(prompter);
		return s.nextLine();
	}

	public void retrieveInputs() throws IOException {
		Scanner keyboard = new Scanner(System.in);
		location = prompt("Enter the location you would like your Excel document to be saved at:\nEx: /Users/Sam/Documents/filename.xls", keyboard);
		start = DateManager.getDateFromString(prompt("Enter start date (dd/MM/YYYY):",keyboard));
		end = DateManager.getDateFromString(prompt("Enter end date (dd/MM/YYYY):",keyboard));
		sheetName = prompt("Enter your Excel Document's sheet name:\nEx: My Sheet", keyboard);
		String rawCompanyList = prompt("Enter company names separated by commas:\nEx: Skyhigh Networks, CipherCloud", keyboard);
//		location = "/Users/nishad/testdoc.xls";
//		start = DateManager.getDateFromString("01/01/2014");
//		end = DateManager.getDateFromString("23/07/2014");
//		sheetName = "Sheet 1";
//		String rawCompanyList = "Skyhigh Networks, CipherCloud, Netskope";
		keyboard.close();
		companyList =  StringFormatter.fromArrayToList(rawCompanyList);
	}


	public List<String> getCompanyList() {
		return companyList;
	}

	public Date getStartDate() {
		return start;
	}

	public Date getEndDate() {
		return end;
	}
	
	public String getSheetName(){
		return sheetName;
	}
	
	public String getFilepath(){
		return location;
	}
}
