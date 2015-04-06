package seu.EOSTI.UI;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import seu.EOSTI.DBConnect.ChangeabilityConnector;
import seu.EOSTI.DBConnect.DBConnector;
import seu.EOSTI.DBConnect.ExtensibilityConnector;
import seu.EOSTI.Parser.ProjectParser;



public class IntergrationMain {

	public static void main(String args[]) {

	String [] arrayOfProjects = {"D:/ProjectEOfHW/jEditor/jEditor0.2"};	
	
/*	String [] arrayOfProjects = {"D:/ProjectEOfHW/junit3.4","D:/ProjectEOfHW/junit3.5","D:/ProjectEOfHW/junit3.6",
			"D:/ProjectEOfHW/junit3.7","D:/ProjectEOfHW/junit3.8","D:/ProjectEOfHW/junit3.9","D:/ProjectEOfHW/junit4.0",
			"D:/ProjectEOfHW/junit4.1","D:/ProjectEOfHW/junit4.2","D:/ProjectEOfHW/junit4.3","D:/ProjectEOfHW/junit4.4",
			"D:/ProjectEOfHW/junit4.5","D:/ProjectEOfHW/junit4.6","D:/ProjectEOfHW/junit4.7","D:/ProjectEOfHW/junit4.8",
			"D:/ProjectEOfHW/junit4.9","D:/ProjectEOfHW/junit4.10","D:/ProjectEOfHW/junit4.11"};
	*/
	String pathOfProject;
	
	
	for (int i = 0; i < arrayOfProjects.length; i++) {
		pathOfProject = arrayOfProjects[i];
		File file = new File(pathOfProject);
		if (!file.isDirectory()) {
			System.out.println("Path of Project not EXIST:\t"+pathOfProject);
			continue;
		}
		
		System.out.println("Project:\t"+pathOfProject);

		ProjectParser projectFileUtil = new ProjectParser(pathOfProject, null,null);
		projectFileUtil.parser();
		projectFileUtil.getChangeabilityInfo();

		
		ChangeabilityConnector dbConnector = new ChangeabilityConnector();
		ArrayList<String> packageNameList= dbConnector.getpackageName();
		// 添加三行数据  		        
        
        for (String string : packageNameList) {
        
        	ArrayList<String> al = dbConnector.packageChangeabilityInfo(string, "jeditor", "0.2");
//        	item.setText((String[])al.toArray(new String[al.size()]));
		}

		System.out.println("end!");
	}
	
	}

		
	
}
