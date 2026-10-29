
package model;

public class LoaiSP {
    private String MaLoaiSP;
    private String TenLoaiSP;
    private String MoTa;

    public LoaiSP(String MaLoaiSP, String TenLoaiSP, String MoTa) {
        this.MaLoaiSP = MaLoaiSP;
        this.TenLoaiSP = TenLoaiSP;
        this.MoTa = MoTa;
    }

    public String getMaLoaiSP() {
        return MaLoaiSP;
    }

    public void setMaLoaiSP(String MaLoaiSP) {
        this.MaLoaiSP = MaLoaiSP;
    }

    public String getTenLoaiSP() {
        return TenLoaiSP;
    }

    public void setTenLoaiSP(String TenLoaiSP) {
        this.TenLoaiSP = TenLoaiSP;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }
    
}
