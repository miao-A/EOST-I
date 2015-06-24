package seu.EOSTI.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.awt.SWT_AWT;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.Frame;
import java.awt.Panel;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import seu.EOSTI.Chart.ChangeabilityLineChart;
import seu.EOSTI.Chart.ExtensibilityLineChart;
import seu.EOSTI.Chart.LineChart;
import seu.EOSTI.DBConnect.ProjectConnector;
import seu.EOSTI.DBConnect.ProjectInfoConnector;
import seu.EOSTI.Parser.ExtensibilityDiff;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;


public class ExtensibilityDiffComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ExtensibilityDiffComposite(Composite parent, int style) {
		super(parent, style);
		
		Label lblNull = new Label(this, SWT.NONE);
		lblNull.setBounds(21, 10, 61, 17);
		lblNull.setText("\u9009\u62E9\u9879\u76EE\uFF1A");
	
		final Combo projectSelectCombo = new Combo(this, SWT.NONE);
		projectSelectCombo.setBounds(102, 7, 98, 25);
		
		final ProjectConnector pcConnector = new ProjectConnector();
		ArrayList<String> rStrings = pcConnector.getProject();
		for (String string : rStrings) {
			projectSelectCombo.add(string);
		}		
		
		final CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setLocation(13, 53);
		tabFolder.setSize(655, 480);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem extenDiffTabItem = new CTabItem(tabFolder, SWT.NONE);
		extenDiffTabItem.setText("\u53EF\u6269\u5C55\u6027\u53D8\u66F4");
		
		TextViewer textViewer = new TextViewer(tabFolder, SWT.BORDER);
		final StyledText eDiffText = textViewer.getTextWidget();
		extenDiffTabItem.setControl(eDiffText);
		
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("\u53EF\u66FF\u4EE3\u6027\u53D8\u66F4");
		
		TextViewer textViewer_1 = new TextViewer(tabFolder, SWT.BORDER);
		StyledText cDiffText = textViewer_1.getTextWidget();
		tabItem.setControl(cDiffText);
			
		projectSelectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = projectSelectCombo.getSelectionIndex();
				String projName = projectSelectCombo.getItem(index);
				
				{
					eDiffText.setText("");
					ProjectInfoConnector projectInfoConnector = new ProjectInfoConnector();
					ArrayList<String> list = projectInfoConnector.getVersion(projName);
					ExtensibilityDiff extensibilityDiff = new ExtensibilityDiff(projName);
					for (int i = 1; i < list.size(); i++) {
						System.out.println(list.get(i-1)+" compare with " + list.get(i));
						String textString = list.get(i-1)+" compare with " + list.get(i);
						
						HashMap<String, HashMap<String,List<String>>> diffmap = extensibilityDiff.diffInProject(list.get(i-1), list.get(i));
						for (String pkgName : diffmap.keySet()) {
							
							HashMap<String, List<String>> map = diffmap.get(pkgName);
							if (map.containsKey("interface")||map.containsKey("+interface")||map.containsKey("-interface")) {
								textString += "\npackage: " + pkgName;
								if (map.containsKey("+interface")) {									
									textString += "\n+interface\t"+map.get("+interface").size();
									for (String string : map.get("+interface")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("+abstract")) {
									textString += "\n+abstract\t"+map.get("+abstract").size();
									for (String string : map.get("+abstract")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("+concrete")) {
									textString += "\n+concrete\t"+map.get("+concrete").size();
									for (String string : map.get("+concrete")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("-interface")) {
									textString += "\n-interface\t"+map.get("-interface").size();
									for (String string : map.get("-interface")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("-abstract")) {
									textString += "\n-abstract\t"+map.get("-abstract").size();
									for (String string : map.get("-abstract")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("-concrete")) {
									textString += "\n-concrete\t"+map.get("-concrete").size();
									for (String string : map.get("-concrete")) {
										textString += "\nName:\t"+string;
									}
								}							
							}else {
								textString += "\nNo effect";
							}							
						}
						
						if (diffmap.size()==0) {
							textString += "\nNo effect";
						}
						eDiffText.append(textString+"\n\n");
					}					
					System.out.println("diff print");
				}
				
				///
				{
				
					System.out.println("可替代性指示图");

					System.out.println("chart print");
					
				}
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
