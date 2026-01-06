package fossid.report.main;

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

public class validateAuthentication {
	private static final Logger logger = LogManager.getLogger(validateAuthentication.class);
	
	public static void validateAuthentication() {
		JSONObject dataObject = new JSONObject();
        dataObject.put("username", allValues.loginValues.getUsername());
        dataObject.put("key", allValues.loginValues.getApikey());
        dataObject.put("searched_username", allValues.loginValues.getUsername());
		
		JSONObject rootObject = new JSONObject();
        rootObject.put("group", "users");
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
				throw new Exception("Please, check the fossid.domain/fossid.schema in the config.properties file or --protocol/--address values"
						+ "\nFailed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
			}
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
			String result = br.readLine();
			
			JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
	        
	        // set false if validation is failed
	        if(jsonObj.get("status").equals("0")){
				throw new Exception("Please, check the fossid.username/fossid.apikey in the config.properties file or --username/--apikey values");
	        }
	        
		} catch (Exception e) {
			logger.error("Exception Message", e);
		}
	}
	

}
