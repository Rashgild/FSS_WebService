package ExperementalPack;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * Created by rkurbanov on 08.12.2016.
 */

@XmlRootElement(name = "prParseFilelnlpu")

public class PrParseFilelnlpu {

    //@XmlAttribute(name = "Id", namespace = "wsu")
    protected String attrib;
    protected String ds;
    protected String attrib3;
    protected String attrib4;
    protected String wsu;
    protected String attrib6;
    protected String attrib7;
    protected String attrib8;
    protected List<Request> requests;


    public String getAttrib() {
        return attrib;
    }

    @XmlAttribute(name = "xmlns",namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }
    public String getDs() {
        return ds;
    }

    //@XmlAttribute(name = "ds", namespace = "ds")

   /* @XmlSchema(ds =
            {@XmlNs(
                    prefix = "123",namespaceURI = "asd"
            )})*/
    @XmlAttribute(name = "ds",namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getAttrib3() {
        return attrib3;
    }
    @XmlAttribute(name = "soapenv",namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setAttrib3(String attrib3) {
        this.attrib3 = attrib3;
    }
    public String getAttrib4() {
        return attrib4;
    }
    @XmlAttribute(name = "wsse",namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setAttrib4(String attrib4) {
        this.attrib4 = attrib4;
    }

    public String getWsu() {
        return wsu;
    }

    @XmlAttribute(name = "wsu",namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    public void setWsu(String wsu) {
        this.wsu = wsu;
    }

    public String getAttrib6() {
        return attrib6;
    }
    @XmlAttribute(name = "xml",namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setAttrib6(String attrib6) {
        this.attrib6 = attrib6;
    }

    public String getAttrib7() {
        return attrib7;
    }
    @XmlAttribute(name = "xsd",namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setAttrib7(String attrib7) {
        this.attrib7 = attrib7;
    }

    public String getAttrib8() {
        return attrib8;
    }
    @XmlAttribute(name = "xsi",namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setAttrib8(String attrib8) {
        this.attrib8 = attrib8;
    }

    public List<Request> getRequests() {
        return requests;
    }

    @XmlElement(name = "request")
    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
