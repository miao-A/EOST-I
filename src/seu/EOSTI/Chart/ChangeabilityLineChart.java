package seu.EOSTI.Chart;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import seu.EOSTI.DBConnect.ChangeabilityInfoConnector;
import seu.EOSTI.DBConnect.ProjectConnector;

public class ChangeabilityLineChart  extends LineChart {

	public ChangeabilityLineChart(String title){
		super(title);
	}
	@Override
	public void creatDataSet(String projectName) {
		// TODO Auto-generated method stub
		ProjectConnector pConnector = new ProjectConnector();
		List<String> versionlist = pConnector.getVersion(projectName);
		
		LinkedHashMap<String, HashMap<String, Double>> dataMap = new LinkedHashMap<String, HashMap<String, Double>>();
		
		
		for (String version : versionlist) {
			ChangeabilityInfoConnector dbConnector = new ChangeabilityInfoConnector(projectName, version);
			HashMap<String, Double> map = new HashMap<String, Double>();
			List<String> pkgNameList = dbConnector.getpackageName();			
			for (String pkgName : pkgNameList) {

				if (pkgName.equals("junit.awtui")) {
					int i=0;
					i=1;
				}
				double ratio = dbConnector.getChangeabilityRatio(pkgName);
				map.put(pkgName, new Double(ratio));
				
			}
			dataMap.put(projectName+version, map);			
		}
		this.setDataSet(dataMap);
	}
}
