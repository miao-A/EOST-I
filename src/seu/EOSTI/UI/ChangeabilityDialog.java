package seu.EOSTI.UI;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableTree;

import seu.EOSTI.DBConnect.ChangeabilityConnector;

public class ChangeabilityDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Tree packageEfferentTree;
	private Tree packageAfferentTree;

	private TreeItem treeItem1;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			ChangeabilityDialog inst = new ChangeabilityDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ChangeabilityDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(633, 440);
			dialogShell.setText("Changeability");

			{
				FormData packagetreeLData = new FormData();
				packagetreeLData.left =  new FormAttachment(0, 1000, 12);
				packagetreeLData.top =  new FormAttachment(0, 1000, 21);
				packagetreeLData.width = 391;
				packagetreeLData.height = 151;	
				
				
				packageEfferentTree = new Tree(dialogShell, SWT.NONE);
				packageEfferentTree.setLayoutData(packagetreeLData);
				packageEfferentTree.setOrientation(SWT.HORIZONTAL);
				{
					ChangeabilityConnector dbConnector = new ChangeabilityConnector();
					ArrayList<String> packageList = dbConnector.getpackageName();
					for (String string : packageList) {
						TreeItem packageItem = new TreeItem(packageEfferentTree, SWT.NONE);
						int ce = dbConnector.packageEfferentCouplingsCount(string, "jeditor", "0.2");
						int ca = dbConnector.packageAfferentCouplingsCount(string, "jeditor", "0.2");

						double c = 1.0*(ce)/(ca+ce);
						DecimalFormat df = new DecimalFormat("0.00");
						
						packageItem.setText(string + " ce: " + ce + " ca: "+ ca + " c: "+df.format(c));
						ArrayList<String> celList = dbConnector.packageEffernetCouplingslist(string, "jeditor", "0.2");
						for (String string2 : celList) {
							TreeItem tItem = new TreeItem(packageItem, SWT.NULL);
							tItem.setText(string2);
						}
					}					
/*					treeItem1 = new TreeItem(packageEfferentTree, SWT.NONE);
					treeItem1.setText("treeItem1");
					TreeItem childItem = new TreeItem(treeItem1, SWT.NULL);
					childItem.setText("child1");*/					

				}
				
				{
					FormData packageAfferentTreeLData = new FormData();
					packageAfferentTreeLData.left =  new FormAttachment(0, 1000, 12);
					packageAfferentTreeLData.top =  new FormAttachment(0, 1000, 201);
					packageAfferentTreeLData.width = 391;
					packageAfferentTreeLData.height = 165;
					packageAfferentTree = new Tree(dialogShell, SWT.NONE);
					packageAfferentTree.setLayoutData(packageAfferentTreeLData);
					{
						ChangeabilityConnector dbConnector = new ChangeabilityConnector();
						ArrayList<String> packageList = dbConnector.getpackageName();
						for (String string : packageList) {
							TreeItem packageItem = new TreeItem(packageAfferentTree, SWT.NONE);
							int ce = dbConnector.packageEfferentCouplingsCount(string, "jeditor", "0.2");
							int ca = dbConnector.packageAfferentCouplingsCount(string, "jeditor", "0.2");
							
							double c = 1.0*(ce)/(ca+ce);
							DecimalFormat df = new DecimalFormat("0.00");
							
							packageItem.setText(string + " ce: " + ce + " ca: "+ ca + " c: "+df.format(c));
							ArrayList<String> calList = dbConnector.packageAffernetCouplingslist(string, "jeditor", "0.2");
							for (String string2 : calList) {
								TreeItem tItem = new TreeItem(packageItem, SWT.NULL);
								tItem.setText(string2);
							}
						}					
					}
				}
				
				
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
