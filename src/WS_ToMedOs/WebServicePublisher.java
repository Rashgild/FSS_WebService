package WS_ToMedOs;

import HelpersMethods.GlobalVariables;
import HelpersMethods.SQLConnect;

import javax.xml.ws.Endpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class WebServicePublisher {
    public static void main(String... args) throws IOException {

        System.out.println("------------------------------------------------------------");
        GlobalVariables.GetConfiguration();
        try {
            GetConfiguration();
        }finally {
            System.err.println("Конфигурация загружена");
        }

        System.setProperty("javax.net.ssl.trustStore",GlobalVariables.PathToSSLcert[1]);//КОНФ
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        try {
            Endpoint.publish(GlobalVariables.ipWS[1]+":"+GlobalVariables.portWS[1]+"/webservice/start", new WebServiceIMPL());
        }catch(Error e){
            System.err.println(e.toString());
        }finally {
            System.err.println("Сервис запущен успешно! На адресе: ");
            System.err.println(GlobalVariables.ipWS[1]+":"+GlobalVariables.portWS[1]+"/webservice/start?wsdl");
        }

        try {
            System.err.println("URL DB: "+GlobalVariables.urlDB[1]);
            System.err.println("Попытка подключения к БД...");
            SQLConnect.SQL_Select("select id from Patient limit 1");

        }catch (Exception ex) {
            // System.out.println("123!");
            ex.printStackTrace();
            Logger.getLogger(WebServiceIMPL.class.getName()).log(Level.SEVERE, null, ex);}

        finally {
            System.err.println("...Подключено!");
        }

        System.err.println("Ключевой алиас: "+GlobalVariables.KeyAliasMO[1]);
        System.err.println("Сертификат УЛ: "+GlobalVariables.CertAlias[1]);
        System.out.println("------------------------------------------------------------");

    }

    public static void GetConfiguration() throws IOException {

        InputStream inp = ClassLoader.getSystemClassLoader().getResourceAsStream("Configuration.conf");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inp));
        String line;
        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }


        String strin = lines.get(0);
        GlobalVariables.urlDB = strin.split("=");
        strin = lines.get(1);
        GlobalVariables.nameDB = strin.split("=");
        strin = lines.get(2);
        GlobalVariables.passwordDB = strin.split("=");
        strin = lines.get(3);
        GlobalVariables.ipWS = strin.split("=");
        strin = lines.get(4);
        GlobalVariables.portWS = strin.split("=");
        strin = lines.get(5);
        GlobalVariables.ogrnMo = strin.split("=");
        strin = lines.get(6);
        GlobalVariables.KeyAliasMO = strin.split("=");
        strin = lines.get(7);
        GlobalVariables.KeyPasswordMO = strin.split("=");
        strin = lines.get(8);
        GlobalVariables.PathToSSLcert = strin.split("=");
        strin = lines.get(9);
        GlobalVariables.PathToSave = strin.split("=");
        strin = lines.get(10);
        GlobalVariables.PathToCertStore = strin.split("=");
        strin = lines.get(11);
        GlobalVariables.CertAlias = strin.split("=");
        strin = lines.get(12);
        GlobalVariables.CertPassword = strin.split("=");

        //System.out.println(PathToSave[1]);

        if(GlobalVariables.passwordDB[1].equalsIgnoreCase("null"))
        {
            GlobalVariables.passwordDB[1] = "";
        }
    }
}
