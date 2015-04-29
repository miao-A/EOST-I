package seu.EOSTI.ASTVisitor;

import java.util.LinkedList;
import java.util.List;







import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WildcardType;

import seu.EOSTI.Model.AccessModifier;
import seu.EOSTI.Model.ClassType;
import seu.EOSTI.Model.FieldModel;
import seu.EOSTI.Model.JModifier;
import seu.EOSTI.Model.TypeModel;

public class ComponentVisitor extends ASTVisitor {

	private TypeModel typeModel;
	
	public ComponentVisitor(){
		typeModel = new TypeModel();
	}

	public boolean visit(TypeDeclaration node){
	
		//内部类或匿名类
		if (node.isMemberTypeDeclaration()) {
			return true;
		}
		
//		System.out.println("Type:\t"+node.getName().getIdentifier());
		String string=node.getName().getIdentifier();
		typeModel.setClassName(string);
		System.out.println("Type:\t"+node.getName().getIdentifier());
		if (node.getSuperclassType() != null) {
			typeModel.setSuperClass(node.getSuperclassType().toString());
//		System.out.println("superclass:"+node.getSuperclassType().toString());
		}
		List<Type> list = node.superInterfaceTypes();
		for (Type interfaceType : list) {
			
		}

		typeModel.setModifier(getJModifier(node));	
		 
		
		
		if (node.getSuperclassType() != null) {
//			System.out.println("Superclass:\t"+node.getSuperclassType());
		}
//		System.out.println(node.getSuperclassType());
		node.superInterfaceTypes();
	
	
		//处理field //声明部分记录，未记录后面的初始化
		FieldDeclaration[] fields = node.getFields();
		for (FieldDeclaration fieldDeclaration : fields) {
			getJModifier(fieldDeclaration);
			
			Type type = fieldDeclaration.getType();
			System.out.println("####################################"+type.toString());
			if (type instanceof PrimitiveType) {
				System.out.println("PrimitiveType");
				System.out.println(((PrimitiveType) type).toString());
			}else if (type instanceof SimpleType) {
				System.out.println("simpletype");
				System.out.println(((SimpleType) type).getName());
			}else if (type instanceof ParameterizedType) {
				System.out.println("ParameterizedType");
				System.out.println(((ParameterizedType) type).getType());
				System.out.println(((ParameterizedType) type).typeArguments());
			}else if (type instanceof ArrayType) {
				System.out.println("ArrayType");
				System.out.println(((ArrayType) type).getComponentType());

			}else  if (type instanceof QualifiedType ) {
				System.out.println("QualifiedType");
			}else if (type instanceof WildcardType) {
				System.out.println("WildcardType");
			}
			
			List<VariableDeclarationFragment> fragment = fieldDeclaration.fragments();
			
			for (VariableDeclarationFragment vdFragment : fragment) {

//				System.out.println("vdFragment:"+vdFragment.getName());
			}			

		}
		
		
		//处理枚举类型
		List bd = node.bodyDeclarations();
		for (Object object : bd) {
			if (object instanceof EnumDeclaration) {
//				System.out.println("EnumDeclaration:\t"+((EnumDeclaration) object).getName());
			} else if(object instanceof TypeDeclaration){
//				System.out.println("TypeDeclaration:\t"+((TypeDeclaration) object).getName());
			} else if (object instanceof MethodDeclaration) {
				if (((MethodDeclaration) object).isConstructor()) {
//					System.out.println("Constructor:\t"+((MethodDeclaration) object).getName());
					List<SingleVariableDeclaration> p = ((MethodDeclaration) object).parameters();
					for (SingleVariableDeclaration singleVariableDeclaration : p) {
						System.out.println(singleVariableDeclaration.getType()+" "+singleVariableDeclaration.getName());
					}
					
				} else{
//					System.out.println("MethodDeclaration:\t"+((MethodDeclaration) object).getName());
				}
			} else if (object instanceof FieldDeclaration) {
				System.out.println("FieldDeclarationType:\t"+((FieldDeclaration) object).getType());
			} else if(object instanceof Initializer) {
				System.out.println("Initializer");
			} 
		}		
	
		return true;
	}
	
