/*
 * Copyright (c) 2021 Tander, All Rights Reserved.
 */

package servlets;

/**
 * Класс RegisterServlet
 */


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet(name = "RegisterServlet", urlPatterns = { "/registerServlet" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(RegisterServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String country = request.getParameter("country");
		String errorMsg = null;
		if(email == null || email.equals("")){
			errorMsg = "Email ID can't be null or empty.";
		}
		if(password == null || password.equals("")){
			errorMsg = "Password can't be null or empty.";
		}
		if(name == null || name.equals("")){
			errorMsg = "Name can't be null or empty.";
		}
		if(country == null || country.equals("")){
			errorMsg = "Country can't be null or empty.";
		}

		if(errorMsg != null){
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>"+errorMsg+"</font>");
			rd.include(request, response);
		}else{

			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement("insert into Users(name,email,country, password) values (?,?,?,?)");
				ps.setString(1, name);
				ps.setString(2, email);
				ps.setString(3, country);
				ps.setString(4, password);

				ps.execute();

				logger.info("User registered with email="+email);

				//forward to login page to login
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
				PrintWriter out= response.getWriter();
				out.println("<font color=green>Registration successful, please login below.</font>");
				rd.include(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("Database connection problem");
				throw new ServletException("DB Connection problem.");
			}finally{
				try {
					ps.close();
				} catch (SQLException e) {
					logger.error("SQLException in closing PreparedStatement");
				}
			}
		}

	}

}
