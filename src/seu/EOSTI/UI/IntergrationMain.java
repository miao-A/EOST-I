package seu.EOSTI.UI;

import japicmp.cmp.JarArchiveComparator;
import japicmp.cmp.JarArchiveComparatorOptions;
import japicmp.model.JApiClass;
import japicmp.model.JApiField;
import japicmp.model.JApiMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableItem;







import seu.EOSTI.DBConnect.ChangeabilityConnector;
import seu.EOSTI.DBConnect.ClassChangeabilityConnector;
import seu.EOSTI.DBConnect.DBConnector;
import seu.EOSTI.DBConnect.ExtensibilityConnector;
import seu.EOSTI.DBConnect.ProjectInfoConnector;
import seu.EOSTI.Model.JarClassModel;
import seu.EOSTI.Model.JarMethodModel;
import seu.EOSTI.Parser.AnalysisJarFile;
import seu.EOSTI.Parser.ChangeComparator;
import seu.EOSTI.Parser.Compatibility;
import seu.EOSTI.Parser.OuterCompatibility;
import seu.EOSTI.Parser.ProjectParser;
import seu.EOSTI.Parser.ReadFile;



public class IntergrationMain {
		
	public static void main(String args[]) {
		
		String projectPath = "D:/workspace/Hello";
		String projectName = "ss";
		String version = "ss";
		
		/*String projectPath = "D:\\ProjectOfHW\\jEditor\\jEditor0.2";
		String pathOfOne = "D:\\ProjectOfHW\\jEditor\\jEditor0.2\\src\\org\\jeditor\\app";*/
//		String pathOfTwo = "D:\\ProjectOfHW\\jEditor\\jEditor0.2\\src\\org\\jeditor\\diff";
		
/*		ProjectParser projectParser = new ProjectParser(projectPath,projectName,version);
		projectParser.parser();
		projectParser.runOuterCompatibilityDectector();*/
		OuterCompatibility outerCompatibility = new OuterCompatibility(projectPath,version);
		if (outerCompatibility.jdkCompatibility("1.4")) {			
			System.out.println("ºÊ»›");
		}else{
			for (String string : outerCompatibility.getuncompatibilityfileList()) {
				System.out.println(string);
			}
			System.out.println("≤ªºÊ»›");
		}
		
    
    	
    	/*List<JarClassModel> list = null;
    	try {

			list = AnalysisJarFile.getJarMethod("D:\\test\\jfreechart-1.0.19.jar","D:\\test\\TestJar");
			//list = AnalysisJarFile.getJarMethod("D:\\eclipse\\dropins\\lib\\mysql-connector-java-5.1.35-bin.jar");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
    	
    	/*for (JarClassModel jarClassModel : list) {
    		System.out.println("----------------------------------------------");
			System.out.println("classname:"+jarClassModel.getClassName());
			for (JarMethodModel jarMethodModel : jarClassModel.getmethod()) {
				System.out.println("methodname:"+jarMethodModel.getMethodName());
			}
			System.out.println("----------------------------------------------");
		}*/
    	
    	System.out.println("END");
    	
    }  
}