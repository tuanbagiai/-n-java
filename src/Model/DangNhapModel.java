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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HT
 */
public class DangNhapModel {
    
    private DangNhapModel() { }
    
    private static DangNhapModel instance;

    /**
     * @return the instance
     */
    public static DangNhapModel getInstance() {
        if (instance == null)
            instance = new DangNhapModel();
        
        return instance;
    }
    
    public NhanVien XacThucDangNhap(String username, String password)
    {
        try {
            String query = "SELECT * FROM NHANVIEN WHERE MANV = ? AND PASSWORDS = ?";
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, new String[] {username, password});
            
            if (rs.next() == false)
            {
                return null;
            }
            
            rs.first();
            
            NhanVien nhanvien = new NhanVien(rs.getString("MANV"), rs.getString("HOTEN"), rs.getDate("NGAYSINH"), 
                                            rs.getString("DIACHI"), rs.getString("CMND"), rs.getString("SDT"), rs.getDate("NGAYVAOLAM"), 
                                            rs.getString("PASSWORDS"), rs.getString("LOAINV"));
            
            return nhanvien;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DangNhapModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
}
