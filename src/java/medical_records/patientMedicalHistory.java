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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.Session;

/**
 *
 * @author Dipesh
 */
@WebServlet(name = "patientMedicalHistory", urlPatterns = {"/patientMedicalHistory"})
public class patientMedicalHistory extends HttpServlet {

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
        Statement st = null;
        ResultSet rs = null;
        String role = null;
// System.out.println(request.getRequestURI().toString());
        //      String str[] = request.getRequestURI().toString().split("/");

        String tempID = null;
        System.out.println("hello:" + request.getRequestURI().toString());
        String str[] = request.getRequestURI().toString().split("/");
        if (str.length >= 4) {
            tempID = str[3];
        }


        try {
            Class.forName(driver).newInstance();
            //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(url + dbName, userName, password);
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);

            role = (String) session.getAttribute("role");
            String phy_speciality = (String) session.getAttribute("doc_department");
            String patientID = request.getParameter("hiddenValue");
            String name =  (String) session.getAttribute("username");
            if (patientID == null) {
                patientID = tempID;
            }
            if (role != null) {
                if (role.equalsIgnoreCase("patient")) {
                    patientID = (String) session.getAttribute("username");
                    System.out.println("hello patient"+patientID);
                }
            session.setAttribute("pat1", patientID);
            request.setAttribute("pat", patientID);
            ArrayList al = null;
            ArrayList userList = new ArrayList();
            String query = "select * from patient_medication_history where patient_id ='" + patientID + "'";
            System.out.println("query " + query);
            st = conn.createStatement();
            rs = st.executeQuery(query);


            while (rs.next()) {
                al = new ArrayList();

                al.add(rs.getInt(1));
                al.add(rs.getString(2));
                al.add(rs.getDate(3));
                al.add(rs.getString(4));
                al.add(rs.getString(5));
                al.add(rs.getString(6));
                // al.add(rs.getString(7));
                al.add(rs.getString(8));
                System.out.println("al :: " + al);
                userList.add(al);
            }

            request.setAttribute("userList", userList);

            String nextJSP = "/patientMedicationHistory.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request, response);
            conn.close();
} else {
                patientID = null;
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ViewRecords</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1> you hacker please login</h1>");
                out.println("</body>");
                out.println("</html>");
            }
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
//             System.out.println("hello:"+request.getRequestURI().toString());
//       String str[] = request.getRequestURI().toString().split("/");
//        System.out.println("patientID"+str[3]);
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(patientMedicalHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(patientMedicalHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(patientMedicalHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(patientMedicalHistory.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(patientMedicalHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(patientMedicalHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(patientMedicalHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(patientMedicalHistory.class.getName()).log(Level.SEVERE, null, ex);
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
