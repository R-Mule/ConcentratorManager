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
    ArrayList<ConcentratorRoutineMaintenanceLog> rountineLogData;

    public Concentrator(String serialNumber, ConcentratorMake make, ConcentratorModel model) {
        this.serialNumber = serialNumber;
        this.make = make;
        this.model = model;
        this.logData = Database.getConcentratorLogBySerialNumber(this.serialNumber);
        this.rountineLogData = Database.getConcentratorRountineLogBySerialNumber(this.serialNumber);
    }

    public ConcentratorData getLatestLog() {
        ConcentratorData mostRecent = logData.get(0);
        for (ConcentratorData cd : logData)
        {
            if (mostRecent.modificationDate.isBefore(cd.modificationDate))
            {
                mostRecent = cd;
            }

        }
        return mostRecent;
    }

    public ConcentratorRoutineMaintenanceLog getLatestRoutineLog() {
        ConcentratorRoutineMaintenanceLog mostRecent = rountineLogData.get(0);
        for (ConcentratorRoutineMaintenanceLog cd : rountineLogData)
        {
            if (mostRecent.modificationDate.isBefore(cd.modificationDate))
            {
                mostRecent = cd;
            }

        }
        return mostRecent;
    }
}
