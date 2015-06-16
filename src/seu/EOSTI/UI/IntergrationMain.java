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
		
/*	String oldPathOfComponet = "D:/ProjectOfHW/junit/junit4.0";
	String newPathOfComponet = "D:/ProjectOfHW/junit/junit4.1";
	Compatibility compatibility = new Compatibility(oldPathOfComponet, newPathOfComponet);
	compatibility.getinfo();*/
	
	/*ComponentParser componentParser = new ComponentParser(oldPathOfComponet, newPathOfComponet);
	componentParser.parser();*/

		
		
	String oldPathOfProject = "D:/ProjectOfHW/junit/junit3.9";
	String newPathOfProject = "D:/ProjectOfHW/junit/junit4.0";
	ChangeComparator changeComparator = new ChangeComparator(oldPathOfProject, newPathOfProject);
	changeComparator.getinfo();
	
	System.out.println("end!!");
	}


		
	
}
