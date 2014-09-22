<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<jsp:include page="auth_verification.jsp"></jsp:include>
<%@page import="interfacedropbox.User;"%>

<html>
<body>
<p align="right">
 <% User userdata = (User) session.getAttribute("user"); %> 
Welcome <%= userdata.getUsername() %> <a href="logout.jsp">logout</a>
</p>
</body>
</html>
