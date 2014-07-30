package google;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import universal.Company;
import universal.Entry;
import universal.InputList;
import universal.QueryResultAdder;

import com.google.gson.Gson;

import functions.DateManager;
import functions.StringFormatter;

public class GoogleQueryResultAdder implements QueryResultAdder {
	private static final int NUM_RESULTS_PER_QUERY = 4;
	private static final int NUM_ARTICLES = 6;
	private static final String GOOGLE_API_URL = "http://ajax.googleapis.com/ajax/services/search/news?v=1.0&start=%s&q=";
	private static final String CHARSET = "UTF-8";

	public void addQueryResultsToEntryList(List<Entry> entryList, InputList il,
			Company company) throws IOException {
		String companyName = company.getName();
		makeSearch(entryList, StringFormatter.addParens(companyName), il, companyName, "");
		for(String keyword: company.getKeywords()){
			makeSearch(entryList, StringFormatter.addParens(companyName)+"+"+StringFormatter.addParens(keyword), il, companyName, keyword);
		}
	}

	public void makeSearch(List<Entry> entryList, String searchTerm, InputList il, String companyName, String keyword)
			throws MalformedURLException, UnsupportedEncodingException,
			IOException {
		Entry entry;
		GoogleResultsManager grm;
		for (int x = 0; x < NUM_ARTICLES; x++) {
			URL url = getUrl(searchTerm, CHARSET, x);
			GoogleResults results = getResults(CHARSET, url);
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
						entry.setCompany(companyName);
						entry.setKeyword(keyword);
						entry.setEngine(grm.getEngine());
						entryList.add(entry);
						System.out.println(entry.getUrl());
//					} else if (DateManager.checkBeforeRange(articleDate,il.getStartDate())){
//						return;
					}
				} catch (IndexOutOfBoundsException e) {
					return;
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
		String google = String.format(GOOGLE_API_URL,
				Integer.toString(NUM_RESULTS_PER_QUERY * x));
		URL url = new URL(google + URLEncoder.encode(search, charset));
		return url;
	}


	public String getName() {
		return "Google";
	}

}
