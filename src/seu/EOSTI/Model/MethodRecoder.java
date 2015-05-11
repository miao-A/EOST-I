package seu.EOSTI.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MethodRecoder {
	
	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	private List<MethodModel> oldMethodModels = new LinkedList<>();
	private List<MethodModel> newMethodModels = new LinkedList<>();
	
	private List<MethodModel> newAddMethodModels = new LinkedList<>();
	private List<MethodModel> removedMethodModels = new LinkedList<>();
	private List<MethodModel> unchangedMethodModels = new LinkedList<>();
//	private List<MethodModel> modifiedMethodModels = new LinkedList<>();
	
	private Map<MethodModel, MethodModel> modifiedMethodMap = new HashMap<MethodModel, MethodModel>();
	
	/*
	 * private String methodName;	
	private boolean Constructor = false;	
	private JModifier modifier = new JModifier();
	private List<String> typeParameters = new LinkedList<>();
	
	private String returnType = null;
	private int extraDimensions = 0;
	
	private List<SingleVariableModel> formalParameters = new LinkedList<>();
	private List<String> thrownList = new LinkedList<>();*/
	
	
	
	public MethodRecoder(List<MethodModel> oldMethodModels,List<MethodModel> newMethodModels) {
		this.oldMethodModels = oldMethodModels;
		this.newMethodModels = newMethodModels;
		compareMethodModel();
	}
	
	public MethodRecoder() {
		// TODO Auto-generated constructor stub
	}

	public void compareMethodModel(){
		
		if (oldMethodModels.containsAll(newMethodModels)&&newMethodModels.containsAll(oldMethodModels)) {
			setChangeStatus(ChangeStatus.UNCHANGED);
			unchangedMethodModels = oldMethodModels;
		}else {
			setChangeStatus(ChangeStatus.MODIFIED);
			for (MethodModel oldMethodModel : oldMethodModels) {
				if (!newMethodModels.contains(oldMethodModel)) {
					removedMethodModels.add(oldMethodModel);
				}			
			}
			
			for (MethodModel newMethodModel : newMethodModels) {
				if (!oldMethodModels.contains(newMethodModel)) {
					newAddMethodModels.add(newMethodModel);
				}			
			}
			
			for (MethodModel newMethodModel : newMethodModels) {
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
	
	public List<MethodModel> getNewAddMethodModels(){
		return newAddMethodModels;
	}
	
	public List<MethodModel> getRemovedMethodModels(){
		return removedMethodModels;
	}
	
	public List<MethodModel> getUnchangedMethodModels(){
		return unchangedMethodModels;
	}

	public Map<MethodModel, MethodModel> getModifiedMethodMap() {
		return modifiedMethodMap;
	}
	
	public int getNewAddMehodNum(){
		int count =0;
		for (MethodModel methodModel : newAddMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}
	
	public int getRemovedMehodNum(){
		int count =0;
		for (MethodModel methodModel : removedMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}
	
	public int getUnchangedMethodNum(){
		int count =0;
		for (MethodModel methodModel : unchangedMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}


	public int getModifiedMethodNum(){
		int count =0;	
		for (MethodModel methodModel : modifiedMethodMap.keySet()) {
			if (methodModel.getModifier().isPUBLIC()||modifiedMethodMap.get(methodModel).getModifier().isPUBLIC()) {
				count++;
			}
			
		}
		return count;
	}
	
}
