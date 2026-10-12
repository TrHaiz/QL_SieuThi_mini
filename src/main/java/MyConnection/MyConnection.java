/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyConnection;
import java.sql.*;
/**
 *
 * @author GIAKHANHPC
 */
public class MyConnection {   private static final String SERVER="localhost";
    private static final String DATABASE="QuanLyBanHangDB";
    private static final String USER="sa";
    private static final String PASSWORD="Truonghai123";
        private static final String PORT="64186";
   private static final String URL = "jdbc:sqlserver://" + SERVER + ":" + PORT + 
        ";databaseName=" + DATABASE + 
        ";encrypt=true;trustServerCertificate=true;";
    
    public Connection getConnection()
    {
        Connection c=null;
        try {
            c=DriverManager.getConnection(URL, USER, PASSWORD);
            
        }   
        catch (SQLException e) {e.printStackTrace();}
        return c;
    }
    public void closeConnection(Connection c)
    {
        try {
            if (c!=null) c.close();
            System.out.println("xoa thanh cong");
        }
        catch (SQLException e) {e.printStackTrace();}
    }
}