package concentratormanager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.Statement;
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
