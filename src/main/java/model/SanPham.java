/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Long
 */
public class SanPham {
    private String MaSP;
    private String TenSP;
    private String TenLoaiSP;
    private String DV;
    private double GiaNhap;
    private double GiaBan;
    private String TrangThai;

    public SanPham() {
    }

    public SanPham(String MaSP, String TenSP, String TenLoaiSP, String DV, double GiaNhap, double GiaBan, String TrangThai) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.TenLoaiSP = TenLoaiSP;
        this.DV = DV;
        this.GiaNhap = GiaNhap;
        this.GiaBan = GiaBan;
        this.TrangThai = TrangThai;
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

    public String getDV() {
        return DV;
    }

    public void setDV(String DV) {
        this.DV = DV;
    }

    public double getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(double GiaNhap) {
        this.GiaNhap = GiaNhap;
    }

    public double getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(double GiaBan) {
        this.GiaBan = GiaBan;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}