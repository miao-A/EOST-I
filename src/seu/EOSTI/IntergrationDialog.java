package seu.EOSTI;

import java.io.File;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class IntergrationDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Text dirPathtext;
	private Label dirLable;
	private Button fileBtn;
	private List dirlist;
	private StyledText resultText;
	private Button AnalyzeBtn;
	private String[] testStrings;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			IntergrationDialog inst = new IntergrationDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IntergrationDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(517, 318);
			dialogShell.setText("\u96c6\u6210\u6027\u8bc4\u4f30\u5e73\u53f0");
			{
				FormData dirlistLData = new FormData();
				dirlistLData.left =  new FormAttachment(0, 1000, 86);
				dirlistLData.top =  new FormAttachment(0, 1000, 88);
				dirlistLData.width = 255;
				dirlistLData.height = 75;
				dirlist = new List(dialogShell, SWT.NONE);
				dirlist.setLayoutData(dirlistLData);
			}

			{
				fileBtn = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData fileBtnLData = new FormData();
				fileBtnLData.left =  new FormAttachment(0, 1000, 300);
				fileBtnLData.top =  new FormAttachment(0, 1000, 42);
				fileBtnLData.width = 44;
				fileBtnLData.height = 27;
				fileBtn.setLayoutData(fileBtnLData);
				fileBtn.setText("files...");
				fileBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e){
						DirectoryDialog folderDialog = new DirectoryDialog(dialogShell);
						folderDialog.setText("chooser project");	
						folderDialog.setFilterPath("SystemDrive");
						folderDialog.open();
						String folderPathString;
						java.util.List<String> foldersNameString = null;
						folderPathString = folderDialog.getFilterPath();
						try {
							ReadFile readFile = new ReadFile(folderPathString);
							foldersNameString = readFile.readProjectFile();
						} catch (Exception e2) {
							// TODO: handle exception
						}
						
						dirPathtext.setText(folderDialog.getFilterPath());
						
						for (String  i : foldersNameString) {
							dirlist.add(i);
						}

					}
				});

			}
			{
				resultText = new StyledText(dialogShell, SWT.NONE);
				FormData resultTextLData = new FormData();
				resultTextLData.left =  new FormAttachment(0, 1000, 88);
				resultTextLData.top =  new FormAttachment(0, 1000, 190);
				resultTextLData.width = 309;
				resultTextLData.height = 71;
				resultText.setLayoutData(resultTextLData);
				resultText.setText("styledText1-result");
			}
			{
				AnalyzeBtn = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData AnalyzeBtnLData = new FormData();
				AnalyzeBtnLData.left =  new FormAttachment(0, 1000, 20);
				AnalyzeBtnLData.top =  new FormAttachment(0, 1000, 204);
				AnalyzeBtnLData.width = 56;
				AnalyzeBtnLData.height = 27;
				AnalyzeBtn.setLayoutData(AnalyzeBtnLData);
				AnalyzeBtn.setText("Analyze");
			}
			{
				dirLable = new Label(dialogShell, SWT.CENTER);
				FormData dirLableLData = new FormData();
				dirLableLData.left =  new FormAttachment(0, 1000, 20);
				dirLableLData.top =  new FormAttachment(0, 1000, 42);
				dirLableLData.width = 60;
				dirLableLData.height = 25;
				dirLable.setLayoutData(dirLableLData);
				dirLable.setText("Analydir:");
			}
			{
				dirPathtext = new Text(dialogShell, SWT.NONE);
				FormData dirtextLData = new FormData();
				dirtextLData.left =  new FormAttachment(0, 1000, 86);
				dirtextLData.top =  new FormAttachment(0, 1000, 42);
				dirtextLData.width = 205;
				dirtextLData.height = 25;
				dirPathtext.setLayoutData(dirtextLData);
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
