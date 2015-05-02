package seu.EOSTI.ASTVisitor;

import java.util.LinkedList;
import java.util.List;


import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Initializer;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import seu.EOSTI.Model.EnumModel;
import seu.EOSTI.Model.FieldModel;
import seu.EOSTI.Model.JModifier;
import seu.EOSTI.Model.MethodModel;
import seu.EOSTI.Model.SingleVariableModel;
import seu.EOSTI.Model.TypeModel;

public class ComponentVisitor extends ASTVisitor {

	private TypeModel typeModel;
	private EnumModel enumModel;
	
	public ComponentVisitor(){
		typeModel = new TypeModel();
		enumModel = new EnumModel();
	}
	
	public boolean visit(PackageDeclaration node){
		typeModel.setPackage(node.getName().toString());
		return true;
	}

	public boolean visit(EnumDeclaration node){
		if (node.isMemberTypeDeclaration()) {
			return true;
		}
		enumModel = getEnumModel(node);
		return true;

		
	}
	
	
	public boolean visit(TypeDeclaration node){
	
		//内部类或匿名类
		if (node.isMemberTypeDeclaration()) {
			return true;
		}
		typeModel = getClassType(node);
		return true;
	}
	
	private TypeModel getClassType(TypeDeclaration node){
		TypeModel typeModel = new TypeModel();
		String string=node.getName().getIdentifier();
		typeModel.setClassName(string);
		
		System.out.println("Type:\t"+node.getName().getIdentifier());
		if (node.getSuperclassType() != null) {
			typeModel.setSuperClass(node.getSuperclassType().toString());
		}		
		
		List<TypeParameter> typeParameters = node.typeParameters();
		for (TypeParameter typeParameter : typeParameters) {
			System.out.println("typeParameter:"+typeParameter.getName());
			typeModel.addTypeParameter(typeParameter.getName().toString());
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
		typeModel.setFieldModels(getFieldModels(node));	
		
		//处理method //记录函数签名
		typeModel.setMethodModels(getMethodModels(node));
		
		//处理Body部分类型
		List bd = node.bodyDeclarations();
		for (Object object : bd) {
			if (object instanceof EnumDeclaration) {
//				System.out.println("EnumDeclaration:\t"+((EnumDeclaration) object).getName());
				typeModel.addEnumClassModel(getEnumModel((EnumDeclaration) object));
			} else if(object instanceof TypeDeclaration){
				typeModel.addInnerClass(getClassType((TypeDeclaration)object));
			} else if (object instanceof MethodDeclaration) {

			} else if (object instanceof FieldDeclaration) {
//				System.out.println("FieldDeclarationType:\t"+((FieldDeclaration) object).getType());
				
			} else if(object instanceof Initializer) {
				System.out.println("Initializer");
			} 
		}		
		
		return typeModel;
	
	}	
	
	private EnumModel getEnumModel(EnumDeclaration node){
		
		EnumModel enumModel = new EnumModel();
		String string = node.getName().getIdentifier();
		enumModel.setClassName(string);
		System.out.println("EnumType:\t"+node.getName().getIdentifier());
		
		//class继承的接口类
		List<Type> list = node.superInterfaceTypes();
		for (Type interfaceType : list) {
			enumModel.setSuperInterfaceType(interfaceType.toString());
		}

		//class的属性
		enumModel.setModifier(getJModifier(node));	
		
		//处理field //声明部分记录，未记录后面的初始化	
		enumModel.setFieldModels(getFieldModels(node));	
		
		//处理method 
		enumModel.setMethodModels(getMethodModels(node));
		
		
		List<EnumConstantDeclaration> list2= ((EnumDeclaration) node).enumConstants();
		  for (EnumConstantDeclaration enumConstantDeclaration : list2) {
//			System.out.println(enumConstantDeclaration.getName());
			enumModel.addEnumConstant(enumConstantDeclaration.getName().toString());
		  }
		
		
		
		//处理枚举类型
		List bd = node.bodyDeclarations();
		for (Object object : bd) {
			if (object instanceof EnumDeclaration) {
//				System.out.println("EnumDeclaration:\t"+((EnumDeclaration) object).getName());
				enumModel.addEnumClassModel(getEnumModel((EnumDeclaration) object));
			} else if(object instanceof TypeDeclaration){
				enumModel.addInnerClass(getClassType((TypeDeclaration)object));
			} else if (object instanceof MethodDeclaration) {

			} else if (object instanceof FieldDeclaration) {
//				System.out.println("FieldDeclarationType:\t"+((FieldDeclaration) object).getType());
			} else if(object instanceof Initializer) {
				System.out.println("Initializer");
			} 
		}		
		
		return enumModel;
	}
	
	
	private JModifier getJModifier(ASTNode node){
		List<IExtendedModifier> ieModifiers = new LinkedList<IExtendedModifier>();
		if (node instanceof TypeDeclaration) {
			ieModifiers = ((TypeDeclaration) node).modifiers();	

		}else if (node instanceof EnumDeclaration) {
			 ieModifiers = ((EnumDeclaration) node).modifiers();

		}else if (node instanceof MethodDeclaration) {
			ieModifiers = ((MethodDeclaration) node).modifiers();

		}else if (node instanceof FieldDeclaration) {
			ieModifiers = ((FieldDeclaration) node).modifiers();
			
		}else if (node instanceof SingleVariableDeclaration) {
			ieModifiers = ((SingleVariableDeclaration) node).modifiers();
		}else {
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

	private List<FieldModel> getFieldModels(ASTNode node){
		
		List<FieldModel> list = new LinkedList<>(); 	
		if (node instanceof TypeDeclaration) {
			FieldDeclaration[] fields = ((TypeDeclaration) node).getFields();
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

		}else if (node instanceof EnumDeclaration) {			  
			  List<BodyDeclaration> list2= ((EnumDeclaration) node).bodyDeclarations();
			  for (BodyDeclaration bodyDeclaration : list2) {
				if (bodyDeclaration instanceof FieldDeclaration) {
					FieldModel fieldModel = new FieldModel();
					fieldModel.setModifier(getJModifier((FieldDeclaration)bodyDeclaration));
					fieldModel.setType(((FieldDeclaration)bodyDeclaration).getType().toString());
					List<VariableDeclarationFragment> variableDeclarationFragments = ((FieldDeclaration)bodyDeclaration).fragments();
					for (VariableDeclarationFragment vdf :variableDeclarationFragments) {
						fieldModel.setFieldName(vdf.getName().toString());
						list.add(fieldModel);
					}
				} 
			}	
		}		
		return list;
	}

	public List<MethodModel> getMethodModels(ASTNode node){
		
		List<MethodModel> list = new LinkedList<>(); 	
		if (node instanceof TypeDeclaration) {
			MethodDeclaration[] methods = ((TypeDeclaration) node).getMethods();
			for (MethodDeclaration methodDeclaration : methods) {
				MethodModel methodModel = new MethodModel();
				methodModel.setMethodName(methodDeclaration.getName().getIdentifier());
				methodModel.setModifier(getJModifier(methodDeclaration));
				if (methodDeclaration.isConstructor()){
					methodModel.setConstructor(methodDeclaration.isConstructor());					
				}else {
					methodModel.setReturnType(methodDeclaration.getReturnType2().toString());
				}
				
				List<TypeParameter> typeParameters = methodDeclaration.typeParameters();
				for (TypeParameter typeParameter : typeParameters) {
					System.out.println("typeParameter:"+typeParameter.getName());
					methodModel.addTypeParameter(typeParameter.getName().toString());					
				}
				
				List<SingleVariableDeclaration> singleVariableDeclarations = methodDeclaration.parameters();
				for (SingleVariableDeclaration singleVariableDeclaration : singleVariableDeclarations) {
					SingleVariableModel svm = new SingleVariableModel();
					svm.setModifier(getJModifier(singleVariableDeclaration));
					svm.setType(singleVariableDeclaration.getType().toString());
					svm.setVarargs(singleVariableDeclaration.isVarargs());
					svm.setExtraDimensions(singleVariableDeclaration.getExtraDimensions());
					methodModel.addFormalParameters(svm);
				}
				methodModel.setExtraDimensions(methodDeclaration.getExtraDimensions());
				List<Name> throwList = methodDeclaration.thrownExceptions();
				for (Name name : throwList) {
					methodModel.addThrownList(name.getFullyQualifiedName());
				}
				list.add(methodModel);
			}		

		}else if (node instanceof EnumDeclaration) {			  
			  List<BodyDeclaration> list2= ((EnumDeclaration) node).bodyDeclarations();
			  for (BodyDeclaration bodyDeclaration : list2) {
				if (bodyDeclaration instanceof MethodDeclaration) {
					MethodModel methodModel = new MethodModel();
					methodModel.setModifier(getJModifier((MethodDeclaration)bodyDeclaration));
					if (((MethodDeclaration)bodyDeclaration).isConstructor()){
						methodModel.setConstructor(((MethodDeclaration)bodyDeclaration).isConstructor());					
					}else {
						methodModel.setReturnType(((MethodDeclaration)bodyDeclaration).getReturnType2().toString());
					}
					
					List<TypeParameter> typeParameters = ((MethodDeclaration)bodyDeclaration).typeParameters();
					for (TypeParameter typeParameter : typeParameters) {
						System.out.println("typeParameter:"+typeParameter.getName());
						methodModel.addTypeParameter(typeParameter.getName().toString());					
					}
					
					List<SingleVariableDeclaration> singleVariableDeclarations = ((MethodDeclaration)bodyDeclaration).parameters();
					for (SingleVariableDeclaration singleVariableDeclaration : singleVariableDeclarations) {
						SingleVariableModel svm = new SingleVariableModel();
						svm.setModifier(getJModifier(singleVariableDeclaration));
						svm.setType(singleVariableDeclaration.getType().toString());
						svm.setVarargs(singleVariableDeclaration.isVarargs());
						svm.setExtraDimensions(singleVariableDeclaration.getExtraDimensions());
						methodModel.addFormalParameters(svm);
					}
					methodModel.setExtraDimensions(((MethodDeclaration)bodyDeclaration).getExtraDimensions());
					List<Name> throwList = ((MethodDeclaration)bodyDeclaration).thrownExceptions();
					for (Name name : throwList) {
						methodModel.addThrownList(name.getFullyQualifiedName());
					}
					list.add(methodModel);				
				} 
			
			}	
		}		
		return list;
	}
	
	
	public TypeModel getTypeModel(){
		return typeModel;
	}
	
	
}
