package concentratormanager;

/**

 @author R-Mule
 */
public enum ConcentratorState {
    IN_DIRTY_ROOM("Dirty Room"), IN_CLEAN_ROOM("Clean Room"), OUT_FOR_MAINTENANCE("Maintenance"), WITH_PATIENT("W/Patient"), RETIRED("Retired");

    public String name;

    public static ConcentratorState getByName(String state) {
        switch (state)
        {
            case "Dirty Room":
                return ConcentratorState.IN_DIRTY_ROOM;
            case "Clean Room":
                return ConcentratorState.IN_CLEAN_ROOM;
            case "Maintenance":
                return ConcentratorState.OUT_FOR_MAINTENANCE;
            case "W/Patient":
                return ConcentratorState.WITH_PATIENT;
            case "Retired":
                return ConcentratorState.RETIRED;
        }
        return null;
    }

        ConcentratorState(String s) {
            name = s;
        }
        
    }

