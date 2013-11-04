<%@ page language="java" import="java.util.*;"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
    <HEAD>
        <link rel="stylesheet" type="text/css" href="../default.css" />
        <TITLE>Servlet Application</TITLE>
        <SCRIPT language="javascript">
            function editRecord(id){
                var te = id;
                document.forms["in"].hiddenValue.value = id; 
                // alert(te);
                // document.write('<input type="hidden" name="temp" value=id />');
                //document.write("<h1>Hello World!</h1><p>Have a nice day!</p>")
                window.location.href="../EditMedication/"+id; 
                          }
            
            function editRecord1(id){
                var te = id;
                document.forms["in"].hiddenValue.value = id; 
                              window.location.href="EditMedication/"+id; 
                             }
            function delRecord(id){
                var te = id;
                document.forms["in"].hiddenValue.value = id;     
                window.location.href="../DeleteData/"+id; 
                       }
        </SCRIPT>
    </HEAD>

    <BODY>
        <br>
        <table align="center">

        </table>
        <br>
        <form id="in" action="patientMedicationHistory" method="POST">
            <input type=hidden name="hiddenValue"/>
            <table width="600px"  align="center" style="background-color:#EDF6EA;border:1px solid #000000;">
                <tr><td colspan=9 align="center" height="10px"></td></tr>


                <tr><td colspan=9 align="center" height="10px"></td></tr>
                <tr style="background-color:#7BA88B;font-weight:bold;">
                    <td>medical record no</td><td>Drugs prescribed</td><td>Record Creation date</td>
                    <td>RBC</td><td>Sugar</td><TD>glucose level</TD><td>patient ID</td> <td>prescribed by</td>
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


                    %>
                    <td><%=viewItr.next()%></td>

                    <%


                        }
                        count++;
                    %>
                    <%
                        if (session.getAttribute("can_update") != null) {
                            if (session.getAttribute("can_update").equals("Y")) {
                    %>
                    <td><input type="button" name="edit" value="Edit" style="background-color:#49743D;font-weight:bold;color:#ffffff;" onclick="editRecord1(<%=id%>);" ></td>
                        <%
                            }

                        } else {
                            
                        if(!(session.getAttribute("role").equals("patient"))){
                        %>
                    <td><input type="button" name="edit" value="Edit" style="background-color:#49743D;font-weight:bold;color:#ffffff;" onclick="editRecord(<%=id%>);" ></td>
                    <td><input type="button" name="edit" value="Delete" style="background-color:#49743D;font-weight:bold;color:#ffffff;" onclick="delRecord(<%=id%>);" ></td>

                </tr>
                <%
                                         }  }
                        }
                    }
                    if (count == 0) {
                %>
                <tr><td colspan="9" align="center">&nbsp;</td></tr>
                <tr><td colspan="9" align="center">No Record Avaliable</td></tr>
                <%                    }

                    if (session.getAttribute("can_add") != null) {
                        if (session.getAttribute("can_add").equals("Y")) {
                %>
                <tr><td colspan=9 align="center"><a href="addRecords.jsp" style="font-weight:bold;color:#cc0000;">Add New Record for patients</a></td></tr>          
                <%                            }
                                               }
                    
                %>




                <tr><td colspan=9 align="center" height="2px"></td></tr>
            </table>

        </FORM>
                <%
                if (session.getAttribute("role") != null && session.getAttribute("role").equals("doctor"))
 {
%>

<form id="goback" action="../ViewRecords" method="post">
            <a href="../ViewRecords" onclick="document.forms["goback"].submit()">Goback</a>
             <a href="../addRecords.jsp" onclick="document.forms["goback"].submit()">AddRecords</a>
        </form>
<%
                                       
                }
                    if(session.getAttribute("role").equals("nurse")){
                 %>
                 <a href="logout.jsp"><b>Logout</b></a>
                 <%
                    }else{
                %>
                
                
                
       
                <a href="logout.jsp"><b>Logout</b></a>
                <%
                    }
                    %>
    </BODY>
</HTML>
