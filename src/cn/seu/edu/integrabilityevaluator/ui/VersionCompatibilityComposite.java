package cn.seu.edu.integrabilityevaluator.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import cn.seu.edu.integrabilityevaluator.model.AbstractClassModel;
import cn.seu.edu.integrabilityevaluator.model.ConstructorMethodModel;
import cn.seu.edu.integrabilityevaluator.model.MethodModel;

import cn.seu.edu.integrabilityevaluator.parser.Compatibility;
import cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder.ChangeStatus;
import cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder.ClassCompatibilityRecoder;
import cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder.ConstructorMethodRecoder;
import cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder.MethodRecoder;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;

public class VersionCompatibilityComposite extends Composite {
	private Text oldComponentText;
	private Text newComponentText;
	private Table changeTypeTable;
	private TableEditor editor = null;
	private final List<MyModel> myModels = new LinkedList<>();
	
	class MyModel{		
		
		String identifierString = new String();
		List<String> newAddList = null;
		List<String> removedList = null;
		List<String> unchangedList = null;
		List<String> compatibilitynewList = null;
		List<String> compatibilityoldList = null;
		
		public MyModel(String id) {
			// TODO Auto-generated constructor stub
			identifierString = id;
			newAddList = new LinkedList<>();
			removedList = new LinkedList<>();
			unchangedList = new LinkedList<>();
			compatibilitynewList = new LinkedList<>();
			compatibilityoldList = new LinkedList<>();
		}
		
