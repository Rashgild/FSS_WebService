package ExperementalPack;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by rkurbanov on 08.12.2016.
 */
@XmlRootElement(name = "request")
//@XmlType(propOrder = {"ROWSET"})
public class Request {

    protected String ogrn;
    protected List<pXmlFile> pXmlFiles;


    public String getOgrn() {
        return ogrn;
    }
    @XmlElement(name = "ogrn")
    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }


    public List<pXmlFile> getpXmlFiles() {
        return pXmlFiles;
    }
    @XmlElement(name = "pXmlFile")
    public void setpXmlFiles(List<pXmlFile> pXmlFiles) {
        this.pXmlFiles = pXmlFiles;
    }
}


