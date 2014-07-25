package google;
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

import universal.Entry;
import universal.EntryListToExcelWriter;
import universal.InputList;
import universal.QueryResultAdder;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.google.gson.Gson;

import functions.DateManager;
import functions.StringFormatter;

public class GoogleQueryResultAdder implements QueryResultAdder {

	private final int NUM_RESULTS_PER_QUERY = 4;
	private final int NUM_ARTICLES = 6;
	private final String charset = "UTF-8";
	private final String googleApiUrl = "http://ajax.googleapis.com/ajax/services/search/news?v=1.0&start=%s&q=";

	public void addQueryResultsToEntryList(List<Entry> entryList, InputList il,
			String searchTerm) throws IOException {

		Entry entry;
		GoogleResultsManager grm;

		for (int x = 0; x < NUM_ARTICLES; x++) {
			URL url = getUrl(StringFormatter.addParens(searchTerm), charset, x);
			GoogleResults results = getResults(charset, url);
			grm = new GoogleResultsManager(results);
			for (int i = 0; i < NUM_RESULTS_PER_QUERY; i++) {
				try {
					Date articleDate = grm.getArticleDate(results, i);
					if (DateManager.checkWithinDateRange(articleDate,
							il.getStartDate(), il.getEndDate())) {
						entry = new Entry();
						entry.setUrl(grm.getLink(results, i));
						entry.setTitle(grm.getTitle(results, i));
						entry.setPublisher(grm.getPublisher(results, i));
						entry.setDate(articleDate);
						entry.setCompany(searchTerm);
						entry.setEngine(grm.getEngine());
						entryList.add(entry);
						
						System.out.println(entry.getUrl());
					}
				} catch (Exception e) {
				}
			}
		}
	}

	private GoogleResults getResults(String charset, URL url)
			throws UnsupportedEncodingException, IOException {
		Reader reader = new InputStreamReader(url.openStream(), charset);
		GoogleResults results = new Gson()
				.fromJson(reader, GoogleResults.class);
		return results;
	}

	private URL getUrl(String search, String charset, int x)
			throws MalformedURLException, UnsupportedEncodingException {
		String google;
		google = String.format(googleApiUrl, Integer.toString(NUM_RESULTS_PER_QUERY * x));
		URL url = new URL(google + URLEncoder.encode(search, charset));
		return url;
	}

	@Override
	public String getName() {
		return "Google";
	}

}
