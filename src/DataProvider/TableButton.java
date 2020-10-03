/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProvider;

import java.awt.Color;
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
public class TableButton extends JButton{

    private String ID;
    private static String lastID;
    private Font font;
    private String TrangThai = ConstantClass.FREE_STATUS;
    private boolean Save = false;
    private String MaOrderCuaBan;

    public TableButton(String id, String TrangThai, String MaOrderCuaBan, String ImagePath) 
    {
        this.setSize(ConstantClass.WIDTH_SIZE_TABLE, ConstantClass.HEIGHT_SIZE_TABLE);
        this.setIcon(ResizeImage(ImagePath));
        this.setText("");
        this.setHorizontalAlignment(JButton.CENTER);
        this.setVerticalAlignment(JButton.CENTER);
        
        this.setID(id);
        this.lastID = id;
        this.setText(id);
        this.setVerticalTextPosition(BOTTOM);
        this.setHorizontalTextPosition(CENTER);
        this.MaOrderCuaBan = MaOrderCuaBan;
        
        ChangeColor(TrangThai);
        
        font = new Font("Segoe UI", Font.BOLD, 12);
        this.setFont(font);
        
    }
    
    void ChangeColor(String TrangThai)
    {
        this.TrangThai = TrangThai;
        if (TrangThai.equals("Trống"))
        {
            this.setBackground(Color.decode(ConstantClass.COLOR_FREE));
        }
        else if (TrangThai.equals("Đang phục vụ"))
        {
            this.setBackground(Color.decode(ConstantClass.COLOR_SERVING));
        }
        else
        {
            this.setBackground(Color.decode(ConstantClass.COLOR_WAITING));
        }
    }
    
    /**
     * @return the lastID
     */
    public static String getLastID() {
        return lastID;
    }
    
    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
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
     * @return the TrangThai
     */
    public String getTrangThai() {
        return TrangThai;
    }

    /**
     * @param TrangThai the TrangThai to set
     */
    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
        ChangeColor(TrangThai);
    }

    /**
     * @return the Save
     */
    public boolean isSave() {
        return Save;
    }

    /**
     * @param Save the Save to set
     */
    public void setSave(boolean Save) {
        this.Save = Save;
    }

    /**
     * @return the MaOrderCuaBan
     */
    public String getMaOrderCuaBan() {
        return MaOrderCuaBan;
    }

    /**
     * @param MaOrderCuaBan the MaOrderCuaBan to set
     */
    public void setMaOrderCuaBan(String MaOrderCuaBan) {
        this.MaOrderCuaBan = MaOrderCuaBan;
    }

}
