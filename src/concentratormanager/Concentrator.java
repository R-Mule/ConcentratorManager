
package concentratormanager;

import java.util.ArrayList;

/**

 @author R-Mule
 */
public class Concentrator {
    String serialNumber;
    String make;
    String model;
    boolean archived;
    ArrayList<ConcentratorData> logData;
    public Concentrator(String serialNumber, String make, String model, boolean archived)
    {
        this.serialNumber = serialNumber;
        this.make = make;
        this.model = model;
        this.archived = archived;
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
