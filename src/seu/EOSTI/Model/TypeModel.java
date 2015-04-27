package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;



public class TypeModel {
	
	private String fullyQualifiedName;	
	private ClassType classType;
	private String superclass;
	private List<String> superInterfaceTypes = new LinkedList<>();
	
	private JModifier<AccessModifier> accessModifier;
	private JModifier<AbstractModifier> abstractModifier;
	private JModifier<FinalModifier> finalModifier;
	private JModifier<StaticModifier> staticModifier;
	
	private List<FieldModel> fieldModels = new LinkedList<>();
	private List<MethodModel> methodModels = new LinkedList<>();
	private List<ConstructorModel> constructorModels = new LinkedList<>();
	private List<TypeModel> innerClassModels = new LinkedList<>();
	private SyntheticAttribute syntheticAttribute;
	
	public void addInnerClass(TypeModel typeModel) {
		innerClassModels.add(typeModel);		
	}
	
}
