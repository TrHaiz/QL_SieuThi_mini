package DAO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author GIAKHANHPC
 */
import java.util.ArrayList;
import MyConnection.MyConnection;
import model.Vouncher;
import java.sql.*;
import javax.swing.JOptionPane;

public class vouncherDAO {
   public ArrayList<Vouncher> getAll(String cond) {
    ArrayList<Vouncher> h = new ArrayList<>();
    String sql = "SELECT * FROM voucher WHERE 1=1";
    
    if (cond != null && !cond.trim().isEmpty()) {
        // Thêm dấu ngoặc đơn () bao quanh mệnh đề OR
        sql += " AND mavoucher LIKE ? ";
    }
    
    try (Connection c = new MyConnection().getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
        
        if (cond != null && !cond.trim().isEmpty()) {
            String searchPattern = "%" + cond.trim() + "%";
            ps.setString(1, searchPattern);
        }
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String ma = rs.getString("mavoucher");
                String ten = rs.getString("tenVoucher");
                double phantramgiam = rs.getDouble("PhanTramGiam");
                double giatu = rs.getDouble("DieuKienGiaTu");
                double giaden = rs.getDouble("DieuKienGiaDen");
                Date batdau = rs.getDate("NgayBatDau");
                Date ketthuc = rs.getDate("NgayKetThuc");
                
                h.add(new Vouncher(ma, ten, phantramgiam, giatu, giaden, batdau, ketthuc));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return h;
}
    
    public int update(String ma, String nma ,String ten, double phantramgiam, double giatu, double giaden, Date batdau, Date ketthuc)
    {
        String sql="update  voucher set mavoucher=?, tenvoucher=?, PhanTramGiam=?, DieuKienGiaTu=?, DieuKienGiaDen=?, NgayBatDau=?, NgayKetThuc=?  where mavoucher=?";
        try (Connection c=new MyConnection().getConnection())
        {
            PreparedStatement ps=c.prepareStatement(sql);
            ps.setString(1, nma);
            ps.setString(2, ten);
            ps.setDouble(3, phantramgiam);
            ps.setDouble(4, giatu);
            ps.setDouble(5, giaden);
            ps.setDate(6, batdau);
            ps.setDate(7, ketthuc);
            ps.setString(8, ma);
            int n=ps.executeUpdate();
            if (n>0) return 1;
        }
        catch (Exception e) {e.printStackTrace();}
        return -1;
    }
    
    public int delete(Vouncher h)
    {
    String sql="delete from voucher where mavoucher=? ";
        try (Connection c=new MyConnection().getConnection())
        {
            PreparedStatement ps=c.prepareStatement(sql);
            ps.setString(1, h.getMa());
            int n=ps.executeUpdate();
            return n;
        }
        catch (Exception e) {e.printStackTrace();}
        return -1;
    }
    
    public int add(Vouncher h)
    {
        String sql="insert into voucher values (?,?,?,?,?,?,?)";
        try (Connection c=new MyConnection().getConnection())
        {
            PreparedStatement ps=c.prepareStatement(sql);
         ps.setString(1, h.getMa());
            ps.setString(2, h.getTen());
            ps.setDouble(3, h.getPhanTramGiam());
            ps.setDouble(4, h.getDieukienGiatu());
            ps.setDouble(5, h.getDieukiengiaden());
            ps.setDate(6, new java.sql.Date(h.getNgaybatdau().getTime()));
            ps.setDate(7,  new java.sql.Date(h.getNgayketthuc().getTime()));
            ps.executeUpdate();
            return 1;
        }
        catch (SQLException e) {e.printStackTrace(); if (e.getErrorCode()==2627) return -2;}
        return -1;
    }
    public static void main(String[] args) {
        ArrayList<Vouncher>v=new vouncherDAO().getAll("");
  
        for (Vouncher t : v) System.out.println(t.toString());
    }
}
