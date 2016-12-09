package ExperementalPack;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by rkurbanov on 09.12.2016.
 */

@XmlRootElement(name = "Envelope")
public class pack {

    protected String Envelope;
    protected List<Header> headers;
    protected List<Body> bodies;

    public String getEnvelope() {
        return Envelope;
    }

    public void setEnvelope(String envelope) {
        Envelope = envelope;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    @XmlElement(name = "Header")
    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    @XmlElement(name = "Body")
    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }

    public static class Header{


    }

    @XmlRootElement(name = "Envelope")
    public static class Body{

        protected List<PrParseFilelnlpu>prParseFilelnlpus;

        public List<PrParseFilelnlpu> getPrParseFilelnlpus() {
            return prParseFilelnlpus;
        }

        @XmlElement(name = "prParseFilelnlpu")
        public void setPrParseFilelnlpus(List<PrParseFilelnlpu> prParseFilelnlpus) {
            this.prParseFilelnlpus = prParseFilelnlpus;
        }
    }
}
