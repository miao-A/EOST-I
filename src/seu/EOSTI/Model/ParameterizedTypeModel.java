package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;

public class ParameterizedTypeModel extends TypeModel {
	
	private List<? extends TypeModel> typeArguments = new LinkedList<>();
	
	public ParameterizedTypeModel(String typeName){
		super(typeName);
	}

	public List<? extends TypeModel> getTypeArguments() {
		return typeArguments;
	}

	public void setTypeArguments(List<? extends TypeModel> typeArguments) {
		this.typeArguments = typeArguments;
	}	
	
}
