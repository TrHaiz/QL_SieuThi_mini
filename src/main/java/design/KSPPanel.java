
package design;
import DAO.khoDAO;
import model.khoSP;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    private JComboBox<String> cbbloai;
    private JTable tblsanpham;
    private JTextField txtgianhap;
    private JTextField txtgiaban;
    private JTextField txtloai;
    private JTextField txtmaSP; 
    private JTextField txtsoluong;
    private JTextField txtten;
    private JTextField txttimkiem;
    
    public KSPPanel() {
        initComponents();
        this.cbbloai.setModel(this.cbomodelLoai);
        this.tblsanpham.setModel(this.tablemodel);
        this.loaddatatocombobox();
        this.inittable();
        this.loaddatatable();
        this.clearForm();
    }
    
    public void setEditableFields(boolean editable) {
        this.txtmaSP.setEnabled(editable);
        this.txtmaSP.setEditable(editable);
        this.txtten.setEnabled(editable);
        this.txtloai.setEnabled(editable);
        this.txtsoluong.setEnabled(editable);
        this.txtgianhap.setEnabled(editable);
        this.txtgiaban.setEnabled(editable);
    }

    public void loaddatatocombobox() {
        this.cbomodelLoai.removeAllElements();
        this.cbomodelLoai.addElement("-- Tất cả loại --");
        ArrayList<String> listLoai = new khoDAO().getAllLoai();
        for (String loai : listLoai) {
            this.cbomodelLoai.addElement(loai);
        }
    }

    public void inittable() {
        String[] header = new String[]{"Mã SP", "Tên SP", "Loại SP", "Số Lượng", "Giá Nhập", "Giá Bán"};
        this.tablemodel.setColumnIdentifiers(header);
        this.tblsanpham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tblsanpham.getSelectionModel().addListSelectionListener((e) -> {
            int row = this.tblsanpham.getSelectedRow();
            if (row >= 0 && row < this.listSanPham.size()) {
                this.sanPhamSelected = this.listSanPham.get(row);
                this.hienthithongtin();
            } else {
                this.sanPhamSelected = null;
            }
        });
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
        this.listSanPham = new khoDAO().getAll(loaiChon, keyword);
        this.tablemodel.setRowCount(0);
        for (khoSP sp : this.listSanPham) {
            this.tablemodel.addRow(new Object[]{
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
        if (this.sanPhamSelected != null) {
            this.txtmaSP.setText(this.sanPhamSelected.getMaSP());
            this.txtten.setText(this.sanPhamSelected.getTenSP());
            this.txtloai.setText(this.sanPhamSelected.getLoaiSP());
            this.txtsoluong.setText(String.valueOf(this.sanPhamSelected.getSoLuong()));
            this.txtgianhap.setText(String.valueOf(this.sanPhamSelected.getGiaNhap()));
            this.txtgiaban.setText(String.valueOf(this.sanPhamSelected.getGiaBan()));

            this.setEditableFields(true);
            this.txtmaSP.setEditable(false); // Không cho sửa mã khi đang chọn cập nhật
            this.btncapnhat.setEnabled(true);
            this.btnxoa.setEnabled(true);
        }
    }

    public void clearForm() {
        this.txtmaSP.setText("");
        this.txtten.setText("");
        this.txtloai.setText("");
        this.txtsoluong.setText("");
        this.txtgianhap.setText("");
        this.txtgiaban.setText("");
        this.tblsanpham.clearSelection();
        this.sanPhamSelected = null;
        this.setEditableFields(true); // Cho phép nhập mới khi bấm Thêm
        this.btncapnhat.setEnabled(false);
        this.btnxoa.setEnabled(false);
    }

    private void initComponents() {
        // Giao diện đã được thiết kế sẵn qua NetBeans Form Designer, các sự kiện nút bấm gắn kết như sau:
        btnsearch.addActionListener(e -> loaddatatable());
        txttimkiem.addActionListener(e -> loaddatatable());
        cbbloai.addActionListener(e -> loaddatatable());
        
        btnthem.addActionListener(e -> btnthemActionPerformed(e));
        btncapnhat.addActionListener(e -> btncapnhatActionPerformed(e));
        btnxoa.addActionListener(e -> btnxoaActionPerformed(e));
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
        jTextField1 = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
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

        jPanel3.setBackground(new java.awt.Color(0, 102, 0));

        btnxuatexcel.setForeground(new java.awt.Color(0, 102, 0));
        btnxuatexcel.setText("Xuất Excel");
        btnxuatexcel.addActionListener(this::btnxuatexcelActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnxuatexcel)
                .addGap(48, 48, 48))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnxuatexcel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 0));
        jLabel9.setText("Quản Lý Sản Phẩm");

        jTextField1.setForeground(new java.awt.Color(255, 102, 102));
        jTextField1.setText("Nhập mã/Tên");

        btnsearch.setText("Tìm kiếm");
        btnsearch.addActionListener(this::btnsearchActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnsearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsearch)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 102, 0))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 102, 0))); // NOI18N

        jLabel10.setText("Mã SP");

        jLabel11.setText("Tên SP");

        jLabel12.setText("Loại SP");

        jLabel13.setText("Số Lượng");

        jLabel14.setText("Giá Nhập");

        jLabel15.setText("Giá Bán");

        btncapnhat.setBackground(new java.awt.Color(0, 102, 0));
        btncapnhat.setForeground(new java.awt.Color(255, 255, 255));
        btncapnhat.setText("Cập Nhật");
        btncapnhat.addActionListener(this::btncapnhatActionPerformed);

        btnthem.setBackground(new java.awt.Color(0, 102, 0));
        btnthem.setForeground(new java.awt.Color(255, 255, 255));
        btnthem.setText("Thêm");
        btnthem.addActionListener(this::btnthemActionPerformed);

        btnxoa.setBackground(new java.awt.Color(0, 102, 0));
        btnxoa.setForeground(new java.awt.Color(255, 255, 255));
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(this::btnxoaActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel15)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                                .addComponent(jTextField6)
                                .addComponent(jTextField5)
                                .addComponent(jTextField4))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btncapnhat)
                                .addGap(46, 46, 46)
                                .addComponent(btnthem)
                                .addGap(56, 56, 56)
                                .addComponent(btnxoa)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncapnhat)
                    .addComponent(btnthem)
                    .addComponent(btnxoa)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
       if (this.sanPhamSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần cập nhật trên bảng!");
            return;
        }

        String ma = this.sanPhamSelected.getMaSP();
        String nma = jTextField2.getText().trim();
        String ten = jTextField3.getText().trim();
        String loai = jTextField4.getText().trim();

        try {
            int soluong = Integer.parseInt(jTextField5.getText().trim());
            double gianhap = Double.parseDouble(jTextField6.getText().trim());
            double giaban = Double.parseDouble(jTextField7.getText().trim());
            
            khoDAO dao = new khoDAO();
            khoSP sp = new khoSP(ma, ten, loai, soluong, gianhap, giaban);
            int kq = dao.update(sp);

            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                this.loaddatatable();
                this.loaddatatocombobox();
                this.clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra lại kiểu dữ liệu số!");
        }
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
      if (this.sanPhamSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần cập nhật trên bảng!");
            return;
        }

        String ma = this.sanPhamSelected.getMaSP();
        String nma = jTextField2.getText().trim();
        String ten = jTextField3.getText().trim();
        String loai = jTextField4.getText().trim();

        try {
            int soluong = Integer.parseInt(jTextField5.getText().trim());
            double gianhap = Double.parseDouble(jTextField6.getText().trim());
            double giaban = Double.parseDouble(jTextField7.getText().trim());

             khoDAO dao = new khoDAO();
            khoSP sp = new khoSP(ma, ten, loai, soluong, gianhap, giaban);
            int kq = dao.update(sp);

            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                this.loaddatatable();
                this.loaddatatocombobox();
                this.clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra lại kiểu dữ liệu số!");
        }
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
       if (this.sanPhamSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            khoDAO dao = new khoDAO();
            int kq = dao.delete(this.sanPhamSelected.getMaSP());
            if (kq > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                this.loaddatatable();
                this.loaddatatocombobox();
                this.clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsearchActionPerformed

    private void btnxuatexcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxuatexcelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnxuatexcelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnsearch;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JButton btnxuatexcel;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
