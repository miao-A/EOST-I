package seu.EOSTI.ASTVisitor;

import java.text.DecimalFormat;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.eclipse.jface.bindings.Trigger;

import seu.EOSTI.ASTParser.AstUnit;
import seu.EOSTI.ASTParser.InfoOfExtensibility;

public class ExtensibilityRequestor extends FileASTRequestor {

	private ExtensibilityVisitor visitor = new ExtensibilityVisitor();
/*	private ProjectElement project;
	private static Collection<ProjectElement> projectCollection;
	private String sourceFilePath;

	public Requestor(String projName) {
		project = new ProjectElement(projName);
	}
*/
	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		//this.sourceFilePath = sourceFilePath;
		
		CompilationUnit compilationUnit = AstUnit.getCompilationUnit(sourceFilePath);
//		System.out.println(sourceFilePath);		
//		ExtensibilityVisitor visitor = new ExtensibilityVisitor();
		compilationUnit.accept(visitor);

//		updateProject(visitor, ast);
	
		super.acceptAST(sourceFilePath, ast);
	}

	public InfoOfExtensibility showInfoOfExitensibily() {
		// TODO Auto-generated method stub
		System.out.print("NumOfInter: "+getNumOfInter());
		System.out.print("\tNumOfClass: "+getNumOfClass());
		double ratioOfInterface = 100.0*getNumOfInter()/getNumOfClass();
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.printf("  RatioOfInter: "+df.format(ratioOfInterface));
		InfoOfExtensibility info = new InfoOfExtensibility(getNumOfInter(),getNumOfClass(),ratioOfInterface);
		
		System.out.println();
		return info;
		

	}
	
	public int getNumOfInter(){
		return visitor.getNumOfInter();
	}
	
	public int getNumOfClass(){
		return visitor.getNumOfClass();
	}
	
	private void updateProject(ExtensibilityVisitor visitor, CompilationUnit ast) {
/*		project.setNumOfLCO(project.getNumOfLCO()
				+ visitor.getTotalNumberOfLOC());
		project.setNumOfClass(project.getNumOfClass()
				+ visitor.getTotalNumberOfClass());
		project.setNumOfMethods(project.getNumOfMethods()
				+ visitor.getTotalNumberOfMethod());

		String packageName = this.getPacakgeFullyQuallifedName(ast);
		if (project.getPackageNameList().contains(packageName) == false) {
			project.addPacakge(packageName);
		}

		List<Object> smellList = visitor.getDetectedSmellList();
		if (smellList.size() > 0) {
			for (int i = 0; i < smellList.size(); i++) {
				Object object = smellList.get(i);
				if (object instanceof DataClass) {
					// DataClass
					DataClass dataClass = (DataClass) object;
					project.addDataClasslList(dataClass);
				} else if (object instanceof LargeClass) {
					// LargeClass
					LargeClass largeClassSmell = (LargeClass) object;
					project.addLargeClassList(largeClassSmell);
				} else if (object instanceof LongMethod) {
					// LongMethod
					LongMethod longMethodSmell = (LongMethod) object;
					project.addLongMethodlList(longMethodSmell);
				} else if (object instanceof LongParameterList) {
					// LongParameterList
					LongParameterList longParaListSmell = (LongParameterList) object;
					project.addLongParameterList(longParaListSmell);
				} else if (object instanceof MiddleMan) {
					// MiddleMan
					MiddleMan middleManSmell = (MiddleMan) object;
					project.addMiddleManList(middleManSmell);
				}
			}
		}

		if (isExistedProject(project.getProjectPath()) == true) {
			ProjectElement existedProject = getProject(project.getProjectPath());
			projectCollection.remove(existedProject);
			projectCollection.add(project);
		} else {
			projectCollection.add(project);
		}*/
	}



/*	private boolean isExistedProject(String name) {
		boolean isExisted = false;
		if (projectCollection.size() > 0) {
			for (Object obj : projectCollection) {
				if (obj instanceof ProjectElement) {
					ProjectElement p = (ProjectElement) obj;
					if (p.getProjectPath() == name) {
						isExisted = true;
					}
				}
			}
		}
		return isExisted;
	}*/

/*	private String getPacakgeFullyQuallifedName(CompilationUnit unit) {
		String fullName = "Null_PackageName";
		if (unit.getPackage() != null) {
			fullName = unit.getPackage().getName().getFullyQualifiedName();
		} else {
			StringBuffer buffer = new StringBuffer();
			buffer.append("ErrorInfor:" + System.getProperty("line.separator"));
			buffer.append(sourceFilePath);
			buffer.append(" has NO package name.");
			Statistics.catchError(buffer.toString());
		}
		return fullName;
	}

	public ProjectElement getProject(String name) {
		ProjectElement pointer = null;
		for (Object obj : projectCollection) {
			if (obj instanceof ProjectElement) {
				ProjectElement p = (ProjectElement) obj;
				if (p.getProjectPath() == name) {
					pointer = p;
					break;
				}
			}

		}
		return pointer;
	}*/

	// this method is very important
/*	public static void setProjectCollection() {
		projectCollection = new ArrayList<ProjectElement>();
	}

	public static Collection<ProjectElement> getProjectCollection() {
		return projectCollection;
	}
*/
}
