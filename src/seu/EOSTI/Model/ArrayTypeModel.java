package seu.EOSTI.Model;

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
}
