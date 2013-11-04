<%@ page language="java" import="java.util.*;"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
    <HEAD>
        <link rel="stylesheet" type="text/css" href="./default.css" />
        <TITLE>Servlet Application</TITLE>
        <SCRIPT language="javascript">
            function view(id){
                var te = id;
                document.forms["in"].hiddenValue.value = id; 
               // alert(te);
                  // document.write('<input type="hidden" name="temp" value=id />');
                       //document.write("<h1>Hello World!</h1><p>Have a nice day!</p>")
               document.forms["in"].submit();
              window.location.href="patientMedicalHistory/"+te; 
             
            }
        </SCRIPT>
    </HEAD>
   
    <BODY>
        <br>
        <table align="center">

        </table>
        <br>
        <form id="in" >
            <input type=hidden name="hiddenValue"/>
            <table width="600px"  align="center" style="background-color:#EDF6EA;border:1px solid #000000;">
                <tr style="font-weight:bold;"><td colspan=9 align="center" height="10px"> Patient List</td></tr>

                
                <tr><td colspan=9 align="center" height="10px"></td></tr>
                <tr style="background-color:#7BA88B;font-weight:bold;">
                    <td>Patient Id</td><td>FirstName</td><td>Last Name</td>
                    <td>Room no</td><td>Allergies</td>
                </tr>
                <%
                    String bgcolor = "";
                    int count = 0;
                    List viewList = new ArrayList();
                    ListIterator viewItr;

                    if (request.getAttribute("userList") != null && request.getAttribute("userList") != "") {
                        List userList = (ArrayList) request.getAttribute("userList");
                        Iterator itr = userList.iterator();
                        //ListIterator itr = userList.listIterator();
                        while (itr.hasNext()) {

                            if (count % 2 == 0) {
                                bgcolor = "#C8E2D1";
                            } else {

                                bgcolor = "#EAF8EF";
                            }

                            viewList = (ArrayList) itr.next();
                            String id = viewList.get(0).toString();
                            viewItr = viewList.listIterator();
                %>
                <tr style="background-color:<%=bgcolor%>;">
                    <%
                        while (viewItr.hasNext()) {
                            if (viewItr.nextIndex() == 0) {
    String temp = (String)viewItr.next();
                    %>   
                    
                   <td>   <a  onclick="javascript:view('<%=temp%>');" href="javascript:view('<%=temp%>')"><%=temp%></a></td>
                                        
                    <%
                    } else {
                    %>
                    <td><%=viewItr.next()%></td>

                    <%

                            }
                        }
                        count++;
                    %>
                   
                    
                </tr>
                <%
                        }
                    }
                    if (count == 0) {
                %>
                <tr><td colspan="9" align="center">&nbsp;</td></tr>
                <tr><td colspan="9" align="center">No Record Avaliable</td></tr>
                <%                    }
                %>
                
                <tr><td colspan=9 align="center" height="2px"></td></tr>
            </table>
                 <%
                if (session.getAttribute("givePrivilegesToNurse") != null && session.getAttribute("givePrivilegesToNurse").equals("Y")) {
            %>
            <a href="nursePrivileges.jsp">Update Privileges for your nurses here </a>
            <%                     }
            %>
        </FORM>
    </BODY>
</HTML>
