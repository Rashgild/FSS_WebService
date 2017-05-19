package WS_ClientToFss.XmlFile;

import EntityClasses.*;
import HelpersMethods.Doc;
import HelpersMethods.GlobalVariables;

import javax.xml.soap.SOAPMessage;
import java.util.List;

/**
 * Created by rkurbanov on 20.01.2017.
 */
public class Sign {

    protected static SOAPMessage Signation(PrParseFileLnLpu prParseFileLnLpu, SOAPMessage message) throws Exception {

        List<ROW> rows = UnPack(prParseFileLnLpu);
        org.apache.xml.security.Init.init();
        Doc.SaveSOAPToXML("my.xml",message);

        for (int i=0;i<rows.size();i++){

            System.out.println("Подписываю "+i+" сообщение");
            GlobalVariables.eln = rows.get(i).getLncode();

            System.out.println("Подписываю ROW");
            /**Подпись "ROW" подписью МО*/
            message= WS_ClientToFss.SignAndEncrypt.Sign.SignationByParametrs2("http://eln.fss.ru/actor/mo/"+GlobalVariables.ogrnMo[1]+"/"+rows.get(i).getAttribId(),
                    "#"+rows.get(i).getAttribId(),message);
            Doc.SaveSOAPToXML("my.xml",message);


            ROW.HOSPITAL_BREACH hospital_breach = new ROW.HOSPITAL_BREACH();
            ROW.LN_RESULT ln_result = new ROW.LN_RESULT();
            List<ROW.HOSPITAL_BREACH>hospital_breaches= rows.get(i).getHospitalbreach();
            List<ROW.LN_RESULT>ln_results = rows.get(i).getLnresult();


            ln_result = ln_results.get(0);
            hospital_breach = hospital_breaches.get(0);
            /**Подпись "Результатов"*/

            System.out.println("--------");
            System.out.println(ln_result.getAttribId());


            if(ln_result.getAttribId()!=null){
                message= WS_ClientToFss.SignAndEncrypt.Sign.SignationByParametrs2("http://eln.fss.ru/actor/doc/"+GlobalVariables.eln+"_2_doc",
                        "#"+ln_result.getAttribId(),message);
                Doc.SaveSOAPToXML("my.xml",message);
            }

            /**Подпись "Нарушений"*/
            if(hospital_breach.getAttributeId()!=null){
                message= WS_ClientToFss.SignAndEncrypt.Sign.SignationByParametrs2("http://eln.fss.ru/actor/doc/"+GlobalVariables.eln+"_1_doc",
                        "#"+hospital_breach.getAttributeId(),message);
                Doc.SaveSOAPToXML("my.xml",message);
            }

            TREAT_FULL_PERIOD treat_full_period = new TREAT_FULL_PERIOD();
            List<TREAT_FULL_PERIOD>treat_full_periods = rows.get(i).getTREAT_PERIODS();
            TREAT_PERIOD treat_period = new TREAT_PERIOD();

            /**Подпись ВК*/
            for(int j=0;j<treat_full_periods.size();j++) {
                System.out.println(i+" сообщение "+j+" вк");
                treat_full_period = treat_full_periods.get(j);
                if(treat_full_period.getAttribIdVk()!=null) {
                    message= WS_ClientToFss.SignAndEncrypt.Sign.SignationByParametrs2("http://eln.fss.ru/actor/doc/"+treat_full_period.getAttribIdVk(),
                            "#"+treat_full_period.getAttribIdVk(),message);
                    Doc.SaveSOAPToXML("my.xml",message);
                }

                List<TREAT_PERIOD> treat_periods1 = treat_full_periods.get(j).getTreat_period();
                /**Подпись доктора*/
                for (int k=0;k<treat_periods1.size();k++) {
                    treat_period = treat_periods1.get(k);
                    System.out.println(i+" сообщение, вк"+j+" | "+k+" док");
                    if(treat_period.getAttribId()!=null) {
                        message= WS_ClientToFss.SignAndEncrypt.Sign.SignationByParametrs2("http://eln.fss.ru/actor/doc/"+treat_period.getAttribId(),
                                "#"+treat_period.getAttribId(),message);
                        Doc.SaveSOAPToXML("my.xml",message);
                    }
                }
            }

        }
        return message;
    }

    /** распаковщик объекта **/
    private static List<ROW> UnPack(PrParseFileLnLpu prParseFileLnLpu) {
        List<PrParseFileLnLpu.Reqest> reqests = prParseFileLnLpu.getRequests();
        List<PrParseFileLnLpu.Reqest.pXmlFile> pXmlFiles = reqests.get(0).getpXmlFiles();
        List<ROWSET> rowsets = pXmlFiles.get(0).getRowset();
        List<ROW> rows = rowsets.get(0).getRow();

        return rows;
    }
}
