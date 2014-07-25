package universal;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import excel.ExcelConstants;
import excel.ExcelDocWriter;


public class EntryListToExcelWriter {
	private static final int FIRST_ROW =0;
	private int latestRow;
	private ExcelDocWriter doc;
	private Set<String> set;
	
	public EntryListToExcelWriter(){
		latestRow = FIRST_ROW;
	}
	
	public void writeEntriesToDoc(List<Entry> entryList, InputList il) throws RowsExceededException, WriteException, IOException{
		initalizeWriter(il);
		initializeDoc();
		for(Entry entry: entryList){
			if(!set.contains(entry.getUrl())){
				writeEntryToDoc(entry);
				set.add(entry.getUrl());
			}
		}
		doc.writeAndClose();
	}
	
	private void initalizeWriter(InputList il) throws IOException{
		set = new HashSet<String>();
		doc = new ExcelDocWriter(il.getFilepath(), il.getSheetName());
	}
	
	private void writeEntryToDoc(Entry entry) throws MalformedURLException, RowsExceededException, WriteException{
		doc.addLabel(entry.getEngine(), ExcelConstants.ENGINE_COLUMN, latestRow);
		doc.addLabel(entry.getCompany(), ExcelConstants.COMPANY_COLUMN, latestRow);
		doc.addLabel(entry.getDate(), ExcelConstants.DATE_COLUMN, latestRow);
		doc.addLabel(entry.getPublisher(), ExcelConstants.PUBLISHER_COLUMN, latestRow);
		doc.addHyperlinkOneCell(entry.getUrl(), entry.getTitle(), ExcelConstants.URL_COLUMN, latestRow);
		latestRow++;
	}
	
	private void initializeDoc(){
		try {
			doc.addLabel("Search Engine", ExcelConstants.ENGINE_COLUMN, latestRow);
			doc.addLabel("Company", ExcelConstants.COMPANY_COLUMN, latestRow);
			doc.addLabel("Date", ExcelConstants.DATE_COLUMN, latestRow);
			doc.addLabel("Publisher", ExcelConstants.PUBLISHER_COLUMN, latestRow);
			doc.addLabel("Headline", ExcelConstants.URL_COLUMN, latestRow);
			doc.addLabel("Author", ExcelConstants.AUTHOR_COLUMN, latestRow);
			latestRow++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
