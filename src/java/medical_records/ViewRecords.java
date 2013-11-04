/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package medical_records;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.Session;

/**
 *
 * @author Dipesh
 */
public class ViewRecords extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
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
        String allergiesOwnPatients = null;
        String allergiesOwnSpeciality = null;
        String givePrivilegesToNurse = null;
        Statement st = null;
        ResultSet rs = null;
        String role = null;

        try {
            Class.forName(driver).newInstance();
            //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(url + dbName, userName, password);
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);

            role = (String) session.getAttribute("role");
            String phy_speciality = (String) session.getAttribute("doc_department");

            String name = (String) session.getAttribute("username");
            String policyName = request.getParameter("policyApplied");
            //String name = (String) session.setAttribute("username");
            String rule = "select * from rules where role_applicable_to='" + role + "' AND rulename='" + policyName + "'";

            System.out.println("policy name:" + policyName);
            //strQuery = "select * from physician,patientinfo where   idphysician='" + username + "'";
            System.out.println(rule);
            st = conn.createStatement();
            rs = st.executeQuery(rule);
            System.out.println(rs.toString());
            String condition = "";
            allergiesOwnPatients = "N";
            allergiesOwnSpeciality = "N";
            givePrivilegesToNurse = "N";
            int count = 0;
            while (rs.next()) {
                // al = new ArrayList();
                count++;
                if (count > 1) {
                    condition = condition + " OR ";
                }
                condition = condition + rs.getString(3);
                allergiesOwnPatients = rs.getString(5);
                allergiesOwnSpeciality = rs.getString(6);
                givePrivilegesToNurse = rs.getString(7);
            }
            session.setAttribute("givePrivilegesToNurse", givePrivilegesToNurse);
            System.out.println("condition :: " + condition);


            System.out.println("name::" + name);
            String doc_dept = phy_speciality;
            phy_speciality = "'" + phy_speciality + "'";
            name = "'" + name + "'";
            ArrayList al = null;
            ArrayList userList = new ArrayList();

            if (condition.contains("physician.speciality")) {
                condition = condition.replace("physician.speciality", phy_speciality);
                //condition.re
            }
            if (condition.contains("physician.idphysician")) {
                System.out.println("In contains");
                condition = condition.replace("physician.idphysician", name);
                //condition.re
            }
            strQuery = "select * from patientinfo";
            if (count >= 1) {
                strQuery = strQuery + " WHERE " + condition;
            }
            System.out.println(strQuery);
            st = conn.createStatement();
            rs = st.executeQuery(strQuery);

            System.out.println(rs.toString());
            // String allergy_dep;
            
            while (rs.next()) {
                boolean temp = false;
                al = new ArrayList();

                al.add(rs.getString(1));
                al.add(rs.getString(2));
                al.add(rs.getString(3));
                al.add(rs.getString(12));
                String allergy_dep = rs.getString(13);
                System.out.println("allergy dept" + allergy_dep);
                System.out.println("doc dep" + doc_dept);
                System.out.println("allergy1" + allergiesOwnPatients);
                System.out.println("allergy1" + allergiesOwnSpeciality);
               if(allergiesOwnPatients!= null){
                if (allergiesOwnPatients.equals("Y")) {
                    al.add(rs.getString(8));
                    temp = true;
                    System.out.println("allergy added loop 1");
                }
               }
                if (allergiesOwnSpeciality.equals("Y") && allergy_dep.equalsIgnoreCase(doc_dept.trim())) {
                    al.add(rs.getString(8));
                    System.out.println("allergy added loop 2");
                } else {
                    if (!temp) {
                        al.add("restricted".toString());
                    }
                }

                System.out.println("al :: " + al);
                userList.add(al);
            }

            request.setAttribute("userList", userList);
            String nextJSP = "/viewRecords.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request, response);
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewRecords</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewRecords at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewRecords.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ViewRecords.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ViewRecords.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ViewRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewRecords.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ViewRecords.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ViewRecords.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ViewRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
