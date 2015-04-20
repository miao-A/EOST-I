package seu.EOSTI.UI;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;

import seu.EOSTI.DBConnect.ExtensibilityConnector;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ExtensibilityComposite extends Composite {
	private Table extensibilityTable;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ExtensibilityComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(null);
		
		Label lblExtensibilityComposite = new Label(this, SWT.NONE);
		lblExtensibilityComposite.setBounds(10, 275, 165, 25);
		lblExtensibilityComposite.setText("Extensibility Composite");
		
		extensibilityTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		extensibilityTable.setBounds(0, 31, 494, 199);
		extensibilityTable.setHeaderVisible(true);
		extensibilityTable.setLinesVisible(true);
		
		String[] tableHeader = {"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
		
		for (int i = 0; i < tableHeader.length; i++)  
	    {  
			TableColumn tableColumn = new TableColumn(extensibilityTable, SWT.NONE);  
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    }  

		
		final Combo projectSelectCombo = new Combo(this, SWT.NONE);
		final Combo versionCombo = new Combo(this, SWT.NONE);
		projectSelectCombo.setBounds(111, 0, 98, 25);
		versionCombo.setBounds(340, 0, 88, 25);	
		projectSelectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = projectSelectCombo.getSelectionIndex();
				if (projectSelectCombo.getItem(index).equals("junit")) {
					versionCombo.removeAll();
					versionCombo.add("3.4");

				}else if (projectSelectCombo.getItem(index).equals("jeditor")) {
					versionCombo.removeAll();
					versionCombo.add("0.2");
					versionCombo.add("0.3");
					versionCombo.add("0.4");
				} else {
					versionCombo.removeAll();
				}
				versionCombo.layout();
			}
		});
		
		
		versionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index1 = projectSelectCombo.getSelectionIndex();
				int index2 = versionCombo.getSelectionIndex();
				
				ExtensibilityConnector dbConnector = new ExtensibilityConnector(projectSelectCombo.getItem(index1),versionCombo.getItem(index2));
				ArrayList<String> packageNameList= dbConnector.getpackageName();
					// 添加三行数据  		        
			    extensibilityTable.removeAll();    
			    for (String string : packageNameList) {
			       	TableItem item = new TableItem(extensibilityTable, SWT.NONE);
			       	ArrayList<String> al = dbConnector.packageExtensibilityRatio(string);
			       	item.setText((String[])al.toArray(new String[al.size()]));
				}
			    TableItem item = new TableItem(extensibilityTable, SWT.NONE);
			    ArrayList<String> al = dbConnector.projectExtensibilityRatio();
		        item.setText((String[])al.toArray(new String[al.size()]));		       
			}
		});

		projectSelectCombo.add("junit");
		projectSelectCombo.add("project2");
		projectSelectCombo.add("jeditor");
		
		Label lblProject = new Label(this, SWT.NONE);
		lblProject.setBounds(47, 8, 61, 17);
		lblProject.setText("Project:");
		
		Label lblVersion = new Label(this, SWT.NONE);
		lblVersion.setBounds(260, 8, 61, 17);
		lblVersion.setText("Version:");
		


	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
