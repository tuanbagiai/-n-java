/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTransfer;

/**
 *
 * @author HT
 */
public class Ban {
    
    private String MaBan;
    private String TenBan;
    private String TrangThai;

    public Ban(String MaBan, String TenBan, String TrangThai) 
    {
        this.MaBan = MaBan;
        this.TenBan = TenBan;
        this.TrangThai = TrangThai;
    }

    /**
     * @return the MaBan
     */
    public String getMaBan() {
        return MaBan;
    }

    /**
     * @param MaBan the MaBan to set
     */
    public void setMaBan(String MaBan) {
        this.MaBan = MaBan;
    }

    /**
     * @return the TenBan
     */
    public String getTenBan() {
        return TenBan;
    }

    /**
     * @param TenBan the TenBan to set
     */
    public void setTenBan(String TenBan) {
        this.TenBan = TenBan;
    }

    /**
     * @return the TrangThai
     */
    public String getTrangThai() {
        return TrangThai;
    }

    /**
     * @param TrangThai the TrangThai to set
     */
    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
