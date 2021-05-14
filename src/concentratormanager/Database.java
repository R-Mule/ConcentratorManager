package concentratormanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**

 @author A.Smith
 */
public class Database {

    private static String host;
    private static String userName;
    private static String password;
    private static String driverPath = "com.mysql.cj.jdbc.Driver";
    //private ConfigFileReader reader;
    private static int addedCntr = 0;
    private static int updatedCntr = 0;
    private static int failedCntr = 0;
    private static int unchangedCntr = 0;
    private static int linesProcessed = 0;

    private Database() {
    }//end databaseCtor

    public static void loadDatabase() {
        host = ConfigFileReader.getHostName();
        userName = ConfigFileReader.getUserName();
        password = ConfigFileReader.getPassword();
    }

    public static void addConcentrator(Concentrator concentrator) {
        try
        {
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO concentrators (pid,serialNumber,make,model) VALUES (NULL,'" + concentrator.serialNumber + "', '" + concentrator.make.name + "','" + concentrator.model.name + "')");
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }//end catch
    }

    public static void addConcentratorLog(String serialNumber, ConcentratorData cd) {
        try
        {
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO concentratorLog (pid,serialNumber,currentHours,nextMaintHours,location,locationDesc,modificationTime,loggedByEmployee) VALUES (NULL,'" + serialNumber + "'," + cd.currentHours + "," + cd.nextMaintHours + ",'" + cd.location.name + "','" + cd.locationDesc + "','" + sdf.format(cd.modificationDate) + "','" + cd.loggedByEmployee + "')");
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }//end catch
    }

        public static void addConcentratorRoutineLog(String serialNumber, ConcentratorRoutineMaintenanceLog cd) {
        try
        {
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO concentratorRoutineLog (pid,serialNumber,disinfect,cleanExterior,checkAlarm,o2Concentration,checkFlowAccuracy,checkPowerCord,checkGroundPlug,modificationTime,loggedByEmployee) VALUES (NULL,'" + serialNumber + "'," + cd.disinfect + "," + cd.cleanExterior + "," + cd.checkAlarm + "," + cd.o2Concentration +","+ cd.flowAccuracy + ","+ cd.checkPowerCord +","+ cd.checkGroundPlug +",'" + sdf.format(cd.modificationDate) + "','" + cd.loggedByEmployee + "')");
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }//end catch
    }
        
    public static String getEmployeesSortByPID() {
        try
        {
            String bigList = "";
            // ArrayList<String> data = new ArrayList<>();
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from employees order by pid asc,empname;");
            while (rs.next())
            {
                bigList += rs.getInt(1) + " : " + rs.getString(2) + "\n";
            }//end while
            con.close();
            return bigList;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public static ArrayList<Concentrator> getConcentrators() {
        try
        {
            ArrayList<Concentrator> concentrators = new ArrayList<>();
            // ArrayList<String> data = new ArrayList<>();
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from concentrators;");
            while (rs.next())
            {
                concentrators.add(new Concentrator(rs.getString(2), ConcentratorMake.getByName(rs.getString(3)), ConcentratorModel.getByName(rs.getString(4))));
            }//end while
            con.close();
            return concentrators;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public static Concentrator getConcentratorBySerialNumber(String serialNumber) {
        try
        {
            Concentrator concentrator = null;
            // ArrayList<String> data = new ArrayList<>();
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from concentrators where serialNumber = '" + serialNumber + "';");
            while (rs.next())
            {
                concentrator = new Concentrator(rs.getString(2), ConcentratorMake.getByName(rs.getString(3)), ConcentratorModel.getByName(rs.getString(4)));
            }//end while
            con.close();
            return concentrator;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public static boolean doesConcentratorExist(String serialNumber) {
        try
        {
            boolean found = false;
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from concentrators where serialNumber = '" + serialNumber + "';");
            while (rs.next())
            {
                found = true;
            }//end while
            con.close();
            return found;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;
    }

    public static ArrayList<ConcentratorRoutineMaintenanceLog> getConcentratorRountineLogBySerialNumber(String serialNumber) {
        try
        {
            ArrayList<ConcentratorRoutineMaintenanceLog> concentratorLog = new ArrayList<>();
            // ArrayList<String> data = new ArrayList<>();
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from concentratorRoutineLog where serialNumber = '" + serialNumber + "' order by modificationTime;");
            while (rs.next())
            {
                concentratorLog.add(new ConcentratorRoutineMaintenanceLog(rs.getBoolean(3), rs.getBoolean(4), rs.getBoolean(5), rs.getDouble(6), rs.getDouble(7), rs.getBoolean(8), rs.getBoolean(9), rs.getTimestamp(10).toLocalDateTime(), rs.getString(11)));
            }//end while
            con.close();
            return concentratorLog;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public static ArrayList<ConcentratorData> getConcentratorLogBySerialNumber(String serialNumber) {
        try
        {
            ArrayList<ConcentratorData> concentratorLog = new ArrayList<>();
            // ArrayList<String> data = new ArrayList<>();
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from concentratorLog where serialNumber = '" + serialNumber + "' order by modificationTime;");
            while (rs.next())
            {
                concentratorLog.add(new ConcentratorData(rs.getInt(3), rs.getInt(4), ConcentratorState.getByName(rs.getString(5)), rs.getString(6), rs.getTimestamp(7).toLocalDateTime(), rs.getString(8)));
            }//end while
            con.close();
            return concentratorLog;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public static ArrayList<Employee> getEmployeesListSortByPID() {
        ArrayList<Employee> employees = new ArrayList<>();
        try
        {
            // ArrayList<String> data = new ArrayList<>();
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from employees order by pid asc,empname;");
            while (rs.next())
            {
                employees.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(7), rs.getInt(3), rs.getInt(6), rs.getInt(4), rs.getInt(5), rs.getString(8), rs.getString(9)));
            }//end while
            con.close();
            return employees;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;

    }

    public static boolean checkIfPasscodeExisits(int passCode) {
        try
        {
            ArrayList<String> data = new ArrayList<>();
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select passcode from employees;");
            while (rs.next())
            {
                if (rs.getInt(1) == passCode)
                {
                    return true;
                }

            }//end while
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;
    }

    public static String[] getEmployeesFromDatabase() {
        String[] employees = new String[20];
        int cntr = 0;
        try
        {
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from employees order by empname;");

            while (rs.next())
            {

                employees[cntr] = rs.getString(2);
                cntr++;
            }//end while

            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        String[] emp = new String[cntr];
        for (int i = 0; i < cntr; i++)
        {
            emp[i] = employees[i];
        }
        if (emp.length == 0)
        {
            return null;
        }
        return emp;
    }//end getTicketFromDatabase

    public static String getEmployeeNameByCode(int code) {

        try
        {
            Class.forName(driverPath);
            Connection con = DriverManager.getConnection(
                    host, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from employees where passcode = " + code);
            while (rs.next())
            {
                //Statement stmt2 = con.createStatement();
                //stmt2.executeUpdate("UPDATE `inventory` set price=" + price + " where mutID = '" + mutID + "';");
                String temp = rs.getString(2);
                con.close();
                return temp;
            }//end while

            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    private static double round(double num) {//rounds to 2 decimal places.
        num = Math.round(num * 100.0) / 100.0;
        return num;
    }//end round

}//end Database
