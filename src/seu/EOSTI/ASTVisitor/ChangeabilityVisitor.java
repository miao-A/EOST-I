package seu.EOSTI.ASTVisitor;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import seu.EOSTI.DBConnect.ChangeabilityConnector;


public class ChangeabilityVisitor extends ASTVisitor{
	

	private String packageString;
	private String classString;
	private String methodDeclaString;
	private String methodInvoString;
	private ArrayList<String> importDeclarationList;
	private HashSet<String> importPackageStrings;
	
	
	public ChangeabilityVisitor() {
		// TODO Auto-generated constructor stub
		importDeclarationList = new ArrayList<String>();
		importPackageStrings = new HashSet<String>();

	}
	
	

	public boolean visit(ImportDeclaration node){
/*		if (node.isOnDemand()) {
			importPackageStrings.add(node.getName().toString());
		}else{
			ITypeBinding binding = node.getName().resolveTypeBinding();
			String importString = binding.getPackage().getName();
			importPackageStrings.add(importString);
		}
		*/
//		importDeclarationList.add(importString);
		return true;
	}
	
	public boolean visit(PackageDeclaration node) {		
		System.out.println("PackageName:" + node.getName());
		packageString = node.getName().toString();
		return true;		
	}

	
	public boolean visit(TypeDeclaration node){
		classString = node.getName().toString();
//		System.out.println("class Declaration: "+ classString);		
		return true;
	}

	public boolean visit(SimpleType node){

		ITypeBinding binding = (ITypeBinding) node.getName().resolveBinding();
////类级别耦合性检测
/*		binding.getPackage();
		binding.getQualifiedName();*/
		String importpackageName = binding.getPackage().getName();
		if (!importpackageName.equals(packageString)) {
			importPackageStrings.add(importpackageName);
		}


	/*
		System.out.println("***********************************SimpleType's packageName:"+packageName);
		importPackageStrings.add(packageName);*/
		
		return true;
	}
	
	public boolean visit(MethodInvocation node){
		IMethodBinding binding =  (IMethodBinding) node.getName().resolveBinding();
			String importpackageName = binding.getDeclaringClass().getPackage().getName();
			
			if (!importpackageName.equals(packageString)) {
				importPackageStrings.add(importpackageName);
			}
		return true;
	}

	public void endVisit(CompilationUnit node){
		//可用于数据库插入，数据库建成后上述get方法可删除
	
		System.out.println("----------------------------------------------------------");
		System.out.println("package "+ packageString );
		ChangeabilityConnector connector = new ChangeabilityConnector();
		for (String string : importPackageStrings) {
			connector.importNameUpatedate(packageString, string, "jEditor", "0.2");
			System.out.println("package "+ packageString + " have package "+string);
			System.out.println("Class "+ classString + " import package "+string);
		}
		
		
		System.out.println("----------------------------------------------------------");

/*		ChangeabilityConnector connector = new ChangeabilityConnector();
		for (String string : importDeclarationList) {
			
		}*/
		
		importPackageStrings.clear();
	}
}
