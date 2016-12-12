package ExperementalPack;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * Created by rkurbanov on 08.12.2016.
 */

@XmlRootElement(name = "prParseFilelnlpu")
public class PrParseFilelnlpu {
    protected String fil;
    protected List<Request> requests;

    public String getFil() {
        return fil;
    }
    @XmlAttribute(name = "xmlns",namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setFil(String fil) {
        this.fil = fil;
    }

    public List<Request> getRequests() {
        return requests;
    }
    @XmlElement(name = "request")
    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}

