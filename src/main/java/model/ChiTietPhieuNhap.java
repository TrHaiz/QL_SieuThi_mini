
package model;

public class ChiTietPhieuNhap {
    private String MaPN;
    private String MaSP; 
    private String TenSP;
    private int SoLuong; 
    private double DonGia;
    

    public ChiTietPhieuNhap() {
    }

    public ChiTietPhieuNhap(String MaPN, String MaSP, String TenSP, int SoLuong, double DonGia) {
        this.MaPN = MaPN;
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
    }

    public String getMaPN() {
        return MaPN;
    }

    public void setMaPN(String MaPN) {
        this.MaPN = MaPN;
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

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double DonGia) {
        this.DonGia = DonGia;
    }

    public double getThanhTien() {
        return this.SoLuong * this.DonGia;
    }
    
}
