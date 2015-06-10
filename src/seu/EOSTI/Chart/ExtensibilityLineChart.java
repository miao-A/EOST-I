package seu.EOSTI.Chart;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;

import seu.EOSTI.DBConnect.ExtensibilityInfoConnector;
import seu.EOSTI.DBConnect.ProjectConnector;

public class ExtensibilityLineChart extends LineChart {

	public ExtensibilityLineChart(String title){
		super(title);
	}
	
	@Override
	public void creatDataSet(String projectName) {
		// TODO Auto-generated method stub
		ProjectConnector pConnector = new ProjectConnector();
		List<String> versionlist = pConnector.getVersion(projectName);	

		LinkedHashMap<String, HashMap<String, Double>> dataMap = new LinkedHashMap<String, HashMap<String, Double>>();		
		
		for (String version : versionlist){
			HashMap<String, Double> map = new HashMap<String, Double>();
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

	
	

}
