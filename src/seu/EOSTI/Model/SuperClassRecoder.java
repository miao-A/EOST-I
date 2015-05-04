package seu.EOSTI.Model;

public class SuperClassRecoder {
	
	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	
	private String oldSuperClass = new String();
	private String newSuperClass = new String();
	
	public SuperClassRecoder(String oldString,String newString){
		oldSuperClass = oldString;
		newSuperClass = newString;
		changeStatus = compareSuperClass();
	}	
	
	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}
	
	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}	
	
	public String getOldSuperClass() {
		return oldSuperClass;
	}
	public void setOldSuperClass(String oldSuperClass) {
		this.oldSuperClass = oldSuperClass;
	}
	public String getNewSuperClass() {
		return newSuperClass;
	}
	public void setNewSuperClass(String newSuperClass) {
		this.newSuperClass = newSuperClass;
	}
	
	public ChangeStatus compareSuperClass(){
		if (oldSuperClass.equals(newSuperClass)) {
			return ChangeStatus.UNCHANGED;
		}else {
			return ChangeStatus.MODIFIED;
		}
	}	
	
}
