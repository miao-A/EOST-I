package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;

public class EnumModel {
	
	private String packageName = null;
	private String className;	
	private List<String> superInterfaceTypes = new LinkedList<>();
	private JModifier modifier = new JModifier();
	
	private List<String> enumConstant = new LinkedList<>();
	
	private List<FieldModel> fieldModels = new LinkedList<>();
	private List<MethodModel> methodModels = new LinkedList<>();
	private List<ConstructorModel> constructorModels = new LinkedList<>();
	private List<EnumModel> enumClassModels = new LinkedList<>();
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


	public List<String> getSuperInterfaceTypes() {
		return superInterfaceTypes;
	}

	public void setSuperInterfaceType(String superInterfaceType) {
		this.superInterfaceTypes.add(superInterfaceType);
	}

	
	public List<FieldModel> getFieldModels() {
		return fieldModels;
	}
	
	public void addFieldModel(FieldModel fieldModel) {
		this.fieldModels.add(fieldModel);		
	}

	public void setFieldModels(List<FieldModel> fieldModels) {
		for (FieldModel fieldModel : fieldModels) {
			this.fieldModels.add(fieldModel);
		}
	}


	public List<String> getEnumConstant() {
		return enumConstant;
	}

	public void setEnumConstant(List<String> enumConstant) {
		this.enumConstant = enumConstant;
	}	
	
	public void addEnumConstant(String enumConstant){
		this.enumConstant.add(enumConstant);
	}

	public List<EnumModel> getEnumClassModels() {
		return enumClassModels;
	}

	public void setEnumClassModels(List<EnumModel> enumClassModels) {
		this.enumClassModels = enumClassModels;
	}
	
	public void addEnumClassModel(EnumModel enumClassModel) {
		this.enumClassModels.add(enumClassModel);
	}

}
