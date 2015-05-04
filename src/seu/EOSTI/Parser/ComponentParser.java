package seu.EOSTI.Parser;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import seu.EOSTI.ASTVisitor.ComponentRequertor;
import seu.EOSTI.Model.AbstractTypeModel;
import seu.EOSTI.Model.ClassComparator;
import seu.EOSTI.Model.TypeModel;

public class ComponentParser {

	private String oldPathOfComponet;
	private String newPathOfComponet;
	private List<AbstractTypeModel> changeRecoder;

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
		parser.setEnvironment(null, null, null, true);
		
		// enable binding	
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		
		ComponentRequertor oldComponentRequertor = new ComponentRequertor();
		ReadFile readoldFile = new ReadFile(oldPathOfComponet);		
		List<String> oldfilelist = readoldFile.readJavaFiles();
		String[] sourceFilePaths = oldfilelist.toArray(new String[oldfilelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], oldComponentRequertor, null);
		
		ComponentRequertor newComponentRequertor = new ComponentRequertor();
		ReadFile readnewFile = new ReadFile(newPathOfComponet);		
		List<String> newfilelist = readnewFile.readJavaFiles();
		sourceFilePaths = newfilelist.toArray(new String[newfilelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], newComponentRequertor, null);
		
		oldComponentRequertor.getTypeModels();
		newComponentRequertor.getTypeModels();
		
		ClassComparator cStatusModel = new ClassComparator(oldComponentRequertor.getTypeModels(),newComponentRequertor.getTypeModels());
		
	}	

}
