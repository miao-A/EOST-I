package seu.EOSTI.Model;

import java.util.List;
//Õ®≈‰∑˚¿‡–Õ
public class WildCardTypeModel<T> extends TypeModel {

	private String boundType;
	private boolean upperBound;
	public  WildCardTypeModel(String typeName) {
		super(typeName);
		setBoundType(new String());
	}
	
	
	public boolean isUpperBound() {
		return upperBound;
	}

	public void setUpperBound(boolean upperBound) {
		this.upperBound = upperBound;
	}

	public String getBoundType() {
		return boundType;
	}

	public void setBoundType(String boundType) {
		this.boundType = boundType;
	}
	
	
}
