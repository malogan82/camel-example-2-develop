package it.marco.camel.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.apache.camel.foo.bar package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final QName personaQNAME = new QName("", "Persona");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.foo.bar
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Persona }
     * 
     */
    public Persona createPersona() {
        return new Persona();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Persona }{@code >}}
     */ 
     
    @XmlElementDecl(namespace = "", name = "Person")
    public JAXBElement<Persona> createPerson(Persona value) {
        return new JAXBElement<Persona>(personaQNAME, Persona.class, null, value);
    }

}