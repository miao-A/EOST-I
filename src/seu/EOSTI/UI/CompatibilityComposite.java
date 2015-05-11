package seu.EOSTI.UI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CompatibilityComposite extends Composite {
	private Text oldComponentText;
	private Text newComponentText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompatibilityComposite(Composite parent, int style) {
		super(parent, style);
		
		Label lblCompatibilityComposite = new Label(this, SWT.NONE);
		lblCompatibilityComposite.setBounds(87, 223, 150, 17);
		lblCompatibilityComposite.setText("Compatibility Composite");
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(10, 10, 103, 17);
		label.setText("\u8001\u7248\u672C\u6784\u4EF6\u8DEF\u5F84\uFF1A");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("\u65B0\u7248\u672C\u6784\u4EF6\u8DEF\u5F84\uFF1A");
		label_1.setBounds(10, 62, 103, 17);
		
		oldComponentText = new Text(this, SWT.BORDER);
		oldComponentText.setBounds(119, 10, 235, 22);
		
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
		btnNewButton.setBounds(360, 10, 53, 22);
		btnNewButton.setText("\u8DEF\u5F84...");
		
		newComponentText = new Text(this, SWT.BORDER);
		newComponentText.setBounds(119, 57, 235, 22);
		
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
		button.setBounds(360, 57, 53, 22);
		button.setText("\u8DEF\u5F84...");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
