<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="auth_verification.jsp"></jsp:include>
<%@page import="interfacedropbox.*"%>
<%@page import="java.rmi.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <body>
        <font size="5" face="verdana" color="black">Users</font>
        <br>
        <%
        TaskInterface taskInterface;
    try {
        taskInterface = (TaskInterface) Naming.lookup("rmi://localhost/RMIDROP");
        String tasks = taskInterface.showOnline();
      
        String[] taskList = tasks.split("\t");
        for (int i = 0;i<taskList.length;i++){
            out.print(taskList[i]);
           %>
           <br>
           <%
        }
    }
    catch(Exception e){
        out.print("Could not retrieve tasks from server..");
    }
        %>
    </body>
</html>
