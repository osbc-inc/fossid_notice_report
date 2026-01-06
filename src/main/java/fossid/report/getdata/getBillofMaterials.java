package fossid.report.getdata;

import static fossid.report.values.AllValues.allValues;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class getBillofMaterials {
	private final Logger logger = LogManager.getLogger(getBillofMaterials.class);

	public void getBomInfo() {
		JSONObject dataObject = new JSONObject();
		dataObject.put("username", allValues.loginValues.getUsername());
		dataObject.put("key", allValues.loginValues.getApikey());
		dataObject.put("scan_code", allValues.projectValues.getVersionId());

		JSONObject rootObject = new JSONObject();
		rootObject.put("group", "scans");
		rootObject.put("action", "get_scan_identified_components");
		rootObject.put("data", dataObject);

		BufferedReader br = null;
		HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		
		getComponentInformation getComonentInfo = new getComponentInformation();
		
		System.out.println("=== Identification Results ====");
		System.out.println("Component Name / Component Version / License Name / License ID / URL / Copyright");
		
		try {
			StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);			
			
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception("Failed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
			}
			
			br = new BufferedReader(new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
			StringBuilder responseBuilder = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				responseBuilder.append(line);
			}
			String result = responseBuilder.toString();
			if (result == null || result.isEmpty()) {
				throw new Exception("Empty response body from server");
			}

	        JSONParser jsonParser = new JSONParser();
	        JSONObject resultJson = (JSONObject) jsonParser.parse(result);

			logger.debug("get_scan_identified_component resultJson: " + resultJson);

	        if(resultJson.toString().contains("You are not the creator of the scan nor part of the scan project. Please assign yourself as a project member.")) {
				throw new Exception("You are not the creator of the scan nor part of the scan project. Please assign yourself as a project member.");
	        }
	        
	        
	        if(resultJson.get("data") == null) {
				throw new Exception("Please, check \"" + allValues.projectValues.getVersionName() + "\" is mapped to \"" + allValues.projectValues.getProjectName() + "\"");
	        }	        
	        
	        
			if(resultJson.get("data").equals(false)) {
				logger.warn("This scan does not include identification information. You may check the [File license only] or [Empty identification] in 'marked as identified' tab");
			} else {
				JSONObject resultJsonData = (JSONObject) resultJson.get("data");

				Iterator<?> iter = resultJsonData.keySet().iterator();
				
				int i = 0;
				
				String totalSum = "";
				
				while(iter.hasNext()) {	
	            	// set key value to key
	            	String key = String.valueOf(iter.next());         
	            	// get values from key
	            	JSONObject tempObj = (JSONObject) resultJsonData.get(key);	            	
	            	i++;
	            	
	            	if(tempObj.get("name") == null) {
	            		logger.warn("ID: Component Name is null; skipping entry: " + key);
	            		continue;
	            	} else 	     
	            	{	     
	            		allValues.billOfMaterialsValues.setidComponent(tempObj.get("name").toString());
	            		totalSum = tempObj.get("name").toString();
	            	}
	            	
	            	if(tempObj.get("version") == null) {
	            		logger.warn("ID: Component Version is null; skipping entry: " + key);
	            		continue;
	            	} else 			
	            	{		
	            		allValues.billOfMaterialsValues.setidVersion(tempObj.get("version").toString());
	            		totalSum = totalSum + "|" + tempObj.get("version").toString();
	            	}
	            		            	
	            	if(tempObj.get("license_name") == null) {
	            		logger.warn("ID: License Name is null; skipping entry: " + key);
	            		continue;
	            	} else 	    		
	            	{	    		
	            		totalSum = totalSum + "|" + tempObj.get("license_name").toString();
	            	}
	            	
	               	if(tempObj.get("license_identifier") == null) {
	            		logger.warn("ID: License ID is null; skipping entry: " + key);
	            		continue;
	            	} else 	    		
	            	{	    		
	            		totalSum = totalSum + "|" + tempObj.get("license_identifier").toString();
	            	}	            	
	            	allValues.totalresultjsonValues.settotalresultJosn(totalSum);
	            	
	            	getComonentInfo.getComponentInfo(tempObj.get("name").toString(), tempObj.get("version").toString(), "ID");
	            	
	            	System.out.println(i + ". " + tempObj.get("name").toString() +  " / " + tempObj.get("version").toString() +  " / " + 
	    	            	tempObj.get("license_name").toString() +  " / " + tempObj.get("license_identifier").toString() +  " / " +
	    	            	allValues.billOfMaterialsValues.getidUrl() +  " / " + allValues.billOfMaterialsValues.getidCopyright());
				}	
				
			}
			
			System.out.println();
			
		} catch (Exception e) {
			logger.error("Exception Message", e);
			
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				logger.error("Exception Message", e);
			}
		}
	}
}

