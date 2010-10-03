package com.nacre.servlet;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import com.nacre.service.ParsingUtils;

public class XMLPostServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(204);
		SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		sf.setErrorHandler(ParsingUtils.nacreXSOMErrorHandler);
		try {
			Schema s = sf.newSchema(this.getClass().getResource(this.getInitParameter("xsd")));
			Validator v = s.newValidator();
			v.validate(new StreamSource(new StringReader(req.getParameter("xml"))));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(req.getParameter("xml"));
		// TODO, validate against schema and send to adapter for saving
	}
}
