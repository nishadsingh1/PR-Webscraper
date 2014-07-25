package bing;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BingResultsManager {
	String results;
	String engine;

	public BingResultsManager(String results) {
		this.results = results;
	}




	public ArrayList<String> getAllUrls(String results, int num) {
		 ArrayList<String> urls = new ArrayList<String>();
	        String mydata = results;
	        Pattern pattern = Pattern.compile("\"Url\":\"(.*?)\"");
	        Matcher matcher = pattern.matcher(mydata);
	        for(int x = 0; x<num; x++)
	        {
	      	  if(matcher.find())
	            {
	                urls.add(matcher.group(1));
	                System.out.println(urls.get(x));
	            }
	        }
	        return urls;
	}
	
	public ArrayList<String> getAllTitles(String results, int num){
        ArrayList<String> titles = new ArrayList<String>();
        String mydata = results;
        Pattern pattern = Pattern.compile("\"Title\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(mydata);
        //this loop will cause issues because regex doesn't really work like that
        //best i can do for now..
        for(int x = 0; x<num; x++)
        {
      	  if(matcher.find())
            {
                titles.add(matcher.group(1));
            }
        }
        return titles;
	}


	public static ArrayList<String> getAllPublishers(String results, int num) {
		 ArrayList<String> publishers = new ArrayList<String>();
	        String mydata = results;
	        Pattern pattern = Pattern.compile("\"Source\":\"(.*?)\"");
	        Matcher matcher = pattern.matcher(mydata);
	        //this loop will cause issues because regex doesn't really work like that
	        //best i can do for now..
	        for(int x = 0; x<num; x++)
	        {
	      	  if(matcher.find())
	            {
	                publishers.add(matcher.group(1));
	                //System.out.println(urls.get(x));
	            }
	        }
	        return publishers;
	}


	public ArrayList<String> getAllDates(String results, int num) {
		ArrayList<String> dates = new ArrayList<String>();
        String mydata = results;
        Pattern pattern = Pattern.compile("\"Date\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(mydata);
        //this loop will cause issues because regex doesn't really work like that
        //best i can do for now..
        for(int x = 0; x<num; x++)
        {
      	  if(matcher.find())
            {
                dates.add(matcher.group(1));
                //System.out.println(urls.get(x));
            }
        }
        return dates;
	}

	public String getEngine() {
		if(engine==null){
			engine="Bing";
		}
		return engine;
	}


}
