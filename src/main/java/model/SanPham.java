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
    private String MaLoaiSP;
    private String MaNCC;
    private int Tonkho;
    private double Gia;

    public SanPham(String MaSP, String TenSP, String MaLoaiSP, String MaNCC, int Tonkho, double Gia) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.MaLoaiSP = MaLoaiSP;
        this.MaNCC = MaNCC;
        this.Tonkho = Tonkho;
        this.Gia = Gia;
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

    public String getMaLoaiSP() {
        return MaLoaiSP;
    }

    public void setMaLoaiSP(String MaLoaiSP) {
        this.MaLoaiSP = MaLoaiSP;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public int getTonkho() {
        return Tonkho;
    }

    public void setTonkho(int Tonkho) {
        this.Tonkho = Tonkho;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double Gia) {
        this.Gia = Gia;
    }
    
}