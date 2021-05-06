
package concentratormanager;

import javax.swing.table.DefaultTableModel;

/**

 @author R-Mule
 */
public class NonEditableTableModel extends DefaultTableModel{


    NonEditableTableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
