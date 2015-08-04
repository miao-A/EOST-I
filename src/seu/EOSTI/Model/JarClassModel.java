package seu.EOSTI.Model;

import java.util.ArrayList;
import java.util.List;

public class JarClassModel {
	String packagename;
	String classname;
	List<JarMethodModel> methodList = new ArrayList<>();
	
	public JarClassModel(String pName, String ppName) {
		// TODO Auto-generated constructor stub
		packagename = pName;
		classname = ppName;
	}
	
	public String getClassName(){
		return classname;
	}
	
	public String getPackageName(){
		return packagename;
	}
	
	public void addmethod(JarMethodModel jarMethodModel){
		methodList.add(jarMethodModel);
	}
	
	public List<JarMethodModel> getmethod(){
		return methodList;
	}
	
	public boolean equals(Object o){
		if (o == this) {
			return true;
		}
		
		if (o instanceof JarClassModel) {
			return this.getClassName().equals(((JarClassModel) o).getClassName())&&this.getPackageName().equals(((JarClassModel) o).getPackageName());
		}
		
		return false;
	}
	
	
}
