package medical_records;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "UpdateEditedRecords", urlPatterns = {"/UpdateEditedRecords"})
public class UpdateEditedRecords extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        System.out.println("MySQL Connect Example.");
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "mydb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";

        out.println(request.getRequestURI());

        if (request.getParameter("id") != null && request.getParameter("id") != "") {

            int RecordID = Integer.parseInt(request.getParameter("RecordID").toString());
            String drugsPrescribed = request.getParameter("drugsPrescribed").toString();
            String RBC = request.getParameter("RBC").toString();
            String sugar = request.getParameter("sugar").toString();
            String glucose = request.getParameter("glucose").toString();
            String patID = request.getParameter("patID").toString();
            String prescribedby = request.getParameter("prescribedby").toString();
            //String country = request.getParameter("country").toString();


            Statement stmt;
            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
                System.out.println("Connected to the database");
                
                ArrayList al = null;
                ArrayList userList = new ArrayList();
                String query = " UPDATE patient_medication_history ";
                query = query + "SET drugs_consumed = ?, ";
                query = query + "RBC = ?, sugar = ?, glucose_levels = ?, patient_Id = ?,";
                query = query + "prescribedby = ? WHERE idpatient_medication_history = ?;";
System.out.println(query);
                PreparedStatement prepStmt = conn.prepareStatement(query);
prepStmt.setString(1, drugsPrescribed);
prepStmt.setString(2, RBC);
prepStmt.setString(3,sugar);
prepStmt.setString(4, glucose);
prepStmt.setString(5, patID);
prepStmt.setString(6, prescribedby);
prepStmt.setInt(7, RecordID);


                int i = prepStmt.executeUpdate();
                System.out.println("query" + query);
                if (i > 0) {
                    response.sendRedirect("patientMedicalHistory/"+patID);
                }
                conn.close();
                System.out.println("Disconnected from database");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}