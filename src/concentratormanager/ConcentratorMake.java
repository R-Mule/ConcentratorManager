package concentratormanager;

/**

 @author R-Mule
 */
public enum ConcentratorMake {
    INVACARE("Invacare"),DRIVE("Drive");

    public String name;

    public static ConcentratorMake getByName(String state) {
        switch (state)
        {
            case "Invacare":
                return ConcentratorMake.INVACARE;
            case "Drive":
                return ConcentratorMake.DRIVE;

        }
        return null;
    }

       // private final String name;

        ConcentratorMake(String s) {
            name = s;
        }
    }

