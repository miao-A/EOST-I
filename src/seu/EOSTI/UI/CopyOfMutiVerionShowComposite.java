package seu.EOSTI.UI;

import java.awt.Frame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.Panel;
import java.awt.BorderLayout;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

import seu.EOSTI.Chart.ChangeabilityLineChart;
import seu.EOSTI.Chart.ExtensibilityLineChart;
import seu.EOSTI.Chart.LineChart;
import seu.EOSTI.DBConnect.ProjectConnector;
import seu.EOSTI.DBConnect.ProjectInfoConnector;
import seu.EOSTI.Parser.ChangeabilityDiff;
import seu.EOSTI.Parser.ExtensibilityDiff;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;

public class CopyOfMutiVerionShowComposite extends Composite {

	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws IOException 
	 */
	public CopyOfMutiVerionShowComposite(Composite parent, int style) throws IOException {
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
		
		final CTabItem extenTabItem = new CTabItem(tabFolder, SWT.NONE);
		extenTabItem.setText("\u53EF\u6269\u5C55\u6027");
		
		CTabItem extenDiffTabItem = new CTabItem(tabFolder, SWT.NONE);
		extenDiffTabItem.setText("\u53EF\u6269\u5C55\u6027\u53D8\u66F4");
		
		TextViewer textViewer = new TextViewer(tabFolder, SWT.BORDER|SWT.V_SCROLL);
		final StyledText eDiffText = textViewer.getTextWidget();
		
		extenDiffTabItem.setControl(eDiffText);
				
		final CTabItem changeTabItem = new CTabItem(tabFolder, SWT.NONE);
		changeTabItem.setText("\u53EF\u66FF\u4EE3\u6027");
		
		CTabItem changeDifftabItem = new CTabItem(tabFolder, SWT.NONE);
		changeDifftabItem.setText("\u53EF\u66FF\u4EE3\u6027\u53D8\u66F4");
		
		TextViewer textViewer_1 = new TextViewer(tabFolder, SWT.BORDER|SWT.V_SCROLL);
		final StyledText cDiffText = textViewer_1.getTextWidget();
		changeDifftabItem.setControl(cDiffText);
			
		projectSelectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = projectSelectCombo.getSelectionIndex();
				String projName = projectSelectCombo.getItem(index);
				
				{
					System.out.println("����չ��ָʾͼ");
					//BarChart extensibilityChart = new ExtensibilityBarChart("����չ��ָʾͼ");	
					LineChart extensibilityChart = new ExtensibilityLineChart("����չ��ָʾͼ");	
					extensibilityChart.creatDataSet(projName);		
					JFreeChart chart = null;
					try {
						chart = extensibilityChart.createChart();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Composite chartComposite = new Composite(tabFolder, SWT.EMBEDDED);
					extenTabItem.setControl(chartComposite);
					
					Frame frame1 = SWT_AWT.new_Frame(chartComposite);
					
					Panel panel1 = new Panel();
					frame1.add(panel1);
					panel1.setLayout(new BorderLayout(0, 0));
					ChartPanel extenChartPanel = new ChartPanel(chart);
					panel1.add(extenChartPanel, BorderLayout.SOUTH);						
					
					frame1.setVisible(true);					
					panel1.setVisible(true);
					extenChartPanel.setVisible(true);
					System.out.println("chart print");
				}
				
				///
				{
				
					System.out.println("�������ָʾͼ");
					//BarChart changeabilityChart = new ChangeabilityBarChart("�������ָʾͼ");
					LineChart changeabilityChart = new ChangeabilityLineChart("�������ָʾͼ");
					changeabilityChart.creatDataSet(projName);		
					JFreeChart chart = null;
					try {
						chart = changeabilityChart.createChart();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Composite chartComposite = new Composite(tabFolder, SWT.EMBEDDED);
					changeTabItem.setControl(chartComposite);
					
					Frame frame2 = SWT_AWT.new_Frame(chartComposite);
					
					Panel panel2 = new Panel();
					frame2.add(panel2);
					panel2.setLayout(new BorderLayout(0, 0));
					ChartPanel changeChartPanel = new ChartPanel(chart);
					panel2.add(changeChartPanel, BorderLayout.SOUTH);	
					frame2.setVisible(true);
					panel2.setVisible(true);
					changeChartPanel.setVisible(true);
					System.out.println("chart print");
					
				}
				
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
					textAddColor(eDiffText);	
					System.out.println("diff print");
				}
				
				
				{
					cDiffText.setText("");
					ProjectInfoConnector projectInfoConnector = new ProjectInfoConnector();
					ArrayList<String> list = projectInfoConnector.getVersion(projName);
					ChangeabilityDiff changeDiff = new ChangeabilityDiff(projName);
					for (int i = 1; i < list.size(); i++) {
						System.out.println(list.get(i-1)+" compare with " + list.get(i));
						String textString = list.get(i-1)+" compare with " + list.get(i);
						
						HashMap<String, HashMap<String,List<String>>> diffmap = changeDiff.diffInProject(list.get(i-1), list.get(i));
						for (String pkgName : diffmap.keySet()) {
							
							HashMap<String, List<String>> map = diffmap.get(pkgName);
							if (map.containsKey("+import")||map.containsKey("+export")||map.containsKey("-import")||map.containsKey("-export")) {
								textString += "\npackage: " + pkgName;
								
								if (map.containsKey("+import")) {									
									textString += "\n+import\t"+map.get("+import").size();
									for (String string : map.get("+import")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("-import")) {									
									textString += "\n-import\t"+map.get("-import").size();
									for (String string : map.get("-import")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("+export")) {									
									textString += "\n+export\t"+map.get("+export").size();
									for (String string : map.get("+export")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("-export")) {									
									textString += "\n-export\t"+map.get("-export").size();
									for (String string : map.get("-export")) {
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
						cDiffText.append(textString+"\n\n");						
					}		
					textAddColor(cDiffText);	
					System.out.println("diff print");
				}
			}			
			
		});
	
	}
	
	private void textAddColor(StyledText styledText){
		String text = styledText.getText();
		String[] strings = text.split("\n");
		int index = 0;
		for (int i = 0; i < strings.length; i++) {
		
			Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
			Color blue = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
		    /*StyleRange sr = new StyleRange(i, strings[i].length(), red, null);
		    styledText.setStyleRange(sr);*/
			StyleRange range = styledText.getStyleRangeAtOffset(index);
			if (strings[i].contains("compare with")) {
				if (range == null) {
					range = new StyleRange();
				}
				range.fontStyle = SWT.BOLD;
				range.foreground = red;
				range.start = index;
				range.length = strings[i].length();
				styledText.setStyleRange(range);
			}
			if (strings[i].startsWith("package: ")) {
				if (range == null) {
					range = new StyleRange();
				}
				range.fontStyle = SWT.BOLD;
				range.foreground = blue;
				range.start = index;
				range.length = strings[i].length();
				styledText.setStyleRange(range);
			}
			
			index += strings[i].length()+1;
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}