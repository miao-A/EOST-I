package seu.EOSTI.UI;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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

public class ExtensibilityChart {
	private DefaultCategoryDataset dataset = null;
	
	public ExtensibilityChart(){
		dataset = new DefaultCategoryDataset();
	}
	
	public JFreeChart createChart() throws IOException{ 
	    
        CategoryDataset dataset = getDataSet(); 
        JFreeChart chart = ChartFactory.createBarChart3D( 
                           "可扩展性指示图", // 图表标题
                           "包名", // 目录轴的显示标签
                           "比例%", // 数值轴的显示标签
                            dataset, // 数据集
                            PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                            true,  // 是否显示图例(对于简单的柱状图必须是 false)
                            false, // 是否生成工具
                            false  // 是否生成 URL 链接
                            );
        //中文乱码
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 20));      
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 16));  
       
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 8));

        
        
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));  
        numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));  
        numberaxis.setVerticalTickLabels(false);
       
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
	                       
	   return chart;
	} 


    /** 
    * 获取一个演示用的简单数据集对象
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
        dataset.addValue(10, "", "苹果"); 
        dataset.addValue(20, "", "梨子"); 
        dataset.addValue(30, "", "葡萄"); 
        dataset.addValue(40, "", "香蕉"); 
        dataset.addValue(50, "", "荔枝"); */
        return dataset; 
    } 
    /** 
    * 获取一个演示用的组合数据集对象
    * @return 
    */ 
    private static CategoryDataset getDataSet2() { 
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        dataset.addValue(100, "北京", "苹果"); 
        dataset.addValue(100, "上海", "苹果"); 
        dataset.addValue(100, "广州", "苹果"); 
        dataset.addValue(200, "北京", "梨子"); 
        dataset.addValue(200, "上海", "梨子"); 
        dataset.addValue(200, "广州", "梨子"); 
        dataset.addValue(300, "北京", "葡萄"); 
        dataset.addValue(300, "上海", "葡萄"); 
        dataset.addValue(300, "广州", "葡萄"); 
        dataset.addValue(400, "北京", "香蕉"); 
        dataset.addValue(400, "上海", "香蕉"); 
        dataset.addValue(400, "广州", "香蕉"); 
        dataset.addValue(500, "北京", "荔枝"); 
        dataset.addValue(500, "上海", "荔枝"); 
        dataset.addValue(500, "广州", "荔枝"); 
        return dataset; 
    } 
} 

