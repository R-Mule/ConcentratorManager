package concentratormanager;

/**

 @author R-Mule
 */
public enum ConcentratorMake {
    INVACARE("Invacare");

    public String name;

    public static ConcentratorMake getByName(String state) {
        switch (state)
        {
            case "Invacare":
                return ConcentratorMake.INVACARE;

        }
        return null;
    }

       // private final String name;

        ConcentratorMake(String s) {
            name = s;
        }
    }

