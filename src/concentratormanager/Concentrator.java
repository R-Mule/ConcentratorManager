
package concentratormanager;

import java.util.ArrayList;

/**

 @author R-Mule
 */
public class Concentrator {
    String serialNumber;
    ConcentratorMake make;
    ConcentratorModel model;
    ArrayList<ConcentratorData> logData;
    public Concentrator(String serialNumber, ConcentratorMake make, ConcentratorModel model)
    {
        this.serialNumber = serialNumber;
        this.make = make;
        this.model = model;
        this.logData = Database.getConcentratorLogBySerialNumber(this.serialNumber);
    }
    
    public ConcentratorData getLatestLog()
    {
     ConcentratorData mostRecent = logData.get(0);
     for(ConcentratorData cd : logData)
     {
         if(mostRecent.modificationDate.isBefore(cd.modificationDate))
         {
             mostRecent = cd;
         }
         
     }
     return mostRecent;
    }
    
}
