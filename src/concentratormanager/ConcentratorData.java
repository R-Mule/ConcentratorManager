
package concentratormanager;

import java.time.LocalDateTime;

/**

 @author R-Mule
 */
public class ConcentratorData {
    LocalDateTime modificationDate;
    int currentHours;
    int nextMaintHours;
    String location;
    
    public ConcentratorData(int currentHours, int nextMainHours, String location, LocalDateTime modificationDateTime)
    {
        this.modificationDate = modificationDateTime;
        this.currentHours = currentHours;
        this.nextMaintHours = nextMainHours;
        this.location = location;
    }
}
