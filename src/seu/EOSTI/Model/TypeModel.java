package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.Modifier;



public class TypeModel {
	
	private String packageName = null;
	private String className;	
	private ClassType classType;

	
	private List<String> superInterfaceTypes = new LinkedList<>();
	private String superClass;
	/*private JModifier<AccessModifier> accessModifier;
	private JModifier<AbstractModifier> abstractModifier;
	private JModifier<FinalModifier> finalModifier;
	private JModifier<StaticModifier> staticModifier;*/
	
	private JModifier modifier = new JModifier();
	
	private List<FieldModel> fieldModels = new LinkedList<>();
	private List<MethodModel> methodModels = new LinkedList<>();
	private List<ConstructorModel> constructorModels = new LinkedList<>();
	private List<TypeModel> enumClassModels = new LinkedList<>();
	private List<TypeModel> innerClassModels = new LinkedList<>();

	public void setPackage(String name){
		packageName = name;
	}
	
	public void setClassName(String name){
		className = name;
	}
	
	
	
	public void addInnerClass(TypeModel typeModel) {
		innerClassModels.add(typeModel);		
	}
	
	public void setModifier(JModifier jModifier){
		modifier = jModifier;
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

	public void setSuperInterfaceTypes(List<String> superInterfaceTypes) {
		this.superInterfaceTypes = superInterfaceTypes;
	}

}
