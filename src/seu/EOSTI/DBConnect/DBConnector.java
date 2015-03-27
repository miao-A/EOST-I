package seu.EOSTI.DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
	String user = "root";
	String pwd = "1234";
	String url = "jdbc:mysql://localhost:3306/mysql";
	Connection connect=null;
	
	public DBConnector(){
		try {
			Class.forName("com.mysql.jdbc.Driver"); // ����MYSQL JDBC��������
			// Class.forName("org.gjt.mm.mysql.Driver");
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection(url,user,pwd);
			// ����URLΪ jdbc:mysql//��������ַ/���ݿ���
			// �����2�������ֱ��ǵ�½�û���������
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
	
	public void selcetStatement(){
		
		
		try {
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from help_topic");
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				}			
			} catch (Exception e) {
				// TODO: handle exception
			}
		
	}

}
