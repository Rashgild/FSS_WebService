package WS_ToMedOs;

import EntityClasses.PrParseFileLnLpu;
import HelpersMethods.GlobalVariables;
import HelpersMethods.SQLConnect;
import WS_ClientToFss.XmlFileLnLpuArray;
import ru.ibs.fss.ln.ws.fileoperationsln.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

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


        String result = s.getMESS();

        //INFO.ROWSET rowset2 = inf!=null?inf.getROWSET():null;

       /* LnNumList lnNumList = s!=null?s.getDATA():null;
        List<String> list =  lnNumList.getLNNum()!=null?lnNumList.getLNNum():null;

        for (int i = 0; i < list.size(); i++) {
            result += list.get(i);
        }*/


       // SaveInBD(result,"");

        return s;
        //System.out.println(Numbers.get(0));
    }


    @WebMethod
    public WSResult SetDisabilityDocumentPack(String datefrom, String dateto) throws SOAPException_Exception {
        GlobalVariables.DateTo = dateto;
        GlobalVariables.DateFrom = datefrom;
        GlobalVariables.flag=1;

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



        List<INFO.ROWSET.ROW> rows = result.getINFO().getROWSET().getROW();

        PrParseFileLnLpu prParseFileLnLpu = GlobalVariables.prparse;
        List<EntityClasses.ROW> rows1 = XmlFileLnLpuArray.UnPack(prParseFileLnLpu);

        //result.getREQUESTID();


        String sq="";
        String res="";
        GlobalVariables.Response = Split(GlobalVariables.Response);

        for (int i=0;i<rows1.size();i++){

            List<INFO.ROWSET.ROW.ERRORS.ERROR> errors1 = result.getINFO().getROWSET().getROW().get(i).getERRORS().getERROR();

            System.out.println(rows1.get(i).getLncode());
            System.out.println(rows1.get(i).getIdDD());
            System.out.println(rows.get(i).getROWNO());
            System.out.println(rows.get(i).getSTATUS());
            System.out.println(result.getMESS());

            for (int j=0;j<errors1.size();j++) {
                System.out.println(errors1.get(j).getERRMESS());
                res+=" "+j+" "+errors1.get(j).getERRMESS();
            }

            res = Split(res);
            sq ="INSERT INTO exportfsslog"+
                    "(result, responsecode, status, disabilitydocument, disabilitynumber, " +
                    "requestcode, requestdate, requesttime, requesttype, request_id)" +
                    "VALUES"+
                    "('"+res+"'," +
                    "'"+GlobalVariables.Response+"','"
                    +rows.get(i).getSTATUS()+"',"
                    +rows1.get(i).getIdDD()+",'"
                    +rows1.get(i).getLncode()+"','"
                    +GlobalVariables.Request
                    +"', current_date, current_time, '"
                    +GlobalVariables.Type+"Pack','"
                    +result.getREQUESTID()+"') RETURNING id;";

            SQLConnect.SQL_UpdIns(sq);
            GlobalVariables.Request="";
            GlobalVariables.Response="";
        }


       /*
        SQLConnect.SQL_UpdIns(sq);*/
        return result;
    }

    @WebMethod
    public WSResult SetDisabilityDocument(String DisabilityDocument_id)
            throws SOAPException_Exception {

        GlobalVariables.DisabilityDocument_id = DisabilityDocument_id;
        GlobalVariables.flag=2;

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


        //System.out.println(result.getMESS());
        //String messag = result.getMESS();





       // System.out.println(rows.get(0).getSTATUS());
        /*
        INFO inf = result.getINFO();
        INFO.ROWSET rowset2 = inf!=null?inf.getROWSET():null;
        INFO.ROWSET.ROW row = rowset2!=null?(rowset2.getROW()!=null?rowset2.getROW().get(0):null):null;
        INFO.ROWSET.ROW.ERRORS errors = row!=null?row.getERRORS():null;
        List<INFO.ROWSET.ROW.ERRORS.ERROR> errors1 = errors!=null?errors.getERROR():null;

        if (errors1!=null&&errors1.size()>0){
            for (int i = 0; i < errors1.size(); i++) {
                messag += errors1.get(i).getERRMESS();
            }
        }



        SaveInBD(messag,result.getSTATUS());*/
        return result;
    }

    private static void SaveInBD(String result, int status)
    {

        result = Split(result);
        GlobalVariables.Response = Split(GlobalVariables.Response);

        String sq ="INSERT INTO exportfsslog"+
                "(result, responsecode, status, disabilitydocument, disabilitynumber, requestcode, requestdate, requesttime, requesttype)" +
                "VALUES"+
                "('"+result+"','"+GlobalVariables.Response+"','"
                +status+"', "+GlobalVariables.DisabilityDocument_id+", '"
                +GlobalVariables.eln+"','"+GlobalVariables.Request+"', current_date, current_time, '"
                +GlobalVariables.Type+"') RETURNING id;";

        SQLConnect.SQL_UpdIns(sq);
    }

    private static String Split(String str)
    {
        String[] arrstr;
        arrstr = str.split("'");
        str="";
        for (int i=0;i<arrstr.length;i++ ) {
            str += arrstr[i];
        }
        return str;
    }
}
