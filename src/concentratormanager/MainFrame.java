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

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.TableColumn;

/**

 @author R-Mule
 */
public class MainFrame extends javax.swing.JFrame {

    private JLabel versionHeader = new JLabel("Version 1.0", SwingConstants.LEFT);
    Timer timer;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    JButton clerkLoginButton = new JButton("Clerk Login");
    JButton clerkLogoutButton = new JButton("Clerk Logout");
    private JTextField textField = new JTextField(10);
    JLabel employeeSelectionHeader = new JLabel("Active Clerk: NONE", SwingConstants.LEFT);
    Employee activeEmployee;
    //05/07/2021 creating strings for formatted JButton labels
    String addConcentratorButtonText = "Add\nConcentrator";
    String deleteConcentratorButtonText = "Delete\nConcentrator";
    String showConcentratorLogButtonText = "Show\nConcentrator\nLog";
    String editConcentratorButtonText = "Edit\nConcentrator";
    //05/07/2021 adding concentrator utility buttons with labels:
    JButton addConcentratorButton = new JButton("<html>" + addConcentratorButtonText.replaceAll("\\n", "<br>") + "</html>");
    JButton deleteConcentratorButton = new JButton("<html>" + deleteConcentratorButtonText.replaceAll("\\n", "<br>") + "</html>");
    JButton showConcentratorLogButton = new JButton("<html>" + showConcentratorLogButtonText.replaceAll("\\n", "<br>") + "</html>");
    JButton editConcentratorButton = new JButton("<html>" + editConcentratorButtonText.replaceAll("\\n", "<br>") + "</html>");

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
        addConcentratorButton.setLocation(1320, 100);
        addConcentratorButton.setSize(100, 100);
        addConcentratorButton.setBackground(new Color(143, 247, 122));
        addConcentratorButton.setVisible(true);
        this.add(addConcentratorButton);

        //This creates the deleteConcentratorButton
        deleteConcentratorButton.setLocation(1420, 100);
        deleteConcentratorButton.setSize(100, 100);
        deleteConcentratorButton.setBackground(new Color(247, 122, 131));
        deleteConcentratorButton.setVisible(true);
        this.add(deleteConcentratorButton);

        //This creates the showConcentratorLogButton
        showConcentratorLogButton.setLocation(1520, 100);
        showConcentratorLogButton.setSize(100, 100);
        showConcentratorLogButton.setBackground(new Color(101, 228, 242));
        showConcentratorLogButton.setVisible(true);
        this.add(showConcentratorLogButton);

        //This creates the editConcentratorButton
        editConcentratorButton.setLocation(1620, 100);
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
                textField.requestFocusInWindow();//this keeps focus on the UPC BAR READER
            }
        });
        clerkLogoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                employeeSelectionHeader.setText("Active Clerk: NONE");
                clerkLogoutButton.setVisible(false);
                activeEmployee = null;
                textField.requestFocusInWindow();//this keeps focus on the UPC BAR READER
            }
        });
        //Init Concentrator Table
        String[] columnNames =
        {
            "Serial #",
            "Make",
            "Model",
            "Current Hours",
            "Next Maintenance Hours",
            "Location",
            "Last Modified"
        };
        //TODO needs loaded from Database..
        ArrayList<Concentrator> concentrators = Database.getConcentrators();
        //Object[][] data = new Object[concentrators.size()][];
        int conCntr = 0;
        Object[][] data = new Object[concentrators.size()][7];
        for (Concentrator c : concentrators)
        {
            if (!c.archived)
            {
                ConcentratorData cd = c.getLatestLog();
                data[conCntr][0] = c.serialNumber;
                data[conCntr][1] = c.make;
                data[conCntr][2] = c.model;
                data[conCntr][3] = cd.currentHours;
                data[conCntr][4] = cd.nextMaintHours;
                data[conCntr][5] = cd.location;
                data[conCntr][6] = cd.modificationDate.format(formatter);
            }
            conCntr++;
        }



        JTable table = new JTable(data, columnNames);
        table.setSize(1200, 1000);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        this.add(scrollPane);
        scrollPane.setSize(1200, 800);
        scrollPane.setLocation(100, 100);
        TableColumn column = null;
        table.setModel(new NonEditableTableModel(data, columnNames));
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
}
