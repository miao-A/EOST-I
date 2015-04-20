package seu.EOSTI.DBConnect;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChangeabilityConnector extends DBConnector{

	private String projectName;
	private String version;
	
	private Connection connect = null;
	
	public ChangeabilityConnector(){
		super();
/*		this.projectName = projectName;
		this.version = version;*/
		connect = getConnection();		
	}

	public void importNameUpatedate(String packageName, String importName,
			String projectName, String version) {
		try {

			Statement stmt = connect.createStatement();
			String sql = "insert into `eosti`.`packageinfo` (`packageName`, `projectName`, `version`, `importPackage`) VALUES ('"
					+ packageName
					+ "','"
					+ projectName
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
			String sql = "SELECT packagename FROM `eosti`.`packageinfo` group by packagename";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("packagename"));
				System.out.println(rs.getString("packagename"));
				}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getpackageName ");
		}
		
		return list; 

	}
	
	//传出耦合计数
	public int packageEfferentCouplingsCount(String packageName,String projectName,String version){
		int ce = 0;
		try {
			Statement stmt = connect.createStatement();

			// / efferent  couplings 被该包依赖的外部包数目
			String cestr = "Select  count(distinct importPackage) as result FROM eosti.packageinfo where packagename = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";

			ResultSet rs;
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				ce = rs.getInt("result");
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
			ce = -1;
		}		
		return ce;
	}
	
	public ArrayList<String> packageEffernetCouplingslist(String packageName,String projectName,String version){

		ArrayList<String> rStrings = new ArrayList<String>();
		
		try {
			ResultSet rs;
			Statement stmt = connect.createStatement();
			String cestr = "Select  distinct importPackage as result FROM eosti.packageinfo where packagename = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		}		
		return rStrings;
	}
	
	//传入耦合计数
	public int packageAfferentCouplingsCount(String packageName,String projectName,String version){
		int ca = 0;
		try {
			Statement stmt = connect.createStatement();

			// / afferent  couplings 该包依赖的外部包数目
			String castr = "Select  count(distinct PackageName) as result FROM eosti.packageinfo where importpackage = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";


			ResultSet rs;
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				ca = rs.getInt("result");
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
			ca = -1;
		}		
		return ca;
	}
	
	
	public ArrayList<String> packageAffernetCouplingslist(String packageName,String projectName,String version){

		ArrayList<String> rStrings = new ArrayList<String>();
		
		try {
			ResultSet rs;
			Statement stmt = connect.createStatement();
			String castr = "Select  distinct packageName as result FROM eosti.packageinfo where importpackage = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		}		
		return rStrings;
	}
	
	
	
	public ArrayList<String> packageChangeabilityInfo(String packageName,String projectName,String version){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();

			// / efferent  couplings 被该包依赖的外部包数目
			String cestr = "Select  count(distinct importPackage) as result FROM eosti.packageinfo where packagename = '"
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
				rStrings.add("ce: " + ce);
			}

			cestr = "Select  distinct importPackage as result FROM eosti.packageinfo where packagename = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}

			String castr = "Select  count(distinct PackageName) as result FROM eosti.packageinfo where importpackage = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";


			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				ca = rs.getInt("result");
				rStrings.add("ca: " + ca);
			}

			castr = "Select  distinct packageName as result FROM eosti.packageinfo where importpackage = '"
					+ packageName
					+ "' and Version = '"
					+ version
					+ "' and projectName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
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
