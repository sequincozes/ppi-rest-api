package com.example.aula_ppi.api;

import com.example.aula_ppi.helpers.PersonenModel;
import com.example.aula_ppi.jaxb.PersonType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;


/**
 * POST-Request Handler<br/>
 * 1. Create a new entry in the collection.
 */
public class PostRequestHandler extends RequestHandler {


    public PostRequestHandler(PersonenModel model) {
        super(model);
    }


    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, String id)
            throws ServletException, IOException {

        // get writer and set the content type of the response to be XML
        PrintWriter responseWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        responseWriter.println(
                request.getReader().lines().collect(Collectors.joining())
        );
        return;

//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			responseWriter.println(failureMessage(e.getMessage()));
    }


    /**
     * Add a new person to the XML-Document.
     */
    private void addPerson(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // add the person to the collection
        String id = addPerson(null, request.getReader());

        // marshal the XML-Document (save changes to the file)
        try {
//			getModel().marshal();
        } catch (Exception e) {
            throw new Exception("Marshalling failed. (" + e.getMessage() + ")");
        }

        // print information about added person
        System.out.println("Person added (" + id + ").");

        // redirect and change method to GET
        //response.setStatus(HttpServletResponse.SC_SEE_OTHER);
        //response.setHeader("Location", address);

        // TODO: works for now, maybe change it to a redirect later
        new GetRequestHandler(getModel()).handleRequest(request, response, id);
    }


    /**
     * Add a new person to the collection using the passed body data.<br/>
     * (Will not write changes to the XML-Document)
     *
     * @param id - can be null
     * @return The id of the added person
     */
    public String addPerson(String id, BufferedReader data)
            throws Exception {

        // check if the data is available (not empty)
//		if (!data.ready()) {
//			throw new IOException("Missing body data!");
//		}

        // try to unmarshal to a Person object
//		PersonType newPerson = getModel().unmarshalPerson(new StreamSource(data));

//		// check that IDs match if the id is given
//		if (id != null && !newPerson.getId().equals(id)) {
//			throw new Exception("IDs do not match!");
//		}
//
//		// add person to collection
//		getModel().addPerson(newPerson);
//
        return "idOfPerson";//newPerson.getId();
    }
}
