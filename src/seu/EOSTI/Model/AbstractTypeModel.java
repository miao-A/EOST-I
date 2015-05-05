package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractTypeModel {
	
/*	private String packageName = null;
	private String className = null;	
	private List<String> superInterfaceTypes = new LinkedList<>();
	private JModifier modifier = new JModifier();	
	private List<FieldModel> fieldModels = new LinkedList<>();
	private List<MethodModel> methodModels = new LinkedList<>();
	private List<EnumModel> enumClassModels = new LinkedList<>();
	private List<TypeModel> innerClassModels = new LinkedList<>();

	*/
	public abstract void setPackage(String name);
	
	public abstract String getPackage();
	
	public abstract void setClassName(String name);
	
	public abstract String getClassName();
	
	public abstract void addInnerClass(TypeModel typeModel);
		
	public abstract void setModifier(JModifier jModifier);
		
	public abstract JModifier getModifier();
	
	public abstract List<String> getSuperInterfaceTypes();
	
	public abstract void setSuperInterfaceType(String superInterfaceType);
	
	public abstract List<FieldModel> getFieldModels();
	
	public abstract void setFieldModels(List<FieldModel> fieldModels);
	
	/*public abstract List<EnumModel> getEnumClassModels();

	public abstract void addEnumClassModel(EnumModel enumModel);*/
	
	public abstract List<MethodModel> getMethodModels();

	public abstract void setMethodModels(List<MethodModel> methodModels);
	
	public abstract List<AbstractTypeModel> getInnerClassModels();
	
	public abstract void setInnerClassModels(List<AbstractTypeModel> innerClassModels);
	
	public abstract void addInnerClassModel(AbstractTypeModel innerClassModel);	
	
	public abstract boolean equals(Object obj);

	
}
