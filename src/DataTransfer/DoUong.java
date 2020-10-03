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
public class DoUong {
    
    private String MaDoUong;
    private String TenDoUong;
    private long GiaBan;
    private String MaLoaiDoUong;
    private String HinhAnh;

    public DoUong()
    {
    
    }
    
    public DoUong(String MaDoUong, String TenDoUong, long GiaBan, String MaLoaiDoUong, String HinhAnh) {
        this.MaDoUong = MaDoUong;
        this.TenDoUong = TenDoUong;
        this.GiaBan = GiaBan;
        this.MaLoaiDoUong = MaLoaiDoUong;
        this.HinhAnh = HinhAnh;
    }
    
    /**
     * @return the MaDoUong
     */
    public String getMaDoUong() {
        return MaDoUong;
    }

    /**
     * @param MaDoUong the MaDoUong to set
     */
    public void setMaDoUong(String MaDoUong) {
        this.MaDoUong = MaDoUong;
    }

    /**
     * @return the TenDoUong
     */
    public String getTenDoUong() {
        return TenDoUong;
    }

    /**
     * @param TenDoUong the TenDoUong to set
     */
    public void setTenDoUong(String TenDoUong) {
        this.TenDoUong = TenDoUong;
    }

    /**
     * @return the GiaBan
     */
    public long getGiaBan() {
        return GiaBan;
    }

    /**
     * @param GiaBan the GiaBan to set
     */
    public void setGiaBan(long GiaBan) {
        this.GiaBan = GiaBan;
    }

    /**
     * @return the MaLoaiDoUong
     */
    public String getMaLoaiDoUong() {
        return MaLoaiDoUong;
    }

    /**
     * @param MaLoaiDoUong the MaLoaiDoUong to set
     */
    public void setMaLoaiDoUong(String MaLoaiDoUong) {
        this.MaLoaiDoUong = MaLoaiDoUong;
    }

    /**
     * @return the HinhAnh
     */
    public String getHinhAnh() {
        return HinhAnh;
    }

    /**
     * @param HinhAnh the HinhAnh to set
     */
    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }
}
