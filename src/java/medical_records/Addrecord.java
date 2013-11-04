package medical_records;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Addrecord", urlPatterns = {"/Addrecord"})
public class Addrecord extends HttpServlet {
private static java.sql.Date getCurrentDate() {
    java.util.Date today = new java.util.Date();
    return new java.sql.Date(today.getTime());
}
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

            //int RecordID = Integer.parseInt(request.getParameter("RecordID").toString());
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
                java.sql.Date d;
                
                ArrayList al = null;
                ArrayList userList = new ArrayList();
                String query = " INSERT INTO patient_medication_history( ";
                query = query + "drugs_consumed , ";
                query = query + "RBC, sugar, glucose_levels , patient_Id ,record_creattion_date, ";
                query = query + "prescribedby) VALUES(?,?,?,?,?,?,?)";
System.out.println(query);
System.out.println("date"+getCurrentDate());
                PreparedStatement prepStmt = conn.prepareStatement(query);
prepStmt.setString(1, drugsPrescribed);
prepStmt.setString(2, RBC);
prepStmt.setString(3,sugar);
prepStmt.setString(4, glucose);
prepStmt.setString(5, patID);
prepStmt.setString(7, prescribedby);
prepStmt.setDate(6,getCurrentDate() );



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
