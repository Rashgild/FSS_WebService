package libs.Samples; /**
 * $RCSfile$
 * version $Revision: 36379 $
 * created 13.07.2009 16:38:35 by Iva
 * last modified $Date: 2012-05-30 12:19:27 +0400 (Wed, 30 May 2012) $ by $Author: afevma $
 * (C) ООО Крипто-Про 2004-2009.
 *
 * Программный код, содержащийся в этом файле, предназначен
 * для целей обучения. Может быть скопирован или модифицирован 
 * при условии сохранения абзацев с указанием авторства и прав.
 *
 * Данный код не может быть непосредственно использован
 * для защиты информации. Компания Крипто-Про не несет никакой
 * ответственности за функционирование этого кода.
 */
//package xmlSign;

import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.keys.content.X509Data;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.EncryptionConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.CryptoPro.JCP.Key.KeyTwix;
import ru.CryptoPro.JCPRequest.CertGen;
import ru.CryptoPro.JCPxml.Consts;
import ru.CryptoPro.JCPxml.XmlInit;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.security.Key;
import java.security.cert.X509Certificate;

/**
 * Пример шифрования XML.
 *
 * @author Copyright 2004-2009 Crypto-Pro. All rights reserved.
 * @.Version
 */
public class CryptXML {
public static void main(String[] args) throws Exception {
    /**
     * Регистрация алгоритмов ГОСТ
     */
    XmlInit.init();

    /**
     * создание простого XML докуента для примера.
     */
    Document doc = createSampleDocument();
    writeDoc(doc, System.out);
    System.out.println("");

    /*
     * создание временной ключевой пары для теста.
     */
    KeyTwix twix = CertGen.generateTwix("GOST3410DHEPH",
            "GOST3411withGOST3410DHEL",
            "CN=cert");
    //KeyTwix twix = new KeyTwix("XmlSignEncrypt");

    /**
     * зашифрование "на сертификате".
     */
    encrypt(doc, twix.getCert());
    writeDoc(doc, System.out);
    System.out.println("");

    /**
     * расшифрование на секретном ключе.
     */
    decrypt(doc, twix.getPrivate());
    writeDoc(doc, System.out);
    System.out.println("");

}

/**
 * Зашифрование документа
 *
 * @param doc документ, который будем шифровать
 * @param cert сертификат
 * @return зашифрованный документ
 * @throws Exception ошибки шифрования
 */
public static Document encrypt(Document doc, X509Certificate cert)
        throws Exception {
    /**
     * Создание случайного сессионного ключа.
     */
    SecretKey sessionKey = KeyGenerator.getInstance("GOST28147").generateKey();
    /**
     * Зашифрование сессионного ключа.
     */
    EncryptedKey encryptedKey = wrapKey(doc, sessionKey, cert);
    /**
     * зашифрование документа
     */
    return encrypt(doc, sessionKey, encryptedKey);
}

/**
 * Зашифрование документа doc на sessionKey.
 *
 * @param doc документ, который будем шифровать
 * @param sessionKey сессионный ключ шифрования
 * @param encryptedKey зашифрованный sessionKey будет добавлен в документ
 * @return шифрованный документ
 * @throws Exception ошибки шифрования
 */
public static Document encrypt(Document doc, SecretKey sessionKey,
                               EncryptedKey encryptedKey) throws Exception {
    Element element = doc.getDocumentElement();
    /**
     * Создаем шифратор в режиме зашифрования. Константа URI_GOST_CIPHER
     * определена в файле ru.CryptoPro.JCPxml.Consts
     * public static final String URI_GOST_CIPHER =
     * "urn:ietf:params:xml:ns:cpxmlsec:algorithms:gost28147";
     */
    XMLCipher xmlCipher = XMLCipher.getInstance(Consts.URI_GOST_CIPHER);
    xmlCipher.init(XMLCipher.ENCRYPT_MODE, sessionKey);
    /**
     * добавляем шифрованный ключ.
     */
    EncryptedData encryptedData = xmlCipher.getEncryptedData();
    KeyInfo keyInfo = new KeyInfo(doc);
    keyInfo.add(encryptedKey);
    encryptedData.setKeyInfo(keyInfo);
    /**
     * зашифрование документа
     */
    xmlCipher.doFinal(doc, element, true);
    return doc;
}

/**
 * зашифрование сессионного ключа <code>sessionKey</code> и создание
 * <code>EncryptedKey</code> с сертификатом.
 *
 * @param doc xml документ
 * @param sessionKey случайный сессионный ключ.
 * @param cert сертификат
 * @return зашифрованный ключ
 * @throws Exception ошибки шифрования
 */
public static EncryptedKey wrapKey(Document doc, SecretKey sessionKey,
                                   X509Certificate cert)
        throws Exception {
    /**
     * создание шифратора для зашифрования ключа. Константа URI_GOST_TRANSPORT
     * определена в файле ru.CryptoPro.JCPxml.Consts
     * public static final String URI_GOST_TRANSPORT =
     * "urn:ietf:params:xml:ns:cpxmlsec:algorithms:transport-gost2001";
     */
    XMLCipher keyCipher = XMLCipher.getInstance(Consts.URI_GOST_TRANSPORT);
    keyCipher.init(XMLCipher.WRAP_MODE, cert.getPublicKey());
    /**
     * создание KeyInfo с сертификатом
     */
    KeyInfo certKeyInfo = new KeyInfo(doc);
    X509Data x509data = new X509Data(doc);
    x509data.addCertificate(cert);
    certKeyInfo.add(x509data);
    /**
     * зашифрование ключа
     */
    EncryptedKey encryptedKey = keyCipher.encryptKey(doc, sessionKey);
    encryptedKey.setKeyInfo(certKeyInfo);
    return encryptedKey;
}

/**
 * Расшифрование документа.
 *
 * @param doc зашифрованный документ
 * @param key секретный ключ шифрования
 * @return расшифрованный документ
 * @throws Exception error
 */
public static Document decrypt(Document doc, Key key) throws Exception {
    /**
     * create cipher in decrypt mode.
     */
    XMLCipher xmlCipher = XMLCipher.getInstance();
    xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
    /*
     * extract element to decrypt.
     */
    Element encryptedDataElement = (Element) doc.getElementsByTagNameNS(
            EncryptionConstants.EncryptionSpecNS,
            EncryptionConstants._TAG_ENCRYPTEDDATA).item(0);
    /*
     * The key to be used for decrypting xml data would be obtained
     * from the keyinfo of the EncrypteData using the kek.
     */
    if (key != null)
        xmlCipher.setKEK(key);
    /*
     * The following doFinal call replaces the encrypted data with
     * decrypted contents in the document.
     */
    xmlCipher.doFinal(doc, encryptedDataElement);
    return doc;
}

/**
 * create DocumentBuilderFactory with properties.
 *
 * @return DocumentBuilderFactory
 */
public static DocumentBuilderFactory createDocFactory() {
    // инициализация объекта чтения XML-документа
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    // установка флага, определяющего игнорирование пробелов
    // в содержимом элементов при обработке XML-документа
    dbf.setIgnoringElementContentWhitespace(true);
    // установка флага, определяющего преобразование узлов CDATA
    // в текстовые узлы при обработке XML-документа
    dbf.setCoalescing(true);
    // установка флага, определяющего поддержку пространств имен
    // при обработке XML-документа
    dbf.setNamespaceAware(true);

    return dbf;
}

/**
 * create new simple document to test. <p/> It will look something like:
 * <p/>
 * <apache:RootElement xmlns:apache="http://www.apache.org/ns/#app1">
 * <apache:foo>Some simple text</apache:foo> </apache:RootElement>
 *
 * @return test document
 * @throws javax.xml.parsers.ParserConfigurationException if a DocumentBuilder
 * cannot be created which satisfies the configuration requested.
 */
public static Document createSampleDocument()
        throws ParserConfigurationException {

    DocumentBuilderFactory dbf = createDocFactory();
    Document document = dbf.newDocumentBuilder().newDocument();

    Element root = document.createElementNS("http://www.apache.org/ns/#app1",
            "apache:RootElement");
    root.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:apache",
            "http://www.apache.org/ns/#app1");
    document.appendChild(root);

    root.appendChild(document.createTextNode("\n"));

    Element childElement = document.createElementNS(
            "http://www.apache.org/ns/#app1", "apache:foo");
    childElement.appendChild(document.createTextNode("Some simple text"));
    root.appendChild(childElement);

    root.appendChild(document.createTextNode("\n"));

    return document;
}

/**
 * save doc.
 *
 * @param doc document to save.
 * @param out output stream
 * @throws javax.xml.transform.TransformerException If an unrecoverable error
 * occurs during the course of the transformation.
 */
public static void writeDoc(Document doc, OutputStream out)
        throws TransformerException {
    // создание объекта копирования содержимого XML-документа в поток
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    // копирование содержимого XML-документа в поток
    transformer.transform(new DOMSource(doc), new StreamResult(out));
}

}
