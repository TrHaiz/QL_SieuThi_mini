
package model;
import java.util.Date;

public class khoSP {
    private String MaSP;
    private String TenSP;
    private String TenLoaiSP;
    private String TenNCC;
    private double TonKho;
    private Date NgayNhap;

    public khoSP() {
    }

    public khoSP(String MaSP, String TenSP, String TenLoaiSP, String TenNCC, double TonKho, Date NgayNhap) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.TenLoaiSP = TenLoaiSP;
        this.TenNCC = TenNCC;
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

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String TenNCC) {
        this.TenNCC = TenNCC;
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

}