<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<jsp:include page="auth_verification.jsp"></jsp:include>
<%@page import="interfacedropbox.*"%>
<%@page import="java.rmi.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<font size="5" face="verdana" color="black">DropTasks</font>
<br>
<br>
<%
    TaskInterface taskInterface;
    try {
        taskInterface = (TaskInterface) Naming.lookup("rmi://localhost/RMIDROP");
        String tasks = taskInterface.showTasks();
      
        String[] taskList = tasks.split("\n");
        for (int i = 0;i<taskList.length;i++){
            int j = taskList[i].lastIndexOf("TITLE:");
            int k = taskList[i].indexOf("AUTHOR");
            
            String id = (taskList[i].substring(4, k-2));
            taskList[i]= taskList[i].substring(j+6, taskList[i].length());
%>
            
           
        <br>
           
                
            <input type='text' id="<%out.print(id);%>" value=<%out.print(taskList[i]);%> />
            <input type='submit' value='save' onClick="editTask(<%out.print(id);%>,document.getElementById('<%out.print(id);%>').value)"/>
            
            <input type='submit' value='delete' onClick="deleteTask(<%out.print(id);%>)" />
            
           
<%
        }
    }
    catch(Exception e){
        out.print("There are no tasks to show.");
    }
%>
       <br>
        <input type='text' value="" id="taskname" />
        <input type='submit' value='Add Task' onClick="addTask(document.getElementById('taskname').value)" />

</body>
</html>
