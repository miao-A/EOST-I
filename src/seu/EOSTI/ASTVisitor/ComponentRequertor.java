package seu.EOSTI.ASTVisitor;

import java.util.ArrayList;



import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import seu.EOSTI.Model.TypeModel;

public class ComponentRequertor extends FileASTRequestor {


	private ArrayList<TypeModel> typeModels;
	
	public ComponentRequertor(){
		typeModels = new ArrayList<TypeModel>();
	}	

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		ComponentVisitor visitor = new ComponentVisitor();
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
	}

}
