package fossid.report.getdata;

import static fossid.report.values.AllValues.allValues;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

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

public class getprojectVersionInfo {
	private final Logger logger = LogManager.getLogger(getprojectVersionInfo.class);
	
	public void getInfo(String projectName, String scanName, String license) {
		String propsPath = System.getProperty("user.dir") + "\\config.properties";
		FileReader resources = null;

		try {
			Properties props = new Properties();
			resources = new FileReader(propsPath);
			props.load(resources);

			// To change encoding to UTF-8
			String encoding;
			
			if(projectName.equals("")) {
				encoding = new String(props.getProperty("fossid.project").getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
				allValues.projectValues.setProjectName(encoding);
			} else {
				allValues.projectValues.setProjectName(projectName);
			}
			
			if(scanName.equals("")) {
				encoding = new String(props.getProperty("fossid.scan").getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
				allValues.projectValues.setVersionName(encoding);
			} else {
				allValues.projectValues.setVersionName(scanName);
			}
			
		} catch (IOException e) {
			logger.error("Exception Message", e);
		} finally {
			try {
				if (resources != null) {
					resources.close();
				}
			} catch (Exception e) {
				logger.error("Exception Message", e);
			}
		}
		
		getProjectId();
		//getVersionId();

		if (("").equals(allValues.projectValues.getProjectName())) {
			checkProjectVersionWithOutProject();
		} else {
			checkProjectVersion();
		}
	}
	
	private void getProjectId() {
		// create json to call FOSSID project/list_projects api
		JSONObject dataObject = new JSONObject();
        dataObject.put("username", allValues.loginValues.getUsername());
        dataObject.put("key", allValues.loginValues.getApikey());
		
		JSONObject rootObject = new JSONObject();
        rootObject.put("group", "projects");
        rootObject.put("action", "list_projects");
		rootObject.put("data", dataObject);
		
		HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
		HttpClient httpClient = HttpClientBuilder.create().build();
		BufferedReader br = null;
		
		try {

			StringEntity entity = new StringEntity(rootObject.toString());
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);			

			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception("Failed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
			}
			
			br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
			String result = br.readLine();

			logger.debug("result: " + result);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
            JSONArray dataArray = (JSONArray) jsonObj.get("data");
            
            ArrayList<String> projectList = new ArrayList<>();
            //set projectid
			for (Object o : dataArray) {
				JSONObject tempObj = (JSONObject) o;
				if (tempObj.get("project_name").equals(allValues.projectValues.getProjectName())) {
					allValues.projectValues.setProjectId(tempObj.get("project_code").toString());
				}
				projectList.add(tempObj.get("project_name").toString());
			}
            
            if(!projectList.contains(allValues.projectValues.getProjectName())){
				logger.warn("Please, check the fossid.project in the config.properties file");
            }
            
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

	private void checkProjectVersion() {
		JSONObject dataObject = new JSONObject();
        dataObject.put("username", allValues.loginValues.getUsername());
        dataObject.put("key", allValues.loginValues.getApikey());
        dataObject.put("project_code", allValues.projectValues.getProjectId());
		
		JSONObject rootObject = new JSONObject();
        rootObject.put("group", "projects");
        rootObject.put("action", "get_all_scans");
		rootObject.put("data", dataObject);
		
		HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
		HttpClient httpClient = HttpClientBuilder.create().build();
		BufferedReader br = null;
		
		try {			
			// TO set UTF-8 Entity
			StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");
			
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);	
			
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception("Please, check " + allValues.projectValues.getVersionName() + " is mapped to "
						+ allValues.projectValues.getProjectName() + "or \ncheck " + allValues.loginValues.getUsername() + " is assigned to "
						+ allValues.projectValues.getProjectName() + "\nFailed : HTTP Error code : "
						+ httpClientResponse.getStatusLine().getStatusCode());
			}
			
			br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
			String result = br.readLine();

			logger.debug("result2: " + result);

			JSONParser jsonParser = new JSONParser();
            JSONObject resultJson = (JSONObject) jsonParser.parse(result);
            
            /*
              Check Project contains fossid.scanname dedicated in config.properties.
              Implement if project include one or more scan names
              one scanname return JSONArray, more scanmes return JSONObject
             */
            // to recognize JSONArray or JSONObject
            String arrayOrObject = resultJson.toString().substring(8,9);

            if(arrayOrObject.equals("[")) {
				JSONArray dataArray = (JSONArray) resultJson.get("data");

				ArrayList<String> codeList = new ArrayList<>();
                // set key value of jsonObj2 and run loop while(until) iter has value
				for (Object data : dataArray) {
					JSONObject jsonData = (JSONObject) data;

					String code = jsonData.get("code").toString();
					String name = jsonData.get("name").toString();

					if(allValues.projectValues.getVersionName().equals(name)) {
						allValues.projectValues.setVersionId(code);
					}

					codeList.add(code);
				}
                
                if(!codeList.contains(allValues.projectValues.getVersionId())) {
					throw new Exception("Please, check the fossid.scanname in the config.properties file or assign a scan to a Project on FOSSID");
                }
            } else if (arrayOrObject.equals("{")){
            	JSONArray dataArray = (JSONArray) resultJson.get("data");
				JSONObject tempObj = (JSONObject) dataArray.get(0);
      	        
      	        if(allValues.projectValues.getVersionName().equals(tempObj.get("name"))) {
      	        	allValues.projectValues.setVersionId(tempObj.get("code").toString());
      	        } else {
					throw new Exception("Please, check the fossid.scanname in the config.properties file or assign a scan to a Project on FOSSID");
      	        }
            }
		} catch (Exception e) {
			logger.error("Exception Message",e);
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

	private void checkProjectVersionWithOutProject() {
		JSONObject dataObject = new JSONObject();
		dataObject.put("username", allValues.loginValues.getUsername());
		dataObject.put("key", allValues.loginValues.getApikey());

		JSONObject rootObject = new JSONObject();
		rootObject.put("group", "scans");
		rootObject.put("action", "list_scans");
		rootObject.put("data", dataObject);

		HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
		HttpClient httpClient = HttpClientBuilder.create().build();
		BufferedReader br = null;

		try {
			// TO set UTF-8 Entity
			StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");

			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(entity);

			HttpResponse httpClientResponse = httpClient.execute(httpPost);

			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception("Please, check " + allValues.projectValues.getVersionName() + " is mapped to "
						+ allValues.projectValues.getProjectName() + "or \ncheck " + allValues.loginValues.getUsername() + " is assigned to "
						+ allValues.projectValues.getProjectName() + "\nFailed : HTTP Error code : "
						+ httpClientResponse.getStatusLine().getStatusCode());
			}

			br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
			String result = br.readLine();

			logger.debug("result2: " + result);

			JSONParser jsonParser = new JSONParser();
			JSONObject resultJson = (JSONObject) jsonParser.parse(result);

            /*
              Check Project contains fossid.scanname dedicated in config.properties.
              Implement if project include one or more scan names
              one scanname return JSONArray, more scanmes return JSONObject
             */
			// to recognize JSONArray or JSONObject

			JSONObject dataArray = (JSONObject) resultJson.get("data");

			ArrayList<String> codeList = new ArrayList<>();
			// set key value of jsonObj2 and run loop while(until) iter has value
			dataArray.values().forEach(values -> {
				JSONObject value = (JSONObject) values;

				String code = value.get("code").toString();
				String name = value.get("name").toString();

				if(allValues.projectValues.getVersionName().equals(name)) {
					allValues.projectValues.setVersionId(code);
				}

				codeList.add(code);
			});

			if(!codeList.contains(allValues.projectValues.getVersionId())) {
				throw new Exception("Please, check the fossid.scanname in the config.properties file or assign a scan to a Project on FOSSID");
			}
		} catch (Exception e) {
			logger.error("Exception Message",e);
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
