package seu.EOSTI.Model;
//��ȷ�����ͱ�����ʽ
/*QualifiedType:
    Type . SimpleName*/
 
public class QualifiedTypeModel extends TypeModel {
	
	private String qualifiedString = "";
	
	public void setQualifiedName(String string){
		this.qualifiedString = string;
	}

	
	@Override
	public boolean CanCompatibility(TypeModel typeModel) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		return qualifiedString+"."+this.getTypeName();
	}
	
	
	
	
	
}

