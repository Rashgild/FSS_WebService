package WS_ClientToFss.XmlFile;

import EntityClasses.PrParseFileLnLpu;
import HelpersMethods.Doc;
import HelpersMethods.GlobalVariables;
import HelpersMethods.SQLStoreQuer;
import ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit;

import javax.xml.soap.SOAPMessage;

/**
 * Created by rkurbanov on 19.01.2017.
 */


public class XmlFileLnLpuNew {

/*    public static void main(String[] args) throws Exception {
       SOAPMessage m =  Mess();
       m.writeTo(System.out);
    }*/

    public static SOAPMessage Mess() throws Exception {

        GlobalVariables.GetConfiguration();
        GlobalVariables.setUp();
        JCPXMLDSigInit.init();
        System.out.println("Создание каркаса!");
        PrParseFileLnLpu prParseFileLnLpu =
                CreateXMLFrame.CreateFrame(SQLStoreQuer.forXMLFileLnLpuN_One(),
        SQLStoreQuer.forXMLFileLnLpuN_Two());

        System.out.println("Создаю сообщение!");
        GlobalVariables.prparse = prParseFileLnLpu;
        SOAPMessage message = CreateSOAPMessage.CreateMessage(prParseFileLnLpu);
        System.out.println("Сохраняю");
        Doc.SaveSOAPToXML("XMLochka.xml",message);
        System.out.println("Подписываю! ");
        message = Sign.Signation(prParseFileLnLpu,message);
        System.out.println("Подгатавливаю запрос к БД!");
        GlobalVariables.Request = Doc.SoapMessageToString(message);
        System.out.println("Шифрую!");
        message = Encr.Encryption(message);


        return message;
    }

}
