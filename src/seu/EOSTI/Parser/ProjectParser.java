package seu.EOSTI.Parser;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.ui.internal.model.*;

import seu.EOSTI.ASTVisitor.ExtensibilityRequestor;
import seu.EOSTI.ASTVisitor.ProjectRequestor;


public class ProjectParser {

	private String pathOfProject;
	private  String pathOfLib;
	private  ASTParser parser;
	private String projectName;
	private String version;
	
//	private static Vector<InfoOfExtensibility> vec = new Vector<>();
	
	public  ProjectParser(String pathOfProject,String projectName,String version) {
		// TODO Auto-generated constructor stub
		this.pathOfProject = pathOfProject;
		this.projectName = projectName;
		this.version = version;	
		
	}
	
	public void parser()  {
		// create a AST parser
		parser = ASTParser.newParser(AST.JLS4);
		// enable binding		
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		// set the environment for the AST parsers
		//String libPath = pathOfLib;
		ReadFile readFile = new ReadFile(pathOfProject);
		
		List<String> filelist = readFile.readJarFiles();		
		String[] classpathEntries = filelist.toArray(new String[filelist.size()]);		
		String[] sourcepathEntries = {pathOfProject};
		parser.setEnvironment(classpathEntries, sourcepathEntries, null, true);
		
		// set the compiler option
		Map<String,String> complierOptions= JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, complierOptions);
		parser.setCompilerOptions(complierOptions);

	}
	
	public void getInfoOfProject() {
		System.out.println("InfoOfProject"+pathOfProject);				
	}
	
	public List<String> getExtensibilityInfo(){
		
		Extensibility extensibility = new Extensibility(parser, pathOfProject,projectName,version);
		extensibility.showInfo();
		return extensibility.getInfo();
/*		ReadFile readFile = new ReadFile(pathOfProject);
		List<String> filelist = readFile.readJavaFiles();
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		ExtensibilityRequestor extensibilityRequestor = new ExtensibilityRequestor();
		parser.createASTs(sourceFilePaths,  null, new String[0], extensibilityRequestor, null);
		extensibilityRequestor.ShowInfoOfExitensibily();*/
		
	}
	
	public void getChangeabilityInfo(){		
	Changeability changeability = new Changeability(parser, pathOfProject);
	changeability.showInfo();
	}
	

	
}





