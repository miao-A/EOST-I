package seu.EOSTI.Parser;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import seu.EOSTI.ASTVisitor.ComponentRequertor;
import seu.EOSTI.Model.TypeModel;

public class ComponentParser {

	private String oldPathOfComponet;
	private String newPathOfComponet;
	private List<TypeModel> typeModels;

	private  ASTParser parser;

	
//	private static Vector<InfoOfExtensibility> vec = new Vector<>();
	
	public  ComponentParser(String oldPathOfComponet,String newPathOfComponet) {
		// TODO Auto-generated constructor stub
		this.oldPathOfComponet = oldPathOfComponet;
		this.newPathOfComponet = newPathOfComponet;		
	}
	
	public void parser()  {
		// create a AST parser

		parser = ASTParser.newParser(AST.JLS4);
	
		Map<String,String> complierOptions= JavaCore.getDefaultOptions();
		complierOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
		parser.setCompilerOptions(complierOptions);
		// set the environment for the AST parsers
		//String libPath = pathOfLib;
		/*ReadFile readFile = new ReadFile(pathOfProject);
		
		List<String> jarfilelist = readFile.readJarFiles();		
		String[] jarpathEntries = jarfilelist.toArray(new String[jarfilelist.size()]);
		
		
		List<String> javafilelist = readFile.readJavaFiles();		
//		String[] sourcepathEntries = javafilelist.toArray(new String[javafilelist.size()]);
		String[] sourcepathEntries = {pathOfProject};*/
		
		//jarpathEntries为项目依赖的jar包，sourcepathEntries为项目中java文件
//		parser.setEnvironment(classpathEntries, sourcepathEntries, null, true);
		parser.setEnvironment(null, null, null, true);
		
		// enable binding	
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		ComponentRequertor componentRequertor = new ComponentRequertor();
		ReadFile readoldFile = new ReadFile(oldPathOfComponet);
		
		List<String> oldfilelist = readoldFile.readJavaFiles();
		String[] sourceFilePaths = oldfilelist.toArray(new String[oldfilelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], componentRequertor, null);
		
	}	

}
