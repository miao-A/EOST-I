package seu.EOSTI.Parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import seu.EOSTI.ASTVisitor.ComponentRequertor;
import seu.EOSTI.Model.AbstractClassModel;
import seu.EOSTI.Model.ClassComparator;
import seu.EOSTI.Model.ClassModel;

public class ComponentParser {

	private String oldPathOfComponet;
	private String newPathOfComponet;
	private List<AbstractClassModel> changeRecoder;
	
//	private static Vector<InfoOfExtensibility> vec = new Vector<>();
	
	public  ComponentParser(String oldPathOfComponet,String newPathOfComponet) {
		// TODO Auto-generated constructor stub
		this.oldPathOfComponet = oldPathOfComponet;
		this.newPathOfComponet = newPathOfComponet;
		changeRecoder = new LinkedList<>();
	}
	
	
	
	public void parser()  {	
				
		Compatibility csModel = new Compatibility(oldPathOfComponet,newPathOfComponet);
		csModel.getTypeChangeRecoders();
	}	
	
	public List<AbstractClassModel> parserComponet(String pathOfComponet)  {
		// create a AST parser
		ASTParser parser;
		parser = ASTParser.newParser(AST.JLS4);
	
		Map<String,String> complierOptions= JavaCore.getDefaultOptions();
		complierOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
		parser.setCompilerOptions(complierOptions);
		//parser.setEnvironment(null, null, null, true);
		
		// enable binding	
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);	
		
		
		
		ComponentRequertor componentRequertor = new ComponentRequertor();
		ReadFile readFile = new ReadFile(pathOfComponet);	
		
		//////////////////////////////////////////////////////////////////////
		List<String> jarfilelist = readFile.readJarFiles();		
		String[] jarpathEntries = jarfilelist.toArray(new String[jarfilelist.size()]);
//		String[] jarpathEntries = {pathOfProject};
		
		List<String> javafilelist = readFile.readJavaFiles();		
//		String[] sourcepathEntries = javafilelist.toArray(new String[javafilelist.size()]);
		String[] sourcepathEntries = {pathOfComponet};
		//jarpathEntries为项目依赖的jar包，sourcepathEntries为项目中java文件
		parser.setEnvironment(jarpathEntries, sourcepathEntries, null, true);
		/////////////////////////////////////////////////////////////////////////
		
		List<String> filelist = readFile.readJavaFiles();
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], componentRequertor, null);	
		
		return componentRequertor.getTypeModels();
	}	

}
