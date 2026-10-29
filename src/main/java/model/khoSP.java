
package model;
import java.util.ArrayList;
import java.util.Date;

public class khoSP {
    private String MaSP;
    private String TenSP;
    private String TenLoaiSP;
    private String MaNCC;
    private double TonKho;
    private Date NgayNhap;

    public khoSP() {
    }

    public khoSP(String MaSP, String TenSP, String TenLoaiSP, String MaNCC, double TonKho, Date NgayNhap) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.TenLoaiSP = TenLoaiSP;
        this.MaNCC = MaNCC;
        this.TonKho = TonKho;
        this.NgayNhap = NgayNhap;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public String getTenLoaiSP() {
        return TenLoaiSP;
    }

    public void setTenLoaiSP(String TenLoaiSP) {
        this.TenLoaiSP = TenLoaiSP;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public double getTonKho() {
        return TonKho;
    }

    public void setTonKho(double TonKho) {
        this.TonKho = TonKho;
    }

    public Date getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(Date NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    @Override
    public String toString() {
        return  this.MaSP +" - "+ this.TenSP;
    }
    

}