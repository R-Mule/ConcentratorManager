
package concentratormanager;

import java.time.LocalDateTime;

/**

 @author R-Mule
 */
public class ConcentratorData {
    LocalDateTime modificationDate;
    int currentHours;
    int nextMaintHours;
    ConcentratorState location;
    String locationDesc;
    String loggedByEmployee;
    
    public ConcentratorData(int currentHours, int nextMainHours, ConcentratorState location, String locationDesc, LocalDateTime modificationDateTime, String loggedByEmployee)
    {
        this.modificationDate = modificationDateTime;
        this.currentHours = currentHours;
        this.nextMaintHours = nextMainHours;
        this.location = location;
        this.locationDesc = locationDesc;
        this.loggedByEmployee = loggedByEmployee;
    }
    
    
}
