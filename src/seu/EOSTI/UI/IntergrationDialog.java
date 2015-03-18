package seu.EOSTI.UI;


import java.io.IOException;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import seu.EOSTI.fileParser.ReadFile;

public class IntergrationDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Text dirPathtext;
	private Label dirLable;
	private Button fileBtn;
	private Label label1;
	private Label label;
	private Button analyzeAllBtn;
	private TableViewer dirViewer;
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
//			dialogShell = new Shell(parent, SWT.CLOSE | SWT.MAX |SWT.MIN);
			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(615, 391);
			dialogShell.setText("\u96c6\u6210\u6027\u8bc4\u4f30\u5e73\u53f0");
			{
				label1 = new Label(dialogShell, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 14);
				label1LData.top =  new FormAttachment(0, 1000, 243);
				label1LData.width = 47;
				label1LData.height = 20;
				label1.setLayoutData(label1LData);
				label1.setText("Result\uff1a");
			}
			{
				label = new Label(dialogShell, SWT.NONE);
				FormData labelLData = new FormData();
				labelLData.left =  new FormAttachment(0, 1000, 14);
				labelLData.top =  new FormAttachment(0, 1000, 87);
				labelLData.width = 71;
				labelLData.height = 17;
				label.setLayoutData(labelLData);
				label.setText("dirContent\uff1a");
			}
			{
				FormData dirViewerLData = new FormData();
				dirViewerLData.left =  new FormAttachment(0, 1000, 85);
				dirViewerLData.top =  new FormAttachment(0, 1000, 87);
				dirViewerLData.width = 418;
				dirViewerLData.height = 75;
				dirViewer = new TableViewer(dialogShell, SWT.NONE);
				dirViewer.getControl().setLayoutData(dirViewerLData);
			}

			{
				fileBtn = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData fileBtnLData = new FormData();
				fileBtnLData.left =  new FormAttachment(0, 1000, 533);
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
							MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(),//shell窗口
									"请选择目录",
									null,
									"请选择目录！！！",
									MessageDialog.WARNING,
									new String[]{"OK"},
									1);
							dialog.open();		            
						}
						
						
						dirPathtext.setText(folderDialog.getFilterPath());
						
						for (String  i : foldersNameString) {
							dirViewer.add(i);
						}

					}
				});

			}
			{
				resultText = new StyledText(dialogShell, SWT.HORIZONTAL|SWT.V_SCROLL|SWT.H_SCROLL);
				FormData resultTextLData = new FormData();
				resultTextLData.left =  new FormAttachment(0, 1000, 86);
				resultTextLData.top =  new FormAttachment(0, 1000, 243);
				resultTextLData.width = 419;
				resultTextLData.height = 79;
				resultText.setLayoutData(resultTextLData);
				resultText.setText("styledText1-result");
			}
			{
				dirLable = new Label(dialogShell, SWT.CENTER);
				FormData dirLableLData = new FormData();
				dirLableLData.left =  new FormAttachment(0, 1000, 12);
				dirLableLData.top =  new FormAttachment(0, 1000, 42);
				dirLableLData.width = 56;
				dirLableLData.height = 27;
				dirLable.setLayoutData(dirLableLData);
				dirLable.setText("Analydir:");
			}
			{
				dirPathtext = new Text(dialogShell, SWT.NONE);
				FormData dirtextLData = new FormData();
				dirtextLData.left =  new FormAttachment(0, 1000, 86);
				dirtextLData.top =  new FormAttachment(0, 1000, 42);
				dirtextLData.width = 432;
				dirtextLData.height = 25;
				dirPathtext.setLayoutData(dirtextLData);
			}
			{
				AnalyzeBtn = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				AnalyzeBtn.setText(" analyzeaproject");
				FormData AnalyzeBtnLData = new FormData();
				AnalyzeBtnLData.width = 144;
				AnalyzeBtnLData.height = 34;
				AnalyzeBtnLData.left =  new FormAttachment(0, 1000, 86);
				AnalyzeBtnLData.top =  new FormAttachment(0, 1000, 197);
				AnalyzeBtn.setLayoutData(AnalyzeBtnLData);
				AnalyzeBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e){
						try {
							TableItem item = dirViewer.getTable().getItem(dirViewer.getTable().getSelectionIndex());

							resultText.setText(item.getText());

						} catch (Exception e2) {
							// TODO: handle exception
							MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(),//shell窗口
									"请选择项目目录",
									null,
									"请选择项目目录后分析",
									MessageDialog.WARNING,
									new String[]{"OK"},
									1);
							dialog.open();		            
						}
						
						
					}
					
				});
			}
			{
				analyzeAllBtn = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				analyzeAllBtn.setText("analyzeAllProject");
				FormData analyzeAllBtnLData = new FormData();
				analyzeAllBtnLData.width = 122;
				analyzeAllBtnLData.height = 34;
				analyzeAllBtnLData.left =  new FormAttachment(0, 1000, 368);
				analyzeAllBtnLData.top =  new FormAttachment(0, 1000, 197);
				analyzeAllBtn.setLayoutData(analyzeAllBtnLData);
				analyzeAllBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e){
						try {
							TableItem[] item = dirViewer.getTable().getItems();
							if (item.length==0) {
								throw new IOException("error");
							}
							resultText.setText("");
							for (TableItem tableItem : item) {
								resultText.append(tableItem.getText()+"\n");
							}
							
						} catch (Exception e2) {
							// TODO: handle exception
							MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(),//shell窗口
									"请选择项目目录",
									null,
									"请选择项目目录后分析",
									MessageDialog.WARNING,
									new String[]{"OK"},
									1);
							dialog.open();		            
						}					
						
					}
				});
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
