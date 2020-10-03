/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe;

import Controller.QLDUController;
import DataTransfer.DoUong;
import DataTransfer.LoaiDoUong;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author HT
 */
public class fmCapNhatDoUong extends javax.swing.JFrame {

    private DoUong Value;
    private JFileChooser fcOpenImage;
    private pnlDSDoUong main;
    private boolean isValueChanged = false;
    private String[] DSMaDoUong;
    private DefaultComboBoxModel DSDUModel;

    /**
     * Creates new form fmCapNhatDoUong
     */
    public fmCapNhatDoUong(pnlDSDoUong pnl, DoUong value) {
        initComponents();
        
        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-this.getSize().width/2,
                         Toolkit.getDefaultToolkit().getScreenSize().height/2-this.getSize().height/2 - 20);
        
        try {
            this.setIconImage(ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\img\\icon.png")));
        } catch (IOException ex) {
            Logger.getLogger(fmDangNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        main = pnl;
        this.Value = value;
        
        LoadDSLoaiDU();
        LoadDSMaDoUong();
        LoadDoUong();
        LoadEventValueChanged();
    }
    
    void LoadDSLoaiDU()
    {
        ArrayList<LoaiDoUong> DSDU = QLDUController.getInstance().LoadDSLoaiDoUong();
        int length = DSDU.size();
        DSDUModel = new DefaultComboBoxModel();        
        
        for (int i = 0; i < length; i++)
        {
            DSDUModel.addElement(DSDU.get(i).getMaLoaiDoUong());
        }

        cmbMaLoaiDoUong.setModel(DSDUModel);
    }
    
    void LoadDoUong()
    {
        txtMaDoUong.setText(Value.getMaDoUong());
        txtTenDoUong.setText(Value.getTenDoUong());
        txtGiaBan.setText(String.valueOf(Value.getGiaBan()));
        cmbMaLoaiDoUong.setSelectedItem(Value.getMaLoaiDoUong());
        lbHinhAnh.setIcon(ResizeImage(System.getProperty("user.dir") + Value.getHinhAnh()));
        lbHinhAnh.setToolTipText(System.getProperty("user.dir") + Value.getHinhAnh());
    }
    
    void LoadDSMaDoUong()
    {
        Vector DSDoUong = QLDUController.getInstance().LoadDSDoUong();
        int length = DSDoUong.size();
        DSMaDoUong = new String[length];
        for (int i = 0; i < length; i++)
        {
            DSMaDoUong[i] = (String)((Vector)DSDoUong.get(i)).get(0);
        }
    }

    void LoadEventValueChanged()
    {
        txtMaDoUong.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               isValueChanged = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isValueChanged = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isValueChanged = true;
            }
        });
        
        txtTenDoUong.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               isValueChanged = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isValueChanged = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isValueChanged = true;
            }
        });
        
        txtGiaBan.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               isValueChanged = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isValueChanged = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isValueChanged = true;
            }
        });
    }
    
    Icon ResizeImage(String url){
        
        ImageIcon img = null;
        try {
            BufferedImage brImg = ImageIO.read(new File(url));
            
            int x;
            int y = 150;
            
            int ix = brImg.getWidth();
            int iy = brImg.getHeight();
            x = (y * ix) / iy;
            
            img = new ImageIcon(brImg.getScaledInstance(x, y, brImg.SCALE_SMOOTH));
        } catch (IOException ex) {
            //JOptionPane.showMessageDialog(null, " Không tìm thấy hình ảnh!", "Lỗi", 0);
            img = new ImageIcon(System.getProperty("user.dir") + "\\src\\img\\no picture.png");
        }
        
        return img;
    }
    
    boolean SaveImage(String url)
    {
        try 
        {
            BufferedImage brImg = ImageIO.read(new File(url));
            
            ImageIO.write(brImg, "png", new File(System.getProperty("user.dir") + "\\src\\img\\Product\\" + txtMaDoUong.getText() + ".png"));
            
            return true;
        } 
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(null, " Không lưu được hình ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            return false;
        }
    }
    
    boolean validateData()
    {
        if (Long.valueOf(txtGiaBan.getText()) < 500)
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaDoUong = new javax.swing.JTextField();
        txtTenDoUong = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        cmbMaLoaiDoUong = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbError = new javax.swing.JLabel();
        lbHinhAnh = new javax.swing.JLabel();
        btnSuaAnh = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cập nhật đồ uống");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Cập nhật đồ uống");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Mã đồ uống");

        txtMaDoUong.setEditable(false);
        txtMaDoUong.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtTenDoUong.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtGiaBan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        cmbMaLoaiDoUong.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cmbMaLoaiDoUong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Mã loại đồ uống");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Giá bán");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Tên đồ uống");

        lbError.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbError.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbMaLoaiDoUong, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtMaDoUong)
                                .addComponent(txtTenDoUong, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
                        .addGap(5, 5, 5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbError, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbError, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbMaLoaiDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        lbHinhAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnSuaAnh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnSuaAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/change image - small.png"))); // NOI18N
        btnSuaAnh.setText("Sửa ảnh");
        btnSuaAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaAnhMouseClicked(evt);
            }
        });

        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update - small.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCapNhatMouseClicked(evt);
            }
        });

        btnThoat.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThoatMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSuaAnh)
                                .addGap(20, 20, 20))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(btnCapNhat)
                        .addGap(65, 65, 65)
                        .addComponent(btnThoat))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaAnh))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCapNhat)
                    .addComponent(btnThoat))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaAnhMouseClicked
        // TODO add your handling code here:
        int select = fcOpenImage.showOpenDialog(this);

        if (select == JFileChooser.APPROVE_OPTION)
        {
            String url = fcOpenImage.getSelectedFile().getPath();
            lbHinhAnh.setIcon(ResizeImage(url));
            lbHinhAnh.setToolTipText(url);
        }
    }//GEN-LAST:event_btnSuaAnhMouseClicked

    private void btnCapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseClicked
        // TODO add your handling code here:
        System.out.println("Valuechanged = " + isValueChanged);
        if (isValueChanged)
        {
            if (validateData() == false)
            {
                JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn cập nhật hay không?", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (select == JOptionPane.YES_OPTION)
            {
                boolean result = QLDUController.getInstance().CapNhatDoUong(txtMaDoUong.getText(), txtTenDoUong.getText(), 
                                                                    txtGiaBan.getText(), (String)cmbMaLoaiDoUong.getSelectedItem(), 
                                                                    "\\src\\img\\Product\\" + txtMaDoUong.getText() + ".png");
                
                if (result)
                {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    isValueChanged = false;
                    main.LoadDSDoUong();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Không tìm thấy thay đổi nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCapNhatMouseClicked

    private void btnThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThoatMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnThoatMouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(fmCapNhatDoUong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(fmCapNhatDoUong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(fmCapNhatDoUong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(fmCapNhatDoUong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new fmCapNhatDoUong().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnSuaAnh;
    private javax.swing.JButton btnThoat;
    private javax.swing.JComboBox<String> cmbMaLoaiDoUong;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbError;
    private javax.swing.JLabel lbHinhAnh;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaDoUong;
    private javax.swing.JTextField txtTenDoUong;
    // End of variables declaration//GEN-END:variables
}
