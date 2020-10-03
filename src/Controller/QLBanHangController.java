/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataProvider.ConnectionManager;
import DataProvider.ConstantClass;
import DataTransfer.Ban;
import DataTransfer.DoUong;
import DataTransfer.KhuyenMai;
import DataTransfer.LoaiDoUong;
import DataTransfer.Order;
import Model.QLBanHangModel;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author HT
 */
public class QLBanHangController {
    
    private static QLBanHangController instance;
    
    /**
     * @return the instance
     */
    public static QLBanHangController getInstance() {
        if (instance == null)
        {
            instance = new QLBanHangController();
        }
        
        return instance;
    }
    
    private QLBanHangController() { }
    
   public ArrayList<Ban> getDSBan()
   {
       return QLBanHangModel.getInstance().getDSBan();
   }
   
   public ArrayList<LoaiDoUong> getDSLoaiDoUong()
   {
       return QLBanHangModel.getInstance().getDSLoaiDoUong();
   }
   
   public ArrayList<DoUong> getDSDoUong()
   {
       return QLBanHangModel.getInstance().getDSDoUong();
   }
   
   public ArrayList<DoUong> getDSDoUong(String MaLoaiDoUong)
   {
       return QLBanHangModel.getInstance().getDSDoUong(MaLoaiDoUong);
   }
    
   public boolean ThemOrder(String MaBan, String TrangThai, long TriGia, String MaKM, long PhaiTra)
   {
       String MaOrder = "SO";
       int id = Order.getLastID() + 1;
       if (id < 10 && id >= 0)
       {
           MaOrder = MaOrder + "00" + String.valueOf(id);
       }
       else if (id <= 99)
       {
           MaOrder = MaOrder + "0" + String.valueOf(id);
       }
       else if (id <= 999)
       {
           MaOrder = MaOrder + String.valueOf(id);
       }
       
       java.util.Date now = new java.util.Date();
       
       Date NgayLap = new Date(now.getTime());
       
       Order order = new Order(MaOrder, NgayLap, MaBan, TrangThai, TriGia, MaKM, PhaiTra);
       return QLBanHangModel.getInstance().ThemOrder(order);
   }
   
   public Order getOrder(String MaOrder)
   {
       return QLBanHangModel.getInstance().getOrder(MaOrder);
   }
   
   public String getMaOrder(String MaBan)
   {
       return QLBanHangModel.getInstance().getMaOrder(MaBan);
   }
   
   public boolean ThemCTOrder(String MaOrder, Vector Records)
   {
        return QLBanHangModel.getInstance().ThemCTOrder(MaOrder, Records);
   }
   
   public Vector getCTOrder(String MaOrder)
   {
       return QLBanHangModel.getInstance().getCTOrder(MaOrder);
   }
   
    public String getMaKM(String TenKM)
    {
        return QLBanHangModel.getInstance().getMaKM(TenKM);
    }
    
    public Vector getDSTenKM()
    {
        Vector DSKM = QLBanHangModel.getInstance().getDSKMHienTai();
        int length = DSKM.size();
        Vector result = new Vector();
                
        for (int i = 0; i < length; i++)
        {
            result.add(((KhuyenMai)DSKM.get(i)).getTenKM());
        }
        
        return result;
    }
    
    public long getTriGiaKM(String TenKM)
    {
        return QLBanHangModel.getInstance().getTriGiaKM(TenKM);
    }
    
    public boolean CapNhatTrangThaiBan(String ID, String TrangThai)
    {
        return QLBanHangModel.getInstance().CapNhatTrangThaiBan(ID, TrangThai);
    }
    
    public Vector LoadDSNVL()
    {
        return QLBanHangModel.getInstance().getDSNVL();
    }
    
    public KhuyenMai getKhuyenMai(String MaKM)
    {
        return QLBanHangModel.getInstance().getKhuyenMai(MaKM);
    }
    
    public KhuyenMai getKMThichHop(long TriGia)
    {
        Vector DSKM = QLBanHangModel.getInstance().getDSKMHienTai();
        KhuyenMai result = new KhuyenMai();
        long TriGiaThichHop = 0;
        int length = DSKM.size();
        
        for (int i = 0; i < length; i++)
        {
            long TriGiaApDung = ((KhuyenMai)DSKM.get(i)).getTriGiaApDung();
            if (TriGia >= TriGiaApDung && TriGiaThichHop < TriGiaApDung)
            {
                TriGiaThichHop = TriGiaApDung;
                result = ((KhuyenMai)DSKM.get(i));
            }
        }
        
        return result;
    }
    
    public boolean XoaToanBoCTOrder(String MaOrder)
    {
        return QLBanHangModel.getInstance().XoaToanBoCTOrder(MaOrder);
    }
    
    public boolean XoaDoUongTrongOrder(String MaOrder, String MaDoUong)
    {
        return QLBanHangModel.getInstance().XoaDoUongTrongOrder(MaOrder, MaDoUong);
    }
    
    public String getMaDoUong(String TenDoUong)
    {
        return QLBanHangModel.getInstance().getMaDoUong(TenDoUong);
    }
    
    public boolean CapNhatTrangThaiOrder(String MaOrder, String TrangThai)
    {
        return QLBanHangModel.getInstance().CapNhatTrangThaiOrder(MaOrder, TrangThai);
    }
    
    public boolean XoaBan(int ID)
    {
        return QLBanHangModel.getInstance().XoaBan(ID);
    }
    
    public boolean ThemBan(int ID)
    {
        String TenBan = "Bàn số " + String.valueOf(ID);
        String TrangThai = ConstantClass.FREE_STATUS;
        
        return QLBanHangModel.getInstance().ThemBan(ID, TenBan, TrangThai);
    }
}
