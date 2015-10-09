package cn.seu.edu.integrabilityevaluator.ui;

import java.util.ArrayList;
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
import cn.seu.edu.integrabilityevaluator.model.UnCompatibilityMIModel;
import cn.seu.edu.integrabilityevaluator.parser.Compatibility;
import cn.seu.edu.integrabilityevaluator.parser.InnerCompatibility;
import cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder.ChangeStatus;
import cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder.ClassCompatibilityRecoder;
import cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder.ConstructorMethodRecoder;
import cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder.MethodRecoder;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;

public class InnerCompatibilityComposite extends Composite {
	private Text pathOfProjectText;
	private Text componentOfProjectText;
	private Table uncompatibilityTable;
	private TableEditor editor = null;
	
	String strings = new String();
	private Text ResultText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public InnerCompatibilityComposite(Composite parent, int style) {
		super(parent, style);
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(10, 13, 103, 17);
		label.setText("\u9879\u76EE\u8DEF\u5F84\uFF1A");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("\u9879\u76EE\u4E2D\u5305\u8DEF\u5F84\uFF1A");
		label_1.setBounds(10, 62, 103, 17);
		
		pathOfProjectText = new Text(this, SWT.BORDER);
		pathOfProjectText.setBounds(119, 10, 375, 22);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("��ѡ����Ŀ�ļ�");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.2");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfProjectText.setText(folderDialog.getFilterPath());
				
			}
			
			
		});
		btnNewButton.setBounds(500, 10, 53, 22);
		btnNewButton.setText("\u8DEF\u5F84...");
		
		componentOfProjectText = new Text(this, SWT.BORDER);
		componentOfProjectText.setBounds(119, 57, 375, 22);
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("��ѡ����Ŀ�ļ�");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.2/src/org/jeditor/app");
				folderDialog.open();
				
				componentOfProjectText.setText(folderDialog.getFilterPath());
				
			}
		});
		button.setBounds(500, 57, 53, 22);
		button.setText("\u8DEF\u5F84...");
		
		uncompatibilityTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		uncompatibilityTable.setBounds(10, 120, 711, 406);
		uncompatibilityTable.setHeaderVisible(true);
		uncompatibilityTable.setLinesVisible(true);

		
		editor = new TableEditor(uncompatibilityTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		String[] tableHeader = {"        ����.����         ", "       �����ݷ���������        ","        �����ݷ�����        ","    Ӧʹ������    ","       ʵ��ʹ������         "};		
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(uncompatibilityTable, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// ���ñ�ͷ���ƶ���Ĭ��Ϊfalse  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    	
	    }
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);		
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				uncompatibilityTable.removeAll();				
				
				String pathOfProject = pathOfProjectText.getText(); // "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String componentOfProject = componentOfProjectText.getText(); //"D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";
								
				InnerCompatibility innerCompatibility = new InnerCompatibility(pathOfProject, componentOfProject);
				List<UnCompatibilityMIModel> unCompatibilityMIModels = innerCompatibility.getunCompatibilityMIModels();					

				if (unCompatibilityMIModels.size() == 0) {
					System.out.println("�ð�����Ŀ�м���");
				}
				
				
				
				for (UnCompatibilityMIModel unCompatibilityMIModel : unCompatibilityMIModels) {
					//unCompatibilityMIModel.getMessage();
					
				    final TableItem item = new TableItem(uncompatibilityTable, SWT.NONE);
				    String string[] = {unCompatibilityMIModel.getInPackageName()+"."+unCompatibilityMIModel.getFromClassName(),
				    		unCompatibilityMIModel.getFromPackageName(),unCompatibilityMIModel.getMethodName(),unCompatibilityMIModel.getDefaultArguments(),
				    		unCompatibilityMIModel.getRealArguments()};
				    item.setText(string);
				}				
					

				if (unCompatibilityMIModels.size()>0) {
					/*TableItem lastItem = new TableItem(uncompatibilityTable, SWT.NONE);
					lastItem.setText(new String[] {"�����ݵĽӿڸ���:",String.valueOf(unCompatibilityMIModels.size())});*/
					ResultText.setText("�����ݵķ�������:"+String.valueOf(unCompatibilityMIModels.size()));
				}else {
/*					TableItem lastItem = new TableItem(uncompatibilityTable, SWT.NONE);
					lastItem.setText(new String[] {"�ð�����Ŀ�м���"});*/
					ResultText.setText("�ð�����Ŀ�м���");
				}
				
			}
		});
		
		CompatibilityBtn.setBounds(583, 10, 79, 69);
		CompatibilityBtn.setText("\u5206\u6790");
		
		ResultText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		ResultText.setBounds(10, 91, 200, 23);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}