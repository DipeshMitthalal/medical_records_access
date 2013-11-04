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
import javax.print.Doc;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "nurseRecords", urlPatterns = {"/nurseRecords"})
public class nurseRecords extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "mydb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";
        
            Class.forName(driver).newInstance();
            //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(url + dbName, userName, password);
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);
            ArrayList al = null;
            ArrayList userList = new ArrayList();
            String otherWards = null;
            String nurseID = (String) session.getAttribute("username");
            String role = (String) session.getAttribute("role");
            String query = "select * from nurses where user_id ='" + nurseID + "'";
            System.out.println("query " + query);
            st = conn.createStatement();
            rs = st.executeQuery(query);
            int nurse_ward = 0;
            String doctorID = null;
            while (rs.next()) {
                nurse_ward = (rs.getInt(8));
                String addPatients = (rs.getString(10));
                session.setAttribute("can_update", rs.getString(11));
                session.setAttribute("can_add", rs.getString(10));
                String update = rs.getString(11);
                otherWards = rs.getString(12);
                doctorID = rs.getString(9);
            }

            query = "select DISTINCT doctorid from delegation,rules where delegation.policyID = rules.rulename AND rules.nurses_privilege = 'Y' AND delegation.doctorid ='" + doctorID + "'";
            System.out.println("query " + query);
            st = conn.createStatement();
            rs = st.executeQuery(query);
            char flag = 'N';
            while (rs.next()) {
                // count++;
                flag = 'Y';
                System.out.println("in while");
            }
            if (flag == 'Y') {
                if (otherWards.equals("Y")) {
                    // query = "select * from patient_medication_history order by patient_id ";
                    query = "select * from patient_medication_history where patient_id IN (select idPatientInfo from patientinfo,nurses,physician where patientinfo.physician_ID =nurses.assigned_to_doc  AND idphysician=nurses.assigned_to_doc AND PHYSICIAN.delete_flag ='N'  AND nurses.user_id='" + nurseID + "')";

                } else {
                    query = "select * from patient_medication_history where patient_id IN (select idPatientInfo from patientinfo,nurses,physician where patientinfo.physician_ID =nurses.assigned_to_doc AND idphysician=nurses.assigned_to_doc AND PHYSICIAN.delete_flag ='N' AND nurses.user_id='" + nurseID + "' AND ward_no ='" + nurse_ward + "')";
                    //query = "SELECT * FROM patient_medication_history where patient_id in (select distinct idpatientInfo from patientinfo where ward_no ='" + nurse_ward + "')";
                }
                System.out.println("query :::" + query);
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
                    al.add(rs.getString(7));
                    al.add(rs.getString(8));
                    System.out.println("al :: " + al);
                    userList.add(al);
                }
                request.setAttribute("userList", userList);

                String nextJSP = "/patientMedicationHistory.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                dispatcher.forward(request, response);
                conn.close();
            }else{
                out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet nurseRecords</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Your doctor is been revoked priviliges.please contact admin</h1>");
         out.println("<a href=\"logout.jsp\"><b>Log Out</b></a>");
        out.println("</body>");
        out.println("</html>");

        out.close();

        }
    }
        
            /* TODO output your page here. You may use following sample code. */
        

    

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
        



} catch (SQLException ex) {
            Logger.getLogger(nurseRecords.class  

.getName()).log(Level.SEVERE, null, ex);
        } 



catch (ClassNotFoundException ex) {
            Logger.getLogger(nurseRecords.class  

.getName()).log(Level.SEVERE, null, ex);
        } 



catch (InstantiationException ex) {
            Logger.getLogger(nurseRecords.class  

.getName()).log(Level.SEVERE, null, ex);
        } 



catch (IllegalAccessException ex) {
            Logger.getLogger(nurseRecords.class  

.getName()).log(Level.SEVERE, null, ex);
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
        



} catch (SQLException ex) {
            Logger.getLogger(nurseRecords.class  

.getName()).log(Level.SEVERE, null, ex);
        } 



catch (ClassNotFoundException ex) {
            Logger.getLogger(nurseRecords.class  

.getName()).log(Level.SEVERE, null, ex);
        } 



catch (InstantiationException ex) {
            Logger.getLogger(nurseRecords.class  

.getName()).log(Level.SEVERE, null, ex);
        } 



catch (IllegalAccessException ex) {
            Logger.getLogger(nurseRecords.class  

.getName()).log(Level.SEVERE, null, ex);
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
