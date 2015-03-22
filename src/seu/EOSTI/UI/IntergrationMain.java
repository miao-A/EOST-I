package seu.EOSTI.UI;

import java.io.File;

import seu.EOSTI.Parser.ProjectParser;



public class IntergrationMain {

	public static void main(String args[]) {

	String [] arrayOfProjects = {"D:/ProjectEOfHW/junit3.4"};	
	
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
		ProjectParser projectFileUtil = new ProjectParser(pathOfProject);
		projectFileUtil.parser();
		projectFileUtil.getInfoOfProject();
		projectFileUtil.getExtensibilityInfo();

		System.out.println("end!");
	}
	
	}

		
	
}
