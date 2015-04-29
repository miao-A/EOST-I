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
import org.eclipse.jdt.core.dom.PackageDeclaration;
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
	
	public boolean visit(PackageDeclaration node){
		typeModel.setPackage(node.getName().toString());
		return true;
	}

	public boolean visit(TypeDeclaration node){
	
		//内部类或匿名类
		if (node.isMemberTypeDeclaration()) {
			return true;
		}
		

		
		String string=node.getName().getIdentifier();
		typeModel.setClassName(string);
		System.out.println("Type:\t"+node.getName().getIdentifier());
		if (node.getSuperclassType() != null) {
			typeModel.setSuperClass(node.getSuperclassType().toString());
		}		
		
		typeModel.setINTERFACE(node.isInterface());
		
		//class继承的接口类
		List<Type> list = node.superInterfaceTypes();
		for (Type interfaceType : list) {
			typeModel.setSuperInterfaceType(interfaceType.toString());
		}

		//class的属性
		typeModel.setModifier(getJModifier(node));	 
		
		//class的父类
		if (node.getSuperclassType() != null) {
			typeModel.setSuperClass(node.getSuperclassType().toString());
		}
	
	
		//处理field //声明部分记录，未记录后面的初始化
		/*FieldDeclaration[] fields = node.getFields();
		for (FieldDeclaration fieldDeclaration : fields) {
			FieldModel fieldModel = new FieldModel();
			fieldModel.setModifier(getJModifier(fieldDeclaration));
			fieldModel.setType(fieldDeclaration.getType().toString());
			List<VariableDeclarationFragment> variableDeclarationFragments = fieldDeclaration.fragments();
			for (VariableDeclarationFragment vdf :variableDeclarationFragments) {
				fieldModel.setFieldName(vdf.getName().toString());
				typeModel.addFieldModel(fieldModel);
			}
		}*/
		typeModel.setFieldModels(getFieldModels(node));
		
		
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
		return type.toString();		
	}


	private List<FieldModel> getFieldModels(TypeDeclaration node){
		List<FieldModel> list = new LinkedList<>(); 
		FieldDeclaration[] fields = node.getFields();
		for (FieldDeclaration fieldDeclaration : fields) {
			FieldModel fieldModel = new FieldModel();
			fieldModel.setModifier(getJModifier(fieldDeclaration));
			fieldModel.setType(fieldDeclaration.getType().toString());
			List<VariableDeclarationFragment> variableDeclarationFragments = fieldDeclaration.fragments();
			for (VariableDeclarationFragment vdf :variableDeclarationFragments) {
				fieldModel.setFieldName(vdf.getName().toString());
				list.add(fieldModel);
			}
		}		
		return list;
	}
	
	public TypeModel getTypeModel(){
		return typeModel;
	}
	
	
}
