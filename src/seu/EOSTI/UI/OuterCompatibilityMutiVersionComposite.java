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
	private Text pathOfOldProjectText;
	private Text jarPathText;
	private TableEditor editor = null;
	
	String strings = new String();
	private Text jarDependPathText;
	private Table jaruncompatibilityTable;
	private Table table;
	private Text pathOfNewProjectText;
	private Text resultOneText;
	private Text resultTwoText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OuterCompatibilityMutiVersionComposite(Composite parent, int style) {
		super(parent, style);
		
		Label label = new Label(this, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		label.setBounds(27, 27, 103, 17);
		label.setText("\u9879\u76EE\u8DEF\u5F841\uFF1A");
		
		Label lbljar = new Label(this, SWT.NONE);
		lbljar.setAlignment(SWT.RIGHT);
		lbljar.setText("\u5916\u90E8jar\u5305\u8DEF\u5F84\uFF1A");
		lbljar.setBounds(27, 119, 103, 17);
		
		pathOfOldProjectText = new Text(this, SWT.BORDER);
		pathOfOldProjectText.setBounds(136, 24, 346, 22);
		
		
		
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
				
				pathOfOldProjectText.setText(folderDialog.getFilterPath());				
			}			
		});
		btnNewButton.setBounds(488, 24, 53, 22);
		btnNewButton.setText("\u8DEF\u5F84...");
		
		jarPathText = new Text(this, SWT.BORDER);
		jarPathText.setBounds(136, 116, 346, 22);
		
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
		button.setBounds(488, 116, 53, 22);
		button.setText("\u8DEF\u5F84...");
		
		jaruncompatibilityTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		jaruncompatibilityTable.setLinesVisible(true);
		jaruncompatibilityTable.setHeaderVisible(false);
		jaruncompatibilityTable.setBounds(10, 236, 711, 278);
		
		String[] tableHeader = {"---------------------------","---------------------------------------------------------------------------"};	
		
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
		lbljar_1.setBounds(2, 144, 128, 17);
		
		Label label_2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(10, 94, 577, 2);
		
		Label label_3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(10, 190, 577, 2);
		
		jarDependPathText = new Text(this, SWT.BORDER);
		jarDependPathText.setBounds(136, 144, 346, 22);
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);		
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				jaruncompatibilityTable.removeAll();				
						
				String pathOfOldProject = pathOfOldProjectText.getText(); // "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String pathOfNewProject = pathOfNewProjectText.getText();
				String jarPath = jarPathText.getText(); //"D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";
				String jarDependPath = jarDependPathText.getText();	
				
				
				OuterCompatibility oldOuterCompatibility = new OuterCompatibility(pathOfOldProject, null);
				OuterCompatibility newOuterCompatibility = new OuterCompatibility(pathOfNewProject, null);
				
				resultOneText.setText("");
				resultTwoText.setText("");
				
				
				if (jarPath != "") {
					if (oldOuterCompatibility.jarCompatibility(jarPath, jarDependPath)) {						
						
						String[] tableHeader = {"       版本1兼容        ","        "+jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);
						resultOneText.setText("版本1兼容");
						//System.out.println("兼容"+jarPath);
					}else {
						//System.out.println("不兼容"+jarPath);
						String[] tableHeader = {"        版本1不兼容        ","        "+jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);
						resultOneText.setText("版本1不兼容");						
						List<JarClassModel> lists = oldOuterCompatibility.getUncompatibilityClassModels();

						for (JarClassModel model : lists) {
							item = new TableItem(jaruncompatibilityTable,SWT.NONE);
							String[] strings= {"位置",model.getFromClass(),};
							item.setText(strings);
						}
						
					}
					
					if (newOuterCompatibility.jarCompatibility(jarPath, jarDependPath)) {						
						
						String[] tableHeader = {"        版本2兼容        ","        "+jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);
						resultTwoText.setText("版本2兼容");
						//System.out.println("兼容"+jarPath);
					}else {
						//System.out.println("不兼容"+jarPath);
						String[] tableHeader = {"        版本2不兼容        ","        "+jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);						
						resultTwoText.setText("版本2不兼容");
						List<JarClassModel> lists = newOuterCompatibility.getUncompatibilityClassModels();

						for (JarClassModel model : lists) {
							item = new TableItem(jaruncompatibilityTable,SWT.NONE);
							String[] strings= {"位置",model.getFromClass(),};
							item.setText(strings);
						}
						
					}
				}			
			}
		});
		
		CompatibilityBtn.setBounds(599, 27, 79, 69);
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
		button_1.setBounds(488, 144, 53, 22);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("\u9879\u76EE\u8DEF\u5F842\uFF1A");
		label_1.setAlignment(SWT.RIGHT);
		label_1.setBounds(27, 55, 103, 17);
		
		pathOfNewProjectText = new Text(this, SWT.BORDER);
		pathOfNewProjectText.setBounds(136, 52, 346, 22);
		
		Button button_2 = new Button(this, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.3");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfNewProjectText.setText(folderDialog.getFilterPath());			
			}
		});
		button_2.setText("\u8DEF\u5F84...");
		button_2.setBounds(488, 52, 53, 22);
		
		resultOneText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		resultOneText.setBounds(10, 207, 194, 23);
		
		resultTwoText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		resultTwoText.setBounds(238, 207, 207, 23);
		
		
		
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
