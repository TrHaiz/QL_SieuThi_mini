package DAO;

import java.sql.*;
import model.SanPham;
import MyConnection.MyConnection;
import java.util.ArrayList;

public class sanphamDAO {

    public int insert(SanPham sp) {
        // Fix: Đã thêm đủ 7 dấu ? và gọi đích danh cột để khớp 100% với CSDL
        String sql = "INSERT INTO SanPham (MaSP, TenSP, MaLoaiSP, DV, GiaNhap, GiaBan, Trangthai) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            // LƯU Ý: Biến này trên Model tên là TenLoaiSP, nhưng khi INSERT vào CSDL ta phải truyền Mã Loại (VD: LSP01)
            ps.setString(3, sp.getTenLoaiSP()); 
            ps.setString(4, sp.getDV());
            ps.setDouble(5, sp.getGiaNhap());
            ps.setDouble(6, sp.getGiaBan());
            ps.setString(7, sp.getTrangThai());

            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getErrorCode() == 2627) { // Lỗi trùng khóa chính (Mã SP đã tồn tại)
                return -2;
            }
        }
        return -1;
    }

    public int update(SanPham sp) {
        // Fix: Đã xóa dấu phẩy thừa trước chữ WHERE gây lỗi cú pháp
        String sql = "UPDATE SanPham SET TenSP = ?, MaLoaiSP = ?, DV = ?, "
                   + "GiaNhap = ?, GiaBan = ?, Trangthai = ? WHERE MaSP = ?";
                   
        try (Connection conn = new MyConnection().getConnection(); 
             PreparedStatement st = conn.prepareStatement(sql)) {
             
            st.setString(1, sp.getTenSP());
            st.setString(2, sp.getTenLoaiSP()); // Tương tự Insert, chỗ này truyền Mã Loại
            st.setString(3, sp.getDV());
            st.setDouble(4, sp.getGiaNhap());
            st.setDouble(5, sp.getGiaBan());
            st.setString(6, sp.getTrangThai());
            st.setString(7, sp.getMaSP());
            
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int delete(String maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP = ?";
        try (Connection conn = new MyConnection().getConnection(); 
             PreparedStatement st = conn.prepareStatement(sql)) {
             
            st.setString(1, maSP);
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public ArrayList<SanPham> getAll(String cond) {
        return getAll("", cond); 
    }

    public ArrayList<SanPham> getAll(String loai, String key) {
        ArrayList<SanPham> list = new ArrayList<>();
        // Truy vấn nối bảng để lấy được Tên Loại hiển thị lên giao diện
        String sql = "SELECT sp.*, lsp.TenLoaiSP FROM SanPham sp "
                   + "INNER JOIN LoaiSanPham lsp ON sp.MaLoaiSP = lsp.MaLoaiSP "
                   + "WHERE (sp.MaSP LIKE ? OR sp.TenSP LIKE ?) AND (? = '' OR sp.MaLoaiSP = ?)";

        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + (key == null ? "" : key.trim()) + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, loai == null ? "" : loai.trim());
            ps.setString(4, loai == null ? "" : loai.trim());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SanPham sp = new SanPham(); // Fix: Dùng constructor rỗng thay vì gán tham số rác
                    sp.setMaSP(rs.getString("MaSP"));
                    sp.setTenSP(rs.getString("TenSP"));
                    sp.setTenLoaiSP(rs.getString("TenLoaiSP")); // Đọc Tên Loại từ bảng JOIN
                    sp.setDV(rs.getString("DV"));
                    sp.setGiaNhap(rs.getDouble("GiaNhap"));
                    sp.setGiaBan(rs.getDouble("GiaBan"));
                    sp.setTrangThai(rs.getString("Trangthai")); 
                    
                    list.add(sp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getAllLoai() {
        ArrayList<String> list = new ArrayList<>();
        // Fix: Tối ưu lại câu lệnh lấy danh sách loại sản phẩm đưa lên ComboBox
        String sql = "SELECT TenLoaiSP FROM LoaiSanPham ORDER BY TenLoaiSP";
        
        try (Connection c = new MyConnection().getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                String loai = rs.getString("TenLoaiSP");
                if (loai != null && !loai.trim().isEmpty()) {
                    list.add(loai);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}