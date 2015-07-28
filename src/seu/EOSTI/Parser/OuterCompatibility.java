package seu.EOSTI.Parser;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTParser;

import seu.EOSTI.ASTVisitor.ChangeabilityOfClassRequestor;
import seu.EOSTI.ASTVisitor.OuterCompatibilityRequestor;
import seu.EOSTI.DBConnect.ChangeabilityInfoConnector;


public class OuterCompatibility {

	private String projectName;
	private String version;	
	
	public OuterCompatibility(ASTParser parser, String pathOfProject,String projectNameString,String versionString){
		
//		ChangeabilityRequestor changeabilityRequestor = new ChangeabilityRequestor(projectNameString,versionString);
		OuterCompatibilityRequestor outerCompatibilityRequestor = new OuterCompatibilityRequestor(projectNameString,versionString);
		ReadFile readFile = new ReadFile(pathOfProject);
		List<String> filelist = readFile.readJavaFiles();
		
		int listLength = filelist.size();
		for (int i = 0; i < listLength; i += 100) {
			int toIndex = 0;
			if (i+100<listLength) {
				toIndex = i+100;
			}else {
				toIndex = listLength;
			}
			
			String[] sourceFilePaths = filelist.subList(i, toIndex).toArray(new String[toIndex - i]);
			parser.createASTs(sourceFilePaths,  null, new String[0], outerCompatibilityRequestor, null);
			
		}
		
		/*String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], changeabilityRequestor, null);*/

		
		/*ChangeabilityInfoConnector dbConnector = new ChangeabilityInfoConnector(projectNameString,versionString);
		ArrayList<String> packageNameList= dbConnector.getpackageName();
			// �����������  		        
	    for (String string : packageNameList) {
	    	dbConnector.setChangeabilityInfo(string);
		}*/
	}
	
	
	
	

	
	
}
