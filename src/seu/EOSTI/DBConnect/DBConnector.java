package seu.EOSTI.DBConnect;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DBConnector {
	String user = "root";
	String pwd = "1234";
	String url = "jdbc:mysql://localhost:3306/mysql";
	Connection connect=null;
	
	public DBConnector(){
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
			// Class.forName("org.gjt.mm.mysql.Driver");
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection(url,user,pwd);
			// 连接URL为 jdbc:mysql//服务器地址/数据库名
			// 后面的2个参数分别是登陆用户名和密码
			System.out.println("Success connect Mysql server!");

		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
	}
	
	protected void finalize() {
		System.out.println("release connect Mysql server!");
		try {
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
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
	
	
	public void extendsibilityUpdateStatement(String packageName,String className,String Version,String ClassType ){
		try {
			

			Statement stmt = connect.createStatement();
			String sql = "INSERT INTO `eosti`.`classinfo` (`PackageName`, `ClassName`, `Version`, `ClassType`) VALUES ('"
					+ packageName
					+"','"
					+className
					+"','"
					+Version
					+"','"
					+ClassType
					+"')";
			stmt.executeUpdate(sql);
			
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("insert failed!");
			}
		
	
	}
	
	public ArrayList<String> packageExtensibilityRatio(String str,String selcetString){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();
			//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
			
			str = "Select  count(classname) as result FROM eosti.classinfo where packagename = '"+selcetString+"'";
			String concretestr = str +"and classtype = 'concrete'";
			String abstractstr = str +"and classtype = 'abstract'";
			String interfacestr = str +"and classtype = 'interface'";
			
			rStrings.add(selcetString);
			
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
