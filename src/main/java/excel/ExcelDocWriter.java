package excel;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import jxl.Workbook;
import jxl.write.Number;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelDocWriter {
	private File file;
	private String sheetName;
	private WritableWorkbook workbook;
	private WritableSheet sheet;
	

	public ExcelDocWriter(File file, String sheetName) throws IOException{
		this.file = file;
		this.sheetName = sheetName;
		createDocument();
	}
	
	public ExcelDocWriter(File file) throws IOException{
		this.file = file;
		sheetName = "My Sheet";
		createDocument();
	}
	
	
	private void createDocument() throws IOException{
		this.workbook =  Workbook.createWorkbook(file);
//	    this.sheet = workbook.createSheet(sheetName, 0);
		sheet = workbook.createSheet(sheetName, 0);
	}
	

	
	private void addHyperlink(URL url, String name, int col, int row, int lastcol, int lastrow) throws RowsExceededException, WriteException{
		WritableHyperlink wh = new WritableHyperlink(col, row, lastcol, lastrow, url);
		wh.setDescription(name);
	    sheet.addHyperlink(wh);
	}
	
	private void addHyperlink(String url, String name,  int col, int row, int lastcol, int lastrow) throws MalformedURLException, RowsExceededException, WriteException{
		addHyperlink(new URL(url), name,  col, row, lastcol, lastrow);
	}
	
	public void addHyperlinkOneCell(String url, String name, int col, int row) throws RowsExceededException, MalformedURLException, WriteException{
		addHyperlink(url,name,col,row,col,row);
	}
	
	public void addLabel(String content, int column, int row) throws RowsExceededException, WriteException{
		Label l = new Label(column,row,content);
		sheet.addCell(l);
	}
	public void addInt(int content, int column, int row) throws RowsExceededException, WriteException{
		Number l = new Number(column,row,content);
		sheet.addCell(l);
	}
	public void addDate(Date d, int column, int row) throws RowsExceededException, WriteException {
		jxl.write.DateTime date = new jxl.write.DateTime(column,row,d);
		sheet.addCell(date);
	}


	public void writeAndClose() throws IOException, WriteException{
	    workbook.write(); 
	    workbook.close(); 
	}
	
	
	
}
