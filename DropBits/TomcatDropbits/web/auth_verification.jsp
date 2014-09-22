<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="interfacedropbox.User;"%>

<%
User userdata = (User) session.getAttribute("user");
if (userdata == null){
%>
    <jsp:forward page="invaliduser.html"></jsp:forward>
<%
} 
%>
