package WS_ToMedOs;

import HelpersMethods.GlobalVariables;
import HelpersMethods.SQLConnect;

import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class WebServicePublisher {
    public static void main(String... args) throws IOException {
        GlobalVariables.GetConfiguration();
        System.setProperty("javax.net.ssl.trustStore",GlobalVariables.PathToSSLcert[1]);//КОНФ
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        Endpoint.publish(GlobalVariables.ipWS[1]+":"+GlobalVariables.portWS[1]+"/webservice/start", new WebServiceIMPL());
        System.out.println("Сервис запущен успешно! Адрес: \n");
        System.out.println(GlobalVariables.ipWS[1]+":"+GlobalVariables.portWS[1]+"/webservice/start?wsdl");
        System.out.println("Попытка подключения к БД...");

        //ResultSet result = null;
        try {
            //result =
            SQLConnect.SQL_Select("select id from Patient limit 1");
            System.out.println("...Подключено!");
        }catch (Exception ex) {
            // System.out.println("123!");
            ex.printStackTrace();
            Logger.getLogger(WebServiceIMPL.class.getName()).log(Level.SEVERE, null, ex);}

    }
}
