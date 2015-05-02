package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;


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
	
	private List<SingleVariableModel> formalParameters = new LinkedList<>();
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

	public void setFormalParameters(List<SingleVariableModel> formalParameters) {
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


}
