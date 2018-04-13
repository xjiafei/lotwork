
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
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

    private final static QName _String_QNAME = new QName("http://tempuri.org/", "string");
    private final static QName _Boolean_QNAME = new QName("http://tempuri.org/", "boolean");
    private final static QName _DataSet_QNAME = new QName("http://tempuri.org/", "DataSet");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RetrieveHistoryNumberResponse }
     * 
     */
    public RetrieveHistoryNumberResponse createRetrieveHistoryNumberResponse() {
        return new RetrieveHistoryNumberResponse();
    }

    /**
     * Create an instance of {@link GrabRandomNumberResponse }
     * 
     */
    public GrabRandomNumberResponse createGrabRandomNumberResponse() {
        return new GrabRandomNumberResponse();
    }

    /**
     * Create an instance of {@link RetrieveHistoryNumber }
     * 
     */
    public RetrieveHistoryNumber createRetrieveHistoryNumber() {
        return new RetrieveHistoryNumber();
    }

    /**
     * Create an instance of {@link RetrieveHistoryNumberResponse.RetrieveHistoryNumberResult }
     * 
     */
    public RetrieveHistoryNumberResponse.RetrieveHistoryNumberResult createRetrieveHistoryNumberResponseRetrieveHistoryNumberResult() {
        return new RetrieveHistoryNumberResponse.RetrieveHistoryNumberResult();
    }

    /**
     * Create an instance of {@link ReceiveNumberResponse }
     * 
     */
    public ReceiveNumberResponse createReceiveNumberResponse() {
        return new ReceiveNumberResponse();
    }

    /**
     * Create an instance of {@link GrabRandomNumber }
     * 
     */
    public GrabRandomNumber createGrabRandomNumber() {
        return new GrabRandomNumber();
    }

    /**
     * Create an instance of {@link DataSet }
     * 
     */
    public DataSet createDataSet() {
        return new DataSet();
    }

    /**
     * Create an instance of {@link ReceiveNumber }
     * 
     */
    public ReceiveNumber createReceiveNumber() {
        return new ReceiveNumber();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "DataSet")
    public JAXBElement<DataSet> createDataSet(DataSet value) {
        return new JAXBElement<DataSet>(_DataSet_QNAME, DataSet.class, null, value);
    }

}
