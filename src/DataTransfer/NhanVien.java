/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTransfer;

import java.util.Date;

/**
 *
 * @author HT
 */
public class NhanVien {

    private String MaNV;
    private String HoTen;
    private Date NgaySinh;
    private String DiaChi;
    private String CMND;
    private String SDT;
    private Date NgayVaoLam;
    private String Password;
    private String LoaiNV;

    public NhanVien() {

    }


    public NhanVien(String MaNV, String HoTen, Date NgaySinh, String DiaChi, String CMND, String SDT, Date NgayVaoLam, String Password, String LoaiNV) 
    {
        this.MaNV = MaNV;
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.CMND = CMND;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.NgayVaoLam = NgayVaoLam;
        this.Password = Password;
        this.LoaiNV = LoaiNV;
    }

    
    /**
     * @return the MaNV
     */

    public String getMaNV() {
        return MaNV;
    }

    /**
     * @param MaNV the MaNV to set
     */
    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    /**
     * @return the HoTen
     */
    public String getHoTen() {
        return HoTen;
    }

    /**
     * @param HoTen the HoTen to set
     */
    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    /**
     * @return the NgaySinh
     */
    public Date getNgaySinh() {
        return NgaySinh;
    }

    /**
     * @param NgaySinh the NgaySinh to set
     */
    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    /**
     * @return the DiaChi
     */
    public String getDiaChi() {
        return DiaChi;
    }

    /**
     * @param DiaChi the DiaChi to set
     */
    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    /**
     * @return the CMND
     */
    public String getCMND() {
        return CMND;
    }

    /**
     * @param CMND the CMND to set
     */
    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    /**
     * @return the SDT
     */
    public String getSDT() {
        return SDT;
    }

    /**
     * @param SDT the SDT to set
     */
    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    /**
     * @return the NgayVaoLam
     */
    public Date getNgayVaoLam() {
        return NgayVaoLam;
    }

    /**
     * @param NgayVaoLam the NgayVaoLam to set
     */
    public void setNgayVaoLam(Date NgayVaoLam) {
        this.NgayVaoLam = NgayVaoLam;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password the Password to set
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * @return the LoaiNV
     */
    public String getLoaiNV() {
        return LoaiNV;
    }

    /**
     * @param LoaiNV the LoaiNV to set
     */
    public void setLoaiNV(String LoaiNV) {
        this.LoaiNV = LoaiNV;
    }

}
