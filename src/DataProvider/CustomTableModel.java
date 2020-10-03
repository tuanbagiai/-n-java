/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProvider;

import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HT
 */
public class CustomTableModel extends DefaultTableModel {

    public CustomTableModel(Vector TableRecords, Vector TableTitle) {
        super(TableRecords, TableTitle);
    }

    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return ((columnIndex == 1) || (columnIndex == 2));
    }
    
    @Override
    public void setValueAt(Object aValue, int row, int column) {
        
        if (column == 1)
        {
            if (Integer.valueOf((String)aValue) < 1)
            {
                JOptionPane.showMessageDialog(null, "Số lượng không thể nhỏ hơn 1", "Lỗi", JOptionPane.OK_OPTION);
                return;
            }
        }
        
        if (column == 2)
        {
            if (Long.valueOf((String)aValue) < 500)
            {
                JOptionPane.showMessageDialog(null, "Giá bán không thể nhỏ hơn 500 đồng", "Lỗi", JOptionPane.OK_OPTION);
                return;
            }
        }
        
        Vector rowVector = (Vector)dataVector.elementAt(row);
        if (column == 1)
        {
            rowVector.setElementAt(Integer.valueOf((String)aValue), column);
        }
        else if (column == 2)
        {
            rowVector.setElementAt(Long.valueOf((String)aValue), column);
        }
        
        fireTableCellUpdated(row, column);
        
        System.out.println("setValueAt has called");
    }
}
