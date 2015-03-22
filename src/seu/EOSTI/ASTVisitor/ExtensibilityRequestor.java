package seu.EOSTI.ASTVisitor;

import java.text.DecimalFormat;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.eclipse.jface.bindings.Trigger;

import seu.EOSTI.ASTParserBACKUP.AstUnit;
import seu.EOSTI.ASTParserBACKUP.InfoOfExtensibility;

public class ExtensibilityRequestor extends FileASTRequestor {

	private ExtensibilityVisitor visitor = new ExtensibilityVisitor();

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
	
		CompilationUnit compilationUnit = AstUnit.getCompilationUnit(sourceFilePath);
		compilationUnit.accept(visitor);

		super.acceptAST(sourceFilePath, ast);
	}

	public void ShowInfoOfExitensibily() {
		// TODO Auto-generated method stub
		
		System.out.print("NumOfInter: "+getNumOfInter());
		System.out.print("\tNumOfAbstract: "+getNumOfAbstract());
		System.out.print("\tNumOfClass: "+getNumOfClass());
		double ratioOfInterface = 100.0*(getNumOfInter()+getNumOfAbstract())/getNumOfClass();
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.printf("  RatioOfInter: "+df.format(ratioOfInterface));
		System.out.println();
		
		
		
	}
	
	public int getNumOfInter(){
		return visitor.getNumOfInter();
	}
	
	public int getNumOfClass(){
		return visitor.getNumOfClass();
	}
	
	public int getNumOfAbstract(){
		return visitor.getNumOfAbstract();
	}
	

}
