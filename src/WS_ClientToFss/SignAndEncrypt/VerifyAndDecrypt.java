package WS_ClientToFss.SignAndEncrypt;

import HelpersMethods.Doc;
import HelpersMethods.GlobalVariables;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.EncryptionConstants;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit;

import javax.xml.crypto.KeySelector;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class VerifyAndDecrypt {

    public static SOAPMessage VerifyAndDecrypt(SOAPMessage soapMessage)
    {
        try {
            Doc.SaveSOAPToXML("CryptedResponse.xml",soapMessage); // сохраняем в файл
            org.w3c.dom.Document doc = StartDecrypt(Certificate.GetCertificateFromStorage(GlobalVariables.CertAliasMO[1]),
                    Certificate.GetPrivateKey(GlobalVariables.CertPasswordMO[1],GlobalVariables.CertAliasMO[1])); // расшифровываем на нашем открытом ключе

            // writeDoc(doc,System.out);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            NodeList originalRoot = doc.getDocumentElement().getElementsByTagName("S:Envelope"); //Удаляем лишние теги(коируем исключая внешние <soap-env:envelope>
            Element element = (Element) originalRoot.item(0);
            Document copiedDocument = db.newDocument();
            org.w3c.dom.Node copiedRoot = copiedDocument.importNode(element, true);
            copiedDocument.appendChild(copiedRoot);

            soapMessage = Doc.DocToSOAP(copiedDocument); //конвертируем Документ в Месседж

            //soapMessage.writeTo(System.out);
            if(!Verify(soapMessage, Certificate.ExtractCertFromCertStore())) // верифицируем
            {System.out.println("Сообщение не прошло проверку подписи!");}

            return soapMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soapMessage;
    }

    //декрипт
    private static Document StartDecrypt(X509Certificate cert, PrivateKey key) throws Exception
    {
        JCPXMLDSigInit.init();
        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage message2 = mf.createMessage();
        SOAPPart soapPart2 = message2.getSOAPPart();
        FileInputStream is2 = new FileInputStream(GlobalVariables.PathToSave[1]+"CryptedResponse.xml"); // ЕСЛИ берем из файла
        soapPart2.setContent(new StreamSource(is2));
        Document doc2 = message2.getSOAPPart().getEnvelope().getOwnerDocument();

        XMLCipher xmlCipher = XMLCipher.getInstance();
        xmlCipher.init(XMLCipher.DECRYPT_MODE, null);

        Element encryptedDataElement = (Element) doc2.getElementsByTagNameNS(
                EncryptionConstants.EncryptionSpecNS,
                EncryptionConstants._TAG_ENCRYPTEDDATA).item(0);

        if (key != null)
            xmlCipher.setKEK(key);

        xmlCipher.doFinal(doc2, encryptedDataElement);
        return doc2;
    }

    //проверка подписи
    private static boolean Verify(SOAPMessage message,X509Certificate cert) throws Exception {

        SOAPHeader header = message.getSOAPHeader();
        Document doc = header.getOwnerDocument();
        SOAPBody soapBody = message.getSOAPBody();
        Attr idAttr = soapBody.getAttributeNode("wsu:Id");
        soapBody.setIdAttributeNode(idAttr, true);

        final Element wssecontext = doc.createElementNS(null, "namespaceContext");
        wssecontext.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

        Iterator allHeaders = header.examineAllHeaderElements();
        Element binarySecurityTokenElement = null;
        SOAPHeaderElement headerElement;

        while (allHeaders.hasNext()) {
            headerElement = (SOAPHeaderElement)allHeaders.next();
            if  ("http://eln.fss.ru/actor/fss/ca/1027739443236".equals(headerElement.getActor())) {
                binarySecurityTokenElement = (Element) headerElement.getElementsByTagName("wsse:BinarySecurityToken").item(0);
                break;
            }
        }
        if (binarySecurityTokenElement == null) {
            System.out.println("Автор-хидер не найден!");
            return false;
        }
        if (cert == null) {
            throw new Exception("Сертификат не найден!");
        }//else System.out.println("Сертификат найден.");
        NodeList nl = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("Не найден элемент Signature.");
        }//else System.out.println("Элемент Signature найден!");

        DOMValidateContext valContext = new DOMValidateContext(KeySelector.singletonKeySelector(cert.getPublicKey()), nl.item(0));

        Provider xmlDSigProvider = new ru.CryptoPro.JCPxml.dsig.internal.dom.XMLDSigRI();
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", xmlDSigProvider);

        //XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);
        return signature.validate(valContext);
        //return true;
    }
}
