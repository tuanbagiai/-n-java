/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataTransfer.NhanVien;
import Model.QLNVModel;
import java.util.Vector;

/**
 *
 * @author HT
 */
public class QLNVController {
    
    private static QLNVController instance;

    private QLNVController() {  }
    
    /**
     * @return the instance
     */
    public static QLNVController getInstance() {
        if (instance == null)
        {
            instance = new QLNVController();
        }
        
        return instance;
    }
    
    public Vector LoadDSNV()
    {
        return QLNVModel.getInstance().LoadDSNV();
    }
    
    public boolean CapNhatThongTinNV(NhanVien value)
    {
        return QLNVModel.getInstance().CapNhatThongTinNV(value);
    }
    
    public boolean CapNhatMatKhau(String MaNV, String MatKhau)
    {
        return QLNVModel.getInstance().CapNhatMatKhau(MaNV, MatKhau);
    }
    
    public String getPasswords(String MaNV)
    {
        return QLNVModel.getInstance().getPasswords(MaNV);
    }
    
    public String getLoaiNV(String MaNV)
    {
        return QLNVModel.getInstance().getLoaiNV(MaNV);
    }
}
