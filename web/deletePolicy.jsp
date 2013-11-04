<%-- 
    Document   : nursePrivileges
    Created on : Nov 29, 2012, 6:51:15 PM
    Author     : Dipesh
--%>

<%@page import="javax.swing.text.Document"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nurses</title>

        <link rel="stylesheet" type="text/css" href="./default.css" />
        <SCRIPT language="javascript">
            function view(){
                
                var sel = document.forms["in"].rule;  
                var opt = sel.options[sel.selectedIndex].value;
                 
                document.forms["in"].policy_ID.value = opt; 
                 document.getElementById("rulename").value = opt;
                              // document.forms["in"].submit();
            }
           
        </SCRIPT>
    </head>

    <body onload="view()">
        <%
            if (session.getAttribute("username") != null && session.getAttribute("username") != "" && session.getAttribute("role").equals("admin")) {
                String user = session.getAttribute("username").toString();
               
        %> 
        %>
     

        <form id="in"  action="DeletePolicy" method="POST">               
            <%@ page import="java.util.*" %>
            <%@ page import="javax.sql.*" %>

            <%
                java.sql.Statement s;
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
                String sql = "select * from  rules_mod";
                try {
                    s = conn.createStatement();
                    rs = s.executeQuery(sql);
                    rs1 = rs;
            %>
            <h1>Hi Admin.<%=user%>.you can Modify or delete the policies here</h1>
            <b>please select the policy which u want to modify here</b>
            <select id="nursedl" name="rule" onchange="view();">
                <%
                    ArrayList al = null;
                    ArrayList per = new ArrayList();
                    String temp = null;
                    int count = 0;
                    while (rs.next()) {
                        al = new ArrayList();
                        al.add(rs1.getString(1));
                        al.add(rs1.getString(2) + ":" + rs1.getString(3) + ":" + rs1.getString(4) + ":" + rs1.getString(5) + ":" + rs1.getString(6) + ":" + rs1.getString(7));
                        per.add(al);
                        if (count == 0) {
                            temp = rs.getString(1);
                        }
                %>

                <%
                %>
                <option   value="<%= rs.getString(1)%>"><%= rs.getString(1)%> </option>
                <%
                        count++;
                    }
                %>
            </select>
            <input type=hidden name="policy_ID" value="<%=temp%>"/>
                          
           
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
           

            <input type="submit" name="Submit" value="Submit">
        </form>


   <a href="logout.jsp"><b>Logout</b></a>
        <%

        } else {
        %>
        you have not logged in. please return to login page
        <a href="./index.jsp">Login</a>
        <%    }
        %>

    </body>
</html>
