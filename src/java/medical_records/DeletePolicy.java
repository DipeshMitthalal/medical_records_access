/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package medical_records;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;

public class DeletePolicy extends HttpServlet{ 
 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 response.setContentType("text/html");
			PrintWriter out = response.getWriter();
                          HttpSession session = request.getSession(true);
 System.out.println("policy to be deleted:"+request.getParameter("policy_ID"));
			System.out.println("MySQL Connect Example.");
			Connection conn = null;
			String url = "jdbc:mysql://localhost:3306/";
			String dbName = "mydb";
			String driver = "com.mysql.jdbc.Driver";
			String userName = "root"; 
			String password = "root";


			String id = request.getParameter("policy_ID");
					
			Statement stmt;
			try {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url+dbName,userName,password);
				System.out.println("Connected to the database");
				
				ArrayList al=null;
				ArrayList userList =new ArrayList();
				String query = "delete from rules_mod  where rule_name='"+id+"'";
				stmt = conn.createStatement();
            
			    int i = stmt.executeUpdate(query);
				System.out.println("query" + query);
                                 query = "delete from rules  where rulename='"+id+"'";
				stmt = conn.createStatement();
            
			     i = stmt.executeUpdate(query);
                                
                                
                                
                                
				if(i>0)
				{
					response.sendRedirect("admin.jsp");
				}
				conn.close();
				System.out.println("Disconnected from database");
			} catch (Exception e) {
			e.printStackTrace();
			}
		
 }
   public void doGet(HttpServletRequest request, HttpServletResponse response)
                                   throws ServletException,IOException{
			
	  }
}