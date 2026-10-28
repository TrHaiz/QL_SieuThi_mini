
package design;

import DAO.KhachHangDao;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import model.KhachHang;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper; //làm việc với các định dạng và kiểu dữ liệu trong file Excel
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class KHPanel extends javax.swing.JPanel {
     DefaultComboBoxModel<String> cboModelGioiTinh = new DefaultComboBoxModel<>();
     ArrayList<KhachHang> listKH = new ArrayList<>();
     DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Khóa không cho sửa trực tiếp trên bảng
        }
    };
     KhachHang khSelected = null;
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public KHPanel() {
        initComponents();
        jdcns.setDateFormatString("yyyy-MM-dd");
        cbogt.setModel(cboModelGioiTinh);
        tablekh.setModel(tableModel);  
        
        loaddatatocombobox();
        inittable();
        loaddatatable(); // Load dữ liệu lên bảng khi khởi tạo
        
        setEditableFields(false); // Khóa các ô nhập liệu khi vừa load form
        btnluu.setEnabled(false);
        btncapnhat.setEnabled(false);
        btnxoa.setEnabled(false);
        btnthem.setEnabled(true);
        btntimkiem.setEnabled(true);
        btnthoat.setEnabled(true);
    }

    public void setEditableFields(boolean editable) {
        txtmkh.setEnabled(editable);
        txtmkh.setEditable(editable);
        txttkh.setEnabled(editable);
        jdcns.setEnabled(editable);
        txtsdt.setEnabled(editable);
        txtemal.setEnabled(editable);
        cbogt.setEnabled(editable);
    }
    
    public void loaddatatocombobox() {
        cboModelGioiTinh.removeAllElements(); //xóa dữ liệu cũ
        cboModelGioiTinh.addElement("--Chọn giới tính--");
        cboModelGioiTinh.addElement("Nam");
        cboModelGioiTinh.addElement("Nữ");
        cboModelGioiTinh.addElement("Khác");
    }
    
    public void inittable() {
        String header[] = {
            "Mã Khách Hàng", "Họ và Tên", "Giới Tính", "Ngày Sinh", "Số Điện Thoại", "Email"
        };
        tableModel.setColumnIdentifiers(header);
        tablekh.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablekh.getSelectionModel().addListSelectionListener(e -> {
            int row = tablekh.getSelectedRow();
            if (row >= 0) {
                khSelected = listKH.get(row);
                hienthithongtin();
            } else {
                khSelected = null;
            }
        });
        jScrollPane1.getViewport().setBackground(Color.white); //màu nền cho phần trống bên trong khung cuộn thành màu trắng
    }
    
    public void loaddatatable() {
        String keyword = txttimkiem.getText().trim(); // Ô nhập từ khóa tìm kiếm (mã, tên hoặc SĐT)
        listKH = new KhachHangDao().getAll(keyword);
        tableModel.setRowCount(0);

        for (KhachHang kh : listKH) {
            tableModel.addRow(new Object[]{
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getGioiTinh(),
                sdf.format(kh.getNgaySinh()),
                kh.getSoDienThoai(),
                kh.getEmail() != null ? kh.getEmail() : ""
            });
        }
    }
    
    public void hienthithongtin() {
        if (khSelected == null) return;

        txtmkh.setText(khSelected.getMaKH());
        txttkh.setText(khSelected.getTenKH());
        cbogt.setSelectedItem(khSelected.getGioiTinh());
        jdcns.setDate(khSelected.getNgaySinh());
        txtsdt.setText(khSelected.getSoDienThoai());
        txtemal.setText(khSelected.getEmail() != null ? khSelected.getEmail() : "");

        setEditableFields(true);
        txtmkh.setEditable(false); // Khóa mã khách hàng khi đang cập nhật

        btnluu.setEnabled(false);
        btncapnhat.setEnabled(true);
        btnxoa.setEnabled(true);
    }
    
    public void clearForm() {
        txtmkh.setText("");
        txttkh.setText("");
        jdcns.setDate(null);
        txtsdt.setText("");
        txtemal.setText("");
        cbogt.setSelectedIndex(0);

        tablekh.clearSelection();
        khSelected = null;

        setEditableFields(false);

        btnluu.setEnabled(false);
        btncapnhat.setEnabled(false);
        btnxoa.setEnabled(false);
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jdcns = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        btnluu = new javax.swing.JButton();
        btnlammoi = new javax.swing.JButton();
        cbogt = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtsdt = new javax.swing.JTextField();
        txtemal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txttkh = new javax.swing.JTextField();
        btncapnhat = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btnthoat = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtmkh = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txttimkiem = new javax.swing.JTextField();
        btntimkiem = new javax.swing.JButton();
        btnxuatexcel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablekh = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1000, 600));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setText("Quản lý khách hàng");
        jLabel1.setVerifyInputWhenFocusTarget(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(350, 350));
        jPanel1.setPreferredSize(new java.awt.Dimension(350, 350));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 204, 204));
        jLabel8.setText("Ngày sinh:");

        jdcns.setDateFormatString("dd/MM/yyyy");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 204));
        jLabel9.setText("Số điện thoại:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 204));
        jLabel6.setText("Giới tính:");

        btnthem.setBackground(new java.awt.Color(0, 204, 204));
        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnthem.setForeground(new java.awt.Color(255, 255, 255));
        btnthem.setText("Thêm");
        btnthem.addActionListener(this::btnthemActionPerformed);

        btnluu.setBackground(new java.awt.Color(0, 204, 204));
        btnluu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnluu.setForeground(new java.awt.Color(255, 255, 255));
        btnluu.setText("Lưu");
        btnluu.addActionListener(this::btnluuActionPerformed);

        btnlammoi.setBackground(new java.awt.Color(0, 204, 204));
        btnlammoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnlammoi.setForeground(new java.awt.Color(255, 255, 255));
        btnlammoi.setText("Làm mới");
        btnlammoi.addActionListener(this::btnlammoiActionPerformed);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 204));
        jLabel10.setText("Email:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 204));
        jLabel5.setText("Tên khách hàng:");

        txttkh.addActionListener(this::txttkhActionPerformed);

        btncapnhat.setBackground(new java.awt.Color(0, 204, 204));
        btncapnhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btncapnhat.setForeground(new java.awt.Color(255, 255, 255));
        btncapnhat.setText("Cập nhật");
        btncapnhat.addActionListener(this::btncapnhatActionPerformed);

        btnxoa.setBackground(new java.awt.Color(0, 204, 204));
        btnxoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnxoa.setForeground(new java.awt.Color(255, 255, 255));
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(this::btnxoaActionPerformed);

        btnthoat.setBackground(new java.awt.Color(0, 204, 204));
        btnthoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnthoat.setForeground(new java.awt.Color(255, 255, 255));
        btnthoat.setText("Thoát");
        btnthoat.addActionListener(this::btnthoatActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 204));
        jLabel3.setText("Mã khách hàng:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(54, 54, 54)
                            .addComponent(btnluu, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(129, 129, 129)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(24, 24, 24)
                                    .addComponent(btncapnhat)
                                    .addGap(49, 49, 49)
                                    .addComponent(btnthoat, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtemal, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtsdt)
                                        .addGap(13, 13, 13)))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(277, 277, 277))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cbogt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jdcns, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addGap(18, 18, 18))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(22, 22, 22)))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txttkh)
                                        .addComponent(txtmkh))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtmkh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttkh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbogt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jdcns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtemal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnluu, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnthoat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Nhập mã hoặc tên:");
        jLabel2.setVerifyInputWhenFocusTarget(false);

        btntimkiem.setBackground(new java.awt.Color(0, 204, 204));
        btntimkiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btntimkiem.setForeground(new java.awt.Color(255, 255, 255));
        btntimkiem.setText("Tìm kiếm");
        btntimkiem.addActionListener(this::btntimkiemActionPerformed);

        btnxuatexcel.setBackground(new java.awt.Color(0, 204, 204));
        btnxuatexcel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnxuatexcel.setForeground(new java.awt.Color(255, 255, 255));
        btnxuatexcel.setText("Xuất excel");
        btnxuatexcel.addActionListener(this::btnxuatexcelActionPerformed);

        tablekh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane1.setViewportView(tablekh);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btntimkiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnxuatexcel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btntimkiem)
                            .addComponent(btnxuatexcel)
                            .addComponent(jLabel2)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btntimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemActionPerformed
        loaddatatable();
    }//GEN-LAST:event_btntimkiemActionPerformed

    public void ExportDataToExcel(ArrayList<KhachHang> list) {
        JFileChooser chooser = new JFileChooser(); //cửa sổ hệ thống để chọn thư mục và đặt tên file khi lưu
        
        chooser.setFileFilter( new FileNameExtensionFilter("File Excel", "xlsx")); //hiển thị các file có phần mở rộng là .xlsx
        int result = chooser.showSaveDialog(this); //mở hộp thoại ở chế độ lưu file
        if (result != JFileChooser.APPROVE_OPTION) { return;  } //nếu không lưu thì cancel tắt đi thoát
        File file = chooser.getSelectedFile(); //gán file được chọn
        // Nếu người dùng không nhập .xlsx thì tự thêm vào
        if (!file.getName().toLowerCase().endsWith(".xlsx")) {
            file = new File(file.getAbsolutePath() + ".xlsx");
        } ////////tạo file excel mới/////////////////////mở luồng ghi dữ liệu xuống file vừa chọn
        try (Workbook w = new XSSFWorkbook(); FileOutputStream fo = new FileOutputStream(file)) {
            CreationHelper creationHelper = w.getCreationHelper(); //tạo biến để định dạng
            CellStyle dateStyle = w.createCellStyle(); 
            dateStyle.setDataFormat(
                    creationHelper.createDataFormat().getFormat("dd/MM/yyyy") //định dạng trong excel
);
            Sheet sheet = w.createSheet("DanhSach");//tạo sheet
            String header[] = {
            "Mã Khách Hàng", "Họ và Tên", "Giới Tính", "Ngày Sinh", "Số Điện Thoại", "Email"
        };
            Row rowHeader = sheet.createRow(0);//tạo hàng tiêu đề
            for (int i = 0; i < header.length; i++) {
                rowHeader.createCell(i).setCellValue(header[i]); //ghi lần lượt
            }
            int index = 1;
            for (KhachHang kh : list) {
                Row row = sheet.createRow(index);
            
            // Cột 0: Số thứ tự (STT)
            row.createCell(0).setCellValue(index);
            // Cột 1: Mã Khách Hàng
            row.createCell(1).setCellValue(kh.getMaKH());
            // Cột 2: Họ và Tên
            row.createCell(2).setCellValue(kh.getTenKH());
            // Cột 3: Giới Tính
            row.createCell(3).setCellValue(kh.getGioiTinh());
            
            // Cột 4: Ngày Sinh (Định dạng kiểu Date)
            Cell cellNgaySinh = row.createCell(4);
            if (kh.getNgaySinh() != null) {
                cellNgaySinh.setCellValue(kh.getNgaySinh());
                cellNgaySinh.setCellStyle(dateStyle);
            } else {
                cellNgaySinh.setCellValue("");
            }
            
            // Cột 5: Số Điện Thoại
            row.createCell(5).setCellValue(kh.getSoDienThoai());
            // Cột 6: Email
            row.createCell(6).setCellValue(kh.getEmail() != null ? kh.getEmail() : "");

            index++;
            }
            
            for (int i = 0; i < header.length; i++) {
                sheet.autoSizeColumn(i);//// Tự động điều chỉnh độ rộng cột
            }
            w.write(fo);// Ghi Workbook xuống file
            JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xuất Excel", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void btnthoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthoatActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnthoatActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        if (khSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng " + khSelected.getTenKH() + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (chon != JOptionPane.YES_OPTION) return;

        int n = new KhachHangDao().delete(khSelected.getMaKH());
        if (n > 0) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loaddatatable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        if (khSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần cập nhật!");
            return;
        }

        String tenkh = txttkh.getText().trim();
        if (tenkh.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ và tên không được để trống!");
            txttkh.requestFocus();
            return;
        }

        String gioitinh = (String) cbogt.getSelectedItem();
        if (gioitinh == null || gioitinh.startsWith("--")) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn giới tính!");
            return;
        }

        Date ngaysinh = jdcns.getDate();
        if (ngaysinh == null) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn ngày sinh!");
            return;
        }

        String dienthoai = txtsdt.getText().trim();
        String regexPhone = "^(03|05|07|08|09)\\d{8}$";
        if (dienthoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            txtsdt.requestFocus();
            return;
        }
        if (!dienthoai.matches(regexPhone)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!");
            txtsdt.requestFocus();
            return;
        }
        
        String email = txtemal.getText().trim();
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.isEmpty() && !email.matches(regexEmail)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!");
            txtemal.requestFocus();
            return;
        }

        KhachHang kh = new KhachHang(khSelected.getMaKH(), tenkh, gioitinh, ngaysinh, dienthoai, email);
        int n = new KhachHangDao().update(kh);

        if (n > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loaddatatable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void txttkhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttkhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttkhActionPerformed

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        txttimkiem.setText(""); // Xóa nội dung ô tìm kiếm
        loaddatatable();        // Load lại toàn bộ danh sách khách hàng lên bảng
        clearForm();            // Xóa form nhập liệu và hủy chọn trên bảng

        // Thiết lập lại trạng thái ban đầu cho các nút bấm
        btnthem.setEnabled(true);
        btnluu.setEnabled(false);
        btncapnhat.setEnabled(false);
        btnxoa.setEnabled(false);
        btntimkiem.setEnabled(true);
        btnthoat.setEnabled(true);
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void btnluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnluuActionPerformed
        String makh = txtmkh.getText().trim();
        if (makh.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không được để trống!");
            txtmkh.requestFocus();
            return;
        }

        String tenkh = txttkh.getText().trim();
        if (tenkh.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ và tên không được để trống!");
            txttkh.requestFocus();
            return;
        }

        String gioitinh = (String) cbogt.getSelectedItem();
        if (gioitinh == null || gioitinh.startsWith("--")) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn giới tính!");
            return;
        }

        Date ngaysinh = jdcns.getDate();
        if (ngaysinh == null) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn ngày sinh!");
            return;
        }

        String dienthoai = txtsdt.getText().trim();
        String regexPhone = "^(03|05|07|08|09)\\d{8}$";
        if (dienthoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            txtsdt.requestFocus();
            return;
        }
        if (!dienthoai.matches(regexPhone)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! (Phải gồm 10 số và bắt đầu bằng 03, 05, 07, 08, 09)");
            txtsdt.requestFocus();
            return;
        }

        String email = txtemal.getText().trim();
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.isEmpty() && !email.matches(regexEmail)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ! (Ví dụ: nguyenvana@gmail.com)");
            txtemal.requestFocus();
            return;
        }

        KhachHang kh = new KhachHang(makh, tenkh, gioitinh, ngaysinh, dienthoai, email);
        int n = new KhachHangDao().insert(kh);

        if (n > 0) {
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
            loaddatatable();
            clearForm();
        } else if (n == -2) { // Bắt lỗi trùng khóa chính
            JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!");
            txtmkh.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
        }
    }//GEN-LAST:event_btnluuActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        clearForm();
        setEditableFields(true);

        btnluu.setEnabled(true);
        btncapnhat.setEnabled(false);
        btnxoa.setEnabled(false);

        txtmkh.requestFocus();

    }//GEN-LAST:event_btnthemActionPerformed

    private void btnxuatexcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxuatexcelActionPerformed
        if (listKH == null || listKH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Khong co du lieu");
            return;
        }
        ExportDataToExcel(listKH);
    }//GEN-LAST:event_btnxuatexcelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnlammoi;
    private javax.swing.JButton btnluu;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnthoat;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JButton btnxuatexcel;
    private javax.swing.JComboBox<String> cbogt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcns;
    private javax.swing.JTable tablekh;
    private javax.swing.JTextField txtemal;
    private javax.swing.JTextField txtmkh;
    private javax.swing.JTextField txtsdt;
    private javax.swing.JTextField txttimkiem;
    private javax.swing.JTextField txttkh;
    // End of variables declaration//GEN-END:variables
}
