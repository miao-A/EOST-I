package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.Modifier;



public class TypeModel {
	
	private String packageName = null;
	private String className;	
	private boolean INTERFACE = false;
	
	private List<String> superInterfaceTypes = new LinkedList<>();
	private String superClass;
	private JModifier modifier = new JModifier();
	
	private List<FieldModel> fieldModels = new LinkedList<>();
	private List<MethodModel> methodModels = new LinkedList<>();
	private List<ConstructorModel> constructorModels = new LinkedList<>();
	private List<TypeModel> enumClassModels = new LinkedList<>();
	private List<TypeModel> innerClassModels = new LinkedList<>();

	public void setPackage(String name){
		packageName = name;
	}
	
	public String getPackage(){
		return packageName;
	}
	
	public void setClassName(String name){
		className = name;
	}
	
	public String getClassName(){
		return className;
	}
	
	public void addInnerClass(TypeModel typeModel) {
		innerClassModels.add(typeModel);		
	}
	
	public void setModifier(JModifier jModifier){
		modifier = jModifier;
	}
	
	public JModifier getModifier(){
		return modifier;
	}

	
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}
	
	public String getSuperClass() {
		return superClass;
	}

	public List<String> getSuperInterfaceTypes() {
		return superInterfaceTypes;
	}

	public void setSuperInterfaceType(String superInterfaceType) {
		this.superInterfaceTypes.add(superInterfaceType);
	}

	public boolean isINTERFACE() {
		return INTERFACE;
	}

	public void setINTERFACE(boolean iNTERFACE) {
		INTERFACE = iNTERFACE;
	}

	public List<FieldModel> getFieldModels() {
		return fieldModels;
	}

	public void setFieldModels(List<FieldModel> fieldModels) {
		for (FieldModel fieldModel : fieldModels) {
			this.fieldModels.add(fieldModel);
		}
	}	


}
