package seu.EOSTI.ASTParser;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;

import org.jfree.chart.*;


public class IntergrationMain {
	
	
	public static void main(String[] args) throws IOException {
		
	/*	String [] arrayOfProjects = {"D:/ProjectEOfHW/heritrix0.2.0","D:/ProjectEOfHW/heritrix0.4.0",
				"D:/ProjectEOfHW/heritrix0.6.0","D:/ProjectEOfHW/heritrix0.8.0",
				"D:/ProjectEOfHW/heritrix1.0.0","D:/ProjectEOfHW/heritrix1.10.0",
				"D:/ProjectEOfHW/heritrix1.12.0","D:/ProjectEOfHW/heritrix1.14.4",
				"D:/ProjectEOfHW/heritrix-3.0.0","D:/ProjectEOfHW/heritrix-3.1.0"};
		*/
//		String [] arrayOfProjects = {"D:/ProjectEOfHW/eclipseCode/eclipse-3.0"};	

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
			ProjectFileUtil projectFileUtil = new ProjectFileUtil(pathOfProject);
			projectFileUtil.parser();
			projectFileUtil.getInfoOfExtensibility();
			if (i==arrayOfProjects.length-1) {
				projectFileUtil.showChart();
			}

			System.out.println("end!");
		}

	}

}
