package application;

import functions.StringFormatter;
import google.GoogleQueryResultAdder;

import java.util.ArrayList;
import java.util.List;

import bing.BingQueryResultAdder;
import universal.Company;
import universal.Entry;
import universal.EntryListToExcelWriter;
import universal.InputList;
import universal.QueryResultAdder;


//currently only supports Google
public class Application {

	public static void main(String[] args) throws Exception {
		
		 // construct QueryResultAdders for each Search Engine
		QueryResultAdder googleAdder = new GoogleQueryResultAdder();
		QueryResultAdder bingAdder = new BingQueryResultAdder();
			
		List<QueryResultAdder> resultAdders = new ArrayList<QueryResultAdder>();
		resultAdders.add(googleAdder);
		resultAdders.add(bingAdder);

		InputList il = new InputList();
		il.retrieveInputs();
		List<Entry> entryList = new ArrayList<Entry>();
		
		for(QueryResultAdder qra: resultAdders){
			for (Company company : il.getCompanyList()) {
				System.out.println(StringFormatter.makeRunTimeMessage("Beginning "+qra.getName()+" search for: "+company.getName()));
				qra.addQueryResultsToEntryList(entryList, il, company);
				System.out.println(StringFormatter.makeRunTimeMessage("Finished "+qra.getName()+" search for: "+company.getName()));
			}
		}
		
		
		EntryListToExcelWriter writer = new EntryListToExcelWriter();
		writer.writeEntriesToDoc(entryList, il);
		
		System.out.println(StringFormatter.makeRunTimeMessage("Search complete, results saved at "+il.getFile().getAbsolutePath()));
	}
}
