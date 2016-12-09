package ExperementalPack;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by rkurbanov on 05.12.2016.
 */
@XmlRootElement(name = "ROWSET")
@XmlType(propOrder = {
        "row"
})
public class ROWSET {

    protected String version;
    protected String software;
    protected String versionSoftware;
    protected String phone;
    protected String email;
    protected String author;
    protected List<ROW> row;

    public List<ROW> getRow() {
        return row;
    }

    @XmlElement(name = "ROW", required = true)
    public void setRow(List<ROW> row) {
        this.row = row;
    }

    public String getAuthor() {
        return author;
    }
    @XmlAttribute(name = "author", namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    @XmlAttribute(name = "version", namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setVersion(String version) {
        this.version = version;
    }

    public String getSoftware() {
        return software;
    }

    @XmlAttribute(name = "software", namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setSoftware(String software) {
        this.software = software;
    }

    public String getVersionSoftware() {
        return versionSoftware;
    }

    @XmlAttribute(name = "version_software", namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setVersionSoftware(String versionSoftware) {
        this.versionSoftware = versionSoftware;
    }

    public String getPhone() {
        return phone;
    }

    @XmlAttribute(name = "phone", namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    @XmlAttribute(name = "email", namespace = "http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl")
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String res = "ROWSET{" +
               "ROW{";
       /* if(row != null){
            for(ROW r : row){
                res += r.toString();
            }
        }*/
        res += "}}";
        return res;
    }
}
