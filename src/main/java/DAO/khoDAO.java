package DAO;

import java.sql.*;
import model.SanPham;
import MyConnection.MyConnection;
import java.util.ArrayList;

public class khoDAO {

    public int insert(SanPham sp) {
        String sql = "INSERT INTO SanPham VALUES (?,?,?,?,?,?)";
        try (Connection c = new MyConnection().getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setString(3, sp.getMaLoaiSP());
            ps.setString(4, sp.getMaNCC());
            ps.setInt(5, sp.getTonkho());
            ps.setDouble(6, sp.getGia());

            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getErrorCode() == 2627) {
                return -2;
            }
        }
        return -1;
    }

    public int update(SanPham sp) {
        String sql = "UPDATE SanPham SET TenSP = ?, LoaiSP = ?, SoLuong = ?, "
                + "GiaNhap = ?, GiaBan = ? WHERE MaSP = ?";
        try (Connection conn =new MyConnection().getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, sp.getTenSP());
            st.setString(2, sp.getMaLoaiSP());
            st.setString(3, sp.getMaNCC());
            st.setInt(4, sp.getTonkho());
            st.setDouble(5, sp.getGia());
            st.setString(6, sp.getMaSP());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int delete(String maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP = ?";
        try (Connection conn = new MyConnection().getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM SanPham WHERE (MaSP LIKE ? OR TenSP LIKE ?) AND (? = '' OR MaLoaiSP = ?)";

        try (Connection c = new MyConnection().getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            String searchPattern = "%" + (key == null ? "" : key.trim()) + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, loai == null ? "" : loai.trim());
            ps.setString(4, loai == null ? "" : loai.trim());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String ma = rs.getString("MaSP");
                    String ten = rs.getString("TenSP");
                    String l = rs.getString("MaLoaiSP");
                    String ncc = rs.getString("MaNCC");
                    int tonkho = rs.getInt("Tonkho");
                    double gia = rs.getDouble("Gia");

                    list.add(new SanPham(ma, ten, l, ncc, tonkho, gia));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getAllLoai() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT MaLoaiSP FROM SanPham ORDER BY MaLoaiSP";
        try (Connection c = new MyConnection().getConnection(); 
                PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String loai = rs.getString("MaLoaiSP");
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
