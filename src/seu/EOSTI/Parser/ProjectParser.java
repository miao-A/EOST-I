package seu.EOSTI.Parser;


import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

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
	
		Map<String,String> complierOptions= JavaCore.getDefaultOptions();
		complierOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
		parser.setCompilerOptions(complierOptions);
		// set the environment for the AST parsers
		//String libPath = pathOfLib;
		ReadFile readFile = new ReadFile(pathOfProject);
		
		List<String> jarfilelist = readFile.readJarFiles();		
		String[] jarpathEntries = jarfilelist.toArray(new String[jarfilelist.size()]);
		
		
		List<String> javafilelist = readFile.readJavaFiles();		
//		String[] sourcepathEntries = javafilelist.toArray(new String[javafilelist.size()]);
		String[] sourcepathEntries = {pathOfProject};
		
		//jarpathEntries为项目依赖的jar包，sourcepathEntries为项目中java文件
//		parser.setEnvironment(classpathEntries, sourcepathEntries, null, true);
		parser.setEnvironment(jarpathEntries, sourcepathEntries, null, true);
		
		// enable binding	
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		
	}	

	public void getInfoOfProject() {
		System.out.println("InfoOfProject"+pathOfProject);				
	}
	
	public void getExtensibilityInfo(){
		
		Extensibility extensibility = new Extensibility(parser, pathOfProject,projectName,version);
	
	}
	
	public void getChangeabilityInfo(){		
	Changeability changeability = new Changeability(parser, pathOfProject);
	changeability.showInfo();
	}	
}





