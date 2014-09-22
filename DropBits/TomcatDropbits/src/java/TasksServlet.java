
import interfacedropbox.*;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.net.MalformedURLException;
import java.rmi.*;

public class TasksServlet extends HttpServlet {

    private static final long serialVersionUID = -8608034654794572382L;
    private TaskInterface taskInterface;

    public void init() throws ServletException {
        try {
            taskInterface = (TaskInterface) Naming.lookup("rmi://localhost/RMIDROP");
        } catch (MalformedURLException e) {
        } catch (AccessException e) {
            throw new ServletException(e);
        } catch (RemoteException e) {
            throw new ServletException(e);
        } catch (NotBoundException e) {
            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String operation = request.getParameter("operation");

        if (operation.equals("addtask")) {
            String taskname = request.getParameter("taskname");

            RequestDispatcher dispatcher;
            HttpSession session = request.getSession(true);
            User userdata = (User) session.getAttribute("user");
            Task task = new Task(userdata.getUsername(), taskname);
            taskInterface.addTask(userdata.getUsername(), task);
            dispatcher = request.getRequestDispatcher("tasks.jsp");
            dispatcher.forward(request, response);


        } else if (operation.equals("deletetask")) {
            String task = request.getParameter("taskname");
            String number = request.getParameter("tasknumber");

            RequestDispatcher dispatcher;
            HttpSession session = request.getSession(true);
            User userdata = (User) session.getAttribute("user");
            taskInterface.deleteTask(userdata.getUsername(), Integer.parseInt(number));
            dispatcher = request.getRequestDispatcher("tasks.jsp");
            dispatcher.forward(request, response);

        } else if (operation.equals("edittask")) {
            String task = request.getParameter("taskname");
            String number = request.getParameter("tasknumber");

            RequestDispatcher dispatcher;
            HttpSession session = request.getSession(true);
            User userdata = (User) session.getAttribute("user");
            taskInterface.editTask(userdata.getUsername(), Integer.parseInt(number), task);
            dispatcher = request.getRequestDispatcher("tasks.jsp");
            dispatcher.forward(request, response);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
