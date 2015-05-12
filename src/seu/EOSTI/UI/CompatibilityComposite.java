package seu.EOSTI.UI;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.osgi.baseadaptor.bundlefile.MRUBundleFileList;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.internal.dnd.SwtUtil;

import seu.EOSTI.Model.AbstractTypeModel;
import seu.EOSTI.Model.ChangeStatus;
import seu.EOSTI.Model.MethodModel;
import seu.EOSTI.Model.MethodRecoder;
import seu.EOSTI.Model.SingleVariableModel;
import seu.EOSTI.Model.TypeChangeRecoder;
import seu.EOSTI.Parser.Compatibility;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;


public class CompatibilityComposite extends Composite {
	private Text oldComponentText;
	private Text newComponentText;
	private Table changeTypeTable;
	private TableEditor editor = null;
	private final List<MyModel> myModels = new LinkedList<>();
	
	class MyModel{
		
		public MyModel(String id) {
			// TODO Auto-generated constructor stub
			identifierString = id;
			newAddList = new LinkedList<>();
			removedList = new LinkedList<>();
			unchangedList = new LinkedList<>();
			modifiednewList = new LinkedList<>();
			modifiedoldList = new LinkedList<>();
		}
		String identifierString = new String();
		List<String> newAddList = null;
		List<String> removedList = null;
		List<String> unchangedList = null;
		List<String> modifiednewList = null;
		List<String> modifiedoldList = null;
		
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

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompatibilityComposite(Composite parent, int style) {
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
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui");
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
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui");
				folderDialog.open();
				
				newComponentText.setText(folderDialog.getFilterPath());
				
			}
		});
		button.setBounds(500, 57, 53, 22);
		button.setText("\u8DEF\u5F84...");
		
		changeTypeTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		changeTypeTable.setBounds(10, 85, 672, 441);
		changeTypeTable.setHeaderVisible(true);
		changeTypeTable.setLinesVisible(true);

		
		editor = new TableEditor(changeTypeTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				changeTypeTable.removeAll();

				
				String oldPathOfComponet = "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String newPathOfComponet = "D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";
								
				Compatibility compatibility = new Compatibility(oldPathOfComponet, newPathOfComponet);
				
				int unchangeCount = 0;
				int newCount = 0;
				int removedCount = 0;
				int modifiedCount = 0;
						

				String[] tableHeader = {"        包+类名        ","        新增方法         ", "        删除方法         ","        修改方法         ","        未变更方法         "};
				
				for (int i = 0; i < tableHeader.length; i++)  
			    {  
					
					TableColumn tableColumn = new TableColumn(changeTypeTable, SWT.NONE);  
					tableColumn.setText(tableHeader[i]);  
					// 设置表头可移动，默认为false  
					tableColumn.setMoveable(false); 
			    	tableColumn.pack();
			    }
			
				List<TypeChangeRecoder> modifiedType = compatibility.getModifiedRecoders();					
				if (modifiedType.size()!=0) {
					TableItem uItem = new TableItem(changeTypeTable, SWT.NONE);
					uItem.setText(new String[] {"修改的类","------","------","------","------"});
				}
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
					
					MyModel myModel = null;
				
				    final TableItem item = new TableItem(changeTypeTable, SWT.NONE);
					if (tcr.getMethodChangeStatus().equals(ChangeStatus.UNCHANGED)) {
						String string[] = {tcr.getNewTypeModel().getPackage() + " " + tcr.getNewTypeModel().getClassName()," new:"+thenewCount," removed:" + theremovedCount," modified:"+themodifiedCount," unchange:"+theunchangeCount};
						myModel = new MyModel(string[0]);
						item.setText(string);
						
						List<MethodModel> oldlist = tcr.getOldTypeModel().getMethodModels();
						for (MethodModel methodModel : oldlist) {						
							if (methodModel.getModifier().isPUBLIC()) {
								myModel.unchangedList.add(methodModel.getFullName());
							}
						}				
						
						myModels.add(myModel);
						continue;
					}else {
						String string[] = {tcr.getNewTypeModel().getPackage() + " " + tcr.getNewTypeModel().getClassName()," new:"+thenewCount," removed:" + theremovedCount," modified:"+themodifiedCount," unchange:"+theunchangeCount};	
						myModel = new MyModel(string[0]);
						item.setText(string);
					}
					
					
							
					String itemString ="***"+ tcr.getNewTypeModel().getPackage() + " " + tcr.getNewTypeModel().getClassName() + " Method Count"+
							" new:"+thenewCount + " removed:" + theremovedCount +" unchange:"+theunchangeCount +" modified:"+themodifiedCount;
					
					System.out.println(itemString);
					
					
					
					MethodRecoder mRecoder = tcr.getMethodRecoder();				
					Map<MethodModel, MethodModel> modifiedMap = mRecoder.getModifiedMethodMap();	
					for (MethodModel methodModel : modifiedMap.keySet()) {
						if (methodModel.getModifier().isPUBLIC()) {
							myModel.modifiedoldList.add("old:" + methodModel.getFullName());
							myModel.modifiednewList.add("new:" + modifiedMap.get(methodModel).getFullName());
						}						
					}
					
					List<MethodModel> newlist = mRecoder.getNewAddMethodModels();					
					for (MethodModel methodModel : newlist) {						
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
					
					
					List<MethodModel> oldlist = tcr.getOldTypeModel().getMethodModels();
					for (MethodModel methodModel : oldlist) {						
						if (methodModel.getModifier().isPUBLIC()) {
							myModel.unchangedList.add(methodModel.getFullName());
						}
					}
				
				
					myModels.add(myModel);
				
				}
				
								
						
				
				
				
				List<AbstractTypeModel> removedType = compatibility.getRemovedTypeModels();	
				if (removedType.size()!=0) {
					TableItem uItem = new TableItem(changeTypeTable, SWT.NONE);
					uItem.setText(new String[] {"删除的类","------","------","------","------"});
				}
				for(AbstractTypeModel atm : removedType){
					final TableItem item = new TableItem(changeTypeTable, SWT.NONE);
					int theremovedCount =  atm.getPublicMethodNum();
					removedCount += theremovedCount;

					String string[] = {atm.getPackage() + " " + atm.getClassName()," new:"+ "0" , " removed:" + theremovedCount," modified:"+"0"," unchange:"+"0"};
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
				
				
				

				List<TypeChangeRecoder> unchangeType = compatibility.getUnchangedRecoders();
				if (unchangeType.size()!=0) {
					TableItem uItem = new TableItem(changeTypeTable, SWT.NONE);
					uItem.setText(new String[] {"未变更的类","------","------","------","------"});
				}
				for(TypeChangeRecoder tcr : unchangeType){
					final TableItem item = new TableItem(changeTypeTable, SWT.NONE);					
					MethodRecoder mRecoder = tcr.getMethodRecoder();
					int theunchangeCount = mRecoder.getUnchangedMethodNum();
					unchangeCount += theunchangeCount;
					String string[] = {tcr.getNewTypeModel().getPackage() + " " + tcr.getNewTypeModel().getClassName()," new:"+"0" , " removed:" + "0"," modified:"+"0"," unchange:"+theunchangeCount};
					item.setText(string);
					MyModel myModel = new MyModel(string[0]);
					List<MethodModel> list = mRecoder.getUnchangedMethodModels();
					for (MethodModel methodModel : list) {						
						if (methodModel.getModifier().isPUBLIC()) {
							if (methodModel.getModifier().isPUBLIC()) {
								myModel.unchangedList.add(methodModel.getFullName());
								
							}
						}
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
						    comb.add("removed:"+list.size(), 0);
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
					    	List<String> newlist = myModels.get(myModels.indexOf(new MyModel(id))).modifiednewList;
					    	List<String> oldlist = myModels.get(myModels.indexOf(new MyModel(id))).modifiedoldList;

					    	if (newlist.size() == 0||oldlist.size() == 0) {
								return;
							}
					    	newlist.addAll(oldlist);
						    comb.setItems( (String[]) newlist.toArray(new String[newlist.size()]));
						    comb.add("modified:"+newlist.size(), 0);
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
					}
				});	
		
				System.out.println(newCount +"\t"+removedCount+"\t"+modifiedCount+"\t"+unchangeCount);
			}
		});
		
		CompatibilityBtn.setBounds(583, 10, 79, 69);
		CompatibilityBtn.setText("\u5206\u6790");
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
