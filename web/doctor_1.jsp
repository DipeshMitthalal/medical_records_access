<%-- 
    Document   : admin
    Created on : Nov 24, 2012, 3:44:15 PM
    Author     : Dipesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            if (session.getAttribute("username") != null && session.getAttribute("username") != "") {
                String user = session.getAttribute("username").toString();

        %>
        <table>
    <tr><td align="center"><h1>Welcome Doctor <b><%= user%></b></h1></td></tr>
    <h1>you can view your patient records here</h1>
    <form name = "Test" action="ViewRecords" method="post">
<a href="ViewRecords">View Records</a>
</form>
    <%
    } else {
    %>
    <tr>you have not logged in. please return to login page
    <a href="./index.jsp">Login</a></tr></table>
    <%    }
    %>


</body>
</html>
