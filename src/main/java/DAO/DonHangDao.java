/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.SanPham;
import model.KhachHang;
import model.Vouncher;
import MyConnection.MyConnection;
import model.ChiTietDonHang;
import model.DonHang;
/**
 *
 * @author Long
 */
public class DonHangDao {
    public static List<Object[]> getandsearchSanPhamDangBan(String tuKhoa) {
    List<Object[]> dsSanPham = new ArrayList<>();

    String sql = "SELECT sp.MaSP, sp.TenSP, lsp.TenLoaiSP, k.SoLuongTrenKe, sp.GiaBan " +
                 "FROM SanPham sp " +
                 "INNER JOIN KeHang k ON sp.MaSP = k.MaSP " +
                 "INNER JOIN LoaiSanPham lsp ON sp.MaLoaiSP = lsp.MaLoaiSP " +
                 "WHERE k.SoLuongTrenKe > 0 " +
                 "AND sp.Trangthai = N'Đang kinh doanh' " +
                 "AND (sp.MaSP LIKE ? OR sp.TenSP LIKE ?)";
                 
    try (Connection conn = new MyConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        String searchPattern = "%" + tuKhoa + "%";
        ps.setString(1, searchPattern);
        ps.setString(2, searchPattern);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[]{
                    rs.getString("MaSP"),
                    rs.getString("TenSP"),
                    rs.getString("TenLoaiSP"),
                    rs.getInt("SoLuongTrenKe"), 
                    rs.getLong("GiaBan")
                };
                dsSanPham.add(row);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    return dsSanPham;
}
    
    public static int laySoLuongTrenKe(String maSP) {
        String sql = "SELECT SoLuongTrenKe FROM KeHang WHERE MaSP = ?";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("SoLuongTrenKe");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static KhachHang timKhachHang(String maKH) {
        String sql = "SELECT * FROM KhachHang WHERE MaKH = ? OR SoDienThoai = ?";
        try (Connection conn =new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH.trim());
            ps.setString(2, maKH.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KhachHang(
                    rs.getString("MaKH"),
                    rs.getString("TenKH"),
                    rs.getString("GioiTinh"),
                    rs.getDate("NgaySinh"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email")
                );

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Vouncher timVouncher(String maVouncher) {
        String sql = "SELECT * FROM Voucher WHERE MaVoucher = ?";
        try (Connection conn =new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVouncher.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Vouncher(
                        rs.getString("MaVoucher"),
                        rs.getString("TenVoucher"),
                        rs.getFloat("PhanTramGiam"),
                        rs.getDouble("DieuKienGiaTu"),
                        rs.getDouble("DieuKienGiaDen"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String taoMaDonHangMoi() {
        String sql = "SELECT TOP 1 MaDH FROM DonHang ORDER BY MaDH DESC";
        try (Connection conn =new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastMaDH = rs.getString("MaDH"); // VD: "DH01"
                int num = Integer.parseInt(lastMaDH.replaceAll("[^0-9]", "")) + 1;
                return String.format("DH%02d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "DH01";
    }
    
    public static boolean thanhToanGiaoDich(String maHD, String maKH, String maVoucher, long tongTien, javax.swing.JTable tblGioHang) {
        String sqlDonHang = "INSERT INTO DonHang (MaDH, MaKH, MaVoucher, NgayMua, TongTien) VALUES (?, ?, ?, ?, ?)";

        // Đã xóa cột MaLoaiSP khỏi câu lệnh INSERT để chuẩn hóa CSDL
        String sqlChiTiet = "INSERT INTO ChiTietDonHang (MaDH, MaSP, SoLuongMua, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";

        // Trừ số lượng thẳng vào bảng KeHang thay vì SanPham
        String sqlTruKe   = "UPDATE KeHang SET SoLuongTrenKe = SoLuongTrenKe - ? WHERE MaSP = ?"; 

        Connection conn = null;
        try {
            conn = new MyConnection().getConnection();
            conn.setAutoCommit(false); 

            // 1. Lưu Hóa Đơn
            try (PreparedStatement psDH = conn.prepareStatement(sqlDonHang)) {
                psDH.setString(1, maHD);
                psDH.setString(2, maKH);
                psDH.setString(3, maVoucher);
                psDH.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                psDH.setDouble(5, tongTien);
                psDH.executeUpdate();
            }

            // 2. Lưu Chi Tiết và Trừ Kệ Hàng
            try (PreparedStatement psCT = conn.prepareStatement(sqlChiTiet);
                 PreparedStatement psKe = conn.prepareStatement(sqlTruKe)) {

                for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                    String maSP = tblGioHang.getValueAt(i, 0).toString();
                    int slMua = Integer.parseInt(tblGioHang.getValueAt(i, 2).toString());
                    long donGia = Long.parseLong(tblGioHang.getValueAt(i, 3).toString().replaceAll("[^0-9]", ""));
                    long tongTienDong = Long.parseLong(tblGioHang.getValueAt(i, 4).toString().replaceAll("[^0-9]", ""));

                    // Gắn tham số cho bảng Chi Tiết (Chỉ còn 5 tham số)
                    psCT.setString(1, maHD);
                    psCT.setString(2, maSP);     
                    psCT.setInt(3, slMua);
                    psCT.setDouble(4, donGia);
                    psCT.setDouble(5, tongTienDong);
                    psCT.addBatch();

                    // Gắn tham số cho lệnh Trừ Kệ
                    psKe.setInt(1, slMua);
                    psKe.setString(2, maSP);
                    psKe.addBatch();
                }

                psCT.executeBatch();  
                psKe.executeBatch(); 
            }

            conn.commit(); 
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try { 
                    conn.rollback(); 
                } catch (Exception ex) {
                    ex.printStackTrace(); 
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try { 
                    conn.setAutoCommit(true); 
                    conn.close(); 
                } catch (Exception ex) { 
                    ex.printStackTrace(); 
                }
            }
        }
    }
    
    public static List<DonHang> layDanhSachDonHang(String tuKhoa) {
        List<DonHang> list = new ArrayList<>();
        String sql = "SELECT MaDH, MaKH, MaVoucher, NgayMua, TongTien FROM DonHang "
                   + "WHERE MaDH LIKE ? ORDER BY NgayMua DESC";
        try (Connection conn =new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String search = "%" + tuKhoa.trim() + "%";
            ps.setString(1, search);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new model.DonHang(
                        rs.getString("MaDH"),
                        rs.getString("MaKH"),
                        rs.getString("MaVoucher"),
                        rs.getTimestamp("NgayMua"),
                        rs.getDouble("TongTien")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static List<ChiTietDonHang> layChiTietTheoMaDH(String maDH) {
        List<ChiTietDonHang> list = new ArrayList<>();
   
        String sql = "SELECT ct.MaDH, ct.MaSP, sp.MaLoaiSP, sp.TenSP, ct.SoLuongMua, ct.DonGia, ct.ThanhTien "
                   + "FROM ChiTietDonHang ct "
                   + "INNER JOIN SanPham sp ON ct.MaSP = sp.MaSP "
                   + "WHERE ct.MaDH = ?";

        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maDH);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new model.ChiTietDonHang(
                        rs.getString("MaDH"),
                        rs.getString("MaSP"),
                        rs.getString("MaLoaiSP"),
                        rs.getString("TenSP"),
                        rs.getInt("SoLuongMua"),
                        rs.getDouble("DonGia"),
                        rs.getDouble("ThanhTien")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static List<DonHang> locDonHangTheoKhoangNgay(java.util.Date tuNgay, java.util.Date denNgay) {
        List<model.DonHang> list = new ArrayList<>();
        String sql = "SELECT MaDH, MaKH, MaVoucher, NgayMua, TongTien FROM DonHang "
                   + "WHERE CAST(NgayMua AS DATE) BETWEEN ? AND ? ORDER BY NgayMua DESC";
        try (Connection conn =new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, new Date(tuNgay.getTime()));
            ps.setDate(2, new Date(denNgay.getTime()));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new model.DonHang(
                        rs.getString("MaDH"),
                        rs.getString("MaKH"),
                        rs.getString("MaVoucher"),
                        rs.getTimestamp("NgayMua"),
                        rs.getDouble("TongTien")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static List<DonHang> locDonHangTheoComboBox(String luaChon) {
        List<model.DonHang> list = new ArrayList<>();
        String sql = "SELECT MaDH, MaKH, MaVoucher, NgayMua, TongTien FROM DonHang ";
        
        if (luaChon.equals("Hôm nay")) {
            sql += "WHERE CAST(NgayMua AS DATE) = CAST(GETDATE() AS DATE) ";
        } else if (luaChon.equals("Tháng này")) {
            sql += "WHERE MONTH(NgayMua) = MONTH(GETDATE()) AND YEAR(NgayMua) = YEAR(GETDATE()) ";
        }
        sql += "ORDER BY NgayMua DESC";

        try (Connection conn =new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new model.DonHang(
                    rs.getString("MaDH"),
                    rs.getString("MaKH"),
                    rs.getString("MaVoucher"),
                    rs.getTimestamp("NgayMua"),
                    rs.getDouble("TongTien")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
