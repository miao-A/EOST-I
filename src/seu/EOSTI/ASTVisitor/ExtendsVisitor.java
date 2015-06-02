package seu.EOSTI.ASTVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javassist.compiler.ast.Visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ExtendsVisitor extends ASTVisitor{

	private static Map<String, List<String>> map = new HashMap<String, List<String>>();
	
	public boolean visit(TypeDeclaration node){
		
		//classString = node.getName().toString();
		System.out.println("class Declaration: ");
		System.out.println(node.getName().toString());
		if (node.getSuperclassType()!=null) {
			System.out.println(node.getSuperclassType().toString());
			if(map.containsKey(node.getSuperclassType().toString())){
				map.get(node.getSuperclassType().toString()).add((node.getName().toString()));
			}else {
				List<String> list = new LinkedList<>();
				list.add(node.getName().toString());
				map.put(node.getSuperclassType().toString(),list);
			}
		}		
		return true;
	}
	
	public void endVisit(TypeDeclaration node){
		
		//classString = node.getName().toString();

		System.out.println("endTTTT");

	}
	
}
