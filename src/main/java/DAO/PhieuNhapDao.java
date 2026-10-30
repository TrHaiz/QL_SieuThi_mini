
package DAO;

import MyConnection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.ChiTietPhieuNhap;
import model.PhieuNhap;


public class PhieuNhapDao {
    // Lấy danh sách phiếu nhập (Hỗ trợ tìm kiếm theo Mã phiếu hoặc Mã NCC)
    public ArrayList<PhieuNhap> getAll(String keyword) {
        ArrayList<PhieuNhap> list = new ArrayList<>();
        String sql = new String("SELECT * FROM PhieuNhap WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql=sql+ " AND (MaPN LIKE ? OR MaNCC LIKE ?)";
        }

        try(Connection conn=new MyConnection().getConnection(); //kết nối cơ sở dữ liệu
            PreparedStatement ps=conn.prepareStatement(sql)) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                String key = "%" + keyword.trim() + "%";
                ps.setString(1, key);
                ps.setString(2, key);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuNhap pn = new PhieuNhap(
                        rs.getString("MaPN"),
                        rs.getDate("NgayNhap"),
                        rs.getString("MaNCC"),
                        rs.getDouble("TongTien")
                    );
                    list.add(pn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy danh sách chi tiết sản phẩm của một phiếu nhập
    public ArrayList<ChiTietPhieuNhap> getChiTietByMaPN(String maPN) {
        ArrayList<ChiTietPhieuNhap> list = new ArrayList<>();
    
    String sql = "SELECT ctpn.MaPN, ctpn.MaSP, sp.TenSP, ctpn.SoLuong, ctpn.DonGia " +
                 "FROM ChiTietPhieuNhap ctpn " +
                 "JOIN SanPham sp ON ctpn.MaSP = sp.MaSP " +
                 "WHERE ctpn.MaPN = ?";

    try (Connection conn = new MyConnection().getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql)) {
         
        ps.setString(1, maPN);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(
                    rs.getString("MaPN"),
                    rs.getString("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("DonGia")
                );
                list.add(ctpn);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
    }
     
    public int insertPhieuNhap(PhieuNhap pn, ArrayList<ChiTietPhieuNhap> listCT) {
     // Kiểm tra điều kiện đầu vào cơ bản
    if (pn == null || pn.getMaPN() == null || pn.getMaPN().trim().isEmpty()) {
        return -1;
    }
    if (listCT == null || listCT.isEmpty()) {
        return -1;
    }

    Connection conn = null;
    try {
        conn = new MyConnection().getConnection(); 
        conn.setAutoCommit(false); // Bật Transaction để đảm bảo tính toàn vẹn dữ liệu, tạm thời lưu dữ liệu 
        //trong bộ nhớ tạm, không ghi thẳng xuống sql tại vì bình thường sql sẽ ghi thẳng vào sql 

        // Thêm thông tin chung vào bảng PhieuNhap
        String sqlPN = "INSERT INTO PhieuNhap (MaPN, NgayNhap, MaNCC, TongTien) VALUES (?, ?, ?, ?)";
        try (PreparedStatement psPN = conn.prepareStatement(sqlPN)) {
            psPN.setString(1, pn.getMaPN().trim());
            psPN.setDate(2, new java.sql.Date(pn.getNgayNhap().getTime()));
            psPN.setString(3, pn.getMaNCC().trim());
            psPN.setDouble(4, pn.getTongTien());
            psPN.executeUpdate(); //thực thi câu lệnh thêm phiếu
        }
        // 2. Thêm danh sách chi tiết vào bảng ChiTietPhieuNhap và Cập nhật cộng dồn TonKho
        String sqlCT = "INSERT INTO ChiTietPhieuNhap (MaPN, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";        
        // Kiểm tra xem sản phẩm đã có trong bảng TonKho chưa để Update hoặc Insert
        String sqlCheckTon = "SELECT SoLuongKho FROM TonKho WHERE MaSP = ?";
        String sqlUpdateTon = "UPDATE TonKho SET SoLuongKho = SoLuongKho + ? WHERE MaSP = ?";
        String sqlInsertTon = "INSERT INTO TonKho (MaSP, SoLuongKho) VALUES (?, ?)";
        String sqlUpdateGiaNhap = "UPDATE SanPham SET GiaNhap = ? WHERE MaSP = ?";

        try (PreparedStatement psCT = conn.prepareStatement(sqlCT); //chuẩn bị các câu lệnh
             PreparedStatement psCheck = conn.prepareStatement(sqlCheckTon);
             PreparedStatement psUpTon = conn.prepareStatement(sqlUpdateTon);
             PreparedStatement psInsTon = conn.prepareStatement(sqlInsertTon);
             PreparedStatement psUpGia = conn.prepareStatement(sqlUpdateGiaNhap)) {

            for (ChiTietPhieuNhap ct : listCT) {
                // Thêm chi tiết phiếu nhập
                psCT.setString(1, pn.getMaPN().trim());
                psCT.setString(2, ct.getMaSP().trim());
                psCT.setInt(3, ct.getSoLuong());
                psCT.setDouble(4, ct.getDonGia());
                psCT.addBatch();

                // Kiểm tra và cộng dồn tồn kho
                psCheck.setString(1, ct.getMaSP().trim());
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) { //nếu có rồi
                        psUpTon.setInt(1, ct.getSoLuong());
                        psUpTon.setString(2, ct.getMaSP().trim());
                        psUpTon.addBatch();
                    } else {
                        psInsTon.setString(1, ct.getMaSP().trim());
                        psInsTon.setInt(2, ct.getSoLuong());
                        psInsTon.addBatch();
                    }
                }
                // ---> THÊM BATCH CẬP NHẬT GIÁ NHẬP CHO SẢN PHẨM <---
                psUpGia.setDouble(1, ct.getDonGia()); // Lấy đơn giá mới nhập
                psUpGia.setString(2, ct.getMaSP().trim());
                psUpGia.addBatch();
            }

            psCT.executeBatch();
            psUpTon.executeBatch();
            psInsTon.executeBatch();
            psUpGia.executeBatch(); //chạy lệnh cập nhật xuống database
        }

        conn.commit(); // Xác nhận toàn bộ giao dịch thành công
        return 1;    

    } catch (SQLException ex) {
        // Rollback lại nếu có lỗi xảy ra trong quá trình thực hiện transaction
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Bắt lỗi trùng khóa chính 
        if (ex.getErrorCode() == 2627 || ex.getErrorCode() == 2601) {
            return -2; 
        }
        
        ex.printStackTrace();
        return -1; 
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    }

    // Cập nhật phiếu nhập
    public int updatePhieuNhap(PhieuNhap pn, ArrayList<ChiTietPhieuNhap> listCT) {
    if (pn == null || pn.getMaPN() == null || pn.getMaPN().trim().isEmpty()) {
        return -1;
    }
    if (listCT == null || listCT.isEmpty()) {
        return -1; 
    }

    String sqlUpdatePN = "UPDATE PhieuNhap SET NgayNhap = ?, MaNCC = ?, TongTien = ? WHERE MaPN = ?";
    String sqlGetOldCT = "SELECT MaSP, SoLuong FROM ChiTietPhieuNhap WHERE MaPN = ?";
    String sqlDeleteCT = "DELETE FROM ChiTietPhieuNhap WHERE MaPN = ?";
    String sqlInsertCT = "INSERT INTO ChiTietPhieuNhap (MaPN, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
    String sqlUpdateTonKho = "UPDATE TonKho SET SoLuongKho = SoLuongKho + ? WHERE MaSP = ?";
    String sqlUpdateGiaNhap = "UPDATE SanPham SET GiaNhap = ? WHERE MaSP = ?";

    Connection conn = null;
    try {
        conn = new MyConnection().getConnection();
        conn.setAutoCommit(false); // Bắt đầu Transaction (đảm bảo tính toàn vẹn)

        // 1. Cập nhật thông tin chung của PhieuNhap
        try (PreparedStatement psPN = conn.prepareStatement(sqlUpdatePN)) {
            psPN.setDate(1, new java.sql.Date(pn.getNgayNhap().getTime()));
            psPN.setString(2, pn.getMaNCC().trim());
            psPN.setDouble(3, pn.getTongTien());
            psPN.setString(4, pn.getMaPN().trim());
            psPN.executeUpdate();
        }

        // 2. Hoàn trả lại tồn kho theo số lượng cũ trước khi xóa chi tiết cũ
        try (PreparedStatement psGetOld = conn.prepareStatement(sqlGetOldCT);
             PreparedStatement psUpTon = conn.prepareStatement(sqlUpdateTonKho)) {
            
            psGetOld.setString(1, pn.getMaPN().trim());
            try (ResultSet rs = psGetOld.executeQuery()) {
                while (rs.next()) {
                    String maSP = rs.getString("MaSP");
                    int soLuongCu = rs.getInt("SoLuong");
                    
                    // Trừ ngược số lượng cũ ra khỏi kho
                    psUpTon.setInt(1, -soLuongCu);
                    psUpTon.setString(2, maSP.trim());
                    psUpTon.executeUpdate();
                }
            }
        }

        // 3. Xóa các dòng chi tiết phiếu nhập cũ đi
        try (PreparedStatement psDel = conn.prepareStatement(sqlDeleteCT)) {
            psDel.setString(1, pn.getMaPN().trim());
            psDel.executeUpdate();
        }

        // 4. Thêm lại danh sách chi tiết mới và cộng dồn vào tồn kho mới
        try (PreparedStatement psIns = conn.prepareStatement(sqlInsertCT);
             PreparedStatement psUpTon = conn.prepareStatement(sqlUpdateTonKho);
             PreparedStatement psUpGia = conn.prepareStatement(sqlUpdateGiaNhap)) {
            
            for (ChiTietPhieuNhap ct : listCT) {
                // Thêm chi tiết mới
                psIns.setString(1, pn.getMaPN().trim());
                psIns.setString(2, ct.getMaSP().trim());
                psIns.setInt(3, ct.getSoLuong());
                psIns.setDouble(4, ct.getDonGia());
                psIns.executeUpdate();

                // Cộng thêm số lượng mới vào kho
                psUpTon.setInt(1, ct.getSoLuong());
                psUpTon.setString(2, ct.getMaSP().trim());
                psUpTon.executeUpdate();
                
                // ---> CẬP NHẬT GIÁ NHẬP MỚI VÀO BẢNG SẢN PHẨM <---
                psUpGia.setDouble(1, ct.getDonGia()); // Lấy đơn giá (giá nhập) mới
                psUpGia.setString(2, ct.getMaSP().trim());
                psUpGia.executeUpdate();
            }
        }

        conn.commit(); // Thành công thì lưu vĩnh viễn
        return 1; 

    } catch (SQLException ex) {
        if (conn != null) {
            try {
                conn.rollback(); // Gặp lỗi thì hoàn tác toàn bộ
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Bắt lỗi trùng khóa chính (SQL Server)
        if (ex.getErrorCode() == 2627 || ex.getErrorCode() == 2601) {
            return -2; 
        }
        
        ex.printStackTrace();
        return -1; // Lỗi chung
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true); // Trả về trạng thái mặc định
                conn.close();             // Đóng kết nối
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
    
    // Xóa phiếu nhập (Tự động xóa chi tiết nhờ ON DELETE CASCADE trong DB)
    public boolean deletePhieuNhap(String maPN) {
        String sql = "DELETE FROM PhieuNhap WHERE MaPN = ?";

        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPN);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
}
