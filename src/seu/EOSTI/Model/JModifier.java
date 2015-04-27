package seu.EOSTI.Model;

public class JModifier<T> {
	
	private  JChangeStatus changeStatus;

	public JModifier(JChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}

	
/*
	
	public boolean hasChangedFromTo(T oldValue, T newValue) {
		boolean hasChanged = false;
		if(oldModifier.isPresent() && newModifier.isPresent()) {
			if(oldModifier.get() == oldValue && newModifier.get() == newValue) {
				hasChanged = true;
			}
		}
		return hasChanged;
	}

	public boolean hasChangedFrom(T oldValue) {
		boolean hasChanged = false;
		if(oldModifier.isPresent() && newModifier.isPresent()) {
			if(oldModifier.get() == oldValue && newModifier.get() != oldValue) {
				hasChanged = true;
			}
		} else if(oldModifier.isPresent() && !newModifier.isPresent()) {
			if(oldModifier.get() == oldValue) {
				hasChanged = true;
			}
		} else {
			hasChanged = true;
		}
		return hasChanged;
	}*/
}
