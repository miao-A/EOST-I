package seu.EOSTI.ASTVisitor;


import java.text.DecimalFormat;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;



public class ExtendsRequestor extends FileASTRequestor {


	
	public ExtendsRequestor(){

	}	
	
	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		ExtendsVisitor visitor = new ExtendsVisitor();
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
	}

	public void ShowInfoOfChangeability() {
		// TODO Auto-generated method stub
		
	}
	
	

}
