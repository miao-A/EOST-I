package seu.EOSTI.old;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class ProjectInfoDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label label1;
	private Text projectIntrText;
	private Label label3;
	private Button OKbtn;
	private Text versionText;
	private Label label2;
	private Text projectNameText;
	
	private String projectNameString;
	private String versionString;
	private String projectIntrString;
	

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			ProjectInfoDialog inst = new ProjectInfoDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String getProjectName(){
		return projectNameString;
	}	
	
	public String getVersion(){
		return versionString;
	}
	
	public String getProjectIntroduction(){
		return projectIntrString;
	}
	

	public ProjectInfoDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(342, 239);
			dialogShell.setText("Project Information");
			{
				FormData projectIntrTextLData = new FormData();
				projectIntrTextLData.left =  new FormAttachment(0, 1000, 144);
				projectIntrTextLData.top =  new FormAttachment(0, 1000, 95);
				projectIntrTextLData.width = 143;
				projectIntrTextLData.height = 48;
				projectIntrText = new Text(dialogShell, SWT.MULTI | SWT.WRAP);
				projectIntrText.setLayoutData(projectIntrTextLData);
			}
			{
				label3 = new Label(dialogShell, SWT.NONE);
				FormData label3LData = new FormData();
				label3LData.left =  new FormAttachment(0, 1000, 12);
				label3LData.top =  new FormAttachment(0, 1000, 95);
				label3LData.width = 126;
				label3LData.height = 17;
				label3.setLayoutData(label3LData);
				label3.setText("Project introduction\uff1a");
			}
			{
				OKbtn = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData OKbtnLData = new FormData();
				OKbtnLData.left =  new FormAttachment(0, 1000, 144);
				OKbtnLData.top =  new FormAttachment(0, 1000, 149);
				OKbtnLData.width = 75;
				OKbtnLData.height = 27;
				OKbtn.setLayoutData(OKbtnLData);
				OKbtn.setText("OK");
				OKbtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e){
						
						if (projectNameText.getText().isEmpty()||versionText.getText().isEmpty()) {
							MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(),//shell窗口
									"WARNNING",
									null,
									"请输入项目名称与版本信息",
									MessageDialog.WARNING,
									new String[]{"OK"},
									1);
							dialog.open();	
							
						}else{
							projectNameString = projectNameText.getText();
							versionString = versionText.getText();
							projectIntrString = projectIntrText.getText();
							dialogShell.dispose();
						}			
					}
					
					
				});
			}
			{
				versionText = new Text(dialogShell, SWT.NONE);
				FormData versionTextLData = new FormData();
				versionTextLData.left =  new FormAttachment(0, 1000, 124);
				versionTextLData.top =  new FormAttachment(0, 1000, 56);
				versionTextLData.width = 72;
				versionTextLData.height = 17;
				versionText.setLayoutData(versionTextLData);
			}
			{
				label2 = new Label(dialogShell, SWT.NONE);
				FormData label2LData = new FormData();
				label2LData.left =  new FormAttachment(0, 1000, 12);
				label2LData.top =  new FormAttachment(0, 1000, 61);
				label2LData.width = 56;
				label2LData.height = 17;
				label2.setLayoutData(label2LData);
				label2.setText("Version\uff1a");
			}
			{
				projectNameText = new Text(dialogShell, SWT.NONE);
				FormData projectNameTextLData = new FormData();
				projectNameTextLData.left =  new FormAttachment(0, 1000, 124);
				projectNameTextLData.top =  new FormAttachment(0, 1000, 27);
				projectNameTextLData.width = 72;
				projectNameTextLData.height = 17;
				projectNameText.setLayoutData(projectNameTextLData);
			}
			{
				label1 = new Label(dialogShell, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 12);
				label1LData.top =  new FormAttachment(0, 1000, 27);
				label1LData.width = 91;
				label1LData.height = 17;
				label1.setLayoutData(label1LData);
				label1.setText("Project Name\uff1a");
			}
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
