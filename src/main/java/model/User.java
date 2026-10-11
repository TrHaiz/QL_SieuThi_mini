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
public class User {
    private String username,ten,diachi,password;
    private Date ngaysinh;

    public User(String username, String ten, String diachi, String password, Date ngaysinh) {
        this.username = username;
        this.ten = ten;
        this.diachi = diachi;
        this.password = password;
        this.ngaysinh = ngaysinh;
    }

    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    

   

    public String getTen() {
        return ten;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getPassword() {
        return password;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", ten=" + ten + ", diachi=" + diachi + ", password=" + password + ", ngaysinh=" + ngaysinh + '}';
    }
    
}
