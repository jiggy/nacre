package com.nacre.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.nacre.service.FormFactory;
import com.nacre.service.vo.FormVO;

public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String XSD = "xsd";

	private FormFactory formFactory = null;
	protected transient final Log log = LogFactory.getLog(this.getClass());

	@Override
	public void init() throws ServletException {
		log.error("test!!!");
		try {
			formFactory = new FormFactory(this.getClass().getResource(this.getInitParameter(XSD)));
		} catch (SAXException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		super.init();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		FormVO form = formFactory.getComplexType(request.getParameter("type"));
		request.setAttribute("form", form);
		request.getRequestDispatcher("/layouts/nacre.jsp").include(request, response);
	}
}
