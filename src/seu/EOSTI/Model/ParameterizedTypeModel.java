package seu.EOSTI.Model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.WildcardType;

public class ParameterizedTypeModel extends TypeModel {
	
	private List<TypeModel> typeArguments = new LinkedList<>();
	
	public ParameterizedTypeModel(String typeName){
		super(typeName);
	}

	public List<? extends TypeModel> getTypeArguments() {
		return typeArguments;
	}

	public void setTypeArguments(List<Type> types) {
		for (Type type : types) {
			TypeModel typeModel= null;
			if (type instanceof PrimitiveType) {
				typeModel = new PrimitiveTypeModel(type.toString());
			}else if (type instanceof ArrayType) {
				typeModel = new ArrayTypeModel(((ArrayType) type).getComponentType().toString(), ((ArrayType) type).getDimensions(), ((ArrayType) type).getElementType().toString());
			}else if (type instanceof SimpleType) {
				typeModel = new SimpleTypeModel(((SimpleType) type).getName().toString());
			}else if (type instanceof QualifiedType) {
				System.out.println(type.getClass().getName());
			}else if (type instanceof WildcardType) {
				System.out.println(type.getClass().getName());
			}else if (type instanceof ParameterizedType) {
				typeModel = new ParameterizedTypeModel(((ParameterizedType) type).getType().toString());
				List<Type> types2 = ((ParameterizedType) type).typeArguments();
				((ParameterizedTypeModel) typeModel).setTypeArguments(types2);						
			}else if (type instanceof UnionType) {
				System.out.println("Union");
			}
			
			this.typeArguments.add(typeModel);
		}

	}	
	
}
