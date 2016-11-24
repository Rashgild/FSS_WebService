
package ru.ibs.fss.ln.ws.fileoperationsln;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.ibs.fss.ln.ws.fileoperationsln package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SOAPException_QNAME = new QName("http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl", "SOAPException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.ibs.fss.ln.ws.fileoperationsln
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link INFO }
     * 
     */
    public INFO createINFO() {
        return new INFO();
    }

    /**
     * Create an instance of {@link INFO.ROWSET }
     * 
     */
    public INFO.ROWSET createINFOROWSET() {
        return new INFO.ROWSET();
    }

    /**
     * Create an instance of {@link INFO.ROWSET.ROW }
     * 
     */
    public INFO.ROWSET.ROW createINFOROWSETROW() {
        return new INFO.ROWSET.ROW();
    }

    /**
     * Create an instance of {@link INFO.ROWSET.ROW.ERRORS }
     * 
     */
    public INFO.ROWSET.ROW.ERRORS createINFOROWSETROWERRORS() {
        return new INFO.ROWSET.ROW.ERRORS();
    }

    /**
     * Create an instance of {@link FileOperationsLnUserGetLNDataOut }
     * 
     */
    public FileOperationsLnUserGetLNDataOut createFileOperationsLnUserGetLNDataOut() {
        return new FileOperationsLnUserGetLNDataOut();
    }

    /**
     * Create an instance of {@link ru.ibs.fss.ln.ws.fileoperationsln.ROW }
     * 
     */
    public ru.ibs.fss.ln.ws.fileoperationsln.ROW createROW() {
        return new ru.ibs.fss.ln.ws.fileoperationsln.ROW();
    }

    /**
     * Create an instance of {@link FileOperationsLnUserDisableLnOut }
     * 
     */
    public FileOperationsLnUserDisableLnOut createFileOperationsLnUserDisableLnOut() {
        return new FileOperationsLnUserDisableLnOut();
    }

    /**
     * Create an instance of {@link SOAPException }
     * 
     */
    public SOAPException createSOAPException() {
        return new SOAPException();
    }

    /**
     * Create an instance of {@link LnNumList }
     * 
     */
    public LnNumList createLnNumList() {
        return new LnNumList();
    }

    /**
     * Create an instance of {@link WSResult }
     * 
     */
    public WSResult createWSResult() {
        return new WSResult();
    }

    /**
     * Create an instance of {@link TREATPERIOD }
     * 
     */
    public TREATPERIOD createTREATPERIOD() {
        return new TREATPERIOD();
    }

    /**
     * Create an instance of {@link FileOperationsLnUserGetNewLNNumOut }
     * 
     */
    public FileOperationsLnUserGetNewLNNumOut createFileOperationsLnUserGetNewLNNumOut() {
        return new FileOperationsLnUserGetNewLNNumOut();
    }

    /**
     * Create an instance of {@link TREATFULLPERIOD }
     * 
     */
    public TREATFULLPERIOD createTREATFULLPERIOD() {
        return new TREATFULLPERIOD();
    }

    /**
     * Create an instance of {@link FileOperationsLnUserGetNewLNNumRangeOut }
     * 
     */
    public FileOperationsLnUserGetNewLNNumRangeOut createFileOperationsLnUserGetNewLNNumRangeOut() {
        return new FileOperationsLnUserGetNewLNNumRangeOut();
    }

    /**
     * Create an instance of {@link OUTROWSET }
     * 
     */
    public OUTROWSET createOUTROWSET() {
        return new OUTROWSET();
    }

    /**
     * Create an instance of {@link INFO.ROWSET.ROW.ERRORS.ERROR }
     * 
     */
    public INFO.ROWSET.ROW.ERRORS.ERROR createINFOROWSETROWERRORSERROR() {
        return new INFO.ROWSET.ROW.ERRORS.ERROR();
    }

    /**
     * Create an instance of {@link FileOperationsLnUserGetLNDataOut.DATA }
     * 
     */
    public FileOperationsLnUserGetLNDataOut.DATA createFileOperationsLnUserGetLNDataOutDATA() {
        return new FileOperationsLnUserGetLNDataOut.DATA();
    }

    /**
     * Create an instance of {@link ru.ibs.fss.ln.ws.fileoperationsln.ROW.HOSPITALBREACH }
     * 
     */
    public ru.ibs.fss.ln.ws.fileoperationsln.ROW.HOSPITALBREACH createROWHOSPITALBREACH() {
        return new ru.ibs.fss.ln.ws.fileoperationsln.ROW.HOSPITALBREACH();
    }

    /**
     * Create an instance of {@link ru.ibs.fss.ln.ws.fileoperationsln.ROW.TREATPERIODS }
     * 
     */
    public ru.ibs.fss.ln.ws.fileoperationsln.ROW.TREATPERIODS createROWTREATPERIODS() {
        return new ru.ibs.fss.ln.ws.fileoperationsln.ROW.TREATPERIODS();
    }

    /**
     * Create an instance of {@link ru.ibs.fss.ln.ws.fileoperationsln.ROW.LNRESULT }
     * 
     */
    public ru.ibs.fss.ln.ws.fileoperationsln.ROW.LNRESULT createROWLNRESULT() {
        return new ru.ibs.fss.ln.ws.fileoperationsln.ROW.LNRESULT();
    }

    /**
     * Create an instance of {@link FileOperationsLnUserDisableLnOut.DATA }
     * 
     */
    public FileOperationsLnUserDisableLnOut.DATA createFileOperationsLnUserDisableLnOutDATA() {
        return new FileOperationsLnUserDisableLnOut.DATA();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SOAPException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl", name = "SOAPException")
    public JAXBElement<SOAPException> createSOAPException(SOAPException value) {
        return new JAXBElement<SOAPException>(_SOAPException_QNAME, SOAPException.class, null, value);
    }

}
