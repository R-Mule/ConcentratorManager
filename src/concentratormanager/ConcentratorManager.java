package concentratormanager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**

 @author R-Mule
 */
public class ConcentratorManager {

    /**
     @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {
            ConfigFileReader.loadConfiguration();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ConcentratorManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.loadDatabase();
        MainFrame jf = new MainFrame();
        jf.setVisible(true);
    }

}
