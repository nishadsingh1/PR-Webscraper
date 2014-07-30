package functions;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class StringFormatter {
	private static String runTimeMessageBorder = "-------------";
	
	public static String processString(String s)
			throws UnsupportedEncodingException {
		String string = URLDecoder.decode(s, "UTF-8");
		string = string.replace("<b>", "");
		string = string.replace("</b>", "");
		string = string.replace("&amp;", "");
		return string;
	}
	
	public static String addParens(String s){
		StringBuilder sb = new StringBuilder();
		sb.append('"');
		sb.append(s);
		sb.append('"');
		return sb.toString();
	}

	public static List<String> fromStringToList(String rawCompanyList) {
		List<String> list = new ArrayList<String>();
		for(String companyName: rawCompanyList.split(",")){
			list.add(pruneSpaces(companyName));
		}
		return list;
	}
	
	private static String pruneSpaces(String s){
		if(s.startsWith(" ")){
			s = s.substring(1);
		}
		if(s.endsWith(" ")){
			s = s.substring(0, s.length()-1);
		}
		return s;
	}
	
	public static String makeRunTimeMessage(String s){
		StringBuilder sb = new StringBuilder();
		sb.append(runTimeMessageBorder);
		sb.append(s);
		sb.append(runTimeMessageBorder);
		return sb.toString();
	}

}
