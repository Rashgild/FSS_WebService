package WS_ClientToFss.XmlFile;

import HelpersMethods.Doc;
import WS_ClientToFss.SignAndEncrypt.Encrypt;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

/**
 * Created by rkurbanov on 20.01.2017.
 */
public class Encr {
    /** Шифрование сообщения **/
    protected static SOAPMessage Encryption(SOAPMessage message) throws Exception {
        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage NewMessg = mf.createMessage();
        NewMessg= Encrypt.CreateXMLAndEncrypt(NewMessg, "my.xml");
        Doc.SaveSOAPToXML("LNCrypted.xml",NewMessg);
        message = NewMessg;
        return message;
    }
}
