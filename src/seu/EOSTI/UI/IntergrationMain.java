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
import seu.EOSTI.Parser.ProjectParser;



public class IntergrationMain {
	
	
	private static int count = 0;
	private static int unchangedcount = 0;
	private static int newcount = 0 ;
	private static int removedcount = 0;
	private static int modifiedcount = 0;
	private static ArrayList<String> unchangeClassList;
	private static ArrayList<String> newClassList;
	private static ArrayList<String> removedClassList;
	private static ArrayList<JApiClass> modifiedClassList;
	
	
	
	public static void  main(String[] args) {
		
/*		File oldArchive = new File("D:/eclipse/plugins/org.eclipse.swt.win32.win32.x86_3.102.1.v20130827-2048.jar");
		File newArchive = new File("");*/
		unchangeClassList = new ArrayList<String>();
		newClassList = new ArrayList<String>();
		removedClassList = new ArrayList<String>();
		modifiedClassList = new ArrayList<JApiClass>();
		
		File oldArchive = new File("D:/eclipse/dropins/lib/junit-4.11.jar");
		File newArchive = new File("D:/eclipse/dropins/lib/junit-4.12.jar");
		
		JarArchiveComparatorOptions comparatorOptions = new JarArchiveComparatorOptions();


		JarArchiveComparator jarArchiveComparator = new JarArchiveComparator(comparatorOptions);

		//getChangeStatus : UNCHANGED NEW MODIFIED  REMOVED
//		try {
			List<JApiClass> jApiClasses = jarArchiveComparator.compare(oldArchive, newArchive);
			for (JApiClass jApiClass : jApiClasses) {
				if (jApiClass.getChangeStatus().toString().equals("UNCHANGED")) {
					++unchangedcount;
					unchangeClassList.add(jApiClass.getFullyQualifiedName());
					
				}else if (jApiClass.getChangeStatus().toString().equals("NEW")) {
					++newcount;
					newClassList.add(jApiClass.getFullyQualifiedName());

				}else if (jApiClass.getChangeStatus().toString().equals("MODIFIED")) {
					++modifiedcount;	
					modifiedClassList.add(jApiClass);				
				}else if (jApiClass.getChangeStatus().toString().equals("REMOVED")) {
					++removedcount;
					removedClassList.add(jApiClass.getFullyQualifiedName());					
				}
			}
//		} catch (Exception e) {
			// TODO: handle exception
//			System.out.println("not a complete jar!");
//		}
		
		for (JApiClass jApiClass: modifiedClassList) {
			List<JApiField> jApiFields = jApiClass.getFields();
			for (JApiField jApiField : jApiFields) {
				System.out.println(jApiField.getChangeStatus());
			}
		}
	
		
		System.out.println(unchangedcount);
		System.out.println(newcount);
		System.out.println(modifiedcount);
		System.out.println(removedcount);
		System.out.println("end");
	
	
	

/*
		
//		String [] arrayOfProjects = {"E:/GitHub/EOST-I"};
		
//		String [] arrayOfProjects = {"D:/ProjectEOfHW/junit/junit3.4"};	
		String [] arrayOfProjects = {"D:/ProjectEOfHW/jEditor/jeditor0.2"};	
	
	String [] arrayOfProjects = {"D:/ProjectEOfHW/junit3.4","D:/ProjectEOfHW/junit3.5","D:/ProjectEOfHW/junit3.6",
			"D:/ProjectEOfHW/junit3.7","D:/ProjectEOfHW/junit3.8","D:/ProjectEOfHW/junit3.9","D:/ProjectEOfHW/junit4.0",
			"D:/ProjectEOfHW/junit4.1","D:/ProjectEOfHW/junit4.2","D:/ProjectEOfHW/junit4.3","D:/ProjectEOfHW/junit4.4",
			"D:/ProjectEOfHW/junit4.5","D:/ProjectEOfHW/junit4.6","D:/ProjectEOfHW/junit4.7","D:/ProjectEOfHW/junit4.8",
			"D:/ProjectEOfHW/junit4.9","D:/ProjectEOfHW/junit4.10","D:/ProjectEOfHW/junit4.11"};
	
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

        }


		System.out.println("end!");
	}
	*/
	}

		
	
}
