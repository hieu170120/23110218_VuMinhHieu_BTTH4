package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
	 private final String host = "DESKTOP-HPRPTG8";   // Tên máy
	    private final String port = "1433";              // Port SQL Server (đã cấu hình cố định)
	    private final String dbName = "LTW";           // Tên CSDL
	    private final String user = "sa";                // User SQL Server
	    private final String password = "123456";        // Mật khẩu

	    // Tạo kết nối JDBC
	    public Connection getConnection() throws ClassNotFoundException, SQLException {
	        String url = "jdbc:sqlserver://" + host + ":" + port 
	                   + ";databaseName=" + dbName 
	                   + ";encrypt=false";   // Bỏ SSL để tránh lỗi

	        // Nạp driver
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

	        // Trả về kết nối
	        return DriverManager.getConnection(url, user, password);
	    }
}
