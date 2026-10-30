/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.*;
import java.util.Date;
import model.SanPham;
import MyConnection.MyConnection;
import java.sql.*;
/**
 *
 * @author Long
 */
public class XuatHangDao {
   public static List<Object[]> layDanhSachSanPhamTrenKe(String tuKhoa, int indexGia, int indexSapXep) {
        List<Object[]> ds = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT sp.MaSP, sp.TenSP, lsp.TenLoaiSP, k.SoLuongTrenKe, sp.DV, sp.GiaBan " +
            "FROM KeHang k " +
            "INNER JOIN SanPham sp ON k.MaSP = sp.MaSP " +
            "INNER JOIN LoaiSanPham lsp ON sp.MaLoaiSP = lsp.MaLoaiSP " +
            "WHERE k.SoLuongTrenKe > 0 AND (sp.MaSP LIKE ? OR sp.TenSP LIKE ?) and sp.Trangthai != N'Ngừng kinh doanh' "
        );

        // 1. Xử lý điều kiện Khoảng Giá
        if (indexGia == 1) {
            sql.append("AND sp.GiaBan < 50000 ");
        } else if (indexGia == 2) {
            sql.append("AND sp.GiaBan >= 50000 AND sp.GiaBan <= 100000 ");
        } else if (indexGia == 3) {
            sql.append("AND sp.GiaBan > 100000 AND sp.GiaBan <= 200000 ");
        } else if (indexGia == 4) {
            sql.append("AND sp.GiaBan > 200000 ");
        }

