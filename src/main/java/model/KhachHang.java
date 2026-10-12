
package model;

import java.util.Date;


public class KhachHang {
    private String MaKH;
    private String TenKH;
    private String GioiTinh; 
    private Date  NgaySinh;
    private String SoDienThoai; 
    private String Email;

    public KhachHang() {
    }

    public KhachHang(String MaKH, String TenKH, String GioiTinh, Date NgaySinh, String SoDienThoai, String Email) {
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.SoDienThoai = SoDienThoai;
        this.Email = Email;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    @Override
    public String toString() {
        return "KhachHang{" + "MaKH=" + MaKH + ", TenKH=" + TenKH + ", GioiTinh=" + GioiTinh + ", NgaySinh=" + NgaySinh + ", SoDienThoai=" + SoDienThoai + ", Email=" + Email + '}';
    }
    
}
