<%@page import="java.rmi.NotBoundException"%>
<%@page import="java.rmi.RemoteException"%>
<%@page import="java.rmi.AccessException"%>
<%@page import="java.net.MalformedURLException"%>
<%@page import="java.rmi.Naming"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="interfacedropbox.*;"%>
<html>
<% 
     
                try
		{
                        TaskInterface taskInterface = (TaskInterface) Naming.lookup("rmi://localhost/RMIDROP"); 
                        User user = (User) session.getAttribute("user");
                        taskInterface.logout(user.getUsername());
                        session.invalidate();
                        response.sendRedirect("index.html");
		}
                catch (MalformedURLException e){
                    
                }
		catch (AccessException e)
		{
			throw new ServletException(e);
		}
		catch (RemoteException e)
		{
			throw new ServletException(e);
		}
		catch (NotBoundException e)
		{
			throw new ServletException(e);
		}

                
%>
</html>
