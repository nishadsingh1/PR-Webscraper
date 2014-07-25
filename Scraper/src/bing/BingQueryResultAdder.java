package bing;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import universal.Entry;
import universal.EntryListToExcelWriter;
import universal.InputList;
import universal.QueryResultAdder;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.google.gson.Gson;

import functions.DateManager;
import functions.StringFormatter;

public class BingQueryResultAdder implements QueryResultAdder {
	
	private final int NUM_RESULTS_PER_QUERY = 14;
	private final int NUM_QUERIES = 1;
	private final String charset = "UTF-8";
	private final String bingAPIUrl = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/News?Query=";

	public void addQueryResultsToEntryList(List<Entry> entryList, InputList il,
			String searchTerm) throws IOException {
		
		Entry entry;
		BingResultsManager brm;
		
		for (int x = 0; x < NUM_QUERIES; x++) {
			String url = getUrl(searchTerm);
			String results = bingResults(url);
			brm = new BingResultsManager(results);
			ArrayList<String> urls = brm.getAllUrls(results, NUM_RESULTS_PER_QUERY);
			ArrayList<String> titles = brm.getAllTitles(results, NUM_RESULTS_PER_QUERY);
			ArrayList<String> publishers = brm.getAllPublishers(results, NUM_RESULTS_PER_QUERY);
			ArrayList<String> dates = brm.getAllDates(results, NUM_RESULTS_PER_QUERY);
			
			for(int i=0; i<dates.size(); i++){
				try{
					Date articleDate = DateManager.setArticleDate(dates.get(i),getName());
					if(DateManager.checkWithinDateRange(articleDate, il.getStartDate(), il.getEndDate())){
						entry = new Entry();
						//set url
						entry.setUrl(urls.get(i));
						//set title, probs issue here
						entry.setTitle(titles.get(i));
						//set publisher
						entry.setPublisher(publishers.get(i));
						//set date
						entry.setDate(articleDate);
						//set company
						entry.setCompany(searchTerm);
						//set engine
						entry.setEngine(brm.getEngine());
						//add to list
						entryList.add(entry);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	private String getUrl(String search){
		String modifiedSearch = "%27" + search + "%27";
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(modifiedSearch);
		boolean found = matcher.find();
		if(found){
			modifiedSearch = modifiedSearch.replace(" ", "%27");
		}
		String url = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/News?Query=" + modifiedSearch + "&$top=15&$format=Json";
		return url;
		
	}
	
	private String bingResults(String url) throws UnsupportedEncodingException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String accountKey = ":1K2Nqn+/MpMy9WlqzA3A/pqHyKbJXXZacCCXo5INimY";
        byte[] accountKeyBytes = Base64.encodeBase64(accountKey.getBytes());
        String accountKeyEnc = new String(accountKeyBytes);
        String responseBody = null;
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Authorization", "Basic " + accountKeyEnc);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        return responseBody = httpclient.execute(httpget, responseHandler);
	}
	
	
	
	@Override
	public String getName() {
		return "Bing";
	}



}
