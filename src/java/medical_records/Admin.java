/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package medical_records;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dipesh
 */
@WebServlet(name = "Admin", urlPatterns = {"/Admin"})
public class Admin extends HttpServlet {

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
    Connection conn = null;
    PreparedStatement statement;
    Statement st = null;
    ResultSet rs = null;
    String url = "jdbc:mysql://localhost:3306/";
    String dbName = "mydb";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "root";
    String password = "root";
    private static final String SQL_INSERT = "INSERT INTO RULES"
            + " VALUES(?, ?, ?,?,?,?,?)";
    private static final String SQL_INSERT1 = "INSERT INTO RULES_MOD"
            + " VALUES(?, ?, ?,?,?,?,?)";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Class.forName(driver).newInstance();
            //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(url + dbName, userName, password);
            /* TODO output your page here. You may use following sample code. */

            String rulename = request.getParameter("rulename");
            String onlyhisdepartment = request.getParameter("onlyhisdepartment");
            String alldepartments = request.getParameter("alldepartments");
            String onlyhispatients = request.getParameter("onlyhispatients");
            String patientsIdentity = request.getParameter("patientsIdentity");
            String nursePrivilege = request.getParameter("nursePrivilege");
            String Allergies = request.getParameter("Allergies");
            String oldpolicy = request.getParameter("oldpolicy");
            //System.out.println("old policy:"+oldpolicy);
            String allergiesOwnPatients = "N";
            String allergiesOwnSpeciality = "N";
            if (Allergies.equalsIgnoreCase("allergiesOwnPatients")) {
                allergiesOwnPatients = "Y";
            }
            if (Allergies.equalsIgnoreCase("allergiesOwnSpeciality")) {
                allergiesOwnSpeciality = "Y";
            }

            System.out.println("Aller::" + Allergies);
            String rule = "";
            HttpSession session = request.getSession(true);
            String phy_speciality = (String) session.getAttribute("doc_department");
            String role = (String) session.getAttribute("role");
            if (!(alldepartments == null)) {
                if (alldepartments.equalsIgnoreCase("Y")) {
                    rule = "patientinfo.department IN (SELECT name FROM departments_in_hospital)";
                }
            } else if (!(onlyhisdepartment == null)) {
                if (onlyhisdepartment.equalsIgnoreCase("Y")) {
                    rule = "patientinfo.department = physician.speciality";
                }
            } else if (!(onlyhispatients == null)) {
                if (onlyhispatients.equalsIgnoreCase("Y")) {
                    rule = "patientinfo.physician_ID = physician.idphysician";
                }
            }


            if (oldpolicy != null) {
                String strQuery = "select * from rules where    rulename='" + oldpolicy + "'";
                System.out.println(strQuery);
                st = conn.createStatement();
                rs = st.executeQuery(strQuery);
                System.out.println(rs.toString());
                int count = 0;
                while (rs.next()) {
                    count++;
                }
                if (count > 0) {
                    String query = "delete from rules where    rulename='" + oldpolicy + "'";
                    st = conn.createStatement();
                    st.executeUpdate(query);
                    query = "delete from rules_mod where    rule_name='" + oldpolicy + "'";
                    st = conn.createStatement();
                    st.executeUpdate(query);
                }
            }
            statement = conn.prepareStatement(SQL_INSERT);
            statement.setString(1, rulename);
            statement.setString(2, "doctor");
            statement.setString(3, rule);
            statement.setString(4, "department");
            statement.setString(5, allergiesOwnPatients);
            statement.setString(6, allergiesOwnSpeciality);
            statement.setString(7, nursePrivilege);
            statement.executeUpdate();
            if (onlyhisdepartment == null) {
                onlyhisdepartment = "N";
            }
            if (alldepartments == null) {
                alldepartments = "N";
            }
            if (onlyhispatients == null) {
                onlyhispatients = "N";
            }
            if (nursePrivilege == null) {
                nursePrivilege = "N";
            }
            if (patientsIdentity == null) {
                patientsIdentity = "N";
            }

            statement = conn.prepareStatement(SQL_INSERT1);
            statement.setString(1, rulename);
            statement.setString(2, onlyhisdepartment);
            statement.setString(3, alldepartments);
            statement.setString(4, onlyhispatients);
            statement.setString(5, nursePrivilege);
            statement.setString(6, patientsIdentity);
            statement.setString(7, Allergies);
            statement.executeUpdate();
response.sendRedirect("admin.jsp");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet dipesh</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>New policy created/updated for doctor with the name of:" + request.getParameter("rulename") + "</h1>");
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
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
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
