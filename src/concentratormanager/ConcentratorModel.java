package concentratormanager;

/**

 @author R-Mule
 */
public enum ConcentratorModel {
    PERFECTO_2("Perfecto 2"), PLATINUM_XL_5("Platinum XL 5"), PLATINUM_MOBILE("Platinum Mobile"), DEVILBISS("DeVilbiss");

    public String name;

    public static ConcentratorModel getByName(String state) {
        switch (state)
        {
            case "Perfecto 2":
                return ConcentratorModel.PERFECTO_2;
            case "Platinum XL 5":
                return ConcentratorModel.PLATINUM_XL_5;
            case "Platinum Mobile":
                return ConcentratorModel.PLATINUM_MOBILE;
            case "DeVilbiss":
                return ConcentratorModel.DEVILBISS;

        }
        return null;
    }

    // private final String name;
    ConcentratorModel(String s) {
        name = s;
    }
}
