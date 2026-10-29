
package design;
import DAO.sanphamDAO;
import java.awt.Color;
import model.SanPham;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;                 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class SPPanel extends javax.swing.JPanel {

    DefaultComboBoxModel<String> cbomodelLoai = new DefaultComboBoxModel<>();
    ArrayList<SanPham> listSanPham = new ArrayList<>();
    DefaultTableModel tablemodel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; 
        }
    };
    SanPham sanPhamSelected = null;

    
    public SPPanel() {
        initComponents();
        cbbloai.setModel(cbomodelLoai);
        cbbloaisp.setModel(cbomodelLoai);
        tblsanpham.setModel(tablemodel);
        loaddatatocombobox();
        inittable();
        loaddatatable();
        clearForm();
        cbbloai.addActionListener(e -> loaddatatable());
        cbbloaisp.addActionListener(e -> loaddatatable());
    }
    
    public void setEditableFields(boolean editable) {
        txtmaSP.setEnabled(editable);
        txtten.setEnabled(editable);
        cbbloaisp.setEnabled(editable);
        txtdv.setEnabled(editable);
        txtgiaban.setEnabled(editable);
        txtgianhap.setEnabled(editable);
        txttrangthai.setEnabled(editable);
    }

    public void loaddatatocombobox() {
    Object dangChon = cbomodelLoai.getSelectedItem();
    cbomodelLoai.removeAllElements();
    cbomodelLoai.addElement("-- Tất cả loại --");
    ArrayList<String> listLoai = new sanphamDAO().getAllLoai();
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
        String[] header = new String[]{"Mã SP", "Tên SP", "Tên Loại SP", "Đơn Vị", "Giá Nhập", "Giá Bán","Trạng Thái"};
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
            keyword = "";      
        
        String loaiChon = "";
        if (this.cbbloai.getSelectedIndex() > 0 && this.cbbloai.getSelectedItem() != null) {
            loaiChon = this.cbbloai.getSelectedItem().toString();
        }
        if (this.cbbloaisp.getSelectedIndex() > 0 && this.cbbloaisp.getSelectedItem() != null) {
            loaiChon = this.cbbloaisp.getSelectedItem().toString();
        }

        // Lấy danh sách kết hợp lọc theo loại và từ khóa
        listSanPham = new sanphamDAO().getAll(loaiChon, keyword);
        tablemodel.setRowCount(0);
        for (SanPham sp : listSanPham) {
            tablemodel.addRow(new Object[]{
                sp.getMaSP(), 
                sp.getTenSP(), 
                sp.getTenLoaiSP(), 
                sp.getTrangThai(), 
                sp.getDV(), 
                sp.getGiaNhap(),
                sp.getGiaBan()
            });
        }
        System.out.println(listSanPham.size());
    }

    public void hienthithongtin() {
        if (sanPhamSelected != null) {
            txtmaSP.setText(sanPhamSelected.getMaSP());
            txtten.setText(sanPhamSelected.getTenSP());
            txtdv.setText(String.valueOf(sanPhamSelected.getDV()));
            txtgianhap.setText(String.valueOf(sanPhamSelected.getGiaNhap()));
            txtgiaban.setText(String.valueOf(sanPhamSelected.getGiaBan()));
            txttrangthai.setText(sanPhamSelected.getTrangThai());

            setEditableFields(true);
            txtmaSP.setEditable(false);
            txtgianhap.setEditable(false);
            txtgiaban.setEditable(false);
            btncapnhat.setEnabled(true);
            btnxoa.setEnabled(true);
        }
    }

    public void clearForm() {
        txtmaSP.setText("");
        txtten.setText("");
        txtdv.setText("");
        txtgiaban.setText("");
        txtgianhap.setText("");
        txttrangthai.setText("");
        tblsanpham.clearSelection();
        sanPhamSelected = null;
        setEditableFields(true);
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
  public void exportSanPhamToExcel(ArrayList<SanPham> list) {
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
        String[] header = {"STT", "MÃ SP", "TÊN SP", "Tên LOẠI SP", "Đơn VỊ", "Giá Nhập", "Giá Bán","Trạng Thái"};
        Row rowHeader = sheet.createRow(0);
        for (int i = 0; i < header.length; i++) {
            rowHeader.createCell(i).setCellValue(header[i]);
        }
        int stt = 1;
        for (SanPham sp : list) {
            Row row = sheet.createRow(stt);
            row.createCell(0).setCellValue(stt);
            row.createCell(1).setCellValue(sp.getMaSP());
            row.createCell(2).setCellValue(sp.getTenSP());
            row.createCell(3).setCellValue(sp.getTenLoaiSP());
            row.createCell(4).setCellValue(sp.getDV());
            row.createCell(5).setCellValue(sp.getGiaNhap());
            row.createCell(6).setCellValue(sp.getGiaBan());
            row.createCell(7).setCellValue(sp.getTrangThai());
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
        txtloai = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txttimkiem = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        cbbloai = new javax.swing.JComboBox<>();
        btnxuatexcel = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsanpham = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtmaSP = new javax.swing.JTextField();
        txtten = new javax.swing.JTextField();
        txtdv = new javax.swing.JTextField();
        txtgiaban = new javax.swing.JTextField();
        txtgianhap = new javax.swing.JTextField();
        btncapnhat = new javax.swing.JButton();
        btnthem = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txttrangthai = new javax.swing.JTextField();
        cbbloaisp = new javax.swing.JComboBox<>();

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

        txtloai.addActionListener(this::txtloaiActionPerformed);

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 204));
        jLabel9.setText("Quản Lý Sản Phẩm");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        txttimkiem.setForeground(new java.awt.Color(255, 102, 102));
        txttimkiem.setText("Nhập mã/Tên");
        txttimkiem.addActionListener(this::txttimkiemActionPerformed);

        btnsearch.setBackground(new java.awt.Color(0, 204, 204));
        btnsearch.setForeground(new java.awt.Color(255, 255, 255));
        btnsearch.setText("Tìm kiếm");
        btnsearch.addActionListener(this::btnsearchActionPerformed);

        btnxuatexcel.setBackground(new java.awt.Color(0, 204, 204));
        btnxuatexcel.setForeground(new java.awt.Color(255, 255, 255));
        btnxuatexcel.setText("Xuất Excel");
        btnxuatexcel.addActionListener(this::btnxuatexcelActionPerformed);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 204, 204))); // NOI18N

        tblsanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Tên Loại SP", "Đơn vị", "Giá Bán", "Giá Nhập", "Trạng Thái"
            }
        ));
        jScrollPane1.setViewportView(tblsanpham);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 989, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Nhập mã/Tên");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbbloai, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnxuatexcel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnxuatexcel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbloai))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnsearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 204, 204))); // NOI18N

        jLabel10.setText("Mã SP");

        jLabel11.setText("Tên SP");

        jLabel12.setText(" Loại SP");

        jLabel13.setText("Đơn vị");

        jLabel14.setText("Giá Bán");

        jLabel15.setText("Giá Nhập");

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

        jLabel17.setText("Trạng Thái");

        cbbloaisp.addActionListener(this::cbbloaispActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtmaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtten, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                            .addComponent(cbbloaisp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13))))
                        .addGap(31, 31, 31)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txttrangthai)
                            .addComponent(txtdv, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(txtgiaban)
                            .addComponent(txtgianhap))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtmaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtdv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtgiaban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtgianhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbloaisp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txttrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnxoa, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(btnthem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(btncapnhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
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
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    String DV = txtdv.getText().trim();
    String gianhapStr = txtgianhap.getText().trim();
    String giabanStr = txtgiaban.getText().trim();
    String trangthai = txttrangthai.getText().trim();

    if (ma.isEmpty() || ten.isEmpty() || loai.isEmpty() || DV.isEmpty() || gianhapStr.isEmpty() || giabanStr.isEmpty()|| trangthai.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ các dữ liệu!");
        return;
    }
    try {
        double gianhap =Double.parseDouble(gianhapStr);
        double giaban = Double.parseDouble(giabanStr);
        
        if (gianhap < 0 || giaban < 0) {
            JOptionPane.showMessageDialog(this, "Tồn kho và giá bán phải lớn hơn hoặc bằng 0!");
            return;
        }

        SanPham sp = new SanPham(ma, ten, loai, DV, gianhap, giaban,trangthai);
        
        sanphamDAO dao = new sanphamDAO();
        int kq = dao.update(sp);

        if (kq > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loaddatatable();
            loaddatatocombobox();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra lại kiểu dữ liệu số cho Tồn kho và Giá bán!");
    }
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
 String ma = txtmaSP.getText().trim();
    String ten = txtten.getText().trim();
    String loai = txtloai.getText().trim();
    String DV = txtdv.getText().trim();
    String gianhapStr = txtgiaban.getText().trim();
    String giabanStr = txtgianhap.getText().trim();
    String trangthai = txttrangthai.getText().trim();

    if (ma.isEmpty() || ten.isEmpty() || loai.isEmpty() || DV.isEmpty() || gianhapStr.isEmpty() || giabanStr.isEmpty()|| trangthai.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ các dữ liệu!");
        return;
    }
    try {
        double gianhap =Double.parseDouble(gianhapStr);
        double giaban = Double.parseDouble(giabanStr);

        if (gianhap < 0 || giaban < 0) {
            JOptionPane.showMessageDialog(this, "giá nhập và giá bán phải lớn hơn hoặc bằng 0!");
            return;
        }

        SanPham sp = new SanPham(ma, ten, loai, DV, gianhap, giaban,trangthai);
        int kq = new sanphamDAO().insert(sp);

        if (kq > 0) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loaddatatocombobox();
            loaddatatable();
            clearForm();
        } else if (kq == -2) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại!");
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra lại kiểu dữ liệu số cho Tồn kho và Giá bán!");
    }
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
       if (sanPhamSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            sanphamDAO dao = new sanphamDAO();
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

    private void cbbloaispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbloaispActionPerformed
        loaddatatable();
    }//GEN-LAST:event_cbbloaispActionPerformed
public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Sản Phẩm");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SPPanel());
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
    private javax.swing.JComboBox<String> cbbloaisp;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JTextField txtdv;
    private javax.swing.JTextField txtgiaban;
    private javax.swing.JTextField txtgianhap;
    private javax.swing.JTextField txtloai;
    private javax.swing.JTextField txtmaSP;
    private javax.swing.JTextField txtten;
    private javax.swing.JTextField txttimkiem;
    private javax.swing.JTextField txttrangthai;
    // End of variables declaration//GEN-END:variables
}
