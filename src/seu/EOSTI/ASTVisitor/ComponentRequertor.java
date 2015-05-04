package seu.EOSTI.ASTVisitor;

import java.util.ArrayList;




import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import seu.EOSTI.Model.AbstractTypeModel;
import seu.EOSTI.Model.TypeModel;

public class ComponentRequertor extends FileASTRequestor {


	private List<AbstractTypeModel> typeModels;
	
	public ComponentRequertor(){
		typeModels = new LinkedList<>();
	}	

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		ComponentVisitor visitor = new ComponentVisitor();
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
		typeModels.add(visitor.getTypeModel());
	}
	
	public List<AbstractTypeModel> getTypeModels(){
		return typeModels;
	}
	

}
