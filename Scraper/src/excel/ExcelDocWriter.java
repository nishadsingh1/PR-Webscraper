package excel;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelDocWriter {
	private String filePath;
	private String sheetName;
	private WritableWorkbook workbook;
	private WritableSheet sheet;
	private final int FIRST_ROW = 0;
	private int nextRow;
	

	public ExcelDocWriter(String filePath, String sheetName) throws IOException{
		this.filePath = filePath;
		this.sheetName = sheetName;
		createDocument();
	}
	
	public ExcelDocWriter(String filePath) throws IOException{
		this.filePath = filePath;
		sheetName = "My Sheet";
		createDocument();
	}
	
	
	private void createDocument() throws IOException{
		this.workbook =  Workbook.createWorkbook(new File(filePath));
//	    this.sheet = workbook.createSheet(sheetName, 0);
		sheet = workbook.createSheet(sheetName, 0);
		nextRow = FIRST_ROW;
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

	
	private int getAndIncrementNextRow() {
		return nextRow++;
	}

	public void writeAndClose() throws IOException, WriteException{
	    workbook.write(); 
	    workbook.close(); 
	}
	
	
	
}
