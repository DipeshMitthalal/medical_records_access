<%@ page language="java" import="java.util.*;"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./default.css" />
</head>
<body>
<%!
int id;
String drugsPrescribed="";
String prescribedBy="";
String recordCreationDate="";
String RBC="";
String sugar="";
String glucoselevel="";

List  userList=null;
%>

<%

if(request.getAttribute("userList")!=null && request.getAttribute("userList")!="")
{
		userList = (ArrayList)request.getAttribute("userList");
		id=Integer.parseInt(userList.get(0).toString());
		drugsPrescribed=userList.get(1).toString();
		recordCreationDate=userList.get(2).toString();
		RBC=userList.get(3).toString();
		sugar=userList.get(4).toString();
		glucoselevel=userList.get(5).toString();
		prescribedBy=userList.get(6).toString();
		//out.println(id);
}
%>

<form name="userform" method="post" action="Addrecord">
<br><br><br>
<input type="hidden" name="id" value="<%=id%>"/>
<table align="center" width="300px" style="background-color:#EDF6EA;border:1px solid #000000;">

<tr><td colspan=2 style="font-weight:bold;" align="center">Add Record</td></tr>
<tr><td colspan=2 align="center" height="10px"></td></tr>
	
	<tr>
		<td>drugsPrescribed</td>
		<td><input type="text" name="drugsPrescribed" value="<%=drugsPrescribed%>"></td>
	</tr>
	<tr>
		<td>Blood cells count(RBC)</td>
		<td><input type="text" name="RBC" value="<%=recordCreationDate%>"></td>
	</tr>
	<tr>
		<td>sugar levels</td>
		<td><input type="text" name="sugar" value="<%=RBC%>"></td>
	</tr>
	
	<tr>
		<td>glucose</td>
		<td><input type="text"  name="glucose" value="<%=sugar%>"></td>
	</tr>
	<tr>
	<%
                 String patientID = (String)session.getAttribute("pat1");
                 String doctor = (String)session.getAttribute("username");
                %>	
            <td>patient Id</td>
                <td><input type="text" name="patID" value="<%=patientID%>" readonly="readonly"></td>
	</tr>
	<tr>
		<td>PrescribedBy</td>
		<td><input type="text" name="prescribedby" value="<%=doctor%>" readonly="readonly"></td>
	</tr>
	
	<tr>
		<td></td>
		<td><input type="submit" name="Submit" value="ADD" style="background-color:#49743D;font-weight:bold;color:#ffffff;"></td>
	</tr>
	<tr><td colspan=2 align="center" height="10px"></td></tr>
</table>
                
        <input type="submit" name="Submit" value="Add a record" style="background-color:#49743D;font-weight:bold;color:#ffffff;"/>
</form>
</body>
</html>


