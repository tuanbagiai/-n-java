/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DataProvider.ConnectionManager;
import DataTransfer.DoUong;
import DataTransfer.LoaiDoUong;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HT
 */
public class QLDUModel {
    
    private static QLDUModel instance;

    /**
     * @return the instance
     */
    public static QLDUModel getInstance() {
        if (instance == null)
        {
            instance = new QLDUModel();
        }
        
        return instance;
    }
    
    private QLDUModel() { }
    
    public ArrayList<LoaiDoUong> LoadDSLoaiDU()
    {
        try 
        {
            String query = "SELECT * FROM LOAIDOUONG";
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            ArrayList<LoaiDoUong> result = new ArrayList<LoaiDoUong>();
            
            while (rs.next())
            {
                LoaiDoUong value = new LoaiDoUong(rs.getString("MALOAIDOUONG"), rs.getString("TENLOAIDOUONG"), rs.getString("HINHANH"));
                result.add(value);
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLDUModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Vector LoadDSLoaiDoUong()
    {
        try 
        {
            String query = "SELECT * FROM LOAIDOUONG";
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            Vector result = new Vector();
            
            while (rs.next())
            {
                Vector value = new Vector();
                value.add(rs.getString("MALOAIDOUONG"));
                value.add(rs.getString("TENLOAIDOUONG"));
                value.add(rs.getString("HINHANH"));
                
                result.add(value);
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLDUModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Vector LoadDSDoUong()
    {
        try 
        {
            String query = "SELECT * FROM DOUONG";
            Vector result = new Vector();
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            while (rs.next())
            {
                Vector value = new Vector();
                value.add(rs.getString("MADOUONG"));
                value.add(rs.getString("TENDOUONG"));
                value.add(rs.getLong("GIABAN"));
                value.add("MALOAIDOUONG");
                value.add(rs.getString("HINHANH"));
                
                result.add(value);
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLDUModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Vector LoadDSDoUong(String MaLoaiDoUong)
    {
        try 
        {
            String query = "SELECT * FROM DOUONG WHERE MALOAIDOUONG = ?";
            Vector result = new Vector();
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, new String[] { MaLoaiDoUong });
            
            while (rs.next())
            {
                Vector value = new Vector();
                value.add(rs.getString("MADOUONG"));
                value.add(rs.getString("TENDOUONG"));
                value.add(rs.getLong("GIABAN"));
                value.add(MaLoaiDoUong);
                value.add(rs.getString("HINHANH"));
                
                result.add(value);
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLDUModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public String getImageURL(String MaDoUong)
    {
        try 
        {
            String query = "SELECT HINHANH FROM DOUONG WHERE MADOUONG = ?";
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, new String[] { MaDoUong });
            
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
            Logger.getLogger(QLDUModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Vector TimKiemTheoTen(String NoiDungTimKiem)
    {
        try 
        {
            String query = "SELECT * FROM DOUONG WHERE dbo.fuConvertToUnsign1(TENDOUONG) LIKE N'%' + dbo.fuConvertToUnsign1(N'" + NoiDungTimKiem + "') + '%'";
            Vector result = new Vector();
            System.out.println("query = " + query);
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            while (rs.next())
            {
                Vector value = new Vector();
                
                value.add(rs.getString("MADOUONG"));
                value.add(rs.getString("TENDOUONG"));
                value.add(rs.getLong("GIABAN"));
                value.add("MALOAIDOUONG");
                value.add(rs.getString("HINHANH"));
                
                result.add(value);
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLDUModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public boolean ThemDoUong(DoUong value)
    {
        String query = "INSERT INTO DOUONG(MADOUONG, TENDOUONG, GIABAN, MALOAIDOUONG, HINHANH) VALUES (?, N'" + value.getTenDoUong() + "', ?, ?, ?)";
        
        int RowEffect = ConnectionManager.getInstance().executeUpdate(query, new Object[] { value.getMaDoUong(), value.getGiaBan(), 
                                                                                    value.getMaLoaiDoUong(), value.getHinhAnh() });
        
        if (RowEffect > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean XoaDoUong(String MaDoUong)
    {
        String query = "DELETE FROM DOUONG WHERE MADOUONG = '" + MaDoUong + "'";
        
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
    
    public boolean CapNhatDoUong(DoUong value)
    {
        String query = "UPDATE DOUONG SET TENDOUONG = N'" + value.getTenDoUong() 
                        + "', GIABAN = " + String.valueOf(value.getGiaBan()) + ", MALOAIDOUONG = '" + value.getMaLoaiDoUong() 
                            + "', HINHANH = '" + value.getHinhAnh() + "' WHERE MADOUONG = '" + value.getMaDoUong() + "'";
        
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
}
