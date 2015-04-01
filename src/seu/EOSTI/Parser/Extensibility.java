package seu.EOSTI.Parser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import seu.EOSTI.ASTVisitor.ExtensibilityRequestor;

public class Extensibility {
	private int numOfInterface;
	private int numOfAbstract;
	private int numOfClass;
	private double ratioOfInterface;
	
	private String projectNameString;
	private String versionString;
	
	
	
	public Extensibility(ASTParser parser, String pathOfProject,String projectNameString,String versionString){
		ExtensibilityRequestor extensibilityRequestor = new ExtensibilityRequestor(projectNameString,versionString);

		this.projectNameString = projectNameString;
		this.versionString = versionString;
		
		ReadFile readFile = new ReadFile(pathOfProject);
		List<String> filelist = readFile.readJavaFiles();
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], extensibilityRequestor, null);

//		extensibilityRequestor.ShowInfoOfExitensibily();
		
/*		numOfAbstract = extensibilityRequestor.getNumOfAbstract();
		numOfInterface = extensibilityRequestor.getNumOfInter();
		numOfClass = extensibilityRequestor.getNumOfClass();	
		ratioOfInterface = 100.0*(numOfInterface+numOfAbstract)/numOfClass;*/
	}
	
/*	private void setValue() {
		numOfAbstract = extensibilityRequestor.getNumOfAbstract();
		numOfInterface = extensibilityRequestor.getNumOfInter();
		numOfClass = extensibilityRequestor.getNumOfClass();	
		ratioOfInterface = 100.0*(numOfInterface+numOfAbstract)/numOfClass;
	}
	*/
	
	
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
		
		String string = "Interface Class:\t"+numOfInterface+"Abstract Class:\t"+numOfAbstract+"\ttotal Class:\t"+ numOfClass;
		infoList.add(string);
/*		infoList.add("Interface Class:\t"+numOfInterface);
		infoList.add("Abstract Class:\t"+numOfAbstract);
		infoList.add("total Class:\t"+ numOfClass);*/
		double ratioOfInterface = 100.0*(numOfInterface+numOfAbstract)/numOfClass;
		DecimalFormat df = new DecimalFormat("#.00");
		infoList.add("Extensibility:\t"+df.format(ratioOfInterface));
		return infoList;
	}
	
	public double getExtensibility(){
		return ratioOfInterface;
	}
	
	

}
