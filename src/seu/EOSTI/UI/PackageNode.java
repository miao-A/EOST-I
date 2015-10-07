package seu.EOSTI.UI;

import java.util.ArrayList;

public class PackageNode {

	private String name;
	private ArrayList<String> afferentList;
	private ArrayList<String> efferentList;
	
	public PackageNode(String name){
		this.name = name;
		afferentList = new ArrayList<String>();
		efferentList = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setAfferents(ArrayList<String> aList) {
		for (String string : aList) {
			afferentList.add("AC: "+string);
		}
	}
	
	public void setEfferents(ArrayList<String> eList) {
		for (String string : eList) {
			efferentList.add("EC: "+ string);
		}
	}
	
	public ArrayList<String> getAfferents() {
		return afferentList;
	}
	
	public ArrayList<String> getEfferents() {
		return efferentList;
	}
	
	public boolean hasAfferents() {
		if (afferentList.size() > 0) {
			return true;			
		}		
		return false;		
	}
	
	public boolean hasEfferents() {
		if (efferentList.size() > 0) {
			return true;			
		}		
		return false;		
	}	
	
	public double getChangeabilityRatio(){
		return 1.0*getEfferents().size()/(getAfferents().size() + getEfferents().size());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof PackageNode) {
			if (this.getName().equals("AC: "+((PackageNode) o).getName())||this.getName().equals("EC: "+((PackageNode) o).getName())) {
			return true;
			}
		}
		
		return false;		
	}
}
