package medical_records;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
@WebServlet(name = "DeleteData", urlPatterns = {"/DeleteData"})
public class DeleteData extends HttpServlet{ 
 
   public void doGet(HttpServletRequest request, HttpServletResponse response)
                                   throws ServletException,IOException{
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			System.out.println("MySQL Connect Example.");
			Connection conn = null;
			String url = "jdbc:mysql://localhost:3306/";
			String dbName = "mydb";
			String driver = "com.mysql.jdbc.Driver";
			String userName = "root"; 
			String password = "root";

			String str[] = request.getRequestURI().toString().split("/");

			int id = Integer.parseInt(str[3]);
					
			Statement stmt;
			try {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url+dbName,userName,password);
				System.out.println("Connected to the database");
				
				ArrayList al=null;
				ArrayList userList =new ArrayList();
				String query = "delete from patient_medication_history  where idpatient_medication_history="+id;
				stmt = conn.createStatement();
            
			    int i = stmt.executeUpdate(query);
				System.out.println("query" + query);
				if(i>0)
				{
					response.sendRedirect("../patientMedicalHistory");
				}
				conn.close();
				System.out.println("Disconnected from database");
			} catch (Exception e) {
			e.printStackTrace();
			}
		
	  }
}