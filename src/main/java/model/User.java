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
    private String email,ten,diachi,password;
    private Date ngaysinh;

    public User(String email, String ten, String diachi, String password, Date ngaysinh) {
        this.email = email;
        this.ten = ten;
        this.diachi = diachi;
        this.password = password;
        this.ngaysinh = ngaysinh;
    }

    public String getEmail() {
        return email;
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

    public void setEmail(String email) {
        this.email = email;
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
        return "User{" + "email=" + email + ", ten=" + ten + ", diachi=" + diachi + ", password=" + password + ", ngaysinh=" + ngaysinh + '}';
    }
    
}
