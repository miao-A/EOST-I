package seu.EOSTI.DBConnect;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ClassChangeabilityConnector extends DBConnector{

	private Connection connect = null;
	
	public ClassChangeabilityConnector(){
		super();
		connect = getConnection();
	}

	public void importNameUpatedate(String packageName, String className,  String importName,String importclassName,
			String projectName, String version) {
		try {

			Statement stmt = connect.createStatement();
			String sql = "insert into `eosti`.`class_packageinfo` (`packageName`, `className`, `projectName`, `version`, `importPackageName`, `importclassname`) VALUES ('"
					+ packageName
					+ "','"					
					+ className
					+ "','"
					+ projectName
					+ "','"
					+ version
					+ "','" 
					+ importName  
					+ "','"
					+ importclassName + "')";

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
			String sql = "SELECT packagename FROM eosti.classpackage_info group by packagename";
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
	//分析耦合度在class级别
	public ArrayList<String> getClassName(String projectName, String version){
		ArrayList<String> list = new ArrayList<String>();
		try {

			Statement stmt = connect.createStatement();
			String sql = "SELECT distinct packagename, classname FROM eosti.class_packageinfo where version = '"					
					+ version
					+ "' and projectName = '" 
					+ projectName + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String str = rs.getString("packagename") + "$" + rs.getString("classname");
				list.add(str);
				System.out.println(rs.getString("packagename")+"$"+rs.getString("classname"));
				}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getClassName run failed!");
		}
		
		return list;
	}
	
	public ArrayList<String> ClassChangeabilityInfo(String packageName,String className,String projectName,String version){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();

			// / couple efferent
			String cestr = "Select  count(distinct importPackageName, importClassName) as result FROM eosti.class_packageinfo where packagename = '"
					+ packageName
					+ "' and className = '"
					+ className
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" 
					+ projectName + "'";

			rStrings.add(packageName+"$"+className);

			ResultSet rs;
			int ce = 0;
			int ca = 0;

			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				ce = rs.getInt("result");
				rStrings.add("ce: " + ce);
			}

			cestr = "Select  distinct importPackageName, importClassName  FROM eosti.class_packageinfo where packagename = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("importPackageName")+"$" +rs.getString("importClassName");
//				rStrings.add(str);
			}

			String castr = "Select  count(distinct PackageName, className) as result FROM eosti.class_packageinfo where importpackagename = '"
					+ packageName
					+ "' and importClassName = '"
					+ className
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" 
					+ projectName + "'";


			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				ca = rs.getInt("result");
				rStrings.add("ca: " + ca);
			}

			castr = "Select  distinct PackageName, className FROM eosti.class_packageinfo where importpackagename = '"
					+ packageName
					+ "' and importClassName = '"
					+ className
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("PackageName")+"$" +rs.getString("ClassName");
//				rStrings.add(str);
			}

			double changeability = 1.0*ce/(ca+ce);
			DecimalFormat df = new DecimalFormat("0.00");
			rStrings.add("changeability:"+df.format(changeability));

			System.out.println("-------------------------------------------------------------");
			for (String string : rStrings) {
				System.out.println(string);
			}
			System.out.println("-------------------------------------------------------------");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		}	
		
		return rStrings;
	}	

}
