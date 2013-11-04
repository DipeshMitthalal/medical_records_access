<%-- 
    Document   : logout
    Created on : Nov 28, 2012, 10:45:38 PM
    Author     : Dipesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="./default.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logout</title>
        
    </head>
    <body>
        <%@page import="java.util.*" %>

<%session.invalidate();%>
You have logged out. Plese
<a href="index.jsp"><b>Login</b></a>

    
    </body>
</html>
