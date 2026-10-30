
package model;

import java.util.Date;


public class PhieuNhap {
    private String MaPN; 
    private Date NgayNhap; 
    private String MaNCC; 
    private double  TongTien;

    public PhieuNhap() {
    }

    public PhieuNhap(String MaPN, Date NgayNhap, String MaNCC, double TongTien) {
        this.MaPN = MaPN;
        this.NgayNhap = NgayNhap;
        this.MaNCC = MaNCC;
        this.TongTien = TongTien;
    }

    public String getMaPN() {
        return MaPN;
    }

    public void setMaPN(String MaPN) {
        this.MaPN = MaPN;
    }

    public Date getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(Date NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

}
