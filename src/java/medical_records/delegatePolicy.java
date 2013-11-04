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
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dipesh
 */
public class delegatePolicy extends HttpServlet {

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
        System.out.println("MySQL Connect Example.");
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "mydb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";
        String languages = " ";
        String[] lang = request.getParameterValues("policyID");
        String docID = request.getParameter("doctorID");
        if (lang != null) {
            int size = 0;
            try {

                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
                String query1 = "delete from delegation  where doctorid='" + docID + "'";
                Statement stmt;
                stmt = conn.createStatement();
                stmt.executeUpdate(query1);
                size = lang.length;
                for (int i = 0; i < lang.length; i++) {
                    languages += lang[i] + " ";

                    String query = " INSERT INTO delegation( ";
                    query = query + "doctorid , policyID)";
                    query = query + "VALUES(?,?)";
                    System.out.println(query);
                    PreparedStatement prepStmt = conn.prepareStatement(query);
                    prepStmt.setString(1, docID);
                    prepStmt.setString(2, lang[i]);
                    int out1 = prepStmt.executeUpdate();
                    HttpSession session = request.getSession(true);
                    session.setAttribute("success", "delegation updated for Dr." + docID);
                    response.sendRedirect("delegatePolicy.jsp");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                /* TODO output your page here. You may use following sample code. */
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet delegatePolicy</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet delegatePolicy at " + docID + size + languages + request.getContextPath() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
            }
        } else {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet delegatePolicy</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>you have not selected any policy for doctor</h1>");
            out.println("<a href=\"delegatePolicy.jsp\"><b>Go back</b></a>");
            out.println("</body>");
            out.println("</html>");
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
