
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

    String sql = "SELECT " +
                 "    sp.MaSP, " +
                 "    sp.TenSP, " +
                 "    lsp.TenLoaiSP, " +
                 "    pn.MaNCC, " +
                 "    tk.SoLuongKho AS TonKho, " +
                 "    pn.NgayNhap " +
                 "FROM SanPham sp " +
                 "LEFT JOIN LoaiSanPham lsp ON sp.MaLoaiSP = lsp.MaLoaiSP " +
                 "LEFT JOIN TonKho tk ON sp.MaSP = tk.MaSP " +
                 "LEFT JOIN ChiTietPhieuNhap ctpn ON sp.MaSP = ctpn.MaSP " +
                 "LEFT JOIN PhieuNhap pn ON ctpn.MaPN = pn.MaPN " +
                 "WHERE (sp.MaSP LIKE ? OR sp.TenSP LIKE ?) " +
                 "AND (? = '' OR lsp.TenLoaiSP = ?)";
     
    try (Connection conn = new MyConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        // 1. Xử lý từ khóa tìm kiếm cho MaSP và TenSP (dấu ?)
        String searchPattern = "%" + (key == null ? "" : key.trim()) + "%";
        ps.setString(1, searchPattern); // Cho sp.MaSP LIKE ?
        ps.setString(2, searchPattern); // Cho sp.TenSP LIKE ?

        // 2. Xử lý điều kiện lọc theo loại sản phẩm (dấu ?)
        String loaiFilter = (loai == null || loai.equals("-- Chọn loại sản phẩm --") || loai.trim().isEmpty()) ? "" : loai.trim();
        ps.setString(3, loaiFilter); // Kiểm tra xem có rỗng không (? = '')
        ps.setString(4, loaiFilter); // So sánh với tên loại (lsp.TenLoaiSP = ?)

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                khoSP sp = new khoSP();
                sp.setMaSP(rs.getString("MaSP"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setTenLoaiSP(rs.getString("TenLoaiSP")); 
                
                // Sửa lại tên cột cho đúng với câu lệnh SELECT (MaNCC và TonKho)
                sp.setMaNCC(rs.getString("MaNCC"));
                sp.setTonKho(rs.getDouble("TonKho")); // Sửa từ "GiaNhap" thành "TonKho"
                
                java.sql.Date sqlDate = rs.getDate("NgayNhap");
                if (sqlDate != null) {
                    sp.setNgayNhap(new java.util.Date(sqlDate.getTime()));
                } else {
                    sp.setNgayNhap(null);
                }
                
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
       String sql = "SELECT DISTINCT lsp.TenLoaiSP " +
                 "FROM TonKho tk " +
                 "JOIN SanPham sp ON tk.MaSP = sp.MaSP " +
                 "JOIN LoaiSanPham lsp ON sp.MaLoaiSP = lsp.MaLoaiSP " +
                 "ORDER BY lsp.TenLoaiSP";
        try (Connection c = new MyConnection().getConnection(); 
                PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
