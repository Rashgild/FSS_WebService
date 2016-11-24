
package ws_tomedos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.ibs.fss.ln.ws.fileoperationsln.FileOperationsLnUserGetNewLNNumRangeOut;


/**
 * <p>Java class for GetRangeNumbersResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetRangeNumbersResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl}FileOperationsLnUser_getNewLNNumRange_Out" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetRangeNumbersResponse", propOrder = {
    "_return"
})
public class GetRangeNumbersResponse {

    @XmlElement(name = "return")
    protected FileOperationsLnUserGetNewLNNumRangeOut _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link FileOperationsLnUserGetNewLNNumRangeOut }
     *     
     */
    public FileOperationsLnUserGetNewLNNumRangeOut getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileOperationsLnUserGetNewLNNumRangeOut }
     *     
     */
    public void setReturn(FileOperationsLnUserGetNewLNNumRangeOut value) {
        this._return = value;
    }

}
