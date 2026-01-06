package fossid.report.main;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import fossid.report.getdata.getBillofMaterials;
import fossid.report.getdata.getDependencyAnalysisResults;
import fossid.report.getdata.getLoginInfo;
import fossid.report.getdata.getprojectVersionInfo;
import fossid.report.getdata.setTotalSumList;
import fossid.report.write.writeNoticeReport;


public class main {
	private static final Logger logger = LogManager.getLogger(main.class);
	private static final long start = System.currentTimeMillis();

	public static void main(String[] args) {
		try {
			
			String protocol = "";
			String address = "";
			String userName = "";
			
			String apikey = "";
			String projectName = "";
			String scanName = "";
			String license = "";
			String cliPath = "";
			
			for(int i = 0; i < args.length; i++) {
				
				if(args[i].equals("--protocol")) {
					protocol = args[i+1]; 
				}
				
				if(args[i].equals("--address")) {
					address = args[i+1];
				}
				
				if(args[i].equals("--username")) {
					userName = args[i+1]; 
				}
				
				if(args[i].equals("--apikey")) {
					apikey = args[i+1];
				}
				
				if(args[i].equals("--projectname")) {
					projectName = args[i+1]; 
				}
				
				if(args[i].equals("--scanname")) {
					scanName = args[i+1];
				}
				
				i++;
			}
			
			printInfo.startFOSSID();
			
			getLoginInfo common = new getLoginInfo();
			common.getInfo(protocol, address, userName, apikey);
			
			validateAuthentication.validateAuthentication();
			
			getprojectVersionInfo pvInfo = new getprojectVersionInfo();
			pvInfo.getInfo(projectName, scanName, license);
			
			printInfo.printInfo();			
			
			getBillofMaterials getidBom = new getBillofMaterials();
			getidBom.getBomInfo();

			getDependencyAnalysisResults getdeBom = new getDependencyAnalysisResults();
			getdeBom.getDependencyResult();
			
			setTotalSumList setTotal = new setTotalSumList();
			setTotal.settotalsumList();
			
			writeNoticeReport writetxt = new writeNoticeReport();
			writetxt.writeNotice();

			printInfo.endFOSSID();

			long end = System.currentTimeMillis();
			logger.info("RunTime : " + (end - start)/60000 + "m" + ((end - start)%60000)/1000 + "s");
		} catch (Exception e) {
			logger.error("Exception Message", e);
			System.exit(1);
		}
	}
}