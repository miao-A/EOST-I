package seu.EOSTI.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;


/**
 * MethodDeclaration:
    [ Javadoc ] { ExtendedModifier }
                  [ < TypeParameter { , TypeParameter } > ]
        ( Type | void ) Identifier (
        [ FormalParameter
                     { , FormalParameter } ] ) {[ ] }
        [ throws TypeName { , TypeName } ] ( Block | ; )
 ConstructorDeclaration:
    [ Javadoc ] { ExtendedModifier }
                  [ < TypeParameter { , TypeParameter } > ]
        Identifier (
                  [ FormalParameter
                         { , FormalParameter } ] )
        [throws TypeName { , TypeName } ] Block
 
 * */
public class MethodModel {

	private String methodName;	
	
	private JModifier modifier = new JModifier();
	private List<String> typeParameters = new LinkedList<>();
	
	private TypeModel returnType ;
	private int extraDimensions = 0;
	
	private ArrayList<SingleVariableModel> formalParameters = new ArrayList<SingleVariableModel>();
	private List<String> thrownList = new LinkedList<>();	
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodeName) {
		this.methodName = methodeName;
	}

	
	public void setModifier(JModifier jModifier){
		modifier = jModifier;
	}
	
	public JModifier getModifier(){
		return modifier;
	}

	
	
	public List<String> getTypeParameters() {
		return typeParameters;
	}

	public void setTypeParameters(List<String> typeParameters) {
		this.typeParameters = typeParameters;
	}	
	
	public void addTypeParameter(String typeParameter){
		this.typeParameters.add(typeParameter);
	}

	public TypeModel getReturnType() {
		return returnType;
	}

	public void setReturnType(TypeModel returnType) {
		this.returnType = returnType;
	}

	public List<SingleVariableModel> getFormalParameters() {
		return formalParameters;
	}

	public void setFormalParameters(ArrayList<SingleVariableModel> formalParameters) {
		this.formalParameters = formalParameters;
	}
	
	public void addFormalParameters(SingleVariableModel formalParameter) {
		this.formalParameters.add(formalParameter);
	}

	public int getExtraDimensions() {
		return extraDimensions;
	}

	public void setExtraDimensions(int extraDimensions) {
		this.extraDimensions = extraDimensions;
	}

	public List<String> getThrownList() {
		return thrownList;
	}

	public void setThrownList(List<String> thrownList) {
		this.thrownList = thrownList;
	}
	
	public void addThrownList(String thrown) {
		this.thrownList.add(thrown);
	}
	
	public String getFullName(){
		String string = new String();
		string += this.getModifier().getModifierInfo()+this.getReturnType()+" "
				+this.getMethodName()+"(";
		List<SingleVariableModel> tpList =  this.getFormalParameters();
		for (int i = 0; i < tpList.size(); i++) {
			string += tpList.get(i).getType()+" "+tpList.get(i).getName();
			if (i!=tpList.size()-1) {
				string +=",";
			}
		}
		string += ")";
		return string;
	}

	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		if (obj instanceof MethodModel) {
			if( this.getMethodName().equals(((MethodModel) obj).getMethodName())){
				List<SingleVariableModel> oldList = this.getFormalParameters();
				List<SingleVariableModel> newList = ((MethodModel) obj).getFormalParameters();
				if (oldList.size() != newList.size()) {
					return false;
				}
				boolean flag = true;
				for (int i = 0; i < oldList.size(); i++) {
					if (!oldList.get(i).equals(newList.get(i))) {
						flag = false;
					};
				}
				return flag;
			}
		}
		return false;
	}

}
