package seu.EOSTI.UI;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import seu.EOSTI.Model.JarClassModel;
import seu.EOSTI.Model.UnCompatibilityMIModel;
import seu.EOSTI.Parser.InnerCompatibility;
import seu.EOSTI.Parser.OuterCompatibility;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.TableViewer;

public class OuterCompatibilityMutiVersionComposite extends Composite {
	private Text pathOfProjectText;
	private Text jarPathText;
	private Table jdkuncompatibilityTable;
	private TableEditor editor = null;
	
	String strings = new String();
	private Text jarDependPathText;
	private Table jaruncompatibilityTable;
	private Table table;
	private Text text;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OuterCompatibilityMutiVersionComposite(Composite parent, int style) {
		super(parent, style);
		
		Label label = new Label(this, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		label.setBounds(26, 39, 103, 17);
		label.setText("\u9879\u76EE\u8DEF\u5F841\uFF1A");
		
		Label lbljar = new Label(this, SWT.NONE);
		lbljar.setAlignment(SWT.RIGHT);
		lbljar.setText("\u5916\u90E8jar\u5305\u8DEF\u5F84\uFF1A");
		lbljar.setBounds(39, 114, 103, 17);
		
		pathOfProjectText = new Text(this, SWT.BORDER);
		pathOfProjectText.setBounds(135, 36, 346, 22);
		
		
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.2");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfProjectText.setText(folderDialog.getFilterPath());				
			}			
		});
		btnNewButton.setBounds(487, 36, 53, 22);
		btnNewButton.setText("\u8DEF\u5F84...");
		
		jarPathText = new Text(this, SWT.BORDER);
		jarPathText.setBounds(148, 111, 346, 22);
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setText("选择jar文件");
				fileDialog.setFilterPath("D:\\test");
				fileDialog.setFileName("jfreechart-1.0.19.jar");
				fileDialog.open();
								
				jarPathText.setText(fileDialog.getFilterPath()+"\\"+fileDialog.getFileName());				
			}
		});
		button.setBounds(500, 111, 53, 22);
		button.setText("\u8DEF\u5F84...");
		
		jdkuncompatibilityTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		jdkuncompatibilityTable.setBounds(10, 376, 711, 160);
		jdkuncompatibilityTable.setHeaderVisible(false);
		jdkuncompatibilityTable.setLinesVisible(true);
		
		jaruncompatibilityTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		jaruncompatibilityTable.setLinesVisible(true);
		jaruncompatibilityTable.setHeaderVisible(false);
		jaruncompatibilityTable.setBounds(10, 194, 711, 160);
		
		/*editor = new TableEditor(jdkuncompatibilityTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;*/
		
		/*String[] tableHeader = {"        包名        ","        位置         ", "       不兼容方法所在类        ","        不兼容方法名        ","    应使用类型    ","       实际使用类型         "};		
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(uncompatibilityTable, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    	
	    }*/
		
		
		
		Label lblJava = new Label(this, SWT.NONE);
		lblJava.setBounds(75, 13, 61, 17);
		lblJava.setText("Java\u7248\u672C\uFF1A");
		
		final Combo jdkVersionCombo = new Combo(this, SWT.NONE);
		jdkVersionCombo.setBounds(142, 10, 88, 25);
		String[] comboItems = {"1.7","1.6","1.5","1.4"};
		jdkVersionCombo.setItems(comboItems);
		jdkVersionCombo.select(0);
		
		String[] tableHeader = {"---------------------------","---------------------------------------------------------------------------"};	
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(jdkuncompatibilityTable, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(true); 
	    	tableColumn.pack();
	    	
	    }
		
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(jaruncompatibilityTable, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(true); 
	    	tableColumn.pack();
	    	
	    }
		
		Label lbljar_1 = new Label(this, SWT.NONE);
		lbljar_1.setAlignment(SWT.RIGHT);
		lbljar_1.setText("\u5916\u90E8jar\u5305\u4F9D\u8D56\u5305\u8DEF\u5F84\uFF1A");
		lbljar_1.setBounds(14, 139, 128, 17);
		
		Label label_2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(0, 94, 577, 2);
		
		Label label_3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(0, 167, 577, 2);
		
		jarDependPathText = new Text(this, SWT.BORDER);
		jarDependPathText.setBounds(148, 139, 346, 22);
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);		
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				jdkuncompatibilityTable.removeAll();
				jaruncompatibilityTable.removeAll();
				
						
				String pathOfProject = pathOfProjectText.getText(); // "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String jarPath = jarPathText.getText(); //"D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";
				String jarDependPath = jarDependPathText.getText();	
				
				int jdkIndex = jdkVersionCombo.getSelectionIndex();
				
				OuterCompatibility outerCompatibility = new OuterCompatibility(pathOfProject, null);
				if (outerCompatibility.jdkCompatibility(jdkVersionCombo.getItem(jdkIndex))) {
					
					String[] tableHeader = {"        兼容        ","        "+jdkVersionCombo.getItem(jdkIndex)+"         "};	
					TableItem item = new TableItem(jdkuncompatibilityTable,SWT.NONE);
					item.setText(tableHeader);						
	
				}else {
					TableItem item =null;
					System.out.println("不兼容"+jdkVersionCombo.getItem(jdkIndex));
					
					String[] tableHeader = {"        不兼容        ","        "+jdkVersionCombo.getItem(jdkIndex)+"         "};
					item = new TableItem(jdkuncompatibilityTable,SWT.NONE);
					item.setText(tableHeader);
					
					
					List<String> lists = outerCompatibility.getuncompatibilityfileList();					
					for (String string : lists) {
						item = new TableItem(jdkuncompatibilityTable,SWT.NONE);
						String[] strings= {"位置",string};
						item.setText(strings);				
						
					}
				}
				
				
				if (jarPath != "") {
					if (outerCompatibility.jarCompatibility(jarPath, jarDependPath)) {
						
						
						String[] tableHeader = {"        兼容        ","        "+jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);
						
						System.out.println("兼容"+jarPath);
					}else {
						System.out.println("不兼容"+jarPath);
						String[] tableHeader = {"        不兼容        ","        "+jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);
						/*TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);*/
						/*for (int i = 0; i < tableHeader.length; i++)  
					    {  					
							TableColumn tableColumn = new TableColumn(jaruncompatibilityTable, SWT.NONE);
							tableColumn.setText(tableHeader[i]);  
							// 设置表头可移动，默认为false  
							tableColumn.setMoveable(false); 
					    	tableColumn.pack();
					    	
					    }*/
						
						List<JarClassModel> lists = outerCompatibility.getUncompatibilityClassModels();

						for (JarClassModel model : lists) {
							item = new TableItem(jaruncompatibilityTable,SWT.NONE);
							String[] strings= {"位置",model.getFromClass(),};
							item.setText(strings);
						}
						
					}	
				}			
			}
		});
		
		CompatibilityBtn.setBounds(622, 100, 79, 69);
		CompatibilityBtn.setText("\u5206\u6790");
		
		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("D:\\test\\TestJar");
				folderDialog.open();				
				jarDependPathText.setText(folderDialog.getFilterPath());				
			}
		});
		button_1.setText("\u8DEF\u5F84...");
		button_1.setBounds(500, 139, 53, 22);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("\u9879\u76EE\u8DEF\u5F842\uFF1A");
		label_1.setAlignment(SWT.RIGHT);
		label_1.setBounds(26, 65, 103, 17);
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(135, 62, 346, 22);
		
		Button button_2 = new Button(this, SWT.NONE);
		button_2.setText("\u8DEF\u5F84...");
		button_2.setBounds(487, 62, 53, 22);
		
		
		
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
