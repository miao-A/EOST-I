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
		
		final CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("\u53EF\u6269\u5C55\u6027");
		
		
		
		CTabItem tabItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("\u53EF\u66FF\u4EE3\u6027");
		
		TabFolder tabFolder_1 = new TabFolder(tabFolder, SWT.NONE);
		tabItem_1.setControl(tabFolder_1);
			
		projectSelectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = projectSelectCombo.getSelectionIndex();
				String projName = projectSelectCombo.getItem(index);
				ExtensibilityChart extensibilityChart = new ExtensibilityChart();		
				extensibilityChart.creatDataSet(projName);		
				JFreeChart chart = null;
				try {
					chart = extensibilityChart.createChart();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Composite chartComposite = new Composite(tabFolder, SWT.EMBEDDED);
				tabItem.setControl(chartComposite);
				
				Frame frame = SWT_AWT.new_Frame(chartComposite);
				
				Panel panel = new Panel();
				frame.add(panel);
				panel.setLayout(new BorderLayout(0, 0));
				ChartPanel extenChartPanel = new ChartPanel(chart);
				panel.add(extenChartPanel, BorderLayout.SOUTH);	
				frame.setVisible(true);
				panel.setVisible(true);
				extenChartPanel.setVisible(true);
			}
		});
		
	
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
