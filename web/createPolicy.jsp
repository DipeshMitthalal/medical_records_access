<%-- 
    Document   : createPolicy
    Created on : Nov 26, 2012, 1:39:51 PM
    Author     : Dipesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="./default.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Policy Creation</title>
        <script language = "Javascript">

</script>
    </head>
    <body>
        <%
            if (session.getAttribute("username") != null && session.getAttribute("username") != "" && session.getAttribute("role").equals("admin")) {
                String user = session.getAttribute("username").toString();

                String policyFor = (String) request.getParameter("policyFor");
        %>
        <table>    <tr><td align="center">Welcome <b><%= user%></b></td></tr></table>
    <br/>
   Create Policy here for Doctor
   
    <form id="admin" action="Admin" method="post" > 
        <table>
            <thead>
                <tr>
                    <th>Please input the name of policy</th>
                    <th><input type="text" name="rulename" value="" size="20" /></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><b>Department Section</b></td>
                </tr>
                <tr>
                    <td>can doctor view records of other patients of his department?</td>
                    <td> <input type="checkbox" name="onlyhisdepartment" value="Y" /></td>
                </tr>
                <tr>
                    <td>can doctor view records of other patients of all department?</td>
                    <td> <input type="checkbox" name="alldepartments" value="Y" /></td>
                </tr>
                <tr>
                    <td>should doctor view records of his own patients?</td>
                    <td> <input type="checkbox" name="onlyhispatients" value="Y" checked="checked" /></td>
                </tr>
            </tbody>
        </table>
       
         <table>
            <thead>
                <tr>
                    <td><b>Doctor give privileges to nurses</b></td>
                    <td> <input type="checkbox" name="nursePrivilege" value="Y" /></td>
                </tr>

            </thead>
         </table>
        <table>
            <thead>
                <tr>
                    <td><b>Allergies</b></td>
                </tr>

            </thead>
            <tbody>
                <tr> 
                    <td>view allergies related of all his patients (if any)</td>
                    <td> <input type="radio" name="Allergies" value="allergiesOwnPatients" /></td>
                </tr>
                <tr> 
                    <td>view allergies of his patients  if only  related to his speciality(If any)</td>
                    <td> <input type="radio" name="Allergies" value="allergiesOwnSpeciality" /></td>
                </tr>
                <tr> 
                    <td>Deny access to view allergies</td>
                    <td> <input type="radio" name="Allergies" value="NoAllergies" checked="checked"/></td>
                </tr>
   
                
                
            </tbody>
        </table>
      
        <input type="submit" name="Submit" value="Submit">
    </form>
   
    <%            
    } else {
    %>
    you have not logged in or you dont have required privileges. please return to login page
    <a href="./index.jsp">Login</a>
    <%    }
    %>

</body>
</html>
