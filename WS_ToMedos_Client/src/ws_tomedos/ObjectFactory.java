
package ws_tomedos;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws_tomedos package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SetDisabilityDocumentResponse_QNAME = new QName("http://WS_ToMedOs/", "SetDisabilityDocumentResponse");
    private final static QName _GetRangeNumbersResponse_QNAME = new QName("http://WS_ToMedOs/", "GetRangeNumbersResponse");
    private final static QName _GetCalculaton_QNAME = new QName("http://WS_ToMedOs/", "GetCalculaton");
    private final static QName _GetCalculatonResponse_QNAME = new QName("http://WS_ToMedOs/", "GetCalculatonResponse");
    private final static QName _GetRangeNumbers_QNAME = new QName("http://WS_ToMedOs/", "GetRangeNumbers");
    private final static QName _SetDisabilityDocument_QNAME = new QName("http://WS_ToMedOs/", "SetDisabilityDocument");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws_tomedos
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetRangeNumbers }
     * 
     */
    public GetRangeNumbers createGetRangeNumbers() {
        return new GetRangeNumbers();
    }

    /**
     * Create an instance of {@link SetDisabilityDocument }
     * 
     */
    public SetDisabilityDocument createSetDisabilityDocument() {
        return new SetDisabilityDocument();
    }

    /**
     * Create an instance of {@link SetDisabilityDocumentResponse }
     * 
     */
    public SetDisabilityDocumentResponse createSetDisabilityDocumentResponse() {
        return new SetDisabilityDocumentResponse();
    }

    /**
     * Create an instance of {@link GetRangeNumbersResponse }
     * 
     */
    public GetRangeNumbersResponse createGetRangeNumbersResponse() {
        return new GetRangeNumbersResponse();
    }

    /**
     * Create an instance of {@link GetCalculatonResponse }
     * 
     */
    public GetCalculatonResponse createGetCalculatonResponse() {
        return new GetCalculatonResponse();
    }

    /**
     * Create an instance of {@link GetCalculaton }
     * 
     */
    public GetCalculaton createGetCalculaton() {
        return new GetCalculaton();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetDisabilityDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WS_ToMedOs/", name = "SetDisabilityDocumentResponse")
    public JAXBElement<SetDisabilityDocumentResponse> createSetDisabilityDocumentResponse(SetDisabilityDocumentResponse value) {
        return new JAXBElement<SetDisabilityDocumentResponse>(_SetDisabilityDocumentResponse_QNAME, SetDisabilityDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRangeNumbersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WS_ToMedOs/", name = "GetRangeNumbersResponse")
    public JAXBElement<GetRangeNumbersResponse> createGetRangeNumbersResponse(GetRangeNumbersResponse value) {
        return new JAXBElement<GetRangeNumbersResponse>(_GetRangeNumbersResponse_QNAME, GetRangeNumbersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCalculaton }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WS_ToMedOs/", name = "GetCalculaton")
    public JAXBElement<GetCalculaton> createGetCalculaton(GetCalculaton value) {
        return new JAXBElement<GetCalculaton>(_GetCalculaton_QNAME, GetCalculaton.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCalculatonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WS_ToMedOs/", name = "GetCalculatonResponse")
    public JAXBElement<GetCalculatonResponse> createGetCalculatonResponse(GetCalculatonResponse value) {
        return new JAXBElement<GetCalculatonResponse>(_GetCalculatonResponse_QNAME, GetCalculatonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRangeNumbers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WS_ToMedOs/", name = "GetRangeNumbers")
    public JAXBElement<GetRangeNumbers> createGetRangeNumbers(GetRangeNumbers value) {
        return new JAXBElement<GetRangeNumbers>(_GetRangeNumbers_QNAME, GetRangeNumbers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetDisabilityDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WS_ToMedOs/", name = "SetDisabilityDocument")
    public JAXBElement<SetDisabilityDocument> createSetDisabilityDocument(SetDisabilityDocument value) {
        return new JAXBElement<SetDisabilityDocument>(_SetDisabilityDocument_QNAME, SetDisabilityDocument.class, null, value);
    }

}
