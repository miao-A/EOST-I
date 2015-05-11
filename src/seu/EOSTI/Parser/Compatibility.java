package seu.EOSTI.Parser;

import java.util.LinkedList;
import java.util.List;

import seu.EOSTI.Model.AbstractTypeModel;
import seu.EOSTI.Model.ChangeStatus;
import seu.EOSTI.Model.MethodRecoder;
import seu.EOSTI.Model.TypeChangeRecoder;

public class Compatibility {
	
	private List<AbstractTypeModel> removedType = new LinkedList<>();
	private List<AbstractTypeModel> newType = new LinkedList<>();
	private List<TypeChangeRecoder> unchangedType = new LinkedList<>();
	private List<TypeChangeRecoder> modifiedType = new LinkedList<>();
	
	private List<TypeChangeRecoder>  typeChangeRecoders = new LinkedList<>();
	
	public Compatibility(List<AbstractTypeModel> oldModels,List<AbstractTypeModel> newModels){

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
//				TypeChangeRecoder tc = new TypeChangeRecoder(oldModels.get(index),newTypeModel);
				
				typeChangeRecoders.add(new TypeChangeRecoder(oldModels.get(index),newTypeModel));
			}
		}
		
		for (TypeChangeRecoder tc : typeChangeRecoders) {
			if (tc.getChangeStatus().equals(ChangeStatus.UNCHANGED)) {
				unchangedType.add(tc);			
			}else if(tc.getChangeStatus().equals(ChangeStatus.MODIFIED)){
				modifiedType.add(tc);
			}
		}
		
		this.getinfo();
		
	}
	
	public List<TypeChangeRecoder> getTypeChangeRecoders(){

		return typeChangeRecoders;
	}
	
	public void getinfo(){
		for(AbstractTypeModel atm : newType){
			System.out.println("newType:"+ atm.getPackage()+" " +atm.getClassName());
		}
		
		for(AbstractTypeModel atm : removedType){
			System.out.println("removedType:"+ atm.getPackage()+" " +atm.getClassName());
		}
		
		for(TypeChangeRecoder atm : unchangedType){
			System.out.println("unchangedType:"+ atm.getNewTypeModel().getPackage()+" " +atm.getNewTypeModel().getClassName());
		}
		
		for(TypeChangeRecoder atm : modifiedType){
			System.out.println("modifiedType:"+ atm.getNewTypeModel().getPackage()+" " +atm.getNewTypeModel().getClassName());
			MethodRecoder mr = atm.getMethodRecoder();
			mr.getNewAddMethodModels();
			mr.getRemovedMethodModels();
			mr.getUnchangedMethodModels();
			mr.getModifiedMethodModels();
		}
	}
	

}
