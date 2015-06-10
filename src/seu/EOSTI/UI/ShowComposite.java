package seu.EOSTI.UI;

import java.awt.Frame;
import java.io.IOException;
import java.util.ArrayList;

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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TabFolder;

import seu.EOSTI.Chart.BarChart;
import seu.EOSTI.Chart.ChangeabilityBarChart;
import seu.EOSTI.Chart.ExtensibilityBarChart;
import seu.EOSTI.DBConnect.ProjectConnector;

public class ShowComposite extends Composite {

	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws IOException 
	 */
	public ShowComposite(Composite parent, int style) throws IOException {
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
				
		final CTabItem changeTabItem = new CTabItem(tabFolder, SWT.NONE);
		changeTabItem.setText("\u53EF\u66FF\u4EE3\u6027");
			
		projectSelectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = projectSelectCombo.getSelectionIndex();
				String projName = projectSelectCombo.getItem(index);
				
				{
					System.out.println("可扩展性指示图");
					BarChart extensibilityChart = new ExtensibilityBarChart("可扩展性指示图");		
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
				}
				
				///
				{
				
					System.out.println("可替代性指示图");
					BarChart changeabilityChart = new ChangeabilityBarChart("可替代性指示图");		
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
				}
			}
		});
	
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
