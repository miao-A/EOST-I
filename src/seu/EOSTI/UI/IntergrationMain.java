package seu.EOSTI.UI;

import japicmp.cmp.JarArchiveComparator;
import japicmp.cmp.JarArchiveComparatorOptions;
import japicmp.model.JApiClass;
import japicmp.model.JApiField;
import japicmp.model.JApiMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import seu.EOSTI.DBConnect.ChangeabilityConnector;
import seu.EOSTI.DBConnect.ClassChangeabilityConnector;
import seu.EOSTI.DBConnect.DBConnector;
import seu.EOSTI.DBConnect.ExtensibilityConnector;
import seu.EOSTI.Parser.ComponentParser;
import seu.EOSTI.Parser.ProjectParser;



public class IntergrationMain {
	
	public static void main(String args[]) {
		
//		String [] arrayOfProjects = {"E:/GitHub/EOST-I"};
		
//		String [] arrayOfProjects = {"D:/ProjectEOfHW/junit/junit3.4/src/junit/awtui"};	
//		String [] arrayOfProjects = {"D:/ProjectEOfHW/jEditor/jeditor0.2"};	
	
/*	String [] arrayOfProjects = {"D:/ProjectEOfHW/junit3.4","D:/ProjectEOfHW/junit3.5","D:/ProjectEOfHW/junit3.6",
			"D:/ProjectEOfHW/junit3.7","D:/ProjectEOfHW/junit3.8","D:/ProjectEOfHW/junit3.9","D:/ProjectEOfHW/junit4.0",
			"D:/ProjectEOfHW/junit4.1","D:/ProjectEOfHW/junit4.2","D:/ProjectEOfHW/junit4.3","D:/ProjectEOfHW/junit4.4",
			"D:/ProjectEOfHW/junit4.5","D:/ProjectEOfHW/junit4.6","D:/ProjectEOfHW/junit4.7","D:/ProjectEOfHW/junit4.8",
			"D:/ProjectEOfHW/junit4.9","D:/ProjectEOfHW/junit4.10","D:/ProjectEOfHW/junit4.11"};
	*/
	String oldPathOfComponet = "D:/ProjectEOfHW/jEditor/jEditor0.3/src/org/jeditor/diff";
	String newPathOfComponet = "D:/ProjectEOfHW/jEditor/jEditor0.3/src/org/jeditor/diff";
	
	ComponentParser componentParser = new ComponentParser(oldPathOfComponet, newPathOfComponet);
	componentParser.parser();
	
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
	System.out.println("end!");
	}


		
	
}
