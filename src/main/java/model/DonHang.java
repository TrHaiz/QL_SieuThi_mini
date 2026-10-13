/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Long
 */
public class DonHang {
    private String maDH;
    private String maKH;
    private String maVoucher;
    private Timestamp ngayMua;
    private double tongTien;

    public DonHang() {
    }

    public DonHang(String maDH, String maKH, String maVoucher, Timestamp ngayMua, double tongTien) {
        this.maDH = maDH;
        this.maKH = maKH;
        this.maVoucher = maVoucher;
        this.ngayMua = ngayMua;
        this.tongTien = tongTien;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public Timestamp getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Timestamp ngayMua) {
        this.ngayMua = ngayMua;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
