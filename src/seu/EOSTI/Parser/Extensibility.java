package seu.EOSTI.Parser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import seu.EOSTI.ASTVisitor.ExtensibilityRequestor;
import seu.EOSTI.DBConnect.ExtensibilityConnector;
import seu.EOSTI.DBConnect.ExtensibilityInfoConnector;

public class Extensibility {
	private int numOfInterface;
	private int numOfAbstract;
	private int numOfClass;
	private double ratioOfInterface;
	
	private String projectNameString;
	private String versionString;
	
	
	
	public Extensibility(ASTParser parser, String pathOfProject,String projectNameString,String versionString){
		
		ExtensibilityRequestor extensibilityRequestor = new ExtensibilityRequestor(projectNameString,versionString);

		this.projectNameString = projectNameString;
		this.versionString = versionString;
		
		ReadFile readFile = new ReadFile(pathOfProject);
		List<String> filelist = readFile.readJavaFiles();
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], extensibilityRequestor, null);
		
		ExtensibilityInfoConnector dbConnector = new ExtensibilityInfoConnector(projectNameString,versionString);
		ArrayList<String> packageNameList= dbConnector.getpackageName();
			// 添加三行数据  		        
	    for (String string : packageNameList) {
	    	dbConnector.setExtensibilityInfo(string);
		}

	}


}
