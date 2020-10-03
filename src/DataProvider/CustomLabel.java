/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProvider;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author HT
 */
public class CustomLabel extends JLabel {
    
    private String Id;
    
    /**
     * For Product
     * @param content
     * @param id
     * @param image 
     */
    public CustomLabel(String content, String id, Icon image){
        
        this.setSize(ConstantClass.WIDTH_SIZE_PRODUCT, ConstantClass.HEIGHT_SIZE_PRODUCT);
        this.setIconTextGap(4);
        
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
        
        this.setText(content);
        this.Id = id;
    }
    
    /**
     * For Table
     * @param id
     * @param image 
     */
    public CustomLabel(String id, String ImagePath){

        this.setSize(ConstantClass.WIDTH_SIZE_TABLE, ConstantClass.HEIGHT_SIZE_TABLE); // 128 x 128
        
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        
        this.setIcon(ResizeImage(ImagePath));
        
        this.setText(null);
        this.Id = id;
    }

    Icon ResizeImage(String url){
        
        ImageIcon img = null;
        try {
            BufferedImage brImg = ImageIO.read(new File(url));
            
            int x = ConstantClass.WIDTH_SIZE_TABLE;
            int y = ConstantClass.HEIGHT_SIZE_TABLE;
            
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
     * @return the Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(String Id) {
        this.Id = Id;
    }
}
