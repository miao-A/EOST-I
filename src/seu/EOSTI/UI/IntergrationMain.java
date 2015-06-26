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
/*	ArrayList<String> list = new ArrayList<String>();
	list.add("3.4");
	list.add("3.5");*/
	for (int i = 1; i < list.size(); i++) {
		System.out.println(list.get(i-1)+" compare with " + list.get(i));

		ClassChangeabilityConnector preProject = new ClassChangeabilityConnector(project, list.get(i-1));
		ClassChangeabilityConnector postProject = new ClassChangeabilityConnector(project, list.get(i));
				
		HashMap<String,ArrayList<String>> preHashMap = new HashMap<>();
		HashMap<String,ArrayList<String>> postHashMap = new HashMap<>();
		
		for (String packageName : preProject.getpackageName()) {			
//			System.out.println("packageName:"+packageName);
			ArrayList<String> exportlist = preProject.packageAffernetCouplingslist(packageName);
//			System.out.println("ca:"+importlist.size());
			for (String string : exportlist) {
				exportlist.set(exportlist.indexOf(string), string+"&export");
			}			
			
			ArrayList<String> importlist = preProject.packageEffernetCouplingslist(packageName);
//			System.out.println("ce:"+exportlist.size());
			for (String string : importlist) {
				importlist.set(importlist.indexOf(string), string+"&import");
			}
			exportlist.addAll(importlist);
			preHashMap.put(packageName, exportlist);		
		}
				
		for (String packageName : postProject.getpackageName()) {
//			System.out.println("packageName:"+packageName);
			ArrayList<String> exportlist = postProject.packageAffernetCouplingslist(packageName);
//			System.out.println("ca:"+importlist.size());
			for (String string : exportlist) {
				exportlist.set(exportlist.indexOf(string), string+"&export");
			}			
			
			ArrayList<String> importlist = postProject.packageEffernetCouplingslist(packageName);
//			System.out.println("ce:"+exportlist.size());
			for (String string : importlist) {
				importlist.set(importlist.indexOf(string), string+"&import");
			}
			exportlist.addAll(importlist);
			postHashMap.put(packageName, exportlist);
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
		for (String packagename : prelist) {
			if (!postlist.contains(packagename)) {
				String[] string = packagename.split("&");
				String keystring = "-"+string[string.length-1];

				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> packageList = new LinkedList<>();
					packageList.add(string[0]);
					map.put(keystring, packageList);
				}
				
			}else {
				String[] string = packagename.split("&");
				String keystring = string[string.length-1];
				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> packageList = new LinkedList<>();
					packageList.add(string[0]);
					map.put(keystring, packageList);
				}
			}
		}
		
		for (String packagename : postlist) {
			System.out.println(packagename);
			if (!prelist.contains(packagename)) {
//				addClasslList.add(classname);
				String[] string = packagename.split("&");
				String keystring = "+"+string[string.length-1];

				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> packagList = new LinkedList<>();
					packagList.add(string[0]);
					map.put(keystring, packagList);
				}
			}
		}		
		
		return map;
	}	
	
}
