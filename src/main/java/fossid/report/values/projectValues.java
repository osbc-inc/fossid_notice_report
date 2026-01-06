package fossid.report.values;

import java.io.Serializable;

public class projectValues implements Serializable {
	
		private static final projectValues values = new projectValues();

		public projectValues() {
		}

		public static projectValues getInstance() {
			return values;
		}

		private static String projectId;
		private static String projectName;
		private static String versionId;
		private static String versionName;
		
		public String getProjectId() {
			return projectId;
		}

		public void setProjectId(String projectId) {
			projectValues.projectId = projectId;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			projectValues.projectName = projectName;
		}
		
		public String getVersionId() {
			return versionId;
		}

		public void setVersionId(String versionId) {
			projectValues.versionId = versionId;
		}

		public void setVersionName(String versionName) {
			projectValues.versionName = versionName;
		}
		
		public String getVersionName() {
			return versionName;
		}
		


}
