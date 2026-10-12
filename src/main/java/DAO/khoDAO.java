
package DAO;
import java.sql.*;
import model.khoSP;
import MyConnection.MyConnection;

public class khoDAO {
     public int insert(khoSP sp) {
        String sql = "INSERT INTO SanPham(MaSP, TenSP, LoaiSP, SoLuong, GiaNhap, GiaBan, HinhAnh) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, sp.getMaSP());
            st.setString(2, sp.getTenSP());
            st.setString(3, sp.getLoaiSP());
            st.setInt(4, sp.getSoLuong());
            st.setDouble(5, sp.getGiaNhap());
            st.setDouble(6, sp.getGiaBan());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
       public int update(khoSP sp) {
        String sql = "UPDATE SanPham SET TenSP = ?, LoaiSP = ?, SoLuong = ?, "
                + "GiaNhap = ?, GiaBan = ? WHERE MaSP = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
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
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, maSP);
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
        public ArrayList<khoSP> getAll() {
        return getAll("", "");
    }
}
