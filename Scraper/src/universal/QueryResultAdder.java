package universal;
import java.io.IOException;
import java.util.List;


public interface QueryResultAdder {
	
	public void addQueryResultsToEntryList(List<Entry> entryList, InputList il,Company company) throws IOException;

	public String getName();
}
