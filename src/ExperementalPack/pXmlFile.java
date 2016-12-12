package ExperementalPack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "pXmlFile")
public class pXmlFile {
    protected List<ROWSET> rowset;


    public List<ROWSET> getRowset() {
        return rowset;
    }
    @XmlElement(name = "ROWSET")
    public void setRowset(List<ROWSET> rowset) {
        this.rowset = rowset;
    }
}