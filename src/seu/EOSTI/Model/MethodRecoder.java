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
	private List<MethodModel> modifiedMethodModels = new LinkedList<>();
	
	private Map<MethodModel, ChangeStatus> modifierRecoderMap = new HashMap<MethodModel, ChangeStatus>();
	
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
	
	public void compareMethodModel(){
		
		if (oldMethodModels.containsAll(newMethodModels)&&newMethodModels.containsAll(oldMethodModels)) {
			setChangeStatus(ChangeStatus.UNCHANGED);
		}else {
			setChangeStatus(ChangeStatus.MODIFIED);
			for (MethodModel oldMethodModel : oldMethodModels) {
				if (!newMethodModels.contains(oldMethodModel)) {
					removedMethodModels.add(oldMethodModel);
				}			
			}
			
			for (MethodModel newMethodModel : newMethodModels) {
				if (!oldMethodModels.contains(newMethodModel)) {
					newMethodModels.add(newMethodModel);
				}			
			}
			
			for (MethodModel newMethodModel : newMethodModels) {
				if (oldMethodModels.contains(newMethodModel)){
					int index = oldMethodModels.indexOf(newMethodModel);
					ModifierRecoder mr = new ModifierRecoder(oldMethodModels.get(index).getModifier(), newMethodModel.getModifier());
					if (mr.hasChange()) {
						modifierRecoderMap.put(oldMethodModels.get(index), ChangeStatus.MODIFIED);
						modifiedMethodModels.add(newMethodModel);
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
}
