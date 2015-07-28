package seu.EOSTI.ASTVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javassist.compiler.ast.Visitor;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class OuterCompatibilityVisitor extends ASTVisitor{

	private static Map<String, List<String>> map = new HashMap<String, List<String>>();
	
	public boolean visit(CompilationUnit node){
		System.out.println(node.getLength());
/*		Message[] messages = node.getMessages();
		for (Message message : messages) {
			System.out.println(message.getMessage());
		}*/
		
		IProblem[] iProblems = node.getProblems();
		for (IProblem iProblem : iProblems) {
			if (iProblem.isError()) {
				System.out.println(iProblem.getMessage());
			}
			
		}
		return true;
	}
	
	public boolean visit(TypeDeclaration node){
		
		return true;
	}
	
	public boolean visit(MethodInvocation node){
		ITypeBinding iTypeBinding = node.resolveTypeBinding();
		if (iTypeBinding!=null) {
			
			if (iTypeBinding.getBinaryName()!=null) {
				//System.out.println(iTypeBinding.getBinaryName());
				if (iTypeBinding.getBinaryName().equals("java.lang.Class")) {
					System.out.println(node.getName());
					System.out.println(node.getExpression());
					List<?> list = node.arguments();
					for (int i=0;i <list.size();i++) {
						System.out.println(list.get(i));
					}
				}
				
			}

		}
		return true;
		
	}
	
	public void endVisit(TypeDeclaration node){
		
		//classString = node.getName().toString();

		System.out.println("endTTTT");

	}
	
}
