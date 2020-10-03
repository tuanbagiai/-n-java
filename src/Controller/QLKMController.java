/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataTransfer.KhuyenMai;
import Model.QLKMModel;
import java.sql.Date;
import java.util.Vector;

/**
 *
 * @author HT
 */
public class QLKMController {
    private QLKMController() {  }
    
    private static QLKMController instance;

    /**
     * @return the instance
     */
    public static QLKMController getInstance() {
        if (instance == null)
        {
            instance = new QLKMController();
        }
        
        return instance;
    }
    
    public Vector getDSKM()
    {
        return QLKMModel.getInstance().getDSKM();
    }
    
    public boolean ThemKM(String MaKM, String TenKM, Date NgayBatDau, Date NgayKetThuc, String LoaiKM, long TriGiaApDung, long TriGiaKM)
    {
        KhuyenMai value = new KhuyenMai(MaKM, TenKM, NgayBatDau, NgayKetThuc, LoaiKM, TriGiaApDung, TriGiaKM);
        
        return QLKMModel.getInstance().ThemKM(value);
    }
}
