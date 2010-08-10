package com.nacre.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.servlet.context.ServletUtil;
import org.xml.sax.SAXException;

import com.nacre.service.FormFactory;
import com.nacre.service.vo.FormVO;

public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter().write("Hello");
		String resourceName = "/test.xsd";
		FormFactory ff;
		try {
			ff = new FormFactory(this.getClass().getResource(resourceName));
			FormVO form = ff.getComplexType(request.getParameter("type"));
			response.getWriter().write(form.toString());
		} catch (SAXException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		TilesContainer container = ServletUtil.getContainer(
		        request.getSession().getServletContext());
		container.render("nacre.doodad", request, response);
	}
}
