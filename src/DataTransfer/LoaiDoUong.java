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
public class LoaiDoUong {
    
    private String MaLoaiDoUong;
    private String TenLoaiDoUong;
    private String HinhAnh;

    public LoaiDoUong() 
    {
    
    }
    
    public LoaiDoUong(String MaLoaiDoUong, String TenLoaiDoUong, String HinhAnh) {
        this.MaLoaiDoUong = MaLoaiDoUong;
        this.TenLoaiDoUong = TenLoaiDoUong;
        this.HinhAnh = HinhAnh;
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
     * @return the TenLoaiDoUong
     */
    public String getTenLoaiDoUong() {
        return TenLoaiDoUong;
    }

    /**
     * @param TenLoaiDoUong the TenLoaiDoUong to set
     */
    public void setTenLoaiDoUong(String TenLoaiDoUong) {
        this.TenLoaiDoUong = TenLoaiDoUong;
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
