package seu.EOSTI.UI;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.Layer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

import seu.EOSTI.Chart.ChangeabilityLineChart;
import seu.EOSTI.Chart.ExtensibilityLineChart;

public class bloodSugarChart {
 
       public bloodSugarChart() {
              this.createChart();
       }
 
// ������ݼ� �������������Ϊ�˲��������д��һ���Զ��������ݵ����ӣ�
       public DefaultCategoryDataset createDataset() {
 
              DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
       // ��������
              String series = "Ѫ��ֵ";  // seriesָ�ľ��Ǳ����������������
                              //��� �������ߵ�������þ���Ҫ��ϵ��serise
                              //����˵setSeriesPaint ���������ߵ���ɫ
 
       // ��������(������) 
              String[] time = new String[15];
              String[] timeValue = { "6-1��", "6-2��", "6-3��", "6-4��", "6-5��", "6-6��",
                            "6-7��", "6-8��", "6-9��", "6-10��", "6-11��", "6-12��", "6-13��",
                            "6-14��", "6-15��" };
              for (int i = 0; i < 15; i++) {
                     time[i] = timeValue[i];
              }
      //����������ֵ
              for (int i = 0; i < 15; i++) {
                     java.util.Random r = new java.util.Random();
                     linedataset.addValue(i + i * 9.34 + r.nextLong() % 100,  //ֵ
series,  //����������
                                              time[i]); // ��Ӧ�ĺ���
              }
 
              return linedataset;
       }
      
// ����ͼ�����JFreeChart
/*
*������Ŀ������JFreeChart
*������������� Plot �䳣�������У�CategoryPlot, MultiplePiePlot, PiePlot , XYPlot
*
***
*/
       public void createChart() {
 
              try {
                     //����ͼ�����
                     JFreeChart chart = ChartFactory.createLineChart(null,// ������Ŀ���ַ�������
                                   "�ɼ�ʱ��", // ����
                                   "Ѫ��ֵ", // ����
                                   this.createDataset(), // ������ݼ�
                                   PlotOrientation.VERTICAL, // ͼ�귽��ֱ
                                   true, // ��ʾͼ��
                                   false, // �������ɹ���
                                   false // ��������URL��ַ
                                   );
//������Ŀ������chart  ��������chart�ı�����ɫ
 
                     // ����ͼ��
                     CategoryPlot plot = chart.getCategoryPlot();
                     // ͼ�����Բ���
                     plot.setBackgroundPaint(Color.white);
plot.setDomainGridlinesVisible(true);  //���ñ����������Ƿ�ɼ�
plot.setDomainGridlinePaint(Color.BLACK); //���ñ�����������ɫ
plot.setRangeGridlinePaint(Color.GRAY);
                     plot.setNoDataMessage("û������");//û������ʱ��ʾ������˵���� 
                     // ���������Բ���
                     NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
                     rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
                     rangeAxis.setAutoRangeIncludesZero(true); //�Զ�����
                     rangeAxis.setUpperMargin(0.20);
                     rangeAxis.setLabelAngle(Math.PI / 2.0); 
                     rangeAxis.setAutoRange(false);
                     // ������Ⱦ���� ��Ҫ�Ƕ�����������
                     LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
                                   .getRenderer();
                     renderer.setBaseItemLabelsVisible(true);
                     renderer.setSeriesPaint(0, Color.black);    //�������ߵ���ɫ
                  renderer.setBaseShapesFilled(true);
                     renderer.setBaseItemLabelsVisible(true);     
                     renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                                   ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
                     renderer
                                   .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
/*
*�����StandardCategoryItemLabelGenerator()����ǿ���£���ʱ�����*��������ͷ���Σ�Standard**ItemLabelGenerator��ͨ�õ� ��Ϊ�Ҵ���*����CategoryPlot   ���Ժܶ����ö���Category���
*��XYPlot  ��Ӧ������ �� StandardXYItemLabelGenerator
*/
//���ڱ����Ա  ���ָ���һ�����ͷ������뵽�����������Ʒ�����˼
//���Ǳ����еİɣ�Ŀǰֻ�����������ˡ���
                     renderer.setBaseItemLabelFont(new Font("Dialog", 1, 14));  //������ʾ�۵�������״
                     plot.setRenderer(renderer);
                     //������Ⱦ����
                     double lowpress = 4.5; 
               double uperpress = 8;   //�趨����Ѫ��ֵ�ķ�Χ
               IntervalMarker inter = new IntervalMarker(lowpress, uperpress);  
               inter.setLabelOffsetType(LengthAdjustmentType.EXPAND); //  ��Χ������������
               inter.setPaint(Color.LIGHT_GRAY);// ���ɫ  
           
               inter.setLabelFont(new Font("SansSerif", 41, 14));  
               inter.setLabelPaint(Color.RED);  
               inter.setLabel("����Ѫ��ֵ��Χ");    //�趨����˵������
               plot.addRangeMarker(inter,Layer.BACKGROUND);  //���mark��ͼ��   BACKGROUNDʹ�����������������ǰ��
 
                     // �����ļ������
                     File fos_jpg = new File("E://asv.jpg ");
                     // ������ĸ������
                     ChartUtilities.saveChartAsJPEG(fos_jpg, chart, // ͳ��ͼ�����
                                   700, // ��
                                   500 // ��
                                   );
 
              } catch (IOException e) {
                     e.printStackTrace();
              }
       }
 
//������
       public static void main(String[] args) {
//              ExtensibilityLineChart my1 = new ExtensibilityLineChart("test111111");
              ChangeabilityLineChart my2 = new ChangeabilityLineChart("test222222");
              try {
//            	  my1.creatDataSet("Junit");
//				  my1.createChart("test111111");
				  my2.creatDataSet("Junit");
				  my2.createChart("test222222");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              System.out.println("end");
       }
 
}