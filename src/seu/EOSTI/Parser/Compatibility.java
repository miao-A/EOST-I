package seu.EOSTI.Parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import seu.EOSTI.ASTVisitor.ComponentRequertor;
import seu.EOSTI.Model.AbstractTypeModel;
import seu.EOSTI.Model.ChangeStatus;
import seu.EOSTI.Model.MethodModel;
import seu.EOSTI.Model.MethodRecoder;
import seu.EOSTI.Model.SingleVariableModel;
import seu.EOSTI.Model.TypeChangeRecoder;

public class Compatibility {
	
	private String oldPathOfComponet;
	private String newPathOfComponet;
	private List<AbstractTypeModel> changeRecoder;
	
	private List<AbstractTypeModel> removedType = new LinkedList<>();
	private List<AbstractTypeModel> newType = new LinkedList<>();
	private List<TypeChangeRecoder> unchangedType = new LinkedList<>();
	private List<TypeChangeRecoder> modifiedType = new LinkedList<>();
	
	private List<TypeChangeRecoder>  typeChangeRecoders = new LinkedList<>();
	
	public Compatibility(String oldPathOfComponet,String newPathOfComponet) {
		// TODO Auto-generated constructor stub
		this.oldPathOfComponet = oldPathOfComponet;
		this.newPathOfComponet = newPathOfComponet;
		changeRecoder = new LinkedList<>();
		compatibilityParser(this.parserComponet(oldPathOfComponet),this.parserComponet(newPathOfComponet));		
	}
	
	public void compatibilityParser(List<AbstractTypeModel> oldModels,List<AbstractTypeModel> newModels){

		for (AbstractTypeModel oldTypeModel : oldModels) {
			if (!newModels.contains(oldTypeModel)) {
				removedType.add(oldTypeModel);
			}			
		}
		
		for (AbstractTypeModel newTypeModel : newModels) {
			if (!oldModels.contains(newTypeModel)) {
				newType.add(newTypeModel);
			}
		}
		
		for (AbstractTypeModel newTypeModel : newModels) {
			if (oldModels.contains(newTypeModel)){
				int index = oldModels.indexOf(newTypeModel);
				typeChangeRecoders.add(new TypeChangeRecoder(oldModels.get(index),newTypeModel));
			}
		}
		
		for (TypeChangeRecoder tc : typeChangeRecoders) {
			if (tc.getChangeStatus().equals(ChangeStatus.UNCHANGED)) {
				unchangedType.add(tc);			
			}else if(tc.getChangeStatus().equals(ChangeStatus.MODIFIED)){
				modifiedType.add(tc);
			}
		}
		
		this.getinfo();
		
	}
	
	
	public List<AbstractTypeModel> parserComponet(String pathOfComponet)  {
		// create a AST parser
		ASTParser parser;
		parser = ASTParser.newParser(AST.JLS4);
	
		Map<String,String> complierOptions= JavaCore.getDefaultOptions();
		complierOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
		parser.setCompilerOptions(complierOptions);
		parser.setEnvironment(null, null, null, true);
		
		// enable binding	
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		
		ComponentRequertor ComponentRequertor = new ComponentRequertor();
		ReadFile readFile = new ReadFile(pathOfComponet);		
		List<String> filelist = readFile.readJavaFiles();
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		parser.createASTs(sourceFilePaths,  null, new String[0], ComponentRequertor, null);	
		return ComponentRequertor.getTypeModels();
	}		
	
	public List<TypeChangeRecoder> getTypeChangeRecoders(){
		return typeChangeRecoders;
	}
	
	
	public List<AbstractTypeModel> getNewTypeModels(){
		return newType;
	}
	
	public List<AbstractTypeModel> getRemovedTypeModels(){
		return removedType;
	}
	
	public List<TypeChangeRecoder> getUnchangedRecoders(){
		return unchangedType;
	}
	
	public List<TypeChangeRecoder> getModifiedRecoders(){
		return modifiedType;
	}
	
	
	
	
	public void getinfo(){
		for(AbstractTypeModel atm : newType){
			System.out.println("newType:"+ atm.getPackage()+" " +atm.getClassName());
			
		}
		
		for(AbstractTypeModel atm : removedType){
			System.out.println("removedType:"+ atm.getPackage()+" " +atm.getClassName());
			List<MethodModel> list = atm.getMethodModels();
			int count = 0;
			for (MethodModel methodModel : list) {
				if (methodModel.getModifier().isPUBLIC()) {
					++count;
				}
			}
			System.out.println(count);
		}
		
		for(TypeChangeRecoder atm : unchangedType){
			System.out.println("unchangedType:"+ atm.getNewTypeModel().getPackage()+" " +atm.getNewTypeModel().getClassName());
			MethodRecoder mr = atm.getMethodRecoder();
			int count = 0;
			List<MethodModel> list = mr.getUnchangedMethodModels();
			for (MethodModel methodModel : list) {
				if (methodModel.getModifier().isPUBLIC()) {
					++count;
				}
			}
			System.out.println(count);
		}
		
		for(TypeChangeRecoder atm : modifiedType){
			System.out.println("modifiedType:"+ atm.getNewTypeModel().getPackage()+" " +atm.getNewTypeModel().getClassName());
			MethodRecoder mr = atm.getMethodRecoder();
			
			if (mr.getNewAddMethodModels().size()!=0) {
				System.out.println("NewAddMethod:");
			}
			

			for (MethodModel methodModel : mr.getNewAddMethodModels()) {
				System.out.print(methodModel.getModifier().getModifierInfo());
				System.out.print(methodModel.getReturnType()+" ");
				System.out.print(methodModel.getMethodName()+"(");
				List<SingleVariableModel> tpList =  methodModel.getFormalParameters();
				for (int i = 0; i < tpList.size(); i++) {
					System.out.print(tpList.get(i).getType()+" "+tpList.get(i).getName());
					if (i!=tpList.size()-1) {
						System.out.print(",");
					}
				}
				System.out.println(")");						
			}
	
			if (mr.getRemovedMethodModels().size()!=0) {
				System.out.println("RemovedMethod:");
			}
			
			for (MethodModel methodModel : mr.getRemovedMethodModels()) {
				System.out.print(methodModel.getModifier().getModifierInfo());
				System.out.print(methodModel.getReturnType()+" ");
				System.out.print(methodModel.getMethodName()+"(");
				List<SingleVariableModel> tpList =  methodModel.getFormalParameters();
				for (int i = 0; i < tpList.size(); i++) {
					System.out.print(tpList.get(i).getType()+" "+tpList.get(i).getName());
					if (i!=tpList.size()-1) {
						System.out.print(",");
					}
				}
				System.out.println(")");						
			}
			
			/*
			if (mr.getUnchangedMethodModels().size()!=0) {
				System.out.println("UnchangedMethod:");
			}
			for (MethodModel methodModel : mr.getUnchangedMethodModels()) {
				System.out.print(methodModel.getModifier().getModifierInfo());
				System.out.print(methodModel.getReturnType()+" ");
				System.out.print(methodModel.getMethodName()+"(");
				List<SingleVariableModel> tpList =  methodModel.getFormalParameters();
				for (int i = 0; i < tpList.size(); i++) {
					System.out.print(tpList.get(i).getType()+" "+tpList.get(i).getName());
					if (i!=tpList.size()-1) {
						System.out.print(",");
					}
				}
				System.out.println(")");						
			}*/
								
						
		}
	}
}
