package DAO;

import java.sql.*;
import model.khoSP;
import MyConnection.MyConnection;
import java.util.ArrayList;

public class khoDAO {

    public int insert(khoSP sp) {
        String sql = "INSERT INTO SanPham VALUES (?,?,?,?,?,?)";
        try (Connection c = new MyConnection().getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setString(3, sp.getLoaiSP());
            ps.setInt(4, sp.getSoLuong());
            ps.setDouble(5, sp.getGiaNhap());
            ps.setDouble(6, sp.getGiaBan());

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

    public int update(khoSP sp) {
        String sql = "UPDATE SanPham SET TenSP = ?, LoaiSP = ?, SoLuong = ?, "
                + "GiaNhap = ?, GiaBan = ? WHERE MaSP = ?";
        try (Connection conn = MyConnection.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, sp.getTenSP());
            st.setString(2, sp.getLoaiSP());
            st.setInt(3, sp.getSoLuong());
            st.setDouble(4, sp.getGiaNhap());
            st.setDouble(5, sp.getGiaBan());
            st.setString(6, sp.getMaSP());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int delete(String maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP = ?";
        try (Connection conn = MyConnection.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, maSP);
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

   public ArrayList<khoSP> getAll(String cond) {
        return getAll("", cond); 
   }
    public ArrayList<khoSP> getAll(String loai, String key) {
        ArrayList<khoSP> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE (MaSP LIKE ? OR TenSP LIKE ?) AND (? = '' OR LoaiSP = ?)";

        try (Connection c = new MyConnection().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            String searchPattern = "%" + (key == null ? "" : key.trim()) + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, loai == null ? "" : loai.trim());
            ps.setString(4, loai == null ? "" : loai.trim());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String ma = rs.getString("MaSP");
                    String ten = rs.getString("TenSP");
                    String l = rs.getString("LoaiSP");
                    int soLuong = rs.getInt("SoLuong");
                    double giaNhap = rs.getDouble("GiaNhap");
                    double giaBan = rs.getDouble("GiaBan");

                    list.add(new khoSP(ma, ten, l, soLuong, giaNhap, giaBan));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getAllLoai() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT LoaiSP FROM SanPham ORDER BY LoaiSP";
        try (Connection c = new MyConnection().getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String loai = rs.getString("LoaiSP");
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
