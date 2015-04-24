package seu.EOSTI.DBConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExtensibilityConnector extends DBConnector{

private Connection connect = null;
private String projectNameString;
private String versionString;
	
	public ExtensibilityConnector(String projectName, String version){
		super();
		connect = getConnection();
		this.projectNameString = projectName;
		this.versionString = version;
	}
	
	public ArrayList<String> getpackageName(){
		ArrayList<String> list = new ArrayList<String>();
		try {

			Statement stmt = connect.createStatement();
			String sql = "SELECT pkgname FROM eosti.classinfo where ProjName = '"
					+ projectNameString 
					+ "' and verID = '"
					+ versionString 
					+"' group by pkgName";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("pkgName"));
//				System.out.println(rs.getString("pkgname"));
				}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("extensibility Connector error");
		}
		
		return list; 

	}
	
	
	public void extendsibilityUpdateStatement(String packageName,String className,String classType ){
		try {
			

			Statement stmt = connect.createStatement();
			String sql = "INSERT INTO eosti.classinfo (`pkgName`, `ClassName`, `ProjName` , `VerID`, `ClassType`) VALUES ('"
					+ packageName
					+"','"
					+className
					+"','"
					+projectNameString
					+"','"
					+versionString
					+"','"
					+classType
					+"')";
			stmt.executeUpdate(sql);
			
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("insert failed!");
			}
		
	
	}
	

///将来改造为接受外部分析输入来源，目前内部用字符串替代了选择	
	public ArrayList<String> packageExtensibilityRatio(String packageName){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();
			//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
			
			String str = "Select  count(classname) as result FROM eosti.classinfo where pkgname = '"
			+ packageName 
			+ "' and VerID = '" 
			+ versionString + "' and projName = '"
			+projectNameString +"'";
			
			String concretestr = str +" and classtype = 'concrete'";
			String abstractstr = str +" and classtype = 'abstract'";
			String interfacestr = str +" and classtype = 'interface'";
			
			rStrings.add(packageName);
			
			ResultSet rs ;
			int concreteNum=0;
			int abstractNum=0;
			int interfaceNum=0;
			int totalNum = 0;
			
			rs= stmt.executeQuery(concretestr);			
			while (rs.next()) {
				concreteNum = rs.getInt("result");
				rStrings.add(concreteNum+"");
				
			}
			
			rs= stmt.executeQuery(abstractstr);
			while (rs.next()) {
				abstractNum = rs.getInt("result");
				rStrings.add(abstractNum+"");			
			}
			
			rs= stmt.executeQuery(interfacestr);
			while (rs.next()) {
				interfaceNum = rs.getInt("result");
				rStrings.add(interfaceNum+"");	
				
			}
			
			totalNum = interfaceNum + abstractNum + concreteNum;
			rStrings.add(totalNum+"");
			
			double ratioOfInterface = 100.0*(interfaceNum)/(interfaceNum+abstractNum+concreteNum);
			DecimalFormat df = new DecimalFormat("#.00");
			rStrings.add(df.format(ratioOfInterface));	
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("failed to run extensibility query!");
			}
		return rStrings;
	}
	
	
public ArrayList<String> projectExtensibilityRatio(){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();
			//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
			
			String str = "Select  count(classname) as result FROM eosti.classinfo where VerID = '"
			+ versionString + "' and projName = '"
					+projectNameString +"'";
			String concretestr = str +" and classtype = 'concrete'";
			String abstractstr = str +" and classtype = 'abstract'";
			String interfacestr = str +" and classtype = 'interface'";
			
			rStrings.add("project");
			
			ResultSet rs ;
			int concreteNum=0;
			int abstractNum=0;
			int interfaceNum=0;
			int totalNum = 0;
			
			rs= stmt.executeQuery(concretestr);			
			while (rs.next()) {
				concreteNum = rs.getInt("result");
				rStrings.add(concreteNum+"");
				
			}
			
			
			rs= stmt.executeQuery(abstractstr);
			while (rs.next()) {
				abstractNum = rs.getInt("result");
				rStrings.add(abstractNum+"");
				
			}
			
			
			rs= stmt.executeQuery(interfacestr);
			while (rs.next()) {
				interfaceNum = rs.getInt("result");
				rStrings.add(interfaceNum+"");	
				
			}
			
			totalNum = interfaceNum + abstractNum + concreteNum;
			rStrings.add(totalNum+"");
			
			double ratioOfInterface = 100.0*(interfaceNum)/(interfaceNum+abstractNum+concreteNum);
			DecimalFormat df = new DecimalFormat("#.00");
			rStrings.add(df.format(ratioOfInterface));	
			

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("failed to run extensibility query!");
			}
		return rStrings;
	}
	
	
	
	public ArrayList<String> selcetStatement(String str,String selcetString){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(str);
			while (rs.next()) {
				rStrings.add(rs.getString(selcetString));
				}			
			} catch (Exception e) {
				// TODO: handle exception
			}
		return rStrings;
	}
}
