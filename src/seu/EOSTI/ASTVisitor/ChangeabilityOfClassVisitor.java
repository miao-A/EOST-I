package seu.EOSTI.ASTVisitor;



import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

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
	private String projectName;
	private String version;

	private HashSet<String> importStrings;
	
	
	public ChangeabilityOfClassVisitor(String projectName, String version) {
		// TODO Auto-generated constructor stub
		importStrings = new HashSet<String>();
		this.projectName = projectName;
		this.version = version;
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
////�༶������Լ��
/*		binding.getPackage();
		binding.getQualifiedName();*/
		if (binding == null) {
			return true;
		}
		String fullString = binding.getQualifiedName();
		String importpackageName = binding.getPackage().getName();
		String importClassName = fullString.substring(importpackageName.length()+1);
		if (importClassName.contains(".")) {
			importClassName = importClassName.substring(0, importClassName.indexOf('.'));
		}
		
		importStrings.add(importpackageName+"$"+importClassName);
		
		return true;
	}
	
	public boolean visit(MethodInvocation node){
		IMethodBinding binding =  (IMethodBinding) node.getName().resolveBinding();
		if (binding == null) {
			return true;
		}
		String fullString = binding.getDeclaringClass().getQualifiedName();
		String importpackageName = binding.getDeclaringClass().getPackage().getName();
		if(fullString.length()==0){
			return true;
		}
		String importClassName = fullString.substring(importpackageName.length()+1);
		if (importClassName.contains(".")) {
			importClassName = importClassName.substring(0, importClassName.indexOf('.'));
		}
		
		importStrings.add(importpackageName+"$"+importClassName);
		return true;
	}

	public void endVisit(CompilationUnit node){
		//���������ݿ���룬���ݿ⽨�ɺ�����get������ɾ��
	
		System.out.println("----------------------------------------------------------");
		System.out.println("package "+ packageString );

		ClassChangeabilityConnector connector = new ClassChangeabilityConnector(projectName,version);
		for (String string : importStrings) {
			
			int index = string.indexOf('$');
			String ipn = string.substring(0, index);
			String icn = string.substring(index+1);
			if (classString == null) {
				continue;
			}

			if (packageString.equals(ipn)) {
				continue;
			}
			
			connector.importNameUpatedate(packageString, classString, ipn, icn);
		}
		
		System.out.println("----------------------------------------------------------");		
		importStrings.clear();
	}
}
