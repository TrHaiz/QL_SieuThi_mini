
package design;

import DAO.KhachHangDao;
import DAO.NCCDao;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import model.NCC;


public class NCCPanel extends javax.swing.JPanel {
     ArrayList<NCC> listcc = new ArrayList<>();
     DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Khóa không cho sửa trực tiếp trên bảng
        }
    };
     NCC ccSelected = null;
    
    public NCCPanel() {
        initComponents();
        initComponents();
        tablencc.setModel(tableModel);
        inittable();
        loaddatatable(); // Load dữ liệu lên bảng khi khởi tạo
        
        setEditableFields(false); // Khóa các ô nhập liệu khi vừa load form
        btnsave.setEnabled(false);
        btnupdate.setEnabled(false);
        btndelete.setEnabled(false);
        btnadded.setEnabled(true);
        btnsearch.setEnabled(true);
        btnexit.setEnabled(true);
    }
    
     public void setEditableFields(boolean editable) {
        txtmncc.setEnabled(editable);
        txtmncc.setEditable(editable);
        txttncc.setEnabled(editable);
        txtdc.setEnabled(editable);
        txtsodt.setEnabled(editable);
        txtemail.setEnabled(editable);
    }
    
     public void inittable() {
        String header[] = {
            "Mã Nhà Cung Cấp", "Tên Nhà Cung Cấp", "Địa chỉ", "Email", "Số Điện Thoại"
        };
        tableModel.setColumnIdentifiers(header);
        tablencc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablencc.getSelectionModel().addListSelectionListener(e -> {
            int row = tablencc.getSelectedRow();
            if (row >= 0) {
                ccSelected = listcc.get(row);
                hienthithongtin();
            } else {
                ccSelected = null;
            }
        });
        jScrollPane1.getViewport().setBackground(Color.white); //màu nền cho phần trống bên trong khung cuộn thành màu trắng
    }
     
     public void loaddatatable() {
        String keyword = txtsearch.getText().trim(); // Ô nhập từ khóa tìm kiếm (mã, tên hoặc SĐT)
        listcc = new NCCDao().getAll(keyword);
        tableModel.setRowCount(0);

        for (NCC cc : listcc) {
            tableModel.addRow(new Object[]{
                cc.getMaNCC(),
                cc.getTenNCC(),
                cc.getDiachi(),
                cc.getEmail() != null ? cc.getEmail() : "",
                cc.getSdt()
            });
        }
    }
     
     public void hienthithongtin() {
        if (ccSelected == null) return;

        txtmncc.setText(ccSelected.getMaNCC());
        txttncc.setText(ccSelected.getTenNCC());
        txtdc.setText(ccSelected.getDiachi());
        txtsodt.setText(ccSelected.getSdt());
        txtemail.setText(ccSelected.getEmail() != null ? ccSelected.getEmail() : "");

        setEditableFields(true);
        txtmncc.setEditable(false); // Khóa mã khách hàng khi đang cập nhật

        btnsave.setEnabled(false);
        btnupdate.setEnabled(true);
        btndelete.setEnabled(true);
    }
     
     public void clearForm() {
        txtmncc.setText("");
        txttncc.setText("");
        txtdc.setText("");
        txtsodt.setText("");
        txtemail.setText("");
        tablencc.clearSelection();
        ccSelected = null;

        setEditableFields(false);

        btnsave.setEnabled(false);
        btnupdate.setEnabled(false);
        btndelete.setEnabled(false);
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnadded = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        btnlm = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtdc = new javax.swing.JTextField();
        txtsodt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txttncc = new javax.swing.JTextField();
        btnupdate = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnexit = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtmncc = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtsearch = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablencc = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1029, 729));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setText("Quản lý nhà cung cấp");
        jLabel1.setVerifyInputWhenFocusTarget(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(350, 350));
        jPanel1.setPreferredSize(new java.awt.Dimension(350, 350));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 204));
        jLabel9.setText("Địa chỉ:");

        btnadded.setBackground(new java.awt.Color(0, 204, 204));
        btnadded.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnadded.setForeground(new java.awt.Color(255, 255, 255));
        btnadded.setText("Thêm");
        btnadded.addActionListener(this::btnaddedActionPerformed);

        btnsave.setBackground(new java.awt.Color(0, 204, 204));
        btnsave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnsave.setForeground(new java.awt.Color(255, 255, 255));
        btnsave.setText("Lưu");
        btnsave.addActionListener(this::btnsaveActionPerformed);

        btnlm.setBackground(new java.awt.Color(0, 204, 204));
        btnlm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnlm.setForeground(new java.awt.Color(255, 255, 255));
        btnlm.setText("Làm mới");
        btnlm.addActionListener(this::btnlmActionPerformed);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 204));
        jLabel10.setText("Email:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 204));
        jLabel5.setText("Tên nhà cung cấp:");

        txttncc.addActionListener(this::txttnccActionPerformed);

        btnupdate.setBackground(new java.awt.Color(0, 204, 204));
        btnupdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnupdate.setForeground(new java.awt.Color(255, 255, 255));
        btnupdate.setText("Cập nhật");
        btnupdate.addActionListener(this::btnupdateActionPerformed);

        btndelete.setBackground(new java.awt.Color(0, 204, 204));
        btndelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btndelete.setForeground(new java.awt.Color(255, 255, 255));
        btndelete.setText("Xóa");
        btndelete.addActionListener(this::btndeleteActionPerformed);

        btnexit.setBackground(new java.awt.Color(0, 204, 204));
        btnexit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnexit.setForeground(new java.awt.Color(255, 255, 255));
        btnexit.setText("Thoát");
        btnexit.addActionListener(this::btnexitActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 204));
        jLabel3.setText("Mã nhà cung cấp:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 204, 204));
        jLabel11.setText("Số điện thoại:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addGap(29, 29, 29)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txttncc, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtmncc, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtsodt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtemail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(52, 52, 52)
                            .addComponent(txtdc, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnadded, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnlm, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnupdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(53, 53, 53))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtmncc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttncc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtsodt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnadded, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnlm, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Nhập mã hoặc tên:");
        jLabel2.setVerifyInputWhenFocusTarget(false);

        btnsearch.setBackground(new java.awt.Color(0, 204, 204));
        btnsearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnsearch.setForeground(new java.awt.Color(255, 255, 255));
        btnsearch.setText("Tìm kiếm");
        btnsearch.addActionListener(this::btnsearchActionPerformed);

        tablencc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane1.setViewportView(tablencc);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnsearch)
                .addGap(51, 51, 51))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(117, 117, 117))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddedActionPerformed
        clearForm();
        setEditableFields(true);

        btnsave.setEnabled(true);
        btnupdate.setEnabled(false);
        btndelete.setEnabled(false);

        txtmncc.requestFocus();
    }//GEN-LAST:event_btnaddedActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        String mancc = txtmncc.getText().trim();
        if (mancc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhà cung cấp không được để trống!");
            txtmncc.requestFocus();
            return;
        }

        String tenncc = txttncc.getText().trim();
        if (tenncc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống!");
            txttncc.requestFocus();
            return;
        }

        String dcncc = txtdc.getText().trim();
        if (dcncc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống!");
            txtdc.requestFocus();
            return;
        }
        
        String email = txtemail.getText().trim();
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.isEmpty() && !email.matches(regexEmail)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ! (Ví dụ: nguyenvana@gmail.com)");
            txtemail.requestFocus();
            return;
        }
        
        String dienthoai = txtsodt.getText().trim();
        String regexPhone = "^(03|05|07|08|09)\\d{8}$";
        if (dienthoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            txtsodt.requestFocus();
            return;
        }
        if (!dienthoai.matches(regexPhone)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! ");
            txtsodt.requestFocus();
            return;
        }
        
        NCC cc = new NCC(mancc, tenncc, dcncc, email, dienthoai);
        int n = new NCCDao().insert(cc);

        if (n > 0) {
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
            loaddatatable();
            clearForm();
        } else if (n == -2) { // Bắt lỗi trùng khóa chính
            JOptionPane.showMessageDialog(this, "Mã nhà cung cấp đã tồn tại!");
            txtmncc.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
        }
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnlmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlmActionPerformed
        txtsearch.setText(""); // Xóa nội dung ô tìm kiếm
        loaddatatable();        // Load lại toàn bộ danh sách khách hàng lên bảng
        clearForm();            // Xóa form nhập liệu và hủy chọn trên bảng

        // Thiết lập lại trạng thái ban đầu cho các nút bấm
        btnadded.setEnabled(true);
        btnsave.setEnabled(false);
        btnupdate.setEnabled(false);
        btndelete.setEnabled(false);
        btnsearch.setEnabled(true);
        btnexit.setEnabled(true);
    }//GEN-LAST:event_btnlmActionPerformed

    private void txttnccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttnccActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttnccActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        if (ccSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần cập nhật!");
            return;
        }

        String mancc = txtmncc.getText().trim();
        if (mancc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhà cung cấp không được để trống!");
            txtmncc.requestFocus();
            return;
        }

        String tenncc = txttncc.getText().trim();
        if (tenncc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống!");
            txttncc.requestFocus();
            return;
        }

        String dcncc = txtdc.getText().trim();
        if (dcncc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống!");
            txtdc.requestFocus();
            return;
        }
        
        String email = txtemail.getText().trim();
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.isEmpty() && !email.matches(regexEmail)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ! (Ví dụ: nguyenvana@gmail.com)");
            txtemail.requestFocus();
            return;
        }
        
        String dienthoai = txtsodt.getText().trim();
        String regexPhone = "^(03|05|07|08|09)\\d{8}$";
        if (dienthoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            txtsodt.requestFocus();
            return;
        }
        if (!dienthoai.matches(regexPhone)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! ");
            txtsodt.requestFocus();
            return;
        }

        NCC cc = new NCC(mancc, tenncc, dcncc, email, dienthoai);
        int n = new NCCDao().update(cc);


        if (n > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loaddatatable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        if (ccSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để xóa!");
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhà cung cấp " + ccSelected.getTenNCC() + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (chon != JOptionPane.YES_OPTION) return;

        int n = new NCCDao().delete(ccSelected.getMaNCC());
        if (n > 0) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loaddatatable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnexitActionPerformed

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        loaddatatable();
    }//GEN-LAST:event_btnsearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadded;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnexit;
    private javax.swing.JButton btnlm;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnsearch;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablencc;
    private javax.swing.JTextField txtdc;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtmncc;
    private javax.swing.JTextField txtsearch;
    private javax.swing.JTextField txtsodt;
    private javax.swing.JTextField txttncc;
    // End of variables declaration//GEN-END:variables
}
