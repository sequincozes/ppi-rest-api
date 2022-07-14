package com.example.aula_ppi.api;

import com.example.aula_ppi.helpers.PersonenModel;
import com.example.aula_ppi.jaxb.ObjectFactory;
import com.example.aula_ppi.jaxb.PersonType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * GET-Request Handler<br/>
 * 1. List the URIs and perhaps other details of the collection's members.<br/>
 * 2. Retrieve a representation of the addressed member of the collection
 */
public class GetRequestHandler extends RequestHandler {

	
	public GetRequestHandler(PersonenModel model) {
		super(model);
	}

	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response, String id)
	throws ServletException, IOException {
		
		// get writer and set the content type of the response to be XML
		PrintWriter responseWriter = response.getWriter();
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		responseWriter.println("Person:");
		getModel().getThatObject();

	}

}
