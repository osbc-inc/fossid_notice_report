package fossid.report.values;

import java.io.Serializable;
import java.util.ArrayList;

public class totalresultjsonValues implements Serializable{
	
	private static final totalresultjsonValues values = new totalresultjsonValues();
	private totalresultjsonValues() {
	}
	
	public static totalresultjsonValues getInstance() {
		return values;
	}
	
	private final ArrayList<String> totalresultJosn = new ArrayList<>();
	
	public ArrayList<String> gettotalresultJosn() {
		return totalresultJosn;
	}
	public void settotalresultJosn(String totalresultJosn) {
		this.totalresultJosn.add(totalresultJosn);
	}
	
}
