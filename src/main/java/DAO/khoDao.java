
package DAO;
import model.khoSP;
import MyConnection.MyConnection;
import java.util.ArrayList;
import java.sql.*;
import java.util.Date;

public class khoDao {
        public int insert(khoSP sp) {
        String sql = "INSERT INTO KhoSanPham VALUES (?,?,?,?,?,?)";
        try (Connection conn = new MyConnection().getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setString(3, sp.getTenLoaiSP());
            ps.setString(4, sp.getMaNCC());
            ps.setDouble(5, sp.getTonKho());
            ps.setDate(6, new java.sql.Date(sp.getNgayNhap().getTime()));

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
        String sql = "UPDATE KhoSanPham SET TenSP = ?, MaLoaiSP = ?, MaNCC = ?, "
                + "TonKho = ?, GiaNhap = ?, WHERE MaSP = ?";
        try (Connection conn =new MyConnection().getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, sp.getTenSP());
            st.setString(2, sp.getTenLoaiSP());
            st.setString(3, sp.getMaNCC());
            st.setDouble(4, sp.getTonKho());
            st.setDate(5,  new java.sql.Date(sp.getNgayNhap().getTime()));
            st.setString(6, sp.getMaSP());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    public int delete(String maSP) {
        String sql = "DELETE FROM KhoSanPham WHERE MaLoaiSP = ?";
        try (Connection conn = new MyConnection().getConnection(); 
            PreparedStatement st = conn.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM KhoSanPham WHERE (MaLoaiSP LIKE ? OR TenSP LIKE ?) AND (? = '' OR MaLoaiSP = ?)";

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
                    String mancc = rs.getString("MaNCC");
                    double tonkho = rs.getInt("TonKho");
                    Date ngaynhap = rs.getDate("NgayNhap");
                    String tt=rs.getString("TrangThai");

                    list.add(new khoSP(ma, ten, loai, mancc, tonkho, ngaynhap));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
}
