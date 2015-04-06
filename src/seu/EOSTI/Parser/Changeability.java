package seu.EOSTI.Parser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTParser;

import seu.EOSTI.ASTVisitor.ChangeabilityOfClassRequestor;
import seu.EOSTI.ASTVisitor.ChangeabilityRequestor;
import seu.EOSTI.ASTVisitor.ExtensibilityRequestor;

public class Changeability {

	private ChangeabilityRequestor changeabilityRequestor = new ChangeabilityRequestor();
	private ChangeabilityOfClassRequestor changeabilityOfClassRequestor = new ChangeabilityOfClassRequestor();
	
	
	public Changeability(ASTParser parser, String pathOfProject){
		ReadFile readFile = new ReadFile(pathOfProject);
		List<String> filelist = readFile.readJavaFiles();
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], changeabilityRequestor, null);
		
//		extensibilityRequestor.ShowInfoOfExitensibily();
	}
	
	
	
	public void showInfo(){		
		System.out.println();
		
	}


	
	
}
