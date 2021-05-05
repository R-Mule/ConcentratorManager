package concentratormanager;

import java.awt.Font;
import java.awt.event.ActionEvent;

import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**

 @author R-Mule
 */
public class MainFrame extends javax.swing.JFrame {
    
    DateTimeFormatter sdf = DateTimeFormatter.ofPattern("h:mm:ss a    EEEE, MMMM d, yyyy");
    private JLabel clock = new JLabel("", SwingConstants.LEFT);
    private JLabel smsLabel = new JLabel("SMS System Status:",SwingConstants.LEFT);
    private JLabel versionHeader = new JLabel("Version 2.0", SwingConstants.LEFT);
    Timer timer;

    
    
    private JTextField textField = new JTextField(10);
    
    public MainFrame() {
        init();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        // setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // setResizable(false);
        // this.set
        //addLabels();
        textField.setBounds(100, 925, 100, 20);
        //textField.isVisible(true);
        textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (textField.getText().matches("[0-9][0-9][0-9],[0-9][0-9][0-9][0-9][0-9]"))//if it is a long
                {
                    String rfid = textField.getText();

                }
                
                textField.setText("");
            }
        });
        this.add(textField);
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
    
}
