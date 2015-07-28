package seu.EOSTI.DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnector {
	private String user = "root";
	private String pwd = "1234";
	private String url = "jdbc:mysql://localhost:3306/mysql";
	private Connection connect=null;
	
	public DBConnector(){
		try {
			Class.forName("com.mysql.jdbc.Driver"); // ����MYSQL JDBC��������
			// Class.forName("org.gjt.mm.mysql.Driver");
			//System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection(url,user,pwd);
			// ����URLΪ jdbc:mysql//��������ַ/���ݿ���
			// �����2�������ֱ��ǵ�½�û���������
			//System.out.println("Success connect Mysql server!");

		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
	}
	
	protected Connection getConnection(){
		return connect;
	}
	
	protected void finalize() {

		try {
			connect.close();
			//System.out.println("release connect Mysql server!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	

}
