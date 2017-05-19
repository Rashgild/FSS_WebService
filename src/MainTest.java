import HelpersMethods.GlobalVariables;
import HelpersMethods.SQLConnect;
import HelpersMethods.SQLStoreQuer;
import Jaxb.JaxbParser;
import Jaxb.Parser;

import java.io.File;
import java.sql.ResultSet;

/**
 * Created by rkurbanov on 20.12.2016.
 */
public class MainTest {

    private static Parser parser;
    private static File file;

    private static void setUp() throws Exception {
        parser = new JaxbParser();
        file = new File("person1.xml");
    }


    public static void main(String[] args) throws Exception {
        GlobalVariables.GetConfiguration();

        GlobalVariables.DateFrom="2017-01-16";
        GlobalVariables.DateTo="2017-01-16";
        GlobalVariables.limit="100";
        ResultSet rs = SQLConnect.SQL_Select(SQLStoreQuer.Query_SkeletonSelect());
        ResultSet rs2 = SQLConnect.SQL_Select(SQLStoreQuer.Query_Treats());

int t =0;
        while (rs.next()){
            t++;
          //  System.out.println(rs.getString("ddid"));
        }
    //    while (rs2.next()) {t++;}

        System.out.println(t);

        int ed = t- (10*(t/10));
        int tens = t/10;


        System.out.println(tens+" "+ed);



    }




}
