package fossid.report.getdata;

import static fossid.report.values.AllValues.allValues;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class getLicenseInformation {
	
	public void getLicenseInfo(String licenseId) {
		

		JSONObject dataObject = new JSONObject();
		dataObject.put("username", allValues.loginValues.getUsername());
		dataObject.put("key", allValues.loginValues.getApikey());
        dataObject.put("license_identifier", licenseId);
		
		JSONObject rootObject = new JSONObject();
        rootObject.put("group", "licenses");
        rootObject.put("action", "get_information");
		rootObject.put("data", dataObject);
		
		
		HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		
		try {

			StringEntity entity = new StringEntity(rootObject.toString());
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);			
			
			
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpClientResponse.getStatusLine().getStatusCode());
			}	
								
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), "utf-8"));
			String result = br.readLine();
			
	        JSONParser jsonParser = new JSONParser();
	        JSONObject resultJson = (JSONObject) jsonParser.parse(result);
	        
			JSONObject resultJsonData = (JSONObject) resultJson.get("data");
			
			if(resultJsonData.get("name") == null) {
				allValues.ulicenseValues.setuComponentLicenseName("");
			} else {
				allValues.ulicenseValues.setuComponentLicenseName(resultJsonData.get("name").toString());
			}
			
			if(resultJsonData.get("text") == null) {
				allValues.ulicenseValues.setuComponentLicenseText("");
				allValues.ulicenseValues.setXlicenseText(resultJsonData.get("name").toString());
			} else {
				allValues.ulicenseValues.setuComponentLicenseText(resultJsonData.get("text").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}

}