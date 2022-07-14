package com.example.aula_ppi.helpers;

import com.example.aula_ppi.jaxb.Name;
import com.example.aula_ppi.jaxb.ObjectFactory;
import com.example.aula_ppi.jaxb.PersonType;
import com.example.aula_ppi.jaxb.Personen;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;


/**
 * Provides an interface to the "personen" document.
 */
public class PersonenModelBkp {

    // constant file reference to the document
    private File document;

    // load constant schema file
    private final Schema schema;

    // main element that holds people
    private Personen people = new Personen();

    // instance for marshalling and unmarshalling operations
    private final Marshalling marshalling;


    /**
     * Constructor
     */
    public PersonenModelBkp(String schema_path, String document_path) {

        // try to load XML-Document (e.g. "personen.xml")
        try {
			document = new File(document_path);
//            document = new File(PersonenModel.class.getResource(document_path).getFile());
        } catch (NullPointerException e) {
            out.println(document_path);
            try {
                File documentFileCreating = new File(document_path);
                documentFileCreating.createNewFile(); // if file already exists will do nothing

                File schemaFileCreating = new File(schema_path);
                schemaFileCreating.createNewFile(); // if file already exists will do nothing

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            document = new File("");
//			throw new RuntimeException("Could not load XML-Document!");
        }

        FileOutputStream oFile = null;
        try {
            oFile = new FileOutputStream(schema_path, false);
            String contant = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                    "    elementFormDefault=\"unqualified\" attributeFormDefault=\"unqualified\">\n" +
                    "    <xsd:complexType name=\"Name\">\n" +
                    "        <xsd:sequence>\n" +
                    "            <xsd:element name=\"vorname\" type=\"xsd:string\" />\n" +
                    "            <xsd:element name=\"nachname\" type=\"xsd:string\" />\n" +
                    "        </xsd:sequence>\n" +
                    "    </xsd:complexType>\n" +
                    "    <xsd:complexType name=\"PersonType\">\n" +
                    "        <xsd:sequence>\n" +
                    "            <xsd:element name=\"id\" type=\"xsd:string\" />\n" +
                    "            <xsd:element name=\"name\" type=\"Name\" />\n" +
                    "            <xsd:element name=\"geburtstag\" type=\"xsd:string\" />\n" +
                    "            <xsd:element name=\"beruf\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                    "        </xsd:sequence>\n" +
                    "    </xsd:complexType>\n" +
                    "    <xsd:element name=\"personen\">\n" +
                    "        <xsd:complexType>\n" +
                    "            <xsd:sequence>\n" +
                    "                <xsd:element name=\"person\" type=\"PersonType\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                    "            </xsd:sequence>\n" +
                    "        </xsd:complexType>\n" +
                    "        <xsd:unique name=\"uniquePersonID\">\n" +
                    "            <xsd:selector xpath=\"person\" />\n" +
                    "            <xsd:field xpath=\"id\" />\n" +
                    "        </xsd:unique>\n" +
                    "    </xsd:element>\n" +
                    "    <xsd:element name=\"person\" type=\"PersonType\" />\n" +
                    "</xsd:schema>\n";
            oFile.write(contant.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // load XML-Schema (e.g. "personen.xsd")
        schema = loadSchema(schema_path);

        // create marshalling helper instance
        marshalling = new Marshalling(schema);

        // create the unmarshaller and marshaller instance
        marshalling.createUnmarshaller(people.getClass());
        marshalling.createMarshaller(people.getClass());

        // perform unmarshalling (get data from the XML-Document)
        try {
            people = unmarshal(document);
        } catch (UnmarshalException e) {
            throw new RuntimeException("Unmarshalling XML-Document failed! (" + e.getCause().getMessage() + ")", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Loads and instantiates the projects XML Schema file
     *
     * @return reference to a Schema object
     */
    private static Schema loadSchema(String xsd_path) {
//        URL schemaFilePath = PersonenModel.class.getResource(xsd_path);
//        URL schemaFilePath = null;
//        try {
//            schemaFilePath = new URL(xsd_path);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        out.println(":" + schemaFilePath);
        try {
            return SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new File(xsd_path));
        } catch (SAXException ex) {
            throw new RuntimeException("Error during schema parsing!", ex);
        } catch (NullPointerException ex) {
            throw new RuntimeException("Could not load schema!", ex);
        }
    }

    /**
     * Get the according schema.
     */
    public Schema getSchema() {
        return schema;
    }


    /**
     * Get the marshalling helper object.
     */
    public Marshalling getMarshallingHelper() {
        return marshalling;
    }


    /**
     * Unmarshalling a collection by StreamSource.
     */
    public Personen unmarshal(StreamSource source) throws UnmarshalException, IOException {
        return (Personen) marshalling.getUnmarshaller()
                .unmarshal(source);


    }

    /**
     * Unmarshalling a collection by a given file.
     */
    public Personen unmarshal(File source) throws UnmarshalException, IOException {
        return unmarshal(new StreamSource(source));
    }


    /**
     * Unmarshalling of a person.
     */
    public PersonType unmarshalPerson(StreamSource source) {
        try {
            return (PersonType) marshalling.getUnmarshaller().unmarshal(source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Marshal a collection (XML-root element) into a writer.
     */
    public void marshal(Personen personen, PrintWriter writer)
            throws JAXBException, IOException {
        marshalling.getMarshaller().marshal((Object) personen, (Result) writer);
    }


    /**
     * Marshal single JAXB-Elements into a writer.
     */
    public void marshal(Object jaxbElement, PrintWriter writer)
            throws JAXBException, IOException {
        marshalling.getMarshaller().marshal(jaxbElement, (Result) writer);
    }

    /**
     * Marshal this model into a writer.
     */
    public void marshal(PrintWriter writer)
            throws JAXBException, IOException {
        marshal(people, writer);
    }

    /**
     * Marshal this model into the given XML-Document file.
     */
    public void marshal()
            throws JAXBException, IOException {
        marshal(new PrintWriter(document));
    }


    /**
     * Validate a new entry
     */
    public void validate(Object contentObject, Class<?> context_type)
            throws JAXBException {
        marshalling.validate(contentObject, context_type);
    }


    /**
     * Reference to the people
     */
    public Personen getPeople() {
        return people;
    }


    /**
     * Get a reference to the person with a specific id.
     *
     * @throws IndexOutOfBoundsException
     */
    public PersonType getPerson(String id)
            throws IndexOutOfBoundsException {

        for (PersonType person : getPeople().getPerson()) {
            if (person.getId().equals(id)) {
                return person;
            }
        }

        throw new IndexOutOfBoundsException("Person not found!");
    }


    /**
     * Get the index of the person with the id.
     */
    public int getPersonIndex(String id)
            throws IndexOutOfBoundsException {

        for (int i = 0; i < getPeople().getPerson().size(); i++) {
            if (getPeople().getPerson().get(i).getId().equals(id)) {
                return i;
            }
        }

        throw new IndexOutOfBoundsException("Person not found!");
    }


    /**
     * Tells if a person with that id exists.
     */
    public boolean existsPerson(String id) {
        try {
            getPerson(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Adds a new person
     */
    public void addPerson(String id, String name, String surname, String birthday, List<String> jobs) throws Exception {

        // use factory created by jaxb to create instances
        ObjectFactory factory = new ObjectFactory();
        PersonType person = factory.createPersonType();

        Name person_name = factory.createName();
        person_name.setVorname(name);
        person_name.setNachname(surname);

        person.setId(id);
        person.setName(person_name);
        person.setGeburtstag(birthday);

        // add all the jobs
        for (String job : jobs) {
            person.getBeruf().add(job);
        }

        // check that the new person object is valid against the schema
        JAXBElement<?> jaxbPerson = new ObjectFactory().createPerson(person);
        marshalling.validate(jaxbPerson, jaxbPerson.getClass());

        // add the person to the people
        people.getPerson().add(person);
    }

    /**
     * Adds a new person without a job
     */
    public void addPerson(String id, String name, String surname, String birthday) throws Exception {
        addPerson(id, name, surname, birthday, new ArrayList<String>());
    }

    /**
     * Adds a new person with unknown birthday and without a job
     */
    public void addPerson(String id, String name, String surname) throws Exception {
        addPerson(id, name, surname, "unknown");
    }

    /**
     * Adds a new person from a given instance
     */
    public void addPerson(PersonType person) throws Exception {
        addPerson(
                person.getId(),
                person.getName().getVorname(),
                person.getName().getNachname(),
                person.getGeburtstag(),
                person.getBeruf()
        );
    }


    /**
     * Replace a person by the given information.
     */
    public void replacePerson(String id, String name, String surname, String birthday, List<String> jobs) throws JAXBException {

        // get person by id
        PersonType person = getPerson(id);

        // create name object
        Name new_name = new ObjectFactory().createName();
        new_name.setVorname(name);
        new_name.setNachname(surname);

        // set person information
        person.setId(id);
        person.setName(new_name);
        person.setGeburtstag(birthday);

        // remove current jobs
        person.getBeruf().clear();
        person.getBeruf().addAll(jobs);

        // validate this person against the XML-Schema
        JAXBElement<?> jaxbPerson = new ObjectFactory().createPerson(person);
        marshalling.validate(jaxbPerson, jaxbPerson.getClass());
    }

    /**
     * Replace a person by another.
     */
    public void replacePerson(String id, PersonType newPerson) throws JAXBException {

        replacePerson(
                id,
                newPerson.getName().getVorname(),
                newPerson.getName().getNachname(),
                newPerson.getGeburtstag(),
                newPerson.getBeruf()
        );
    }


    /**
     * Replace the whole collection of people.
     */
    public void replaceCollection(Personen people) {

        // remove all previous people
        this.people.getPerson().clear();

        // add the new collection of people
        this.people.getPerson().addAll(people.getPerson());
    }


    /**
     * Deletes a person from the collection.
     *
     * @return A copy of the deleted person.
     * @throws IndexOutOfBoundsException if the person does not exist
     */
    public PersonType deletePerson(String id)
            throws IndexOutOfBoundsException {

        // get index and create a copy of the element
        int personIndex = getPersonIndex(id);
        PersonType person = getPeople().getPerson().get(personIndex);
        PersonType copy = copyItem(person);

        // remove from collection
        this.people.getPerson().remove(personIndex);

        return copy;
    }


    /**
     * Deletes the whole collection.
     *
     * @return A copy of the deleted collection.
     */
    public Personen deleteCollection() {

        Personen copy = copyCollection(getPeople());

        // remove all people
        getPeople().getPerson().clear();

        return copy;
    }


    /**
     * Creates a copy of a person.
     */
    public PersonType copyItem(PersonType person) {

        PersonType copy = new ObjectFactory().createPersonType();

        Name name = new ObjectFactory().createName();
        name.setVorname(person.getName().getVorname());
        name.setNachname(person.getName().getNachname());

        copy.setId(person.getId());
        copy.setGeburtstag(person.getGeburtstag());
        copy.setName(name);

        return copy;
    }


    /**
     * Create a copy of the whole collection.
     */
    public Personen copyCollection(Personen people) {

        Personen copy = new ObjectFactory().createPersonen();

        // copy all the people
        for (PersonType person : people.getPerson()) {
            copy.getPerson().add(copyItem(person));
        }

        return copy;
    }
}
