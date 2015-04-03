package seu.EOSTI.ASTVisitor;


import java.util.ArrayList;
import java.util.Collection;

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


public class ChangeabilityVisitor extends ASTVisitor{
	

	private String packageString;
	private String classString;
	private String methodDeclaString;
	private String methodInvoString;
	private ArrayList<String> importDeclarationList;
	private Collection<String> importPackageStrings;
	
	
	public ChangeabilityVisitor() {
		// TODO Auto-generated constructor stub
		importDeclarationList = new ArrayList<String>();
	}
	

	public boolean visit(ImportDeclaration node){
		String[] string = node.getName().toString().split("\\.");
		String importString = new String();
		for (int i=0;i<string.length-1; i++) {
			importString += string[i];
			if (i != string.length-2) {
				importString +=".";
			}
		}
		importDeclarationList.add(importString);
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
/*		String packageName = binding.getPackage().getName();
		System.out.println("***********************************SimpleType's packageName:"+packageName);
		importPackageStrings.add(packageName);*/
		
		return true;
	}

	public void endVisit(CompilationUnit node){
		//可用于数据库插入，数据库建成后上述get方法可删除
	
		System.out.println("----------------------------------------------------------");
		System.out.println("package "+ packageString );
		for (String string : importDeclarationList) {
			System.out.println("package "+ packageString + " have package "+string);
		}
		System.out.println("----------------------------------------------------------");

		importDeclarationList.clear();
	}
}
