
package DAO;
import model.LoaiSP;
import MyConnection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class loaiSPDao {
    public int insert(LoaiSP sp) {
        String sql = "INSERT INTO LoaiSanPham (MaLoaiSP, TenLoaiSP, MoTa) VALUES (?, ?, ?)";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getMaLoaiSP());
            ps.setString(2, sp.getTenLoaiSP());
            ps.setString(3, sp.getMoTa());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getErrorCode() == 2627 || e.getErrorCode() == 2601) {
                return -2;
            }
        }
        return -1;
    }
      public int update(LoaiSP sp) {
        String sql = "UPDATE LoaiSanPham SET TenLoaiSP = ?, MoTa = ? WHERE MaLoaiSP = ?";
        try (Connection conn =new MyConnection().getConnection(); 
            PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, sp.getTenLoaiSP());
            st.setString(2, sp.getMoTa());         
            st.setString(3, sp.getMaLoaiSP());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
          public int delete(String maloaiSP) {
        String sql = "DELETE FROM LoaiSanPham WHERE MaLoaiSP = ?";
        try (Connection conn = new MyConnection().getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, maloaiSP);
            return st.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 547) {
                return -2; // Loại sản phẩm đang được hàng hóa sử dụng.
            }
            ex.printStackTrace();
        }
        return 0;
    }
   public ArrayList<LoaiSP> getAll(String cond) {
        return getAll("", cond); 
   }
    public ArrayList<LoaiSP> getAll(String maloai, String key) {
        ArrayList<LoaiSP> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiSanPham WHERE (MaLoaiSP LIKE ? OR TenLoaiSP LIKE ?) AND (? = '' OR MaLoaiSP = ?) ORDER BY MaLoaiSP";

        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + (key == null ? "" : key.trim()) + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, maloai == null ? "" : maloai.trim());
            ps.setString(4, maloai == null ? "" : maloai.trim());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                LoaiSP l = new LoaiSP();
                l.setMaLoaiSP(rs.getString("MaLoaiSP"));
                l.setTenLoaiSP(rs.getString("TenLoaiSP"));
                l.setMoTa(rs.getString("MoTa")); 
                
                list.add(l);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
