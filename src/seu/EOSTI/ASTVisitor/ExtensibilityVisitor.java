package seu.EOSTI.ASTVisitor;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ExtensibilityVisitor extends ASTVisitor {
	private static int numOfInter=0;
	private static int numOfClass=0;
	
	public ExtensibilityVisitor(){
		numOfInter=0;
		numOfClass=0;
	}
	
	public boolean visit(TypeDeclaration node){
		if (node.isInterface()) {
//			System.out.println("Class:\t" + node.getName());
			++numOfInter;
		}
		++numOfClass;
		return true;
	}
	

	public int getNumOfInter(){
		return numOfInter;
	}
	
	public int getNumOfClass(){
		return numOfClass;
	}
}

