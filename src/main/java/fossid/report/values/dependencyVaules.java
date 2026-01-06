package fossid.report.values;

import java.io.Serializable;

public class dependencyVaules implements Serializable {


	private static final dependencyVaules values = new dependencyVaules();

	private dependencyVaules() {
	}

	public static dependencyVaules getInstance() {
		return values;
	}
	private String licenseName;
	private String copyright;
	private String url;
	private String component;
	private String version;


	
	public String getdelicenseName() {
		return licenseName;
	}
	public void setdelicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public String getdeUrl() {
		return url;
	}
	public void setdeUrl(String url) {
		this.url = url;
	}
	
	public String getdeCopyright() {
		return copyright;
	}
	public void setdeCopyright(String copyright) {
		this.copyright = copyright;
	}
	
	public String getdeComponent() {
		return component;
	}
	public void setdeComponent(String component) {
		this.component = component;
	}
	
	public String getdeVersion() {
		return version;
	}
	public void setdeVersion(String version) {
		this.version = version;
	}

}
