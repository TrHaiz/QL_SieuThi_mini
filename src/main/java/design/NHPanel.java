
package design;

import DAO.NCCDao;
import DAO.PhieuNhapDao;
import DAO.khoDao;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import model.ChiTietPhieuNhap;
import model.NCC;
import model.PhieuNhap;
import java.util.Date;
import model.SanPham;
import model.khoSP;

public class NHPanel extends javax.swing.JPanel {
    DefaultComboBoxModel<NCC> cbomodelncc= new DefaultComboBoxModel<>();
    DefaultComboBoxModel<khoSP> cbomodelsp= new DefaultComboBoxModel<>();
    ArrayList<PhieuNhap> listPN = new ArrayList<>();
    ArrayList<ChiTietPhieuNhap> listCTTemp = new ArrayList<>(); // Danh sách chi tiết tạm
    
    DefaultTableModel tableModelPN = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    
    private DefaultTableModel tableModelCT = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    
    private PhieuNhap pnSelected = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public NHPanel() {
        initComponents();
        initTables();
        loadDataToCombobox();
        loadDataToTablePN("");
        setInitialState();
    }
    
    private void initTables() {
        String[] headersPN = {"Mã phiếu", "Ngày nhập", "Nhà cung cấp", "Tổng tiền"};
        tableModelPN.setColumnIdentifiers(headersPN);
        tablephieunhap.setModel(tableModelPN); 
        tablephieunhap.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablephieunhap.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablephieunhap.getSelectedRow();
                if (row >= 0 && row < listPN.size()) {
                    pnSelected = listPN.get(row);
                    hienthithongtin(pnSelected); 
                    loadDataToTableCT(pnSelected.getMaPN());
                    // Đưa danh sách chi tiết vào danh sách tạm để sẵn sàng cho việc Sửa/Xóa sản phẩm chi tiết
                    listCTTemp = new PhieuNhapDao().getChiTietByMaPN(pnSelected.getMaPN());
                } else {
                    pnSelected = null;
                }
            }
        });

        String[] headersCT = {"Mã phiếu nhập", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModelCT.setColumnIdentifiers(headersCT);
        tablechitiet.setModel(tableModelCT);
        tablechitiet.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tablechitiet.getSelectedRow();
                if (row >= 0) {
                    String maSP = tablechitiet.getValueAt(row, 1).toString();
                    String soLuong = tablechitiet.getValueAt(row, 3).toString();
                    String donGia = tablechitiet.getValueAt(row, 4).toString().replace(",", "");

                    for (int i = 0; i < cbosp.getItemCount(); i++) {
                        khoSP sp = cbosp.getItemAt(i);
                        if (sp.getMaSP().equalsIgnoreCase(maSP)) {
                            cbosp.setSelectedItem(sp);
                            break;
                        }
                    }

                    txtsoluong.setText(soLuong);
                    txtgianhap.setText(donGia);
                }
            }
        });
        jScrollPane1.getViewport().setBackground(Color.white);
    }
   
    private void loadDataToCombobox() {
            cboncc.removeAllItems();
        ArrayList<NCC> listNCC = new NCCDao().getAll("");
        for (NCC ncc : listNCC) {
            cbomodelncc.addElement(ncc);
        }
        cboncc.setModel(cbomodelncc);
        
        cbomodelsp.removeAllElements();
        ArrayList<khoSP> listSP = new khoDao().getAll("", "");
        for (khoSP sp : listSP) {
            cbomodelsp.addElement(sp);
        }
        cbosp.setModel(cbomodelsp);
    }
    
    private void loadDataToTablePN(String keyword) {
        listPN = new PhieuNhapDao().getAll(keyword);
        tableModelPN.setRowCount(0);

        for (PhieuNhap pn : listPN) {
            tableModelPN.addRow(new Object[]{
                pn.getMaPN(),
                sdf.format(pn.getNgayNhap()),
                pn.getMaNCC(),
                String.format("%,.0f", pn.getTongTien())
            });
        }
    }
    
    private void loadDataToTableCT(String maPN) {
        ArrayList<ChiTietPhieuNhap> listCT = new PhieuNhapDao().getChiTietByMaPN(maPN);
        tableModelCT.setRowCount(0);

        for (ChiTietPhieuNhap ctpn : listCT) {
            tableModelCT.addRow(new Object[]{
                ctpn.getMaPN(), 
                ctpn.getMaSP(),
                ctpn.getTenSP(),
                ctpn.getSoLuong(),
                String.format("%,.0f", ctpn.getDonGia()),
                String.format("%,.0f", ctpn.getThanhTien())
            });
        }
    }
    
    private void setInitialState() {
        txtmaphieu.setEditable(false);
        cboncc.setEnabled(false);
        cbosp.setEnabled(false);
        txtsoluong.setEditable(false);
        txtgianhap.setEditable(false);
        jdcngaynhap.setEnabled(false);
        btnluu.setEnabled(false);
        btnsua.setEnabled(false);
        btnxoa.setEnabled(false);
        btnthem.setEnabled(true);
    }
    
    private void setEditingState(boolean edit) {
        txtmaphieu.setEditable(edit);
        cboncc.setEnabled(true);
        cbosp.setEnabled(true);
        txtsoluong.setEditable(true);
        txtgianhap.setEditable(true);
        jdcngaynhap.setEnabled(true);
    }
    
    private void hienthithongtin(PhieuNhap pn) {
        if (pn == null) return;
        
        txtmaphieu.setText(pn.getMaPN());
        
        if (pn.getNgayNhap() != null) {
            jdcngaynhap.setDate(pn.getNgayNhap());
        }
        
        for (int i = 0; i < cboncc.getItemCount(); i++) {
            NCC ncc = cboncc.getItemAt(i);
            if (ncc.getMaNCC().equalsIgnoreCase(pn.getMaNCC())) {
                cboncc.setSelectedItem(ncc);
                break;
            }
        }

        loadDataToTableCT(pn.getMaPN());

        double tongTienPhieu = 0;
        ArrayList<ChiTietPhieuNhap> listCT = new PhieuNhapDao().getChiTietByMaPN(pn.getMaPN());
        for (ChiTietPhieuNhap ct : listCT) {
            tongTienPhieu += ct.getThanhTien();
        }
        tbntongtien.setText(String.format("%,.0f", tongTienPhieu));

        txtmaphieu.setEditable(false);
        jdcngaynhap.setEnabled(true); 
        cboncc.setEnabled(true);      
    
        cbosp.setEnabled(true);
        txtsoluong.setEditable(true);
        txtgianhap.setEditable(true);

        btnluu.setEnabled(false);
        btnsua.setEnabled(true);
        btnxoa.setEnabled(true);
        btnthem.setEnabled(true);
    }
    
    private void clearForm() {
        txtmaphieu.setText("");
        txtsoluong.setText("");
        txtgianhap.setText("");
        tableModelCT.setRowCount(0);
        listCTTemp.clear();
        tablephieunhap.clearSelection();
        tbntongtien.setText("0");
        pnSelected = null;
        setInitialState();
    }
    
    private void hienThiListCTTempToTable() {
    tableModelCT.setRowCount(0);
        for (ChiTietPhieuNhap ct : listCTTemp) {
            tableModelCT.addRow(new Object[]{
                ct.getMaPN(),
                ct.getMaSP(),
                ct.getTenSP(),
                ct.getSoLuong(),
                String.format("%,.0f", ct.getDonGia()),
                String.format("%,.0f", ct.getThanhTien())
            });
        }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtmaphieu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jdcngaynhap = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        cboncc = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cbosp = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtsoluong = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtgianhap = new javax.swing.JTextField();
        btnthemvaophieu = new javax.swing.JButton();
        btnsuasp = new javax.swing.JButton();
        btnxoasp = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btntimkiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablephieunhap = new javax.swing.JTable();
        txttimkiem = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablechitiet = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        btnluu = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btnlammoi = new javax.swing.JButton();
        tbntongtien = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setText("Quản lý nhập hàng");
        jLabel1.setVerifyInputWhenFocusTarget(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)), "Thông tin phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 204));
        jLabel3.setText("Mã phiếu:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 204, 204));
        jLabel4.setText("Ngày nhập:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 204));
        jLabel5.setText("Nhà cung cấp:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cboncc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtmaphieu)
                            .addComponent(jdcngaynhap, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtmaphieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addComponent(jLabel4))
                    .addComponent(jdcngaynhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(cboncc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)), "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 204));
        jLabel6.setText("Sản phẩm:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 204, 204));
        jLabel7.setText("Số lượng:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 204));
        jLabel9.setText("Giá nhập:");

        btnthemvaophieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnthemvaophieu.setText("Thêm");
        btnthemvaophieu.addActionListener(this::btnthemvaophieuActionPerformed);

        btnsuasp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnsuasp.setText("Cập nhật");
        btnsuasp.addActionListener(this::btnsuaspActionPerformed);

        btnxoasp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnxoasp.setText("Xóa");
        btnxoasp.addActionListener(this::btnxoaspActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtgianhap, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbosp, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnthemvaophieu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnsuasp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnxoasp)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbosp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtgianhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthemvaophieu)
                    .addComponent(btnsuasp)
                    .addComponent(btnxoasp))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)), "Danh sách phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Nhập mã phiếu:");
        jLabel2.setVerifyInputWhenFocusTarget(false);

        btntimkiem.setText("Tìm kiếm");
        btntimkiem.addActionListener(this::btntimkiemActionPerformed);

        tablephieunhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã phiếu", "Ngày nhập", "Nhà cung cấp", "Tổng tiền"
            }
        ));
        jScrollPane1.setViewportView(tablephieunhap);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btntimkiem)
                .addContainerGap(64, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btntimkiem)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)), "Chi tiết phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tablechitiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã phiếu nhập", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane2.setViewportView(tablechitiet);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 204, 204));
        jLabel11.setText("Tổng tiền:");

        btnthem.setBackground(new java.awt.Color(0, 204, 204));
        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnthem.setForeground(new java.awt.Color(255, 255, 255));
        btnthem.setText("Thêm");
        btnthem.addActionListener(this::btnthemActionPerformed);

        btnsua.setBackground(new java.awt.Color(0, 204, 204));
        btnsua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnsua.setForeground(new java.awt.Color(255, 255, 255));
        btnsua.setText("Cập nhật");
        btnsua.addActionListener(this::btnsuaActionPerformed);

        btnluu.setBackground(new java.awt.Color(0, 204, 204));
        btnluu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnluu.setForeground(new java.awt.Color(255, 255, 255));
        btnluu.setText("Lưu");
        btnluu.addActionListener(this::btnluuActionPerformed);

        btnxoa.setBackground(new java.awt.Color(0, 204, 204));
        btnxoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnxoa.setForeground(new java.awt.Color(255, 255, 255));
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(this::btnxoaActionPerformed);

        btnlammoi.setBackground(new java.awt.Color(0, 204, 204));
        btnlammoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnlammoi.setForeground(new java.awt.Color(255, 255, 255));
        btnlammoi.setText("Làm mới");
        btnlammoi.addActionListener(this::btnlammoiActionPerformed);

        tbntongtien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tbntongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnthem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(btnluu)
                .addGap(36, 36, 36)
                .addComponent(btnsua)
                .addGap(41, 41, 41)
                .addComponent(btnxoa)
                .addGap(32, 32, 32)
                .addComponent(btnlammoi)
                .addGap(32, 32, 32))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(tbntongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnlammoi)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnxoa)
                        .addComponent(btnsua)
                        .addComponent(btnluu)
                        .addComponent(btnthem)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
       clearForm();
        setEditingState(true);
        btnluu.setEnabled(true);
        btnsua.setEnabled(false);
        btnxoa.setEnabled(false);
        txtmaphieu.setEnabled(true);
        txtmaphieu.setEditable(true);
        txtmaphieu.requestFocus();
        jdcngaynhap.setEnabled(true);
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        // 1. Kiểm tra xem đã chọn phiếu trên bảng chưa
    if (pnSelected == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần sửa từ bảng danh sách!");
        return;
    }
    
    // LẤY MÃ PHIẾU GỐC TỪ ĐỐI TƯỢNG ĐÃ CHỌN (Tránh việc người dùng sửa nhầm trên textfield)
    String maPN = pnSelected.getMaPN(); 
    
    Date ngayNhap = jdcngaynhap.getDate();
    
    // Kiểm tra ComboBox có đang chọn đối tượng NCC không
    Object selectedObj = cboncc.getSelectedItem();
    if (selectedObj == null || !(selectedObj instanceof NCC)) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp hợp lệ!");
        return;
    }
    NCC selectedNCC = (NCC) selectedObj;
    
    if (ngayNhap == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày nhập!");
        return;
    }
    
    // 2. Kiểm tra xem danh sách chi tiết có dữ liệu không
    if (listCTTemp == null || listCTTemp.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Phiếu nhập phải có ít nhất một sản phẩm chi tiết!");
        return;
    }
    
    // 3. Tính lại tổng tiền và cập nhật lại MaPN cho từng chi tiết
    double tongTien = 0;
    for (ChiTietPhieuNhap ct : listCTTemp) {
        ct.setMaPN(maPN); 
        tongTien += ct.getThanhTien();
    }
    
    // 4. Tạo đối tượng PhieuNhap mới với thông tin cập nhật
    PhieuNhap pn = new PhieuNhap(maPN, ngayNhap, selectedNCC.getMaNCC(), tongTien);
    
    // 5. Gọi hàm DAO để thực hiện cập nhật (Transaction)
    int result = new PhieuNhapDao().updatePhieuNhap(pn, listCTTemp);
    
    // 6. Kiểm tra kết quả trả về từ DAO
    if (result > 0) {
        JOptionPane.showMessageDialog(this, "Cập nhật phiếu nhập thành công!");
        loadDataToTablePN(""); // Load lại bảng danh sách phiếu nhập
        clearForm();           // Xóa trắng form và reset trạng thái
        pnSelected = null;     // Bỏ chọn để tránh sửa nhầm lần nữa
    } else if (result == -2) {
        JOptionPane.showMessageDialog(this, "Lỗi: Trùng dữ liệu khóa chính!");
    } else {
        JOptionPane.showMessageDialog(this, "Cập nhật phiếu nhập thất bại, vui lòng kiểm tra lại kết nối hoặc dữ liệu kho!");
    }
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btnluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnluuActionPerformed
        String maPN = txtmaphieu.getText().trim();
    Date ngayNhap = jdcngaynhap.getDate();
    NCC selectedNCC = (NCC) cboncc.getSelectedItem();
    
    // Kiểm tra dữ liệu nhập
    if (maPN.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Mã phiếu không được để trống!");
        txtmaphieu.requestFocus();
        return;
    }
    if (ngayNhap == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày nhập!");
        return;
    }
    if (selectedNCC == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!");
        return;
    }
    if (listCTTemp.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Phiếu nhập phải có ít nhất một sản phẩm chi tiết!");
        return;
    }

    // Tính tổng tiền từ danh sách tạm
    double tongTien = 0;
    for (ChiTietPhieuNhap ct : listCTTemp) {
        tongTien += ct.getThanhTien();
        ct.setMaPN(maPN); 
    }

    // Tạo đối tượng PhieuNhap
    PhieuNhap pn = new PhieuNhap(maPN, ngayNhap, selectedNCC.getMaNCC(), tongTien);

    // Gọi DAO để insert vào cơ sở dữ liệu (Transaction)
    int result = new PhieuNhapDao().insertPhieuNhap(pn, listCTTemp);
    
    if (result == 1) {
        JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công!");
        loadDataToTablePN("");
        clearForm();
    } else if (result == -2) {
        JOptionPane.showMessageDialog(this, "Mã phiếu nhập đã tồn tại trong hệ thống!");
        txtmaphieu.requestFocus();
    } else {
        JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thất bại, vui lòng kiểm tra lại!");
    }
    }//GEN-LAST:event_btnluuActionPerformed
    
    
    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        if (pnSelected == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xóa từ bảng!");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa phiếu nhập: " + pnSelected.getMaPN() + "?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            
    if (confirm == JOptionPane.YES_OPTION) {
        boolean success = new PhieuNhapDao().deletePhieuNhap(pnSelected.getMaPN());
        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công!");
            loadDataToTablePN("");
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        clearForm();
        loadDataToTablePN("");
    }//GEN-LAST:event_btnlammoiActionPerformed
        private void tinhTongTien() {
    double tongTien = 0;
    for (ChiTietPhieuNhap ctpn : listCTTemp) {
        tongTien += ctpn.getThanhTien();
    }
    // Hiển thị định dạng tiền tệ ra jLabel12
    tbntongtien.setText(String.format("%,.0f", tongTien));
}
    private void btnthemvaophieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemvaophieuActionPerformed
        khoSP selectedSP = (khoSP) cbosp.getSelectedItem();
    if (selectedSP == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần thêm!");
        return;
    }
    
    try {
        String strSoLuong = txtsoluong.getText().trim();
        String strGiaNhap = txtgianhap.getText().trim();
        
        if (strSoLuong.isEmpty() || strGiaNhap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ số lượng và giá nhập!");
            return;
        }

        int soLuong = Integer.parseInt(strSoLuong);
        double donGia = Double.parseDouble(strGiaNhap);
        
        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng nhập phải lớn hơn 0!");
            txtsoluong.requestFocus();
            return;
        }
        if (donGia < 0) {
            JOptionPane.showMessageDialog(this, "Giá nhập không được nhỏ hơn 0!");
            txtgianhap.requestFocus();
            return;
        }

        String maSP = selectedSP.getMaSP();
        String tenSP = selectedSP.getTenSP();
        String maPN = txtmaphieu.getText().trim();

        // Kiểm tra xem sản phẩm đã có trong listCTTemp chưa
        boolean daCo = false;
        for (ChiTietPhieuNhap item : listCTTemp) {
            if (item.getMaSP().equalsIgnoreCase(maSP)) {
                // Nếu có rồi thì cộng dồn số lượng
                item.setSoLuong(item.getSoLuong() + soLuong);
                item.setDonGia(donGia);
                daCo = true;
                break;
            }
        }

        // Nếu chưa có thì thêm mới vào danh sách tạm
        if (!daCo) {
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(maPN, maSP, tenSP, soLuong, donGia);
            listCTTemp.add(ctpn);
        }

        // Cập nhật lại bảng và tổng tiền
        hienThiListCTTempToTable();
        tinhTongTien();

        // Reset ô nhập liệu sản phẩm
        txtsoluong.setText("");
        txtgianhap.setText("");
        cbosp.requestFocus();

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên và giá nhập phải là số hợp lệ!");
    }
    }//GEN-LAST:event_btnthemvaophieuActionPerformed
    
    
    private void btntimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemActionPerformed
        String keyword = txttimkiem.getText().trim();
        loadDataToTablePN(keyword);
    }//GEN-LAST:event_btntimkiemActionPerformed

    private void btnsuaspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaspActionPerformed
        khoSP selectedSP = (khoSP) cbosp.getSelectedItem();
    if (selectedSP == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
        return;
    }

    try {
        String strSoLuong = txtsoluong.getText().trim();
        String strGiaNhap = txtgianhap.getText().trim();
        
        if (strSoLuong.isEmpty() || strGiaNhap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng và giá nhập mới!");
            return;
        }

        int soLuongMoi = Integer.parseInt(strSoLuong);
        double donGiaMoi = Double.parseDouble(strGiaNhap);

        if (soLuongMoi <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
            return;
        }
        if (donGiaMoi < 0) {
            JOptionPane.showMessageDialog(this, "Giá nhập không được âm!");
            return;
        }

        // Tìm sản phẩm trong danh sách tạm để cập nhật
        boolean found = false;
        for (ChiTietPhieuNhap item : listCTTemp) {
            if (item.getMaSP().equalsIgnoreCase(selectedSP.getMaSP())) {
                item.setSoLuong(soLuongMoi);
                item.setDonGia(donGiaMoi);
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này chưa có trong chi tiết phiếu nhập!");
            return;
        }

        // Cập nhật lại bảng và tổng tiền
        hienThiListCTTempToTable();
        tinhTongTien();
        JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số lượng và giá nhập phải là định dạng số hợp lệ!");
    }
    }//GEN-LAST:event_btnsuaspActionPerformed

    private void btnxoaspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaspActionPerformed
        int row = tablechitiet.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng sản phẩm cần xóa trên bảng chi tiết!");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này khỏi phiếu nhập?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        // Xóa khỏi danh sách tạm dựa vào vị trí dòng hoặc mã sản phẩm
        String maSP = tablechitiet.getValueAt(row, 1).toString();
        
        listCTTemp.removeIf(item -> item.getMaSP().equalsIgnoreCase(maSP));

        // Cập nhật lại bảng và tổng tiền
        hienThiListCTTempToTable();
        tinhTongTien();

        // Xóa trắng ô nhập liệu
        txtsoluong.setText("");
        txtgianhap.setText("");
    }
    }//GEN-LAST:event_btnxoaspActionPerformed
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlammoi;
    private javax.swing.JButton btnluu;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnsuasp;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnthemvaophieu;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JButton btnxoasp;
    private javax.swing.JComboBox<NCC> cboncc;
    private javax.swing.JComboBox<khoSP> cbosp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdcngaynhap;
    private javax.swing.JTable tablechitiet;
    private javax.swing.JTable tablephieunhap;
    private javax.swing.JLabel tbntongtien;
    private javax.swing.JTextField txtgianhap;
    private javax.swing.JTextField txtmaphieu;
    private javax.swing.JTextField txtsoluong;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
