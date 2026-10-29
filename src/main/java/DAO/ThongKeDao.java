package DAO;

import MyConnection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import model.SanPhamBanChay;
import model.ThongKeLoaiSPNgay;

public class ThongKeDao {

    public static Map<String, String> layDanhSachLoaiSanPham() {
        Map<String, String> mapLoai = new LinkedHashMap<>();
        String sql = "SELECT MaLoaiSP, TenLoaiSP FROM LoaiSanPham ORDER BY MaLoaiSP";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                mapLoai.put(rs.getString("MaLoaiSP"), rs.getString("TenLoaiSP"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapLoai;
    }

    public static ArrayList<ThongKeLoaiSPNgay> layThongKeSoLuongTheoLoaiVaNgay(LocalDate startDate, LocalDate endDateExclusive) {
        ArrayList<ThongKeLoaiSPNgay> list = new ArrayList<>();
        String sql = "SELECT "
                   + "    CAST(dh.NgayMua AS DATE) AS NgayBan, "
                   + "    COALESCE(lsp.MaLoaiSP, 'KHAC') AS MaLoaiSP, "
                   + "    COALESCE(lsp.TenLoaiSP, N'Khác') AS TenLoaiSP, "
                   + "    SUM(ct.SoLuongMua) AS TongSoLuongBan "
                   + "FROM DonHang dh "
                   + "INNER JOIN ChiTietDonHang ct ON dh.MaDH = ct.MaDH "
                   + "INNER JOIN SanPham sp ON ct.MaSP = sp.MaSP "
                   + "LEFT JOIN LoaiSanPham lsp ON sp.MaLoaiSP = lsp.MaLoaiSP "
                   + "WHERE dh.NgayMua >= ? AND dh.NgayMua < ? "
                   + "GROUP BY CAST(dh.NgayMua AS DATE), COALESCE(lsp.MaLoaiSP, 'KHAC'), COALESCE(lsp.TenLoaiSP, N'Khác') "
                   + "ORDER BY NgayBan, MaLoaiSP";

        LocalDateTime startLdt = startDate.atStartOfDay();
        LocalDateTime endLdt = endDateExclusive.atStartOfDay();

        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(startLdt));
            ps.setTimestamp(2, Timestamp.valueOf(endLdt));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    java.sql.Date d = rs.getDate("NgayBan");
                    LocalDate ngayBan = (d != null) ? d.toLocalDate() : null;
                    String maLoai = rs.getString("MaLoaiSP");
                    String tenLoai = rs.getString("TenLoaiSP");
                    int tongSL = rs.getInt("TongSoLuongBan");

                    list.add(new ThongKeLoaiSPNgay(ngayBan, maLoai, tenLoai, tongSL));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static SanPhamBanChay laySanPhamBanChayNhat(LocalDate startDate, LocalDate endDateExclusive) {
        String sql = "SELECT TOP 1 "
                   + "    sp.MaSP, "
                   + "    sp.TenSP, "
                   + "    COALESCE(lsp.TenLoaiSP, N'Khác') AS TenLoaiSP, "
                   + "    COALESCE(ncc.tenNCC, N'N/A') AS TenNCC, "
                   + "    SUM(ct.SoLuongMua) AS TongSoLuongBan, "
                   + "    SUM(ct.ThanhTien) AS TongDoanhThu "
                   + "FROM DonHang dh "
                   + "INNER JOIN ChiTietDonHang ct ON dh.MaDH = ct.MaDH "
                   + "INNER JOIN SanPham sp ON ct.MaSP = sp.MaSP "
                   + "LEFT JOIN LoaiSanPham lsp ON sp.MaLoaiSP = lsp.MaLoaiSP "
                   + "LEFT JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC "
                   + "WHERE dh.NgayMua >= ? AND dh.NgayMua < ? "
                   + "GROUP BY sp.MaSP, sp.TenSP, COALESCE(lsp.TenLoaiSP, N'Khác'), COALESCE(ncc.tenNCC, N'N/A') "
                   + "ORDER BY SUM(ct.SoLuongMua) DESC";

        LocalDateTime startLdt = startDate.atStartOfDay();
        LocalDateTime endLdt = endDateExclusive.atStartOfDay();

        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(startLdt));
            ps.setTimestamp(2, Timestamp.valueOf(endLdt));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String maSP = rs.getString("MaSP");
                    String tenSP = rs.getString("TenSP");
                    String loaiSP = rs.getString("TenLoaiSP");
                    String tenNCC = rs.getString("TenNCC");
                    int tongSL = rs.getInt("TongSoLuongBan");
                    double tongDoanhThu = rs.getDouble("TongDoanhThu");
                    String donViTinh = "Cái / Hộp";

                    return new SanPhamBanChay(maSP, tenSP, loaiSP, tenNCC, tongSL, donViTinh, tongDoanhThu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double layTongDoanhThuTuan(LocalDate startDate, LocalDate endDateExclusive) {
        String sql = "SELECT COALESCE(SUM(TongTien), 0) AS TongDoanhThu "
                   + "FROM DonHang "
                   + "WHERE NgayMua >= ? AND NgayMua < ?";

        LocalDateTime startLdt = startDate.atStartOfDay();
        LocalDateTime endLdt = endDateExclusive.atStartOfDay();

        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(startLdt));
            ps.setTimestamp(2, Timestamp.valueOf(endLdt));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("TongDoanhThu");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
