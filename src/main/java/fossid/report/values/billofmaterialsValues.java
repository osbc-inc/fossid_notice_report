package fossid.report.values;

import java.io.Serializable;
import java.util.ArrayList;

public class billofmaterialsValues implements Serializable {

	private static final billofmaterialsValues values = new billofmaterialsValues();

	private billofmaterialsValues() {
	}

	public static billofmaterialsValues getInstance() {
		return values;
	}

    private final ArrayList<String> idComponentName = new ArrayList<>();
	private final ArrayList<String> idComponentVersion = new ArrayList<>();
	private final ArrayList<String> idComponentHomepage = new ArrayList<>();
	private final ArrayList<String> idComponentLicenseName = new ArrayList<>();
	private final ArrayList<String> idComponentLicenseId = new ArrayList<>();
	private final ArrayList<String> idComponentCopyright = new ArrayList<>();
	
	private final ArrayList<String> componentLicensecheck = new ArrayList<>();
	
	
	private String copyright;
	private String url;
	private String component;
	private String version;
	private String componentLicense;

	public ArrayList<String> getidComponentName() {
		return idComponentName;
	}
	public void setidComponentName(String componentName) {
		this.idComponentName.add(componentName);
	}
	
	public ArrayList<String> getidComponentVersion() {
		return idComponentVersion;
	}
	public void setidComponentVersion(String componentVersion) {
		this.idComponentVersion.add(componentVersion);
	}
	
	public ArrayList<String> getidComponentHomepage() {
		return idComponentHomepage;
	}
	public void setidComponentHomepage(String componentHomepage) {
		this.idComponentHomepage.add(componentHomepage);
	}
	
	public ArrayList<String> getidComponentLicenseName() {
		return idComponentLicenseName;
	}
	public void setidComponentLicenseName(String componentLicenseName) {
		this.idComponentLicenseName.add(componentLicenseName);
	}

	public ArrayList<String> getidComponentLicensId() {
		return idComponentLicenseId;
	}
	public void setidComponentLicenseId(String componentLicenseId) {
		this.idComponentLicenseId.add(componentLicenseId);
	}

	public ArrayList<String> getidComponentCopyright() {
		return idComponentCopyright;
	}
	public void setidComponentCopyright(String componentCopyright) {
		this.idComponentCopyright.add(componentCopyright);
	}
	
	
	public String getidCopyright() {
		return copyright;
	}
	public void setidCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getidUrl() {
		return url;
	}
	public void setidUrl(String url) {
		this.url = url;
	}
	
	public String getidComponent() {
		return component;
	}
	public void setidComponent(String component) {
		this.component = component;
	}
	
	public String getidVersion() {
		return version;
	}
	public void setidVersion(String version) {
		this.version = version;
	}
	
	public String getcomponentLicense() {
		return componentLicense;
	}
	public void setcomponentLicense(String componentLicense) {
		this.componentLicense = componentLicense;
	}
	
	public ArrayList<String> getcomponentLicensecheck() {
		return componentLicensecheck;
	}
	public void setcomponentLicensecheck(String componentLicensecheck) {
		this.componentLicensecheck.add(componentLicensecheck);
	}
}
