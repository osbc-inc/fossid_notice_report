package fossid.report.write;

import static fossid.report.values.AllValues.allValues;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import fossid.report.getdata.getLicenseInformation;

public class writeNoticeReport {
	
	public void writeNotice() {	
		
		String date = new DateTime().toString(DateTimeFormat.forPattern("yyyyMMdd_HHmmss"));
		String fileName = date + "_" + allValues.projectValues.getVersionName() + "_고지문.txt" ;
		
		getLicenseInformation getlicenseTxt = new getLicenseInformation();
		
		try{
						
			
			//BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF8"));
			
			// 파일안에 문자열 쓰기
			fw.write("Open Source Software Notice\n");
			fw.write("\n");
			fw.write("==============================================================================\n");
			fw.write("\n");
			fw.write(allValues.loginValues.getSetHeader());
			fw.write("\n");			
			
			if(allValues.billOfMaterialsValues.getidComponentName().size() != 0) {
				for(int i = 0; i < allValues.billOfMaterialsValues.getidComponentName().size(); i++) {
					fw.write(allValues.billOfMaterialsValues.getidComponentName().get(i)+"\n");
					fw.write(allValues.billOfMaterialsValues.getidComponentHomepage().get(i)+"\n");
					fw.write(allValues.billOfMaterialsValues.getidComponentCopyright().get(i)+"\n");
					fw.write(allValues.billOfMaterialsValues.getidComponentLicenseName().get(i)+"\n");
					if(i < allValues.billOfMaterialsValues.getidComponentName().size() - 1 ) {
						fw.write("----------------\n");
					}
				}	
			}
			
			fw.write("\n");
			
			if(allValues.ulicenseValues.getuComponentLicenseId().size() != 0) {
				for(int i = 0; i < allValues.ulicenseValues.getuComponentLicenseId().size(); i++) {
					getlicenseTxt.getLicenseInfo(allValues.ulicenseValues.getuComponentLicenseId().get(i));
					
					if(allValues.ulicenseValues.getuComponentLicenseName().equals("Not Applicable")){						
					} else {
						fw.write("=============================================================================\n");
						fw.write(allValues.ulicenseValues.getuComponentLicenseName() + "\n");
						fw.write("\n");
						fw.write(allValues.ulicenseValues.getuComponentLicenseText() + "\n");
						fw.write("\n");
						fw.write("\n");
					}				
				}
			}
			
			fw.flush();

			// 객체 닫기
			fw.close(); 
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
