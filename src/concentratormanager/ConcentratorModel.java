package concentratormanager;

/**

 @author R-Mule
 */
public enum ConcentratorModel {
    PERFECTO_V2("Perfecto V2");

    public String name;

    public static ConcentratorModel getByName(String state) {
        switch (state)
        {
            case "Perfecto V2":
                return ConcentratorModel.PERFECTO_V2;

        }
        return null;
    }

       // private final String name;

        ConcentratorModel(String s) {
            name = s;
        }
    }

