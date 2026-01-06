package fossid.report.getdata;

import static fossid.report.values.AllValues.allValues;

import java.util.Collections;
import java.util.StringTokenizer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class setTotalSumList {
	
	private final Logger logger = LogManager.getLogger(getBillofMaterials.class);
	
	public void settotalsumList() {
		
    	String componentName = ""; 
		String componentVersion = "";
		String licenseName = "";
		String licenseId = "";
		String componentLicensecheck = "";
		
		getComponentInformation getComonentInfo = new getComponentInformation();
		Collections.sort(allValues.totalresultjsonValues.gettotalresultJosn(), String.CASE_INSENSITIVE_ORDER);
		
		
    	 
    	for(int j = 0; j < allValues.totalresultjsonValues.gettotalresultJosn().size(); j++) {            		            		
    		StringTokenizer st = new StringTokenizer(allValues.totalresultjsonValues.gettotalresultJosn().get(j).toString(), "|");
    		
    		while(st.hasMoreTokens()) {
    			componentName = st.nextToken().toString(); 
    			componentVersion = st.nextToken().toString();
    			licenseName = st.nextToken().toString();
    			licenseId = st.nextToken().toString();
    		}
    		
    		componentLicensecheck = componentName+ licenseName;
    			
    		if(allValues.billOfMaterialsValues.getcomponentLicensecheck().contains(componentLicensecheck)) {    			
    		} else {
    			allValues.billOfMaterialsValues.setidComponentName(componentName);
        		//allValues.billOfMaterialsValues.setidComponentVersion(componentVersion);
            	allValues.billOfMaterialsValues.setidComponentLicenseName(licenseName);        	        	
            	allValues.billOfMaterialsValues.setidComponentLicenseId(licenseId);
            	
            	allValues.billOfMaterialsValues.setcomponentLicensecheck(componentLicensecheck);
            	
            	// To set component URL, Copyright
            	getComonentInfo.getComponentInfo(componentName, componentVersion, "SE");
    		}
    		
        	
        	if(allValues.ulicenseValues.getuComponentLicenseId().contains(licenseId)){            	
        	} else {
        		allValues.ulicenseValues.setuComponentLicenseId(licenseId);
        	}
    	}
    	
    	Collections.sort(allValues.ulicenseValues.getuComponentLicenseId(), String.CASE_INSENSITIVE_ORDER);
    	

	}

}
