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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;


/**
 * Provides an interface to the "personen" document.
 */
public class PersonenModel {


    public PersonenModel(String s, String s1) {
    }

    public void getThatObject() {
    }
}
