package concentratormanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

    public AddConcentratorDialog() {
        this.setTitle("Add Concentrator Menu");
        JPanel pane = new JPanel();
        JLabel serialNumberLabel = new JLabel("Serial Number:");
        JLabel makeLabel = new JLabel("Make:");
        JLabel modelLabel = new JLabel("Model:");
        JLabel currentHoursLabel = new JLabel("Current Hours:");
        JLabel nextMaintHoursLabel = new JLabel("Next Maintenance Hours:");
        pane.setLayout(new GridBagLayout());
        this.add(pane);
        GridBagConstraints c = new GridBagConstraints();
        this.setModal(true);
        this.setAlwaysOnTop(true);
        this.setSize(400, 400);
        pane.setSize(400, 400);
        this.setLocation(800, 200);

        for (ConcentratorMake cm : ConcentratorMake.values())
        {
            makeComboBox.addItem(cm.name);
        }

        for (ConcentratorModel cm : ConcentratorModel.values())
        {
            modelComboBox.addItem(cm.name);
        }

        c.weightx = 1;

        c.gridx = 0;
        c.gridy = 0;
        pane.add(serialNumberLabel, c);
        c.gridx = 1;
        pane.add(serialNumberTextField, c);

        c.gridx = 0;
        c.gridy = 1;
        pane.add(makeLabel, c);
        c.gridx = 1;
        pane.add(makeComboBox, c);

        c.gridx = 0;
        c.gridy = 2;
        pane.add(modelLabel, c);
        c.gridx = 1;
        pane.add(modelComboBox, c);

        c.gridx = 0;
        c.gridy = 3;
        pane.add(currentHoursLabel, c);
        c.gridx = 1;
        pane.add(currentHoursTextField, c);

        c.gridx = 0;
        c.gridy = 4;
        pane.add(nextMaintHoursLabel, c);
        c.gridx = 1;
        pane.add(nextMaintHoursTextField, c);

        c.gridx = 0;
        c.gridy = 5;
        pane.add(saveButton, c);
        c.gridx = 1;
        pane.add(cancelButton, c);

    }

    public void showDialog() {
        this.setVisible(true);
        currentHoursTextField.setText("0");
        nextMaintHoursTextField.setText("10000");
    }
}
