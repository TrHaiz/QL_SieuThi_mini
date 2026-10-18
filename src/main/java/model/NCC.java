
package model;


public class NCC {
    private String MaNCC;
    private String tenNCC; 
    private String diachi; 
    private String email; 
    private String sdt;

    public NCC() {
    }

    public NCC(String MaNCC, String tenNCC, String diachi, String email, String sdt) {
        this.MaNCC = MaNCC;
        this.tenNCC = tenNCC;
        this.diachi = diachi;
        this.email = email;
        this.sdt = sdt;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return "NCC{" + "MaNCC=" + MaNCC + ", tenNCC=" + tenNCC + ", diachi=" + diachi + ", email=" + email + ", sdt=" + sdt + '}';
    }
    
    
}
