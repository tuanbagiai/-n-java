/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTransfer;

import java.sql.Date;

/**
 *
 * @author HT
 */
public class KhuyenMai {
    
    private String MaKM;
    private String TenKM;
    private Date NgayBatDau;
    private Date NgayKetThuc;
    private String LoaiKM;
    private long TriGiaApDung;
    private long TriGiaKM;

    public KhuyenMai() 
    {  
        this.MaKM = "KM000";
        this.TenKM = "Không áp dụng";
        this.NgayBatDau = Date.valueOf("2017-01-01");
        this.NgayKetThuc = Date.valueOf("2020-01-01");
        this.LoaiKM = "Giảm tiền mặt";
        this.TriGiaApDung = 0;
        this.TriGiaKM = 0;
    }
    
    public KhuyenMai(String MaKM, String TenKM, Date NgayBatDau, Date NgayKetThuc, String LoaiKM, long TriGiaApDung, long TriGiaKM) 
    {
        this.MaKM = MaKM;
        this.TenKM = TenKM;
        this.NgayBatDau = NgayBatDau;
        this.NgayKetThuc = NgayKetThuc;
        this.LoaiKM = LoaiKM;
        this.TriGiaApDung = TriGiaApDung;
        this.TriGiaKM = TriGiaKM;
    }
    
    /**
     * @return the MaKM
     */
    public String getMaKM() {
        return MaKM;
    }

    /**
     * @param MaKM the MaKM to set
     */
    public void setMaKM(String MaKM) {
        this.MaKM = MaKM;
    }

    /**
     * @return the TenKM
     */
    public String getTenKM() {
        return TenKM;
    }

    /**
     * @param TenKM the TenKM to set
     */
    public void setTenKM(String TenKM) {
        this.TenKM = TenKM;
    }

    /**
     * @return the NgayBatDau
     */
    public Date getNgayBatDau() {
        return NgayBatDau;
    }

    /**
     * @param NgayBatDau the NgayBatDau to set
     */
    public void setNgayBatDau(Date NgayBatDau) {
        this.NgayBatDau = NgayBatDau;
    }

    /**
     * @return the NgayKetThuc
     */
    public Date getNgayKetThuc() {
        return NgayKetThuc;
    }

    /**
     * @param NgayKetThuc the NgayKetThuc to set
     */
    public void setNgayKetThuc(Date NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }

    /**
     * @return the LoaiKM
     */
    public String getLoaiKM() {
        return LoaiKM;
    }

    /**
     * @param LoaiKM the LoaiKM to set
     */
    public void setLoaiKM(String LoaiKM) {
        this.LoaiKM = LoaiKM;
    }

    /**
     * @return the TriGiaApDung
     */
    public long getTriGiaApDung() {
        return TriGiaApDung;
    }

    /**
     * @param TriGiaApDung the TriGiaApDung to set
     */
    public void setTriGiaApDung(long TriGiaApDung) {
        this.TriGiaApDung = TriGiaApDung;
    }

    /**
     * @return the TriGiaKM
     */
    public long getTriGiaKM() {
        return TriGiaKM;
    }

    /**
     * @param TriGiaKM the TriGiaKM to set
     */
    public void setTriGiaKM(long TriGiaKM) {
        this.TriGiaKM = TriGiaKM;
    }
    
}
