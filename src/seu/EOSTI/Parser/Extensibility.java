package seu.EOSTI.Parser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import seu.EOSTI.ASTVisitor.ExtensibilityRequestor;

public class Extensibility {
	private ExtensibilityRequestor extensibilityRequestor = new ExtensibilityRequestor();
	private int numOfInterface;
	private int numOfAbstract;
	private int numOfClass;
	
	
	
	public Extensibility(ASTParser parser, String pathOfProject){
		ReadFile readFile = new ReadFile(pathOfProject);
		List<String> filelist = readFile.readJavaFiles();
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], extensibilityRequestor, null);
		setValue();
//		extensibilityRequestor.ShowInfoOfExitensibily();
	}
	
	private void setValue() {
		numOfAbstract = extensibilityRequestor.getNumOfAbstract();
		numOfInterface = extensibilityRequestor.getNumOfInter();
		numOfClass = extensibilityRequestor.getNumOfClass();	
	}
	
	public void showInfo(){
		System.out.print("NumOfInter: "+numOfInterface);
		System.out.print("\tNumOfAbstract: "+numOfAbstract);
		System.out.print("\tNumOfClass: "+ numOfClass);
		double ratioOfInterface = 100.0*(numOfInterface+numOfAbstract)/numOfClass;
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.printf("  RatioOfInter: "+df.format(ratioOfInterface));
		System.out.println();
	}

	public List<String> getInfo(){
		List<String> infoList = new ArrayList<String>();
		infoList.add("NumOfInter: "+numOfInterface);
		infoList.add("\tNumOfAbstract: "+numOfAbstract);
		infoList.add("\tNumOfClass: "+ numOfClass);
		double ratioOfInterface = 100.0*(numOfInterface+numOfAbstract)/numOfClass;
		DecimalFormat df = new DecimalFormat("#.00");
		infoList.add("  RatioOfInter: "+df.format(ratioOfInterface));
		return infoList;
	}
	
	

}
