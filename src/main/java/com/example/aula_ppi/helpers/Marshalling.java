package com.example.aula_ppi.helpers;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.IOException;


/**
 * Helper for marshalling and unmarshalling operations.
 */
public class Marshalling {

	private final Schema xml_schema;
	
	// private constructor for singleton pattern
	public Marshalling(Schema xml_schema) {
		this.xml_schema = xml_schema;
	}
	
	
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;
	
	
	/** Create the Unmarshaller */
	public void createUnmarshaller(Class<?> context_type) {
		
		// create context and Unmarshaller
		try {
			JAXBContext context = JAXBContext.newInstance(context_type);
			unmarshaller = (Unmarshaller) context.createUnmarshaller();
//			unmarshaller.setSchema(xml_schema);
		} catch (JAXBException e) {
			throw new RuntimeException("Failed to create Unmarshaller!", e);
		}
	}
	
	/** Get the Unmarshaller. Can be null if not initialized yet! */
	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}
	
	
	/** Create the Marshaller */
	public void createMarshaller(Class<?> context_type) {
		
		// create context and Marshaller
		try {
			JAXBContext context = JAXBContext.newInstance(context_type);
			marshaller = (Marshaller) context.createMarshaller();
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//			marshaller.setSchema(xml_schema);
		} catch (JAXBException e) {
			throw new RuntimeException("Failed to create Marshaller!", e);
		}
	}
	
	
	/** Get the XML-Schema used by this instance. */
	public Schema getSchema() {
		return xml_schema;
	}
	
	
	/** Get the Marshaller. Can be null if not initialized yet! */
	public Marshaller getMarshaller() {
		return marshaller;
	}
	

    /** Validates a content object against the schema using the Marshaller instance. */
    public void validate(Object jaxbObject, Class<?> context_type)
    throws JAXBException {
        try {
            Validator validator = xml_schema.newValidator();
            JAXBSource jaxbSource = new JAXBSource((JAXBContext) getMarshaller(), jaxbObject);
            validator.validate(jaxbSource);
        } catch (JAXBException | SAXException | IOException e) {
            throw new JAXBException("Validating object failed!", e);
        }
    }
}
