/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe;

import Controller.QLBanHangController;
import DataProvider.ConstantClass;
import DataProvider.CustomTableModel;
import DataProvider.ProductButton;
import DataProvider.ProductTypeButton;
import DataProvider.TableButton;
import DataTransfer.Ban;
import DataTransfer.DoUong;
import DataTransfer.KhuyenMai;
import DataTransfer.LoaiDoUong;
import DataTransfer.Order;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author HT
 */
public class pnlQLBH extends javax.swing.JPanel {

    private ArrayList<TableButton> DSBan;
    private ProductTypeButton[] DSLoaiDU;
    private ProductButton[] DSDU;
    private CustomTableModel TableModel;
    private Vector TableTitle = new Vector();
    private Vector TableRecords = new Vector();
    private static boolean isOrdered = false;
    private boolean FirstLogin = true;
    private DefaultComboBoxModel KhuyenMaiModel;
    private NumberFormat DinhDangTien = new DecimalFormat("#,###,###");
    
    /**
     * Creates new form pnlQLBH
     */
    public pnlQLBH(String LoaiNV) {
        initComponents();
        
        this.setSize(ConstantClass.WIDTH_OF_CONTENT_PANEL, ConstantClass.HEIGHT_OF_CONTENT_PANEL);
        
        LoadDSBan();
        LoadDSLoaiDoUong();
        lbFreeColor.setBackground(Color.decode(ConstantClass.COLOR_FREE));
        lbServingColor.setBackground(Color.decode(ConstantClass.COLOR_SERVING));
        lbWaittingColor.setBackground(Color.decode(ConstantClass.COLOR_WAITING));
        
        ConfigTable();
        
        Configuration(LoaiNV);
        
        LoadDSKM();
        
        setEventValueChanged();
    }
    
    void Configuration(String LoaiNV)
    {
        if (LoaiNV.equals("NV"))
        {
            btnThemBan.setEnabled(false);
            btnXoaBan.setEnabled(false);
        }
        
    }
    
    void ConfigTable()
    {
        //{ "Tên Đồ uống", "Số lượng", "Giá bán", "Thành tiền" }
        TableTitle.add("Tên đồ uống");
        TableTitle.add("Số lượng");
        TableTitle.add("Giá bán");
        TableTitle.add("Thành tiền");
        
        TableModel = new CustomTableModel(TableRecords, TableTitle);
        
        tblOrder.setFont(new Font("Segoe UI", 0, 20));
        tblOrder.setRowHeight(30);
        tblOrder.setModel(TableModel);
        
        SetTableModelListener();
        
    }
    
