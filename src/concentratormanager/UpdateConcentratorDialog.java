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
public class UpdateConcentratorDialog extends JDialog {

    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");
    JTextField currentHoursTextField = new JTextField();
    JTextField nextMaintHoursTextField = new JTextField();
    JComboBox locationComboBox = new JComboBox();
    JTextField locationDescTextField = new JTextField();
    JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
    MainFrame mf;
    Concentrator concentrator;
    JLabel serialNumberValueLabel = new JLabel("");
    JLabel makeValueLabel = new JLabel("");
    JLabel modelValueLabel = new JLabel("");
    ConcentratorRoutineMaintenanceLogChecklistDialog checklist;

    public UpdateConcentratorDialog(MainFrame mf) {
        this.setTitle("Update Concentrator Menu");
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
        pane.add(serialNumberValueLabel, c);
        c.gridy = 1;
        pane.add(makeValueLabel, c);
        c.gridy = 2;
        pane.add(modelValueLabel, c);
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

    public void showDialog(Concentrator concentrator) {
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "MM/dd/yy hh:mm:ss aa");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());
        ConcentratorData cd = concentrator.getLatestLog();
        currentHoursTextField.setText(Integer.toString(cd.currentHours));
        nextMaintHoursTextField.setText(Integer.toString(cd.nextMaintHours));
        this.concentrator = concentrator;
        this.serialNumberValueLabel.setText(concentrator.serialNumber);
        this.makeValueLabel.setText(concentrator.make.name);
        this.modelValueLabel.setText(concentrator.model.name);
        currentHoursTextField.requestFocus();
        this.setVisible(true);
    }

    public void closeDialog() {
        this.setVisible(false);
    }

    public boolean validateSave() {
        ConcentratorData cd = this.concentrator.getLatestLog();

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
        if (cd.currentHours > currentHours)
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "Current Hours must be the same or larger than previously logged hours (" + cd.currentHours + ").");
            return false;
        }

        if (cd.nextMaintHours > nextMaintHours)
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "Next Maintenance Hours must be the same or larger than previously logged Next Maintenance Hours (" + cd.nextMaintHours + ").");
            return false;
        }

        ConcentratorState cState = ConcentratorState.getByName(locationComboBox.getSelectedItem().toString());
        if (cState == ConcentratorState.WITH_PATIENT && (locationDesc == null || locationDesc.isEmpty()))
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "If the Concentrator is with a patient you must have a description (Patient Name).");
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy");

        LocalDateTime time = LocalDateTime.parse(timeSpinner.getValue().toString(), formatter);

        if (time.isBefore(cd.modificationDate))
        {
            JFrame message1 = new JFrame("");
            JOptionPane.showMessageDialog(message1, "Date & Time must be newer than previously logged Date & Time.");
            return false;
        }

        if (cd.location == ConcentratorState.IN_CLEAN_ROOM)
        {
            do
            {
                checklist.showDialog(this.concentrator.serialNumber, time);
            }
            while (!checklist.pass);
        }

        ConcentratorData concentratorData = new ConcentratorData(currentHours, nextMaintHours, cState, locationDesc, time, mf.activeEmployee.name);
        Database.addConcentratorLog(this.concentrator.serialNumber, concentratorData);
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
