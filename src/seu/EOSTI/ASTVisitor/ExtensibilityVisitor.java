package seu.EOSTI.ASTVisitor;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import seu.EOSTI.DBConnect.DBConnector;

public class ExtensibilityVisitor extends ASTVisitor {
	private static int numOfInter=0;
	private static int numOfClass=0;
	private static int numOfAbstract=0;
	private String packageString;
	private String classString;
	private String classType;
	
	public ExtensibilityVisitor(){
		numOfInter=0;
		numOfClass=0;
		numOfAbstract=0;
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
	System.out.println("package "+ packageString + " have class "+classString);
	
	DBConnector connector = new DBConnector();
	connector.extendsibilityUpdateStatement(packageString, classString, "test", classType);
	
	}
}





