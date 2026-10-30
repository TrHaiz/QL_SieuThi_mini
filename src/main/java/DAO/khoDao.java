package DAO;

import MyConnection.MyConnection;
import java.sql.*;
import java.util.ArrayList;
import model.khoSP;

public class khoDao {
    public int insert(khoSP sp) {
        String sql = "INSERT INTO TonKho (MaSP, SoLuongKho) VALUES (?, ?)";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSP());
            ps.setInt(2, sp.getSoLuongKho());
            return ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 2627 || e.getErrorCode() == 2601) return -2;
            e.printStackTrace();
            return -1;
        }
    }

    public int update(khoSP sp) {
        String sql = "UPDATE TonKho SET SoLuongKho = ? WHERE MaSP = ?";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sp.getSoLuongKho());
            ps.setString(2, sp.getMaSP());
            int n = ps.executeUpdate();
            if (n > 0) return n;
            // Sản phẩm mới có thể chưa có bản ghi tồn kho.
            try (PreparedStatement them = conn.prepareStatement(
                    "INSERT INTO TonKho (MaSP, SoLuongKho) VALUES (?, ?)")) {
                them.setString(1, sp.getMaSP());
                them.setInt(2, sp.getSoLuongKho());
                return them.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String maSP) {
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM TonKho WHERE MaSP = ?")) {
            ps.setString(1, maSP);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<khoSP> getAll(String cond) { return getAll("", cond); }

    public ArrayList<khoSP> getAll(String loai, String key) {
        ArrayList<khoSP> list = new ArrayList<>();
        // Nhà cung cấp và ngày nhập thuộc cùng phiếu gần nhất; mỗi sản phẩm một dòng.
        String sql = "SELECT sp.MaSP, sp.TenSP, lsp.TenLoaiSP, ncc.tenNCC, "
                + "COALESCE(tk.SoLuongKho, 0) AS TonKho, pn.NgayNhap FROM SanPham sp "
                + "LEFT JOIN LoaiSanPham lsp ON sp.MaLoaiSP = lsp.MaLoaiSP "
                + "LEFT JOIN TonKho tk ON sp.MaSP = tk.MaSP "
                + "LEFT JOIN PhieuNhap pn ON pn.MaPN = ("
                + "SELECT TOP 1 pn2.MaPN FROM PhieuNhap pn2 "
                + "INNER JOIN ChiTietPhieuNhap ct ON pn2.MaPN = ct.MaPN "
                + "WHERE ct.MaSP = sp.MaSP ORDER BY pn2.NgayNhap DESC, pn2.MaPN DESC) "
                + "LEFT JOIN NhaCungCap ncc ON pn.MaNCC = ncc.MaNCC "
                + "WHERE (sp.MaSP LIKE ? OR sp.TenSP LIKE ?) "
                + "AND (? = '' OR lsp.TenLoaiSP = ?) ORDER BY sp.MaSP";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String keyword = "%" + (key == null ? "" : key.trim()) + "%";
            String tenLoai = loai == null ? "" : loai.trim();
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, tenLoai);
            ps.setString(4, tenLoai);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    khoSP sp = new khoSP();
                    sp.setMaSP(rs.getString("MaSP"));
                    sp.setTenSP(rs.getString("TenSP"));
                    sp.setTenLoaiSP(rs.getString("TenLoaiSP"));
                    sp.setTenNCC(rs.getString("tenNCC"));
                    sp.setTonKho(rs.getInt("TonKho"));
                    sp.setNgayNhap(rs.getDate("NgayNhap"));
                    list.add(sp);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public khoSP getByMaSP(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) return null;
        for (khoSP sp : getAll("", maSP)) {
            if (maSP.trim().equalsIgnoreCase(sp.getMaSP())) return sp;
        }
        return null;
    }

    public ArrayList<String> getAllLoai() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT TenLoaiSP FROM LoaiSanPham ORDER BY TenLoaiSP";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rs.getString("TenLoaiSP"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
