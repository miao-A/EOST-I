package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;

public class ClassComparator {
	
	private List<AbstractTypeModel> removedType = new LinkedList<>();
	private List<AbstractTypeModel> newType = new LinkedList<>();
	
	private List<TypeChangeRecoder>  typeChangeRecoders = new LinkedList<>();
	
	public ClassComparator(List<AbstractTypeModel> oldModels,List<AbstractTypeModel> newModels){

		for (AbstractTypeModel oldTypeModel : oldModels) {
			if (!newModels.contains(oldTypeModel)) {
				removedType.add(oldTypeModel);
			}			
		}
		
		for (AbstractTypeModel newTypeModel : newModels) {
			if (!oldModels.contains(newTypeModel)) {
				newType.add(newTypeModel);
			}
		}
		
		for (AbstractTypeModel newTypeModel : newModels) {
			if (oldModels.contains(newTypeModel)){
				int index = oldModels.indexOf(newTypeModel);
				typeChangeRecoders.add(new TypeChangeRecoder(oldModels.get(index),newTypeModel));
			}
		}
		
	}
	
	public List<TypeChangeRecoder> getTypeChangeRecoders(){
		
		removedType.clear();
		newType.clear();;
		return typeChangeRecoders;
	}
	
}
