package seu.EOSTI.Model;

public class SingleVariableModel {

	private String name;
	private JModifier modifier;
	private String type;
	private boolean varargs = false;
	private int extraDimensions = 0;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public JModifier getModifier() {
		return modifier;
	}
	
	
	
	public void setModifier(JModifier modifier) {
		this.modifier = modifier;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isVarargs() {
		return varargs;
	}
	public void setVarargs(boolean varargs) {
		this.varargs = varargs;
	}
	public int getExtraDimensions() {
		return extraDimensions;
	}
	public void setExtraDimensions(int extraDimensions) {
		this.extraDimensions = extraDimensions;
	}	
	

	
	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		if (obj instanceof SingleVariableModel) {
			if (this.getType().equals(((SingleVariableModel) obj).getType())&&(this.isVarargs()==((SingleVariableModel) obj).isVarargs())) {
				return true;
			}
		}
		
		return false;
	}

}
