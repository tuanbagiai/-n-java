/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTransfer;

import DataProvider.ConnectionManager;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HT
 */
public class Order {

    private String MaOrder;
    private Date NgayLap;
    private String MaBan;
    private String TrangThai;
    private long TriGia;
    private String MaKM;
    private long PhaiTra;
    
    public Order()
    {
        
    }

    public Order(String MaOrder, Date NgayLap, String MaBan, String TrangThai, long TriGia, String MaKM, long PhaiTra) {
        this.MaOrder = MaOrder;
        this.NgayLap = NgayLap;
        this.MaBan = MaBan;
        this.TrangThai = TrangThai;
        this.TriGia = TriGia;
        this.MaKM = MaKM;
        this.PhaiTra = PhaiTra;
    }
    
    /**
     * @return the LastID
     */
    public static int getLastID() {
        try 
        {
            int LastID = -1;
            String MaOrder = "";
            String query = "SELECT MAORDER FROM ORDERS";
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            if (rs.last())
                MaOrder = rs.getString(1);
            System.out.println("MaOrder = " + MaOrder);
            if (MaOrder.isEmpty() == false)
            {
                LastID = Integer.valueOf(MaOrder.substring(2));
            }
            System.out.println("LastID = " + LastID);
            return LastID;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    /**
     * @return the MaOrder
     */
    public String getMaOrder() {
        return MaOrder;
    }

    /**
     * @param MaOrder the MaOrder to set
     */
    public void setMaOrder(String MaOrder) {
        this.MaOrder = MaOrder;
    }

    /**
     * @return the NgayLap
     */
    public Date getNgayLap() {
        return NgayLap;
    }

    /**
     * @param NgayLap the NgayLap to set
     */
    public void setNgayLap(Date NgayLap) {
        this.NgayLap = NgayLap;
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

    /**
     * @return the TriGia
     */
    public long getTriGia() {
        return TriGia;
    }

    /**
     * @param TriGia the TriGia to set
     */
    public void setTriGia(long TriGia) {
        this.TriGia = TriGia;
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
     * @return the PhaiTra
     */
    public long getPhaiTra() {
        return PhaiTra;
    }

    /**
     * @param PhaiTra the PhaiTra to set
     */
    public void setPhaiTra(long PhaiTra) {
        this.PhaiTra = PhaiTra;
    }

    
}