    void SetTableModelListener()
    {
        tblOrder.getModel().removeTableModelListener(tblOrder);
        tblOrder.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                long TongCu = 0;
                try 
                {
                    TongCu = DinhDangTien.parse(txtTongTien.getText()).longValue();
                } 
                catch (ParseException ex) 
                {
                    Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                long newThanhTien = (int)((Vector)TableRecords.get(e.getFirstRow())).get(1) * (long)((Vector)TableRecords.get(e.getFirstRow())).get(2);
                
                ((Vector)TableRecords.get(e.getFirstRow())).setElementAt(newThanhTien, 3);
                long Tong = 0;
                int lenght = TableModel.getRowCount();
                
                for (int i = 0; i < lenght; i++)
                {
                    Tong = Tong + ((long)((Vector)TableModel.getDataVector().elementAt(i)).elementAt(3));
                }
                
                txtTongTien.setText(String.valueOf(DinhDangTien.format(Tong)));
                
                tblOrder.updateUI();
                if (TongCu == Tong)
                {
                    DSBan.get(Integer.valueOf(txtID.getText()) - 1).setSave(true);
                }
                else
                {
                    DSBan.get(Integer.valueOf(txtID.getText()) - 1).setSave(false);
                }
            }
        });
    }
    
    void LoadDSKM()
    {
        Vector value = QLBanHangController.getInstance().getDSTenKM();
        KhuyenMaiModel = new DefaultComboBoxModel(value);
        
        cmbKM.setModel(KhuyenMaiModel);
    }
    
    void RenewTable()
    {
        TableRecords.removeAllElements();
        tblOrder.updateUI();
        txtTongTien.setText("0");
        cmbKM.setSelectedIndex(0);
        txtPhaiTra.setText("0");
    }
    
    void setEventValueChanged()
    {
        txtTongTien.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                
                if (txtTongTien.getText().equals("0"))
                {
                    txtPhaiTra.setText("0");
                    return;
                }
                
                long TongTien = 0;
                try 
                {
                    TongTien = DinhDangTien.parse(txtTongTien.getText()).longValue();
                } 
                catch (ParseException ex) 
                {
                    Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                KhuyenMai KMThichHop = QLBanHangController.getInstance().getKMThichHop(TongTien);
                
                long TriGiaKM = KMThichHop.getTriGiaKM();
                
                txtPhaiTra.setText(String.valueOf(DinhDangTien.format(TongTien - TriGiaKM)));
                cmbKM.setSelectedItem(KMThichHop.getTenKM());
                cmbKM.updateUI();
                
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
    }
    
    void SaveOrder(String MaBan, String TrangThai, long TriGia, String MaKM, long PhaiTra, Vector CTOrder)
    {
        if (CTOrder.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Bạn chưa thêm đồ uống vào Order!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        
        boolean resultThemOrder = true;
        if (DSBan.get(Integer.valueOf(MaBan) - 1).getMaOrderCuaBan().isEmpty() == false)
        {
            // Xoá CTOrder
            QLBanHangController.getInstance().XoaToanBoCTOrder(DSBan.get(Integer.valueOf(txtID.getText()) - 1).getMaOrderCuaBan());
        }
        else
        {
            resultThemOrder = QLBanHangController.getInstance().ThemOrder(MaBan, TrangThai, TriGia, MaKM, PhaiTra);
        }
                  
        boolean resultThemCTOrder = QLBanHangController.getInstance().ThemCTOrder(QLBanHangController.getInstance().getMaOrder(MaBan), CTOrder);
        
        if (resultThemCTOrder == true && resultThemOrder == true)
        {
            JOptionPane.showMessageDialog(null, "Lưu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            DSBan.get(Integer.valueOf(MaBan) - 1).setTrangThai(ConstantClass.WAITTING_STATUS);
            txtTrangThai.setText(ConstantClass.WAITTING_STATUS);
            btnGoiMon.setText(ConstantClass.NO_ORDER_AVAILABLE);
            DSBan.get(Integer.valueOf(MaBan) - 1).setSave(true);
            QLBanHangController.getInstance().CapNhatTrangThaiBan(MaBan, ConstantClass.WAITTING_STATUS);
            DSBan.get(Integer.valueOf(MaBan) - 1).setMaOrderCuaBan(QLBanHangController.getInstance().getMaOrder(MaBan));
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Lưu thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void LoadDSBan() 
    {
        pnlDSBan.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlDSBan.setPreferredSize(new Dimension(417, 500));
        pnlDSBan.setSize(new Dimension(417, 500));
        
        ArrayList<Ban> list = QLBanHangController.getInstance().getDSBan();
        int lenght = list.size();
        DSBan = new ArrayList<TableButton>();
        for (int i = 0; i < lenght; i++)
        {
            String TrangThai = list.get(i).getTrangThai();
            String MaOrderCuaBan = "";
            
            DSBan.add(new TableButton(String.valueOf(list.get(i).getMaBan()), TrangThai, MaOrderCuaBan, 
                                                        System.getProperty("user.dir") + "\\src\\img\\table.png"));
            if (TrangThai.equals(ConstantClass.FREE_STATUS) == false)
            {
                MaOrderCuaBan = QLBanHangController.getInstance().getMaOrder(list.get(i).getMaBan());
                DSBan.get(i).setMaOrderCuaBan(MaOrderCuaBan);
                DSBan.get(i).setSave(true);
            }
            
            DSBan.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    TableButton btnTable = (TableButton)e.getSource();
                    if (FirstLogin)
                    {
                        txtID.setText(btnTable.getID());
                        txtTenBan.setText("Bàn số " + btnTable.getID());
                    }
                    
                    if (DSBan.get(Integer.valueOf(txtID.getText()) - 1).isSave() == false && FirstLogin == false)
                    {
                        int choose = JOptionPane.showConfirmDialog(null, "Order vừa tạo chưa được lưu. Bạn có muốn lưu Order này không?", "Cảnh báo", 
                                                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        
                        if (choose == JOptionPane.YES_OPTION)
                        {
                            long PhaiTra = 0;
                            try 
                            {
                                PhaiTra = DinhDangTien.parse(txtPhaiTra.getText()).longValue();
                            } 
                            catch (ParseException ex) 
                            {
                                Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            long TongTien = 0;
                            try 
                            {
                                TongTien = DinhDangTien.parse(txtTongTien.getText()).longValue();
                            } 
                            catch (ParseException ex) 
                            {
                                Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            SaveOrder(txtID.getText(), ConstantClass.ORDER_STATUS, TongTien, 
                                    QLBanHangController.getInstance().getMaKM((String)cmbKM.getSelectedItem()), 
                                        PhaiTra, TableRecords);
                        }
                        else
                        {
                            if (DSBan.get(Integer.valueOf(txtID.getText()) - 1).getMaOrderCuaBan().isEmpty())
                            {
                                DSBan.get(Integer.valueOf(txtID.getText()) - 1).setTrangThai(ConstantClass.FREE_STATUS);
                                DSBan.get(Integer.valueOf(txtID.getText()) - 1).updateUI();
                            }
                            DSBan.get(Integer.valueOf(txtID.getText()) - 1).setSave(true);
                        }
                    }
                    
                    FirstLogin = false;
                    txtID.setText(btnTable.getID());
                    txtTenBan.setText("Bàn số " + btnTable.getID());
                    txtTrangThai.setText(btnTable.getTrangThai());
                    
                    if (btnTable.getTrangThai().equals(ConstantClass.FREE_STATUS))
                    {
                        btnTable.setTrangThai(ConstantClass.WAITTING_STATUS);
                        btnGoiMon.setText(ConstantClass.ORDER_AVAILABLE);
                        RenewTable();
                        System.out.println("Bàn trống");
                    }
                    else
                    {
                        System.out.println("Bàn không trống");
                        if (txtTrangThai.getText().equals(ConstantClass.WAITTING_STATUS))
                        {
                            btnGoiMon.setText(ConstantClass.NO_ORDER_AVAILABLE);
                        }
                        else
                        {
                            btnGoiMon.setText(ConstantClass.ORDER_AVAILABLE);
                        }
                        
                        // get CTOrder
                        TableRecords = QLBanHangController.getInstance().getCTOrder(QLBanHangController.getInstance().getMaOrder(txtID.getText()));
                        
                        TableModel = new CustomTableModel(TableRecords, TableTitle);
                        tblOrder.setModel(TableModel);
                        SetTableModelListener();
                        
                        //get Tổng tiền
                        Order current = QLBanHangController.getInstance().getOrder(QLBanHangController.getInstance().getMaOrder(txtID.getText()));
                        
                        txtTongTien.setText(String.valueOf(DinhDangTien.format(current.getTriGia())));
                        cmbKM.setSelectedItem(QLBanHangController.getInstance().getKhuyenMai(current.getMaKM()).getTenKM());
                        txtPhaiTra.setText(String.valueOf(DinhDangTien.format(current.getPhaiTra())));
                    }
                    
                }
            });
            
            if (i % 4 == 1 && i > 20)
            {
                pnlDSBan.setPreferredSize(new Dimension(ConstantClass.WIDTH_PANEL_TABLE, pnlDSBan.getHeight() + ConstantClass.HEIGHT_ROW_OF_PANEL_TABLE));
                
            }
            
            pnlDSBan.add(DSBan.get(i));
            pnlDSBan.updateUI();
        }
    }
    
    void LoadDSLoaiDoUong()
    {
        pnlDSLoaiDU.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        ArrayList<LoaiDoUong> list = QLBanHangController.getInstance().getDSLoaiDoUong();
        int lenght = list.size();
        
        DSLoaiDU = new ProductTypeButton[lenght];
        for (int i = 0; i < lenght; i++)
        {
            
            DSLoaiDU[i] = new ProductTypeButton(list.get(i).getMaLoaiDoUong(), list.get(i).getTenLoaiDoUong(), System.getProperty("user.dir") + list.get(i).getHinhAnh());
            
            DSLoaiDU[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    LoadDSDU(((ProductTypeButton)(e.getSource())).getMaLoaiDU());
                }
            });
            
            if (i % 2 == 1 && i > 4)
            {
                pnlDSLoaiDU.setPreferredSize(new Dimension(pnlDSLoaiDU.getWidth(), pnlDSLoaiDU.getHeight() + 50));
            }
            
            pnlDSLoaiDU.add(DSLoaiDU[i]);
        }
        
    }

    void LoadDSDU(String MaLoaiDoUong)
    {
        pnlDSDU.removeAll();
        pnlDSDU.setPreferredSize(new Dimension(380, 340));
        pnlDSDU.updateUI();
        
        pnlDSDU.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        ArrayList<DoUong> list = QLBanHangController.getInstance().getDSDoUong(MaLoaiDoUong);
        int lenght = list.size();
        
        DSDU = new ProductButton[lenght];
        
        for (int i = 0; i < lenght; i++)
        {
            DSDU[i] = new ProductButton(list.get(i), System.getProperty("user.dir") + list.get(i).getHinhAnh());
            
            DSDU[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    if (txtID.getText().isEmpty() == false)
                    {
                        ProductButton product = (ProductButton)e.getSource();
                        boolean isExist = false;

                        for (int i = 0; i < TableRecords.size(); i++)
                        {
                            Vector tmp = (Vector)TableRecords.get(i);
                            if (tmp.get(0).equals(product.getDoUong().getTenDoUong()))
                            {
                                tmp.set(1, (int)tmp.get(1) + 1);
                                tmp.set(3, product.getDoUong().getGiaBan() * (int)tmp.get(1));
                                isExist = true;
                                break;
                            }
                        }

                        if (isExist == false)
                        {
                            Vector record = new Vector();
                            record.add(product.getDoUong().getTenDoUong());
                            record.add(1);
                            record.add(product.getDoUong().getGiaBan());
                            record.add(product.getDoUong().getGiaBan());

                            TableRecords.add(record);

                        }
                        TableModel = new CustomTableModel(TableRecords, TableTitle);
                        tblOrder.setModel(TableModel);
                        // Vì model đã bị thay đổi nên phải set lại bộ lắng nghe sự kiện mới
                        SetTableModelListener();

                        // Cập nhật lại tổng tiển
                        long TongTien = 0;
                        try 
                        {
                            TongTien = DinhDangTien.parse(txtTongTien.getText()).longValue();
                        } 
                        catch (ParseException ex) 
                        {
                            Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
                        }
            
                        txtTongTien.setText(String.valueOf(DinhDangTien.format(TongTien + product.getDoUong().getGiaBan())));

                        DSBan.get(Integer.valueOf(txtID.getText()) - 1).setSave(false);
                        DSBan.get(Integer.valueOf(txtID.getText()) - 1).setTrangThai(ConstantClass.WAITTING_STATUS);
                        btnGoiMon.setText(ConstantClass.ORDER_AVAILABLE);
                    }
                }
            });
            
            if (i % 4 == 1 && i > 8)
            {
                pnlDSDU.setPreferredSize(new Dimension(pnlDSDU.getWidth(), pnlDSDU.getHeight() + 50));
            }
            
            pnlDSDU.add(DSDU[i]);
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spnlTable = new javax.swing.JScrollPane();
        pnlDSBan = new javax.swing.JPanel();
        pnlOrder = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        txtTongTien = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTenBan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTrangThai = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPhaiTra = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        cmbKM = new javax.swing.JComboBox<>();
        pnlFunctionKey = new javax.swing.JPanel();
        btnThemBan = new javax.swing.JButton();
        btnXoaBan = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        btnGoiMon = new javax.swing.JButton();
        btnChuyenBan = new javax.swing.JButton();
        btnXoaDU = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        pnlDSDU = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pnlDSLoaiDU = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbServingColor = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbWaittingColor = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbFreeColor = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setLayout(null);

        pnlDSBan.setPreferredSize(new java.awt.Dimension(417, 100));

        javax.swing.GroupLayout pnlDSBanLayout = new javax.swing.GroupLayout(pnlDSBan);
        pnlDSBan.setLayout(pnlDSBanLayout);
        pnlDSBanLayout.setHorizontalGroup(
            pnlDSBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 426, Short.MAX_VALUE)
        );
        pnlDSBanLayout.setVerticalGroup(
            pnlDSBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );

        spnlTable.setViewportView(pnlDSBan);

        add(spnlTable);
        spnlTable.setBounds(0, 0, 428, 510);

        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblOrder);

        txtTongTien.setEditable(false);
        txtTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtTongTien.setText("0");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Tổng tiền");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tên bàn");

        txtTenBan.setEditable(false);
        txtTenBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Trạng thái");

        txtTrangThai.setEditable(false);
        txtTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Khách đưa");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Trả lại");

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Khuyến mãi");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Phải trả");

        txtPhaiTra.setEditable(false);
        txtPhaiTra.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        txtPhaiTra.setForeground(new java.awt.Color(255, 0, 0));
        txtPhaiTra.setText("0");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("ID");

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cmbKM.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cmbKM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbKMItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlOrderLayout = new javax.swing.GroupLayout(pnlOrder);
        pnlOrder.setLayout(pnlOrderLayout);
        pnlOrderLayout.setHorizontalGroup(
            pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderLayout.createSequentialGroup()
                .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOrderLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel8)
                        .addGap(10, 10, 10)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(txtTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlOrderLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27)
                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(4, 4, 4)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlOrderLayout.createSequentialGroup()
                        .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbKM, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhaiTra, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addComponent(jLabel5)
                        .addGap(4, 4, 4)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4))
        );
        pnlOrderLayout.setVerticalGroup(
            pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlOrderLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOrderLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1))
                    .addGroup(pnlOrderLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4))
                    .addGroup(pnlOrderLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlOrderLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOrderLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlOrderLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel5))
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlOrderLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbKM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(17, 17, 17)
                .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPhaiTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(pnlOrder);
        pnlOrder.setBounds(430, 0, 520, 440);

        pnlFunctionKey.setMinimumSize(new java.awt.Dimension(516, 225));
        pnlFunctionKey.setPreferredSize(new java.awt.Dimension(137, 225));
        pnlFunctionKey.setLayout(null);

        btnThemBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThemBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add - small.png"))); // NOI18N
        btnThemBan.setText("Thêm bàn");
        btnThemBan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThemBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemBanMouseClicked(evt);
            }
        });
        pnlFunctionKey.add(btnThemBan);
        btnThemBan.setBounds(10, 0, 143, 41);

        btnXoaBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoaBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Remove - small.png"))); // NOI18N
        btnXoaBan.setText("Xoá bàn");
        btnXoaBan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXoaBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaBanMouseClicked(evt);
            }
        });
        pnlFunctionKey.add(btnXoaBan);
        btnXoaBan.setBounds(10, 50, 143, 41);

        btnThanhToan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/payment.png"))); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThanhToan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThanhToanMouseClicked(evt);
            }
        });
        pnlFunctionKey.add(btnThanhToan);
        btnThanhToan.setBounds(390, 0, 120, 77);

        btnGoiMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGoiMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/order.png"))); // NOI18N
        btnGoiMon.setText("Gọi món");
        btnGoiMon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGoiMon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGoiMon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGoiMonMouseClicked(evt);
            }
        });
        pnlFunctionKey.add(btnGoiMon);
        btnGoiMon.setBounds(270, 0, 100, 77);

        btnChuyenBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnChuyenBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exchange - small.png"))); // NOI18N
        btnChuyenBan.setText("Chuyển bàn");
        btnChuyenBan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnChuyenBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChuyenBanMouseClicked(evt);
            }
        });
        pnlFunctionKey.add(btnChuyenBan);
        btnChuyenBan.setBounds(10, 100, 143, 41);

        btnXoaDU.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnXoaDU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete product - small.png"))); // NOI18N
        btnXoaDU.setText("Xoá đồ uống");
        btnXoaDU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaDUMouseClicked(evt);
            }
        });
        pnlFunctionKey.add(btnXoaDU);
        btnXoaDU.setBounds(350, 90, 160, 40);

        add(pnlFunctionKey);
        pnlFunctionKey.setBounds(430, 449, 520, 140);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách đồ uống", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N
        jScrollPane2.setPreferredSize(new java.awt.Dimension(392, 360));

        pnlDSDU.setPreferredSize(new java.awt.Dimension(380, 340));

        javax.swing.GroupLayout pnlDSDULayout = new javax.swing.GroupLayout(pnlDSDU);
        pnlDSDU.setLayout(pnlDSDULayout);
        pnlDSDULayout.setHorizontalGroup(
            pnlDSDULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        pnlDSDULayout.setVerticalGroup(
            pnlDSDULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 379, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(pnlDSDU);

        add(jScrollPane2);
        jScrollPane2.setBounds(950, 180, 410, 410);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách loại đồ uống", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        pnlDSLoaiDU.setPreferredSize(new java.awt.Dimension(380, 110));

        javax.swing.GroupLayout pnlDSLoaiDULayout = new javax.swing.GroupLayout(pnlDSLoaiDU);
        pnlDSLoaiDU.setLayout(pnlDSLoaiDULayout);
        pnlDSLoaiDULayout.setHorizontalGroup(
            pnlDSLoaiDULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        pnlDSLoaiDULayout.setVerticalGroup(
            pnlDSLoaiDULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(pnlDSLoaiDU);

        add(jScrollPane3);
        jScrollPane3.setBounds(950, 0, 410, 180);

        jPanel3.setPreferredSize(new java.awt.Dimension(428, 90));

        lbServingColor.setBackground(new java.awt.Color(25, 250, 52));
        lbServingColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbServingColor.setOpaque(true);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setText("Đang phục vụ");

        lbWaittingColor.setBackground(new java.awt.Color(250, 62, 25));
        lbWaittingColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbWaittingColor.setOpaque(true);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel11.setText("Đang chờ");

        lbFreeColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbFreeColor.setOpaque(true);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel13.setText("Trống");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbWaittingColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbServingColor, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(lbFreeColor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13))
                    .addComponent(jLabel11))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbServingColor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFreeColor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbWaittingColor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        add(jPanel3);
        jPanel3.setBounds(0, 500, 428, 90);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemBanMouseClicked
        // TODO add your handling code here:
        ArrayList<Ban> list = QLBanHangController.getInstance().getDSBan();
        int length = list.size() + 1;
        if (length % 4 == 1 && length > 20)
        {
            pnlDSBan.setPreferredSize(new Dimension(ConstantClass.WIDTH_PANEL_TABLE, pnlDSBan.getHeight() + ConstantClass.HEIGHT_ROW_OF_PANEL_TABLE));
        }
        DSBan.add(new TableButton(String.valueOf(length), ConstantClass.FREE_STATUS, "", System.getProperty("user.dir") + "\\src\\img\\table.png"));
        
        DSBan.get(length - 1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                TableButton btnTable = (TableButton)e.getSource();
                if (FirstLogin)
                {
                    txtID.setText(btnTable.getID());
                    txtTenBan.setText("Bàn số " + btnTable.getID());
                }

                if (DSBan.get(Integer.valueOf(txtID.getText()) - 1).isSave() == false && FirstLogin == false)
                {
                    int choose = JOptionPane.showConfirmDialog(null, "Order vừa tạo chưa được lưu. Bạn có muốn lưu Order này không?", "Cảnh báo", 
                                                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (choose == JOptionPane.YES_OPTION)
                    {
                        long PhaiTra = 0;
                        try 
                        {
                            PhaiTra = DinhDangTien.parse(txtPhaiTra.getText()).longValue();
                        } 
                        catch (ParseException ex) 
                        {
                            Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        long TongTien = 0;
                        try 
                        {
                            TongTien = DinhDangTien.parse(txtTongTien.getText()).longValue();
                        } 
                        catch (ParseException ex) 
                        {
                            Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        SaveOrder(txtID.getText(), ConstantClass.ORDER_STATUS, TongTien, 
                                QLBanHangController.getInstance().getMaKM((String)cmbKM.getSelectedItem()), 
                                    PhaiTra, TableRecords);
                    }
                    else
                    {
                        if (DSBan.get(Integer.valueOf(txtID.getText()) - 1).getMaOrderCuaBan().isEmpty())
                        {
                            DSBan.get(Integer.valueOf(txtID.getText()) - 1).setTrangThai(ConstantClass.FREE_STATUS);
                            DSBan.get(Integer.valueOf(txtID.getText()) - 1).updateUI();
                        }
                        DSBan.get(Integer.valueOf(txtID.getText()) - 1).setSave(true);
                    }
                }

                FirstLogin = false;
                txtID.setText(btnTable.getID());
                txtTenBan.setText("Bàn số " + btnTable.getID());
                txtTrangThai.setText(btnTable.getTrangThai());

                if (btnTable.getTrangThai().equals(ConstantClass.FREE_STATUS))
                {
                    btnTable.setTrangThai(ConstantClass.WAITTING_STATUS);
                    btnGoiMon.setText(ConstantClass.ORDER_AVAILABLE);
                    RenewTable();
                    System.out.println("Bàn trống");
                }
                else
                {
                    System.out.println("Bàn không trống");
                    if (txtTrangThai.getText().equals(ConstantClass.WAITTING_STATUS))
                    {
                        btnGoiMon.setText(ConstantClass.NO_ORDER_AVAILABLE);
                    }
                    else
                    {
                        btnGoiMon.setText(ConstantClass.ORDER_AVAILABLE);
                    }

                    // get CTOrder
                    TableRecords = QLBanHangController.getInstance().getCTOrder(QLBanHangController.getInstance().getMaOrder(txtID.getText()));

                    TableModel = new CustomTableModel(TableRecords, TableTitle);
                    tblOrder.setModel(TableModel);
                    SetTableModelListener();

                    //get Tổng tiền
                    Order current = QLBanHangController.getInstance().getOrder(QLBanHangController.getInstance().getMaOrder(txtID.getText()));

                    txtTongTien.setText(String.valueOf(DinhDangTien.format(current.getTriGia())));
                    cmbKM.setSelectedItem(QLBanHangController.getInstance().getKhuyenMai(current.getMaKM()).getTenKM());
                    txtPhaiTra.setText(String.valueOf(DinhDangTien.format(current.getPhaiTra())));
                }

            }
        });
        
        pnlDSBan.add(DSBan.get(length - 1));
        QLBanHangController.getInstance().ThemBan(length);
        pnlDSBan.updateUI();
    }//GEN-LAST:event_btnThemBanMouseClicked

    private void btnXoaBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaBanMouseClicked
        // TODO add your handling code here:
        if (txtID.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn bàn để xoá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (DSBan.get(Integer.valueOf(txtID.getText()) - 1).getMaOrderCuaBan().isEmpty() == false)
        {
            JOptionPane.showMessageDialog(null, "Bàn có Order chưa thanh toán nên không thể xoá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean result = QLBanHangController.getInstance().XoaBan(Integer.valueOf(txtID.getText()));
        
        if (result)
        {
            pnlDSBan.removeAll();
            LoadDSBan();
            pnlDSBan.updateUI();
            JOptionPane.showMessageDialog(null, "Xoá thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Xoá thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaBanMouseClicked

    private void btnChuyenBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChuyenBanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChuyenBanMouseClicked

    private void btnXoaDUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaDUMouseClicked
        // TODO add your handling code here:
        int Selected = tblOrder.getSelectedRow();
        if (Selected == -1)
        {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn đồ uống cần xoá trong order!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (txtTrangThai.getText().equals(ConstantClass.SERVING_STATUS))
        {
            JOptionPane.showMessageDialog(null, "Không thể xoá đồ uống đã được gọi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (DSBan.get(Integer.valueOf(txtID.getText()) - 1).isSave() == true)
        {
            String TenDoUong = (String)((Vector)TableRecords.get(Selected)).get(0);
            String MaDoUong = QLBanHangController.getInstance().getMaDoUong(TenDoUong);
            boolean result = QLBanHangController.getInstance().XoaDoUongTrongOrder(DSBan.get(Integer.valueOf(txtID.getText()) - 1).getMaOrderCuaBan(), MaDoUong);
            
            if (result)
            {
                JOptionPane.showMessageDialog(null, "Thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        TableRecords.removeElementAt(Selected);
        TableModel = new CustomTableModel(TableRecords, TableTitle);
        tblOrder.setModel(TableModel);
        SetTableModelListener();
        tblOrder.updateUI();
    }//GEN-LAST:event_btnXoaDUMouseClicked

    private void btnGoiMonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGoiMonMouseClicked
        // TODO add your handling code here:
        String MaOrderCuaBan = DSBan.get(Integer.valueOf(txtID.getText()) - 1).getMaOrderCuaBan();
        if (DSBan.get(Integer.valueOf(txtID.getText()) - 1).isSave() == false && btnGoiMon.getText() == ConstantClass.ORDER_AVAILABLE && MaOrderCuaBan.isEmpty() == true)
        {
            long PhaiTra = 0;
            try 
            {
                PhaiTra = DinhDangTien.parse(txtPhaiTra.getText()).longValue();
            } 
            catch (ParseException ex) 
            {
                Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            long TongTien = 0;
            try 
            {
                TongTien = DinhDangTien.parse(txtTongTien.getText()).longValue();
            } 
            catch (ParseException ex) 
            {
                Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            SaveOrder(txtID.getText(), ConstantClass.ORDER_STATUS, TongTien, 
                                    QLBanHangController.getInstance().getMaKM((String)cmbKM.getSelectedItem()), 
                                        PhaiTra, TableRecords);
        }
        else if (DSBan.get(Integer.valueOf(txtID.getText()) - 1).isSave() == true && btnGoiMon.getText().equals(ConstantClass.NO_ORDER_AVAILABLE))
        {
            txtTrangThai.setText(ConstantClass.SERVING_STATUS);
            btnGoiMon.setText(ConstantClass.ORDER_AVAILABLE);
            DSBan.get(Integer.valueOf(txtID.getText()) - 1).setTrangThai(ConstantClass.SERVING_STATUS);
            QLBanHangController.getInstance().CapNhatTrangThaiBan(txtID.getText(), ConstantClass.SERVING_STATUS);
        }
        else
        {
            // Xoá toàn bộ CTOrder của Order của bàn này
            QLBanHangController.getInstance().XoaToanBoCTOrder(DSBan.get(Integer.valueOf(txtID.getText()) - 1).getMaOrderCuaBan());
            
            // Lưu lại CTOrder mới
            
            long PhaiTra = 0;
            try 
            {
                PhaiTra = DinhDangTien.parse(txtPhaiTra.getText()).longValue();
            } 
            catch (ParseException ex) 
            {
                Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            long TongTien = 0;
            try 
            {
                TongTien = DinhDangTien.parse(txtTongTien.getText()).longValue();
            } 
            catch (ParseException ex) 
            {
                Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            SaveOrder(txtID.getText(), ConstantClass.ORDER_STATUS, TongTien, 
                                    QLBanHangController.getInstance().getMaKM((String)cmbKM.getSelectedItem()), 
                                        PhaiTra, TableRecords);
        }
    }//GEN-LAST:event_btnGoiMonMouseClicked

    private void cmbKMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbKMItemStateChanged
        // TODO add your handling code here:
        long TongTien = 0;
        try 
        {
            TongTien = DinhDangTien.parse(txtTongTien.getText()).longValue();
        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (TongTien > 0)
        {
            String MaKM = QLBanHangController.getInstance().getMaKM((String)cmbKM.getSelectedItem());
            KhuyenMai khuyenmai = QLBanHangController.getInstance().getKhuyenMai(MaKM);
            txtPhaiTra.setText(String.valueOf(DinhDangTien.format(TongTien - khuyenmai.getTriGiaKM())));
        }
    }//GEN-LAST:event_cmbKMItemStateChanged

    private void btnThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToanMouseClicked
        // TODO add your handling code here:
        if (TableRecords.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Bàn không có đồ uống nào thanh toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (txtTrangThai.getText().equals(ConstantClass.WAITTING_STATUS))
        {
            JOptionPane.showMessageDialog(null, "Bàn đang chờ phục vụ không thể thanh toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int choose = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thanh toán và in hoá đơn không?\n Chọn \"yes\" để thanh toán và in hoá đơn\n Chọn \"no\" để thanh toán nhưng không in hoá đơn\n Chọn \"cancel\" để thoát", "Thông báo", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (choose == JOptionPane.CANCEL_OPTION)
        {
            return;
        }
        
        if(DSBan.get(Integer.valueOf(txtID.getText()) - 1).isSave() == false)
        {
            long PhaiTra = 0;
            try 
            {
                PhaiTra = DinhDangTien.parse(txtPhaiTra.getText()).longValue();
            } 
            catch (ParseException ex) 
            {
                Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            long TongTien = 0;
            try 
            {
                TongTien = DinhDangTien.parse(txtTongTien.getText()).longValue();
            } 
            catch (ParseException ex) 
            {
                Logger.getLogger(pnlQLBH.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            SaveOrder(txtID.getText(), ConstantClass.ORDER_STATUS, TongTien, 
                                    QLBanHangController.getInstance().getMaKM((String)cmbKM.getSelectedItem()), 
                                        PhaiTra, TableRecords);
            
        }
        
        boolean ResultOrder = QLBanHangController.getInstance().CapNhatTrangThaiOrder(QLBanHangController.getInstance().getMaOrder(txtID.getText()), ConstantClass.INVOICE_STATUS);
        boolean ResultBan = QLBanHangController.getInstance().CapNhatTrangThaiBan(txtID.getText(), ConstantClass.FREE_STATUS);

        if (ResultOrder == true && ResultBan == true)
        {
            DSBan.get(Integer.valueOf(txtID.getText()) - 1).setTrangThai(ConstantClass.FREE_STATUS);
            DSBan.get(Integer.valueOf(txtID.getText()) - 1).setSave(false);
            txtID.setText("");
            txtTenBan.setText("");
            txtTrangThai.setText("");
            FirstLogin = true;
            RenewTable();
            JOptionPane.showMessageDialog(null, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Thanh toán thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        
        if (choose == JOptionPane.YES_OPTION)
        {
            JOptionPane.showMessageDialog(null, "In hoá đơn nè!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_btnThanhToanMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChuyenBan;
    private javax.swing.JButton btnGoiMon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemBan;
    private javax.swing.JButton btnXoaBan;
    private javax.swing.JButton btnXoaDU;
    private javax.swing.JComboBox<String> cmbKM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel lbFreeColor;
    private javax.swing.JLabel lbServingColor;
    private javax.swing.JLabel lbWaittingColor;
    private javax.swing.JPanel pnlDSBan;
    private javax.swing.JPanel pnlDSDU;
    private javax.swing.JPanel pnlDSLoaiDU;
    private javax.swing.JPanel pnlFunctionKey;
    private javax.swing.JPanel pnlOrder;
    private javax.swing.JScrollPane spnlTable;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtPhaiTra;
    private javax.swing.JTextField txtTenBan;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
