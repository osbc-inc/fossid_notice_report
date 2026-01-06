package fossid.report.values;

import java.io.Serializable;

public class loginValues implements Serializable {

	private static final loginValues values = new loginValues();

	public loginValues() {
	}

	public static loginValues getInstance() {
		return values;
	}

	private String serverUri;
	private static String serverApiUri;
	private static String username;
	private static String apikey;	
	private static String header;	

	public String getSetHeader() {
		return header;
	}

	public void setSetHeader(String header) {
		this.header = header;
	}

	
	public String getServerUri() {
		return serverUri;
	}

	public void setServerUri(String serverUri) {
		this.serverUri = serverUri;
	}

	public String getServerApiUri() {
		return serverApiUri;
	}

	public void setServerApiUri(String serverApiUri) {
		loginValues.serverApiUri = serverApiUri;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		loginValues.username = username;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		loginValues.apikey = apikey;
	}
	
}
