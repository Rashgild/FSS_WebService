package WS_ClientToFss;

import EntityClasses.*;
import HelpersMethods.Doc;
import HelpersMethods.GlobalVariables;
import HelpersMethods.SQLConnect;
import HelpersMethods.SQLStoreQuer;
import Jaxb.JaxbParser;
import Jaxb.Parser;
import WS_ClientToFss.SignAndEncrypt.Encrypt;
import WS_ClientToFss.SignAndEncrypt.Sign;
import org.w3c.dom.Document;
import ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkurbanov on 13.12.2016.
 */


public class XmlFileLnLpuArray {

    private static Parser parser;
    private static File file;

    private static void setUp() throws Exception {
        parser = new JaxbParser();
        file = new File("person.xml");
    }

    private static void initialization() throws Exception {
        // GlobalVariables.GetConfiguration();
        setUp();
        JCPXMLDSigInit.init();
        //System.setProperty("javax.net.ssl.trustStore",GlobalVariables.PathToSSLcert[1]);//КОНФ
        //System.setProperty("javax.net.ssl.trustStorePassword", "123456");
    }

    public static SOAPMessage Mess() {

        try {
            initialization();
            PrParseFileLnLpu prParseFileLnLpu = CreateFrame();
            GlobalVariables.prparse = prParseFileLnLpu;
            SOAPMessage message = CreateMessage(prParseFileLnLpu);
            message = Signation(prParseFileLnLpu,message);

            //TODO Подписанный запрос храним в переменной
            //if(GlobalVariables.Request.length()<60000)
            GlobalVariables.Request = Doc.SoapMessageToString(message);
            //Doc.SaveSOAPToXML("2.xml",message);
            message = Encryption(message);
            //message.writeTo(System.out);
            return message;
            //
        } catch (Exception e) {
            e.printStackTrace();}

        return null;
    }

