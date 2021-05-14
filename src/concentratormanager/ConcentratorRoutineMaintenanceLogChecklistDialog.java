package concentratormanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**

 @author R-Mule
 */
public class ConcentratorRoutineMaintenanceLogChecklistDialog extends JDialog {

    JButton saveButton = new JButton("Ok");
    JCheckBox disinfectCheckBox = new JCheckBox();
    JCheckBox cleanExteriorCheckBox = new JCheckBox();
    JCheckBox alarmCheckBox = new JCheckBox();
    JCheckBox powerCordCheckBox = new JCheckBox();
    JCheckBox groundPlugCheckBox = new JCheckBox();
    JTextField o2ConcentrationTextField = new JTextField();
    JTextField flowAccuracyTextField = new JTextField();
    MainFrame mf;
    LocalDateTime time;
    String serialNumber;
    protected boolean pass = false;
    ConcentratorRoutineMaintenanceLog crmLog;

    public ConcentratorRoutineMaintenanceLogChecklistDialog(MainFrame mf) {
        this.setTitle("Add Concentrator Menu");
        JPanel pane = new JPanel();
        JLabel disinfectedLabel = new JLabel("Disinfected:");
        JLabel cleanExteriorLabel = new JLabel("Clean Exterior:");
        JLabel alarmLabel = new JLabel("Alarm:");
        JLabel o2ConcentrationLabel = new JLabel("O2 Concentration:");
        JLabel flowAccuracyLabel = new JLabel("Flow Accuracy:");
        JLabel powerCordLabel = new JLabel("Power Cord:");
        JLabel groundPlugLabel = new JLabel("Ground Plug:");
        pane.setLayout(new GridBagLayout());
        this.add(pane);
        GridBagConstraints c = new GridBagConstraints();
        this.setModal(true);
        this.setSize(400, 400);
        pane.setSize(400, 400);
        this.setLocation(800, 200);
        this.mf = mf;

        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (validateSave())
                {
                    closeDialog();
                }
            }
        });

        c.weighty = 1;

        //c.ipadx = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        pane.add(disinfectedLabel, c);
        c.gridy = 1;
        pane.add(cleanExteriorLabel, c);
        c.gridy = 2;
        pane.add(alarmLabel, c);
        c.gridy = 3;
        pane.add(o2ConcentrationLabel, c);
        c.gridy = 4;
        pane.add(flowAccuracyLabel, c);
        c.gridy = 5;
        pane.add(powerCordLabel, c);
        c.gridy = 6;
        pane.add(groundPlugLabel, c);

        c.insets = new Insets(0, 10, 0, 10);
        c.ipadx = 50;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(disinfectCheckBox, c);
        c.gridy = 1;
        pane.add(cleanExteriorCheckBox, c);
        c.gridy = 2;
        pane.add(alarmCheckBox, c);
        c.gridy = 3;
        pane.add(o2ConcentrationTextField, c);
        c.gridy = 4;
        pane.add(flowAccuracyTextField, c);
        c.gridy = 5;
        pane.add(powerCordCheckBox, c);
        c.gridy = 6;
        pane.add(groundPlugCheckBox, c);

        c.gridx = 0;
        c.gridy = 8;
        pane.add(saveButton, c);

    }

    public void showDialog(String serialNumber, LocalDateTime time) {
        this.pass = false;
        disinfectCheckBox.setSelected(false);
        cleanExteriorCheckBox.setSelected(false);
        alarmCheckBox.setSelected(false);
        powerCordCheckBox.setSelected(false);
        groundPlugCheckBox.setSelected(false);
        flowAccuracyTextField.setText("");
        o2ConcentrationTextField.setText("");
        o2ConcentrationTextField.requestFocus();
        this.time = time;
        this.serialNumber = serialNumber;
        this.setVisible(true);
    }

    public void closeDialog() {
        this.setVisible(false);
    }

    public boolean validateSave() {
        if (!disinfectCheckBox.isSelected())
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "You must disinfect Concentrator before continuing.");
            return false;
        }

        if (!cleanExteriorCheckBox.isSelected())
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "You must clean the exterior of the Concentrator before continuing.");
            return false;
        }
        if (!alarmCheckBox.isSelected())
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "You must check the Concentrator's Alarm before continuing.");
            return false;
        }
        if (!groundPlugCheckBox.isSelected())
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "You must check the Concentrator's Ground Plug before continuing.");
            return false;
        }
        if (!powerCordCheckBox.isSelected())
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "You must check the Concentrator's Power Cord before continuing.");
            return false;
        }

        String o2ConText = o2ConcentrationTextField.getText();
        if (!validateDouble(o2ConText))
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "O2 Concentration must be a valid number.");
            return false;
        }
        String flowAccuracyText = flowAccuracyTextField.getText();
        if (!validateDouble(flowAccuracyText))
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "Flow Accuracy must be a valid number.");
            return false;
        }

        double o2Con = Double.parseDouble(o2ConText);
        if (o2Con < 2 || o2Con > 5)
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "O2 Concentration must be greater than 2 liters, but no more than 5 liters.");
            return false;
        }

        double flowAccuracy = Double.parseDouble(flowAccuracyText);
        if (flowAccuracy < 90 || flowAccuracy > 100)
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "Flow Accuracy must be greater than 90% and no more than 100%.");
            return false;
        }

        ConcentratorRoutineMaintenanceLog conLog = new ConcentratorRoutineMaintenanceLog(true, true, true, o2Con, flowAccuracy, true, true, time, mf.activeEmployee.name);
        this.crmLog = conLog;
        this.pass = true;
        Database.addConcentratorRoutineLog(serialNumber, conLog);
        return true;
    }

    protected boolean validateDouble(String copay) {
        try
        {
            double cpay = Double.parseDouble(copay);
            if (cpay < 0)
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
