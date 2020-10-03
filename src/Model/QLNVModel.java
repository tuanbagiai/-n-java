/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DataProvider.ConnectionManager;
import DataTransfer.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HT
 */
public class QLNVModel {
    
    private static QLNVModel instance;

    private QLNVModel() { }
    
    /**
     * @return the instance
     */
    public static QLNVModel getInstance() {
        if (instance == null)
        {
            instance = new QLNVModel();
        }
        
        return instance;
    }
    
    public Vector LoadDSNV()
    {
        try 
        {
            String query = "SELECT * FROM NHANVIEN";
            Vector result = new Vector();
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            while (rs.next())
            {
                Vector value = new Vector();
                value.add(rs.getString("MANV"));
                value.add(rs.getString("HOTEN"));
                value.add(rs.getDate("NGAYSINH").toString());
                value.add(rs.getString("DIACHI"));
                value.add(rs.getString("CMND"));
                value.add(rs.getString("SDT"));
                value.add(rs.getDate("NGAYVAOLAM").toString());
                value.add(rs.getString("PASSWORDS"));
                value.add(rs.getString("LOAINV"));
                
                result.add(value);
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLNVModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public boolean CapNhatThongTinNV(NhanVien value)
    {
        String query = "UPDATE NHANVIEN SET HOTEN = N'" + value.getHoTen() + "', NGAYSINH = '" + String.valueOf(value.getNgaySinh())
                            + "', DIACHI = N'" + value.getDiaChi() + "', CMND = '" + value.getCMND() + "', NGAYVAOLAM = '" 
                                + String.valueOf(value.getNgayVaoLam()) + "', PASSWORDS = '" + value.getPassword() 
                                    + "', LOAINV = '" + value.getLoaiNV() + "' WHERE MANV = '" + value.getMaNV() + "'";
        
        int RowEffect = ConnectionManager.getInstance().executeUpdate(query, null);
        
        if (RowEffect > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean CapNhatMatKhau(String MaNV, String MatKhau)
    {
        String query = "UPDATE NHANVIEN SET PASSWORDS = '" + MatKhau + "' WHERE MANV = '" + MaNV + "'";
        
        int RowEffect = ConnectionManager.getInstance().executeUpdate(query, null);
        
        if (RowEffect > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public String getLoaiNV(String MaNV)
    {
        try 
        {
            String query = "SELECT LOAINV FROM NHANVIEN WHERE MANV = '" + MaNV + "'";
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            if (rs.next())
            {
                return rs.getString(1);
            }
            else
            {
                return null;
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public String getPasswords(String MaNV)
    {
        try 
        {
            String query = "SELECT PASSWORDS FROM NHANVIEN WHERE MANV = '" + MaNV + "'";
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            if (rs.next())
            {
                return rs.getString(1);
            }
            else
            {
                return null;
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
