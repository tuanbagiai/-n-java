/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe;

import Controller.QLDUController;
import DataProvider.ConstantClass;
import DataTransfer.DoUong;
import DataTransfer.LoaiDoUong;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HT
 */
public class pnlDSDoUong extends javax.swing.JPanel {

    private ArrayList<LoaiDoUong> DSLoaiDU;
    private DefaultComboBoxModel MaLoaiDUModel;
    private DefaultComboBoxModel TenLoaiDUModel;
    private DefaultTableModel TableModel;
    private Vector Rows;
    private Vector Columns;
    private boolean isFormLoaded = false;
    private boolean isCalledMaLoaiDU = true;
    private boolean isCalledTenLoaiDU = true;
    private JFileChooser fcOpen = new JFileChooser();
    
    /**
     * Creates new form pnlDSDoUong
     */
    public pnlDSDoUong() {
        initComponents();
        
        DSLoaiDU = QLDUController.getInstance().LoadDSLoaiDoUong();
        
        Configuration();
        LoadDSLoaiDU();
        
        isFormLoaded = true;
        LoadDSDoUong((String)cmbMaLoaiDoUong.getSelectedItem());
        
    }

    void Configuration()
    {
        MaLoaiDUModel = new DefaultComboBoxModel();
        TenLoaiDUModel = new DefaultComboBoxModel();
        
        Columns = new Vector();
        Columns.add("Mã đồ uống");
        Columns.add("Tên đồ uống");
        Columns.add("Giá bán");
        Rows = new Vector();
        TableModel = new DefaultTableModel(Rows, Columns);
        tblDSDoUong.setFont(new Font("Segoe UI", 0, 20));
        tblDSDoUong.setRowHeight(30);
        tblDSDoUong.setModel(TableModel);
        
        tblDSDoUong.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblDSDoUong.rowAtPoint(e.getPoint());
                int column = tblDSDoUong.columnAtPoint(e.getPoint());
                
                if (row >= 0 && column >= 0)
                {
                    Vector selected = (Vector)Rows.get(row);
                    String urlImage = QLDUController.getInstance().getImageURL(String.valueOf(selected.get(0)));
                    lbHinhAnhDU.setIcon(ResizeImage(System.getProperty("user.dir") + urlImage));
                    lbHinhAnhDU.updateUI();
                }
            }

        });
        
        cmbMaLoaiDoUong.setModel(MaLoaiDUModel);
        cmbTenLoaiDoUong.setModel(TenLoaiDUModel);
    }
    
    void LoadDSLoaiDU()
    {
        int length = DSLoaiDU.size();
        
        for (int i = 0; i < length; i++)
        {
            MaLoaiDUModel.addElement(DSLoaiDU.get(i).getMaLoaiDoUong());
            TenLoaiDUModel.addElement(DSLoaiDU.get(i).getTenLoaiDoUong());
        }
    }
    
    void LoadDSDoUong(String MaLoaiDoUong)
    {
        if (Rows.isEmpty() == false)
        {
            Rows.removeAllElements();
        }
        
        Rows = QLDUController.getInstance().LoadDSDoUong(MaLoaiDoUong);
        TableModel = new DefaultTableModel(Rows, Columns);
        tblDSDoUong.setModel(TableModel);
    }
    
    public void LoadDSDoUong()
    {
        if (Rows.isEmpty() == false)
        {
            Rows.removeAllElements();
        }
        
        Rows = QLDUController.getInstance().LoadDSDoUong();
        TableModel = new DefaultTableModel(Rows, Columns);
        tblDSDoUong.setModel(TableModel);
    }
    
    Icon ResizeImage(String url){
        
        ImageIcon img = null;
        try {
            BufferedImage brImg = ImageIO.read(new File(url));
            
            int x;
            int y = 100;
            
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
    
    void RunThemDUThread()
    {
        fmThemDoUong fm = new fmThemDoUong(this);
        fm.setVisible(true);
    }
    
    void RunCapNhatDUThread()
    {
        int Selected = tblDSDoUong.getSelectedRow();
        
        if (Selected == -1)
        {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        DoUong value = new DoUong();
        
        value.setMaDoUong((String)((Vector)Rows.get(Selected)).get(0));
        value.setTenDoUong((String)((Vector)Rows.get(Selected)).get(1));
        value.setGiaBan((Long)((Vector)Rows.get(Selected)).get(2));
        value.setMaLoaiDoUong((String)cmbMaLoaiDoUong.getSelectedItem());
        value.setHinhAnh(lbHinhAnhDU.getToolTipText());
        
        fmCapNhatDoUong fm = new fmCapNhatDoUong(this, value);
        fm.setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbMaLoaiDoUong = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDSDoUong = new javax.swing.JTable();
        cmbTenLoaiDoUong = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnThemDU = new javax.swing.JButton();
        btnCapNhatDU = new javax.swing.JButton();
        btnXoaDU = new javax.swing.JButton();
        btnXemTatCa = new javax.swing.JButton();
        txtTenDoUong = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lbHinhAnhDU = new javax.swing.JLabel();
        btnSuaAnh = new javax.swing.JButton();

        cmbMaLoaiDoUong.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cmbMaLoaiDoUong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbMaLoaiDoUong.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMaLoaiDoUongItemStateChanged(evt);
            }
        });

        tblDSDoUong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblDSDoUong);

        cmbTenLoaiDoUong.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cmbTenLoaiDoUong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTenLoaiDoUong.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTenLoaiDoUongItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Mã loại đồ uống");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Tên loại đồ uống");

        btnThemDU.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnThemDU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add - small.png"))); // NOI18N
        btnThemDU.setText("Thêm đồ uống");
        btnThemDU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemDUMouseClicked(evt);
            }
        });

        btnCapNhatDU.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnCapNhatDU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update - small.png"))); // NOI18N
        btnCapNhatDU.setText("Cập nhật đồ uống");
        btnCapNhatDU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCapNhatDUMouseClicked(evt);
            }
        });

        btnXoaDU.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnXoaDU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Remove - small.png"))); // NOI18N
        btnXoaDU.setText("Xoá đồ uống");
        btnXoaDU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaDUMouseClicked(evt);
            }
        });

        btnXemTatCa.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnXemTatCa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/view-512.png"))); // NOI18N
        btnXemTatCa.setText("Xem tất cả");
        btnXemTatCa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXemTatCa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXemTatCaMouseClicked(evt);
            }
        });

        txtTenDoUong.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Tên đồ uống");

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search - small.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiemMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("Danh sách đồ uống");

        lbHinhAnhDU.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnSuaAnh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnSuaAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/change image - small.png"))); // NOI18N
        btnSuaAnh.setText("Đổi ảnh");
        btnSuaAnh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSuaAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(273, 273, 273)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTenDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(135, 135, 135)
                                .addComponent(btnXemTatCa)
                                .addGap(44, 44, 44)
                                .addComponent(btnThemDU)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCapNhatDU)
                                .addGap(40, 40, 40)
                                .addComponent(btnXoaDU))
                            .addComponent(btnTimKiem)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbMaLoaiDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbTenLoaiDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSuaAnh)
                        .addGap(88, 88, 88))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbHinhAnhDU, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cmbMaLoaiDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(cmbTenLoaiDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtTenDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTimKiem))
                                .addGap(42, 97, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnXemTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThemDU)
                                    .addComponent(btnCapNhatDU)
                                    .addComponent(btnXoaDU))
                                .addGap(22, 22, 22))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(lbHinhAnhDU, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaAnh)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbMaLoaiDoUongItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMaLoaiDoUongItemStateChanged
        // TODO add your handling code here:
        isCalledTenLoaiDU = false;
        if (isFormLoaded == true && isCalledMaLoaiDU == true)
        {
            int index = cmbMaLoaiDoUong.getSelectedIndex();
            cmbTenLoaiDoUong.setSelectedIndex(index);
            cmbTenLoaiDoUong.updateUI();
            System.out.println("Selected value: " + (String)cmbMaLoaiDoUong.getSelectedItem());
            LoadDSDoUong((String)cmbMaLoaiDoUong.getSelectedItem());
        }
        isCalledTenLoaiDU = true;
    }//GEN-LAST:event_cmbMaLoaiDoUongItemStateChanged

    private void cmbTenLoaiDoUongItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTenLoaiDoUongItemStateChanged
        // TODO add your handling code here:
        isCalledMaLoaiDU = false;
        if (isFormLoaded == true && isCalledTenLoaiDU == true)
        {
            int index = cmbTenLoaiDoUong.getSelectedIndex();
            cmbMaLoaiDoUong.setSelectedIndex(index);
            cmbMaLoaiDoUong.updateUI();
            LoadDSDoUong((String)cmbMaLoaiDoUong.getSelectedItem());
        }
        isCalledMaLoaiDU = true;
    }//GEN-LAST:event_cmbTenLoaiDoUongItemStateChanged

    private void btnSuaAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaAnhMouseClicked
        // TODO add your handling code here:
        
        int select = fcOpen.showOpenDialog(this);
        String url;
        if (select == JFileChooser.APPROVE_OPTION)
        {
            url = fcOpen.getSelectedFile().getPath();
            lbHinhAnhDU.setIcon(ResizeImage(url));
            lbHinhAnhDU.setToolTipText(url);
        }
    }//GEN-LAST:event_btnSuaAnhMouseClicked

    private void btnXemTatCaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXemTatCaMouseClicked
        // TODO add your handling code here:
        LoadDSDoUong();
    }//GEN-LAST:event_btnXemTatCaMouseClicked

    private void btnTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseClicked
        // TODO add your handling code here:
        if (txtTenDoUong.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập tên đồ uống cần tìm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else
        {
            System.out.println("content = " + txtTenDoUong.getText());
            Rows = QLDUController.getInstance().TimKiemTheoTen(txtTenDoUong.getText());
            TableModel = new DefaultTableModel(Rows, Columns);
            
            tblDSDoUong.setModel(TableModel);
        }
    }//GEN-LAST:event_btnTimKiemMouseClicked

    private void btnThemDUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemDUMouseClicked
        // TODO add your handling code here:
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RunThemDUThread();
            }
        });
        
        thread.start();
    }//GEN-LAST:event_btnThemDUMouseClicked

    private void btnCapNhatDUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatDUMouseClicked
        // TODO add your handling code here:
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RunCapNhatDUThread();
            }
        });
        
        thread.start();
    }//GEN-LAST:event_btnCapNhatDUMouseClicked

    private void btnXoaDUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaDUMouseClicked
        // TODO add your handling code here:
        int CheckSelected = tblDSDoUong.getSelectedRow();
        
        if (CheckSelected == -1)
        {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xoá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int valid = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xoá đồ uống này không?", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (valid == JOptionPane.YES_OPTION)
        {
            int[] SelectedRows = tblDSDoUong.getSelectedRows();
            int length = SelectedRows.length;

            for (int i = 0; i < length; i++)
            {
                String MaDU = (String)((Vector)Rows.get(SelectedRows[i])).get(0);
                boolean Result = QLDUController.getInstance().XoaDoUong(MaDU);

                if (Result == false)
                {
                    JOptionPane.showMessageDialog(null, "Không thể xoá đồ uống có mã " + MaDU, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            JOptionPane.showMessageDialog(null, "Xoá thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            LoadDSDoUong((String)cmbMaLoaiDoUong.getSelectedItem());
            lbHinhAnhDU.setIcon(null);
        }
    }//GEN-LAST:event_btnXoaDUMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatDU;
    private javax.swing.JButton btnSuaAnh;
    private javax.swing.JButton btnThemDU;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXemTatCa;
    private javax.swing.JButton btnXoaDU;
    private javax.swing.JComboBox<String> cmbMaLoaiDoUong;
    private javax.swing.JComboBox<String> cmbTenLoaiDoUong;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbHinhAnhDU;
    private javax.swing.JTable tblDSDoUong;
    private javax.swing.JTextField txtTenDoUong;
    // End of variables declaration//GEN-END:variables

}

class TextWithIconCellRenderer extends DefaultTableCellRenderer {
    
    @Override
    protected void setValue(Object value) 
    {
        if (value instanceof Icon) {
          if (value != null) 
          {
            Icon d = (Icon)value;
            setText("");
            setIcon(d);
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
          } 
          else 
          {
            setText("");
            setIcon(null);
          }
        } 
        else 
        {
          super.setValue(value);
        }
      }
}