/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package medical_records;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.driver.*;
/**
 *
 * @author Dipesh
 */
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        System.out.println("MySQL Connect Example.");
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        //String url = "jdbc:oracle:thin:@localhost:1521";
        String dbName = "mydb";
        String driver = "com.mysql.jdbc.Driver";
        // String driver = "oracle.jdbc.driver.OracleDriver";
//        String userName = "system";
//        String password = "dipesh";
        String userName = "root";
        String password = "root";
        String username = "";
        String userpass = "";
        String strQuery = "";
        Statement st = null;
        ResultSet rs = null;
        String role = null;
        HttpSession session = request.getSession(true);
        try {
            Class.forName(driver).newInstance();
            //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(url + dbName, userName, password);
            if (request.getParameter("username") != null
                    && request.getParameter("username") != "" && request.getParameter("password") != null
                    && request.getParameter("password") != "") {
                username = request.getParameter("username").toString();
                userpass = request.getParameter("password").toString();
                String selectStatement = "SELECT * FROM Users_list WHERE system_user_id = ? and password = ?";
                PreparedStatement prepStmt = conn.prepareStatement(selectStatement);
                prepStmt.setString(1, username);
                prepStmt.setString(2, userpass);
                rs = prepStmt.executeQuery();



                System.out.println("record set:" + rs.toString());
                int count = 0;
                while (rs.next()) {
                    session.setAttribute("username", rs.getString(2));
                    session.setAttribute("role", rs.getString(4));
                    //  request.setAttribute("user", rs.getString(2));
                    role = rs.getString(4);
                    count++;
                }
                if (count > 0) {
                    if (role.equalsIgnoreCase("admin")) {
                        response.sendRedirect("admin.jsp");
                    }

                   
                     if (role.equalsIgnoreCase("nurse")) {
                        ServletContext servletContext = getServletContext();
                        RequestDispatcher requestDispatcher = servletContext
                                .getRequestDispatcher("/nurseRecords");
                        requestDispatcher.forward(request, response);
                    }
                      if (role.equalsIgnoreCase("patient")) {
                        ServletContext servletContext = getServletContext();
                        RequestDispatcher requestDispatcher = servletContext
                                .getRequestDispatcher("/patientMedicalHistory");
                        requestDispatcher.forward(request, response);
                    }
                    if (role.equalsIgnoreCase("doctor")) {

                        ArrayList al = null;
                        ArrayList userList = new ArrayList();
                        strQuery = "select * from physician where   idphysician='" + username + "'";
                        System.out.println(strQuery);
                        st = conn.createStatement();
                        rs = st.executeQuery(strQuery);
                        System.out.println(rs.toString());
                        while (rs.next()) {
                            al = new ArrayList();

                            al.add(rs.getString(1));
                            al.add(rs.getString(2));
                            al.add(rs.getString(3));
                            session.setAttribute("doc_department", rs.getString(4));
                            session.setAttribute("doc_experience", rs.getFloat(5));


                            al.add(rs.getString(6));
                            al.add(rs.getString(7));

                            System.out.println("al :: " + al);
                            userList.add(al);
                        }

                        request.setAttribute("userList", userList);
                        response.sendRedirect("doctor.jsp");
                    }

                } else {

                    response.sendRedirect("index_1.jsp");

                }
            } else {
                response.sendRedirect("index.jsp");
            }
            System.out.println("Connected to the database");
            conn.close();
            System.out.println("Disconnected from database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
