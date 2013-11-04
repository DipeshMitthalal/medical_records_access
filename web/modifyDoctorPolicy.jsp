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
                //    alert(opt);
                document.forms["in"].policy_ID.value = opt; 
                var di = document.forms["in"].elements[opt].value;
                var n=di.split(":");
                // alert(n[0]);
                document.getElementById("rulename").value = n[0];
                document.getElementById("oldpolicy").value = n[0];
                if(n[1]=="Y")
                    document.getElementById("onlyhisdepartment").checked = true;
                else
                    document.getElementById("onlyhisdepartment").checked = false;
                if(n[2]=="Y")
                    document.getElementById("alldepartments").checked = true;
                else
                    document.getElementById("alldepartments").checked = false;
                
                if(n[3]=="Y")
                    document.getElementById("onlyhispatients").checked = true;
                else
                    document.getElementById("onlyhispatients").checked = false;
                if(n[4]=="Y")
                    document.getElementById("patientsIdentity").checked = true;
                else
                    document.getElementById("patientsIdentity").checked = false;
                if(n[5]=="Y")
                    document.getElementById("nursePrivilege").checked = true;
                else
                    document.getElementById("nursePrivilege").checked = false;
                document.getElementById(n[6]).checked = true;
                
                
                // document.forms["in"].submit();
            }
            function populate(){
                var e = document.getElementById("nursedl");
                var strUser = e.options[e.selectedIndex].value;
                
            }
            function set(){
               
                if(document.getElementById("a").checked == false){
                    document.getElementById("a").value="N";
                }
                if(document.getElementById("b").checked == false){
                    document.getElementById("b").value="N";
                }
                if(document.getElementById("c").checked == false){
                    document.getElementById("c").value="N";
                }
                // alert(document.forms["in"].addPatients.value);
                // alert(document.forms["in"].upd_Patient_Medication.value);
                //alert(document.forms["in"].patients_from_other_wards.value);
                return true;
            }
        </SCRIPT>
    </head>

    <body onload="view()">
        <%
            if (session.getAttribute("username") != null && session.getAttribute("username") != "" && session.getAttribute("role").equals("admin")) {
                String user = session.getAttribute("username").toString();
                String policyFor = (String) request.getParameter("policyFor");
        %> 
        %>
        <a href="logout.jsp"><b>Logout</b></a>

        <form id="in"  action="Admin" method="POST">               
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
                List viewList = new ArrayList();
                ListIterator viewItr;
                // List userList = (ArrayList) request.getAttribute("userList");
                Iterator itr = per.iterator();
                while (itr.hasNext()) {
                    viewList = (ArrayList) itr.next();
                    String id1 = viewList.get(0).toString();
                    viewItr = viewList.listIterator();
                    String per1 = "";
                    while (viewItr.hasNext()) {
                        per1 = per1 + viewItr.next().toString() + ":";
                    }
            %>
            <input type="hidden"  name="<%=id1%>" value="<%=per1%>" />
            <%
                    }
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
            <table>
                <thead>
                    <tr>
                        <th>Please input the updated name of policy if required</th>
                        <th><input type="text" id="rulename" name="rulename" value="" size="20" /></th>
                        <th> <input type="hidden" id="oldpolicy" name="oldpolicy" value="" /></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><b>Department Section</b></td>
                    </tr>
                    <tr>
                        <td>can doctor view records of other patients of his department?</td>
                        <td> <input type="checkbox" id ="onlyhisdepartment" name="onlyhisdepartment" value="Y" /></td>
                    </tr>
                    <tr>
                        <td>can doctor view records of other patients of all department?</td>
                        <td> <input type="checkbox" id="alldepartments" name="alldepartments" value="Y" /></td>
                    </tr>
                    <tr>
                        <td>should doctor view records of his own patients?</td>
                        <td> <input type="checkbox" id="onlyhispatients" name="onlyhispatients" value="Y"  /></td>
                    </tr>
                </tbody>
            </table>

            <table>
                <thead>
                    <tr>
                        <td><b>Doctor give privileges to nurses</b></td>
                        <td> <input type="checkbox" id="nursePrivilege" name="nursePrivilege" value="Y" /></td>
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
                        <td> <input id="allergiesOwnPatients" type="radio" name="Allergies" value="allergiesOwnPatients" /></td>
                    </tr>
                    <tr> 
                        <td>view allergies of his patients  if only  related to his speciality(If any)</td>
                        <td> <input id="allergiesOwnSpeciality" type="radio" name="Allergies" value="allergiesOwnSpeciality" /></td>
                    </tr>
                    <tr> 
                        <td>Deny access to view allergies</td>
                        <td> <input id="NoAllergies" type="radio" name="Allergies" value="NoAllergies" checked="checked"/></td>
                    </tr>
                </tbody>
            </table>

            <input type="submit" name="Submit" value="Submit">
        </form>



        <%

        } else {
        %>
        you have not logged in. please return to login page
        <a href="./index.jsp">Login</a>
        <%    }
        %>

    </body>
</html>
