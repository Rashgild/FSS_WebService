package HelpersMethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class GlobalVariables {
    /*
    -- ОГРН
    -- Путь к файлу
     */

    //public static String ogrn="1023000836099";
    //public static String path="C:\\123\\";


    public static String DisabilityDocument_id="1010";
    public static String eln="0";
    public static String urlDB[];//="jdbc:postgresql://192.168.7.249:5432/riams";
    public static String nameDB[];//="postgres";
    public static String passwordDB[];//="";
    public static String ipWS[];//="http://192.168.4.151";
    public static String portWS[];//="1986";

    public static String ogrnMo[];//="1023000855020";

    public static String KeyAliasMO[];
    public static String KeyPasswordMO[];

    public static String PathToSSLcert[];
    public static String PathToSave[];
    public static String PathToCertStore[];

    public static String CertAlias[];//=FSS.cer
    public static String CertPassword[];//=123456
    /*
    public static String pathtofile[];
    public static String AliasMO[];//="1986";
    public static String PasswordMO[];//="1023000855020";*/

    public static void GetConfiguration() throws IOException {

        InputStream inp = ClassLoader.getSystemClassLoader().getResourceAsStream("configuration.conf");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inp));
        String line;
        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }


        String strin = lines.get(0);
        urlDB = strin.split("=");
        strin = lines.get(1);
        nameDB = strin.split("=");
        strin = lines.get(2);
        passwordDB = strin.split("=");
        strin = lines.get(3);
        ipWS = strin.split("=");
        strin = lines.get(4);
        portWS = strin.split("=");
        strin = lines.get(5);
        ogrnMo = strin.split("=");
        strin = lines.get(6);
        KeyAliasMO = strin.split("=");
        strin = lines.get(7);
        KeyPasswordMO = strin.split("=");
        strin = lines.get(8);
        PathToSSLcert = strin.split("=");
        strin = lines.get(9);
        PathToSave = strin.split("=");
        strin = lines.get(10);
        PathToCertStore = strin.split("=");
        strin = lines.get(11);
        CertAlias = strin.split("=");
        strin = lines.get(12);
        CertPassword = strin.split("=");

        //System.out.println(PathToSave[1]);

        if(passwordDB[1].equalsIgnoreCase("null"))
        {
            passwordDB[1] = "";
        }
    }

    private static void l(String str[])
    {
        //for(int i=0;i<lines.size();i++) {
       // }

    }

}
