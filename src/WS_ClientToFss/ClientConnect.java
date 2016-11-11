package WS_ClientToFss;

import HelpersMethods.GlobalVariables;
import ru.ibs.fss.ln.ws.fileoperationsln.FileOperationsLn;
import ru.ibs.fss.ln.ws.fileoperationsln.FileOperationsLnImplService;
import ru.ibs.fss.ln.ws.fileoperationsln.PrParseFilelnlpuElement;
import ru.ibs.fss.ln.ws.fileoperationsln.ROWSET;

/**
 * Created by rkurbanov on 10.11.16.
 */
public class ClientConnect {

    public static void main(String[] args) throws Exception{

        GlobalVariables.GetConfiguration();
        System.setProperty("javax.net.ssl.trustStore",GlobalVariables.PathToSSLcert[1]);//КОНФ

        //System.setProperty("javax.net.ssl.trustStore","C:\\Program Files\\Java\\jdk1.7.0_21\\jre\\lib\\security\\cacerts1");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        FileOperationsLnImplService service = new  FileOperationsLnImplService();
        FileOperationsLn start = service.getFileOperationsLnPort();


        //start.getNewLNNum("123123123");
        ///start.getNewLNNumRange("1023000836099",1);


        ROWSET rowset = new ROWSET();
        PrParseFilelnlpuElement prParseFilelnlpuElement = new PrParseFilelnlpuElement();
        PrParseFilelnlpuElement.PXmlFile pXmlFile= new PrParseFilelnlpuElement.PXmlFile();
        pXmlFile.setROWSET(rowset);
        prParseFilelnlpuElement.setPXmlFile(pXmlFile);
        start.prParseFilelnlpu(prParseFilelnlpuElement);

        //System.out.println(GlobalVariables.ogrn[1]);
    }
}
