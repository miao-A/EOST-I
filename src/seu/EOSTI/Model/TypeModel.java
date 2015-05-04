package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.Modifier;

import com.ibm.icu.text.StringTransform;



public class TypeModel extends AbstractTypeModel {
	
	private String packageName = null;
	private String className = null;	
	private boolean INTERFACE = false;
	
	private List<String> superInterfaceTypes = new LinkedList<>();
	private String superClass;
	private JModifier modifier = new JModifier();
	
	private List<FieldModel> fieldModels = new LinkedList<>();
	private List<MethodModel> methodModels = new LinkedList<>();
	private List<EnumModel> enumClassModels = new LinkedList<>();
	private List<TypeModel> innerClassModels = new LinkedList<>();
	private List<String> typeParameters =  new LinkedList<>();
	private boolean empty = true;

	

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

	public List<EnumModel> getEnumClassModels() {
		return enumClassModels;
	}

	public void addEnumClassModel(EnumModel enumModel) {
		this.enumClassModels.add(enumModel);
	}

	public List<String> getTypeParameters() {
		return typeParameters;
	}

	public void setTypeParameters(List<String> typeParameters) {
		this.typeParameters = typeParameters;
	}	
	
	public void addTypeParameter(String typeParameter){
		this.typeParameters.add(typeParameter);
	}

	public List<MethodModel> getMethodModels() {
		return methodModels;
	}

	public void setMethodModels(List<MethodModel> methodModels) {
		for (MethodModel methodModel : methodModels) {
			this.methodModels.add(methodModel);
		}
	}

	public void addMethodModel(MethodModel methodModel){
		this.methodModels.add(methodModel);
	}


	public boolean isEmpty() {
		return empty;
	}


	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
