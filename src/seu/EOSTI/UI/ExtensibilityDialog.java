package seu.EOSTI.UI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;

import seu.EOSTI.DBConnect.DBConnector;
import seu.EOSTI.DBConnect.ExtensibilityConnector;
/**
* @author   Yam
*@version  1.1
*@see     软件可扩展性信息展示窗口
*/
public class ExtensibilityDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Table table1;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			ExtensibilityDialog inst = new ExtensibilityDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExtensibilityDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL|SWT.RESIZE);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(485, 208);
			dialogShell.setText("ExtensibilityInfo");
			{
				FormData table1LData = new FormData();
				table1LData.left =  new FormAttachment(0, 1000, 0);
				table1LData.top =  new FormAttachment(0, 1000, 0);
				table1LData.width = 388;
				table1LData.height = 141;
				table1LData.right =  new FormAttachment(1000, 1000, -64);
				table1LData.bottom =  new FormAttachment(1000, 1000, -12);
				table1 = new Table(dialogShell, SWT.NONE);
				table1.setLayoutData(table1LData);
				table1.setLinesVisible(true);
				table1.setHeaderVisible(true);
				table1.setOrientation(SWT.HORIZONTAL);

				String[] tableHeader = {"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
				
				for (int i = 0; i < tableHeader.length; i++)  
		        {  
		            TableColumn tableColumn = new TableColumn(table1, SWT.NONE);  
		            tableColumn.setText(tableHeader[i]);  
		            // 设置表头可移动，默认为false  
		            tableColumn.setMoveable(false); 
		            tableColumn.pack();
		        }  

				ExtensibilityConnector dbConnector = new ExtensibilityConnector();
				ArrayList<String> packageNameList= dbConnector.getpackageName();
				// 添加三行数据  		        
		        
		        for (String string : packageNameList) {
		        	TableItem item = new TableItem(table1, SWT.NONE);
		        	ArrayList<String> al = dbConnector.packageExtensibilityRatio(null, string,null,null);
		        	item.setText((String[])al.toArray(new String[al.size()]));
				}
		        TableItem item = new TableItem(table1, SWT.NONE);
		        ArrayList<String> al = dbConnector.projectExtensibilityRatio(null, null,"0.2");
	        	item.setText((String[])al.toArray(new String[al.size()]));
		        
		       
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
