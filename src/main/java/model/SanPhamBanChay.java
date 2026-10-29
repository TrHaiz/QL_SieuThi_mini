package model;

public class SanPhamBanChay {
    private String maSP;
    private String tenSP;
    private String loaiSP;
    private int tongSoLuongBan;
    private String donViTinh;
    private double tongDoanhThu;

    public SanPhamBanChay() {
    }

    public SanPhamBanChay(String maSP, String tenSP, String loaiSP, int tongSoLuongBan, String donViTinh, double tongDoanhThu) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.tongSoLuongBan = tongSoLuongBan;
        this.donViTinh = donViTinh;
        this.tongDoanhThu = tongDoanhThu;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    

    public int getTongSoLuongBan() {
        return tongSoLuongBan;
    }

    public void setTongSoLuongBan(int tongSoLuongBan) {
        this.tongSoLuongBan = tongSoLuongBan;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public double getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(double tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }
}
