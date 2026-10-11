/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import MyConnection.MyConnection;
import java.sql.*;
import model.User;
/**
 *
 * @author GIAKHANHPC
 */
public class AuthDAO {
   public User login(String username, String password) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
        
        try (Connection conn =new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getString("TenDangNhap"),
                        rs.getString("HoTen"),
                        rs.getString("Diachi"),
                        rs.getString("MatKhau"),
                        rs.getDate("ngaysinh")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

 
    public boolean checkUsernameExists(String username) {
        String sql = "SELECT 1 FROM TaiKhoan WHERE TenDangNhap = ?";
        
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public User register(String username, String ten, String diachi, String password, java.util.Date ngaysinh) {
        String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, HoTen, Diachi, ngaysinh) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, ten);
            ps.setString(4, diachi);
            
            // Chuyển java.util.Date sang java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(ngaysinh.getTime());
            ps.setDate(5, sqlDate);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected>0) {return new User(username, ten, diachi, password, ngaysinh);} 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
