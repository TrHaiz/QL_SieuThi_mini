
package MyConnection;

import java.sql.*;


public class MyConnection {
    private final static String SERVER="localhost";
    private final static String DATABASE="QuanLyBanHangDB";
    private final static String USER="sa";
    private final static String PASSWORD="123";
    private final static String URL="jdbc:sqlserver://"
                                + SERVER + ":1433;"
                                +"databaseName=" +DATABASE+";"
                                +"encrypt=true;trustServerCertificate=true;";
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException ex) {
            System.out.println("khong the ket noi csdl!");
            ex.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        if(MyConnection.getConnection()!=null)
            System.out.println("ket noi thanh cong");
        else
            System.out.println("that bai!");
    }
}
