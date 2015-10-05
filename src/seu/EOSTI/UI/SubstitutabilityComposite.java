package seu.EOSTI.UI;

import java.util.ArrayList;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.SWT;


import seu.EOSTI.DBConnect.ClassChangeabilityConnector;
import seu.EOSTI.DBConnect.ProjectConnector;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import com.ibm.icu.text.DecimalFormat;

public class SubstitutabilityComposite extends Composite {

	private ProjectConnector pcConnector = new ProjectConnector();
	private ArrayList<String> rStrings;
	private Combo projectSelectCombo = new Combo(this, SWT.NONE);
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SubstitutabilityComposite(Composite parent, int style) {
		super(parent, style);	
		
		final Combo versionCombo = new Combo(this, SWT.NONE);
		projectSelectCombo.setBounds(111, 0, 98, 25);
		versionCombo.setBounds(340, 0, 88, 25);	
						
		projectSelectCombo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = projectSelectCombo.getSelectionIndex();
			
				
				ArrayList<String> verList = pcConnector.getVersion(projectSelectCombo.getItem(index));
				versionCombo.removeAll();
				for (String string : verList) {
					versionCombo.add(string);
				}
				
				versionCombo.layout();
			}
		});
		
		Label lblProject = new Label(this, SWT.NONE);
		lblProject.setBounds(47, 8, 61, 17);
		lblProject.setText("Project:");
		
		Label lblVersion = new Label(this, SWT.NONE);
		lblVersion.setBounds(260, 8, 61, 17);
		lblVersion.setText("Version:");
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(0, 51, 655, 406);
		
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("\u5305\u8026\u5408\u5173\u7CFB\u8868");
		
		final Tree packageCouplingTree = new Tree(tabFolder, SWT.BORDER);
		tabItem.setControl(packageCouplingTree);
		packageCouplingTree.setLinesVisible(true);

		
		
		
		versionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index1 = projectSelectCombo.getSelectionIndex();
				int index2 = versionCombo.getSelectionIndex();				
				
				///Ê÷µÄ²ã¼¶ÏÔÊ¾
				packageCouplingTree.removeAll();
				
//				ChangeabilityConnector dbConnector = new ChangeabilityConnector(projectSelectCombo.getItem(index1),versionCombo.getItem(index2));
				ClassChangeabilityConnector dbConnector = new ClassChangeabilityConnector(projectSelectCombo.getItem(index1),versionCombo.getItem(index2));
				final ArrayList<PackageNode> packageNodeList = new ArrayList<PackageNode>();
				ArrayList<String> packageList = dbConnector.getpackageName();
						//dbConnector.getpackageName();
				for (String string : packageList) {
					PackageNode packageNode = new PackageNode(string);
					packageNode.setAfferents(dbConnector.packageAffernetCouplingslist(string));
					packageNode.setEfferents(dbConnector.packageEffernetCouplingslist(string));			
					packageNodeList.add(packageNode);		
				}
				
				/*for (PackageNode node : packageNodeList) {
					TreeItem item = new TreeItem(packageEfferentTree, SWT.NONE);
					DecimalFormat df = new DecimalFormat("0.00");
					item.setText(node.getName()+" Ca: " + node.getAfferents().size() + " Ce: " + node.getEfferents().size()
							+ " C: " + df.format(node.getChangeabilityRatio()) );
					ArrayList<String> list = node.getEfferents();
					for (String string : list) {
						TreeItem treeItem = new TreeItem(item, SWT.NONE);				
						treeItem.setText(string);
						if (packageNodeList.contains(new PackageNode(string))) {
							int index = packageNodeList.indexOf(new PackageNode(string));
							PackageNode pNode = packageNodeList.get(index);
							ArrayList<String> nextList = pNode.getEfferents();
							for (String string2 : nextList) {
								TreeItem nextItem = new TreeItem(treeItem, SWT.NONE);				
								nextItem.setText(string2);
							}
							
						}
					}			
				}
				
				for (PackageNode node : packageNodeList) {
					TreeItem item = new TreeItem(packageAfferentTree, SWT.NONE);
					DecimalFormat df = new DecimalFormat("0.00");
					item.setText(node.getName() + " Ca: " + node.getAfferents().size() + " Ce: " + node.getEfferents().size()
							+ " C: " + df.format(node.getChangeabilityRatio()));
					ArrayList<String> list = node.getAfferents();
					for (String string : list) {
						TreeItem treeItem = new TreeItem(item, SWT.NONE);				
						treeItem.setText(string);
						if (packageNodeList.contains(new PackageNode(string))) {
							int index = packageNodeList.indexOf(new PackageNode(string));
							PackageNode pNode = packageNodeList.get(index);
							ArrayList<String> nextList = pNode.getAfferents();
							for (String string2 : nextList) {
								TreeItem nextItem = new TreeItem(treeItem, SWT.NONE);				
								nextItem.setText(string2);
							}
							
						}
					}			
				}*/
				
				for (PackageNode node : packageNodeList) {
					TreeItem item = new TreeItem(packageCouplingTree, SWT.NONE);
					DecimalFormat df = new DecimalFormat("0.00");
					item.setText(node.getName()+" Ca: " + node.getAfferents().size() + " Ce: " + node.getEfferents().size()
							+ " C: " + df.format(node.getChangeabilityRatio()));
					ArrayList<String> list = new ArrayList<>(node.getEfferents());
					list.addAll(node.getAfferents());
					for (String string : list) {
						TreeItem treeItem = new TreeItem(item, SWT.NONE);				
						treeItem.setText(string);
						if (packageNodeList.contains(new PackageNode(string))) {
							int index = packageNodeList.indexOf(new PackageNode(string));
							PackageNode pNode = packageNodeList.get(index);
							ArrayList<String> nextList = new ArrayList<>(pNode.getEfferents());
							nextList.addAll(pNode.getAfferents());
							for (String string2 : nextList) {
								TreeItem nextItem = new TreeItem(treeItem, SWT.NONE);				
								nextItem.setText(string2);
							}
							
						}
					}			
				}			
				
				packageCouplingTree.addListener(SWT.Expand, new Listener() {					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						TreeItem selectItem =  (TreeItem) event.item;
						System.out.println("selectitem:" + selectItem.getText());			
						if (packageNodeList.contains(new PackageNode(selectItem.getText()))) {
							int index = packageNodeList.indexOf(new PackageNode(selectItem.getText()));
							PackageNode pNode = packageNodeList.get(index);
							ArrayList<String> nextList = new ArrayList<>(pNode.getEfferents());
							nextList.addAll(pNode.getAfferents());
							selectItem.removeAll();
							for (String string2 : nextList) {
								TreeItem nextItem = new TreeItem(selectItem, SWT.NONE);				
								nextItem.setText(string2);
								if (packageNodeList.contains(new PackageNode(string2))) {
									int index2 = packageNodeList.indexOf(new PackageNode(string2));
									PackageNode nextpNode = packageNodeList.get(index2);
									ArrayList<String> nextnextList = new ArrayList<>(nextpNode.getEfferents());
									for (String string3 : nextnextList) {
										TreeItem nextnextItem = new TreeItem(nextItem, SWT.NONE);				
										nextnextItem.setText(string3);
									}						
								}
							}
						}							
					}
				});

				packageCouplingTree.layout();				
			}
		});
	}

	public void reloadProject(){
		projectSelectCombo.removeAll();
		rStrings = pcConnector.getProject();	
		for (String string : rStrings) {
			projectSelectCombo.add(string);
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
