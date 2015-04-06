package seu.EOSTI.DBConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExtensibilityConnector extends DBConnector{

private Connection connect = null;
	
	public ExtensibilityConnector(){
		super();
		connect = getConnection();
	}
	
	public ArrayList<String> getpackageName(){
		ArrayList<String> list = new ArrayList<String>();
		try {

			Statement stmt = connect.createStatement();
			String sql = "SELECT packagename FROM eosti.classinfo group by packagename";
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
	
	
	public void extendsibilityUpdateStatement(String packageName,String className,String projectName,String version,String classType ){
		try {
			

			Statement stmt = connect.createStatement();
			String sql = "INSERT INTO eosti.classinfo (`PackageName`, `ClassName`, `ProjectName` , `Version`, `ClassType`) VALUES ('"
					+ packageName
					+"','"
					+className
					+"','"
					+projectName
					+"','"
					+version
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
	public ArrayList<String> packageExtensibilityRatio(String str,String packageName,String projectName,String version){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();
			//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
			
			str = "Select  count(classname) as result FROM eosti.classinfo where packagename = '"
			+packageName+"' and Version = '0.2'";
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
			
			double ratioOfInterface = 100.0*(interfaceNum+abstractNum)/(interfaceNum+abstractNum+concreteNum);
			DecimalFormat df = new DecimalFormat("#.00");
			rStrings.add(df.format(ratioOfInterface));	
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("failed to run extensibility query!");
			}
		return rStrings;
	}
	
	
public ArrayList<String> projectExtensibilityRatio(String str,String projectName,String Version){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();
			//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
			
			str = "Select  count(classname) as result FROM eosti.classinfo where Version = '"
			+Version+"'";
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
			
			double ratioOfInterface = 100.0*(interfaceNum+abstractNum)/(interfaceNum+abstractNum+concreteNum);
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
