package universal;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import excel.ExcelConstants;
import excel.ExcelDocWriter;


public class EntryListToExcelWriter {
	private static final int FIRST_ROW =0;
	private int latestRow;
	private ExcelDocWriter doc;
	private Hashtable<String, Entry> set;
	private int duplicates;
	
	public EntryListToExcelWriter(){
		latestRow = FIRST_ROW;
		duplicates = 0;
	}
	
	
	public void writeEntriesToDoc(List<Entry> entryList, InputList il) throws RowsExceededException, WriteException, IOException{
		System.out.println("Finding duplicates");
		initalizeWriter(il);
		initializeDoc();
		String url;
		Entry exists;
		for(Entry entry: entryList){
			url = entry.getUrl();
			if(!set.containsKey(url)){
				set.put(url, entry);
			} else{
				exists = set.get(url);
				exists.merge(entry);
				duplicates++;
			}
		}
		for(Entry entry: set.values()){
			writeEntryToDoc(entry);
		}
		doc.writeAndClose();
		System.out.println(duplicates+" duplicates found");
	}
	
	private void initalizeWriter(InputList il) throws IOException{
		set = new Hashtable<String,Entry>();
		doc = new ExcelDocWriter(il.getFile(), il.getSheetName());
	}
	
	private void writeEntryToDoc(Entry entry) throws MalformedURLException, RowsExceededException, WriteException{
		doc.addLabel(entry.getAllEngines(), ExcelConstants.ENGINE_COLUMN, latestRow);
		doc.addLabel(entry.getAllCompanies(), ExcelConstants.COMPANY_COLUMN, latestRow);
		doc.addDate(entry.getDate(), ExcelConstants.DATE_COLUMN, latestRow);
		doc.addLabel(entry.getPublisher(), ExcelConstants.PUBLISHER_COLUMN, latestRow);
		doc.addLabel(entry.getAllKeywords(), ExcelConstants.KEYWORD_COLUMN, latestRow);
		doc.addHyperlinkOneCell(entry.getUrl(), entry.getTitle(), ExcelConstants.URL_COLUMN, latestRow);
		doc.addInt(entry.getOccurences(),ExcelConstants.OCCURENCES_COLUMN, latestRow);
		latestRow++;
	}
	
	private void initializeDoc(){
		try {
			doc.addLabel("Search Engine", ExcelConstants.ENGINE_COLUMN, latestRow);
			doc.addLabel("Company", ExcelConstants.COMPANY_COLUMN, latestRow);
			doc.addLabel("Date", ExcelConstants.DATE_COLUMN, latestRow);
			doc.addLabel("Publisher", ExcelConstants.PUBLISHER_COLUMN, latestRow);
			doc.addLabel("Headline", ExcelConstants.URL_COLUMN, latestRow);
			doc.addLabel("Keyword", ExcelConstants.KEYWORD_COLUMN, latestRow);
			doc.addLabel("Author", ExcelConstants.AUTHOR_COLUMN, latestRow);
			doc.addLabel("Occurences", ExcelConstants.OCCURENCES_COLUMN, latestRow);
			latestRow++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
