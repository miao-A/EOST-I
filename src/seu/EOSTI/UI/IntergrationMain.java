package seu.EOSTI.UI;

import japicmp.cmp.JarArchiveComparator;
import japicmp.cmp.JarArchiveComparatorOptions;
import japicmp.model.JApiClass;
import japicmp.model.JApiField;
import japicmp.model.JApiMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import seu.EOSTI.ASTVisitor.ExtendsRequestor;
import seu.EOSTI.DBConnect.ChangeabilityConnector;
import seu.EOSTI.DBConnect.ClassChangeabilityConnector;
import seu.EOSTI.DBConnect.DBConnector;
import seu.EOSTI.DBConnect.ExtensibilityConnector;
import seu.EOSTI.Parser.ChangeComparator;
import seu.EOSTI.Parser.Compatibility;
import seu.EOSTI.Parser.ProjectParser;
import seu.EOSTI.Parser.ReadFile;



public class IntergrationMain {
		
	public static void main(String args[]) {
		

	/*String oldPath = "D:/ProjectOfHW/jEditor/jEditor0.4.1";
	String newPathOfComponet = "D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";*/
		
	/*String oldPathOfComponet = "D:/ProjectOfHW/junit/junit4.0";
	String newPathOfComponet = "D:/ProjectOfHW/junit/junit4.1";*/

	
	/*ComponentParser componentParser = new ComponentParser(oldPathOfComponet, newPathOfComponet);
	componentParser.parser();*/
	/*Compatibility compatibility = new Compatibility(oldPathOfComponet, newPathOfComponet);
	compatibility.getinfo();*/
		
		
	String oldPathOfProject = "D:/ProjectOfHW/junit/junit4.0";
	String newPathOfProject = "D:/ProjectOfHW/junit/junit4.1";
	ChangeComparator changeComparator = new ChangeComparator(oldPathOfProject, newPathOfProject);
	changeComparator.getinfo();

	/*	for (int i = 0; i < arrayOfProjects.length; i++) {
		pathOfProject = arrayOfProjects[i];
		File file = new File(pathOfProject);
		if (!file.isDirectory()) {
			System.out.println("Path of Project not EXIST:\t"+pathOfProject);
			continue;
		}
		
		System.out.println("Project:\t"+pathOfProject);
			
		ProjectParser projectFileUtil = new ProjectParser(pathOfProject, null,null);
		projectFileUtil.parser();
		projectFileUtil.getExtensibilityInfo();

	
		//以包级别分析易替代性 
		ChangeabilityConnector dbConnector = new ChangeabilityConnector();
 		ArrayList<String> packageNameList= dbConnector.getpackageName();

        for (String string : packageNameList) { 
        
        	ArrayList<String> al = dbConnector.packageChangeabilityInfo(string, "EOSTI", "1.0");
//        	item.setText((String[])al.toArray(new String[al.size()]));
        }
		
		//以类级别分析易替代性
		ClassChangeabilityConnector dbConnector = new ClassChangeabilityConnector();
		ArrayList<String> classNameList= dbConnector.getClassName("EOSTI", "1.0");
        for (String string : classNameList) {
        	String[] pc = string.split("[$]"); 
            
        	dbConnector.ClassChangeabilityInfo(pc[0],pc[1], "EOSTI", "1.0");
//        	item.setText((String[])al.toArray(new String[al.size()]));
        }

		
	}*/

//	extensibilityRequestor.ShowInfoOfExitensibily();
	
	

	
	System.out.println("end!!");
	}


		
	
}