        // 2. Xử lý điều kiện Sắp Xếp
        if (indexSapXep == 1) {
            sql.append("ORDER BY sp.GiaBan ASC");
        } else if (indexSapXep == 2) {
            sql.append("ORDER BY sp.GiaBan DESC");
        } else {
            sql.append("ORDER BY sp.MaSP ASC");
        }

        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            String search = "%" + tuKhoa.trim() + "%";
            ps.setString(1, search);
            ps.setString(2, search);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(new Object[]{
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getString("TenLoaiSP"),
                        rs.getInt("SoLuongTrenKe"),
                        rs.getString("DV"),
                        rs.getDouble("GiaBan")
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    // 1. Lấy danh sách sản phẩm đang CÓ TRONG KHO để đổ vào ComboBox
    public static List<Object[]> layDanhSachSanPhamTrongKho() {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT sp.MaSP, sp.TenSP, tk.SoLuongKho, sp.GiaBan FROM SanPham sp " +
                     "INNER JOIN TonKho tk ON sp.MaSP = tk.MaSP " +
                     "WHERE tk.SoLuongKho > 0 AND sp.Trangthai != N'Ngừng kinh doanh'";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new Object[]{ 
                    rs.getString("MaSP"), 
                    rs.getString("TenSP"), 
                    rs.getInt("SoLuongKho"),
                    rs.getDouble("GiaBan")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
        return ds;
    }

    // 2. Tạo mã Phiếu Kê tự động
    public static String taoMaPhieuKeMoi() {
        String sql = "SELECT TOP 1 MaPK FROM PhieuKe ORDER BY MaPK DESC";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastMa = rs.getString("MaPK"); 
                int num = Integer.parseInt(lastMa.replaceAll("[^0-9]", "")) + 1;
                return String.format("PK%02d", num);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return "PK01";
    }

    // 3. Lưu phiếu kê, Trừ Tồn Kho, Cộng Kệ Hàng
    public static boolean luuPhieuXuatKe(String maPK, String ghiChu, javax.swing.JTable tbl) {
        String sqlPhieu = "INSERT INTO PhieuKe (MaPK, NgayLenKe, GhiChu) VALUES (?, GETDATE(), ?)";
        String sqlChiTiet = "INSERT INTO ChiTietPhieuKe (MaPK, MaSP, SoLuong) VALUES (?, ?, ?)";
        String sqlTruKho = "UPDATE TonKho SET SoLuongKho = SoLuongKho - ? WHERE MaSP = ?";
        String sqlCapNhatGia = "UPDATE SanPham SET GiaBan = ? WHERE MaSP = ?";
        // Kiểm tra xem hàng đã có trên kệ chưa. Nếu có thì UPDATE cộng thêm, nếu chưa có thì INSERT mới
        String sqlCongKe = "IF EXISTS (SELECT 1 FROM KeHang WHERE MaSP = ?) " +
                           "UPDATE KeHang SET SoLuongTrenKe = SoLuongTrenKe + ? WHERE MaSP = ? " +
                           "ELSE INSERT INTO KeHang (MaSP, SoLuongTrenKe) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = new MyConnection().getConnection();
            conn.setAutoCommit(false); // Bật Transaction

            // Lưu bảng PhieuKe
            try (PreparedStatement psPK = conn.prepareStatement(sqlPhieu)) {
                psPK.setString(1, maPK);
                psPK.setString(2, ghiChu);
                psPK.executeUpdate();
            }

            // Lưu Chi Tiết và xử lý số lượng
            try (PreparedStatement psCT = conn.prepareStatement(sqlChiTiet);
                 PreparedStatement psTru = conn.prepareStatement(sqlTruKho);
                 PreparedStatement psGia = conn.prepareStatement(sqlCapNhatGia);
                 PreparedStatement psCong = conn.prepareStatement(sqlCongKe)) {
                
                for (int i = 0; i < tbl.getRowCount(); i++) {
                    String maSP = tbl.getValueAt(i, 0).toString();
                    int sl = Integer.parseInt(tbl.getValueAt(i, 2).toString());
                    double giaBanMoi = Double.parseDouble(tbl.getValueAt(i, 3).toString());
                    // ChiTietPhieuKe
                    psCT.setString(1, maPK); psCT.setString(2, maSP); psCT.setInt(3, sl); psCT.addBatch();
                    // Trừ Kho
                    psTru.setInt(1, sl); psTru.setString(2, maSP); psTru.addBatch();
                    // Update Giá
                    psGia.setDouble(1, giaBanMoi); 
                    psGia.setString(2, maSP);
                    psGia.addBatch();
                    // Cộng Kệ
                    psCong.setString(1, maSP); psCong.setInt(2, sl); psCong.setString(3, maSP); 
                    psCong.setString(4, maSP); psCong.setInt(5, sl); psCong.addBatch();
                }
                psCT.executeBatch(); psTru.executeBatch(); psGia.executeBatch(); psCong.executeBatch();
            }
            conn.commit(); return true;
        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (Exception ex) {}
        }
    }
    
    // 1. Lấy Danh Sách Phiếu Kê (Bảng bên trái) kèm theo các điều kiện lọc
    public static List<Object[]> layDanhSachPhieuKe(String maPK, Date tuNgay, Date denNgay, String cbbLuaChon) {
        List<Object[]> ds = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT MaPK, NgayLenKe, GhiChu FROM PhieuKe WHERE MaPK LIKE ? ");
        
        if (tuNgay != null && denNgay != null) {
            sql.append("AND CAST(NgayLenKe AS DATE) >= CAST(? AS DATE) AND CAST(NgayLenKe AS DATE) <= CAST(? AS DATE) ");
        } else {
            if (cbbLuaChon.equals("Hôm nay")) {
                sql.append("AND CAST(NgayLenKe AS DATE) = CAST(GETDATE() AS DATE) ");
            } else if (cbbLuaChon.equals("Tháng này")) {
                sql.append("AND MONTH(NgayLenKe) = MONTH(GETDATE()) AND YEAR(NgayLenKe) = YEAR(GETDATE()) ");
            }
        }
        
        sql.append("ORDER BY NgayLenKe DESC");
        
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            ps.setString(1, "%" + maPK + "%");
            
            if (tuNgay != null && denNgay != null) {
                ps.setTimestamp(2, new Timestamp(tuNgay.getTime()));
                ps.setTimestamp(3, new Timestamp(denNgay.getTime()));
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    ds.add(new Object[]{ rs.getString("MaPK"), rs.getTimestamp("NgayLenKe"), rs.getString("GhiChu") });
                }
            }
        } catch(Exception e) { e.printStackTrace(); }
        return ds;
    }
    
    // 2. Lấy chi tiết sản phẩm của 1 phiếu xuất kệ
    public static List<Object[]> layChiTietPhieuKe(String maPK) {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT ct.MaSP, sp.TenSP, lsp.TenLoaiSP, sp.GiaBan, ct.SoLuong " +
                     "FROM ChiTietPhieuKe ct " +
                     "INNER JOIN SanPham sp ON ct.MaSP = sp.MaSP " +
                     "INNER JOIN LoaiSanPham lsp ON sp.MaLoaiSP = lsp.MaLoaiSP " +
                     "WHERE ct.MaPK = ?";
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPK);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(new Object[]{
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getString("TenLoaiSP"),
                        rs.getDouble("GiaBan"),
                        rs.getInt("SoLuong")
                    });
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return ds;
    }
}
