package universal;
import java.util.Date;

import functions.DateManager;


public class Entry {
	String company;
	Date date;
	String publisher;
	String url;
	String engine;
	String title;
	
	public void setUrl(String url){
		this.url = url;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public void setPublisher(String source){
		this.publisher = source;
	}
	public void setDate(Date date){
		this.date = date;
	}
	public void setEngine(String engine){
		this.engine = engine;
	}
	public void setCompany(String company){
		this.company = company;
	}
	
	public String getUrl(){
		return url;
	}
	public String getTitle(){
		return title;
	}
	public String getPublisher(){
		return publisher;
	}
	public String getDate(){
		return DateManager.formatFromDate(date);
	}
	public String getEngine(){
		return engine;
	}
	public String getCompany(){
		return company;
	}
	
	
}
