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
    </head>
    <body onload="view()">
        <%
            if (session.getAttribute("username") != null && session.getAttribute("username") != "" && session.getAttribute("role").equals("doctor")) {
                String user = session.getAttribute("username").toString();
                if (session.getAttribute("givePrivilegesToNurse").equals("Y")) {
        %>
        <a href="logout.jsp"><b>Logout</b></a>
        <SCRIPT language="javascript">
            function view(){
                
                var sel = document.forms["in"].rule;  
                var opt = sel.options[sel.selectedIndex].value;
                //    alert(opt);
                document.forms["in"].nurse_ID.value = opt; 
                var di = document.forms["in"].elements[opt].value;
     //           alert(di);
                var n=di.split(":");
     
   // alert(n[2]);
                if(n[1]=="Y")
                    document.getElementById("addPatients").checked = true;
                else
                    document.getElementById("addPatients").checked = false;
                if(n[2]=="Y")
                    document.getElementById("b").checked =  true;
                else
                    document.getElementById("b").checked =  false;
                if(n[3]=="Y")
                    document.getElementById("c").checked = true;
                else
                    document.getElementById("c").checked = false;
         
   
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
        <form id="in"  action="nursePrivilege" method="POST" onsubmit="return set()">               
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
                String sql = "select * from  nurses where assigned_to_doc='" + user + "'";
                try {
                    s = conn.createStatement();
                    rs = s.executeQuery(sql);
                    rs1 = rs;
            %>
            <h1>Hi Dr.<%=user%>.you can set privileges for your nurses here</h1>
            <b>please select the name of nurse here</b>
            <select id="nursedl" name="rule" onchange="view();">
                <%
                    ArrayList al = null;
                    ArrayList per = new ArrayList();
                    String temp = null;
                    int count = 0;
                    while (rs.next()) {
                        al = new ArrayList();
                        al.add(rs1.getString(1));
                        al.add(rs1.getString(10) + ":" + rs1.getString(11) + ":" + rs1.getString(12));
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
            <input type=hidden name="nurse_ID" value="<%=temp%>"/>
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
                        <th><b>Patient Data Maintenance</b></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>can nurse add patient medication history</td>
                        <td> <input type="checkbox" id="addPatients" name="addPatients" value="Y" /></td>
                    </tr>
                    <tr>
                        <td>can nurse edit patient medication history</td>
                        <td> <input type="checkbox" id="b" name="upd_Patient_Medication" value="Y" /></td>
                    </tr>
                    <tr>
                        <td>can nurse access patient records from other wards</td>
                        <td> <input type="checkbox" id="c" name="patients_from_other_wards" value="Y" /></td>
                    </tr>
                </tbody>
            </table>

            <input type="submit" name="Submit" value="Submit">

        </form>
        <%
        } else {
        %>
        <h1>Hi Dr.<%=user%>.you Dont have privileges to modify/set privileges for your nurses</h1>
        Please contact your administrator
        <a href="viewRecords.jsp">Go Back</a>

        <%
            }

        } else {
        %>
        you have not logged in. please return to login page
        <a href="./index.jsp">Login</a>
        <%    }
        %>

    </body>
</html>
