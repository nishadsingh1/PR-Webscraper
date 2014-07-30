package google;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import functions.DateManager;
import functions.StringFormatter;


public class GoogleResultsManager {
	GoogleResults results;
	String engine;
	
	public GoogleResultsManager(GoogleResults results){
		this.results = results;
	}
	
	public String getPublisher(GoogleResults results, int i) {
		return results.getResponseData()
				.getResults().get(i).getPublisher();
	}


	public String getLink(GoogleResults results, int i)
			throws UnsupportedEncodingException {
		return StringFormatter.processString(results
				.getResponseData().getResults().get(i).getUrl());
	}


	public String getTitle(GoogleResults results, int i) throws UnsupportedEncodingException {
		String title = results.getResponseData().getResults()
				.get(i).getTitle();
		return StringFormatter.processString(title);
	}


	public Date getArticleDate(GoogleResults results, int i) {
		String date = results.getResponseData().getResults().get(i)
				.getDate();
		Date articleDate = DateManager.setArticleDate(date, "Google");
		return articleDate;
	}

	public String getEngine() {
		if(engine==null){
			engine="Google";
		}
		return engine;
	}


}
