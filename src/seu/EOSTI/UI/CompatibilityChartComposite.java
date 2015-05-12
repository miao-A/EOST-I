package seu.EOSTI.UI;

import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class CompatibilityChartComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompatibilityChartComposite(Composite parent, int style) {
		super(parent, style);
/*		CompatibilityChartComposite chartD = new CompatibilityChartComposite();     
		chartD.createBarDemo("bar1.jpg");     
		chartD.createPieDemo("pie.jpg"); */
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	    
         
     
     
//创建柱状图     
public void createBarDemo(String jpgname)     
{     
    CategoryDataset dataset = getBarDataset();     
    JFreeChart chart = ChartFactory.createBarChart3D(     
            "水果产量图", "水果","产量", dataset, PlotOrientation.VERTICAL,      
            true,false,false);     
         
    FileOutputStream jpg = null;     
    try {     
        jpg = new FileOutputStream(jpgname);     
        ChartUtilities.writeChartAsJPEG(jpg,0.5f,chart,400,300,null);     
             
             
    } catch (Exception e) {     
        // TODO 自动生成 catch 块     
        e.printStackTrace();     
    }     
    finally    
    {     
        try {     
            jpg.close();     
        } catch (IOException e) {     
            // TODO 自动生成 catch 块     
            e.printStackTrace();     
        }     
    }     
}     

//获取柱状图数据     
private CategoryDataset getBarDataset() {     
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();     
    dataset.addValue(100, "北京", "苹果");     
    dataset.addValue(200, "上海", "梨子");     
    dataset.addValue(300, "南昌", "葡萄");     
    dataset.addValue(400, "海南", "香蕉");     
    dataset.addValue(500, "北京", "荔枝");     
    dataset.addValue(-250, "上海", "荔枝");     
    return dataset;     
}     
     
//创建饼图     
public void createPieDemo(String jpgname)     
{     
    DefaultPieDataset dataset = getPieDataset();     
    JFreeChart chart = ChartFactory.createPieChart3D("水果产量", dataset, true, true, true);     
         
    FileOutputStream jpg = null;     
    try {     
        jpg = new FileOutputStream(jpgname);     
        ChartUtilities.writeChartAsJPEG(jpg,0.5f,chart,400,300,null);     
             
             
    } catch (Exception e) {     
        // TODO 自动生成 catch 块     
        e.printStackTrace();     
    }     
    finally    
    {     
        try {     
            jpg.close();     
        } catch (IOException e) {     
            // TODO 自动生成 catch 块     
            e.printStackTrace();     
        }     
    }     
}     

//获取饼图数据     
private DefaultPieDataset getPieDataset() {     
    DefaultPieDataset dataset = new DefaultPieDataset();     
    dataset.setValue("苹果", 100);     
    dataset.setValue("梨子", 200);     
    dataset.setValue("葡萄", 300);     
    dataset.setValue("荔枝", 400);     
    dataset.setValue("香蕉", 500);     
    dataset.setValue("枣子", 600);     
    return dataset;     
}     
}
