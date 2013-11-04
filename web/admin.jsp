<%-- 
    Document   : admin
    Created on : Nov 24, 2012, 3:44:15 PM
    Author     : Dipesh
--%>

<%@page import="javax.swing.text.Document"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="./default.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <script language="javascript">
            function mod(){
                var sel = document.forms["in"].policyFor;  
                var opt = sel.options[sel.selectedIndex].value;
                     
                document.getElementById('in').submit();
                if(opt == "doctor")    {
                    window.location.href="modifyDoctorPolicy.jsp"; 
                }else  {
                    window.location.href="thankyou.jsp";    
                }
                return true;
            }
            function del(){
                var sel = document.forms["in"].policyFor;  
                var opt = sel.options[sel.selectedIndex].value;
                     
                document.getElementById('in').submit();
                if(opt == "doctor")    {
                    window.location.href="deletePolicy.jsp"; 
                }else  {
                    window.location.href="thankyou.jsp";    
                }
                return true;
            }
            function add(){
                var sel = document.forms["in"].policyFor;  
                var opt = sel.options[sel.selectedIndex].value;
                     
                document.getElementById('in').submit();
                if(opt == "doctor")    {
                    window.location.href="createPolicy.jsp"; 
                }else  {
                    window.location.href="thankyou";    
                }
                return true;
            }
        </script>
        <%
            if (session.getAttribute("username") != null && session.getAttribute("username") != "" && session.getAttribute("role").equals("admin")) {
                String user = session.getAttribute("username").toString();
        %>
        Welcome <b><%= user%></b>
        <br>
        <form id="in" action="createPolicy.jsp" onsubmit="return add()" method="POST">
            Please select the type of user for which you want to create or update policies


            <select name="policyFor" id="policyFor">
                <option value="doctor">Doctor</option>
                <option value="nurse">Nurse</option>
                <option value="student">Student</option>
            </select>
            <br>
            <a href="javascript:add()" >create policy</a>
            <br>


            <a href="javascript:mod()" >Modify Policy</a>
            <br>
            <a href="javascript:del()">Delete Policy</a>
            <br>
            <a href="delegatePolicy.jsp">Delegate Policy</a>
        </form>
        <br>
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
