package com.mapr.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/test" })
public class TestServlet extends HttpServlet
{

  @Resource(lookup = "java:/datasources/DRILL-DS1")
  DataSource ds;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

    try {
      Connection con = ds.getConnection();

      Statement stmt = con.createStatement();
      String query = "SELECT * FROM cp.`employee.json` LIMIT 20";
      ResultSet rs = stmt.executeQuery(query);

      PrintWriter out = response.getWriter();
      response.setContentType("text/html");

      out.print("<html><body><table>");
      while (rs.next()) {
        out.print("<tr>");
        out.print("<td>" + rs.getString(1) + "</td>");
        out.print("<td>" + rs.getString(2) + "</td>");
        out.print("<td>" + rs.getString(3) + "</td>");
        out.print("<td>" + rs.getString(4) + "</td>");
        out.print("</tr>");
      }
      out.print("</table></body></html>");
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }
}