
package design;
import DAO.khoDAO;
import java.awt.Color;
import model.khoSP;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;                 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class KSPPanel extends javax.swing.JPanel {

    DefaultComboBoxModel<String> cbomodelLoai = new DefaultComboBoxModel<>();
    ArrayList<khoSP> listSanPham = new ArrayList<>();
    DefaultTableModel tablemodel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; 
        }
    };
    khoSP sanPhamSelected = null;
    private final String PLACEHOLDER = "Nhập mã/Tên";

    
    public KSPPanel() {
        initComponents();
        cbbloai.setModel(cbomodelLoai);
        tblsanpham.setModel(tablemodel);
        loaddatatocombobox();
        inittable();
        loaddatatable();
        clearForm();
        cbbloai.addActionListener(e -> loaddatatable());
    }
    
    public void setEditableFields(boolean editable) {
        txtmaSP.setEnabled(editable);
        txtmaSP.setEditable(editable);
        txtten.setEnabled(editable);
        txtloai.setEnabled(editable);
        txtsoluong.setEnabled(editable);
        txtgianhap.setEnabled(editable);
        txtgiaban.setEnabled(editable);
    }

    public void loaddatatocombobox() {
    Object dangChon = cbomodelLoai.getSelectedItem();
    cbomodelLoai.removeAllElements();
    cbomodelLoai.addElement("-- Tất cả loại --");
    ArrayList<String> listLoai = new khoDAO().getAllLoai();
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
        String[] header = new String[]{"Mã SP", "Tên SP", "Loại SP", "Số Lượng", "Giá Nhập", "Giá Bán"};
        tablemodel.setColumnIdentifiers(header);
        tblsanpham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblsanpham.getSelectionModel().addListSelectionListener((e) -> {
            int row = tblsanpham.getSelectedRow();
            if (row >= 0 && row < listSanPham.size()) {
                sanPhamSelected = listSanPham.get(row);
                hienthithongtin();
            } else {
                sanPhamSelected = null;
            }
        });
        jScrollPane1.getViewport().setBackground(Color.white);
    }

    public void loaddatatable() {
        String keyword = this.txttimkiem.getText().trim();
        if (keyword.equals(PLACEHOLDER)) {
            keyword = "";
        }
        
        String loaiChon = "";
        if (this.cbbloai.getSelectedIndex() > 0 && this.cbbloai.getSelectedItem() != null) {
            loaiChon = this.cbbloai.getSelectedItem().toString();
        }

        // Lấy danh sách kết hợp lọc theo loại và từ khóa
        listSanPham = new khoDAO().getAll(loaiChon, keyword);
        tablemodel.setRowCount(0);
        for (khoSP sp : listSanPham) {
            tablemodel.addRow(new Object[]{
                sp.getMaSP(), 
                sp.getTenSP(), 
                sp.getLoaiSP(), 
                sp.getSoLuong(), 
                sp.getGiaNhap(), 
                sp.getGiaBan()
            });
        }
    }

    public void hienthithongtin() {
        if (sanPhamSelected != null) {
            txtmaSP.setText(sanPhamSelected.getMaSP());
            txtten.setText(sanPhamSelected.getTenSP());
            txtloai.setText(sanPhamSelected.getLoaiSP());
            txtsoluong.setText(String.valueOf(sanPhamSelected.getSoLuong()));
            txtgianhap.setText(String.valueOf(sanPhamSelected.getGiaNhap()));
            txtgiaban.setText(String.valueOf(sanPhamSelected.getGiaBan()));

            setEditableFields(true);
            txtmaSP.setEditable(false); // Không cho sửa mã khi đang chọn cập nhật
            btncapnhat.setEnabled(true);
            btnxoa.setEnabled(true);
        }
    }

    public void clearForm() {
        txtmaSP.setText("");
        txtten.setText("");
        txtloai.setText("");
        txtsoluong.setText("");
        txtgianhap.setText("");
        txtgiaban.setText("");
        tblsanpham.clearSelection();
        sanPhamSelected = null;
        setEditableFields(true); // Cho phép nhập mới khi bấm Thêm
        btncapnhat.setEnabled(false);
        btnxoa.setEnabled(false);
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
        Sheet sheet = w.createSheet("SanPham");
        String[] header = {"STT", "MÃ SP", "TÊN SP", "LOẠI SP", "SỐ LƯỢNG", "GIÁ NHẬP", "GIÁ BÁN"};
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
            row.createCell(3).setCellValue(sp.getLoaiSP());
            row.createCell(4).setCellValue(sp.getSoLuong());
            row.createCell(5).setCellValue(sp.getGiaNhap());
            row.createCell(6).setCellValue(sp.getGiaBan());
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnxuatexcel = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txttimkiem = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        cbbloai = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsanpham = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtmaSP = new javax.swing.JTextField();
        txtten = new javax.swing.JTextField();
        txtloai = new javax.swing.JTextField();
        txtsoluong = new javax.swing.JTextField();
        txtgianhap = new javax.swing.JTextField();
        txtgiaban = new javax.swing.JTextField();
        btncapnhat = new javax.swing.JButton();
        btnthem = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();

        jLabel1.setText("Product");

        jPanel1.setBackground(new java.awt.Color(0, 102, 51));

        jLabel2.setForeground(new java.awt.Color(242, 242, 242));
        jLabel2.setText("Tạo mới SP");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Xuất Excel");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(345, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(27, 27, 27)
                .addComponent(jLabel3)
                .addGap(63, 63, 63))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tạo mới SP");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Xuất Excel");

        jLabel6.setText("jLabel6");

        jPanel2.setBackground(new java.awt.Color(0, 102, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jLabel7.setText("jLabel7");

        jLabel8.setText("jLabel8");

        jButton1.setForeground(new java.awt.Color(0, 102, 51));
        jButton1.setText("Tạo mới SP");

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));

        btnxuatexcel.setForeground(new java.awt.Color(0, 204, 204));
        btnxuatexcel.setText("Xuất Excel");
        btnxuatexcel.addActionListener(this::btnxuatexcelActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnxuatexcel)
                .addGap(63, 63, 63))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnxuatexcel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 204));
        jLabel9.setText("Quản Lý Sản Phẩm");

        txttimkiem.setForeground(new java.awt.Color(255, 102, 102));
        txttimkiem.setText("Nhập mã/Tên");
        txttimkiem.addActionListener(this::txttimkiemActionPerformed);

        btnsearch.setText("Tìm kiếm");
        btnsearch.addActionListener(this::btnsearchActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnsearch)
                .addGap(18, 18, 18)
                .addComponent(cbbloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsearch)
                    .addComponent(cbbloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 204, 204))); // NOI18N

        tblsanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Loại SP", "Số Lượng", "Giá Nhập", "Giá Bán"
            }
        ));
        jScrollPane1.setViewportView(tblsanpham);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 204, 204))); // NOI18N

        jLabel10.setText("Mã SP");

        jLabel11.setText("Tên SP");

        jLabel12.setText("Loại SP");

        jLabel13.setText("Số Lượng");

        jLabel14.setText("Giá Nhập");

        jLabel15.setText("Giá Bán");

        txtloai.addActionListener(this::txtloaiActionPerformed);

        btncapnhat.setBackground(new java.awt.Color(0, 204, 204));
        btncapnhat.setForeground(new java.awt.Color(255, 255, 255));
        btncapnhat.setText("Cập Nhật");
        btncapnhat.addActionListener(this::btncapnhatActionPerformed);

        btnthem.setBackground(new java.awt.Color(0, 204, 204));
        btnthem.setForeground(new java.awt.Color(255, 255, 255));
        btnthem.setText("Thêm");
        btnthem.addActionListener(this::btnthemActionPerformed);

        btnxoa.setBackground(new java.awt.Color(0, 204, 204));
        btnxoa.setForeground(new java.awt.Color(255, 255, 255));
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(this::btnxoaActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(22, 22, 22)
                                .addComponent(txtmaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtten, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtloai, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel13))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtsoluong, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(txtgianhap)
                            .addComponent(txtgiaban))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(196, 196, 196)
                        .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtmaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtgianhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtgiaban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncapnhat)
                    .addComponent(btnthem)
                    .addComponent(btnxoa))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
       if (sanPhamSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần cập nhật trên bảng!");
            return;
        }

        String ma = sanPhamSelected.getMaSP();
        String ten = txtten.getText().trim();
        String loai = txtloai.getText().trim();
 if (ma.isEmpty() || ten.isEmpty() || loai.isEmpty() || txtsoluong.getText().trim().isEmpty() || txtgianhap.getText().trim().isEmpty() || txtgiaban.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ các dữ liệu!");
        return;
    }
        try {
            int soluong = Integer.parseInt(txtsoluong.getText().trim());
            double gianhap = Double.parseDouble(txtgianhap.getText().trim());
            double giaban = Double.parseDouble(txtgiaban.getText().trim());
                if (soluong<0) {JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn hoặc bằng 0"); return;}
            if (gianhap<0 || giaban<0) {JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng, giá nhập, giá bán lớn hơn 0"); return;}
            khoDAO dao = new khoDAO();
            khoSP sp = new khoSP(ma, ten, loai, soluong, gianhap, giaban);
            int kq = dao.update(sp);

            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loaddatatable();
                loaddatatocombobox();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra lại kiểu dữ liệu số!");
        }
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
   String ma = txtmaSP.getText().trim();
    String ten = txtten.getText().trim();
    String loai = txtloai.getText().trim();

    if (ma.isEmpty() || ten.isEmpty() || loai.isEmpty() || txtsoluong.getText().trim().isEmpty() || txtgianhap.getText().trim().isEmpty() || txtgiaban.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ các dữ liệu!");
        return;
    }
    try {
        int soluong = Integer.parseInt(txtsoluong.getText().trim());
        double gianhap = Double.parseDouble(txtgianhap.getText().trim());
        double giaban = Double.parseDouble(txtgiaban.getText().trim());
          if (soluong<0) {JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn hoặc bằng 0"); return;}
            if (gianhap<0 || giaban<0) {JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng, giá nhập, giá bán lớn hơn 0"); return;}
        khoSP sp = new khoSP(ma, ten, loai, soluong, gianhap, giaban);
        int kq = new khoDAO().insert(sp);

        if (kq > 0) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loaddatatocombobox();
            loaddatatable();
            clearForm();
        } else if (kq==-2)  {
            
            JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại");
        }
        else {
         JOptionPane.showMessageDialog(this, "Thêm thất bại (có thể mã đã tồn tại)!");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra lại kiểu dữ liệu số!");
    }
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
       if (sanPhamSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            khoDAO dao = new khoDAO();
            int kq = dao.delete(sanPhamSelected.getMaSP());
            if (kq > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loaddatatable();
                loaddatatocombobox();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        loaddatatable();
    }//GEN-LAST:event_btnsearchActionPerformed

    private void btnxuatexcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxuatexcelActionPerformed
 if (listSanPham == null || listSanPham.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Không có dữ liệu", "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    exportSanPhamToExcel(listSanPham);
    }//GEN-LAST:event_btnxuatexcelActionPerformed

    private void txttimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttimkiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttimkiemActionPerformed

    private void txtloaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtloaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtloaiActionPerformed
public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Kho");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new KSPPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnsearch;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JButton btnxuatexcel;
    private javax.swing.JComboBox<String> cbbloai;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblsanpham;
    private javax.swing.JTextField txtgiaban;
    private javax.swing.JTextField txtgianhap;
    private javax.swing.JTextField txtloai;
    private javax.swing.JTextField txtmaSP;
    private javax.swing.JTextField txtsoluong;
    private javax.swing.JTextField txtten;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
