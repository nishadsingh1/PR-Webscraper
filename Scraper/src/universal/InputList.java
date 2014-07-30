package universal;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InputList {
	File file;
	String sheetName;
	List<Company> companyList;
	Date start;
	Date end;

	

	public void retrieveInputs() throws IOException {
		Scanner keyboard = new Scanner(System.in);
		Prompter p = new Prompter(keyboard);
		file = p.getFile("Enter the location you would like your Excel document to be saved at:\nEx: /Users/Sam/Documents/filename.xls");
		start = p.getDate("Enter start date (dd/MM/YYYY):");
		end = p.getDate("Enter end date (dd/MM/YYYY):");
		sheetName = p.getString("Enter your Excel Document's sheet name:");
		List<String> companyNames = p.getStringList("Enter company names separated by commas:\nEx: Skyhigh Networks, CipherCloud");
		List<String> keywordList = null;
		companyList = new ArrayList<Company>();
		for(String companyName: companyNames){
			 keywordList = p.getStringList("Enter the keywords you would like to search for with respect to "+companyName+". \n Leave blank if you are not looking for specific keywords.");
			 companyList.add(new Company(companyName, keywordList));
		}
		keyboard.close();
	}


	public List<Company> getCompanyList() {
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


	public File getFile() {
		return file;
	}
}
