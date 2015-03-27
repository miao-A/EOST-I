package seu.EOSTI.ASTVisitor;

import java.text.DecimalFormat;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import seu.EOSTI.Parser.AstUnit;

public class ChangeabilityRequestor extends FileASTRequestor {
	
	private ChangeabilityVisitor visitor = new ChangeabilityVisitor();

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
	
		CompilationUnit compilationUnit = AstUnit.getCompilationUnit(sourceFilePath);
		compilationUnit.accept(visitor);

		super.acceptAST(sourceFilePath, ast);
	}

	public void ShowInfoOfChangeability() {
		// TODO Auto-generated method stub
		
	}
	
	

}