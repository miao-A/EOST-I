package seu.EOSTI.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seu.EOSTI.DBConnect.ChangeabilityInfoConnector;
import seu.EOSTI.DBConnect.ExtensibilityInfoConnector;
import seu.EOSTI.DBConnect.ProjectConnector;

public class ChangeabilityBarChart extends BarChart {

	public ChangeabilityBarChart(String title){
		super(title);
	}
	@Override
	public void creatDataSet(String projectName) {
		// TODO Auto-generated method stub
		ProjectConnector pConnector = new ProjectConnector();
		List<String> versionlist = pConnector.getVersion(projectName);
		
		Map<String, HashMap<String, Double>> dataMap = new HashMap<String, HashMap<String, Double>>();
		HashMap<String, Double> map = new HashMap<String, Double>();
		
		for (String version : versionlist) {
			ChangeabilityInfoConnector dbConnector = new ChangeabilityInfoConnector(projectName, version);
			List<String> pkgNameList = dbConnector.getpackageName();
			for (String pkgName : pkgNameList) {
				double ratio = dbConnector.getChangeabilityRatio(pkgName);
				map.put(pkgName, new Double(ratio));
			}
			dataMap.put(projectName+version, map);			
		}
		this.setDataSet(dataMap);
	}

}
