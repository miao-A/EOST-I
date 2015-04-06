package seu.EOSTI.DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChangeabilityConnector extends DBConnector{

	private Connection connect = null;
	
	public ChangeabilityConnector(){
		super();
		connect = getConnection();
	}

	public void importNameUpatedate(String packageName, String importName,String className,
			String projectName, String version) {
		try {

			Statement stmt = connect.createStatement();
			String sql = "insert into `eosti`.`class_package_info` (`packageName`, `projectName`,`className`, `version`, `importPackageName`) VALUES ('"
					+ packageName
					+ "','"
					+ projectName
					+ "','"
					+ className
					+ "','"
					+ version
					+ "','" 
					+ importName + "')";

			try {
				stmt.executeUpdate(sql);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("already insert!");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("insert failed!");
		}

	}
	
	
	public ArrayList<String> getpackageName(){
		ArrayList<String> list = new ArrayList<String>();
		try {

			Statement stmt = connect.createStatement();
			String sql = "SELECT packagename FROM eosti.class_package_info group by packagename";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("packagename"));
				System.out.println(rs.getString("packagename"));
				}	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list; 

	}
	

	public ArrayList<String> packageChangeabilityInfo(String packageName,String projectName,String version){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();

			// / couple efferent
			String cestr = "Select  count(distinct importPackageName) as result FROM eosti.class_package_info where packagename = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";

			rStrings.add(packageName);

			ResultSet rs;
			int ce = 0;
			int ca = 0;

			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				ce = rs.getInt("result");
				rStrings.add(ce + "");
			}

			cestr = "Select  distinct importPackageName as result FROM eosti.class_package_info where packagename = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}

			String castr = "Select  count(distinct PackageName) as result FROM eosti.class_package_info where importpackagename = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";


			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				ca = rs.getInt("result");
				rStrings.add(ca + "");
			}

			castr = "Select  distinct packageName as result FROM eosti.class_package_info where importpackagename = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}

/*			double ratioOfInterface = 100.0;
			DecimalFormat df = new DecimalFormat("#.00");
			rStrings.add(df.format(ratioOfInterface));*/

			System.out.println("-------------------------------------------------------------");
			for (String string : rStrings) {
				System.out.println(string);
			}
			System.out.println("-------------------------------------------------------------");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run extensibility query!");
		}
		
		
		return rStrings;
	}
	

}
