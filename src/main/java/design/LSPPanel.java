
package design;
import DAO.loaiSPDao;
import java.awt.Color;
import model.LoaiSP;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel; 
public class LSPPanel extends javax.swing.JPanel {
    ArrayList<LoaiSP> listloai = new ArrayList<>();
    DefaultTableModel tablemodel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; 
        }
    };
    LoaiSP loaiSelected = null;
    
    public LSPPanel() {
    initComponents();
    jPanel1.setLayout(new java.awt.BorderLayout());
    SPPanel sp = new SPPanel();
    jPanel1.removeAll(); 
    jPanel1.add(sp, java.awt.BorderLayout.CENTER);
    jPanel1.revalidate();
    jPanel1.repaint();
        
     tblloaisp.setModel(tablemodel);
     inittable();
     loaddatatable();
     clearForm();    
    }
 public void setEditableFields(boolean editable) {
        txtmaloai.setEnabled(editable);
        txttenloaisp.setEnabled(editable);
        txtmota.setEnabled(editable);
    }
        public void inittable() {
        String[] header = new String[]{"Mã Loại SP", "Tên Loại SP", "Mô Tả"};
        tablemodel.setColumnIdentifiers(header);
        tblloaisp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblloaisp.getSelectionModel().addListSelectionListener((e) -> {
            int row = tblloaisp.getSelectedRow();
            if (row >= 0 && row < listloai.size()) {
                loaiSelected = listloai.get(row);
                hienthithongtin();
            } else {
                loaiSelected = null;
            }
        });
        jScrollPane1.getViewport().setBackground(Color.white);
    }
        public void loaddatatable() {
        String keyword = this.txttimkiem.getText().trim();     
           String maloai = "";
        listloai = new loaiSPDao().getAll(maloai, keyword);
        tablemodel.setRowCount(0);
        for (LoaiSP sp : listloai) {
            tablemodel.addRow(new Object[]{
                sp.getMaLoaiSP(), 
                sp.getTenLoaiSP(), 
                sp.getMoTa()
            });
        }
        System.out.println(listloai.size());
    }
       public void hienthithongtin() {
        if (loaiSelected != null) {
            txtmaloai.setText(loaiSelected.getMaLoaiSP());
            txttenloaisp.setText(loaiSelected.getTenLoaiSP());
            txtmota.setText(loaiSelected.getMoTa());

            setEditableFields(true);
            txtmaloai.setEditable(false);
            btnsua.setEnabled(true);
            btnxoa.setEnabled(true);
        }
    }
   public void clearForm() {
        txtmaloai.setText("");
        txttenloaisp.setText("");
        txtmota.setText("");
        tblloaisp.clearSelection();
        loaiSelected = null;
        setEditableFields(true);
        btnsua.setEnabled(false);
        btnxoa.setEnabled(false);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblloaisp = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txttimkiem = new javax.swing.JTextField();
        btntimkiem = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtmota = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txttenloaisp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtmaloai = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1074, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 255, 255));
        jLabel2.setText("Quản Lý Loại Sản Phẩm");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sach Loai Sản Phảm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(51, 51, 51))); // NOI18N

        tblloaisp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Loại SP", "Tên Loại SP", "Mô Tả"
            }
        ));
        jScrollPane1.setViewportView(tblloaisp);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 255, 255));
        jLabel5.setText("Tìm Tên/Mã Loại SP :");

        btntimkiem.setBackground(new java.awt.Color(51, 255, 255));
        btntimkiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btntimkiem.setForeground(new java.awt.Color(255, 255, 255));
        btntimkiem.setText("Search");
        btntimkiem.addActionListener(this::btntimkiemActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(btntimkiem))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntimkiem))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Loại Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(51, 51, 51))); // NOI18N

        txtmota.setColumns(20);
        txtmota.setRows(5);
        jScrollPane2.setViewportView(txtmota);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 255, 255));
        jLabel4.setText("Mô Tả :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 255, 255));
        jLabel3.setText("Tên Loại SP :");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 255));
        jLabel1.setText("Mã Loại SP :");

        btnthem.setBackground(new java.awt.Color(51, 255, 255));
        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnthem.setForeground(new java.awt.Color(255, 255, 255));
        btnthem.setText("Thêm ");
        btnthem.addActionListener(this::btnthemActionPerformed);

        btnsua.setBackground(new java.awt.Color(51, 255, 255));
        btnsua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnsua.setForeground(new java.awt.Color(255, 255, 255));
        btnsua.setText("Sửa ");
        btnsua.addActionListener(this::btnsuaActionPerformed);

        btnxoa.setBackground(new java.awt.Color(51, 255, 255));
        btnxoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnxoa.setForeground(new java.awt.Color(255, 255, 255));
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(this::btnxoaActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(jLabel1))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel3)))
                    .addComponent(btnthem))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txttenloaisp, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtmaloai, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(btnsua)
                        .addGap(99, 99, 99)
                        .addComponent(btnxoa)
                        .addGap(51, 51, 51))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtmaloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttenloaisp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel4))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem)
                    .addComponent(btnsua)
                    .addComponent(btnxoa))
                .addGap(49, 49, 49))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(388, 388, 388)
                        .addComponent(jLabel2)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Loại sản phẩm", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1074, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
    String ma =txtmaloai.getText().trim();
    String ten = txttenloaisp.getText().trim();
    String mota = txtmota.getText().trim();

    if (ma.isEmpty() ||ten.isEmpty() || mota.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ các dữ liệu!");
        return;
    }
    
    try {
        LoaiSP lsp = new LoaiSP(); 
        lsp.setMaLoaiSP(ma);
        lsp.setTenLoaiSP(ten);
        lsp.setMoTa(mota);

        loaiSPDao dao = new loaiSPDao();
        int kq = dao.insert(lsp); 

        if (kq > 0) {
            JOptionPane.showMessageDialog(this, "Thêm mới loại sản phẩm thành công!");
            loaddatatable();    
            clearForm();         
        } else {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
   if (loaiSelected == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn loại sản phẩm cần cập nhật trên bảng!");
        return;
    }

    String ma = loaiSelected.getMaLoaiSP(); 
    String ten = txttenloaisp.getText().trim();    
    String mota = txtmota.getText().trim();   

    if (ma.isEmpty() || ten.isEmpty() || mota.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ các dữ liệu!");
        return;
    }

    try {
        LoaiSP lsp = new LoaiSP(ma, ten, mota);
        loaiSPDao dao = new loaiSPDao();
        int kq = dao.update(lsp);

        if (kq > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật loại sản phẩm thành công!");
            loaddatatable();    
            clearForm();        
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật loại sản phẩm thất bại!");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
      if (loaiSelected == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn loại sản phẩm cần xóa trên bảng!");
        return;
    }
    int confirm = JOptionPane.showConfirmDialog(this,"Bạn có chắc chắn muốn xóa loại sản phẩm này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION  );

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            String ma = loaiSelected.getMaLoaiSP();
            loaiSPDao dao = new loaiSPDao();
            int kq = dao.delete(ma); 

            if (kq > 0) {
                JOptionPane.showMessageDialog(this, "Xóa loại sản phẩm thành công!");
                loaddatatable();
                clearForm();
                loaiSelected = null;
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btntimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemActionPerformed
       loaddatatable();
    }//GEN-LAST:event_btntimkiemActionPerformed

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new LSPPanel().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblloaisp;
    private javax.swing.JTextField txtmaloai;
    private javax.swing.JTextArea txtmota;
    private javax.swing.JTextField txttenloaisp;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
