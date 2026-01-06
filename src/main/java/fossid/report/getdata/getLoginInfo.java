package fossid.report.getdata;

import static fossid.report.values.AllValues.allValues;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class getLoginInfo {
	private final Logger logger = LogManager.getLogger(getLoginInfo.class);

	public void getInfo(String protocol, String address, String userName, String apikey) {
		getLoginInfo(protocol, address, userName, apikey);
	}
	
	private void getLoginInfo(String protocol, String address, String userName, String apiKey) {
		String propsPath = System.getProperty("user.dir") + "\\config.properties";
		FileReader resources = null;

		logger.info("config.properties path : " + propsPath);

		try {
			resources = new FileReader(propsPath);
			Properties props = new Properties();
			//InputStream is = getClass().getResourceAsStream(propsPath);
			props.load(resources);
			
			String schema;
			String url;

			if(protocol.equals("")) {
				schema = props.getProperty("fossid.schema");
			} else {
				schema = protocol;
			}
			
			if(address.equals("")) {
				url = props.getProperty("fossid.domain");
			} else {
				url = address;
			}
			
			if(schema.equals("http")) {
				
				allValues.loginValues.setServerUri("http://" + url);
				
				//check "fossid.domain" to add / in front of api.php
				String temp = allValues.loginValues.getServerUri();
				temp = temp.substring(temp .length() - 1);
				
				if(temp.endsWith("/")) {
					allValues.loginValues.setServerApiUri("http://" + url + "api.php");
				} else {
					allValues.loginValues.setServerApiUri("http://" + url + "/api.php");
				}
				
			} else if(schema.equals("https")) {
				allValues.loginValues.setServerUri("https://" + url);
				
				//check "fossid.domain" to add / in front of api.php
				String temp = allValues.loginValues.getServerUri();
				temp = temp.substring(temp .length() - 1);
				
				if(temp.endsWith("/")) {
					allValues.loginValues.setServerApiUri("https://" + url + "api.php");
				} else {
					allValues.loginValues.setServerApiUri("https://" + url + "/api.php");
				}				
			}
			
			String username;
			String apikey;
			
			if(userName.equals("")) {
				username = props.getProperty("fossid.username");
			} else {
				username = userName;
			}
			
			if(apiKey.equals("")) {
				apikey = props.getProperty("fossid.apikey");
			} else {
				apikey = apiKey;
			}
			
			allValues.loginValues.setUsername(username);
			allValues.loginValues.setApikey(apikey);
			allValues.loginValues.setSetHeader(props.getProperty("fossid.header"));
			
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
	}
}
