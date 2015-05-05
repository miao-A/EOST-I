package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TypeChangeRecoder {

	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	private ChangeStatus superClassChangeStatus = ChangeStatus.UNCHANGED;
	private ChangeStatus superInterfaceChangeStatus = ChangeStatus.UNCHANGED;
	private ChangeStatus fieldChangeStatus = ChangeStatus.UNCHANGED;
	private ChangeStatus methodChangeStatus = ChangeStatus.UNCHANGED;
	

	
	private AbstractTypeModel oldTypeModel = null;
	private AbstractTypeModel newTypeModel = null;
	
	private ModifierRecoder modifierRecoder;
	private SuperClassRecoder superClassRecoder;
	private TypeParameterRecoder typeParameterRecoder;
	private SuperInterfaceClassRecoder superInterfaceClassRecoder;
	private FieldRecoder fieldRecoder;
	private MethodRecoder methodRecoder;
	private EnumConstantRecoder enumConstantRecoder;
	
	private List<AbstractTypeModel> newInnerTypeModels = new LinkedList<>();
	private List<AbstractTypeModel> removedInnerTypeModels = new LinkedList<>();
	private List<AbstractTypeModel> unchangedInnerTypeModels = new LinkedList<>();
	private List<AbstractTypeModel> modifiedInnerTypeModels = new LinkedList<>();
	
	
	public TypeChangeRecoder(AbstractTypeModel oldModel,AbstractTypeModel newModel){
		this.oldTypeModel = oldModel;
		this.newTypeModel = newModel;
		changeStatus = compareRun();
	}
	
	
	public ChangeStatus compareRun(){
		if ((oldTypeModel instanceof TypeModel)&&(newTypeModel instanceof TypeModel)) {
			superClassRecoder =new SuperClassRecoder(((TypeModel)oldTypeModel).getSuperClass(),((TypeModel)newTypeModel).getSuperClass());

		}
		
		if ((oldTypeModel instanceof EnumModel)&&(newTypeModel instanceof EnumModel)) {
			enumConstantRecoder =new EnumConstantRecoder(((EnumModel)oldTypeModel).getEnumConstant(),((EnumModel)newTypeModel).getEnumConstant());
		}
		
		
		
		modifierRecoder = new ModifierRecoder(oldTypeModel.getModifier(),newTypeModel.getModifier());
		typeParameterRecoder = new TypeParameterRecoder(oldTypeModel.getSuperInterfaceTypes(), newTypeModel.getSuperInterfaceTypes());
		superInterfaceClassRecoder = new SuperInterfaceClassRecoder(oldTypeModel.getSuperInterfaceTypes(), newTypeModel.getSuperInterfaceTypes());
		fieldRecoder = new FieldRecoder(oldTypeModel.getFieldModels(), newTypeModel.getFieldModels());
		methodRecoder = new MethodRecoder(oldTypeModel.getMethodModels(), newTypeModel.getMethodModels());
		
		if (isUnchanged(modifierRecoder.getChangeStatus())&&isUnchanged(superClassRecoder.getChangeStatus())&&isUnchanged(typeParameterRecoder.getChangeStatus())&&
				isUnchanged(superInterfaceClassRecoder.getChangeStatus())&&isUnchanged(fieldRecoder.getChangeStatus())&&isUnchanged(methodRecoder.getChangeStatus())) {

			this.changeStatus = ChangeStatus.UNCHANGED;
		}else {
			this.changeStatus = ChangeStatus.MODIFIED;
		}
		
		
		List<AbstractTypeModel> oldInners = oldTypeModel.getInnerClassModels();
		List<AbstractTypeModel> newInners = newTypeModel.getInnerClassModels();
		
		for (AbstractTypeModel oldInner : oldInners) {
			if (!newInners.contains(oldInner)) {
				removedInnerTypeModels.add(oldInner);
				this.changeStatus = ChangeStatus.MODIFIED;
			}
		}
		
		for (AbstractTypeModel newInner : newInners) {
			if (!oldInners.contains(newInner)) {
				newInnerTypeModels.add(newInner);
				this.changeStatus = ChangeStatus.MODIFIED;
			}
		}
		
		for (AbstractTypeModel newInner : newInners) {
			if (oldInners.contains(newInner)) {
				int index = oldInners.indexOf(newInner);
				TypeChangeRecoder innertypeChangeRecoder = new TypeChangeRecoder(oldInners.get(index), newInner); 
				if (isUnchanged(innertypeChangeRecoder.getChangeStatus())) {
					unchangedInnerTypeModels.add(newInner);
				}else {
					modifiedInnerTypeModels.add(newInner);
					this.changeStatus = ChangeStatus.MODIFIED;
				}
			}
		}
		
		return this.changeStatus;		
	}	
	
	
	
	private boolean isUnchanged(ChangeStatus changeStatus) {
		return changeStatus.equals(ChangeStatus.UNCHANGED);
	}
	
	public ChangeStatus getSuperClassChangeStatus() {
		return superClassChangeStatus;
	}
	public void setSuperClassChangeStatus(ChangeStatus superClassChangeStatus) {
		this.superClassChangeStatus = superClassChangeStatus;
	}
	public ChangeStatus getSuperInterfaceChangeStatus() {
		return superInterfaceChangeStatus;
	}
	public void setSuperInterfaceChangeStatus(ChangeStatus superInterfaceChangeStatus) {
		this.superInterfaceChangeStatus = superInterfaceChangeStatus;
	}
	public ChangeStatus getFieldChangeStatus() {
		return fieldChangeStatus;
	}
	public void setFieldChangeStatus(ChangeStatus fieldChangeStatus) {
		this.fieldChangeStatus = fieldChangeStatus;
	}
	public ChangeStatus getMethodChangeStatus() {
		return methodChangeStatus;
	}
	public void setMethodChangeStatus(ChangeStatus methodChangeStatus) {
		this.methodChangeStatus = methodChangeStatus;
	}	

	public SuperClassRecoder getSuperClassRecoder() {
		return superClassRecoder;
	}

	public void setSuperClassRecoder(SuperClassRecoder superClassRecoder) {
		this.superClassRecoder = superClassRecoder;
	}


	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}


	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}

}
