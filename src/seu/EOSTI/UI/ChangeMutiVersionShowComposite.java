package seu.EOSTI.UI;

import java.awt.Frame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Button;
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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;

import seu.EOSTI.Chart.ChangeabilityLineChart;
import seu.EOSTI.Chart.ExtensibilityLineChart;
import seu.EOSTI.Chart.LineChart;
import seu.EOSTI.DBConnect.ProjectConnector;
import seu.EOSTI.DBConnect.ProjectInfoConnector;
import seu.EOSTI.Parser.ChangeabilityDiff;
import seu.EOSTI.Parser.ExtensibilityDiff;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;

public class ChangeMutiVersionShowComposite extends Composite {

	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws IOException 
	 */
	public ChangeMutiVersionShowComposite(Composite parent, int style) throws IOException {
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
				
		final CTabItem changeTabItem = new CTabItem(tabFolder, SWT.NONE);
		changeTabItem.setText("\u53EF\u66FF\u4EE3\u6027\u8D8B\u52BF\u56FE");
		
		CTabItem changeDifftabItem = new CTabItem(tabFolder, SWT.NONE);
		changeDifftabItem.setText("\u7248\u672C\u53D8\u66F4");
		
		TextViewer textViewer_1 = new TextViewer(tabFolder, SWT.BORDER|SWT.V_SCROLL);
		final StyledText cDiffText = textViewer_1.getTextWidget();
		changeDifftabItem.setControl(cDiffText);
		
		final CTabItem changeDiff2tabItem = new CTabItem(tabFolder, SWT.NONE);
		changeDiff2tabItem.setText("\u7248\u672C\u53D8\u66F42");
		

		
		CTabItem classChangeDifftabItem = new CTabItem(tabFolder, SWT.NONE);
		classChangeDifftabItem.setText("\u7248\u672C\u53D8\u66F4\u8BE6\u7EC6\u4FE1\u606F");
		
		TextViewer textViewer = new TextViewer(tabFolder, SWT.BORDER);
		final StyledText moreCDiffText = textViewer.getTextWidget();
		classChangeDifftabItem.setControl(moreCDiffText);
		
		final CTabItem classChangeDiff2TabItem = new CTabItem(tabFolder, SWT.NONE);
		classChangeDiff2TabItem.setText("\u7248\u672C\u53D8\u66F4\u8BE6\u7EC6\u4FE1\u606F2");
			
		projectSelectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = projectSelectCombo.getSelectionIndex();
				String projName = projectSelectCombo.getItem(index);		
				
				{				
					System.out.println("可替代性指示图");
					//BarChart changeabilityChart = new ChangeabilityBarChart("可替代性指示图");
					LineChart changeabilityChart = new ChangeabilityLineChart("可替代性指示图");
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
									textString += "\n+import \t"+map.get("+import").size();
									for (String string : map.get("+import")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("-import")) {									
									textString += "\n-import \t"+map.get("-import").size();
									for (String string : map.get("-import")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("+export")) {									
									textString += "\n+export \t"+map.get("+export").size();
									for (String string : map.get("+export")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("-export")) {									
									textString += "\n-export \t"+map.get("-export").size();
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
				
				{
					moreCDiffText.setText("");
					ProjectInfoConnector projectInfoConnector = new ProjectInfoConnector();
					ArrayList<String> list = projectInfoConnector.getVersion(projName);
					ChangeabilityDiff changeDiff = new ChangeabilityDiff(projName);
					for (int i = 1; i < list.size(); i++) {
						System.out.println(list.get(i-1)+" compare with " + list.get(i));
						String textString = list.get(i-1)+" compare with " + list.get(i);
						
						HashMap<String, HashMap<String,List<String>>> diffmap = changeDiff.moreDiffInProject(list.get(i-1), list.get(i));
						for (String pkgName : diffmap.keySet()) {
							
							HashMap<String, List<String>> map = diffmap.get(pkgName);
							if (map.containsKey("+import")||map.containsKey("+export")||map.containsKey("-import")||map.containsKey("-export")) {
								textString += "\npackage: " + pkgName;
								
								if (map.containsKey("+import")) {									
									textString += "\n+import \t"+map.get("+import").size();
									for (String string : map.get("+import")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("-import")) {									
									textString += "\n-import \t"+map.get("-import").size();
									for (String string : map.get("-import")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("+export")) {									
									textString += "\n+export \t"+map.get("+export").size();
									for (String string : map.get("+export")) {
										textString += "\nName:\t"+string;
									}
								}
								
								if (map.containsKey("-export")) {									
									textString += "\n-export \t"+map.get("-export").size();
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
						moreCDiffText.append(textString+"\n\n");						
					}		
					textAddColor(moreCDiffText);	
					System.out.println("diff print");
				}
				
				
				{
					Composite composite = new Composite(tabFolder, SWT.NONE);
					changeDiff2tabItem.setControl(composite);
					
					final StyledText cDiffText2 = new StyledText(composite, SWT.BORDER|SWT.V_SCROLL);
					cDiffText2.setBounds(0, 93, 649, 348);
					
					cDiffText2.setText("");
					cDiffText2.setEditable(false);
					ProjectInfoConnector projectInfoConnector = new ProjectInfoConnector();
					ArrayList<String> list = projectInfoConnector.getVersion(projName);
					final ChangeabilityDiff	changeabilityDiff = new ChangeabilityDiff(projName);
					Rectangle rectangle = new Rectangle(0, 0, 200, 17);
					for (int i = 1; i < list.size(); i++) {
						//System.out.println(list.get(i-1)+" compare with " + list.get(i));
						String textString = list.get(i-1)+" compare with " + list.get(i);
						
						final Button btnRadioButton = new Button(composite, SWT.RADIO);
						btnRadioButton.setBounds(rectangle);
						btnRadioButton.setText(textString);						
						if (i%2==1) {							
							rectangle.x = 2*rectangle.width;
							
						}else {
							rectangle.x = 0;
							rectangle.y += rectangle.height;
						}
						
						btnRadioButton.addSelectionListener(new SelectionListener() {
							
							@Override
							public void widgetSelected(SelectionEvent arg0) {
								// TODO Auto-generated method stub
								cDiffText2.setText(btnRadioButton.getText());
								String[] index = btnRadioButton.getText().split(" ");
								
								HashMap<String, HashMap<String,List<String>>> diffmap = changeabilityDiff.diffInProject(index[0], index[index.length-1]);
								String textString = new String();
								for (String pkgName : diffmap.keySet()) {
									
									HashMap<String, List<String>> map = diffmap.get(pkgName);
									if (map.containsKey("+import")||map.containsKey("+export")||map.containsKey("-import")||map.containsKey("-export")) {
										textString += "\npackage: " + pkgName;
										
										if (map.containsKey("+import")) {									
											textString += "\n+import \t"+map.get("+import").size();
											for (String string : map.get("+import")) {
												textString += "\nName:\t"+string;
											}
										}
										
										if (map.containsKey("-import")) {									
											textString += "\n-import \t"+map.get("-import").size();
											for (String string : map.get("-import")) {
												textString += "\nName:\t"+string;
											}
										}
										
										if (map.containsKey("+export")) {									
											textString += "\n+export \t"+map.get("+export").size();
											for (String string : map.get("+export")) {
												textString += "\nName:\t"+string;
											}
										}
										
										if (map.containsKey("-export")) {									
											textString += "\n-export \t"+map.get("-export").size();
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
								
								cDiffText2.append(textString+"\n\n");								
								textAddColor(cDiffText2);	
								System.out.println("diff print");
								
							}

							@Override
							public void widgetDefaultSelected(
									SelectionEvent arg0) {
								// TODO Auto-generated method stub
								
							}
							
							
						});						
											
					}		
					
				}
				
				
				{
					Composite composite = new Composite(tabFolder, SWT.NONE);
					classChangeDiff2TabItem.setControl(composite);
					
					final StyledText mcDiffText2 = new StyledText(composite, SWT.BORDER|SWT.V_SCROLL);
					mcDiffText2.setBounds(0, 93, 649, 348);
					
					mcDiffText2.setText("");
					mcDiffText2.setEditable(false);
					ProjectInfoConnector projectInfoConnector = new ProjectInfoConnector();
					ArrayList<String> list = projectInfoConnector.getVersion(projName);
					final ChangeabilityDiff	changeabilityDiff = new ChangeabilityDiff(projName);
					Rectangle rectangle = new Rectangle(0, 0, 200, 17);
					for (int i = 1; i < list.size(); i++) {
						//System.out.println(list.get(i-1)+" compare with " + list.get(i));
						String textString = list.get(i-1)+" compare with " + list.get(i);
						
						final Button btnRadioButton = new Button(composite, SWT.RADIO);
						btnRadioButton.setBounds(rectangle);
						btnRadioButton.setText(textString);						
						if (i%2==1) {							
							rectangle.x = 2*rectangle.width;
							
						}else {
							rectangle.x = 0;
							rectangle.y += rectangle.height;
						}
						
						btnRadioButton.addSelectionListener(new SelectionListener() {
							
							@Override
							public void widgetSelected(SelectionEvent arg0) {
								// TODO Auto-generated method stub
								mcDiffText2.setText(btnRadioButton.getText());
								String[] index = btnRadioButton.getText().split(" ");
								
								HashMap<String, HashMap<String,List<String>>> diffmap = changeabilityDiff.moreDiffInProject(index[0], index[index.length-1]);
								String textString = new String();
								for (String pkgName : diffmap.keySet()) {
									
									HashMap<String, List<String>> map = diffmap.get(pkgName);
									if (map.containsKey("+import")||map.containsKey("+export")||map.containsKey("-import")||map.containsKey("-export")) {
										textString += "\npackage: " + pkgName;
										
										if (map.containsKey("+import")) {									
											textString += "\n+import \t"+map.get("+import").size();
											for (String string : map.get("+import")) {
												textString += "\nName:\t"+string;
											}
										}
										
										if (map.containsKey("-import")) {									
											textString += "\n-import \t"+map.get("-import").size();
											for (String string : map.get("-import")) {
												textString += "\nName:\t"+string;
											}
										}
										
										if (map.containsKey("+export")) {									
											textString += "\n+export \t"+map.get("+export").size();
											for (String string : map.get("+export")) {
												textString += "\nName:\t"+string;
											}
										}
										
										if (map.containsKey("-export")) {									
											textString += "\n-export \t"+map.get("-export").size();
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
								
								mcDiffText2.append(textString+"\n\n");								
								textAddColor(mcDiffText2);	
								System.out.println("diff print");
								
							}

							@Override
							public void widgetDefaultSelected(
									SelectionEvent arg0) {
								// TODO Auto-generated method stub
								
							}
							
							
						});						
											
					}		
					
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
