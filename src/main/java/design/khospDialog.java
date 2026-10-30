
package design;
import DAO.khoDao;
import java.awt.Color;
import model.khoSP;
import java.util.ArrayList;
import javax.swing.*;
public class khospDialog extends java.awt.Dialog {
private khoSP k; 
    public khospDialog(java.awt.Frame parent, boolean modal,khoSP khoSelect) {
        super(parent, modal);
        initComponents();
        this.k = khoSelect;
        if (k != null) {
            loadDataToForm();
        }
    }
  public void loadDataToForm() {
        txtmasp.setText(k.getMaSP());
        txtmasp.setEditable(false); 
        
        txttensp.setText(k.getTenSP());
        txttensp.requestFocus();
        
        txtloaisp.setText(k.getTenLoaiSP()); 
        txttenncc.setText(k.getTenNCC());
        
        txttonkho.setText(String.valueOf(k.getTonKho()));
        
         jDateChooser1.setDate(k.getNgayNhap()); 
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtmasp = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txttensp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtloaisp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txttenncc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txttonkho = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        btnlammoi = new javax.swing.JButton();
        btnluu = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 255));
        jLabel1.setText("Mã SP");
        jPanel1.add(jLabel1);
        jPanel1.add(txtmasp);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 255, 255));
        jLabel2.setText("Tên SP");
        jPanel1.add(jLabel2);
        jPanel1.add(txttensp);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 255, 255));
        jLabel3.setText("Loại SP");
        jPanel1.add(jLabel3);

        txtloaisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtloaispActionPerformed(evt);
            }
        });
        jPanel1.add(txtloaisp);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 255, 255));
        jLabel4.setText("Tên NCC");
        jPanel1.add(jLabel4);

        txttenncc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttennccActionPerformed(evt);
            }
        });
        jPanel1.add(txttenncc);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 255, 255));
        jLabel5.setText("Tồn Kho");
        jPanel1.add(jLabel5);
        jPanel1.add(txttonkho);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 255, 255));
        jLabel6.setText("Ngày Nhập");
        jPanel1.add(jLabel6);
        jPanel1.add(jDateChooser1);

        btnlammoi.setBackground(new java.awt.Color(51, 255, 255));
        btnlammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnlammoi.setForeground(new java.awt.Color(255, 255, 255));
        btnlammoi.setText("Làm Mới");
        btnlammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlammoiActionPerformed(evt);
            }
        });
        jPanel1.add(btnlammoi);

        btnluu.setBackground(new java.awt.Color(51, 255, 255));
        btnluu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnluu.setForeground(new java.awt.Color(255, 255, 255));
        btnluu.setText("Lưu");
        btnluu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnluuActionPerformed(evt);
            }
        });
        jPanel1.add(btnluu);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

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

        txtmasp.setEditable(true);

        txtmasp.requestFocusInWindow();
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void btnluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnluuActionPerformed
        String masp = txtmasp.getText().trim();
        if (masp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm không được để trống");
            txtmasp.requestFocusInWindow();
            return;
        }
        String tensp = txttensp.getText().trim();
        if (tensp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống");
            txttensp.requestFocusInWindow();
            return;
        }
        String loaisp = txtloaisp.getText().trim();
        String tenncc = txttenncc.getText().trim();
        String tonkhoStr = txttonkho.getText().trim();

        if (tonkhoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số lượng tồn kho không được để trống");
            txttonkho.requestFocusInWindow();
            return;
        }

        int tonkho;
        try {
            tonkho = Integer.parseInt(tonkhoStr);
            if (tonkho < 0) {
                JOptionPane.showMessageDialog(this, "Tồn kho phải lớn hơn hoặc bằng 0");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tồn kho phải là số nguyên");
            return;
        }
     java.util.Date ngayNhap = jDateChooser1.getDate();
    if (ngayNhap == null) {
    JOptionPane.showMessageDialog(this, "Ngày nhập không được để trống!");
    jDateChooser1.requestFocusInWindow();
    return;
}
        int n;
        khoDao dao = new khoDao(); 

        if (k== null) {
            khoSP newItem = new khoSP();
            n = dao.insert(newItem);
            if (n == -2) {
                JOptionPane.showMessageDialog(this, "Mã sản phẩm bị trùng");
                txtmasp.requestFocusInWindow();
                return;
            }
            if (n == -1) {
                JOptionPane.showMessageDialog(this, "Thêm mới thất bại");
                return;
            }
            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Thêm mới thành công");
                dispose(); // Đóng dialog sau khi lưu thành công
            }
        }
    }//GEN-LAST:event_btnluuActionPerformed

    private void txttennccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttennccActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttennccActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                khospDialog dialog = new khospDialog(new java.awt.Frame(), true,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlammoi;
    private javax.swing.JButton btnluu;
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
