/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DataProvider.ConnectionManager;
import DataTransfer.KhuyenMai;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HT
 */
public class QLKMModel {

    private QLKMModel() {  }
    
    private static QLKMModel instance;

    /**
     * @return the instance
     */
    public static QLKMModel getInstance() {
        if (instance == null)
        {
            instance = new QLKMModel();
        }
        
        return instance;
    }
    
    public Vector getDSKM()
    {
        try 
        {
            String query = "SELECT * FROM KHUYENMAI";
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            Vector result = new Vector();
            
            while (rs.next())
            {
                Vector row = new Vector();
                
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getDate(3));
                row.add(rs.getDate(4));
                row.add(rs.getString(5));
                row.add(rs.getLong(6));
                row.add(rs.getLong(7));
                
                result.add(row);
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLKMModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean ThemKM(KhuyenMai KM)
    {
        String query = "INSERT INTO KHUYENMAI VALUES ('" + KM.getMaKM() + "', N'" + KM.getTenKM() + "', '" + String.valueOf(KM.getNgayBatDau()) + "', '" + KM.getNgayKetThuc() + "', N'" + KM.getLoaiKM() + "', " + String.valueOf(KM.getTriGiaApDung()) + ", " + String.valueOf(KM.getTriGiaKM()) + ")";
        
        int Row = ConnectionManager.getInstance().executeUpdate(query, null);
        
        if (Row > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
