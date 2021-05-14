package concentratormanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**

 @author R-Mule
 */
public class ConcentratorLogDialog extends JDialog {

    JPanel pane = new JPanel();
    JButton closeButton = new JButton("Close");
    JTable table;
    final String[] COLUMN_NAMES =
    {
        "Current Hours",
        "Next Maintenance Hours",
        "Location",
        "Location Description",
        "Modification Date Time",
        "Logged By"
    };
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    final int CURRENT_HOURS = 0;
    final int NEXT_MAINTENANCE_HOURS = 1;
    final int LOCATION = 2;
    final int LOCATION_DESCRIPTION = 3;
    final int LAST_MODIFIED = 4;
    final int LOGGED_BY_EMPLOYEE = 5;

    public ConcentratorLogDialog() {

    }

    public void showDialog(Concentrator concentrator) {
        pane.removeAll();
        pane.setLayout(new GridBagLayout());
        this.add(pane);
        GridBagConstraints c = new GridBagConstraints();
        this.setModal(true);
        this.setSize(1600, 900);
        pane.setSize(1600, 900);
        this.setLocation(100, 100);
        Object[][] data = new Object[concentrator.logData.size()][COLUMN_NAMES.length];
        int cdCntr = 0;
        for (ConcentratorData cd : concentrator.logData)
        {
            data[cdCntr][CURRENT_HOURS] = cd.currentHours;
            data[cdCntr][NEXT_MAINTENANCE_HOURS] = cd.nextMaintHours;
            data[cdCntr][LOCATION] = cd.location.name;
            data[cdCntr][LOCATION_DESCRIPTION] = cd.locationDesc;
            data[cdCntr][LAST_MODIFIED] = cd.modificationDate.format(formatter);
            data[cdCntr][LOGGED_BY_EMPLOYEE] = cd.loggedByEmployee;
            cdCntr++;
        }

        table = new JTable(data, COLUMN_NAMES);
        table.setModel(new NonEditableTableModel(data, COLUMN_NAMES));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setSize(1600, 800);
        scrollPane.setLocation(0, 0);
        table.setSize(1600, 800);
        closeButton.setSize(100, 100);
        TableColumn column = null;

        for (int i = 0; i < COLUMN_NAMES.length; i++)
        {
            column = table.getColumnModel().getColumn(i);

                column.setPreferredWidth(100); //third column is bigger

                column.setPreferredWidth(50);
        }

        table.setAutoCreateRowSorter(true);
        table.setRowSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);

        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                closeDialog();
            }
        });

        c.weighty = 1;
        c.weightx =1;
        //c.ipadx = 100;
        //c.anchor = GridBagConstraints.FIRST_LINE_END;
        //pane.add(serialNumberLabel, c);
        //c.insets = new Insets(0, 10, 0, 10);
        // c.ipadx = 50;
        //c.anchor = GridBagConstraints.FIRST_LINE_START;
        
c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(scrollPane, c);
        c.gridy = 1;
        c.weighty = 0;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        pane.add(closeButton, c);
        this.setTitle("Concentrator Log For Serial Number: " + concentrator.serialNumber + "    Make: " + concentrator.make.name + "    Model: " + concentrator.model.name);
        this.setVisible(true);
    }

    public void closeDialog() {
        this.setVisible(false);
    }

}
