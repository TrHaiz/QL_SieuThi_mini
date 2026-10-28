package model;

import java.time.LocalDate;

public class ThongKeLoaiSPNgay {
    private LocalDate ngayBan;
    private String maLoaiSP;
    private String tenLoaiSP;
    private int tongSoLuongBan;

    public ThongKeLoaiSPNgay() {
    }

    public ThongKeLoaiSPNgay(LocalDate ngayBan, String maLoaiSP, String tenLoaiSP, int tongSoLuongBan) {
        this.ngayBan = ngayBan;
        this.maLoaiSP = maLoaiSP;
        this.tenLoaiSP = tenLoaiSP;
        this.tongSoLuongBan = tongSoLuongBan;
    }

    public LocalDate getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(LocalDate ngayBan) {
        this.ngayBan = ngayBan;
    }

    public String getMaLoaiSP() {
        return maLoaiSP;
    }

    public void setMaLoaiSP(String maLoaiSP) {
        this.maLoaiSP = maLoaiSP;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public int getTongSoLuongBan() {
        return tongSoLuongBan;
    }

    public void setTongSoLuongBan(int tongSoLuongBan) {
        this.tongSoLuongBan = tongSoLuongBan;
    }
}
