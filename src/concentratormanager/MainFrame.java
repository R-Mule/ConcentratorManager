package concentratormanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.TableColumn;

/**

 @author R-Mule
 */
public class MainFrame extends javax.swing.JFrame {

    private JLabel versionHeader = new JLabel("Version 1.0", SwingConstants.LEFT);
    Timer timer;
    JTable table;
    AddConcentratorDialog addConcentratorDialog = new AddConcentratorDialog();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    JButton clerkLoginButton = new JButton("Clerk Login");
    JButton clerkLogoutButton = new JButton("Clerk Logout");
    JLabel employeeSelectionHeader = new JLabel("Active Clerk: NONE", SwingConstants.LEFT);
    Employee activeEmployee;
    //05/07/2021 creating strings for formatted JButton labels
    String addConcentratorButtonText = "Add\nConcentrator";
    String showConcentratorLogButtonText = "Show\nConcentrator\nLog";
    String editConcentratorButtonText = "Update\nConcentrator";
    //05/07/2021 adding concentrator utility buttons with labels:
    JButton addConcentratorButton = new JButton("<html>" + addConcentratorButtonText.replaceAll("\\n", "<br>") + "</html>");
    JButton showConcentratorLogButton = new JButton("<html>" + showConcentratorLogButtonText.replaceAll("\\n", "<br>") + "</html>");
    JButton editConcentratorButton = new JButton("<html>" + editConcentratorButtonText.replaceAll("\\n", "<br>") + "</html>");
    final String[] COLUMN_NAMES =
    {
        "Serial #",
        "Make",
        "Model",
        "Current Hours",
        "Next Maintenance Hours",
        "Location",
        "Location Description",
        "Last Modified",
        "Last Logged By"
    };

    final int SERIAL_NUMBER = 0;
    final int MAKE = 1;
    final int MODEL = 2;
    final int CURRENT_HOURS = 3;
    final int NEXT_MAINTENANCE_HOURS = 4;
    final int LOCATION = 5;
    final int LOCATION_DESCRIPTION = 6;
    final int LAST_MODIFIED = 7;
    final int LOGGED_BY_EMPLOYEE = 8;

    public MainFrame() {
        init();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        // setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // setResizable(false);
        // this.set
        //addLabels();

    }

