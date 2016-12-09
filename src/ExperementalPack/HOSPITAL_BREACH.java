package ExperementalPack;

import com.sun.xml.internal.txw2.annotation.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by rkurbanov on 05.12.2016.
 */
@XmlRootElement(name = "ROW")
@XmlType(propOrder = {"hospitalbreachcode","hospitalbreachdt"})
@XmlAccessorType(XmlAccessType.FIELD)
public class HOSPITAL_BREACH {

    @XmlAttribute(name = "Id", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    protected String AttributeId;
    @XmlElement(name = "HOSPITAL_BREACH_CODE")
    private String hospitalbreachcode;
    @XmlElement(name = "HOSPITAL_BREACH_DT")
    private String hospitalbreachdt;

    public String getAttributeId() {
        return AttributeId;
    }


    public void setAttributeId(String attributeId) {
        AttributeId = attributeId;
    }

    public String getHospitalbreachcode() {
        return hospitalbreachcode;
    }


    public void setHospitalbreachcode(String hospitalbreachcode) {
        if(hospitalbreachcode!=null && !hospitalbreachcode.equals("")) this.hospitalbreachcode = hospitalbreachcode;
    }

    public String getHospitalbreachdt() {
        return hospitalbreachdt;
    }


    public void setHospitalbreachdt(String hospitalbreachdt) {
        if(hospitalbreachdt!=null && !hospitalbreachdt.equals("")) this.hospitalbreachdt = hospitalbreachdt;
    }
}
