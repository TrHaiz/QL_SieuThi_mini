
package DAO;

import MyConnection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import model.NCC;


public class NCCDao {
    //lấy tất cả
    public ArrayList<NCC> getAll() {
        ArrayList<NCC> list = new ArrayList<>();

        String sql = "SELECT * FROM NhaCungCap";

        try(Connection conn=new MyConnection().getConnection(); //kết nối cơ sở dữ liệu
            PreparedStatement ps=conn.prepareStatement(sql)) { //khởi tạo ps để thực thi câu lệnh
            ResultSet rs= ps.executeQuery();
            while(rs.next())  { //duyệt từng đòng dữ liệu
              NCC cc = new NCC(
                    rs.getString("MaNCC"),
                    rs.getString("tenNCC"),
                    rs.getString("diachi"),
                    rs.getString("email"),
                    rs.getString("sdt")
                );
              list.add(cc);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    //lấy theo mã hoặc tên
    public ArrayList<NCC> getAll(String keyword) {
        ArrayList<NCC> list = new ArrayList<>();
    String sql = "SELECT * FROM NhaCungCap";
    
    if (keyword != null && !keyword.trim().isEmpty()) {
        sql = sql + " WHERE MaNCC LIKE ? OR tenNCC LIKE ? ";
    }

    try (Connection conn = new MyConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        if (keyword != null && !keyword.trim().isEmpty()) {
            String key = "%" + keyword.trim() + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            
        }

        try (ResultSet rs = ps.executeQuery()) {
            while(rs.next())  { //duyệt từng đòng dữ liệu
              NCC cc = new NCC(
                    rs.getString("MaNCC"),
                    rs.getString("tenNCC"),
                    rs.getString("diachi"),
                    rs.getString("email"),
                    rs.getString("sdt")
                );
              list.add(cc);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
    }
    
    public int insert(NCC cc) {
        // Kiểm tra điều kiện đầu vào
    if (cc == null) {
        return -1;
    }
    if (cc.getMaNCC() == null || cc.getMaNCC().trim().isEmpty()) {
        return -1;
    }
    if (cc.getTenNCC() == null || cc.getTenNCC().trim().isEmpty()) {
        return -1;
    }
    if (cc.getDiachi() == null || cc.getDiachi().trim().isEmpty()) 
        return -1;
    String sql = "INSERT INTO NhaCungCap (MaNCC, tenNCC, diachi, email, sdt) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection conn = new MyConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, cc.getMaNCC().trim());
        ps.setString(2, cc.getTenNCC().trim());
        ps.setString(3, cc.getDiachi().trim());
        // Xử lý Email có thể bỏ trống (null hoặc chuỗi rỗng)
        if (cc.getEmail() == null || cc.getEmail().trim().isEmpty()) {
            ps.setNull(4, Types.VARCHAR);
        } else {
            ps.setString(4, cc.getEmail().trim());
        }
        ps.setString(5, cc.getSdt());
        return ps.executeUpdate();
        
    } catch (SQLException ex) {
        if(ex.getErrorCode()==2627 || ex.getErrorCode()== 2601) 
                return -2;
            ex.printStackTrace();
            return -1;
    } 
}
    public int update(NCC cc) {
    if (cc == null)
        return -1;
    if (cc.getMaNCC() == null || cc.getMaNCC().trim().isEmpty())
        return -1;
    if (cc.getTenNCC() == null || cc.getTenNCC().trim().isEmpty())
        return -1;
    if (cc.getDiachi() == null || cc.getDiachi().trim().isEmpty()) 
        return -1;

    String sql = "UPDATE NhaCungCap SET tenNCC = ?, diachi = ?, email = ?, sdt = ? WHERE MaNCC = ?";
    try (Connection conn = new MyConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, cc.getTenNCC().trim());
        ps.setString(2, cc.getDiachi().trim());
        // Xử lý Email có thể bỏ trống (null hoặc chuỗi rỗng)
        if (cc.getEmail() == null || cc.getEmail().trim().isEmpty()) {
            ps.setNull(3, Types.VARCHAR);
        } else {
            ps.setString(3, cc.getEmail().trim());
        }
        ps.setString(4, cc.getSdt());
        ps.setString(5, cc.getMaNCC().trim());
        return ps.executeUpdate();
        
    } catch (SQLException ex) {
        if(ex.getErrorCode()==2627 || ex.getErrorCode()== 2601) 
                return -2;
            ex.printStackTrace();
            return -1;
    } 
    }
    
    public int delete(String MaNCC) {
    if (MaNCC == null || MaNCC.trim().isEmpty()) {
        return -1;
    }
    
    String sql = "DELETE FROM NhaCungCap WHERE MaNCC = ?";
    
    try (Connection conn =new MyConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, MaNCC.trim());
        return ps.executeUpdate();
        
    } catch (SQLException ex) {
       if(ex.getErrorCode()==2627 || ex.getErrorCode()== 2601) 
                return -2;
            ex.printStackTrace();
            return -1;
    }
}
}
