package seu.EOSTI.UI;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.SWT;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;

import java.awt.Panel;
import java.awt.BorderLayout;

import javax.swing.JRootPane;

public class NULLComposite extends Composite {

	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws IOException 
	 */
	public NULLComposite(Composite parent, int style) throws IOException {
		super(parent, style);
		
		Label lblNull = new Label(this, SWT.NONE);
		lblNull.setBounds(21, 10, 61, 17);
		lblNull.setText("Null");
		
		Composite chartComposite = new Composite(this, SWT.EMBEDDED);
		chartComposite.setBounds(10, 112, 541, 226);
		
		Frame frame = SWT_AWT.new_Frame(chartComposite);
		
		Panel panel = new Panel();
		frame.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
	
		ExtensibilityChart extensibilityChart = new ExtensibilityChart();
		
		
		Map<String, HashMap<String, Double>> dataMap = new HashMap<String, HashMap<String, Double>>();
		HashMap<String, Double> map = new HashMap<String, Double>();
		map.put("org.jeditor", new Double(0));
		map.put("org.jeditor.app", new Double(0));
		map.put("org.jeditor.diff", new Double(8.33));
		map.put("org.jeditor.gui", new Double(9.62));
		map.put("org.jeditor.scripts", new Double(0));
		map.put("org.jeditor.scripts.base", new Double(0));		
		dataMap.put("jEditor0.2", map);
		
		map = new HashMap<String, Double>();
		
		map.put("org.jeditor", new Double(0));
		map.put("org.jeditor.app", new Double(0));
		map.put("org.jeditor.diff", new Double(8.33));
		map.put("org.jeditor.gui", new Double(9.62));
		map.put("org.jeditor.scripts", new Double(0));
		map.put("org.jeditor.scripts.base", new Double(0));		
		dataMap.put("jEditor0.3", map);
		
		map = new HashMap<String, Double>();
		
		map.put("org.jeditor", new Double(0));
		map.put("org.jeditor.app", new Double(0));
		map.put("org.jeditor.diff", new Double(8.33));
		map.put("org.jeditor.gui", new Double(8.77));
		map.put("org.jeditor.scripts", new Double(0));
		map.put("org.jeditor.scripts.base", new Double(0));		
		dataMap.put("jEditor0.4.2", map);
		map = new HashMap<String, Double>();
		
		extensibilityChart.setDataSet(dataMap);

		final JFreeChart chart = extensibilityChart.createChart();
		ChartPanel extenChartPanel = new ChartPanel(chart);
		panel.add(extenChartPanel);	
			
		frame.setVisible(true);
		panel.setVisible(true);
		extenChartPanel.setVisible(true);
	
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
