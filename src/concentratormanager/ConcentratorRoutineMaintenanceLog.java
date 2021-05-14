
package concentratormanager;

import java.time.LocalDateTime;

/**

 @author R-Mule
 */
public class ConcentratorRoutineMaintenanceLog {
    
    LocalDateTime modificationDate;
    String loggedByEmployee;
    boolean disinfect;
    boolean cleanExterior;
    boolean checkAlarm;
    boolean checkPowerCord;
    boolean checkGroundPlug;
    double o2Concentration;
    double flowAccuracy;
    
    public ConcentratorRoutineMaintenanceLog(boolean disinfect, boolean cleanExterior, boolean checkAlarm, double o2Concentration, double flowAccuracy, boolean checkPowerCord, boolean checkGroundPlug, LocalDateTime modificationDateTime, String loggedByEmployee)
    {
        this.modificationDate = modificationDateTime;
        this.disinfect = disinfect;
        this.cleanExterior = cleanExterior;
        this.checkAlarm = checkAlarm;
        this.o2Concentration = o2Concentration;
        this.flowAccuracy = flowAccuracy;
        this.checkPowerCord = checkPowerCord;
        this.checkGroundPlug = checkGroundPlug;
        this.loggedByEmployee = loggedByEmployee;
    }
    
    
}
