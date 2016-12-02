package HelpersMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class SQLConnect {

    public static ResultSet SQL_Select (String reqSQL) {
        Connection connection = null;

        ResultSet result1 = null;

        try {
            Class.forName("org.postgresql.Driver");

            //GlobalVariables.DisabilityDocument_id ="1010";
            //System.out.println(GlobalVariables.DisabilityDocument_id);
            //System.out.println("Драйвер подключен");
            connection = DriverManager.getConnection(GlobalVariables.urlDB[1],
                    GlobalVariables.nameDB[1], GlobalVariables.passwordDB[1]);
            //System.out.println("Соединение установлено");
            Statement statement = null;
            statement = connection.createStatement();
            //Выполним запрос
            result1 = statement.executeQuery(reqSQL);
            String N="";
            connection.close();

            return result1;

        } catch (Exception ex) {

            ex.printStackTrace();
        }
            //Logger.getLogger(WebServiceImpl.class.getName()).log(Level.SEVERE, null, ex);}
        return result1;
    }

    public static ResultSet SQL_UpdIns (String s)
    {
        Connection connection = null;
        ResultSet res= null;
        try {
            Class.forName("org.postgresql.Driver");
            //System.out.println("Драйвер подключен");
            connection = DriverManager.getConnection(GlobalVariables.urlDB[1],
                    GlobalVariables.nameDB[1], GlobalVariables.passwordDB[1]);

            //System.out.println("Соединение установлено");
            Statement statement = null;
            statement = connection.createStatement();

            res  =   statement.executeQuery(s);
            connection.close();
            return res;

        } catch (Exception ex) {
            ex.printStackTrace();
            }
        return res;
    }
}
