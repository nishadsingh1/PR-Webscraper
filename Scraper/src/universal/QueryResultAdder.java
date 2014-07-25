package universal;
import java.io.IOException;
import java.util.List;


public interface QueryResultAdder {
	public void addQueryResultsToEntryList(List<Entry> entryList, InputList il,String search) throws IOException;

	public String getName();
}
