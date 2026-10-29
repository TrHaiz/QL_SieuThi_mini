/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JTable;
/**
 *
 * @author Long
 */
public class XuatHoaDonPDFDAO {
    public static void inHoaDonThanhToan(String maHD, String thoiGian, String tenNhanVien,
            JTable tblGioHang, String tongCong, String giamGia,
            String thanhTien, String khachTra, String tienThua) {
        try {
            // --- THÊM PHẦN KHỞI TẠO THƯ MỤC ---
            String folderPath = "HoaDon_PDF";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            
            String filePath = folderPath + File.separator + "HoaDon_" + maHD + ".pdf";

            // 1. Cấu hình khổ giấy in nhiệt (Khoảng 80mm)
            Rectangle pageSize = new Rectangle(250, 600);
            Document document = new Document(pageSize, 10, 10, 15, 15);
            
            // Truyền filePath mới vào FileOutputStream
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // 2. Cài đặt Font chữ tiếng Việt
            BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontTitle = new Font(bf, 14, Font.BOLD);
            Font fontBold = new Font(bf, 10, Font.BOLD);
            Font fontNormal = new Font(bf, 10, Font.NORMAL);

            // 3. Tiêu đề cửa hàng
            Paragraph header = new Paragraph("Siêu Thị Mini\n", fontTitle);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph info = new Paragraph("54 Triều Khúc, Quận Thanh Xuân, TP Hà Nội\nSĐT: 0123456789\n-----------------------------------------", fontNormal);
            info.setAlignment(Element.ALIGN_CENTER);
            document.add(info);

            // 4. Thông tin hóa đơn
            Paragraph title = new Paragraph("Hóa Đơn Thanh Toán\n", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph meta = new Paragraph("Số Phiếu: " + maHD + "\nNgày: " + thoiGian + "\nThu Ngân: " + tenNhanVien + "\n-----------------------------------------", fontNormal);
            document.add(meta);

            // 5. Bảng Chi Tiết Sản Phẩm
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4f, 1f, 2.5f, 2.5f}); // Tỉ lệ độ rộng các cột: Tên, SL, Giá, Thành Tiền

            // Header bảng
            table.addCell(new PdfPCell(new Phrase("Tên SP", fontBold))).setBorder(Rectangle.NO_BORDER);
            table.addCell(new PdfPCell(new Phrase("SL", fontBold))).setBorder(Rectangle.NO_BORDER);
            table.addCell(new PdfPCell(new Phrase("Giá", fontBold))).setBorder(Rectangle.NO_BORDER);
            table.addCell(new PdfPCell(new Phrase("Thành Tiền", fontBold))).setBorder(Rectangle.NO_BORDER);

            // Duyệt từng dòng của JTable trên giao diện đưa vào PDF
            for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                String tenSP = tblGioHang.getValueAt(i, 1).toString();
                String sl = tblGioHang.getValueAt(i, 2).toString();
                String gia = tblGioHang.getValueAt(i, 3).toString().replace(" đ", ""); 
                String tTien = tblGioHang.getValueAt(i, 4).toString().replace(" đ", "");

                table.addCell(taoO(tenSP, fontNormal, Element.ALIGN_LEFT));
                table.addCell(taoO(sl, fontNormal, Element.ALIGN_LEFT));
                table.addCell(taoO(gia, fontNormal, Element.ALIGN_LEFT));
                table.addCell(taoO(tTien, fontNormal, Element.ALIGN_LEFT));
            }
            document.add(table);
            document.add(new Paragraph("-----------------------------------------", fontNormal));

            // 6. Tổng kết tiền
            PdfPTable sumTable = new PdfPTable(2);
            sumTable.setWidthPercentage(100);
            sumTable.setWidths(new float[]{1.5f, 1f});

            sumTable.addCell(taoO("Tổng cộng:", fontNormal, Element.ALIGN_RIGHT));
            sumTable.addCell(taoO(tongCong, fontNormal, Element.ALIGN_RIGHT));

            sumTable.addCell(taoO("Khuyến Mãi:", fontNormal, Element.ALIGN_RIGHT));
            sumTable.addCell(taoO(giamGia, fontNormal, Element.ALIGN_RIGHT));

            sumTable.addCell(taoO("Thành Tiền:", fontBold, Element.ALIGN_RIGHT));
            sumTable.addCell(taoO(thanhTien, fontBold, Element.ALIGN_RIGHT));

            sumTable.addCell(taoO("Tiền Khách Đưa:", fontNormal, Element.ALIGN_RIGHT));
            sumTable.addCell(taoO(khachTra, fontNormal, Element.ALIGN_RIGHT));

            sumTable.addCell(taoO("Tiền Thừa:", fontNormal, Element.ALIGN_RIGHT));
            sumTable.addCell(taoO(tienThua, fontNormal, Element.ALIGN_RIGHT));

            document.add(sumTable);

            // 7. Chân chữ ký
            Paragraph footer = new Paragraph("\nXin Cảm Ơn Quý Khách", fontTitle);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

            // 8. Tự động mở file PDF vừa in lên màn hình (Dùng filePath thay vì fileName)
            java.awt.Desktop.getDesktop().open(new File(filePath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm tiện ích giúp rút gọn việc tạo ô trong bảng PDF
    private static PdfPCell taoO(String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }
}