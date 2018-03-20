package cn.javaEE.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Test
	protected void fun(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		System.out.println(this.getServletContext().getRealPath("/WebInf/"));
	}

}
