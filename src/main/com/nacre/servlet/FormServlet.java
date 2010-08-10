package com.nacre.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.servlet.context.ServletUtil;
import org.xml.sax.SAXException;

import com.nacre.service.FormFactory;
import com.nacre.service.vo.FormVO;

public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String XSD = "xsd";

	private FormFactory formFactory = null;

	@Override
	public void init() throws ServletException {
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
		response.getWriter().write("Hello");
		FormVO form = formFactory.getComplexType(request.getParameter("type"));
		response.getWriter().write(form.toString());
		TilesContainer container = ServletUtil.getContainer(
		        request.getSession().getServletContext());
		AttributeContext ctx = container.startContext(request, response);
		ctx.putAttribute("form", new Attribute(form));
		container.render("nacre.doodad", request, response);
		container.endContext(request, response);
	}
}
