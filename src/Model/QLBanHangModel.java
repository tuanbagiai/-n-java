/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DataProvider.ConnectionManager;
import DataProvider.ConstantClass;
import DataTransfer.Ban;
import DataTransfer.DoUong;
import DataTransfer.KhuyenMai;
import DataTransfer.LoaiDoUong;
import DataTransfer.Order;
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
public class QLBanHangModel {
    
    private static QLBanHangModel instance;

    private QLBanHangModel() { }
    
    /**
     * @return the instance
     */
    public static QLBanHangModel getInstance() {
        if (instance == null)
        {
            instance = new QLBanHangModel();
        }
        
        return instance;
    }
    
    public ArrayList<Ban> getDSBan()
    {
        try 
        {
            ArrayList<Ban> result = new ArrayList<Ban>();
            String query = "SELECT * FROM BAN";
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            while (rs.next())
            {
                result.add(new Ban(rs.getString("MABAN"), rs.getString("TENBAN"), rs.getString("TRANGTHAI")));
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public ArrayList<LoaiDoUong> getDSLoaiDoUong()
    {
        try 
        {
            ArrayList<LoaiDoUong> result = new ArrayList<LoaiDoUong>();
            String query = "SELECT * FROM LOAIDOUONG";
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            while (rs.next())
            {
                result.add(new LoaiDoUong(rs.getString("MALOAIDOUONG"), rs.getString("TENLOAIDOUONG"), rs.getString("HINHANH")));
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public ArrayList<DoUong> getDSDoUong()
    {
        try 
        {
            ArrayList<DoUong> result = new ArrayList<DoUong>();
            String query = "SELECT * FROM DOUONG";
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            while (rs.next())
            {
                result.add(new DoUong(rs.getString("MADOUONG"), rs.getString("TENDOUONG"), 
                                    rs.getLong("GIABAN"), rs.getString("MALOAIDODUONG"), rs.getString("HINHANH")));
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public ArrayList<DoUong> getDSDoUong(String MaLoaiDoUong)
    {
        try 
        {
            ArrayList<DoUong> result = new ArrayList<DoUong>();
            String query = "SELECT * FROM DOUONG WHERE MALOAIDOUONG = ?";
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, new String[] { MaLoaiDoUong });
            
            while (rs.next())
            {
                result.add(new DoUong(rs.getString("MADOUONG"), rs.getString("TENDOUONG"), 
                                    rs.getLong("GIABAN"), rs.getString("MALOAIDOUONG"), rs.getString("HINHANH")));
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public boolean ThemOrder(Order order)
    {
        String query = "INSERT INTO ORDERS (MAORDER, NGAYLAP, MABAN, TRANGTHAI, TRIGIA, MAKM, PHAITRA) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        int RowEffect = ConnectionManager.getInstance().executeUpdate(query, new Object[] { order.getMaOrder(), order.getNgayLap(), order.getMaBan(), 
                                                                        order.getTrangThai(), order.getTriGia(), 
                                                                        order.getMaKM(), order.getPhaiTra() });
        
        if (RowEffect > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public Order getOrder(String MaOrder)
    {
        try 
        {
            String query = "SELECT * FROM ORDERS WHERE MAORDER = ?";
            Order result = new Order();
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, new String[] { MaOrder });
            
            if (rs.next())
            {
                result.setMaOrder(MaOrder);
                result.setNgayLap(rs.getDate("NGAYLAP"));
                result.setMaBan(String.valueOf(rs.getInt("MABAN")));
                result.setTrangThai(rs.getString("TRANGTHAI"));
                result.setTriGia(rs.getLong("TRIGIA"));
                result.setMaKM(rs.getString("MAKM"));
                result.setPhaiTra(rs.getLong("PHAITRA"));
                
                return result;
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
    
    public String getMaOrder(String MaBan)
    {
        try 
        {
            String query = "SELECT MAORDER FROM ORDERS WHERE MABAN = ? AND TRANGTHAI = N'Chưa thanh toán'";
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, new String[] { MaBan });
            
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
    
    public String getMaDoUong(String TenDoUong)
    {
        try 
        {
            String query = "SELECT MADOUONG FROM DOUONG WHERE TENDOUONG = ?";
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, new String[] { TenDoUong });
            
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
    
    public boolean ThemCTOrder(String MaOrder, Vector Records)
    {
        int length = Records.size();
        String query = "INSERT INTO CTORDERS(MAORDER, MADOUONG, GIABAN, SOLUONG, THANHTIEN) VALUES (?, ?, ?, ?, ?)";        
        for (int i = 0; i < length; i++)
        {
            Vector row = (Vector)Records.get(i);
            
            int rowEffect = ConnectionManager.getInstance().executeUpdate(query, new Object[] { MaOrder, getMaDoUong((String)row.get(0)), row.get(2), row.get(1), row.get(3) });

            if (rowEffect == 0)
            {
                return false;
            }
        }
        
        return true;
    }
    
    public String getTenDoUong(String MaDoUong)
    {
        try 
        {
            String query = "SELECT TENDOUONG FROM DOUONG WHERE MADOUONG = ?";
            
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
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Vector getCTOrder(String MaOrder)
    {
        try 
        {
            String query = "SELECT * FROM CTORDERS, ORDERS WHERE CTORDERS.MAORDER = ORDERS.MAORDER AND ORDERS.MAORDER = '" + MaOrder + "' AND ORDERS.TRANGTHAI = N'" + ConstantClass.ORDER_STATUS + "'";
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            Vector result = new Vector();
            
            while (rs.next())
            {
                Vector value = new Vector();
                value.add(this.getTenDoUong(rs.getString("MADOUONG")));
                value.add(rs.getInt("SOLUONG"));
                value.add(rs.getLong("GIABAN"));
                value.add(rs.getLong("THANHTIEN"));
                value.add(rs.getString("MAORDER"));
                
                result.add(value);
            }
            
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Vector getDSKMHienTai()
    {
        try 
        {
            String query = "SELECT * FROM KHUYENMAI WHERE GETDATE() BETWEEN NGAYBATDAU AND NGAYKETTHUC";
            Vector result = new Vector();
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            while (rs.next())
            {
                KhuyenMai row = new KhuyenMai();
                row.setMaKM(rs.getString("MAKM"));
                row.setTenKM(rs.getString("TENKM"));
                row.setNgayBatDau(rs.getDate("NGAYBATDAU"));
                row.setNgayKetThuc(rs.getDate("NGAYKETTHUC"));
                row.setLoaiKM(rs.getString("LOAIKM"));
                row.setTriGiaApDung(rs.getLong("TRIGIAAPDUNG"));
                row.setTriGiaKM(rs.getLong("TRIGIAKM"));
                
                result.add(row);
            }
            return result;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public String getMaKM(String TenKM)
    {
        try 
        {
            String query = "SELECT MAKM FROM KHUYENMAI WHERE TENKM = N'" + TenKM + "'";
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
    
    public long getTriGiaKM(String TenKM)
    {
        try 
        {
            String query = "SELECT TRIGIAKM FROM KHUYENMAI WHERE TENKM = N'" + TenKM + "'";
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            if (rs.next())
            {
                return rs.getLong(1);
            }
            else
            {
                return 0;
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public boolean CapNhatTrangThaiBan(String ID, String TrangThai)
    {
        String query = "UPDATE BAN SET TRANGTHAI = N'" + TrangThai + "' WHERE MABAN = " + ID;
        
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
    
    public Vector getDSNVL()
    {
        try 
        {
            String query = "SELECT * FROM NGUYENVATLIEU";
            Vector result = new Vector();
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, null);
            
            while (rs.next())
            {
                Vector value = new Vector();
                value.add(rs.getString("MANVL"));
                value.add(rs.getString("TENNVL"));
                value.add(rs.getInt("SOLUONG"));
                value.add(rs.getLong("DONGIA"));
                value.add(rs.getString("DONVITINH"));
                
                result.add(value);
            }
            
            return result;
        } 
        catch (SQLException ex) {
            Logger.getLogger(QLBanHangModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public KhuyenMai getKhuyenMai(String MaKM)
    {
        try 
        {
            String query = "SELECT * FROM KHUYENMAI WHERE MAKM = ?";
            
            ResultSet rs = ConnectionManager.getInstance().executeQuery(query, new String[] { MaKM });
            if (rs.next())
            {
                KhuyenMai result = new KhuyenMai();
                result.setMaKM(rs.getString("MAKM"));
                result.setTenKM(rs.getString("TENKM"));
                result.setNgayBatDau(rs.getDate("NGAYBATDAU"));
                result.setNgayKetThuc(rs.getDate("NGAYKETTHUC"));
                result.setLoaiKM(rs.getString("LOAIKM"));
                result.setTriGiaApDung(rs.getLong("TRIGIAAPDUNG"));
                result.setTriGiaKM(rs.getLong("TRIGIAKM"));
                
                return result;
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
    
    public boolean XoaToanBoCTOrder(String MaOrder)
    {
        String query = "DELETE FROM CTORDERS WHERE MAORDER = ?";
        
        int RowEffect = ConnectionManager.getInstance().executeUpdate(query, new String[] { MaOrder });
        
        if (RowEffect > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean XoaDoUongTrongOrder(String MaOrder, String MaDoUong)
    {
        String query = "DELETE FROM CTORDERS WHERE MAORDER = ? AND MADOUONG = ?";
        
        int RowEffect = ConnectionManager.getInstance().executeUpdate(query, new String[] { MaOrder, MaDoUong });
        
        if (RowEffect > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean CapNhatTrangThaiOrder(String MaOrder, String TrangThai)
    {
        String query = "UPDATE ORDERS SET TRANGTHAI = N'" + TrangThai + "' WHERE MAORDER = '" + MaOrder + "'";
        
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
    
    public boolean XoaBan(int ID)
    {
        String query = "DELETE FROM BAN WHERE MABAN = ?";
        
        int RowEffect = ConnectionManager.getInstance().executeUpdate(query, new Object[] { ID });
        
        if (RowEffect > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean ThemBan(int ID, String TenBan, String TrangThai)
    {
        String query = "INSERT INTO BAN(MABAN, TENBAN, TRANGTHAI) VALUES (" + String.valueOf(ID) + ", N'" + TenBan + "', N'" + TrangThai + "')";
        
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
