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
	private boolean Constructor = false;	
	private JModifier modifier = new JModifier();
	private List<String> typeParameters = new LinkedList<>();
	
	private String returnType = null;
	private int extraDimensions = 0;
	
	private ArrayList<SingleVariableModel> formalParameters = new ArrayList<SingleVariableModel>();
	private List<String> thrownList = new LinkedList<>();
	
	public String getMethodName(int i, double d) {
		return methodName;
	}
	
	public String getMethodName(double d, int i) {
		return methodName;
	}
	
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

	public boolean isConstructor() {
		return Constructor;
	}

	public void setConstructor(boolean constructor) {
		Constructor = constructor;
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

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		if (returnType == null) {
			setConstructor(true);
		}
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
				for (int i = 0; i < oldList.size(); i++) {
					oldList.get(i).equals(newList.get(i));
				}
			}
		}
		return false;
	}

}
