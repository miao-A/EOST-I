package seu.EOSTI.UI;

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

import seu.EOSTI.Model.AbstractClassModel;
import seu.EOSTI.Model.ConstructorMethodModel;
import seu.EOSTI.Model.MethodModel;
import seu.EOSTI.Model.UnCompatibilityMIModel;
import seu.EOSTI.Parser.Compatibility;
import seu.EOSTI.Parser.InnerCompatibility;
import seu.EOSTI.modelcompatibilityrecoder.ChangeStatus;
import seu.EOSTI.modelcompatibilityrecoder.ClassCompatibilityRecoder;
import seu.EOSTI.modelcompatibilityrecoder.ConstructorMethodRecoder;
import seu.EOSTI.modelcompatibilityrecoder.MethodRecoder;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;

public class InnerCompatibilityMutiVersionComposite extends Composite {
	private Text pathOfOldProjectText;
	private Table uncompatibilityTableOne;

	private TableEditor editor = null;
	
	String strings = new String();
	private Text pathOfNewProjectText;
	private Table uncompatibilityTableTwo;
	private Text ResultOneText;
	private Text ResultTwoText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public InnerCompatibilityMutiVersionComposite(Composite parent, int style) {
		super(parent, style);
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(10, 13, 103, 17);
		label.setText("\u9879\u76EE\u8DEF\u5F841\uFF1A");
		
		pathOfOldProjectText = new Text(this, SWT.BORDER);
		pathOfOldProjectText.setBounds(119, 10, 375, 22);
		
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
				
				pathOfOldProjectText.setText(folderDialog.getFilterPath());
				
			}
			
			
		});
		btnNewButton.setBounds(500, 10, 53, 22);
		btnNewButton.setText("\u8DEF\u5F84...");
		
		uncompatibilityTableOne = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		uncompatibilityTableOne.setBounds(10, 105, 711, 166);
		uncompatibilityTableOne.setHeaderVisible(true);
		uncompatibilityTableOne.setLinesVisible(true);

		uncompatibilityTableTwo = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		uncompatibilityTableTwo.setLinesVisible(true);
		uncompatibilityTableTwo.setHeaderVisible(true);
		uncompatibilityTableTwo.setBounds(10, 301, 711, 166);
		
		
		editor = new TableEditor(uncompatibilityTableOne);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		String[] tableHeader = {"        ����        ", "       �����ݷ���������        ","        �����ݷ�����        ","    Ӧʹ������    ","       ʵ��ʹ������         "};		
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(uncompatibilityTableOne, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// ���ñ�ͷ���ƶ���Ĭ��Ϊfalse  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    	
	    }
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(uncompatibilityTableTwo, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// ���ñ�ͷ���ƶ���Ĭ��Ϊfalse  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    	
	    }
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);		
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				uncompatibilityTableOne.removeAll();
				uncompatibilityTableTwo.removeAll();
				
				String pathOfOldProject = pathOfOldProjectText.getText(); // "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String pathOfNewProject = pathOfNewProjectText.getText();
								
				InnerCompatibility oldInnerCompatibility = new InnerCompatibility(pathOfOldProject, pathOfOldProject);
				InnerCompatibility newInnerCompatibility = new InnerCompatibility(pathOfNewProject, pathOfNewProject);
				List<UnCompatibilityMIModel> oldUnCompatibilityMIModels = oldInnerCompatibility.getunCompatibilityMIModels();
				List<UnCompatibilityMIModel> newUnCompatibilityMIModels = newInnerCompatibility.getunCompatibilityMIModels();

				if (oldUnCompatibilityMIModels.size() == 0) {
					//System.out.println("�汾1�ڲ�����������");
					ResultOneText.setText("�汾1�ڲ�����������");
				}
				
				if (newUnCompatibilityMIModels.size() == 0) {
					//System.out.println("�汾2�ڲ�����������");
					ResultTwoText.setText("�汾2�ڲ�����������");
				}
				
				for (UnCompatibilityMIModel unCompatibilityMIModel : oldUnCompatibilityMIModels) {
					//unCompatibilityMIModel.getMessage();
					
				    final TableItem item = new TableItem(uncompatibilityTableOne, SWT.NONE);
				    String string[] = {unCompatibilityMIModel.getInPackageName(),unCompatibilityMIModel.getFromPackageName(),
				    		unCompatibilityMIModel.getMethodName(),unCompatibilityMIModel.getDefaultArguments(),
				    		unCompatibilityMIModel.getRealArguments()};
				    item.setText(string);
				}				
					

				if (oldUnCompatibilityMIModels.size()>0) {
					//TableItem lastItem = new TableItem(uncompatibilityTableOne, SWT.NONE);
					//lastItem.setText(new String[] {"�汾1�����ݵĽӿڸ���:",String.valueOf(oldUnCompatibilityMIModels.size())});
					ResultOneText.setText("�汾1�����ݵ����͸���:"+String.valueOf(oldUnCompatibilityMIModels.size()));
				}else {
					//TableItem lastItem = new TableItem(uncompatibilityTableOne, SWT.NONE);
					//lastItem.setText(new String[] {"�汾1�����ڲ�����������"});
					ResultOneText.setText("�汾1�ڲ�����������");
				}
				
				for (UnCompatibilityMIModel unCompatibilityMIModel : newUnCompatibilityMIModels) {
					//unCompatibilityMIModel.getMessage();
					
				    final TableItem item = new TableItem(uncompatibilityTableTwo, SWT.NONE);
				    String string[] = {unCompatibilityMIModel.getInPackageName(),unCompatibilityMIModel.getFromPackageName(),
				    		unCompatibilityMIModel.getMethodName(),unCompatibilityMIModel.getDefaultArguments(),
				    		unCompatibilityMIModel.getRealArguments()};
				    item.setText(string);
				}	
				
				if (newUnCompatibilityMIModels.size()>0) {
					//TableItem lastItem = new TableItem(uncompatibilityTableOne, SWT.NONE);
					//lastItem.setText(new String[] {"�汾2�����ݵĽӿڸ���:",String.valueOf(newUnCompatibilityMIModels.size())});
					ResultTwoText.setText("�汾2�����ݵ����͸���:"+String.valueOf(newUnCompatibilityMIModels.size()));
				}else {
					//TableItem lastItem = new TableItem(uncompatibilityTableOne, SWT.NONE);
					//lastItem.setText(new String[] {"�汾2�����ڲ�����������"});
					ResultTwoText.setText("�汾2�ڲ�����������");
				}
				
			}
		});
		
		CompatibilityBtn.setBounds(583, 10, 79, 69);
		CompatibilityBtn.setText("\u5206\u6790");
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setText("\u9879\u76EE\u8DEF\u5F842\uFF1A");
		label_2.setBounds(10, 51, 103, 17);
		
		pathOfNewProjectText = new Text(this, SWT.BORDER);
		pathOfNewProjectText.setBounds(119, 48, 375, 22);
		
		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("��ѡ����Ŀ�ļ�");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.3");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfNewProjectText.setText(folderDialog.getFilterPath());
			}
		});
		button_1.setText("\u8DEF\u5F84...");
		button_1.setBounds(500, 48, 53, 22);
		
		
		
		ResultOneText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		ResultOneText.setBounds(10, 74, 144, 23);
		
		ResultTwoText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		ResultTwoText.setBounds(10, 277, 144, 23);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
