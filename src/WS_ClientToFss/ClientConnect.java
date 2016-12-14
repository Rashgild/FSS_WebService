package WS_ClientToFss;

import HelpersMethods.GlobalVariables;
import ru.ibs.fss.ln.ws.fileoperationsln.*;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class ClientConnect {

    public static void main(String[] args) throws Exception{

        GlobalVariables.GetConfiguration();

        System.setProperty("javax.net.ssl.trustStore",GlobalVariables.PathToSSLcert[1]);//КОНФ
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");


     /*  SOAPMessage message = XmlFileLnLpu.StartSetxmlFileLn();*/
        //System.setProperty("javax.net.ssl.trustStore","C:\\cacert_my");//КОНФ
       // System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        FileOperationsLnImplService service = new FileOperationsLnImplService();
        FileOperationsLn start = service.getFileOperationsLnPort();


        //start.getNewLNNum("123123123");
        ///start.getNewLNNumRange("1023000836099",1);


        ROWSET rowset = new ROWSET();
        PrParseFilelnlpuElement prParseFilelnlpuElement = new PrParseFilelnlpuElement();
        PrParseFilelnlpuElement.PXmlFile pXmlFile= new PrParseFilelnlpuElement.PXmlFile();
        pXmlFile.setROWSET(rowset);
        prParseFilelnlpuElement.setPXmlFile(pXmlFile);

        WSResult s = start.prParseFilelnlpu(prParseFilelnlpuElement);
        //List<WSResult> ss
        //s.ge
        System.out.println(s.getMESS());

        //System.out.println(GlobalVariables.ogrn[1]);
    }
}
