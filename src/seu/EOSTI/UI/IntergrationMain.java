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
		

	/*String oldPath = "D:/ProjectOfHW/jEditor/jEditor0.4.1";
	String newPathOfComponet = "D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";*/
		
/*	String oldPathOfComponet = "D:/ProjectOfHW/junit/junit4.0";
	String newPathOfComponet = "D:/ProjectOfHW/junit/junit4.1";
	Compatibility compatibility = new Compatibility(oldPathOfComponet, newPathOfComponet);
	compatibility.getinfo();*/
	
	/*ComponentParser componentParser = new ComponentParser(oldPathOfComponet, newPathOfComponet);
	componentParser.parser();*/

		
		
	/*String oldPathOfProject = "D:/ProjectOfHW/jEditor/jEditor0.3";
	String newPathOfProject = "D:/ProjectOfHW/jEditor/jEditor0.4";
	ChangeComparator changeComparator = new ChangeComparator(oldPathOfProject, newPathOfProject);
	changeComparator.getinfo();*/
	
		
		
	String project = "jUnit";
	ProjectInfoConnector projectInfoConnector = new ProjectInfoConnector();
	ArrayList<String> list = projectInfoConnector.getVersion(project);
	for (int i = 1; i < list.size(); i++) {
		System.out.println(list.get(i-1)+" compare with " + list.get(i));
		//ChangeabilityConnector preProject = new ChangeabilityConnector(project, list.get(i-1));
		//ChangeabilityConnector postProject = new ChangeabilityConnector(project, list.get(i-1));
		ExtensibilityConnector preProject = new ExtensibilityConnector(project, list.get(i-1));
		ExtensibilityConnector postProject = new ExtensibilityConnector(project, list.get(i));
				
		HashMap<String,ArrayList<String>> preHashMap = new HashMap<>();
		HashMap<String,ArrayList<String>> postHashMap = new HashMap<>();
		
		for (String packageName : preProject.getpackageName()) {			
	//		System.out.println(packageName);
			ArrayList<String> typelist = preProject.packageExtensibilityInfo(packageName);
			preHashMap.put(packageName, typelist);		
		}
				
		for (String packageName : postProject.getpackageName()) {
	//		System.out.println(packageName);
			ArrayList<String> typelist = postProject.packageExtensibilityInfo(packageName);
			postHashMap.put(packageName, typelist);
		}
		
		Iterator<String> preIterator = preHashMap.keySet().iterator();
		Iterator<String> postIterator = postHashMap.keySet().iterator();
	//	HashMap<String, HashMap<String, String>>
		while (preIterator.hasNext()) {		
			String packageName = preIterator.next();
			
			if (postHashMap.containsKey(packageName)) {
				ArrayList<String> prelist = preHashMap.get(packageName);
				ArrayList<String> postlist = postHashMap.get(packageName);
				if (prelist.containsAll(postlist)) {
					preIterator.remove();
					postHashMap.remove(packageName);	
				}else {
					System.out.println(packageName);
					System.out.println("something change");
					HashMap<String, List<String>> diffMap = diffInPackage(prelist,postlist);
				}		
					
			}		
		}
		
	}
	
	System.out.println("end!!");
	}
	
	private static HashMap<String, List<String>> diffInPackage(ArrayList<String> prelist,ArrayList<String> postlist) {
	/*	List<String> removedClassList =new LinkedList();
		List<String> addClasslList = new LinkedList<>();*/
		HashMap<String, List<String>> map = new HashMap<>();		
		for (String classname : prelist) {
			if (!postlist.contains(classname)) {
//				removedClassList.add(classname);
				String[] string = classname.split("&");
				String keystring = "-"+string[string.length-1];

				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> classList = new LinkedList<>();
					classList.add(string[0]);
					map.put(keystring, classList);
				}
				
			}else {
				String[] string = classname.split("&");
				String keystring = string[string.length-1];
				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> classList = new LinkedList<>();
					classList.add(string[0]);
					map.put(keystring, classList);
				}
			}
		}
		
		for (String classname : postlist) {
			if (!prelist.contains(classname)) {
//				addClasslList.add(classname);
				String[] string = classname.split("&");
				String keystring = "+"+string[string.length-1];

				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> classList = new LinkedList<>();
					classList.add(string[0]);
					map.put(keystring, classList);
				}
			}
		}		
		
		return map;
	}


		
	
}
