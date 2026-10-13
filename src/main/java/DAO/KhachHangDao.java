
package DAO;

import MyConnection.MyConnection;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import model.KhachHang;

public class KhachHangDao {
    //lấy tất cả
    public ArrayList<KhachHang> getAll() {
        ArrayList<KhachHang> list = new ArrayList<>();

        String sql = "SELECT * FROM KhachHang";

        try(Connection conn=new MyConnection().getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)) {
            ResultSet rs= ps.executeQuery();
            while(rs.next()) {
              KhachHang kh = new KhachHang(
                    rs.getString("MaKH"),
                    rs.getString("TenKH"),
                    rs.getString("GioiTinh"),
                    rs.getDate("NgaySinh"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email")
                );
              list.add(kh);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    
    //lấy theo mã hoặc tên
    public ArrayList<KhachHang> getAll(String keyword) {
        ArrayList<KhachHang> list = new ArrayList<>();
    String sql = "SELECT * FROM KhachHang";
    
    if (keyword != null && !keyword.trim().isEmpty()) {
        sql = sql + " WHERE MaKH LIKE ? OR TenKH LIKE ? OR SoDienThoai LIKE ?";
    }

    try (Connection conn = new MyConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        if (keyword != null && !keyword.trim().isEmpty()) {
            String key = "%" + keyword.trim() + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                    rs.getString("MaKH"),
                    rs.getString("TenKH"),
                    rs.getString("GioiTinh"),
                    rs.getDate("NgaySinh"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email")
                );
                list.add(kh);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
    
    }
    
    public int insert(KhachHang kh) {
        // Kiểm tra điều kiện đầu vào
    if (kh == null) {
        return -1;
    }
    if (kh.getMaKH() == null || kh.getMaKH().trim().isEmpty()) {
        return -1;
    }
    if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()) {
        return -1;
    }

    String sql = "INSERT INTO KhachHang (MaKH, TenKH, GioiTinh, NgaySinh, SoDienThoai, Email) VALUES (?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = new MyConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, kh.getMaKH().trim());
        ps.setString(2, kh.getTenKH().trim());
        ps.setString(3, kh.getGioiTinh());
        ps.setDate(4, new java.sql.Date(kh.getNgaySinh().getTime())); 
        ps.setString(5, kh.getSoDienThoai());
        // Xử lý Email có thể bỏ trống (null hoặc chuỗi rỗng)
        if (kh.getEmail() == null || kh.getEmail().trim().isEmpty()) {
            ps.setNull(6, Types.VARCHAR);
        } else {
            ps.setString(6, kh.getEmail().trim());
        }

        return ps.executeUpdate();
        
    } catch (SQLException ex) {
        if(ex.getErrorCode()==2627 || ex.getErrorCode()== 2601) 
                return -2;
            ex.printStackTrace();
            return -1;
    } 
}
    
    public int update(KhachHang kh) {
    if (kh == null)
        return -1;
    if (kh.getMaKH() == null || kh.getMaKH().trim().isEmpty())
        return -1;
    if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty())
        return -1;
    if (kh.getNgaySinh() == null)
        return -1;

    String sql = "UPDATE KhachHang SET TenKH = ?, GioiTinh = ?, NgaySinh = ?, SoDienThoai = ?, Email = ? WHERE MaKH = ?";
    
    try (Connection conn =new MyConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, kh.getTenKH().trim());
        ps.setString(2, kh.getGioiTinh());
        ps.setDate(3, new java.sql.Date(kh.getNgaySinh().getTime()));
        ps.setString(4, kh.getSoDienThoai());
        
        if (kh.getEmail() == null || kh.getEmail().trim().isEmpty()) {
            ps.setNull(5, Types.VARCHAR);
        } else {
            ps.setString(5, kh.getEmail().trim());
        }
        
        ps.setString(6, kh.getMaKH().trim());

        return ps.executeUpdate();
        
    } catch (SQLException ex) {
        if(ex.getErrorCode()==2627 || ex.getErrorCode()== 2601) 
                return -2;
            ex.printStackTrace();
            return -1;
    }
}
    
    public int delete(String maKH) {
    if (maKH == null || maKH.trim().isEmpty()) {
        return -1;
    }
    
    String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
    
    try (Connection conn =new MyConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, maKH.trim());
        return ps.executeUpdate();
        
    } catch (SQLException ex) {
       if(ex.getErrorCode()==2627 || ex.getErrorCode()== 2601) 
                return -2;
            ex.printStackTrace();
            return -1;
    }
}
    
    
}
    

