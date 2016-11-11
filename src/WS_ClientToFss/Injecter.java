package WS_ClientToFss;

import HelpersMethods.Doc;
import WS_ClientToFss.SignAndEncrypt.VerifyAndDecrypt;
import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.IOException;
import java.util.Set;

/**
 * Created by rkurbanov on 10.11.16.
 */


public class Injecter implements SOAPHandler<SOAPMessageContext> {



    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (isRequest) {
            SOAPMessage soapMsg = context.getMessage();
            //Запрос получения номера.
            if (WhatTheFunc(soapMsg) == 1)//
            {
                soapMsg = LNNNumbers.StartGetLNNumbers(soapMsg);
                context.setMessage(soapMsg);
                //System.out.println("Номера!");
            }
            //Запрос на отправку ЛН.
            if (WhatTheFunc(soapMsg) == 2)//
            {
                soapMsg= XmlFileLnLpu.StartSetxmlFileLn();
                context.setMessage(soapMsg);
            }
        }
// TODO Работа с ответом
            if(!isRequest)// Если не реквест, значит респонз
            {
                try {
                    SOAPMessage msg = context.getMessage(); // перехватываем респонз
                    msg = VerifyAndDecrypt.VerifyAndDecrypt(msg);
                    msg.writeTo(System.out);
                    context.setMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context)
    {
        SOAPMessage msg = context.getMessage();
        try {
            System.out.println("\n--------------------\n " +
                    "Ошибка:" +
                    "\n--------------------");
            msg.writeTo(System.out);
            System.out.println("\n--------------------\n");

        } catch (SOAPException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }

    private static int WhatTheFunc(SOAPMessage msg)
    {
        int flag=0;
        Document document = null;
        try {
            document = msg.getSOAPPart().getEnvelope().getOwnerDocument();

            String strdoc= Doc.DocToString(document);
            String str[];
            str = strdoc.split(":");

            if(str[5].equals("prParseFilelnlpu xmlns"))
            {
                return flag=2;
            }

            if(str[5].equals("getNewLNNumRange xmlns"))
            {
                return flag=1;
            }

        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}