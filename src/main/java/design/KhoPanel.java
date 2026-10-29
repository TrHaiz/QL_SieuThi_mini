
package design;
import DAO.khoDao;
import DAO.sanphamDAO;
import java.awt.Color;
import model.khoSP;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;  
import model.SanPham;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import DAO.ReloadData;

public class KhoPanel extends javax.swing.JPanel implements ReloadData{
    DefaultComboBoxModel<String> cbomodelLoai = new DefaultComboBoxModel<>();
    ArrayList<khoSP> listkho = new ArrayList<>();
    DefaultTableModel tablemodel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; 
        }
    };
    khoSP khoSelected = null;
    
    public KhoPanel() {
        initComponents();
        cbbloai.setModel(cbomodelLoai);
        tblkho.setModel(tablemodel);
        loaddatatocombobox();
        inittable();
        loaddatatable();
        clearForm();
    }
    
    @Override
    public void reloadData() {
            loaddatatable();
            loaddatatocombobox();
        }
    
    public void loaddatatocombobox() {
    Object dangChon = cbomodelLoai.getSelectedItem();
    cbomodelLoai.removeAllElements();
    cbomodelLoai.addElement("-- Tất cả loại --");
    ArrayList<String> listLoai = new khoDao().getAllLoai();
    for (String loai : listLoai) {
        cbomodelLoai.addElement(loai);
    }

    if (dangChon != null && cbomodelLoai.getIndexOf(dangChon) >= 0) {
        cbomodelLoai.setSelectedItem(dangChon);
    } else {
        cbomodelLoai.setSelectedItem("-- Tất cả loại --");
    }
    }
       public void inittable() {
        String[] header = new String[]{"Mã SP", "Tên SP", "Loại SP", "Mã NCC", "Tồn Kho", "Ngày Nhập"};
        tablemodel.setColumnIdentifiers(header);
        tblkho.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblkho.getSelectionModel().addListSelectionListener((e) -> {
            int row = tblkho.getSelectedRow();
            if (row >= 0 && row < listkho.size()) {
                khoSelected = listkho.get(row);
                hienthithongtin();
            } else {
                khoSelected = null;
            }
        });
        jScrollPane1.getViewport().setBackground(Color.white);
    }
           public void loaddatatable() {
        String keyword = this.txttimkiem.getText().trim();
            keyword = "";      
        
        String loaiChon = "";
        if (this.cbbloai.getSelectedIndex() > 0 && this.cbbloai.getSelectedItem() != null) {
            loaiChon = this.cbbloai.getSelectedItem().toString();
        }

        // Lấy danh sách kết hợp lọc theo loại và từ khóa
        listkho = new khoDao().getAll(loaiChon, keyword);
        tablemodel.setRowCount(0);
        for (khoSP sp : listkho) {
            tablemodel.addRow(new Object[]{
                sp.getMaSP(), 
                sp.getTenSP(), 
                sp.getTenLoaiSP(), 
                sp.getMaNCC(), 
                sp.getTonKho(), 
                sp.getNgayNhap()
            });
        }
        System.out.println(listkho.size());
    }
        public void hienthithongtin() {
        if (khoSelected != null) {

        }
    }

    public void clearForm() {
        khoSelected = null;
    }
    public int getCellInt(Cell cell) {
    try {
        if (cell == null) {
            return 0;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        if (cell.getCellType() == CellType.STRING) {
            String value = cell.getStringCellValue().trim();
            if (value.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(value);   // dòng bổ sung
        }
    } catch (NumberFormatException numberFormatException) {
        System.out.println("Lỗi chuyển đổi dữ liệu");
        return 0;
    } catch (Exception e) {
        System.out.println("Lỗi đọc dữ liệu");
        return 0;
    }
    return 0;
}
  public String getCellString(Cell cell) {
    if (cell == null) {
        return "";
    }

    if (cell.getCellType() == CellType.STRING) {
        return cell.getStringCellValue().trim();
    }

    if (cell.getCellType() == CellType.NUMERIC) {
        return String.valueOf(cell.getNumericCellValue());
    }

    return "";
}
  public void exportSanPhamToExcel(ArrayList<khoSP> list) {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("File Excel", "xlsx"));
    if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
        return;
    }
    java.io.File file = chooser.getSelectedFile();
    if (!file.getName().toLowerCase().endsWith(".xlsx")) {
        file = new java.io.File(file.getAbsolutePath() + ".xlsx");
    }
    try (Workbook w = new XSSFWorkbook();
         java.io.FileOutputStream fo = new java.io.FileOutputStream(file)) {
        Sheet sheet = w.createSheet("Kho Sản Phẩm");
        String[] header = {"STT", "MÃ SP", "TÊN SP", "LOẠI SP", "Mã NCC", "Tồn Kho", "Ngày Nhập"};
        Row rowHeader = sheet.createRow(0);
        for (int i = 0; i < header.length; i++) {
            rowHeader.createCell(i).setCellValue(header[i]);
        }
        int stt = 1;
        for (khoSP sp : list) {
            Row row = sheet.createRow(stt);
            row.createCell(0).setCellValue(stt);
            row.createCell(1).setCellValue(sp.getMaSP());
            row.createCell(2).setCellValue(sp.getTenSP());
            row.createCell(3).setCellValue(sp.getTenLoaiSP());
            row.createCell(4).setCellValue(sp.getMaNCC());
            row.createCell(5).setCellValue(sp.getTonKho());
            row.createCell(6).setCellValue(sp.getNgayNhap());
            stt++;
        }
        for (int i = 0; i < header.length; i++) {
            sheet.autoSizeColumn(i);
        }
        w.write(fo);
        JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + e.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
}
    // </editor-fold>                        

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btntaomoi = new javax.swing.JButton();
        btnxuat = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txttimkiem = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btntimkiem = new javax.swing.JButton();
        cbbloai = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblkho = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        jLabel4.setText("jLabel4");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 255, 255));
        jLabel2.setText("Quản Lý Kho Hàng");

        btntaomoi.setBackground(new java.awt.Color(51, 255, 255));
        btntaomoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btntaomoi.setForeground(new java.awt.Color(255, 255, 255));
        btntaomoi.setText("Tạo mới SP");

        btnxuat.setBackground(new java.awt.Color(51, 255, 255));
        btnxuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnxuat.setForeground(new java.awt.Color(255, 255, 255));
        btnxuat.setText("Xuất SP");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btntaomoi)
                .addGap(45, 45, 45)
                .addComponent(btnxuat)
                .addGap(54, 54, 54))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btntaomoi)
                    .addComponent(btnxuat))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txttimkiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Nhập Tên/Mã :");

        btntimkiem.setBackground(new java.awt.Color(51, 255, 255));
        btntimkiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btntimkiem.setForeground(new java.awt.Color(255, 255, 255));
        btntimkiem.setText("Tìm kiếm ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btntimkiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 596, Short.MAX_VALUE)
                .addComponent(cbbloai, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(102, 255, 255));

        tblkho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Loại SP", "Mã NCC", "Tồn Kho", "Ngày Nhập"
            }
        ));
        jScrollPane1.setViewportView(tblkho);

        jLabel3.setBackground(new java.awt.Color(51, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Danh Sách Kho Sản Phẩm ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1047, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1140, 1140, 1140)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btntaomoi;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JButton btnxuat;
    private javax.swing.JComboBox<String> cbbloai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblkho;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
