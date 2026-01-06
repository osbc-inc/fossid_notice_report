package fossid.report.getdata;

import static fossid.report.values.AllValues.allValues;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class getDependencyAnalysisResults {
	
	private final Logger logger = LogManager.getLogger(getBillofMaterials.class);
	
	public void getDependencyResult() {
		
		JSONObject dataObject = new JSONObject();
		dataObject.put("username", allValues.loginValues.getUsername());
		dataObject.put("key", allValues.loginValues.getApikey());
		dataObject.put("scan_code", allValues.projectValues.getVersionId());

		JSONObject rootObject = new JSONObject();
		rootObject.put("group", "scans");
		rootObject.put("action", "get_dependency_analysis_results");
		rootObject.put("data", dataObject);
		
		BufferedReader br = null;
		HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		try {
			StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);			
			
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception("Failed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
			}
			
			br = new BufferedReader(new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
			String result = br.readLine();

	        JSONParser jsonParser = new JSONParser();
	        JSONObject resultJson = (JSONObject) jsonParser.parse(result);

			logger.debug("get_DependencyAnalysisResult resultJson: " + resultJson);
	        
			JSONArray resultJsonData = (JSONArray) resultJson.get("data");
			
	        getComponentInformation getComonentInfo = new getComponentInformation();
	        
        	System.out.println("=== Dependency Results ====");
        	System.out.println("Component Name / Component Version / License Name / License ID / URL / Copyright");
			
        	int j = 0;
        	String totalSum = "";
        	
			for(int i = 0; i < resultJsonData.size(); i++) {
				JSONObject tempObj = (JSONObject) jsonParser.parse(resultJsonData.get(i).toString());
				
				if(tempObj.get("name").toString() == null) {
            		System.out.println("DE: " + "Component Name is null");
            		System.exit(1);
            	} else {
            		allValues.dependencyAnalysisResultValues.setdeComponent(tempObj.get("name").toString());
            		totalSum = tempObj.get("name").toString();            		
            	}
            	
            	if(tempObj.get("version").toString() == null) {
            		System.out.println("DE: " + "Component Version is null");
            		System.exit(1);
            	} else {
            		allValues.dependencyAnalysisResultValues.setdeVersion(tempObj.get("version").toString());
            		totalSum = totalSum + "|" + tempObj.get("version").toString();	            	
            	}
            	
            	// To LicenseName
               	getComonentInfo.getComponentInfo(tempObj.get("name").toString(), tempObj.get("version").toString(), "DE");

               	totalSum = totalSum + "|" + allValues.dependencyAnalysisResultValues.getdelicenseName();
			            	
               	if(tempObj.get("license_identifier") == null) {
            		System.out.println("DE: " + "License ID is null");
            		System.exit(1);
            	} else {
            		totalSum = totalSum + "|" + tempObj.get("license_identifier").toString();
            	}
			
               	j++;
               	
               	allValues.totalresultjsonValues.settotalresultJosn(totalSum);
               	
            	System.out.println(j + ". " + tempObj.get("name").toString() +  " / " + tempObj.get("version").toString() +  " / " + 
            	allValues.dependencyAnalysisResultValues.getdelicenseName() +  " / " + tempObj.get("license_identifier").toString() +  " / " +
            	allValues.dependencyAnalysisResultValues.getdeUrl() +  " / " + allValues.dependencyAnalysisResultValues.getdeCopyright());
			}
			
        	System.out.println();			

		} catch (Exception e) {
			logger.error("Exception Message", e);
			System.exit(1);
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
