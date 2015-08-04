package seu.EOSTI.ASTVisitor;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import seu.EOSTI.Model.AbstractClassModel;
import seu.EOSTI.Model.UnCompatibilityMIModel;



public class JavaVersionCompatibilityRequestor extends FileASTRequestor {

	private String projectName;
	private String version;
	private List<String> packageList=null;
	List<String> unCompatibilityFiles = new ArrayList<>();
	
	/*public InnerCompatibilityRequestor(String projectName,String version){
		this.projectName = projectName;
		this.version = version;
	}	*/
		
	public JavaVersionCompatibilityRequestor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		JavaVersionCompatibilityVisitor visitor = new JavaVersionCompatibilityVisitor(packageList);
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
		unCompatibilityFiles.addAll(visitor.getuncompatibilityFiles());
	}

	
	public List<String> getuncompatibilityFiles(){
		return unCompatibilityFiles;
	}
	
	public void ShowInfoOfChangeability() {
		// TODO Auto-generated method stub
		
	}
	
	

}
