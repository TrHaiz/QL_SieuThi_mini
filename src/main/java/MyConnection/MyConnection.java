
package MyConnection;
import java.sql.*;

public class MyConnection {   
    private static final String SERVER="localhost";
    private static final String DATABASE="QuanLyBanHangDB";
    private static final String USER="sa";
    private static final String PASSWORD="1";
        private static final String PORT="1433";
   private static final String URL = "jdbc:sqlserver://" + SERVER + ":" + PORT + 
        ";databaseName=" + DATABASE + 
        ";encrypt=true;trustServerCertificate=true;";
    
    public static Connection getConnection() {
              try {
            return DriverManager.getConnection(URL,USER,PASSWORD );
        } catch (SQLException e) {
            System.out.println("Không thể kết nối CSDL!");
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        if (MyConnection.getConnection()!=null) {
            System.out.println("thanh cong");
        } else{
            System.out.println("that bai");
        }
    }
    
}
