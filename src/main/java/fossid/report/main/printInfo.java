package fossid.report.main;

import static fossid.report.values.AllValues.allValues;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class printInfo {
	private static final Logger logger = LogManager.getLogger(printInfo.class);
	public static void startFOSSID() {
		logger.info("Start Report Generator");
		logger.info("");
		logger.info("******                                 *****    ****");
		logger.info("*                                        *      *    *");
		logger.info("*                                        *      *     *");
		logger.info("******    ****     *****     *****       *      *     *");
		logger.info("*        *    *   *         *            *      *     *");
		logger.info("*        *    *    *****     *****       *      *    *");
		logger.info("*        *    *         *         *      *      *   *");
		logger.info("*         ****     *****     *****     *****    ****");
		logger.info("");
	}
	
	public static void endFOSSID() {
		
		System.out.println("=== This licenses does not contain License Text ====");
		for(int i = 0; i < allValues.ulicenseValues.getXlicenseText().size(); i++) {
			System.out.println(allValues.ulicenseValues.getXlicenseText().get(i));
		}
		System.out.println("");
		
	}

	public static void printInfo() {
		int apiKeyLength = allValues.loginValues.getApikey().length();
		StringBuilder apiKey = new StringBuilder();
		String projectName = "";

		for (int i = 0; i < apiKeyLength; i++) {
			apiKey.append("*");
		}

		if (allValues.projectValues.getProjectId() == null) {
			projectName = "No projects included";
		} else {
			projectName = allValues.projectValues.getProjectName() + " / " + allValues.projectValues.getProjectId();
		}

		logger.info("");
		logger.info("Server URL: " + allValues.loginValues.getServerApiUri());
		logger.info("UserName: " + allValues.loginValues.getUsername());
		logger.info("ApiKey: " + apiKey);
		logger.info("Project Name/Code: " + projectName);
		logger.info("Scan Name/Code: " + allValues.projectValues.getVersionName() + " / " + allValues.projectValues.getVersionId());
		logger.info("");
	}

}
