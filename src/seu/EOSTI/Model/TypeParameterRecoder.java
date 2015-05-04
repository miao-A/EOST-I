package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;

public class TypeParameterRecoder {

	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	
	private List<String> oldTypeParameters = new LinkedList<>();
	private List<String> newTypeparameters = new LinkedList<>();

	
	public TypeParameterRecoder(List<String> olds,List<String> news){
		oldTypeParameters = olds;
		newTypeparameters = news;
		changeStatus = compareTypeParameters();		
	}
	
	
	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}
	
	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}
	
	public ChangeStatus compareTypeParameters(){
		if(oldTypeParameters.containsAll(newTypeparameters)&&newTypeparameters.containsAll(oldTypeParameters)) {
			return ChangeStatus.UNCHANGED;
		}else {
			return ChangeStatus.MODIFIED;
		}
	}	
}
