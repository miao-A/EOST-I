package seu.EOSTI.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FieldRecoder {

	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	private List<FieldModel> oldFieldModels = new LinkedList<>();
	private List<FieldModel> newFieldModels = new LinkedList<>();
	
	private List<FieldModel> newAddFieldModels = new LinkedList<>();
	private List<FieldModel> removedFieldModels = new LinkedList<>();
	private List<FieldModel> unchangedFieldModels = new LinkedList<>();
	private List<FieldModel> modifiedFieldModels = new LinkedList<>();
	
	private Map<FieldModel, ChangeStatus> modifierRecoderMap = new HashMap<FieldModel, ChangeStatus>();
	
	
	
	/*private String fieldName;
	private String type;
	private JModifier modifier = new JModifier();*/
	
	public FieldRecoder(List<FieldModel> oldFieldModels,List<FieldModel> newFieldModels) {
		this.oldFieldModels = oldFieldModels;
		this.newFieldModels = newFieldModels;
		this.changeStatus = compareFieldModel();
	}
	
	public ChangeStatus compareFieldModel(){
		if (oldFieldModels.containsAll(newFieldModels)&&newFieldModels.containsAll(oldFieldModels)) {
			this.changeStatus = ChangeStatus.UNCHANGED;
		}else{
			this.changeStatus = ChangeStatus.MODIFIED;
			for (FieldModel oldFieldModel : oldFieldModels) {
				if (!newFieldModels.contains(oldFieldModel)) {
					removedFieldModels.add(oldFieldModel);
				}			
			}
			
			for (FieldModel newFieldModel : newFieldModels) {
				if (!oldFieldModels.contains(newFieldModel)) {
					newFieldModels.add(newFieldModel);
				}			
			}
			
			for (FieldModel newFieldModel : newFieldModels) {
				if (oldFieldModels.contains(newFieldModel)){
					int index = oldFieldModels.indexOf(newFieldModel);
					ModifierRecoder mr = new ModifierRecoder(oldFieldModels.get(index).getModifier(), newFieldModel.getModifier());
					if (mr.hasChange()) {
						modifierRecoderMap.put(oldFieldModels.get(index), ChangeStatus.MODIFIED);
						modifiedFieldModels.add(newFieldModel);
					}else {
						unchangedFieldModels.add(newFieldModel);
					}
								
				}
			}
		}
		return this.changeStatus;
	}

	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}	
	
}
