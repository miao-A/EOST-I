package seu.EOSTI.ASTVisitor;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
import seu.EOSTI.DBConnect.ClassChangeabilityConnector;


public class ChangeabilityOfClassVisitor extends ASTVisitor{
	

	private String packageString;
	private String classString;
	private String methodDeclaString;
	private String methodInvoString;

	private HashSet<String> importStrings;
	
	
	public ChangeabilityOfClassVisitor() {
		// TODO Auto-generated constructor stub
		importStrings = new HashSet<String>();
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
		String fullString = binding.getQualifiedName();
		String importpackageName = binding.getPackage().getName();
		String importClassName = fullString.substring(importpackageName.length()+1);
		if (importClassName.contains(".")) {
			importClassName = importClassName.substring(0, importClassName.indexOf('.'));
		}
		
		importStrings.add(importpackageName+"$"+importClassName);
	/*
		System.out.println("***********************************SimpleType's packageName:"+packageName);
		importPackageStrings.add(packageName);*/
		
		return true;
	}
	
	public boolean visit(MethodInvocation node){
		IMethodBinding binding =  (IMethodBinding) node.getName().resolveBinding();
		
		String fullString = binding.getDeclaringClass().getQualifiedName();
		String importpackageName = binding.getDeclaringClass().getPackage().getName();
		String importClassName = fullString.substring(importpackageName.length()+1);
		if (importClassName.contains(".")) {
			importClassName = importClassName.substring(0, importClassName.indexOf('.'));
		}
		
		importStrings.add(importpackageName+"$"+importClassName);
		return true;
	}

	public void endVisit(CompilationUnit node){
		//可用于数据库插入，数据库建成后上述get方法可删除
	
		System.out.println("----------------------------------------------------------");
		System.out.println("package "+ packageString );

		ClassChangeabilityConnector connector = new ClassChangeabilityConnector();
		for (String string : importStrings) {
			
			int index = string.indexOf('$');
			String ipn = string.substring(0, index);
			String icn = string.substring(index+1);
			
			if (packageString.equals(ipn)&&classString.equals(icn)) {
				continue;
			}
			
			connector.importNameUpatedate(packageString, classString, ipn, icn, "jEditor", "0.2.1");
		}
		
		System.out.println("----------------------------------------------------------");		
		importStrings.clear();
	}
}
