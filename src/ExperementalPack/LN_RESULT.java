package ExperementalPack;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by rkurbanov on 05.12.2016.
 */
@XmlRootElement(name = "ROW")
@XmlType(propOrder = {"mseresult","otherstatedt","returndatelpu","nextlncode"})
public class LN_RESULT {

    protected String attribId;
    protected String mseresult;
    protected String otherstatedt;
    protected String returndatelpu;
    protected String nextlncode;

    public String getAttribId() {
        return attribId;
    }
    @XmlAttribute(name = "Id", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    public void setAttribId(String attribId) {
        this.attribId = attribId;
    }

    public String getMseresult() {
        return mseresult;
    }
    @XmlElement(name = "MSE_RESULT")
    public void setMseresult(String mseresult) {
        this.mseresult = mseresult;
    }

    public String getOtherstatedt() {
        return otherstatedt;
    }
    @XmlElement(name = "OTHER_STATE_DT")
    public void setOtherstatedt(String otherstatedt) {
        this.otherstatedt = otherstatedt;
    }

    public String getReturndatelpu() {
        return returndatelpu;
    }
    @XmlElement(name = "RETURN_DATE_LPU")
    public void setReturndatelpu(String returndatelpu) {
        this.returndatelpu = returndatelpu;
    }

    public String getNextlncode() {
        return nextlncode;
    }
    @XmlElement(name = "NEXT_LN_CODE")
    public void setNextlncode(String nextlncode) {
        this.nextlncode = nextlncode;
    }
}
