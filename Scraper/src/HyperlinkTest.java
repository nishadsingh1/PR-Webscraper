
import java.io.File;
import java.io.IOException;
import java.net.URL;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class HyperlinkTest {

  /**
   * @param args
   * @throws IOException 
   * @throws IOException 
   * @throws WriteException 
   * @throws BiffException 
   */
  public static void main(String[] args) throws IOException, WriteException {
    //Creates a writable workbook with the given file name
    WritableWorkbook workbook = Workbook.createWorkbook(new File("/Users/Nishad/Hyperlink.xls"));

    WritableSheet sheet = workbook.createSheet("My Sheet", 0);
    
      //Add hyper link
      URL  url = new URL("http://www.skyhighnetworks.com/");
      
      //Activate cell A2
      WritableHyperlink wh = new WritableHyperlink(0, 1, 0, 1, url);
      wh.setDescription("should be name");
      sheet.addHyperlink(wh);
    
      //Activate cells A4, B4
      WritableHyperlink wh2 = new WritableHyperlink(0, 3, 1, 3, url);
      sheet.addHyperlink(wh2);
      
      //Activate cells A6, B6, C6
      WritableHyperlink wh3 = new WritableHyperlink(0, 5, 2, 5, url);
      sheet.addHyperlink(wh3);
      
    //Writes out the data held in this workbook in Excel format
    workbook.write(); 

    //Close and free allocated memory 
    workbook.close(); 
  }

}