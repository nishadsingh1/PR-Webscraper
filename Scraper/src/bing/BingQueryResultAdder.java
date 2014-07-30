package bing;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import universal.Company;
import universal.Entry;
import universal.InputList;
import universal.QueryResultAdder;
import functions.DateManager;
@SuppressWarnings("deprecation")
public class BingQueryResultAdder implements QueryResultAdder {
	
	private static final int NUM_RESULTS_PER_QUERY = 14;
	private static final int NUM_QUERIES = 1;
	private static final String BING_API_URL = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/News?Query=";

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
		String url = BING_API_URL + modifiedSearch + "&$top=15&$format=Json";
		return url;
		
	}
	
	@SuppressWarnings("resource")
	private String bingResults(String url) throws UnsupportedEncodingException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String accountKey = ":1K2Nqn+/MpMy9WlqzA3A/pqHyKbJXXZacCCXo5INimY";
        byte[] accountKeyBytes = Base64.encodeBase64(accountKey.getBytes());
        String accountKeyEnc = new String(accountKeyBytes);
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Authorization", "Basic " + accountKeyEnc);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        return  httpclient.execute(httpget, responseHandler);
	}
	
	
	
	@Override
	public String getName() {
		return "Bing";
	}
	
	public static void main(String[] args){
		StringEscapeUtils u = new StringEscapeUtils();
		String d = "Security firm <b>Zscaler</b> finds 28 % of Android apps request permission to read SMS <b>...</b>";
		String s = d.replaceAll("%", "percent");
		System.out.println(s);
		System.out.println(URLDecoder.decode(s));
	}


	@Override
	public void addQueryResultsToEntryList(List<Entry> entryList, InputList il,
			Company company) throws IOException {
		// TODO Auto-generated method stub
		
	}



}
