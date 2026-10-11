/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author GIAKHANHPC
 */
public class Vouncher {
    private String ma,ten;
    private double phanTramGiam, dieukienGiatu, dieukiengiaden;
    private Date ngaybatdau, ngayketthuc;

    public Vouncher(String ma, String ten, double phanTramGiam, double dieukienGiatu, double dieukiengiaden, Date ngaybatdau, Date ngayketthuc) {
        this.ma = ma;
        this.ten = ten;
        this.phanTramGiam = phanTramGiam;
        this.dieukienGiatu = dieukienGiatu;
        this.dieukiengiaden = dieukiengiaden;
        this.ngaybatdau = ngaybatdau;
        this.ngayketthuc = ngayketthuc;
    }

    @Override
    public String toString() {
        return "Vouncher{" + "ma=" + ma + ", ten=" + ten + ", phanTramGiam=" + phanTramGiam + ", dieukienGiatu=" + dieukienGiatu + ", dieukiengiaden=" + dieukiengiaden + ", ngaybatdau=" + ngaybatdau + ", ngayketthuc=" + ngayketthuc + '}';
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(double phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public double getDieukienGiatu() {
        return dieukienGiatu;
    }

    public void setDieukienGiatu(double dieukienGiatu) {
        this.dieukienGiatu = dieukienGiatu;
    }

    public double getDieukiengiaden() {
        return dieukiengiaden;
    }

    public void setDieukiengiaden(double dieukiengiaden) {
        this.dieukiengiaden = dieukiengiaden;
    }

    public Date getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(Date ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public Date getNgayketthuc() {
        return ngayketthuc;
    }

    public void setNgayketthuc(Date ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }

    
}
