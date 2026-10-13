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
    public static List<SanPham> getandsearchSanPhamDangBan(String tuKhoa) {
      List<SanPham> list = new ArrayList<>();
        String sql = "SELECT MaSP, TenSP, LoaiSP, SoLuong, GiaNhap, GiaBan "
                   + "FROM SanPham WHERE SoLuong > 0 AND (MaSP LIKE ?)";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + tuKhoa.trim()+"%";
            ps.setString(1, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new SanPham(
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getString("LoaiSP"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("GiaNhap"),
                        rs.getDouble("GiaBan")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static int laySoLuongTonKho(String maSP) {
        String sql = "SELECT SoLuong FROM SanPham WHERE MaSP = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("SoLuong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static KhachHang timKhachHang(String maKH) {
        String sql = "SELECT MaKH, TenKH, SoDienThoai FROM KhachHang WHERE MaKH = ? OR SoDienThoai = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH.trim());
            ps.setString(2, maKH.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KhachHang(
                        rs.getString("MaKH"),
                        rs.getString("TenKH"),
                        rs.getString("SoDienThoai")
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
        try (Connection conn = MyConnection.getConnection();
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
        try (Connection conn = MyConnection.getConnection();
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
    
    //Lưu 1 dòng thông tin chung vào bảng DonHang
    public static boolean themDonHang(String maDH, String maKH, String maVoucher, double tongTien) {
        String sql = "INSERT INTO DonHang (MaDH, MaKH, MaVoucher, NgayMua, TongTien) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maDH);
            ps.setString(2, maKH);
            ps.setString(3, maVoucher); // Nếu maVoucher là null, JDBC tự động hiểu là lưu NULL vào SQL
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setDouble(5, tongTien);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Lưu 1 món hàng trong giỏ vào bảng ChiTietDonHang
    public static void themChiTietDonHang(String maDH, String maSP, int soLuongMua, double donGia, double thanhTien) {
        String sql = "INSERT INTO ChiTietDonHang (MaDH, MaSP, SoLuongMua, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maDH);
            ps.setString(2, maSP);
            ps.setInt(3, soLuongMua);
            ps.setDouble(4, donGia);
            ps.setDouble(5, thanhTien);
            
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Trừ đi số lượng tồn kho trong bảng SanPham sau khi bán
    public static void truSoLuongTonKho(String maSP, int soLuongBan) {
        String sql = "UPDATE SanPham SET SoLuong = SoLuong - ? WHERE MaSP = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, soLuongBan);
            ps.setString(2, maSP);
            
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<DonHang> layDanhSachDonHang(String tuKhoa) {
        List<model.DonHang> list = new ArrayList<>();
        String sql = "SELECT MaDH, MaKH, MaVoucher, NgayMua, TongTien FROM DonHang "
                   + "WHERE MaDH LIKE ? ORDER BY NgayMua DESC";
        try (Connection conn = MyConnection.getConnection();
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
        List<model.ChiTietDonHang> list = new ArrayList<>();
        String sql = "SELECT ct.MaDH, ct.MaSP, sp.TenSP, ct.SoLuongMua, ct.DonGia, ct.ThanhTien "
                   + "FROM ChiTietDonHang ct "
                   + "INNER JOIN SanPham sp ON ct.MaSP = sp.MaSP "
                   + "WHERE ct.MaDH = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maDH);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new model.ChiTietDonHang(
                        rs.getString("MaDH"),
                        rs.getString("MaSP"),
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
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, new java.sql.Date(tuNgay.getTime()));
            ps.setDate(2, new java.sql.Date(denNgay.getTime()));
            
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

        try (Connection conn = MyConnection.getConnection();
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
