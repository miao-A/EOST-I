package seu.EOSTI.ASTVisitor;


import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

public class ExtensibilityRequestor extends FileASTRequestor {

	private String projectName;
	private String version;
	
	public ExtensibilityRequestor(String projectName,String version){
		this.projectName = projectName;
		this.version = version;
	}	

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		ExtensibilityVisitor visitor = new ExtensibilityVisitor(projectName,version);
/*		CompilationUnit compilationUnit = AstUnit.getCompilationUnit(sourceFilePath);
		compilationUnit.accept(visitor);*/
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
	}

	/*public void ShowInfoOfExitensibily() {
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
	*/

}
