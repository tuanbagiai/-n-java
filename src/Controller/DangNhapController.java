/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataTransfer.NhanVien;
import Model.DangNhapModel;

/**
 *
 * @author HT
 */
public class DangNhapController {
    
    private DangNhapController() { }
    
    private static DangNhapController instance;
    
    public NhanVien XacThucDangNhap(String username, String password) {
        NhanVien nhanvien = DangNhapModel.getInstance().XacThucDangNhap(username, password);
        
        return nhanvien;
    }

    /**
     * @return the instance
     */
    public static DangNhapController getInstance() {
        if (instance == null)
            instance = new DangNhapController();
        
        return instance;
    }

}
