package seu.EOSTI.ASTVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javassist.bytecode.stackmap.TypeData.ClassName;
import javassist.compiler.ast.Visitor;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import seu.EOSTI.Model.AbstractClassModel;
import seu.EOSTI.Model.MethodModel;
import seu.EOSTI.Model.SingleVariableModel;
import seu.EOSTI.Model.UnCompatibilityMIModel;

public class InnerCompatibilityVisitor extends ASTVisitor{
	
	private List<String> packageList = null;
	private List<UnCompatibilityMIModel> unCompatibilityMIModels = new ArrayList<>();

	private static Map<String, List<String>> map = new HashMap<String, List<String>>();
	
	public InnerCompatibilityVisitor(List<String> packageList) {
		// TODO Auto-generated constructor stub
		this.packageList = packageList;
	}	
	
		
	public boolean visit(CompilationUnit node){
//		System.out.println(node.getLength());
/*		Message[] messages = node.getMessages();
		for (Message message : messages) {
			System.out.println(message.getMessage());
		}*/
		
		IProblem[] iProblems = node.getProblems();
		for (IProblem iProblem : iProblems) {
			if (iProblem.isError()) {
				if(iProblem.getID()==iProblem.ParameterMismatch){					
					//System.out.println("##"+node.getPackage());
					if (packageList.contains(node.getPackage().getName().toString())) {
						UnCompatibilityMIModel unCompatibilityMIModel = new UnCompatibilityMIModel(iProblem.getArguments()[0], 
								iProblem.getArguments()[1], iProblem.getArguments()[2], iProblem.getArguments()[3],
								node.getPackage().getName().toString(), String.valueOf(iProblem.getOriginatingFileName()));
						unCompatibilityMIModels.add(unCompatibilityMIModel);
					}					
				}			
			}
	
		}
		return true;
	}
	
	
	public List<UnCompatibilityMIModel> getUncompatibilityMiModels(){
		return unCompatibilityMIModels;
	}
	
}
