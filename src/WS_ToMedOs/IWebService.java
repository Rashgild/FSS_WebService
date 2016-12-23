package WS_ToMedOs;

import ru.ibs.fss.ln.ws.fileoperationsln.FileOperationsLnUserGetNewLNNumRangeOut;
import ru.ibs.fss.ln.ws.fileoperationsln.SOAPException_Exception;
import ru.ibs.fss.ln.ws.fileoperationsln.WSResult;

import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;

/**
 * Created by rkurbanov on 16.09.16.
 */
@javax.jws.WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IWebService {
    @WebMethod
    int GetCalculaton(int a);
    FileOperationsLnUserGetNewLNNumRangeOut GetRangeNumbers(String OGRN, int count_numbers) throws SOAPException_Exception;
    WSResult SetDisabilityDocument(String DisabilityDocument_id) throws SOAPException_Exception;
    WSResult SetDisabilityDocumentPack(String datefrom, String dateto, String limit) throws SOAPException_Exception;
}
