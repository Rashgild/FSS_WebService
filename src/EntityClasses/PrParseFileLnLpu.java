package EntityClasses;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by rkurbanov on 12.12.2016.
 */
@XmlRootElement(name = "prParseFilelnlpu")
@XmlAccessorType(XmlAccessType.FIELD)
public class PrParseFileLnLpu {

    @XmlAttribute(name = "xmlns", namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    private String fil;
    @XmlElement(name = "request")
    private List<Reqest> requests;

    public String getFil() {
        return fil;
    }
    public void setFil(String fil) {
        this.fil = fil;
    }
    public List<Reqest> getRequests() {
        return requests;
    }
    public void setRequests(List<Reqest> requests) {
        this.requests = requests;
    }

    @XmlRootElement(name = "request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Reqest {

        @XmlElement(name = "ogrn")
        private String ogrn;
        @XmlElement(name = "pXmlFile")
        private List<pXmlFile> pXmlFiles;

        public String getOgrn() {
            return ogrn;
        }
        public void setOgrn(String ogrn) {
            this.ogrn = ogrn;
        }
        public List<pXmlFile> getpXmlFiles() {
            return pXmlFiles;
        }
        public void setpXmlFiles(List<pXmlFile> pXmlFiles) {
            this.pXmlFiles = pXmlFiles;
        }

        @XmlRootElement(name = "pXmlFile")
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class pXmlFile {
            @XmlElement(name = "ROWSET")
            private List<ROWSET> rowset;

            public List<ROWSET> getRowset() {
                return rowset;
            }
            public void setRowset(List<ROWSET> rowset) {
                this.rowset = rowset;
            }
        }
    }
}
