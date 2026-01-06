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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class getComponentInformation {
	private final Logger logger = LogManager.getLogger(getBillofMaterials.class);
	
	public void getComponentInfo(String componentName, String componentVersion, String identify) {
		
		JSONObject dataObject = new JSONObject();
		dataObject.put("username", allValues.loginValues.getUsername());
		dataObject.put("key", allValues.loginValues.getApikey());
		dataObject.put("component_name", componentName);
		dataObject.put("component_version", componentVersion);

		JSONObject rootObject = new JSONObject();
		rootObject.put("group", "components");
		rootObject.put("action", "get_information");
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
	        JSONObject resultJsonData = (JSONObject) resultJson.get("data");

			logger.debug("get_ComponentInformation resultJson: " + resultJson);
	        
			if(identify.equals("DE")) {
				if(resultJsonData.get("license_name") == null) {	        		
	        		System.out.println("DE: " + allValues.dependencyAnalysisResultValues.getdeComponent() + " / " + allValues.dependencyAnalysisResultValues.getdeVersion() + " > License Name is null");
	        		System.exit(1);
	        	} else {
	        		allValues.dependencyAnalysisResultValues.setdelicenseName(resultJsonData.get("license_name").toString());
	        	}
			}

			
			if(resultJsonData.get("url") == null) {
				if(identify.equals("ID")) {
					System.out.println("ID: " + allValues.billOfMaterialsValues.getidComponent() + " / " + allValues.billOfMaterialsValues.getidVersion()  + " > URL is null");
				} else {
					System.out.println("DE: " + allValues.dependencyAnalysisResultValues.getdeComponent() + " / " + allValues.dependencyAnalysisResultValues.getdeVersion()  + " > URL is null");
				}
        		System.exit(1);
        	} else {
        		if(identify.equals("SE")) {
            		allValues.billOfMaterialsValues.setidComponentHomepage(resultJsonData.get("url").toString());
        		}
        		
    			if(identify.equals("ID")) {
                	allValues.billOfMaterialsValues.setidUrl(resultJsonData.get("url").toString());
				} else {	            	
					allValues.dependencyAnalysisResultValues.setdeUrl(resultJsonData.get("url").toString());
				}
        	}
			
			if(resultJsonData.get("copyright") == null) {
				if(identify.equals("ID")) {
					System.out.println("ID: " + allValues.billOfMaterialsValues.getidComponent() + " / " + allValues.billOfMaterialsValues.getidVersion()  + " > Copyright is null");
				} else {
					System.out.println("DE: " + allValues.dependencyAnalysisResultValues.getdeComponent() + " / " + allValues.dependencyAnalysisResultValues.getdeVersion()  + " > Copyright is null");
				}
        		System.exit(1);
        	} else {        		
        		if(identify.equals("SE")) {
        			allValues.billOfMaterialsValues.setidComponentCopyright(resultJsonData.get("copyright").toString());
        		}
        		
				if(identify.equals("ID")) {						        	
	            	allValues.billOfMaterialsValues.setidCopyright(resultJsonData.get("copyright").toString());	            	
				} else {	            	
	            	allValues.dependencyAnalysisResultValues.setdeCopyright(resultJsonData.get("copyright").toString());
				}
        	}
		
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
