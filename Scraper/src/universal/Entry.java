package universal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import functions.DateManager;

public class Entry {
	String company;
	Date date;
	String publisher;
	String url;
	String engine;
	String title;
	String keyword;
	int numOccurences;
	List<String> additionalKeywordList;
	List<String> additionalEngineList;
	List<String> additionalCompanyList;
	
	public Entry(){
		numOccurences=1;
	}
	
	public void merge(Entry other){
		String otherEngine = other.getEngine();
		String otherKeyword = other.getKeyword();
		String otherCompany = other.getCompany();
	
		incrementOccurences();
		if(!otherEngine.equals(getEngine()) && !getEngineList().contains(otherEngine)){
			
			addEngine(otherEngine);
		}
		if(!otherKeyword.equals(getKeyword()) && !getKeywordList().contains(otherKeyword)){
			addKeyword(otherKeyword);
		}
		if(!otherCompany.equals(getCompany()) && !getCompanyList().contains(otherCompany)){
			addCompany(otherCompany);
		}
	}
	
	public void incrementOccurences(){
		numOccurences++;
	}
	
	public int getOccurences(){
		return numOccurences;
	}
	
	private List<String> getEngineList(){
		if (additionalEngineList==null){
			additionalEngineList = new ArrayList<String>();
		}
		return additionalEngineList;
	}
	
	private List<String> getCompanyList(){
		if (additionalCompanyList==null){
			additionalCompanyList = new ArrayList<String>();
		}
		return additionalCompanyList;
	}
	
	
	private List<String> getKeywordList(){
		if (additionalKeywordList==null){
			additionalKeywordList = new ArrayList<String>();
		}
		return additionalKeywordList;
	}
	
	
	private void addEngine(String engine){
		getEngineList().add(engine);
	}
	private void addKeyword(String keyword){
		getKeywordList().add(keyword);
	}
	private void addCompany(String company){
		getCompanyList().add(company);
		
	}
	
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
	public void setKeyword(String keyword){
		this.keyword = keyword;
	}
	public String getKeyword(){
		return keyword;
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
	public Date getDate(){
		return date;
	}
	public String getEngine(){
		return engine;
	}
	public String getCompany(){
		return company;
	}
	public String getAllKeywords(){
		return keywordToString(additionalKeywordList, keyword);
	}
	public String getAllCompanies(){
		return listToString(additionalCompanyList, company);
	}
	public String getAllEngines(){
		return listToString(additionalEngineList, engine);
	}
	public String listToString(List<String> list, String str){
		StringBuilder sb = new StringBuilder();
		if(list!=null){
			for(String term: list){
				sb.append(term);
				sb.append(" + ");
			}
		}
		sb.append(str);
		return sb.toString();
	}
	public String keywordToString(List<String> list, String str){
		StringBuilder sb = new StringBuilder();

		if(list!=null){
			for(String term: list){
				sb.append(term);
				sb.append(" + ");
			}
		} else {
			return "No keyword matches";
		}
		
		if(str.equals("")){
			sb.delete(sb.length()-3, sb.length());
		} else{
			sb.append(str);
		}
		return sb.toString();
	}
	
	
	
}
