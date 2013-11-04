/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package medical_records;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dipesh
 */
@WebServlet(name = "nursePrivilege", urlPatterns = {"/nursePrivilege"})
public class nursePrivilege extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String addPatients = null;

        System.out.println("MySQL Connect Example.");
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "mydb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";

        out.println(request.getRequestURI());


        if (request.getParameter("nurse_ID") != null && request.getParameter("nurse_ID") != "") {

            String id = request.getParameter("nurse_ID");
            addPatients = request.getParameter("addPatients");
            if (addPatients == null) {
                addPatients = "N";
            }
            String upd_Patient_Medication = request.getParameter("upd_Patient_Medication");
            if (upd_Patient_Medication == null) {
                upd_Patient_Medication = "N";
            }
            String patients_from_other_wards = request.getParameter("patients_from_other_wards");
            if (patients_from_other_wards == null) {
                patients_from_other_wards = "N";
            }


            Statement stmt;
            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
                System.out.println("Connected to the database");

                ArrayList al = null;
                ArrayList userList = new ArrayList();
                String query = "update nurses set addPatients='" + addPatients + "',upd_Patient_Medication='" + upd_Patient_Medication + "',patients_from_other_wards='" + patients_from_other_wards + "' where user_id='" + id + "'";
                stmt = conn.createStatement();

                int i = stmt.executeUpdate(query);
                System.out.println("query" + query);
                if (i > 0) {
                    String nextJSP = "/doctor.jsp";
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                    dispatcher.forward(request, response);
                    //  response.sendRedirect("ViewRecords");
                }
                conn.close();
                System.out.println("Disconnected from database");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet nursePrivilege</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet nursePrivilege at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
