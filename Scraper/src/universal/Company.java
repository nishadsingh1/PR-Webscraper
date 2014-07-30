package universal;

import java.util.List;

public class Company {
	private String name;
	private List<String> keywords;
	
	public Company(String name, List<String> keywords){
		this.name = name;
		this.keywords = keywords;
	}
	
	public Company(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	
	
	
	
}
