package seu.EOSTI.UI;

import java.util.ArrayList;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.SWT;

import seu.EOSTI.DBConnect.ChangeabilityConnector;
import seu.EOSTI.DBConnect.ProjectConnector;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.internal.handlers.WizardHandler.New;

public class ChangeabilityComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ChangeabilityComposite(Composite parent, int style) {
		super(parent, style);
		
		Label lblChagneabilityComposite = new Label(this, SWT.NONE);
		lblChagneabilityComposite.setBounds(278, 31, 162, 28);
		lblChagneabilityComposite.setText("Chagneability Composite");

		
		
		final Combo projectSelectCombo = new Combo(this, SWT.NONE);
		final Combo versionCombo = new Combo(this, SWT.NONE);
		projectSelectCombo.setBounds(111, 0, 98, 25);
		versionCombo.setBounds(340, 0, 88, 25);	
		
		final ProjectConnector pcConnector = new ProjectConnector();
		ArrayList<String> rStrings = pcConnector.getProject();
		for (String string : rStrings) {
			projectSelectCombo.add(string);
		}
		
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
		tabFolder.setBounds(0, 51, 655, 383);
		
		TabItem efferent_tabItem = new TabItem(tabFolder, SWT.NONE);
		efferent_tabItem.setText("\u5305\u4F9D\u8D56\u6811");
		
		final Tree packageEfferentTree = new Tree(tabFolder, SWT.BORDER);
		packageEfferentTree.setLinesVisible(true);
		efferent_tabItem.setControl(packageEfferentTree);
		
		TabItem afferent_tabItem = new TabItem(tabFolder, SWT.NONE);
		afferent_tabItem.setText("\u5305\u88AB\u4F9D\u8D56\u6811");
		
		final Tree packageAfferentTree = new Tree(tabFolder, SWT.BORDER);
		packageAfferentTree.setLinesVisible(true);
		afferent_tabItem.setControl(packageAfferentTree);

		
		
		
		versionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index1 = projectSelectCombo.getSelectionIndex();
				int index2 = versionCombo.getSelectionIndex();
				
				///���Ĳ㼶��ʾ
				packageEfferentTree.removeAll();
				packageAfferentTree.removeAll();
				ChangeabilityConnector dbConnector = new ChangeabilityConnector(projectSelectCombo.getItem(index1),versionCombo.getItem(index2));
				
				final ArrayList<PackageNode> packageNodeList = new ArrayList<PackageNode>();
				ArrayList<String> packageList = dbConnector.getpackageName();
						//dbConnector.getpackageName();
				for (String string : packageList) {
					PackageNode packageNode = new PackageNode(string);
					packageNode.setAfferents(dbConnector.packageAffernetCouplingslist(string));
					packageNode.setEfferents(dbConnector.packageEffernetCouplingslist(string));			
					packageNodeList.add(packageNode);		
				}
				
				for (PackageNode node : packageNodeList) {
					TreeItem item = new TreeItem(packageEfferentTree, SWT.NONE);
					item.setText(node.getName());
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
					item.setText(node.getName());
					ArrayList<String> list = node.getAfferents();
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
				

				packageEfferentTree.addListener(SWT.Expand, new Listener() {					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						TreeItem selectItem =  (TreeItem) event.item;
						System.out.println("selectitem:" + selectItem.getText());			
						if (packageNodeList.contains(new PackageNode(selectItem.getText()))) {
							int index = packageNodeList.indexOf(new PackageNode(selectItem.getText()));
							PackageNode pNode = packageNodeList.get(index);
							ArrayList<String> nextList = pNode.getEfferents();
							selectItem.removeAll();
							for (String string2 : nextList) {
								TreeItem nextItem = new TreeItem(selectItem, SWT.NONE);				
								nextItem.setText(string2);
								if (packageNodeList.contains(new PackageNode(string2))) {
									int index2 = packageNodeList.indexOf(new PackageNode(string2));
									PackageNode nextpNode = packageNodeList.get(index2);
									ArrayList<String> nextnextList = nextpNode.getEfferents();
									for (String string3 : nextnextList) {
										TreeItem nextnextItem = new TreeItem(nextItem, SWT.NONE);				
										nextnextItem.setText(string3);
									}						
								}
							}
						}							
					}
				});

				packageAfferentTree.addListener(SWT.Expand, new Listener() {					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						TreeItem selectItem =  (TreeItem) event.item;
						System.out.println("selectitem:" + selectItem.getText());			
						if (packageNodeList.contains(new PackageNode(selectItem.getText()))) {
							int index = packageNodeList.indexOf(new PackageNode(selectItem.getText()));
							PackageNode pNode = packageNodeList.get(index);
							ArrayList<String> nextList = pNode.getAfferents();
							selectItem.removeAll();
							for (String string2 : nextList) {
								TreeItem nextItem = new TreeItem(selectItem, SWT.NONE);				
								nextItem.setText(string2);
								if (packageNodeList.contains(new PackageNode(string2))) {
									int index2 = packageNodeList.indexOf(new PackageNode(string2));
									PackageNode nextpNode = packageNodeList.get(index2);
									ArrayList<String> nextnextList = nextpNode.getAfferents();
									for (String string3 : nextnextList) {
										TreeItem nextnextItem = new TreeItem(nextItem, SWT.NONE);				
										nextnextItem.setText(string3);
									}						
								}
							}
						}							
					}
				});

				packageAfferentTree.layout();
				packageEfferentTree.layout();
	  
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