    private void init() {

        //versionHeader
        versionHeader.setLocation(1750, 925);
        versionHeader.setSize(250, 50);
        versionHeader.setFont(new Font(versionHeader.getName(), Font.BOLD, 12));
        versionHeader.setVisible(true);
        this.add(versionHeader);
        this.setTitle("Smith's Concentrator Manager - Developed by: Andrew & Hollie Smith");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //This creates the clerkLoginButton
        clerkLoginButton.setLocation(10, 920);
        clerkLoginButton.setSize(100, 40);
        clerkLoginButton.setBackground(new Color(0, 255, 0));
        clerkLoginButton.setVisible(true);
        this.add(clerkLoginButton);

        //This creates the clerkLogoutButton
        clerkLogoutButton.setLocation(120, 950);
        clerkLogoutButton.setSize(150, 40);
        clerkLogoutButton.setBackground(new Color(255, 0, 0));
        clerkLogoutButton.setVisible(false);
        this.add(clerkLogoutButton);

        //TODO Add Concentrator, Delete Concentrator, Show Concentrator Log, Edit Concentrator
        //This creates the addConcentratorButton
        addConcentratorButton.setLocation(1720, 100);
        addConcentratorButton.setSize(100, 100);
        addConcentratorButton.setBackground(new Color(143, 247, 122));
        addConcentratorButton.setVisible(true);
        this.add(addConcentratorButton);

        //This creates the showConcentratorLogButton
        showConcentratorLogButton.setLocation(1720, 200);
        showConcentratorLogButton.setSize(100, 100);
        showConcentratorLogButton.setBackground(new Color(101, 228, 242));
        showConcentratorLogButton.setVisible(true);
        this.add(showConcentratorLogButton);

        //This creates the editConcentratorButton
        editConcentratorButton.setLocation(1720, 300);
        editConcentratorButton.setSize(100, 100);
        editConcentratorButton.setBackground(new Color(251, 255, 140));
        editConcentratorButton.setVisible(true);
        this.add(editConcentratorButton);

        employeeSelectionHeader.setBounds(120, 925, 400, 30);
        employeeSelectionHeader.setVisible(true);
        this.add(employeeSelectionHeader);
        clerkLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JFrame textInputFrame = new JFrame("");

                JPasswordField field1 = new JPasswordField();
                field1.addAncestorListener(new RequestFocusListener());
                Object[] message =
                {
                    "Enter Passcode:", field1
                };

                int option = JOptionPane.showConfirmDialog(textInputFrame, message, "Employee Login Menu", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION)
                {
                    char[] field1Value = field1.getPassword();
                    if (field1Value.length > 0)
                    {
                        String tempCode = new String(field1Value);
                        if (validateInteger(tempCode))
                        {
                            int passcode = Integer.parseInt(tempCode);
                            String clerkName = Database.getEmployeeNameByCode(passcode);
                            if (clerkName != null)
                            {
                                employeeSelectionHeader.setText("Active Clerk: " + clerkName);

                                clerkLogoutButton.setVisible(true);

                                ArrayList<Employee> employees = Database.getEmployeesListSortByPID();
                                for (Employee employee : employees)
                                {
                                    if (employee.passcode == passcode)
                                    {
                                        activeEmployee = employee;
                                    }
                                }
                            }

                        }
                    }
                }
            }
        });
        clerkLogoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                employeeSelectionHeader.setText("Active Clerk: NONE");
                clerkLogoutButton.setVisible(false);
                activeEmployee = null;
            }
        });

        editConcentratorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (activeEmployee == null)
                {
                    JFrame message1 = new JFrame("");
                    JOptionPane.showMessageDialog(message1, "You must login first.");
                    return;
                }

                if (getSelectedConcentratorSerialNumber() == null)
                {
                    JFrame message1 = new JFrame("");
                    JOptionPane.showMessageDialog(message1, "No Concentrator selected.");
                    return;
                }

                String serialNumber = getSelectedConcentratorSerialNumber();
                if (isSelectedConcentratorRetired())
                {
                    JFrame message1 = new JFrame("");
                    JOptionPane.showMessageDialog(message1, "Concentrator retired. No state changes allowed.");
                    return;
                }

                //TODO
                System.out.println();
            }
        });

        addConcentratorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (activeEmployee == null)
                {
                    JFrame message1 = new JFrame("");
                    JOptionPane.showMessageDialog(message1, "You must login first.");
                    return;
                }
                
                addConcentratorDialog.showDialog();

                System.out.println();
            }
        });

        //Init Concentrator Table
        ArrayList<Concentrator> concentrators = Database.getConcentrators();
        int conCntr = 0;
        Object[][] data = new Object[concentrators.size()][COLUMN_NAMES.length];
        for (Concentrator c : concentrators)
        {
            ConcentratorData cd = c.getLatestLog();
            data[conCntr][SERIAL_NUMBER] = c.serialNumber;
            data[conCntr][MAKE] = c.make.name;
            data[conCntr][MODEL] = c.model.name;
            data[conCntr][CURRENT_HOURS] = cd.currentHours;
            data[conCntr][NEXT_MAINTENANCE_HOURS] = cd.nextMaintHours;
            data[conCntr][LOCATION] = cd.location.name;
            data[conCntr][LOCATION_DESCRIPTION] = cd.locationDesc;
            data[conCntr][LAST_MODIFIED] = cd.modificationDate.format(formatter);
            data[conCntr][LOGGED_BY_EMPLOYEE] = cd.loggedByEmployee;
            conCntr++;
        }

        table = new JTable(data, COLUMN_NAMES);
        table.setModel(new NonEditableTableModel(data, COLUMN_NAMES));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        this.add(scrollPane);
        scrollPane.setSize(1600, 800);
        scrollPane.setLocation(100, 100);
        table.setSize(1600, 1000);
        TableColumn column = null;

        for (int i = 0; i < 7; i++)
        {
            column = table.getColumnModel().getColumn(i);
            if (i == 0 || i == 4 || i == 5 || i == 6)
            {
                column.setPreferredWidth(200); //third column is bigger
            }
            else
            {
                column.setPreferredWidth(50);
            }

        }

        table.setAutoCreateRowSorter(true);
        table.setRowSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 900, Short.MAX_VALUE)
        );

        pack();
    }

    protected boolean validateInteger(String integer) {
        try
        {
            int integ = Integer.parseInt(integer);
            if (integ < 0)
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }//end catch
        return true;
    }

    private String getSelectedConcentratorSerialNumber() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1)
        {
            return table.getModel().getValueAt(selectedRow, SERIAL_NUMBER).toString();
        }
        return null;
    }

    private boolean isSelectedConcentratorRetired() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1)
        {
            return table.getModel().getValueAt(selectedRow, LOCATION).toString().contentEquals(ConcentratorState.RETIRED.toString());
        }
        return false;
    }

    private void reloadConcentratorTable() {
        ArrayList<Concentrator> concentrators = Database.getConcentrators();
        for (int c = 0; c < concentrators.size(); c++)
        {
            boolean found = false;
            for (int r = 0; r < table.getRowCount(); r++)
            {
                if (table.getModel().getValueAt(r, 0).toString().contentEquals(concentrators.get(c).serialNumber))
                {
                    ConcentratorData cd = concentrators.get(c).getLatestLog();
                    table.getModel().setValueAt(concentrators.get(c).serialNumber, r, SERIAL_NUMBER);
                    table.getModel().setValueAt(concentrators.get(c).make.name, r, MAKE);
                    table.getModel().setValueAt(concentrators.get(c).model.name, r, MODEL);
                    table.getModel().setValueAt(cd.currentHours, r, CURRENT_HOURS);
                    table.getModel().setValueAt(cd.nextMaintHours, r, NEXT_MAINTENANCE_HOURS);
                    table.getModel().setValueAt(cd.location.name, r, LOCATION);
                    table.getModel().setValueAt(cd.locationDesc, r, LOCATION_DESCRIPTION);
                    table.getModel().setValueAt(cd.modificationDate, r, LAST_MODIFIED);
                    table.getModel().setValueAt(cd.loggedByEmployee, r, LOGGED_BY_EMPLOYEE);
                    found = true;
                }
            }
            if (!found)
            {
                addConcentratorToTable(concentrators.get(c));
            }
        }
    }

    private void addConcentratorToTable(Concentrator c) {
        NonEditableTableModel tm = (NonEditableTableModel) table.getModel();
        ConcentratorData cd = c.getLatestLog();
        Object[] rowData =
        {
            c.serialNumber, c.make, c.model, cd.currentHours, cd.nextMaintHours, cd.location, cd.locationDesc, cd.modificationDate
        };
        tm.addRow(rowData);
    }
}