		public boolean equals(Object o){

			if (o == this) {
				return true;
			}
			
			if (o instanceof MyModel) {
				return identifierString.equals(((MyModel) o).identifierString);
			}
			
			return false;
		}
		
	}
	String strings = new String();
	private Text ResultText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public VersionCompatibilityComposite(Composite parent, int style) {
		super(parent, style);
		
		Label lblProjectPath = new Label(this, SWT.NONE);
		lblProjectPath.setBounds(10, 10, 79, 17);
		lblProjectPath.setText("Project path1:");
		
		Label lblProjectPath_1 = new Label(this, SWT.NONE);
		lblProjectPath_1.setText("Project path2:");
		lblProjectPath_1.setBounds(10, 62, 79, 17);
		
		oldComponentText = new Text(this, SWT.BORDER);
		oldComponentText.setBounds(92, 7, 375, 22);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("D:/ProjectOfHW/junit/junit3.4");//"D:/ProjectOfHW/junit/junit3.4/src/junit/runner"
				folderDialog.open();
				
				oldComponentText.setText(folderDialog.getFilterPath());
				
			}
			
			
		});
		btnNewButton.setBounds(500, 10, 53, 22);
		btnNewButton.setText("Path...");
		
		newComponentText = new Text(this, SWT.BORDER);
		newComponentText.setBounds(92, 59, 375, 22);
		
		Button btnPath = new Button(this, SWT.NONE);
		btnPath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("D:/ProjectOfHW/junit/junit3.5");
				folderDialog.open();
				
				newComponentText.setText(folderDialog.getFilterPath());
				
			}
		});
		btnPath.setBounds(500, 57, 53, 22);
		btnPath.setText("Path...");
		
		changeTypeTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		changeTypeTable.setBounds(10, 120, 711, 406);
		changeTypeTable.setHeaderVisible(true);
		changeTypeTable.setLinesVisible(true);

		
		editor = new TableEditor(changeTypeTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		//String[] tableHeader = {"        包+类名        ","        新增方法         ", "        不兼容方法         ","        兼容的方法         ","        未变更方法         "};		
		String[] tableHeader = {"        Package.Class        ","        New methods         ", "        Incompathiblity methods         ","        Compatible methods         ","        Unchanged methods         "};		

		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(changeTypeTable, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    	
	    }
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);		
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				changeTypeTable.removeAll();
				
				myModels.clear();

				
				String oldPathOfComponet = oldComponentText.getText(); // "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String newPathOfComponet = newComponentText.getText(); //"D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";
								
				Compatibility compatibility = new Compatibility(oldPathOfComponet, newPathOfComponet);
				
				int unchangeCount = 0;
				int newCount = 0;
				int removedCount = 0;
				int compatibilityCount = 0;		

				
			
				List<ClassCompatibilityRecoder> unCompatibilityRecoders = compatibility.getUncompatibilityRecoders();					
				if (unCompatibilityRecoders.size()!=0) {
					TableItem uItem = new TableItem(changeTypeTable, SWT.NONE);
					//uItem.setText(new String[] {"修改的类","------","------","------","------"});
					uItem.setText(new String[] {"Changed classes","------","------","------","------"});
				}
				for(ClassCompatibilityRecoder tcr : unCompatibilityRecoders){					
	
					if (!tcr.getModifierRecoder().isCompatibility()){
						System.out.println("not compatibility");
					}
					
					
					//仅统计与public相关类
					int thenewCount = tcr.getMethodRecoder().getNewAddMehodNum()+tcr.getConstructorMethodRecoder().getNewAddMehodNum();
					int theunchangeCount = tcr.getMethodRecoder().getUnchangedMethodNum()+tcr.getConstructorMethodRecoder().getUnchangedMethodNum();
					int theremovedCount = tcr.getMethodRecoder().getRemovedMehodNum()+tcr.getConstructorMethodRecoder().getRemovedMehodNum();
					int thecompatibilityCount = tcr.getMethodRecoder().getCompatibilityMethodMap().size()+tcr.getConstructorMethodRecoder().getCompatibilityConstructorMethodMap().size();
					
					
					
					newCount += thenewCount;
					unchangeCount += theunchangeCount;
					removedCount += theremovedCount;
					compatibilityCount += thecompatibilityCount;
					
					MyModel myModel = null;
				
				    final TableItem item = new TableItem(changeTypeTable, SWT.NONE);
					if (tcr.getMethodChangeStatus().equals(ChangeStatus.UNCHANGED)) {
						String string[] = {tcr.getNewTypeModel().getPackage() + " " + tcr.getNewTypeModel().getClassName()," new:"+thenewCount," uncompatibility:" + theremovedCount," compatibility:"+thecompatibilityCount," unchange:"+theunchangeCount};
						myModel = new MyModel(string[0]);
						item.setText(string);
						
						List<MethodModel> oldlist = tcr.getMethodRecoder().getUnchangedMethodModels();
						for (MethodModel methodModel : oldlist) {						
							if (methodModel.getModifier().isPUBLIC()) {
								myModel.unchangedList.add(methodModel.getFullName());
							}
						}
						
						List<ConstructorMethodModel> coldlist = tcr.getConstructorMethodRecoder().getUnchangedMethodModels();
						for (ConstructorMethodModel methodModel : coldlist) {						
							if (methodModel.getModifier().isPUBLIC()) {
								myModel.unchangedList.add(methodModel.getFullName());
							}
						}						
						myModels.add(myModel);
						continue;
					}else {
						String string[] = {tcr.getNewTypeModel().getPackage() + " " + tcr.getNewTypeModel().getClassName()," new:"+thenewCount," uncompatibility:" + theremovedCount," compatibility:"+thecompatibilityCount," unchange:"+theunchangeCount};	
						myModel = new MyModel(string[0]);
						item.setText(string);
					}
					
					
							
					String itemString ="***"+ tcr.getNewTypeModel().getPackage() + " " + tcr.getNewTypeModel().getClassName() + " Method Count"+
							" new:"+thenewCount + " uncompatibility:" + theremovedCount +" compatibility:" + theunchangeCount +" modified:"+thecompatibilityCount;
					
					System.out.println(itemString);					
					
					MethodRecoder mRecoder = tcr.getMethodRecoder();				
					Map<MethodModel, MethodModel> modifiedMap = mRecoder.getCompatibilityMethodMap();//getModifiedMethodMap();	
					for (MethodModel methodModel : modifiedMap.keySet()) {
						if (methodModel.getModifier().isPUBLIC()||modifiedMap.get(methodModel).getModifier().isPUBLIC()) {
							myModel.compatibilityoldList.add("old:" + methodModel.getFullName());
							myModel.compatibilitynewList.add("new:" + modifiedMap.get(methodModel).getFullName());
							if (methodModel.getModifier().isPUBLIC()&&modifiedMap.get(methodModel).getModifier().isPUBLIC()){
								unchangeCount++;
							}else if (methodModel.getModifier().isPUBLIC()&&modifiedMap.get(methodModel).getModifier().isPRIVATE()){
								removedCount++;							
							}else if(methodModel.getModifier().isPRIVATE()&&modifiedMap.get(methodModel).getModifier().isPUBLIC()){
								newCount++;
							}else {
								compatibilityCount++;
							}
						}						
					}
					
					
					List<MethodModel> newlist = mRecoder.getNewAddMethodModels();					
					for (MethodModel methodModel : newlist) {						
						if (methodModel.getModifier().isPUBLIC()) {
							myModel.newAddList.add(methodModel.getFullName());							
						}
					}
					
					ConstructorMethodRecoder cmRecoder = tcr.getConstructorMethodRecoder();	
					List<ConstructorMethodModel> cnewlist = cmRecoder.getNewAddMethodModels();					
					for (ConstructorMethodModel methodModel : cnewlist) {						
						if (methodModel.getModifier().isPUBLIC()) {
							myModel.newAddList.add(methodModel.getFullName());							
						}
					}
					
					
					
					List<MethodModel> removedlist = mRecoder.getRemovedMethodModels();					
					for (MethodModel methodModel : removedlist) {
						
						if (methodModel.getModifier().isPUBLIC()) {
							myModel.removedList.add(methodModel.getFullName());

						}
					}
					
					List<ConstructorMethodModel> cremovedlist = cmRecoder.getRemovedMethodModels();					
					for (ConstructorMethodModel methodModel : cremovedlist) {
						
						if (methodModel.getModifier().isPUBLIC()) {
							myModel.removedList.add(methodModel.getFullName());

						}
					}
					
					List<MethodModel> unlist = mRecoder.getUnchangedMethodModels();
					for (MethodModel methodModel : unlist) {						
						if (methodModel.getModifier().isPUBLIC()) {
							myModel.unchangedList.add(methodModel.getFullName());
						}
					}
					
					List<ConstructorMethodModel> cunlist = cmRecoder.getUnchangedMethodModels();
					for (ConstructorMethodModel methodModel : cunlist) {						
						if (methodModel.getModifier().isPUBLIC()) {
							myModel.unchangedList.add(methodModel.getFullName());
						}
					}
					myModels.add(myModel);				
				}									
				
				List<AbstractClassModel> removedType = compatibility.getRemovedTypeModels();	
				if (removedType.size()!=0) {
					TableItem uItem = new TableItem(changeTypeTable, SWT.NONE);
					//uItem.setText(new String[] {"删除的类","------","------","------","------"});
					uItem.setText(new String[] {"deleted classes","------","------","------","------"});
				}
				for(AbstractClassModel atm : removedType){
					final TableItem item = new TableItem(changeTypeTable, SWT.NONE);
					int theremovedCount =  atm.getPublicMethodNum();
					removedCount += theremovedCount;

					String string[] = {atm.getPackage() + " " + atm.getClassName()," new:"+ "0" , " removed:" + theremovedCount," compatibility:"+"0"," unchange:"+"0"};
					item.setText(string);
					MyModel myModel = new MyModel(string[0]);
					List<MethodModel> list = atm.getMethodModels();
					for (MethodModel methodModel : list) {						
						if (methodModel.getModifier().isPUBLIC()) {
							myModel.removedList.add(methodModel.getFullName());
							
						}
					}
					myModels.add(myModel);
				}				
				

				List<ClassCompatibilityRecoder> unchangeType = compatibility.getCompatibilityRecoders();
				if (unchangeType.size()!=0) {
					TableItem uItem = new TableItem(changeTypeTable, SWT.NONE);
					//uItem.setText(new String[] {"可兼容的类","------","------","------","------"});
					uItem.setText(new String[] {"Compatible classes","------","------","------","------"});
				}
				for(ClassCompatibilityRecoder tcr : unchangeType){
					final TableItem item = new TableItem(changeTypeTable, SWT.NONE);					
					MethodRecoder mRecoder = tcr.getMethodRecoder();
					ConstructorMethodRecoder cmRecoder = tcr.getConstructorMethodRecoder();
					
					int theunchangeCount = mRecoder.getUnchangedMethodNum()+cmRecoder.getUnchangedMethodNum()+cmRecoder.getUnchangedMethodNum();
					int thecompatibilityCount = mRecoder.getCompatibilityMethodMap().size()+cmRecoder.getCompatibilityConstructorMethodMap().size();

					
					unchangeCount += theunchangeCount;
					compatibilityCount += thecompatibilityCount;
					String string[] = {tcr.getNewTypeModel().getPackage() + " " + tcr.getNewTypeModel().getClassName()," new:"+"0" , " removed:" + "0"," compatibility:"+thecompatibilityCount," unchange:"+theunchangeCount};
					item.setText(string);
					MyModel myModel = new MyModel(string[0]);
					
					List<ConstructorMethodModel> clist = tcr.getConstructorMethodRecoder().getUnchangedMethodModels();
					for (ConstructorMethodModel methodModel : clist) {						
						if (methodModel.getModifier().isPUBLIC()) {
							myModel.unchangedList.add(methodModel.getFullName());
						}
					}
					
					List<MethodModel> list = mRecoder.getUnchangedMethodModels();
					for (MethodModel methodModel : list) {						
						if (methodModel.getModifier().isPUBLIC()) {							
								myModel.unchangedList.add(methodModel.getFullName());								
							
						}
					}		
					
					
					ConstructorMethodRecoder cmr = tcr.getConstructorMethodRecoder();
					//int count = 0;
					Map<ConstructorMethodModel, ConstructorMethodModel> cmap = cmr.getCompatibilityConstructorMethodMap();			
					//System.out.println(count);			
					for (ConstructorMethodModel methodModel : cmap.keySet()) {
						myModel.compatibilityoldList.add("old:"+methodModel.getFullName());
						myModel.compatibilitynewList.add("new:"+cmap.get(methodModel).getFullName());
						System.out.println("old:"+methodModel.getFullName());
						System.out.println("new:"+cmap.get(methodModel).getFullName());
						
					}
					
					
					MethodRecoder mr = tcr.getMethodRecoder();
					int count = 0;		
					System.out.println(count);
					Map<MethodModel, MethodModel> map = tcr.getMethodRecoder().getCompatibilityMethodMap();
					for (MethodModel methodModel : map.keySet()) {
						myModel.compatibilityoldList.add("old:"+methodModel.getFullName());
						myModel.compatibilitynewList.add("new:"+map.get(methodModel).getFullName());
						System.out.println("old:"+methodModel.getFullName());
						System.out.println("new:"+map.get(methodModel).getFullName());				
					}
					
					
					
					myModels.add(myModel);
				}		
					
				
				
				changeTypeTable.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseUp(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseDown(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						Control c = editor.getEditor();
						Point point = new Point(e.x, e.y);
						if (c!=null) {
							c.dispose();
						}				
						
						final TableItem tableitem = changeTypeTable.getItem(point);

						if (tableitem==null) {
							return;
						}
						
						int column = -1;
						for (int i = 0; i < changeTypeTable.getColumnCount(); i++) {
							Rectangle rec = tableitem.getBounds(i);
					        if (rec.contains(point))
					        	column = i;
					    }
					    try {
					    	final int col1 = column;
							final int row1 = changeTypeTable.getSelectionIndex();
						    
						    if (col1 == 1) {
						    	final Combo comb = new Combo(changeTypeTable, SWT.READ_ONLY);
						    	
						    	String id = changeTypeTable.getItem(row1).getText(0);
						    	List<String> list = myModels.get(myModels.indexOf(new MyModel(id))).newAddList;
						    	
						    	if (list.size() == 0) {
									return;
								}
						    	
							    comb.setItems( (String[]) list.toArray(new String[list.size()]));
							    comb.add("new:"+list.size(), 0);
							    
							    comb.addSelectionListener(new SelectionAdapter() {
							    	@Override
							    	public void widgetSelected(SelectionEvent e) {
							    		tableitem.setText(col1, comb.getText());
							    		comb.dispose();
							    		super.widgetSelected(e);
							    	}
							    });
							    editor.setEditor(comb, tableitem, col1);
							}else if (col1 == 2) {
								final Combo comb = new Combo(changeTypeTable, SWT.READ_ONLY);
								String id = changeTypeTable.getItem(row1).getText(0);
						    	List<String> list = myModels.get(myModels.indexOf(new MyModel(id))).removedList;
						    	
						    	if (list.size() == 0) {
									return;
								}
						    	
							    comb.setItems( (String[]) list.toArray(new String[list.size()]));
							    comb.add("uncompatibility:"+list.size(), 0);
							    comb.addSelectionListener(new SelectionAdapter() {
							    	@Override
							    	public void widgetSelected(SelectionEvent e) {
							    		tableitem.setText(col1, comb.getText());
							    		comb.dispose();
							    		super.widgetSelected(e);
							    	}
							    });
							    editor.setEditor(comb, tableitem, col1);
							}else if (col1 == 3) {
								final Combo comb = new Combo(changeTypeTable, SWT.READ_ONLY);
								
								String id = changeTypeTable.getItem(row1).getText(0);
						    	List<String> newlist = myModels.get(myModels.indexOf(new MyModel(id))).compatibilitynewList;
						    	List<String> oldlist = myModels.get(myModels.indexOf(new MyModel(id))).compatibilityoldList;

						    	if (newlist.size() == 0||oldlist.size() == 0) {
									return;
								}
						    	//newlist.addAll(oldlist);
							    comb.setItems( (String[]) newlist.toArray(new String[newlist.size()]));
							    int begin = newlist.size();
							    for (int i = 0; i < oldlist.size(); i++) {
									comb.add(oldlist.get(i),i+begin);
								}
							    comb.add("compatibility:"+newlist.size(), 0);
							    comb.addSelectionListener(new SelectionAdapter() {
							    	@Override
							    	public void widgetSelected(SelectionEvent e) {
							    		tableitem.setText(col1, comb.getText());
							    		comb.dispose();
							    		super.widgetSelected(e);
							    	}
							    });
							    editor.setEditor(comb, tableitem, col1);
							}else if (col1 == 4) {
								final Combo comb = new Combo(changeTypeTable, SWT.READ_ONLY);
								
								String id = changeTypeTable.getItem(row1).getText(0);
						    	List<String> list = myModels.get(myModels.indexOf(new MyModel(id))).unchangedList;
							
						    	
						    	if (list.size() == 0) {
									return;
								}					    	
						    	
							    comb.setItems( (String[]) list.toArray(new String[list.size()]));
							    comb.add("unchange:"+list.size(), 0);
							    comb.addSelectionListener(new SelectionAdapter() {
							    	@Override
							    	public void widgetSelected(SelectionEvent e) {
							    		tableitem.setText(col1, comb.getText());
							    		
							    		comb.dispose();
							    		super.widgetSelected(e);
							    	}
							    });
							    editor.setEditor(comb, tableitem, col1);
							}				
						
						} catch (Exception e2) {
							// TODO: handle exception
							System.out.println("out off index");
						}
					}
				});	
				
				if (unchangeCount+removedCount>0) {
					//TableItem lastItem = new TableItem(changeTypeTable, SWT.NONE);
					Double double1 = new Double(1.0*(unchangeCount+compatibilityCount)/(unchangeCount+removedCount+compatibilityCount));
					//lastItem.setText(new String[] {"新版本与旧版本相兼容的接口个数:"+(unchangeCount+compatibilityCount),"旧版本软件的接口数:"+(unchangeCount+removedCount+compatibilityCount),double1.toString()});
					//ResultText.setText("版本1与版本2的兼容性为\t"+double1.toString());
					ResultText.setText("Compatible between version1 and version2:"+double1.toString());
				}
				
				System.out.println(newCount +"\t"+removedCount+"\t"+compatibilityCount+"\t"+unchangeCount);
			}
		});
		
		CompatibilityBtn.setBounds(583, 10, 79, 69);
		CompatibilityBtn.setText("Analysis");
		
		ResultText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		ResultText.setBounds(10, 85, 457, 23);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
