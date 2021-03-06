package HelpersMethods;

import org.apache.xml.security.c14n.Canonicalizer;
import org.w3c.dom.Document;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class Doc {


    public static String DocToString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    public static void writeDoc(Document doc, OutputStream out)
            throws TransformerException {
        // создание объекта копирования содержимого XML-документа в поток
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        // копирование содержимого XML-документа в поток
        transformer.transform(new DOMSource(doc), new StreamResult(out));
    }

    public byte[] documentToByte(Document document)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        org.apache.xml.security.utils.XMLUtils.outputDOM(document, baos, true);
        return baos.toByteArray();
    }

    /**
     * Сохранение Soap-сообщения в файл
     * @param FileName Имя сохраняемого файла
     * @param soapMessage Сообщение, которое требуется сохранить
     * @throws Exception e,
     * @throws IOException e
     */
    public static void SaveSOAPToXML( String FileName, SOAPMessage soapMessage)
            throws IOException, SOAPException {

        String strMsg = SoapMessageToString(soapMessage);//new String(out2.toByteArray());//.getBytes("UTF-16");
        try {

            Writer w = new OutputStreamWriter(new FileOutputStream(GlobalVariables.PathToSave[1]+FileName), "UTF-8");
            w.write(strMsg);
            w.flush();
            w.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Конверт из w3.dom.Document в Soap-сообщение
     * @param doc конвертируемый документ
     * @return SOAPMessage
     */
    public static SOAPMessage DocToSOAP(Document doc) throws Exception {
        Canonicalizer c14n =
                Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS);
        byte[] canonicalMessage = c14n.canonicalizeSubtree(doc);
        ByteArrayInputStream in = new ByteArrayInputStream(canonicalMessage);
        MessageFactory factory = MessageFactory.newInstance();
        return factory.createMessage(null, in);
    }

    public static String SoapMessageToString(SOAPMessage soapMessage)
    {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            soapMessage.writeTo(stream);
            String message = new String(stream.toByteArray(), "utf-8");
            return message;
        }
        catch (SOAPException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
