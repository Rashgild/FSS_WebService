package WS_ClientToFss;

import HelpersMethods.Doc;
import HelpersMethods.GlobalVariables;
import HelpersMethods.SQLConnect;
import HelpersMethods.SQLStoreQuer;
import WS_ClientToFss.SignAndEncrypt.Encrypt;
import WS_ClientToFss.SignAndEncrypt.Sign;
import org.apache.ws.security.WSSecurityException;
import org.apache.xml.security.transforms.TransformationException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.soap.*;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class XmlFileLnLpu {

    private static int fdoc=0,fdoc2=0,fdoc3=0; // флаги врачей
    private static int fvk=0,fvk2=0,fvk3=0; // флаги ВК
    private static int fresult=0,fBREACH=0; //флаг блоков result и breach

    public static SOAPMessage StartSetxmlFileLn()
    {
        try {
            SOAPMessage soapMessage = CreateMessage();
            Doc.SaveSOAPToXML("LNSkeleton.xml",soapMessage);
            soapMessage = SignationMessage(soapMessage);
            Doc.SaveSOAPToXML("LNSigned.xml",soapMessage);
            Doc.SaveSOAPToXML("1.xml",soapMessage);

            //TODO Подписанный запрос храним в переменной
            GlobalVariables.Request = Doc.SoapMessageToString(soapMessage);

            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage NewMessg = mf.createMessage();
            NewMessg= Encrypt.CreateXMLAndEncrypt(NewMessg, "LNSigned.xml");


            Doc.SaveSOAPToXML("LNCrypted.xml",NewMessg);

            /*soapMessage.writeTo(System.out);
            System.out.println(GlobalVariables.DisabilityDocument_id);
            soapMessage = EncryptDecrypt.CreateXMLAndEncrypt(soapMessage);*/

            return NewMessg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        SOAPMessage soapMessage = null;
        return soapMessage;
    }

    public static SOAPMessage CreateMessage() throws SOAPException {

        ResultSet ResultSQLRequest = SQLConnect.SQL_Select(SQLStoreQuer.SQL_Req());
        ResultSet ResultSQLRequest2 = SQLConnect.SQL_Select(SQLStoreQuer.SelectLNN());

        ArrayList<String> treat_doc = treat_doc();
        ArrayList<String> treat_vk = treat_vk();
        try {
            while (ResultSQLRequest2.next()) {
                GlobalVariables.eln = ResultSQLRequest2.getString("number"); //"126877042912";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //region CreateSkeleton
        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage soapMessage = mf.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope soapEnv = soapMessage.getSOAPPart().getEnvelope();
        SOAPHeader soapHeader = soapEnv.getHeader();
        SOAPBody soapBody = soapEnv.getBody();

        /**
         * soapBody.addAttribute(name, "OGRN_"+ ClientMain.ogrn);
         */
        //soapBody.addAttribute(soapEnv.createName("Id"), "OGRN_"+GlobalVariables.ogrnMo[1]);

        soapEnv.addNamespaceDeclaration("ds","http://www.w3.org/2000/09/xmldsig#");
        soapEnv.addNamespaceDeclaration("wsse","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        soapEnv.addNamespaceDeclaration("wsu","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        soapEnv.addNamespaceDeclaration("xsd","http://www.w3.org/2001/XMLSchema");
        soapEnv.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");
        soapEnv.addNamespaceDeclaration("fil","http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl");
        SOAPElement prParseFilelnlpu = soapBody.addChildElement("fil:prParseFilelnlpu");
        prParseFilelnlpu.addNamespaceDeclaration("","http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl");
        prParseFilelnlpu.addNamespaceDeclaration("ds","http://www.w3.org/2000/09/xmldsig#");
        prParseFilelnlpu.addNamespaceDeclaration("SOAP-ENV","http://schemas.xmlsoap.org/soap/envelope/");
        prParseFilelnlpu.addNamespaceDeclaration("wsse","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        prParseFilelnlpu.addNamespaceDeclaration("wsu","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        prParseFilelnlpu.addNamespaceDeclaration("xml","http://www.w3.org/XML/1998/namespace");
        prParseFilelnlpu.addNamespaceDeclaration("xsd","http://www.w3.org/2001/XMLSchema");
        prParseFilelnlpu.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");

        SOAPElement request = prParseFilelnlpu.addChildElement("request");
        SOAPElement ogrn = request.addChildElement("ogrn");
        ogrn.setTextContent(GlobalVariables.ogrnMo[1]);

        SOAPElement pXmlFile = request.addChildElement("pXmlFile");
        SOAPElement ROWSET = pXmlFile.addChildElement("ROWSET");

        ROWSET.addNamespaceDeclaration("ns1","http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl");

        ROWSET.addAttribute(soapEnv.createName("ns1:version"), "1.0");
        ROWSET.addAttribute(soapEnv.createName("ns1:software"), "SignAndCrypt");
        ROWSET.addAttribute(soapEnv.createName("ns1:version_software"), "0.5");
        ROWSET.addAttribute(soapEnv.createName("ns1:author"), "Kurbanov R.B.");
        ROWSET.addAttribute(soapEnv.createName("ns1:phone"), "89608634440");
        ROWSET.addAttribute(soapEnv.createName("ns1:email"), "rashgild@gmail.com");

        SOAPElement ROW = ROWSET.addChildElement("ROW");

        //ROW.addNamespaceDeclaration("wsu","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        //ROW.addAttribute(soapEnv.createName("wsu:Id"), "ELN_"+GlobalVariables.eln); //**--**//
        ROW.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id", "ELN_"+GlobalVariables.eln);
        /*message.getSOAPPart().getEnvelope().addNamespaceDeclaration("wsse","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        message.getSOAPPart().getEnvelope().addNamespaceDeclaration("wsu","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        message.getSOAPPart().getEnvelope().addNamespaceDeclaration("ds","http://www.w3.org/2000/09/xmldsig#");
        message.getSOAPBody().setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id", "body");
        message.getSOAPBody().setIdAttribute("wsu:Id", true);*/

        SOAPElement SNILS = ROW.addChildElement("SNILS");
        SOAPElement SURNAME = ROW.addChildElement("SURNAME");
        SOAPElement NAME = ROW.addChildElement("NAME");
        SOAPElement PATRONIMIC = ROW.addChildElement("PATRONIMIC");//-
        SOAPElement BOZ_FLAG = ROW.addChildElement("BOZ_FLAG");
        SOAPElement LPU_EMPLOYER = ROW.addChildElement("LPU_EMPLOYER");//-
        SOAPElement LPU_EMPL_FLAG = ROW.addChildElement("LPU_EMPL_FLAG");//-
        SOAPElement LN_CODE = ROW.addChildElement("LN_CODE");
        SOAPElement PREV_LN_CODE = ROW.addChildElement("PREV_LN_CODE");//-
        SOAPElement PRIMARY_FLAG = ROW.addChildElement("PRIMARY_FLAG");
        SOAPElement DUPLICATE_FLAG = ROW.addChildElement("DUPLICATE_FLAG");
        SOAPElement LN_DATE = ROW.addChildElement("LN_DATE");
        SOAPElement LPU_NAME = ROW.addChildElement("LPU_NAME");
        SOAPElement LPU_ADDRESS = ROW.addChildElement("LPU_ADDRESS");//-
        SOAPElement LPU_OGRN = ROW.addChildElement("LPU_OGRN");
        SOAPElement BIRTHDAY = ROW.addChildElement("BIRTHDAY");
        SOAPElement GENDER = ROW.addChildElement("GENDER");
        SOAPElement REASON1 = ROW.addChildElement("REASON1");
        SOAPElement REASON2 = ROW.addChildElement("REASON2");
        SOAPElement REASON3 = ROW.addChildElement("REASON3");//-
        SOAPElement DIAGNOS = ROW.addChildElement("DIAGNOS");
        SOAPElement PARENT_CODE = ROW.addChildElement("PARENT_CODE");
        SOAPElement DATE1 = ROW.addChildElement("DATE1");
        SOAPElement DATE2 = ROW.addChildElement("DATE2");
        SOAPElement VOUCHER_NO = ROW.addChildElement("VOUCHER_NO");
        SOAPElement VOUCHER_OGRN = ROW.addChildElement("VOUCHER_OGRN");
        SOAPElement SERV1_AGE = ROW.addChildElement("SERV1_AGE");
        SOAPElement SERV1_MM = ROW.addChildElement("SERV1_MM");
        SOAPElement SERV1_RELATION_CODE = ROW.addChildElement("SERV1_RELATION_CODE");
        SOAPElement SERV1_FIO = ROW.addChildElement("SERV1_FIO");
        SOAPElement SERV1_DT1 = ROW.addChildElement("SERV1_DT1");
        SOAPElement SERV1_DT2 = ROW.addChildElement("SERV17_DT2");
        SOAPElement SERV2_AGE = ROW.addChildElement("SERV2_AGE");
        SOAPElement SERV2_MM = ROW.addChildElement("SERV2_MM");
        SOAPElement SERV2_RELATION_CODE = ROW.addChildElement("SERV2_RELATION_CODE");
        SOAPElement SERV2_FIO = ROW.addChildElement("SERV2_FIO");
        SOAPElement SERV2_DT1 = ROW.addChildElement("SERV2_DT1");
        SOAPElement SERV2_DT2 = ROW.addChildElement("SERV2_DT2");
        SOAPElement PREGN12W_FLAG = ROW.addChildElement("PREGN12W_FLAG");
        SOAPElement HOSPITAL_DT1 = ROW.addChildElement("HOSPITAL_DT1");
        SOAPElement HOSPITAL_DT2 = ROW.addChildElement("HOSPITAL_DT2");
        SOAPElement HOSPITAL_BREACH = ROW.addChildElement("HOSPITAL_BREACH");

        SOAPElement HOSPITAL_BREACH_CODE = HOSPITAL_BREACH.addChildElement("HOSPITAL_BREACH_CODE");
        SOAPElement HOSPITAL_BREACH_DT = HOSPITAL_BREACH.addChildElement("HOSPITAL_BREACH_DT");
        SOAPElement MSE_DT1 = ROW.addChildElement("MSE_DT1");
        SOAPElement MSE_DT2 = ROW.addChildElement("MSE_DT2");
        SOAPElement MSE_DT3 = ROW.addChildElement("MSE_DT3");
        SOAPElement MSE_INVALID_GROUP = ROW.addChildElement("MSE_INVALID_GROUP");
        SOAPElement LN_RESULT = ROW.addChildElement("LN_RESULT");

        SOAPElement MSE_RESULT = LN_RESULT.addChildElement("MSE_RESULT");
        SOAPElement OTHER_STATE_DT = LN_RESULT.addChildElement("OTHER_STATE_DT");
        SOAPElement RETURN_DATE_LPU = LN_RESULT.addChildElement("RETURN_DATE_LPU");
        SOAPElement NEXT_LN_CODE = LN_RESULT.addChildElement("NEXT_LN_CODE");
        SOAPElement LN_STATE = ROW.addChildElement("LN_STATE");
        SOAPElement LN_HASH = ROW.addChildElement("LN_HASH");
        SOAPElement TREAT_PERIODS = ROW.addChildElement("TREAT_PERIODS");

        SOAPElement TREAT_FULL_PERIOD = TREAT_PERIODS.addChildElement("TREAT_FULL_PERIOD");
        SOAPElement TREAT_CHAIRMAN_ROLE = TREAT_FULL_PERIOD.addChildElement("TREAT_CHAIRMAN_ROLE");
        SOAPElement TREAT_CHAIRMAN = TREAT_FULL_PERIOD.addChildElement("TREAT_CHAIRMAN");

        SOAPElement TREAT_PERIOD = TREAT_FULL_PERIOD.addChildElement("TREAT_PERIOD");
        SOAPElement TREAT_DT1 = TREAT_PERIOD.addChildElement("TREAT_DT1");
        SOAPElement TREAT_DT2 = TREAT_PERIOD.addChildElement("TREAT_DT2");
        SOAPElement TREAT_DOCTOR_ROLE = TREAT_PERIOD.addChildElement("TREAT_DOCTOR_ROLE");
        SOAPElement TREAT_DOCTOR = TREAT_PERIOD.addChildElement("TREAT_DOCTOR");

        SOAPElement TREAT_FULL_PERIOD2 = TREAT_PERIODS.addChildElement("TREAT_FULL_PERIOD");
        SOAPElement TREAT_CHAIRMAN_ROLE2 = TREAT_FULL_PERIOD2.addChildElement("TREAT_CHAIRMAN_ROLE");
        SOAPElement TREAT_CHAIRMAN2 = TREAT_FULL_PERIOD2.addChildElement("TREAT_CHAIRMAN");

        SOAPElement TREAT_PERIOD_2 = TREAT_FULL_PERIOD2.addChildElement("TREAT_PERIOD");
        SOAPElement TREAT_DT1_2 = TREAT_PERIOD_2.addChildElement("TREAT_DT1");
        SOAPElement TREAT_DT2_2 = TREAT_PERIOD_2.addChildElement("TREAT_DT2");
        SOAPElement TREAT_DOCTOR_ROLE_2 = TREAT_PERIOD_2.addChildElement("TREAT_DOCTOR_ROLE");
        SOAPElement TREAT_DOCTOR_2 = TREAT_PERIOD_2.addChildElement("TREAT_DOCTOR");

        SOAPElement TREAT_FULL_PERIOD3 = TREAT_PERIODS.addChildElement("TREAT_FULL_PERIOD");
        SOAPElement TREAT_CHAIRMAN_ROLE3 = TREAT_FULL_PERIOD3.addChildElement("TREAT_CHAIRMAN_ROLE");
        SOAPElement TREAT_CHAIRMAN3 = TREAT_FULL_PERIOD3.addChildElement("TREAT_CHAIRMAN");
        SOAPElement TREAT_PERIOD_3 = TREAT_FULL_PERIOD3.addChildElement("TREAT_PERIOD");
        SOAPElement TREAT_DT1_3 = TREAT_PERIOD_3.addChildElement("TREAT_DT1");
        SOAPElement TREAT_DT2_3 = TREAT_PERIOD_3.addChildElement("TREAT_DT2");
        SOAPElement TREAT_DOCTOR_ROLE_3 = TREAT_PERIOD_3.addChildElement("TREAT_DOCTOR_ROLE");
        SOAPElement TREAT_DOCTOR_3 = TREAT_PERIOD_3.addChildElement("TREAT_DOCTOR");

        try {
            while(ResultSQLRequest.next())
            {
                String str[];
                String snils= ResultSQLRequest.getString("snils");
                str = snils.split("-");
                snils = str[0]+str[1]+str[2];
                str = snils.split(" ");
                snils = str[0]+str[1];

                SNILS.setTextContent(snils);
                SURNAME.setTextContent(ResultSQLRequest.getString("SURNAME"));
                NAME.setTextContent(ResultSQLRequest.getString("name"));
                VerifyNode(ResultSQLRequest,"PATRONIMIC",ROW,PATRONIMIC);
                BOZ_FLAG.setTextContent(ResultSQLRequest.getString("BOZ_FLAG"));
                VerifyNode(ResultSQLRequest,"LPU_EMPLOYER",ROW,LPU_EMPLOYER);
                VerifyNode(ResultSQLRequest,"LPU_EMPL_FLAG",ROW,LPU_EMPL_FLAG);
                LN_CODE.setTextContent(ResultSQLRequest.getString("LN_CODE"));

                VerifyNode(ResultSQLRequest,"prevDocument",ROW,PREV_LN_CODE);
                PRIMARY_FLAG.setTextContent(ResultSQLRequest.getString("PRIMARY_FLAG"));
                DUPLICATE_FLAG.setTextContent(ResultSQLRequest.getString("DUPLICATE_FLAG"));
                LN_DATE.setTextContent(ResultSQLRequest.getString("LN_DATE"));
                LPU_NAME.setTextContent(ResultSQLRequest.getString("LPU_NAME"));
                VerifyNode(ResultSQLRequest,"LPU_ADDRESS",ROW,LPU_ADDRESS);
                LPU_OGRN.setTextContent(ResultSQLRequest.getString("LPU_OGRN"));
                BIRTHDAY.setTextContent(ResultSQLRequest.getString("BIRTHDAY"));
                GENDER.setTextContent(ResultSQLRequest.getString("GENDER"));
                REASON1.setTextContent(ResultSQLRequest.getString("REASON1"));
                VerifyNode(ResultSQLRequest,"REASON2",ROW,REASON2);
                VerifyNode(ResultSQLRequest,"REASON3",ROW,REASON3);
                VerifyNode(ResultSQLRequest,"DIAGNOS",ROW,DIAGNOS);
                VerifyNode(ResultSQLRequest,"PARENT_CODE",ROW,PARENT_CODE);//-  Номер ЛН, предъявляемого на основном месте работы
                VerifyNode(ResultSQLRequest,"DATE1",ROW,DATE1);
                VerifyNode(ResultSQLRequest,"DATE2",ROW,DATE2);
                VerifyNode(ResultSQLRequest,"VOUCHER_NO",ROW,VOUCHER_NO);
                VerifyNode(ResultSQLRequest,"VOUCHER_OGRN",ROW,VOUCHER_OGRN);

                if(NodeIsEmpty(ResultSQLRequest,"SERV1_AGE"))
                {
                    ROW.removeChild(SERV1_AGE);
                    ROW.removeChild(SERV1_MM);
                }else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(ResultSQLRequest.getDate("SERV1_AGE"));
                    SERV1_AGE.setTextContent(HowYearsNow(calendar));
                    SERV1_MM.setTextContent(HowMonthNow(calendar));

                }

                if(NodeIsEmpty(ResultSQLRequest,"SERV2_AGE"))
                {
                    ROW.removeChild(SERV2_AGE);
                    ROW.removeChild(SERV2_MM);
                }else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(ResultSQLRequest.getDate("SERV2_AGE"));
                    SERV2_AGE.setTextContent(HowYearsNow(calendar));
                    SERV2_MM.setTextContent(HowMonthNow(calendar));//По уходу за 2 родственником: Возраст (мес.)
                }

                VerifyNode(ResultSQLRequest,"SERV1_RELATION_CODE",ROW,SERV1_RELATION_CODE);
                VerifyNode(ResultSQLRequest,"SERV1_FIO",ROW,SERV1_FIO);
                ROW.removeChild(SERV1_DT1); //SERV1_DT1.setTextContent(ResultSQLRequest.getDate(""));//- Дата начала ухода за первым родственником
                ROW.removeChild(SERV1_DT2); //SERV1_DT2.setTextContent(ResultSQLRequest.getInt(""));//- Дата окончания ухода за первым родственнико
                VerifyNode(ResultSQLRequest,"SERV2_RELATION_CODE",ROW,SERV2_RELATION_CODE);
                VerifyNode(ResultSQLRequest,"SERV2_FIO",ROW,SERV2_FIO);
                ROW.removeChild(SERV2_DT1);//SERV2_DT1.setTextContent(ResultSQLRequest.getDate(""));//-
                ROW.removeChild(SERV2_DT2); //SERV2_DT2.setTextContent(ResultSQLRequest.getInt(""));//-
                VerifyNode(ResultSQLRequest,"PREGN12W_FLAG",ROW,PREGN12W_FLAG);
                VerifyNode(ResultSQLRequest,"HOSPITAL_DT1",ROW,HOSPITAL_DT1);
                VerifyNode(ResultSQLRequest,"HOSPITAL_DT2",ROW,HOSPITAL_DT2);


                if(NodeIsEmpty(ResultSQLRequest,"MSE_RESULT")
                        && NodeIsEmpty(ResultSQLRequest,"OTHER_STATE_DT")
                        && NodeIsEmpty(ResultSQLRequest,"NEXT_LN_CODE"))
                {
                    ROW.removeChild(LN_RESULT);
                }else {

                    LN_RESULT.removeChild(RETURN_DATE_LPU);
                    VerifyNode(ResultSQLRequest,"MSE_RESULT", LN_RESULT,MSE_RESULT);
                    VerifyNode(ResultSQLRequest,"OTHER_STATE_DT", LN_RESULT,OTHER_STATE_DT);
                    VerifyNode(ResultSQLRequest,"NEXT_LN_CODE", LN_RESULT,NEXT_LN_CODE);
                    // LN_RESULT.addAttribute(soapEnv.createName("wsu:Id"), "ELN_"+GlobalVariables.eln+"_2_doc");
                    LN_RESULT.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id",
                            "ELN_"+GlobalVariables.eln+"_2_doc");

                    fresult=1;
                }


                VerifyNode(ResultSQLRequest,"LN_STATE", LN_RESULT,LN_STATE);
                VerifyNode(ResultSQLRequest,"mse_dt1", ROW,MSE_DT1);
                VerifyNode(ResultSQLRequest,"mse_dt2", ROW,MSE_DT2);
                VerifyNode(ResultSQLRequest,"mse_dt3", ROW,MSE_DT3);
                VerifyNode(ResultSQLRequest,"code", ROW,MSE_INVALID_GROUP);

                ROW.removeChild(LN_HASH);

                int i=1;
                if(NodeIsEmpty(ResultSQLRequest,"HOSPITAL_BREACH_CODE")
                        && NodeIsEmpty(ResultSQLRequest,"HOSPITAL_BREACH_DT"))
                {
                    ROW.removeChild(HOSPITAL_BREACH);

                }else {
                    HOSPITAL_BREACH_CODE.setTextContent(ResultSQLRequest.getString("HOSPITAL_BREACH_CODE"));
                    HOSPITAL_BREACH_DT.setTextContent(ResultSQLRequest.getString("HOSPITAL_BREACH_DT"));
                    //HOSPITAL_BREACH.addAttribute(soapEnv.createName("wsu:Id"), "ELN_"+GlobalVariables.eln+"_1_doc");
                    HOSPITAL_BREACH.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id",
                            "ELN_"+GlobalVariables.eln+"_1_doc");
                    fBREACH=1;
                }


             /*   ResultSQLRequest.close();
                ResultSQLRequest.deleteRow();
                ResultSQLRequest2.deleteRow();
                ResultSQLRequest.close();
                ResultSQLRequest.beforeFirst();
                ResultSQLRequest2.beforeFirst();*/


               /* ResultSQLRequest = null;
                ResultSQLRequest2 = null;*/
            }

        }catch (Exception ex){}


        int treatsize = treat_doc.size();

        if(treatsize==4)
        {
            TREAT_DT1.setTextContent(treat_doc.get(0));
            TREAT_DT2.setTextContent(treat_doc.get(1));
            TREAT_DOCTOR.setTextContent(treat_doc.get(2));
            TREAT_DOCTOR_ROLE.setTextContent(treat_doc.get(3));


            Vk_check(treat_vk,0,TREAT_CHAIRMAN_ROLE,TREAT_CHAIRMAN,TREAT_FULL_PERIOD,soapEnv,3);

            TREAT_PERIODS.removeChild(TREAT_FULL_PERIOD2);
            TREAT_PERIODS.removeChild(TREAT_FULL_PERIOD3);
            // TREAT_PERIOD.addAttribute(soapEnv.createName("wsu:Id"), "ELN_"+GlobalVariables.eln+"_3_doc");.
            TREAT_PERIOD.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id",
                    "ELN_"+GlobalVariables.eln+"_3_doc");
            fdoc=1;
        }

        if(treatsize==8)
        {
            TREAT_DT1_2.setTextContent(treat_doc.get(0));
            TREAT_DT2_2.setTextContent(treat_doc.get(1));
            TREAT_DOCTOR_2.setTextContent(treat_doc.get(2));
            TREAT_DOCTOR_ROLE_2.setTextContent(treat_doc.get(3));

            TREAT_DT1.setTextContent(treat_doc.get(4));
            TREAT_DT2.setTextContent(treat_doc.get(5));
            TREAT_DOCTOR.setTextContent(treat_doc.get(6));
            TREAT_DOCTOR_ROLE.setTextContent(treat_doc.get(7));

            Vk_check(treat_vk,0,TREAT_CHAIRMAN_ROLE2,TREAT_CHAIRMAN2,TREAT_FULL_PERIOD2,soapEnv,4);
            Vk_check(treat_vk,2,TREAT_CHAIRMAN_ROLE,TREAT_CHAIRMAN,TREAT_FULL_PERIOD,soapEnv,3);

            TREAT_PERIODS.removeChild(TREAT_FULL_PERIOD3);
            TREAT_PERIOD.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id",
                    "ELN_"+GlobalVariables.eln+"_3_doc");
            TREAT_PERIOD_2.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id",
                    "ELN_"+GlobalVariables.eln+"_4_doc");
            // TREAT_PERIOD.addAttribute(soapEnv.createName("wsu:Id"), "ELN_"+GlobalVariables.eln+"_3_doc");
            //TREAT_PERIOD_2.addAttribute(soapEnv.createName("wsu:Id"), "ELN_"+GlobalVariables.eln+"_4_doc");
            fdoc=1;
            fdoc2=1;
        }
        if(treatsize==12)
        {
            TREAT_DT1_3.setTextContent(treat_doc.get(0));
            TREAT_DT2_3.setTextContent(treat_doc.get(1));
            TREAT_DOCTOR_3.setTextContent(treat_doc.get(2));
            TREAT_DOCTOR_ROLE_3.setTextContent(treat_doc.get(3));

            TREAT_DT1_2.setTextContent(treat_doc.get(4));
            TREAT_DT2_2.setTextContent(treat_doc.get(5));
            TREAT_DOCTOR_2.setTextContent(treat_doc.get(6));
            TREAT_DOCTOR_ROLE_2.setTextContent(treat_doc.get(7));

            TREAT_DT1.setTextContent(treat_doc.get(8));
            TREAT_DT2.setTextContent(treat_doc.get(9));
            TREAT_DOCTOR.setTextContent(treat_doc.get(10));
            TREAT_DOCTOR_ROLE.setTextContent(treat_doc.get(11));

            Vk_check(treat_vk,0,TREAT_CHAIRMAN_ROLE3,TREAT_CHAIRMAN3,TREAT_FULL_PERIOD3,soapEnv,5);
            Vk_check(treat_vk,2,TREAT_CHAIRMAN_ROLE2,TREAT_CHAIRMAN2,TREAT_FULL_PERIOD2,soapEnv,4);
            Vk_check(treat_vk,4,TREAT_CHAIRMAN_ROLE,TREAT_CHAIRMAN,TREAT_FULL_PERIOD,soapEnv,3);

            /*TREAT_PERIOD.addAttribute(soapEnv.createName("wsu:Id"), "ELN_"+GlobalVariables.eln+"_3_doc");
            TREAT_PERIOD_2.addAttribute(soapEnv.createName("wsu:Id"), "ELN_"+GlobalVariables.eln+"_4_doc");
            TREAT_PERIOD_3.addAttribute(soapEnv.createName("wsu:Id"), "ELN_"+GlobalVariables.eln+"_5_doc");*/
            TREAT_PERIOD.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id",
                    "ELN_"+GlobalVariables.eln+"_3_doc");
            TREAT_PERIOD_2.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id",
                    "ELN_"+GlobalVariables.eln+"_3_doc");
            TREAT_PERIOD_3.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id",
                    "ELN_"+GlobalVariables.eln+"_5_doc");
            fdoc=1;
            fdoc2=1;
            fdoc3=1;
        }

        //out(fdoc+":"+fdoc2+":"+fdoc3);
        //out(fvk+":"+fvk2+":"+fvk3);
        // endregion


       /* try {
            GlobalVariables.SaveToFile("jastforTest", soapMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return soapMessage;
    }

    private static void Vk_check(ArrayList<String> treat_vk, int num,
                                 SOAPElement TREAT_CHAIRMAN_ROLE, SOAPElement TREAT_CHAIRMAN,
                                 SOAPElement TREAT_FULL_PERIOD, SOAPEnvelope soapEnv, int number_line)
            throws SOAPException
    {
        if(treat_vk.get(num) != null) {
            TREAT_CHAIRMAN_ROLE.setTextContent(treat_vk.get(num));
            TREAT_CHAIRMAN.setTextContent(treat_vk.get(num+1));
            //TREAT_FULL_PERIOD.addAttribute(soapEnv.createName("wsu:Id"), "ELN_"+GlobalVariables.eln+"_"+number_line+"_vk");

            TREAT_FULL_PERIOD.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd","wsu:Id",
                    "ELN_"+GlobalVariables.eln+"_"+number_line+"_vk");
            if(num==0) fvk=1;
            if(num==2) {
                if(treat_vk.get(0)!= null) fvk2=1;
                if(treat_vk.get(2)!= null) fvk=1;
            }
            if(num==4) {
                if(treat_vk.get(4)!= null) fvk=1;
                if(treat_vk.get(2)!= null) fvk2=1;
                if(treat_vk.get(0)!= null) fvk3=1;
            }
        }
        else
        {
            TREAT_FULL_PERIOD.removeChild(TREAT_CHAIRMAN_ROLE);
            TREAT_FULL_PERIOD.removeChild(TREAT_CHAIRMAN);
        }
    }

    private static ArrayList<String> treat_doc()
    {
        ArrayList<String> treat = new ArrayList<String>();
        ResultSet ResultSQLRequest = SQLConnect.SQL_Select(SQLStoreQuer.SQL_Req());

        try {
            while (ResultSQLRequest.next()) {
                treat.add(ResultSQLRequest.getString("tread_dt1"));
                treat.add(ResultSQLRequest.getString("tread_dt2"));
                treat.add(ResultSQLRequest.getString("treat_doctor"));
                treat.add(ResultSQLRequest.getString("tread_doctor_role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treat;
    }

    private static ArrayList<String> treat_vk()
    {
        ArrayList<String> treat = new ArrayList<String>();
        ResultSet ResultSQLRequest = SQLConnect.SQL_Select(SQLStoreQuer.SQL_Req());

        try {
            while (ResultSQLRequest.next()) {
                treat.add(ResultSQLRequest.getString("TREAT_CHAIRMAN_ROLE"));
                treat.add(ResultSQLRequest.getString("TREAT_CHAIRMAN"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treat;
    }

    private  static  String HowYearsNow(Calendar calendar) // вычисление сколько сейчас полных лет пациенту
    {
        Calendar nowYear = Calendar.getInstance();// Сейчас
        return  Integer.toString(((nowYear.get(Calendar.YEAR)) -(calendar.get(Calendar.YEAR))));
    }

    private  static  String HowMonthNow(Calendar calendar) // вычисление сколько сейчас месяцев пациенту
    {
        Calendar nowMonth = Calendar.getInstance();// Сейчас
        return Integer.toString((nowMonth.get(Calendar.MONTH)+1) -(calendar.get(Calendar.MONTH)+1));
    }


    private static boolean NodeIsEmpty(ResultSet ResultSQLRequest, String str) // Проверка надо на пустоту
    {
        try {
            return ((ResultSQLRequest.getString(str)) == null || (ResultSQLRequest.getString(str)).equals("")) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //Если узел пуст удалить, если нет - заполнить
    private static void VerifyNode(ResultSet ResultSQLRequest, String str,SOAPElement row,SOAPElement del )
    {
        try {
            //  while(ResultSQLRequest.next()) {

            if (((ResultSQLRequest.getString(str))==null) || (ResultSQLRequest.getString(str)).equals("")
                    || ((ResultSQLRequest.getString(str))==" ")) {
                // System.out.println("Удалить:"+str+":'"+ResultSQLRequest.getString(str)+"'");
                row.removeChild(del);
            } else {
                // System.out.println("Добавить:"+str+":'"+ResultSQLRequest.getString(str)+"'");
                del.addTextNode(ResultSQLRequest.getString(str));
            }
            //  }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public static SOAPMessage SignationMessage(SOAPMessage soapMessage)
    {
        try {
            soapMessage= Sign.SignationByParametrs("http://eln.fss.ru/actor/mo/"+GlobalVariables.ogrnMo[1]+"/ELN_"+GlobalVariables.eln,
                    "#ELN_"+GlobalVariables.eln,soapMessage);

            if(fdoc==1)// Если есть что подписывать у доктора 1
            {
                soapMessage =Sign.SignationByParametrs("http://eln.fss.ru/actor/doc/"+GlobalVariables.eln+"_3_doc",
                        "#ELN_"+GlobalVariables.eln+"_3_doc",soapMessage);
                if(fvk==1)//Если есть вк 1
                {
                    soapMessage =Sign.SignationByParametrs("http://eln.fss.ru/actor/doc/"+GlobalVariables.eln+"_3_vk",
                            "#ELN_"+GlobalVariables.eln+"_3_vk",soapMessage);
                }
            }
            if(fdoc2==1)
            {
                soapMessage =Sign.SignationByParametrs("http://eln.fss.ru/actor/doc/"+GlobalVariables.eln+"_4_doc",
                        "#ELN_"+GlobalVariables.eln+"_4_doc",soapMessage);
                if(fvk2==1)
                {
                    soapMessage =Sign.SignationByParametrs("http://eln.fss.ru/actor/doc/"+GlobalVariables.eln+"_4_vk",
                            "#ELN_"+GlobalVariables.eln+"_4_vk",soapMessage);
                }
            }
            if(fdoc3==1)
            {
                soapMessage =Sign.SignationByParametrs("http://eln.fss.ru/actor/doc/"+GlobalVariables.eln+"_5_doc",
                        "#ELN_"+GlobalVariables.eln+"_5_doc",soapMessage);
                if(fvk3==1)
                {
                    soapMessage =Sign.SignationByParametrs("http://eln.fss.ru/actor/doc/"+GlobalVariables.eln+"_5_vk",
                            "#ELN_"+GlobalVariables.eln+"_5_vk",soapMessage);
                }
            }

            if(fBREACH==1)
            {
                soapMessage =Sign.SignationByParametrs("http://eln.fss.ru/actor/doc/"+GlobalVariables.eln+"_1_doc",
                        "#ELN_"+GlobalVariables.eln+"_1_doc",soapMessage);
            }

            if(fresult==1)
            {
                soapMessage =Sign.SignationByParametrs("http://eln.fss.ru/actor/doc/"+GlobalVariables.eln+"_2_doc",
                        "#ELN_"+GlobalVariables.eln+"_2_doc",soapMessage);
            }

            return soapMessage;

        } catch (SOAPException | IOException | InvalidAlgorithmParameterException
                | TransformerException | KeyStoreException | CertificateException | NoSuchProviderException
                | TransformationException | MarshalException | XMLSignatureException | WSSecurityException
                | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return soapMessage;
    }
}
