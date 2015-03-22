package seu.EOSTI.ASTVisitor;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import seu.EOSTI.Parser.AstUnit;

public class ProjectRequestor extends FileASTRequestor{
	private ProjectVisitor visitor = new ProjectVisitor();

	public ProjectRequestor(){
		super();
	}
		@Override
		public void acceptAST(String sourceFilePath, CompilationUnit ast) {
			//this.sourceFilePath = sourceFilePath;	
			CompilationUnit compilationUnit = AstUnit.getCompilationUnit(sourceFilePath);

			compilationUnit.accept(visitor);
			
			super.acceptAST(sourceFilePath, ast);
		}
}
