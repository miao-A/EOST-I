package seu.EOSTI.DBConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;



public class ChangeabilityInfoConnector extends DBConnector {
	private Connection connect = null;
	private String projectNameString;
	private String versionString;
		
		public ChangeabilityInfoConnector(String projectName, String version){
			super();
			connect = getConnection();
			this.projectNameString = projectName;
			this.versionString = version;
		}
		
		public ArrayList<String> getpackageName(){
			ArrayList<String> list = new ArrayList<String>();
			try {

				Statement stmt = connect.createStatement();
				String sql = "SELECT pkgname FROM eosti.pkgCouplingInfo where ProjName = '"
						+ projectNameString 
						+ "' and verID = '"
						+ versionString 
						+"' group by pkgName";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					list.add(rs.getString("pkgName"));
//					System.out.println(rs.getString("pkgname"));
					}	
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("ChangeabilityInfo Connector error");
			}
			
			return list; 

		}
		
		


		public void setChangeabilityInfo(String packageName){
			

			try {
				Statement stmt = connect.createStatement();

				// / efferent  couplings 被该包依赖的外部包数目
				String cestr = "Select  count(distinct importPkgName) as result FROM eosti.pkgCouplingInfo where pkgName = '"
						+ packageName
						+ "' and verID = '"
						+ versionString
						+ "' and projName = '" + projectNameString + "'";


				ResultSet rs;
				int ce = 0;
				int ca = 0;

				rs = stmt.executeQuery(cestr);
				while (rs.next()) {
					ce = rs.getInt("result");

				}

				

				String castr = "Select  count(distinct pkgName) as result FROM eosti.pkgCouplingInfo where importPkgName = '"
						+ packageName
						+ "' and verID = '"
						+ versionString
						+ "' and projName = '" + projectNameString + "'";


				rs = stmt.executeQuery(castr);
				while (rs.next()) {
					ca = rs.getInt("result");
				}
				

				double changeability = 100.0*ca/(ca+ce);
				DecimalFormat df = new DecimalFormat("0.00");				

				
				String changeSql = "INSERT INTO `eosti`.`changeabilityinfo`  (`pkgName`, `projName`, `verID`, `coupleAfferent`, `coupleEfferent`, `ratio`) VALUES ('"
						+ packageName + "' , '"
						+ projectNameString + "', '"
						+ versionString + "', "
						+ ca + ", "
						+ ce + ", "
						+ changeability + ")";
				
				stmt.executeUpdate(changeSql);

				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("failed to run changeabilityinfo update!");
				}
		}
		
		public double getChangeabilityRatio(String pkgName){
			double ratio=0.0;
			try {
				Statement stmt = connect.createStatement();
				//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
				
				String str = "Select  ratio as result FROM eosti.changeabilityinfo where VerID = '"
						+ versionString + "' and projName = '"
						+projectNameString +"' and pkgName = '"
						+ pkgName +"'";				
				
				ResultSet rs ;
				
				rs= stmt.executeQuery(str);			
				while (rs.next()) {					
					ratio = rs.getDouble("result");
				}
				
				

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("failed to run changeabilityinfo query!");
			}
			return ratio;			
		}
}
