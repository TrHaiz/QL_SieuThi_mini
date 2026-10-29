
package design;
import DAO.khoDao;
import java.awt.Color;
import model.khoSP;
import java.util.ArrayList;
import javax.swing.*;

public class KhoDialog extends javax.swing.JDialog {
private khoSP k; // Biến lưu đối tượng kho khi ở chế độ Sửa

    public KhoDialog(java.awt.Frame parent, boolean modal, khoSP khoSelect) {
        super(parent, modal);
        initComponents();
        
        this.k = khoSelect;
        if (k != null) {
            loadDataToForm();
        }
    }

    public void loadDataToForm() {
        txtmasp.setText(k.getMaSP());
        txtmasp.setEditable(false); // Khóa mã sản phẩm lại vì là khóa chính, không cho sửa
        
        txttensp.setText(k.getTenSP());
        txttensp.requestFocus();
        
        txtloaisp.setText(k.getTenLoaiSP()); // Hoặc cboLoaiSP nếu bạn dùng ComboBox
        txttenncc.setText(k.getTenNCC()); // Hoặc cboNCC
        
        txttonkho.setText(String.valueOf(k.getTonKho()));
        
         jDateChooser1.setDate(k.getNgayNhap()); // Nếu dùng JDateChooser cho ngày tháng
    }

    public KhoDialog() {
        initComponents();
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txttensp = new javax.swing.JTextField();
        txtloaisp = new javax.swing.JTextField();
        txttenncc = new javax.swing.JTextField();
        txtmasp = new javax.swing.JTextField();
        txttonkho = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnluu = new javax.swing.JButton();
        btnlammoi = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        btnxoa1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtloaisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtloaispActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 255));
        jLabel1.setText("Mã SP");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 255, 255));
        jLabel2.setText("Tên SP");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 255, 255));
        jLabel3.setText("Loại SP");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 255, 255));
        jLabel4.setText("Tên NCC");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 255, 255));
        jLabel5.setText("Tồn Kho");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 255, 255));
        jLabel6.setText("Ngày Nhập");

        btnluu.setBackground(new java.awt.Color(51, 255, 255));
        btnluu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnluu.setForeground(new java.awt.Color(255, 255, 255));
        btnluu.setText("Lưu");
        btnluu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnluuActionPerformed(evt);
            }
        });

        btnlammoi.setBackground(new java.awt.Color(51, 255, 255));
        btnlammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnlammoi.setForeground(new java.awt.Color(255, 255, 255));
        btnlammoi.setText("Làm Mới");
        btnlammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlammoiActionPerformed(evt);
            }
        });

        btnxoa1.setBackground(new java.awt.Color(51, 255, 255));
        btnxoa1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnxoa1.setForeground(new java.awt.Color(255, 255, 255));
        btnxoa1.setText("Hủy");
        btnxoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoa1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txttonkho)
                            .addComponent(txtloaisp, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txttenncc, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtmasp)
                            .addComponent(txttensp)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(btnlammoi)
                        .addGap(38, 38, 38)
                        .addComponent(btnluu)
                        .addGap(53, 53, 53)
                        .addComponent(btnxoa1)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtmasp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtloaisp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttenncc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttonkho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnxoa1)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnlammoi)
                            .addComponent(btnluu))
                        .addContainerGap(30, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtloaispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtloaispActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtloaispActionPerformed

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        txtmasp.setText("");
        txttensp.setText("");
        txtloaisp.setText("");
        txttenncc.setText("");
        txttonkho.setText("");
        jDateChooser1.setDate(null); 
        
        // Mở lại quyền sửa ô Mã SP (nếu trước đó bị khóa ở chế độ Sửa)
        txtmasp.setEditable(true);
        
        // Đưa con trỏ chuột về ô Mã SP để nhập mới từ đầu
        txtmasp.requestFocusInWindow();
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void btnluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnluuActionPerformed
String masp = txtmasp.getText().trim();
        if (masp.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Mã sản phẩm không được để trống");
            txtmasp.requestFocusInWindow();
            return;
        }

        String tensp = txttensp.getText().trim();
        if (tensp.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Tên sản phẩm không được để trống");
            txttensp.requestFocusInWindow();
            return;
        }

        String loaisp = txtloaisp.getText().trim();
        String tenncc = txttenncc.getText().trim();
        String tonkhoStr = txttonkho.getText().trim();

        if (tonkhoStr.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Số lượng tồn kho không được để trống");
            txttonkho.requestFocusInWindow();
            return;
        }

        int tonkho;
        try {
            tonkho = Integer.parseInt(tonkhoStr);
            if (tonkho < 0) {
                JOptionPane.showMessageDialog(rootPane, "Tồn kho phải lớn hơn hoặc bằng 0");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Tồn kho phải là số nguyên");
            return;
        }

        // Tạo đối tượng Kho (tùy thuộc vào constructor trong class Entity Kho của nhóm bạn)
        // Ví dụ: Kho(maSP, tenSP, loaiSP, tenNCC, tonKho)
        khoSP k;
        if (k== null) {
            k = new khoSP();
        } else {
            k = new  khoSP();
        }

        int n;
        khoDao dao = new khoDao(); // Gọi DAO của phần kho
        
        if (k== null) {
            // Chế độ THÊM
            n = dao.insert(k);
            if (n == -2) {
                JOptionPane.showMessageDialog(rootPane, "Mã sản phẩm bị trùng");
                txtmasp.requestFocusInWindow();
                return;
            }
            if (n == -1) {
                JOptionPane.showMessageDialog(rootPane, "Thêm mới thất bại");
                return;
            }
            if (n > 0) {
                JOptionPane.showMessageDialog(rootPane, "Thêm mới thành công");
                dispose(); // Đóng dialog sau khi lưu thành công
            }
        } 
    }//GEN-LAST:event_btnluuActionPerformed

    private void btnxoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoa1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnxoa1ActionPerformed

    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhoDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlammoi;
    private javax.swing.JButton btnluu;
    private javax.swing.JButton btnxoa1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtloaisp;
    private javax.swing.JTextField txtmasp;
    private javax.swing.JTextField txttenncc;
    private javax.swing.JTextField txttensp;
    private javax.swing.JTextField txttonkho;
    // End of variables declaration//GEN-END:variables
}
