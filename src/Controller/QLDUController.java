/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataTransfer.DoUong;
import DataTransfer.LoaiDoUong;
import Model.QLDUModel;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author HT
 */
public class QLDUController {
    
    private static QLDUController instance;

    private QLDUController() { }
    
    /**
     * @return the instance
     */
    public static QLDUController getInstance() {
        if (instance == null)
        {
            instance = new QLDUController();
        }
        
        return instance;
    }
    
    public ArrayList<LoaiDoUong> LoadDSLoaiDoUong()
    {
        return QLDUModel.getInstance().LoadDSLoaiDU();
    }
    
    public Vector LoadDSDoUong()
    {
        return QLDUModel.getInstance().LoadDSDoUong();
    }
    
    public Vector LoadDSDoUong(String MaLoaiDoUong)
    {
        return QLDUModel.getInstance().LoadDSDoUong(MaLoaiDoUong);
    }
    
    public String getImageURL(String MaDoUong)
    {
        return QLDUModel.getInstance().getImageURL(MaDoUong);
    }
    
    public Vector TimKiemTheoTen(String NoiDungTimKiem)
    {
        return QLDUModel.getInstance().TimKiemTheoTen(NoiDungTimKiem);
    }
    
    public boolean ThemDoUong(String MaDoUong, String TenDoUong, String GiaBan, String MaLoaiDoUong, String HinhAnh)
    {
        DoUong value = new DoUong();
        value.setMaDoUong(MaDoUong);
        value.setTenDoUong(TenDoUong);
        value.setGiaBan(Long.valueOf(GiaBan));
        value.setMaLoaiDoUong(MaLoaiDoUong);
        value.setHinhAnh(HinhAnh);
        
        return QLDUModel.getInstance().ThemDoUong(value);
    }
    
    public boolean XoaDoUong(String MaDoUong)
    {
        return QLDUModel.getInstance().XoaDoUong(MaDoUong);
    }
    
    public boolean CapNhatDoUong(String MaDoUong, String TenDoUong, String GiaBan, String MaLoaiDoUong, String HinhAnh)
    {
        DoUong value = new DoUong(MaDoUong, TenDoUong, Long.valueOf(GiaBan), MaLoaiDoUong, HinhAnh);
        
        return QLDUModel.getInstance().CapNhatDoUong(value);
    }
    
    public Vector LoadDSLoaiDU()
    {
        return QLDUModel.getInstance().LoadDSLoaiDoUong();
    }
}
