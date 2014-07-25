package functions;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class DateManager {
	public static String formatFromDate(Date d){
		return d.toString();
	}
	
	public static Date setArticleDate(String dateString, String engine) {
		Calendar mydate = new GregorianCalendar();
		Date thedate = null;
		try {
			if(engine.equals("Google")){
				thedate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z",
						Locale.ENGLISH).parse(dateString);
			}
			//2014-06-23T15:30:02Z
			if(engine.equals("Bing")){
				thedate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
						Locale.ENGLISH).parse(dateString);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		mydate.setTime(thedate);
		return mydate.getTime();
	}
	
	public static boolean checkWithinDateRange(Date articleDate, Date start,
			Date end) {
		if (articleDate.compareTo(start) >= 0
				&& articleDate.compareTo(end) <= 0) {
			return true;
		}
		return false;
	}
	

	public static Date getDateFromString(String startDate) {
		Calendar mydate = new GregorianCalendar();
		Date start = null;
		DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			start = sourceFormat.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mydate.setTime(start);
		return mydate.getTime();
	}
}
