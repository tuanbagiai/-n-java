/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProvider;

import DataTransfer.DoUong;
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
public class ProductButton extends JButton {
    private DoUong douong;
    private Font font;

    public ProductButton(DoUong douong, String ImagePath) {
        this.setSize(ConstantClass.WIDTH_SIZE_PRODUCT, ConstantClass.HEIGHT_SIZE_PRODUCT);
        this.setIcon(new ImageIcon(ImagePath));
        this.setText(douong.getTenDoUong());
        this.setHorizontalAlignment(JButton.CENTER);
        this.setVerticalAlignment(JButton.CENTER);
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setVerticalTextPosition(JButton.BOTTOM);
        
        font = new Font("Segoe UI", 0, 12);
        this.setFont(font);
        
        this.douong = douong;
    }
    
    Icon ResizeImage(String url){
        
        ImageIcon img = null;
        try {
            BufferedImage brImg = ImageIO.read(new File(url));
            
            int x = ConstantClass.WIDTH_SIZE_PRODUCT;
            int y = ConstantClass.HEIGHT_SIZE_PRODUCT;
            
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
     * @return the DoUong
     */
    public DoUong getDoUong()
    {
        return douong;
    }

}
