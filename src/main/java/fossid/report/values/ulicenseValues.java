package fossid.report.values;

import java.util.ArrayList;

public class ulicenseValues {
	
	private static final ulicenseValues values = new ulicenseValues();

	private ulicenseValues() {
	}
	
	public static ulicenseValues getInstance() {
		return values;
	}
	
	private final ArrayList<String> uComponentLicenseId = new ArrayList<>();
	private final ArrayList<String> xlicenseText = new ArrayList<>();
	
	private String uComponentLicenseName;
	private String uComponentLicenseText;

	
	public ArrayList<String> getuComponentLicenseId() {
		return uComponentLicenseId;
	}
	public void setuComponentLicenseId(String ucomponentLicenseId) {
		this.uComponentLicenseId.add(ucomponentLicenseId);
	}
	
	public String getuComponentLicenseName() {
		return uComponentLicenseName;
	}
	public void setuComponentLicenseName(String uComponentLicenseName) {
		this.uComponentLicenseName = uComponentLicenseName;
	}

	public String getuComponentLicenseText() {
		return uComponentLicenseText;
	}
	public void setuComponentLicenseText(String uComponentLicenseText) {
		this.uComponentLicenseText = uComponentLicenseText;
	}
	
	
	public ArrayList<String> getXlicenseText() {
		return xlicenseText;
	}
	public void setXlicenseText(String xlicenseText) {
		this.xlicenseText.add(xlicenseText);
	}
}
