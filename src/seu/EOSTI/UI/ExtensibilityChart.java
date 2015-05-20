package seu.EOSTI.UI;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.ReturnStatement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import seu.EOSTI.DBConnect.ExtensibilityInfoConnector;
import seu.EOSTI.DBConnect.ProjectConnector;
import seu.EOSTI.DBConnect.ProjectInfoConnector;

public class ExtensibilityChart {
	private DefaultCategoryDataset dataset = null;
	
	public ExtensibilityChart(){
		dataset = new DefaultCategoryDataset();
	}
	
	public JFreeChart createChart() throws IOException{ 
	    
        CategoryDataset dataset = getDataSet(); 
        JFreeChart chart = ChartFactory.createBarChart( 
                           "����չ��ָʾͼ", // ͼ�����
                           "����", // Ŀ¼�����ʾ��ǩ
                           "����%", // ��ֵ�����ʾ��ǩ
                            dataset, // ���ݼ�
                            PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ
                            true,  // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������ false)
                            false, // �Ƿ����ɹ���
                            false  // �Ƿ����� URL ����
                            );
        //��������
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("����", Font.PLAIN, 20));      
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
        domainAxis.setLabelFont(new Font("����", Font.PLAIN, 16));  
       
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 8));

        
        
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));  
        numberaxis.setLabelFont(new Font("����", Font.PLAIN, 12));  
        numberaxis.setVerticalTickLabels(false);
       
        chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12));
	                       
	   return chart;
	} 

	
	public void creatDataSet(String projectName){
		
		ProjectConnector pConnector = new ProjectConnector();
		List<String> versionlist = pConnector.getVersion(projectName);
		
		Map<String, HashMap<String, Double>> dataMap = new HashMap<String, HashMap<String, Double>>();
		HashMap<String, Double> map = new HashMap<String, Double>();
		
		for (String version : versionlist) {
			ExtensibilityInfoConnector dbConnector = new ExtensibilityInfoConnector(projectName, version);
			List<String> pkgNameList = dbConnector.getpackageName();
			for (String pkgName : pkgNameList) {
				double ratio = dbConnector.getExtensibilityRatio(pkgName);
				map.put(pkgName, new Double(ratio));
			}
			dataMap.put(projectName+version, map);			
		}
		this.setDataSet(dataMap);
	}

    /** 
    * ��ȡһ����ʾ�õļ����ݼ�����
    * @return 
    */ 

	public void setDataSet(Map<String, HashMap<String, Double>> dataMap){		
		Iterator<Entry<String, HashMap<String, Double>>> it = dataMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HashMap<String, Double>> entry1 = it.next();
			
			for (Map.Entry<String, Double> entry2 :entry1.getValue().entrySet() ) {
				System.out.println("key= " + entry1.getKey() + " and value= " + entry2.getKey()+entry2.getValue());
				dataset.addValue(entry2.getValue(), entry1.getKey(), entry2.getKey());
			}
		
		}
		
	}

	
    private  CategoryDataset getDataSet() { 
       /* DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        dataset.addValue(10, "", "ƻ��"); 
        dataset.addValue(20, "", "����"); 
        dataset.addValue(30, "", "����"); 
        dataset.addValue(40, "", "�㽶"); 
        dataset.addValue(50, "", "��֦"); */
        return dataset; 
    } 

   
} 

