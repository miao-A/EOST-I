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

	    
         
     
     
//������״ͼ     
public void createBarDemo(String jpgname)     
{     
    CategoryDataset dataset = getBarDataset();     
    JFreeChart chart = ChartFactory.createBarChart3D(     
            "ˮ������ͼ", "ˮ��","����", dataset, PlotOrientation.VERTICAL,      
            true,false,false);     
         
    FileOutputStream jpg = null;     
    try {     
        jpg = new FileOutputStream(jpgname);     
        ChartUtilities.writeChartAsJPEG(jpg,0.5f,chart,400,300,null);     
             
             
    } catch (Exception e) {     
        // TODO �Զ����� catch ��     
        e.printStackTrace();     
    }     
    finally    
    {     
        try {     
            jpg.close();     
        } catch (IOException e) {     
            // TODO �Զ����� catch ��     
            e.printStackTrace();     
        }     
    }     
}     

//��ȡ��״ͼ����     
private CategoryDataset getBarDataset() {     
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();     
    dataset.addValue(100, "����", "ƻ��");     
    dataset.addValue(200, "�Ϻ�", "����");     
    dataset.addValue(300, "�ϲ�", "����");     
    dataset.addValue(400, "����", "�㽶");     
    dataset.addValue(500, "����", "��֦");     
    dataset.addValue(-250, "�Ϻ�", "��֦");     
    return dataset;     
}     
     
//������ͼ     
public void createPieDemo(String jpgname)     
{     
    DefaultPieDataset dataset = getPieDataset();     
    JFreeChart chart = ChartFactory.createPieChart3D("ˮ������", dataset, true, true, true);     
         
    FileOutputStream jpg = null;     
    try {     
        jpg = new FileOutputStream(jpgname);     
        ChartUtilities.writeChartAsJPEG(jpg,0.5f,chart,400,300,null);     
             
             
    } catch (Exception e) {     
        // TODO �Զ����� catch ��     
        e.printStackTrace();     
    }     
    finally    
    {     
        try {     
            jpg.close();     
        } catch (IOException e) {     
            // TODO �Զ����� catch ��     
            e.printStackTrace();     
        }     
    }     
}     

//��ȡ��ͼ����     
private DefaultPieDataset getPieDataset() {     
    DefaultPieDataset dataset = new DefaultPieDataset();     
    dataset.setValue("ƻ��", 100);     
    dataset.setValue("����", 200);     
    dataset.setValue("����", 300);     
    dataset.setValue("��֦", 400);     
    dataset.setValue("�㽶", 500);     
    dataset.setValue("����", 600);     
    return dataset;     
}     
}
