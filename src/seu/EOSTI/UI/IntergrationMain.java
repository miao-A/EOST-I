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

import seu.EOSTI.ASTVisitor.ExtendsRequestor;
import seu.EOSTI.DBConnect.ChangeabilityConnector;
import seu.EOSTI.DBConnect.ClassChangeabilityConnector;
import seu.EOSTI.DBConnect.DBConnector;
import seu.EOSTI.DBConnect.ExtensibilityConnector;
import seu.EOSTI.DBConnect.ProjectInfoConnector;
import seu.EOSTI.Parser.ChangeComparator;
import seu.EOSTI.Parser.Compatibility;
import seu.EOSTI.Parser.ProjectParser;
import seu.EOSTI.Parser.ReadFile;



public class IntergrationMain {
		
	public static void main(String args[]) {
		
		String projectPath = "D:/ProjectOfHW/src";
		String projectName = "jdk";
		String version = "1.7.0";

		
		ProjectParser projectParser = new ProjectParser(projectPath,projectName,version);
//		projectParser.parser();
		projectParser.runDectors();
	}
	
}
