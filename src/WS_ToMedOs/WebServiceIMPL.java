package WS_ToMedOs;

import HelpersMethods.GlobalVariables;
import ru.ibs.fss.ln.ws.fileoperationsln.*;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by rkurbanov on 10.11.16.
 */
@WebService(endpointInterface = "WS_ToMedOs.IWebService")
public class WebServiceIMPL implements IWebService
{
    @Override
    @WebMethod
    public int GetCalculaton(int a)
    {
        a = a*a;
        System.out.println(a);
        return a;
    }

    @Override
    @WebMethod
    public FileOperationsLnUserGetNewLNNumRangeOut GetRangeNumbers(String OGRN, int count_numbers) throws SOAPException_Exception {

        System.setProperty("javax.net.ssl.trustStore",GlobalVariables.PathToSSLcert[1]);//КОНФ
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        FileOperationsLnImplService service = new  FileOperationsLnImplService();
        FileOperationsLn start = service.getFileOperationsLnPort();
        System.out.println("Подключились!");
        FileOperationsLnUserGetNewLNNumRangeOut s = start.getNewLNNumRange(OGRN,count_numbers);
        //List<String> Numbers = s.getDATA().getLNNum();
        return s;
        //System.out.println(Numbers.get(0));
    }

    @WebMethod
    public WSResult SetDisabilityDocument(String DisabilityDocument_id)
            throws SOAPException_Exception {


        GlobalVariables.DisabilityDocument_id = DisabilityDocument_id;

        System.setProperty("javax.net.ssl.trustStore",GlobalVariables.PathToSSLcert[1]);//КОНФ
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        FileOperationsLnImplService service = new  FileOperationsLnImplService();
        FileOperationsLn start = service.getFileOperationsLnPort();

        ROWSET rowset = new ROWSET();
        PrParseFilelnlpuElement prParseFilelnlpuElement = new PrParseFilelnlpuElement();
        PrParseFilelnlpuElement.PXmlFile pXmlFile= new PrParseFilelnlpuElement.PXmlFile();
        pXmlFile.setROWSET(rowset);
        prParseFilelnlpuElement.setPXmlFile(pXmlFile);
        WSResult result = start.prParseFilelnlpu(prParseFilelnlpuElement);

       // result.getMESS();

        //System.out.println(GlobalVariables.DisabilityDocument_id);
        return result;
    }
}
