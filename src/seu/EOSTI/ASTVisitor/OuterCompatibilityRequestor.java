package seu.EOSTI.ASTVisitor;


import java.text.DecimalFormat;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;



public class OuterCompatibilityRequestor extends FileASTRequestor {

	private String projectName;
	private String version;
	
	
	public OuterCompatibilityRequestor(String projectName,String version){
		this.projectName = projectName;
		this.version = version;
	}	
	
	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		OuterCompatibilityVisitor visitor = new OuterCompatibilityVisitor();
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
	}

	public void ShowInfoOfChangeability() {
		// TODO Auto-generated method stub
		
	}
	
	

}
