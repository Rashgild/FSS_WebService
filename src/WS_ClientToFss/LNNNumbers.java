package WS_ClientToFss;

import HelpersMethods.Doc;
import HelpersMethods.GlobalVariables;
import WS_ClientToFss.SignAndEncrypt.Encrypt;
import WS_ClientToFss.SignAndEncrypt.Sign;

import javax.xml.soap.*;
import java.io.IOException;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class LNNNumbers {

    /**
     * Меняет перехваченное сообщение под нужный шаблон
     * @param soapMsg передается из Инъектера
     * @Out подписанное и зашифрованное soapMsg
     */
    public static SOAPMessage StartGetLNNumbers(SOAPMessage soapMsg)
    {

        try {

            //TODO Формирование каркаса XML
            CreateXMLSkeleton(soapMsg);

            //TODO Подписание xml
            soapMsg= Sign.Signation();
            Doc.SaveSOAPToXML("GetNumSigned.xml", soapMsg);

            GlobalVariables.Request = Doc.SoapMessageToString(soapMsg);
            //TODO Шифрование
            MessageFactory mf = MessageFactory.newInstance();

            SOAPMessage NewMessg = mf.createMessage();
            NewMessg = Encrypt.CreateXMLAndEncrypt(NewMessg, "GetNumSigned.xml");


            return NewMessg;//CreateXMLAndEncrypt(NewMessg);
            //soapMsg.writeTo(System.out);
            //return  soapMsg;

            //return soapMsg;
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soapMsg;
    }


    /**
     * GlobalVariables.ogrn!
     * Меняет перехваченное сообщение под нужный шаблон
     * @param soapMessage перехваченное сообщение
     * @Out Файл "tempSkeleton.xml"
     */
   private static void CreateXMLSkeleton(SOAPMessage soapMessage)
            throws SOAPException, IOException {
        SOAPEnvelope soapEnv = soapMessage.getSOAPPart().getEnvelope();
        SOAPHeader soapHeader = soapEnv.getHeader();
        soapEnv.addHeader();
        //soapMessage
        soapEnv.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema");
        soapEnv.addNamespaceDeclaration("xsd","http://www.w3.org/2001/XMLSchema-instance");
        soapEnv.addNamespaceDeclaration("wsse","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        soapEnv.addNamespaceDeclaration("wsu","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

        SOAPBody soapBody = soapEnv.getBody();
        Name name = soapEnv.createName("Id");
        soapBody.addAttribute(name, "OGRN_"+ GlobalVariables.ogrnMo[1]);
        soapMessage.saveChanges();
        Doc.SaveSOAPToXML("tempSkeleton.xml", soapMessage);
    }

}
