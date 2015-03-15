package seu.EOSTI;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;

public class IntergrationDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Text dirtext;
	private Label dirLable;
	private StyledText resultText;
	private Button AnalyzeBtn;

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
			dialogShell.setSize(522, 312);
			{
				resultText = new StyledText(dialogShell, SWT.NONE);
				FormData resultTextLData = new FormData();
				resultTextLData.left =  new FormAttachment(0, 1000, 108);
				resultTextLData.top =  new FormAttachment(0, 1000, 95);
				resultTextLData.width = 361;
				resultTextLData.height = 167;
				resultText.setLayoutData(resultTextLData);
				resultText.setText("styledText1");
			}
			{
				AnalyzeBtn = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData AnalyzeBtnLData = new FormData();
				AnalyzeBtnLData.left =  new FormAttachment(0, 1000, 20);
				AnalyzeBtnLData.top =  new FormAttachment(0, 1000, 95);
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
				dirtext = new Text(dialogShell, SWT.NONE);
				FormData dirtextLData = new FormData();
				dirtextLData.left =  new FormAttachment(0, 1000, 86);
				dirtextLData.top =  new FormAttachment(0, 1000, 42);
				dirtextLData.width = 205;
				dirtextLData.height = 25;
				dirtext.setLayoutData(dirtextLData);
				dirtext.setText("dirtext");
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
