/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author GIAKHANHPC
 */
public class KetQuaThongKe {
private  Map<String, String> categories;
    private    ArrayList<ThongKeLoaiSPNgay> salesData;
    private    SanPhamBanChay bestSellers;
    private    double totalRevenue;
  
    public KetQuaThongKe(Map<String, String> categories, ArrayList<ThongKeLoaiSPNgay> salesData, SanPhamBanChay bestSellers, double totalRevenue) {
            this.categories = categories;
            this.salesData = salesData;
            this.bestSellers = bestSellers;
            this.totalRevenue = totalRevenue;
        }
    
    public Map<String, String> getCategories() {
        return categories;
    }

    public ArrayList<ThongKeLoaiSPNgay> getSalesData() {
        return salesData;
    }

    public SanPhamBanChay getBestSellers() {
        return bestSellers;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setCategories(Map<String, String> categories) {
        this.categories = categories;
    }

    public void setSalesData(ArrayList<ThongKeLoaiSPNgay> salesData) {
        this.salesData = salesData;
    }

    public void setBestSellers(SanPhamBanChay bestSellers) {
        this.bestSellers = bestSellers;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
       
}
