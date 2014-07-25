package bing;

import java.util.List;

public class BingResults {

	private ResponseData responseData;

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public String toString() {
		return "ResponseData[" + responseData + "]";
	}

	static class ResponseData {
		private List<Result> results;

		public List<Result> getResults() {
			return results;
		}

		public void setResults(List<Result> results) {
			this.results = results;
		}

		public String toString() {
			return "Results[" + results + "]";
		}
	}

	static class Result {
		private String url;
		private String title;
		private String publisher;
		private String publishedDate;

		public String getUrl() {
			return url;
		}

		public String getTitle() {
			return title;
		}

		public String getPublisher() {
			return publisher;
		}

		public String getDate() {
			return publishedDate;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String toString() {
			return "Result[url:" + url + ",title:" + title + "]";
		}
	}

}