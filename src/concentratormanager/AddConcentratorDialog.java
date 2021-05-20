package concentratormanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

/**

 @author R-Mule
 */
public class AddConcentratorDialog extends JDialog {

    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");
    JTextField serialNumberTextField = new JTextField();
    JComboBox makeComboBox = new JComboBox();
    JComboBox modelComboBox = new JComboBox();
    JTextField currentHoursTextField = new JTextField();
    JTextField nextMaintHoursTextField = new JTextField();
    JComboBox locationComboBox = new JComboBox();
    JTextField locationDescTextField = new JTextField();
    JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
    ConcentratorRoutineMaintenanceLogChecklistDialog checklist;
    MainFrame mf;

    public AddConcentratorDialog(MainFrame mf) {
        this.setTitle("Add Concentrator Menu");
        JPanel pane = new JPanel();
        JLabel serialNumberLabel = new JLabel("Serial Number:");
        JLabel makeLabel = new JLabel("Make:");
        JLabel modelLabel = new JLabel("Model:");
        JLabel currentHoursLabel = new JLabel("Current Hours:");
        JLabel nextMaintHoursLabel = new JLabel("Next Maintenance Hours:");
        JLabel locationLabel = new JLabel("Location:");
        JLabel locationDescLabel = new JLabel("Location Description:");
        JLabel dateTimeLabel = new JLabel("Date & Time:");
        pane.setLayout(new GridBagLayout());
        this.add(pane);
        GridBagConstraints c = new GridBagConstraints();
        this.setModal(true);
        this.setSize(400, 400);
        pane.setSize(400, 400);
        this.setLocation(800, 200);
        this.mf = mf;
        checklist = new ConcentratorRoutineMaintenanceLogChecklistDialog(mf);
        
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (validateSave())
                {
                    closeDialog();
                }
            }
        });

        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                closeDialog();
            }
        });

        for (ConcentratorMake cm : ConcentratorMake.values())
        {
            makeComboBox.addItem(cm.name);
        }

        for (ConcentratorModel cm : ConcentratorModel.values())
        {
            modelComboBox.addItem(cm.name);
        }

        for (ConcentratorState cs : ConcentratorState.values())
        {
            locationComboBox.addItem(cs.name);
        }

        c.weighty = 1;

        //c.ipadx = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        pane.add(serialNumberLabel, c);
        c.gridy = 1;
        pane.add(makeLabel, c);
        c.gridy = 2;
        pane.add(modelLabel, c);
        c.gridy = 3;
        pane.add(currentHoursLabel, c);
        c.gridy = 4;
        pane.add(nextMaintHoursLabel, c);
        c.gridy = 5;
        pane.add(locationLabel, c);
        c.gridy = 6;
        pane.add(locationDescLabel, c);
        c.gridy = 7;
        pane.add(dateTimeLabel, c);

        c.insets = new Insets(0, 10, 0, 10);
        c.ipadx = 50;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(serialNumberTextField, c);
        c.gridy = 1;
        pane.add(makeComboBox, c);
        c.gridy = 2;
        pane.add(modelComboBox, c);
        c.gridy = 3;
        pane.add(currentHoursTextField, c);
        c.gridy = 4;
        pane.add(nextMaintHoursTextField, c);
        c.gridy = 5;
        pane.add(locationComboBox, c);
        c.gridy = 6;
        pane.add(locationDescTextField, c);
        c.gridy = 7;
        pane.add(timeSpinner, c);

        c.gridx = 0;
        c.gridy = 8;
        pane.add(saveButton, c);
        c.gridx = 1;
        pane.add(cancelButton, c);

    }

    public void showDialog() {
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "MM/dd/yy hh:mm:ss aa");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());
        serialNumberTextField.setText("");
        currentHoursTextField.setText("0");
        nextMaintHoursTextField.setText("10000");
        serialNumberTextField.requestFocus();

        this.setVisible(true);
    }

    public void closeDialog() {
        this.setVisible(false);
    }

    public boolean validateSave() {
        String serialNumber = serialNumberTextField.getText().toUpperCase();
        if (serialNumber.isEmpty() || Database.doesConcentratorExist(serialNumber))
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "Serial Number is blank or already exists.");
            return false;
        }

        ConcentratorMake make = ConcentratorMake.getByName(makeComboBox.getSelectedItem().toString());
        ConcentratorModel model = ConcentratorModel.getByName(modelComboBox.getSelectedItem().toString());
        if (make == ConcentratorMake.DRIVE && (model == ConcentratorModel.PERFECTO_2 || model == ConcentratorModel.PLATINUM_MOBILE || model == ConcentratorModel.PLATINUM_XL_5))
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "Invalid Make/Model combination.");
            return false;
        }

        if (make == ConcentratorMake.INVACARE && model == ConcentratorModel.DEVILBISS)
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "Invalid Make/Model combination.");
            return false;
        }
        String currentHoursText = currentHoursTextField.getText();
        String nextMaintHoursText = nextMaintHoursTextField.getText();

        if (!validateInteger(currentHoursText))
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "Current Hours must be a number.");
            return false;
        }

        if (!validateInteger(nextMaintHoursText))
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "Next Maintenance Hours must be a number.");
            return false;
        }

        String locationDesc = locationDescTextField.getText();
        if (locationDesc == null)
        {
            locationDesc = "";
        }

        int currentHours = Integer.parseInt(currentHoursText);
        int nextMaintHours = Integer.parseInt(nextMaintHoursText);

        ConcentratorState cState = ConcentratorState.getByName(locationComboBox.getSelectedItem().toString());
        if (cState == ConcentratorState.WITH_PATIENT && (locationDesc == null || locationDesc.isEmpty()))
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "If the Concentrator is with a patient you must have a description (Patient Name).");
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy");

        LocalDateTime time = LocalDateTime.parse(timeSpinner.getValue().toString(), formatter);

        Concentrator concentrator = new Concentrator(serialNumber, make, model);
        ConcentratorData cd = new ConcentratorData(currentHours, nextMaintHours, cState, locationDesc, time, mf.activeEmployee.name);
        
        if(cd.location == ConcentratorState.IN_CLEAN_ROOM)
        {
            do{
            checklist.showDialog(serialNumber, time);
            }while(!checklist.pass);
        }
        
        Database.addConcentrator(concentrator);
        Database.addConcentratorLog(serialNumber, cd);
        mf.reloadConcentratorTable();
        return true;
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
