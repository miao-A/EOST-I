package seu.EOSTI.Model;

import org.omg.CORBA.PUBLIC_MEMBER;

public class ArrayTypeModel extends TypeModel {
	private int dimiensions;
	private String elementType;
	
	public ArrayTypeModel(String typeName){
		super(typeName);
		dimiensions = 0;
		elementType = new String();
	}
	
	
	public ArrayTypeModel(String typeName,int dimiensions,String elementType){
		super(typeName);
		this.dimiensions = dimiensions;
		elementType = new String();
	}


	@Override
	public boolean CanCompatibility(TypeModel typeModel) {
		// TODO Auto-generated method stub
		
		return false;
	}
	
	public int getDimiensions(){
		return dimiensions;
	}


	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		String string = "";
		string += this.getTypeName();
		for (int i = 0; i < dimiensions; i++) {
			string += "[]";
		}
		return string;
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof ArrayTypeModel) {
			if (this.getTypeName().equals(((ArrayTypeModel) obj).getTypeName())&&(this.getDimiensions() == ((ArrayTypeModel) obj).getDimiensions())) {
				return true;
			}
		}
		return false;
	}
	
	
}