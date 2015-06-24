package seu.EOSTI.Parser;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTParser;

import seu.EOSTI.ASTVisitor.ChangeabilityOfClassRequestor;
import seu.EOSTI.ASTVisitor.ChangeabilityRequestor;
import seu.EOSTI.DBConnect.ChangeabilityInfoConnector;
import seu.EOSTI.DBConnect.ExtensibilityInfoConnector;


public class Changeability {

	private String projectName;
	private String version;	
	
	public Changeability(ASTParser parser, String pathOfProject,String projectNameString,String versionString){
		
//		ChangeabilityRequestor changeabilityRequestor = new ChangeabilityRequestor(projectNameString,versionString);
		ChangeabilityOfClassRequestor changeabilityRequestor = new ChangeabilityOfClassRequestor(projectNameString,versionString);
		ReadFile readFile = new ReadFile(pathOfProject);
		List<String> filelist = readFile.readJavaFiles();
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], changeabilityRequestor, null);
//		extensibilityRequestor.ShowInfoOfExitensibily();
		
		ChangeabilityInfoConnector dbConnector = new ChangeabilityInfoConnector(projectNameString,versionString);
		ArrayList<String> packageNameList= dbConnector.getpackageName();
			// 添加三行数据  		        
	    for (String string : packageNameList) {
	    	dbConnector.setChangeabilityInfo(string);
		}
	}
	
	
	
	

	
	
}
