/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProvider;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author HT
 */
public class ProductTypeButton extends JButton{
    private String MaLoaiDU;
    private String TenLoaiDU;
    private Font font;

    public ProductTypeButton(String MaLoaiDU, String TenLoaiDU, String ImagePath) {
        this.setSize(ConstantClass.WIDTH_SIZE_PRODUCT_TYPE, ConstantClass.HEIGHT_SIZE_PRODUCT_TYPE);
        this.setIcon(new ImageIcon(ImagePath));
        this.setText(TenLoaiDU);
        this.setHorizontalAlignment(JButton.CENTER);
        this.setVerticalAlignment(JButton.CENTER);
        this.setHorizontalTextPosition(JButton.RIGHT);
        this.setVerticalTextPosition(JButton.CENTER);
        
        font = new Font("Segoe UI", 0, 16);
        this.setFont(font);
        
        this.MaLoaiDU = MaLoaiDU;
        this.TenLoaiDU = TenLoaiDU;
    }
    
    Icon ResizeImage(String url){
        
        ImageIcon img = null;
        try {
            BufferedImage brImg = ImageIO.read(new File(url));
            
            int x = ConstantClass.WIDTH_SIZE_PRODUCT_TYPE;
            int y = ConstantClass.HEIGHT_SIZE_PRODUCT_TYPE;
            
            int ix = brImg.getWidth();
            int iy = brImg.getHeight();
            int dx = 0;
            int dy = 0;
            
            if (x / y > ix / iy) {
                dy = y;
                dx = dy * ix / iy;
            } else {
                dx = x;
                dy = dx * iy / ix;
            }
            
            img = new ImageIcon(brImg.getScaledInstance(dx, dy, brImg.SCALE_SMOOTH));
        } catch (IOException ex) {
            //JOptionPane.showMessageDialog(null, " Không tìm thấy hình ảnh!", "Lỗi", 0);
            img = new ImageIcon("D:\\Java\\Java Project\\NetBeans\\QuanLyQuanCafe\\src\\img\\no picture.png");
        }
        
        return img;
    }

    /**
     * @return the MaLoaiDU
     */
    public String getMaLoaiDU() {
        return MaLoaiDU;
    }

    /**
     * @return the TenLoaiDU
     */
    public String getTenLoaiDU() {
        return TenLoaiDU;
    }

    
}
