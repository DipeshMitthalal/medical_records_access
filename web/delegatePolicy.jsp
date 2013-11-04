<%-- 
    Document   : admin
    Created on : Nov 24, 2012, 3:44:15 PM
    Author     : Dipesh
--%>


<%@page import="java.io.PrintWriter"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./default.css" />
        <title>Policy Delegation Page</title>
        
    </head>
    <body>
        <%
            if (session.getAttribute("username") != null && session.getAttribute("username") != "" && session.getAttribute("role").equals("admin")) {
                String user = session.getAttribute("username").toString();

        %>
        
        <b> Welcome Admin </b>
        <br>
            you can delegate policies to doctors here
        <SCRIPT language="javascript">
            
            function view(){
                var sel = document.forms["in"].rule;  
                var opt = sel.options[sel.selectedIndex].value;
                //    alert(opt);
                document.forms["in"].policyApplied.value = opt; 
                // alert(document.forms["in"].policyApplied.value);
                // alert(te);
                // document.write('<input type="hidden" name="temp" value=id />');
                //document.write("<h1>Hello World!</h1><p>Have a nice day!</p>")
                //  window.location.href="patientMedicationHistory/"+id; 
                // document.forms["in"].submit();
            }
        </SCRIPT>

        <form id="in" action="delegatePolicy" method="POST">               
            <%@ page import="java.util.*" %>
            <%@ page import="javax.sql.*" %>

            <%


                java.sql.Statement s;
                java.sql.Statement s1;
                java.sql.ResultSet rs;
                java.sql.ResultSet rs1;
                java.sql.PreparedStatement pst;


                s = null;
                pst = null;
                rs = null;

                // Remember to change the next line with your own environment 
                String url = "jdbc:mysql://localhost:3306/";

                String dbName = "mydb";
                String driver = "com.mysql.jdbc.Driver";

                String userName = "root";
                String password = "root";
                Connection conn = null;
                try {

                    Class.forName(driver).newInstance();
                    //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                    conn = DriverManager.getConnection(url + dbName, userName, password);
                    /* TODO output your page here. You may use following sample code. */
                    // HttpSession session = request.getSession(true);

                } catch (ClassNotFoundException cnfex) {
                    cnfex.printStackTrace();

                }
                String sql = "select system_user_id from  users_list where type='doctor'";
                 String sql1 = "select rulename from rules";
                try {
                    s = conn.createStatement();
                    rs = s.executeQuery(sql);
                     s1 = conn.createStatement();
                    rs1 = s1.executeQuery(sql1);
            %>
            <b> Please select the doctor id here</b>
            <select name="doctorID" onchange="view();">
                <%
                    String temp = null;
                    int count = 0;
                    while (rs.next()) {
                        if (count == 0) {
                            temp = rs.getString(1);
                %>

                <%                }
                %>
                <option   value="<%= rs.getString(1)%>"><%= rs.getString(1)%> </option>

                <%
                        count++;
                    }
                %>
            </select>
            <input type=hidden name="policyApplied" value="<%=temp%>"/>
            
            
             <b> Please select the policies  here</b>
            <select name="policyID" onchange="view();" multiple="multiple">
                <%
                    String temp1 = null;
                    int count1 = 0;
                    while (rs1.next()) {
                        if (count1 == 0) {
                            temp1 = rs1.getString(1);
                %>

                <%                }
                %>
                <option   value="<%= rs1.getString(1)%>"><%= rs1.getString(1)%> </option>

                <%
                        count++;
                    }
                %>
            </select>
            <input type=hidden name="policyApplied" value="<%=temp1%>"/>
            
            
            
            
            <%

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (s != null) {
                        s.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                }

            %>
           
            <br>
            <a href="#" onclick="document.forms[0].submit()">Delegate</a>
            
            <%
            if(session.getAttribute("success")!=null){
String strExpired = (String) session.getAttribute("success");
if (!(strExpired.equals(null))){  
PrintWriter out1 = response.getWriter(); 
out1.print("<SCRIPT language=\"javascript\">");  
out1.print("alert('"+strExpired+"');");
out1.print("</SCRIPT>");
}      }
%><br>
           <a href="admin.jsp"><b>GoBack</b></a>
        </form>
           <br>
             <a href="logout.jsp"><b>Logout</b></a>
        <%
        } else {
        %>
        you have not logged in. please return to login page
        <a href="./index.jsp" onclick="">Login</a>
        <%    }
        %>
        <br/>

    </body>
</html>
