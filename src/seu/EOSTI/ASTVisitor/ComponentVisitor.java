package seu.EOSTI.ASTVisitor;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import seu.EOSTI.Model.ClassType;
import seu.EOSTI.Model.TypeModel;

public class ComponentVisitor extends ASTVisitor {

	private TypeModel typeModel;
	
	public boolean visit(TypeDeclaration node){
		//内部类或匿名类
		if (node.isMemberTypeDeclaration()) {
			return true;
		}
		
		System.out.println("Type:\t"+node.getName());
		System.out.println(node.getSuperclassType());
		node.superInterfaceTypes();
		TypeDeclaration[] innerTypes = node.getTypes();
		for (TypeDeclaration innerType : innerTypes) {
			typeModel.addInnerClass( innerClassType(innerType));
		}
		

		
		
		return true;
	}
	
	private TypeModel innerClassType(TypeDeclaration type){
		TypeModel typeModel = new TypeModel();
		TypeDeclaration[] innerTypes = type.getTypes();
		for (TypeDeclaration innerType : innerTypes) {
			typeModel.addInnerClass( innerClassType(innerType));
		}
		
		return typeModel;
		
		
		
	}
	
	
	public TypeModel getTypeModel(){
		return typeModel;
	}
	
	
}
