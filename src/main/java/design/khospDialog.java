package design;

import DAO.khoDao;
import DAO.sanphamDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.SanPham;
import model.khoSP;

public class khospDialog extends java.awt.Dialog {
    private final khoSP k;
    private ArrayList<SanPham> listSanPham = new ArrayList<>();
    private boolean daLuuThanhCong;

    public khospDialog(java.awt.Frame parent, boolean modal, khoSP khoSelect) {
        super(parent, modal);
        initComponents();
        setTitle("Tạo kho sản phẩm");
        k = khoSelect;
        loaddatatocombobox();
        loadDataToForm();
    }

    public void loaddatatocombobox() {
        listSanPham = new sanphamDAO().getAll("", "");
        MspCbo.removeAllItems();
        MspCbo.addItem("-- Chọn sản phẩm --");
        for (SanPham sp : listSanPham) {
            MspCbo.addItem(sp.getMaSP() + " - " + sp.getTenSP());
        }
        btnluu.setEnabled(!listSanPham.isEmpty());
        if (listSanPham.isEmpty()) {
            MspCbo.setToolTipText("Chưa có sản phẩm. Hãy thêm sản phẩm hoặc kiểm tra kết nối database.");
        }
    }

    public void loadDataToForm() {
        if (k == null) return;
        for (int i = 0; i < listSanPham.size(); i++) {
            if (k.getMaSP().equals(listSanPham.get(i).getMaSP())) {
                MspCbo.setSelectedIndex(i + 1);
                break;
            }
        }
    }

    public boolean isDaLuuThanhCong() {
        return daLuuThanhCong;
    }

    int luuKho() {
        int index = MspCbo.getSelectedIndex() - 1;
        if (index < 0 || index >= listSanPham.size()) {
            throw new IllegalArgumentException("Vui lòng chọn sản phẩm cần tạo kho!");
        }
        khoSP kho = new khoSP();
        kho.setMaSP(listSanPham.get(index).getMaSP());
        kho.setTonKho(0);
        int result = new khoDao().insert(kho);
        daLuuThanhCong = result > 0;
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnThoat = new javax.swing.JButton();
        btnluu = new javax.swing.JButton();
        MspCbo = new javax.swing.JComboBox<>();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 255));
        jLabel1.setText("Sản phẩm");

        btnThoat.setBackground(new java.awt.Color(51, 255, 255));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        btnluu.setBackground(new java.awt.Color(51, 255, 255));
        btnluu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnluu.setForeground(new java.awt.Color(255, 255, 255));
        btnluu.setText("Lưu");
        btnluu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnluuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btnluu, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(MspCbo, 0, 250, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(MspCbo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnluu)
                    .addComponent(btnThoat))
                .addGap(107, 107, 107))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnluuActionPerformed
        btnluu.setEnabled(false);
        try {
            int result = luuKho();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Tạo kho thành công! Tồn kho ban đầu là 0.");
                dispose();
            } else if (result == -2) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã có kho. Dùng chức năng nhập kho để bổ sung số lượng!");
            } else {
                JOptionPane.showMessageDialog(this, "Tạo kho thất bại. Vui lòng kiểm tra kết nối database!");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tạo kho. Vui lòng kiểm tra kết nối database!");
        } finally {
            if (!daLuuThanhCong) btnluu.setEnabled(!listSanPham.isEmpty());
        }
    }//GEN-LAST:event_btnluuActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            khospDialog dialog = new khospDialog(null, true, null);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) { System.exit(0); }
            });
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> MspCbo;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnluu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
