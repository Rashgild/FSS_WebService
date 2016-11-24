
package ws_tomedos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.ibs.fss.ln.ws.fileoperationsln.WSResult;


/**
 * <p>Java class for SetDisabilityDocumentResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SetDisabilityDocumentResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ru/ibs/fss/ln/ws/FileOperationsLn.wsdl}WSResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetDisabilityDocumentResponse", propOrder = {
    "_return"
})
public class SetDisabilityDocumentResponse {

    @XmlElement(name = "return")
    protected WSResult _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link WSResult }
     *     
     */
    public WSResult getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSResult }
     *     
     */
    public void setReturn(WSResult value) {
        this._return = value;
    }

}
