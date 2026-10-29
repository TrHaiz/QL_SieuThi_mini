/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import MyConnection.MyConnection;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;

public class AuthDAO {

    public User login(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ?";
        
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("MatKhau");
                    if (encoder.matches(password, hashedPasswordFromDB)) {
                        return new User(
                            rs.getString("TenDangNhap"),
                            rs.getString("HoTen"),
                            rs.getString("Diachi"),
                            hashedPasswordFromDB,
                            rs.getDate("ngaysinh")
                        );
                    }
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, HoTen, Diachi, ngaysinh) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Mã hóa mật khẩu trước khi lưu vào Database
            String hashedPassword = encoder.encode(password);
            
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, ten);
            ps.setString(4, diachi);
            
            if (ngaysinh != null) {
                ps.setDate(5, new java.sql.Date(ngaysinh.getTime()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return new User(username, ten, diachi, hashedPassword, ngaysinh);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
    