	private TypeModel innerClassType(TypeDeclaration type){
		TypeModel typeModel = new TypeModel();
		TypeDeclaration[] innerTypes = type.getTypes();
		for (TypeDeclaration innerType : innerTypes) {
			typeModel.addInnerClass(innerClassType(innerType));
		}
		
		return typeModel;		
	}
	
	private JModifier getJModifier(ASTNode node){
		List<IExtendedModifier> ieModifiers = new LinkedList<IExtendedModifier>();
		if (node instanceof TypeDeclaration) {
			ieModifiers = ((TypeDeclaration) node).modifiers();	
			System.out.println("1");
		}else if (node instanceof EnumDeclaration) {
			 ieModifiers = ((EnumDeclaration) node).modifiers();
			 System.out.println("2");
		}else if (node instanceof MethodDeclaration) {
			ieModifiers = ((MethodDeclaration) node).modifiers();
			System.out.println("3");
		}else if (node instanceof FieldDeclaration) {
			ieModifiers = ((FieldDeclaration) node).modifiers();
			System.out.println("4");
		}else  {
			System.out.println("!!!!not include, check!!!");
		}
		
		JModifier jm = new JModifier();
		for (IExtendedModifier modifier : ieModifiers) {
			if (modifier.isModifier()) {
				
				jm.setABSTRACT(((Modifier) modifier).isAbstract());
				jm.setFINAL(((Modifier) modifier).isFinal());
				jm.setNATIVE(((Modifier) modifier).isNative());
				jm.setPUBLIC(((Modifier) modifier).isPublic());
				jm.setPRIVATE(((Modifier) modifier).isPrivate());
				jm.setPROTECTED(((Modifier) modifier).isProtected());
				jm.setSTATIC(((Modifier) modifier).isStatic());
				jm.setSTRICTFP(((Modifier) modifier).isStrictfp());
				jm.setSYNCHRONIZED(((Modifier) modifier).isSynchronized());
				jm.setTRANSIENT(((Modifier) modifier).isTransient());
				jm.setVOLATILE(((Modifier) modifier).isVolatile());
			}
		}
		
		
		return jm;
		
	}
	
	private String getTypeName(Type type){
		String string = null;
		if (type instanceof PrimitiveType) {
//			System.out.println("PrimitiveType");
			System.out.println(((PrimitiveType) type).toString());
			string = ((PrimitiveType) type).toString();
		}else if (type instanceof SimpleType) {
//			System.out.println("simpletype");
//			System.out.println(((SimpleType) type).getName());
			string = ((SimpleType) type).getName().toString();
		}else if (type instanceof ParameterizedType) {
//			System.out.println("ParameterizedType");
//			System.out.println(((ParameterizedType) type).getType());
			string = ((ParameterizedType) type).getType().toString();
		}else if (type instanceof ArrayType) {
//			System.out.println("ArrayType");
//			System.out.println(((ArrayType) type).getComponentType());
			string = ((ArrayType) type).getComponentType().toString();

		}else  if (type instanceof QualifiedType ) {
//			System.out.println("QualifiedType");
			((QualifiedType) type).getName().toString();
		}else if (type instanceof WildcardType) {
//			System.out.println("WildcardType");
			System.out.println("WildcardType"+getTypeName(((WildcardType) type).getBound()));
		}
		
		return string;
		
	}
	
/*	private FieldModel formFieldModel(FieldDeclaration node){
		FieldModel fieldModel;
		node.getModifiers();
		node.getType();
		node.fragments();
		
		return fieldModel;
	}
	*/
	public TypeModel getTypeModel(){
		return typeModel;
	}
	
	
}