    /** Создание XML-фала **/
    private static PrParseFileLnLpu CreateFrame() throws SQLException, JAXBException {

        ResultSet rs = SQLConnect.SQL_Select(SQLStoreQuer.Query_SkeletonSelect());
        ResultSet rs2 = SQLConnect.SQL_Select(SQLStoreQuer.Query_Treats());
        List<ROW> rows = new ArrayList<>();

        while(rs.next())
        {
            String ELN = rs.getString("LN_CODE");
            int per =3;
            int DDID_1 = rs.getInt("DDID");

            List <TREAT_FULL_PERIOD> treat_full_periods = new ArrayList<>();
            while (rs2.next())
            {
                int DDID_2 = rs2.getInt("DDID");
                //System.out.println("1: "+DDID_1+" j: "+DDID_2);
                if(DDID_1==DDID_2)
                {
                    TREAT_PERIOD treat_period = new TREAT_PERIOD();
                    treat_period.setTreatdt1(rs2.getString("TREAT_DT1"));
                    treat_period.setTreatdt2(rs2.getString("TREAT_DT2"));
                    treat_period.setTreatdoctorrole(rs2.getString("TREAT_DOCTOR_ROLE"));
                    treat_period.setTreatdoctor(rs2.getString("TREAT_DOCTOR"));
                    treat_period.setAttribId("ELN_" + ELN + "_" + per + "_doc");
                    List<TREAT_PERIOD> treat_periods =new ArrayList<>();
                    treat_periods.add(treat_period);


                    TREAT_FULL_PERIOD treat_full_period = new TREAT_FULL_PERIOD();
                    treat_full_period.setTreatchairmanrole(rs2.getString("TREAT_CHAIRMAN_ROLE"));
                    treat_full_period.setTreatchairman(rs2.getString("TREAT_CHAIRMAN"));
                    if(treat_full_period.getTreatchairmanrole()!=null) {
                        treat_full_period.setAttribIdVk("ELN_" + ELN + "_" + per + "_vk");
                    }

                    treat_full_period.setTreat_period(treat_periods);
                    treat_full_periods.add(treat_full_period);
                    per++;
                }else{
                    rs2.previous();
                    break;
                }
            }

            ROW.HOSPITAL_BREACH hospital_breach = new ROW.HOSPITAL_BREACH();
            hospital_breach.setHospitalbreachcode(rs.getString("HOSPITAL_BREACH_CODE"));
            hospital_breach.setHospitalbreachdt(rs.getString("HOSPITAL_BREACH_DT"));
            if(hospital_breach.getHospitalbreachcode()!=null) {
                hospital_breach.setAttributeId("ELN_"+ELN+"_1_doc");
            }
            List<ROW.HOSPITAL_BREACH> hospital_breaches = new ArrayList<>();
            hospital_breaches.add(hospital_breach);


            ROW.LN_RESULT ln_result = new ROW.LN_RESULT();
            ln_result.setMseresult(rs.getString("MSE_RESULT"));
            ln_result.setOtherstatedt(rs.getString("other_state_dt"));
            ln_result.setNextlncode(rs.getString("NEXT_LN_CODE"));
            if(ln_result.getMseresult()!=null && !ln_result.getMseresult().equals("")) {
                ln_result.setAttribId("ELN=" + ELN + "_2_doc");
            }
            List<ROW.LN_RESULT>ln_results = new ArrayList<>();
            ln_results.add(ln_result);

            ROW row  = new ROW(); //новый экземпляр row
            //Заполняем

            String str[];
            String snils= rs.getString("SNILS");
            str = snils.split("-");
            snils = str[0]+str[1]+str[2];
            str = snils.split(" ");
            snils = str[0]+str[1];


            row.setIdDD(DDID_1);
            row.setAttribId("ELN_"+ELN);
            row.setSnils(snils);
            row.setSurname(rs.getString("SURNAME"));
            row.setName(rs.getString("NAME"));
            row.setPatronimic(rs.getString("PATRONIMIC"));
            row.setBozflag(rs.getInt("BOZ_FLAG"));
            row.setLpuemployer(rs.getString("LPU_EMPLOYER"));
            row.setLpuemplflag(rs.getInt("LPU_EMPL_FLAG"));
            row.setLncode(rs.getString("LN_CODE"));
            // row.setPrevlncode(rs.getString());
            row.setPrimaryflag(rs.getInt("PRIMARY_FLAG"));
            row.setDuplicateflag(rs.getInt("DUPLICATE_FLAG"));
            row.setLndate(rs.getString("LN_DATE"));
            row.setLpuname(rs.getString("LPU_NAME"));
            row.setLpuaddress(rs.getString("LPU_ADDRESS"));
            row.setLpuogrn(rs.getString("LPU_OGRN"));
            row.setBirthday(rs.getString("BIRTHDAY"));
            row.setGender(rs.getInt("GENDER"));
            row.setReason1(rs.getString("REASON1"));
            row.setReason2(rs.getString("REASON2"));
            row.setReason3(rs.getString("REASON3"));
            row.setDiagnos(rs.getString("DIAGNOS"));
            row.setParentcode(rs.getString("PARENT_CODE"));
            row.setDate1(rs.getString("DATE1"));
            row.setDate2(rs.getString("DATE2"));
            row.setVoucherno(rs.getString("VOUCHER_NO"));
            row.setVoucherogrn(rs.getString("VOUCHER_OGRN"));
            row.setServ1AGE(rs.getString("SERV1_AGE"));
            row.setServ1RELATIONCODE(rs.getString("SERV1_RELATION_CODE"));
            row.setServ1FIO(rs.getString("SERV1_FIO"));
            row.setServ2AGE(rs.getString("SERV2_AGE"));
            row.setServ2RELATIONCODE(rs.getString("SERV2_RELATION_CODE"));
            row.setServ2FIO(rs.getString("SERV2_FIO"));
            row.setPregn12WFLAG(rs.getString("PREGN12W_FLAG"));
            row.setHospitaldt1(rs.getString("HOSPITAL_DT1"));
            row.setHospitaldt2(rs.getString("HOSPITAL_DT2"));
            //row.setclosereason(rs.getString(""));
            row.setMsedt1(rs.getString("MSE_DT1"));
            row.setMsedt2(rs.getString("MSE_DT2"));
            row.setMsedt3(rs.getString("MSE_DT3"));
            row.setLnstate(rs.getString("LN_STATE"));

          //  row.setLnresult(ln_results);
            row.setHospitalbreach(hospital_breaches);
            row.setTREAT_PERIODS(treat_full_periods);

            rows.add(row);
        }

        ROWSET rowset = new ROWSET();
        rowset.setAuthor("R.Kurbanov");
        rowset.setEmail("Rashgild@gmail.com");
        rowset.setPhone("89608634440");
        rowset.setSoftware("SignAndcypt");
        rowset.setVersion("1.0");
        rowset.setVersionSoftware("2.0");
        rowset.setRow(rows);
        List<ROWSET>rowsets = new ArrayList<>();
        rowsets.add(rowset);

        PrParseFileLnLpu.Reqest.pXmlFile pXmlFile = new PrParseFileLnLpu.Reqest.pXmlFile();
        pXmlFile.setRowset(rowsets);
        List<PrParseFileLnLpu.Reqest.pXmlFile>pXmlFiles= new ArrayList<>();
        pXmlFiles.add(pXmlFile);

        PrParseFileLnLpu.Reqest request = new PrParseFileLnLpu.Reqest();
        request.setOgrn(GlobalVariables.ogrnMo[1]);
        request.setpXmlFiles(pXmlFiles);
        List<PrParseFileLnLpu.Reqest>reqests = new ArrayList<>();
        reqests.add(request);

        PrParseFileLnLpu prParseFilelnlpu = new PrParseFileLnLpu();
        prParseFilelnlpu.setFil("http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl");
        prParseFilelnlpu.setRequests(reqests);
        List<PrParseFileLnLpu>prParseFileLnLpus = new ArrayList<>();
        prParseFileLnLpus.add(prParseFilelnlpu);

        //parser.saveObject(file,prParseFilelnlpu);
        return prParseFilelnlpu;
    }
    /** распаковщик объекта **/
    public static List<ROW> UnPack(PrParseFileLnLpu prParseFileLnLpu) {
        List<PrParseFileLnLpu.Reqest> reqests = prParseFileLnLpu.getRequests();
        List<PrParseFileLnLpu.Reqest.pXmlFile> pXmlFiles = reqests.get(0).getpXmlFiles();
        List<ROWSET> rowsets = pXmlFiles.get(0).getRowset();
        List<ROW> rows = rowsets.get(0).getRow();

        return rows;
    }
    /** Создание SOAP-сообщения из xml **/
    private static SOAPMessage CreateMessage(PrParseFileLnLpu prParseFileLnLpu) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        SOAPMessage message = null;
        try {
            db = dbf.newDocumentBuilder();

            Document document = db.newDocument();
            document= parser.ObjToSoap(prParseFileLnLpu);
            MessageFactory mf = MessageFactory.newInstance();
            message  = mf.createMessage();
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope soapEnv = message.getSOAPPart().getEnvelope();
            SOAPHeader soapHeader = soapEnv.getHeader();
            SOAPBody soapBody = soapEnv.getBody();
            soapBody.addDocument(document);

            soapEnv.addNamespaceDeclaration("ds","http://www.w3.org/2000/09/xmldsig#");
            soapEnv.addNamespaceDeclaration("wsse","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
            soapEnv.addNamespaceDeclaration("wsu","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
            soapEnv.addNamespaceDeclaration("xsd","http://www.w3.org/2001/XMLSchema");
            soapEnv.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");
            soapEnv.addNamespaceDeclaration("fil","http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl");


            return message;
        } catch (ParserConfigurationException | TransformerException | IOException | JAXBException | SOAPException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
    /** Подпись сообщения **/
    private static SOAPMessage Signation(PrParseFileLnLpu prParseFileLnLpu, SOAPMessage message) throws Exception {
        List<ROW> rows = UnPack(prParseFileLnLpu);
        org.apache.xml.security.Init.init();
        Doc.SaveSOAPToXML("my.xml",message);

        for (int i=0;i<rows.size();i++){

            GlobalVariables.eln = rows.get(i).getLncode();
            message= Sign.SignationByParametrs2("http://eln.fss.ru/actor/mo/"+GlobalVariables.ogrnMo[1]+"/"+rows.get(i).getAttribId(),
                    "#"+rows.get(i).getAttribId(),message);
            Doc.SaveSOAPToXML("my.xml",message);


            ROW.HOSPITAL_BREACH hospital_breach = new ROW.HOSPITAL_BREACH();
            ROW.LN_RESULT ln_result = new ROW.LN_RESULT();
            List<ROW.HOSPITAL_BREACH>hospital_breaches= rows.get(i).getHospitalbreach();
            List<ROW.LN_RESULT>ln_results = rows.get(i).getLnresult();
            //Подпись "Результатов"
            if(ln_result.getAttribId()!=null)
            {
                message= Sign.SignationByParametrs2("http://eln.fss.ru/actor/mo/"+GlobalVariables.ogrnMo[1]+"/"+ln_result.getAttribId()+"/",
                        "#"+ln_result.getAttribId(),message);
                Doc.SaveSOAPToXML("my.xml",message);
            }

            //Подпись "Нарушений"
            if(hospital_breach.getAttributeId()!=null)
            {
                message= Sign.SignationByParametrs2("http://eln.fss.ru/actor/mo/"+GlobalVariables.ogrnMo[1]+"/"+hospital_breach.getAttributeId()+"/",
                        "#"+hospital_breach.getAttributeId(),message);
                Doc.SaveSOAPToXML("my.xml",message);
            }

            TREAT_FULL_PERIOD treat_full_period = new TREAT_FULL_PERIOD();
            List<TREAT_FULL_PERIOD>treat_full_periods = rows.get(i).getTREAT_PERIODS();
            TREAT_PERIOD treat_period = new TREAT_PERIOD();

            //Подпись ВК
            for(int j=0;j<treat_full_periods.size();j++) {

                treat_full_period = treat_full_periods.get(j);
                if(treat_full_period.getAttribIdVk()!=null) {
                    message= Sign.SignationByParametrs2("http://eln.fss.ru/actor/mo/"+GlobalVariables.ogrnMo[1]+"/"+treat_full_period.getAttribIdVk()+"/",
                            "#"+treat_full_period.getAttribIdVk(),message);
                    Doc.SaveSOAPToXML("my.xml",message);
                }

                List<TREAT_PERIOD> treat_periods1 = treat_full_periods.get(j).getTreat_period();
                //Подпись доктора
                for (int k=0;k<treat_periods1.size();k++) {
                    treat_period = treat_periods1.get(k);
                    if(treat_period.getAttribId()!=null) {
                        message= Sign.SignationByParametrs2("http://eln.fss.ru/actor/mo/"+GlobalVariables.ogrnMo[1]+"/"+treat_period.getAttribId()+"/",
                                "#"+treat_period.getAttribId(),message);
                        Doc.SaveSOAPToXML("my.xml",message);
                    }
                }
            }

        }

        //message.writeTo(System.out);
        return message;
    }
    /** Шифрование сообщения **/
    private static SOAPMessage Encryption(SOAPMessage message) throws Exception {

        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage NewMessg = mf.createMessage();
        NewMessg= Encrypt.CreateXMLAndEncrypt(NewMessg, "my.xml");
        Doc.SaveSOAPToXML("LNCrypted.xml",NewMessg);
        message = NewMessg;
        return message;
    }
}
