package seu.EOSTI.ASTVisitor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class ChangeabilityVisitor extends ASTVisitor{
	
	private static int numOfInter=0;
	private static int numOfClass=0;
	private static int numOfAbstract=0;
	private String packageString;
	private String classString;
	private String methodDeclaString;
	private String methodInvoString;
	
	public ChangeabilityVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean visit(PackageDeclaration node) {		
//		System.out.println("PackageName:" + node.getName());
		packageString = node.getName().toString();
		return true;		
	}

	
	public boolean visit(TypeDeclaration node){
		classString = node.getName().toString();
	//	System.out.println("class Declaration: "+ classString);		
		return true;
	}
	
	public boolean visit(MethodDeclaration node){
		methodDeclaString = node.getName().toString();
	//	System.out.println("method Declaration: "+ methodInvoString);		
		return true;
	}	
	
		
	public boolean visit(VariableDeclarationFragment node){
		System.out.println("variable Declaration: "+node.getName().toString());

		return true;
	}
	
	
	public boolean visit(MethodInvocation node){
		methodInvoString = node.getName().toString();
		System.out.println("Method Invocation: "+ methodInvoString);
		    
		return true;
	}	
	
	
	public void endVisit(CompilationUnit node){
		//可用于数据库插入，数据库建成后上述get方法可删除
//	System.out.println("package "+ packageString + " have class "+classString);
	}

}
