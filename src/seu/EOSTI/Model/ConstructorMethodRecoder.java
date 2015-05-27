package seu.EOSTI.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConstructorMethodRecoder {
	
	private CompatibilityStatus compatibilityStatus = CompatibilityStatus.COMPATIBILITY;
	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	private List<ConstructorMethodModel> oldMethodModels = new LinkedList<>();
	private List<ConstructorMethodModel> newMethodModels = new LinkedList<>();
	
	private List<ConstructorMethodModel> newAddMethodModels = new LinkedList<>();
	private List<ConstructorMethodModel> removedMethodModels = new LinkedList<>();
	private List<ConstructorMethodModel> unchangedMethodModels = new LinkedList<>();

	
	private Map<ConstructorMethodModel, ConstructorMethodModel> modifiedMethodMap = new HashMap<ConstructorMethodModel, ConstructorMethodModel>();
	
	/*
	 * private String methodName;	
	private boolean Constructor = false;	
	private JModifier modifier = new JModifier();
	private List<String> typeParameters = new LinkedList<>();
	
	private String returnType = null;
	private int extraDimensions = 0;
	
	private List<SingleVariableModel> formalParameters = new LinkedList<>();
	private List<String> thrownList = new LinkedList<>();*/
	
	
	
	public ConstructorMethodRecoder(List<ConstructorMethodModel> oldMethodModels,List<ConstructorMethodModel> newMethodModels) {
		this.oldMethodModels = oldMethodModels;
		this.newMethodModels = newMethodModels;
		compareMethodModel();
	}
	
	public ConstructorMethodRecoder() {
		// TODO Auto-generated constructor stub
	}

	public void compareMethodModel(){
		
		if (oldMethodModels.containsAll(newMethodModels)&&newMethodModels.containsAll(oldMethodModels)) {
			setChangeStatus(ChangeStatus.UNCHANGED);
			unchangedMethodModels = oldMethodModels;
		}else {
			setChangeStatus(ChangeStatus.MODIFIED);
			for (ConstructorMethodModel oldMethodModel : oldMethodModels) {
				if (!newMethodModels.contains(oldMethodModel)) {
					removedMethodModels.add(oldMethodModel);
				}			
			}
			
			for (ConstructorMethodModel newMethodModel : newMethodModels) {
				if (!oldMethodModels.contains(newMethodModel)) {
					newAddMethodModels.add(newMethodModel);
				}			
			}
			
			for (ConstructorMethodModel newMethodModel : newMethodModels) {
				if (oldMethodModels.contains(newMethodModel)){
					int index = oldMethodModels.indexOf(newMethodModel);
					ModifierRecoder mr = new ModifierRecoder(oldMethodModels.get(index).getModifier(), newMethodModel.getModifier());
					if (mr.hasChange()) {
						modifiedMethodMap.put(oldMethodModels.get(index), newMethodModel);

					}else {
						unchangedMethodModels.add(newMethodModel);
					}
								
				}
			}
		}
	}

	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}
	
	public List<ConstructorMethodModel> getNewAddMethodModels(){
		return newAddMethodModels;
	}
	
	public List<ConstructorMethodModel> getRemovedMethodModels(){
		return removedMethodModels;
	}
	
	public List<ConstructorMethodModel> getUnchangedMethodModels(){
		return unchangedMethodModels;
	}

	public Map<ConstructorMethodModel, ConstructorMethodModel> getModifiedMethodMap() {
		return modifiedMethodMap;
	}
	
	public int getNewAddMehodNum(){
		int count =0;
		for (ConstructorMethodModel methodModel : newAddMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}
	
	public int getRemovedMehodNum(){
		int count =0;
		for (ConstructorMethodModel methodModel : removedMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}
	
	public int getUnchangedMethodNum(){
		int count =0;
		for (ConstructorMethodModel methodModel : unchangedMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}


	public int getModifiedMethodNum(){
		int count =0;	
		for (ConstructorMethodModel methodModel : modifiedMethodMap.keySet()) {
			if (methodModel.getModifier().isPUBLIC()||modifiedMethodMap.get(methodModel).getModifier().isPUBLIC()) {
				count++;
			}
			
		}
		return count;
	}

	public CompatibilityStatus getCompatibilityStatus() {
		return compatibilityStatus;
	}

	public void setCompatibilityStatus(CompatibilityStatus compatibilityStatus) {
		this.compatibilityStatus = compatibilityStatus;
	}
	
	public boolean isCompatibility(){
		boolean flag = true;
		return flag;
	}
}
