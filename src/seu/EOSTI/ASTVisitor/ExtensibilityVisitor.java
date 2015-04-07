package seu.EOSTI.ASTVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import seu.EOSTI.DBConnect.DBConnector;
import seu.EOSTI.DBConnect.ExtensibilityConnector;

public class ExtensibilityVisitor extends ASTVisitor {
	private static int numOfInter=0;
	private static int numOfClass=0;
	private static int numOfAbstract=0;
	private String packageString;
	private String classString;
	private String classType;
	private ArrayList<String> classNameList;
	private ArrayList<String> classTypeList;
	
	private String projectName;
	private String version;
	
	private static int numofmethod=0;
	
	
	
	public ExtensibilityVisitor(String projectName,String version){
		numOfInter=0;
		numOfClass=0;
		numOfAbstract=0;
		classNameList = new ArrayList<String>();
		classTypeList = new ArrayList<String>();
		this.projectName = projectName;
		this.version = version;
		
	}
	

	
	
	public boolean visit(PackageDeclaration node) {		
//		System.out.println("PackageName:" + node.getName());
		packageString = node.getName().toString();
		return true;		
	}

	
	
	public boolean visit(TypeDeclaration node){

		classString = node.getName().toString();

		
		if (node.modifiers().toString().contains("abstract")) {			
//			System.out.println("abstract class"+ node.getName());
			classType = "abstract";
			++numOfAbstract;
		} else if (node.isInterface()) {
//			System.out.println("interface class:"+ node.getName());
			classType = "interface";
			++numOfInter;
		}else {	
			System.out.println("concrete class:" + node.getName());
			classType = "concrete";
			++numOfClass;
		}
		
		classNameList.add(classString);		
		classTypeList.add(classType);		

		return true;
	}
	

	public int getNumOfInter(){
		return numOfInter;
	}
	
	public int getNumOfClass(){
		return numOfClass;
	}
	
	public int getNumOfAbstract(){
		return numOfAbstract;
	}
	
	public void endVisit(CompilationUnit node){
		//可用于数据库插入，数据库建成后上述get方法可删除		
		System.out.println("package "+ packageString + " have class "+classNameList.get(0));
		ExtensibilityConnector connector = new ExtensibilityConnector();
		connector.extendsibilityUpdateStatement(packageString, classNameList.get(0), projectName, version, classTypeList.get(0));
		
		for (int i=1; i<classNameList.size(); i++ ) {
			classNameList.get(i);
			classTypeList.get(i);
			System.out.println("package "+ packageString + " have class "+classNameList.get(i));
			connector.extendsibilityUpdateStatement(packageString, classNameList.get(0)+"$"+classNameList.get(i), projectName, version, classTypeList.get(i));
		}
		
		classNameList.clear();
		classTypeList.clear();
	}
}





