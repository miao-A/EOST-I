package seu.EOSTI.UI;

import java.util.List;
import java.util.Map;

import org.eclipse.osgi.baseadaptor.bundlefile.MRUBundleFileList;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.internal.dnd.SwtUtil;

import seu.EOSTI.Model.AbstractTypeModel;
import seu.EOSTI.Model.ChangeStatus;
import seu.EOSTI.Model.MethodModel;
import seu.EOSTI.Model.MethodRecoder;
import seu.EOSTI.Model.SingleVariableModel;
import seu.EOSTI.Model.TypeChangeRecoder;
import seu.EOSTI.Parser.Compatibility;


public class CompatibilityComposite1 extends Composite {
	private Text oldComponentText;
	private Text newComponentText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompatibilityComposite1(Composite parent, int style) {
		super(parent, style);
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(10, 10, 103, 17);
		label.setText("\u8001\u7248\u672C\u6784\u4EF6\u8DEF\u5F84\uFF1A");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("\u65B0\u7248\u672C\u6784\u4EF6\u8DEF\u5F84\uFF1A");
		label_1.setBounds(10, 62, 103, 17);
		
		oldComponentText = new Text(this, SWT.BORDER);
		oldComponentText.setBounds(119, 10, 375, 22);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.2");
				folderDialog.open();
				
				oldComponentText.setText(folderDialog.getFilterPath());
				
			}
			
			
		});
		btnNewButton.setBounds(500, 10, 53, 22);
		btnNewButton.setText("\u8DEF\u5F84...");
		
		newComponentText = new Text(this, SWT.BORDER);
		newComponentText.setBounds(119, 57, 375, 22);
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("");
				folderDialog.open();
				
				newComponentText.setText(folderDialog.getFilterPath());
				
			}
		});
		button.setBounds(500, 57, 53, 22);
		button.setText("\u8DEF\u5F84...");
		
		final Tree methodInfoTree = new Tree(this, SWT.BORDER);
		methodInfoTree.setHeaderVisible(true);
		methodInfoTree.setLinesVisible(true);
		methodInfoTree.setBounds(20, 85, 645, 465);
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				methodInfoTree.removeAll();

				
				String oldPathOfComponet = "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String newPathOfComponet = "D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";
								
				Compatibility compatibility = new Compatibility(oldPathOfComponet, newPathOfComponet);
				
				int unchangeCount = 0;
				int newCount = 0;
				int removedCount = 0;
				int modifiedCount = 0;
								
				TreeItem topItem1 = new TreeItem(methodInfoTree, 0);
				topItem1.setText("modified Types");
				
				TreeItem topItem2 = new TreeItem(methodInfoTree, 0);
				topItem2.setText("Removed Types");
				
				TreeItem topItem3 = new TreeItem(methodInfoTree, 0);
				topItem3.setText("unchanged Types");
			
				methodInfoTree.setTopItem(topItem1);
				methodInfoTree.setTopItem(topItem2);
				methodInfoTree.setTopItem(topItem3);
				
				List<TypeChangeRecoder> modifiedType = compatibility.getModifiedRecoders();				
				for(TypeChangeRecoder tcr : modifiedType){					
	
					//仅统计与public相关类
					int thenewCount = tcr.getMethodRecoder().getNewAddMehodNum();
					int theunchangeCount = tcr.getMethodRecoder().getUnchangedMethodNum();
					int theremovedCount = tcr.getMethodRecoder().getRemovedMehodNum();
					int themodifiedCount = tcr.getMethodRecoder().getModifiedMethodNum();
					
					newCount += thenewCount;
					unchangeCount += theunchangeCount;
					removedCount += theremovedCount;
					modifiedCount += themodifiedCount;
					
					TreeItem typeItem;
					if (tcr.getMethodChangeStatus().equals(ChangeStatus.UNCHANGED)) {
						System.out.println("###"+tcr.getNewTypeModel().getPackage() + " " + tcr.getNewTypeModel().getClassName());
						typeItem = new TreeItem(topItem3, 0);
					}else {
						typeItem = new TreeItem(topItem1, 0);
					}
					
					
					String itemString = tcr.getNewTypeModel().getPackage() + " " + tcr.getNewTypeModel().getClassName() + " Method Count"+
							" new:"+thenewCount + " removed:" + theremovedCount +" unchange:"+theunchangeCount +" modified:"+themodifiedCount;
					typeItem.setText(itemString);
					System.out.println(itemString);
					MethodRecoder mRecoder = tcr.getMethodRecoder();
					
					
					Map<MethodModel, MethodModel> modifiedMap = mRecoder.getModifiedMethodMap();	
					for (MethodModel methodModel : modifiedMap.keySet()) {
						if (methodModel.getModifier().isPUBLIC()) {
							TreeItem methodItem = new TreeItem(typeItem,SWT.NONE);
							methodItem.setText("old:" + methodModel.getFullName());
							methodItem.setText("new:" + modifiedMap.get(methodModel).getFullName());
						}						
					}
					
					List<MethodModel> newlist = mRecoder.getNewAddMethodModels();					
					for (MethodModel methodModel : newlist) {
						
						if (methodModel.getModifier().isPUBLIC()) {
							TreeItem methodItem = new TreeItem(typeItem,SWT.NONE);
							methodItem.setText("new:" + methodModel.getFullName());
						}
					}	
					
					List<MethodModel> removedlist = mRecoder.getRemovedMethodModels();					
					for (MethodModel methodModel : removedlist) {
						
						if (methodModel.getModifier().isPUBLIC()) {
							TreeItem methodItem = new TreeItem(typeItem,SWT.NONE);
							methodItem.setText("remove:"+methodModel.getFullName());
						}
					}	
					
					
//					typeItem.setText(tcr.getOldTypeModel().getPackage()+" 2" +tcr.getOldTypeModel().getClassName());
					List<MethodModel> oldlist = tcr.getOldTypeModel().getMethodModels();
					for (MethodModel methodModel : oldlist) {						
						if (methodModel.getModifier().isPUBLIC()) {
							TreeItem methodItem = new TreeItem(typeItem,SWT.NONE);
							methodItem.setText(methodModel.getFullName());
						}
					}
					
								
				}			
				
				
				
				List<AbstractTypeModel> removedType = compatibility.getRemovedTypeModels();				
				for(AbstractTypeModel atm : removedType){	
					int theremovedCount =  atm.getPublicMethodNum();
					removedCount += theremovedCount;
					TreeItem typeItem = new TreeItem(topItem2, 0);					
					
					typeItem.setText(atm.getPackage()+" " +atm.getClassName()+" removed Methods:"+theremovedCount);
					List<MethodModel> list = atm.getMethodModels();
					for (MethodModel methodModel : list) {
						
						if (methodModel.getModifier().isPUBLIC()) {
						    TreeItem methodItem = new TreeItem(typeItem,SWT.NONE);
							methodItem.setText(methodModel.getFullName());
						}
					}
				}
				
				
				

				List<TypeChangeRecoder> unchangeType = compatibility.getUnchangedRecoders();				
				for(TypeChangeRecoder tcr : unchangeType){
					TreeItem typeItem = new TreeItem(topItem3, 0);
					MethodRecoder mRecoder = tcr.getMethodRecoder();
					int theunchangeCount = mRecoder.getUnchangedMethodNum();
					unchangeCount += theunchangeCount;
					
					typeItem.setText(tcr.getOldTypeModel().getPackage()+" " + tcr.getOldTypeModel().getClassName() 
							+ " Methods: " + theunchangeCount);
					List<MethodModel> list = mRecoder.getUnchangedMethodModels();
					for (MethodModel methodModel : list) {						
						if (methodModel.getModifier().isPUBLIC()) {
							TreeItem methodItem = new TreeItem(typeItem,SWT.NONE);
							methodItem.setText(methodModel.getFullName());
						}
					}					
				}		
				
				
				System.out.println(newCount +"\t"+removedCount+"\t"+modifiedCount+"\t"+unchangeCount);
			}
		});
		CompatibilityBtn.setBounds(589, 10, 76, 69);
		CompatibilityBtn.setText("\u5206\u6790");
		


	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
}
