
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

public class LogServlet extends HttpServlet {

    private static final long serialVersionUID = -8608034654794572382L;
    private TaskInterface taskInterface;

    public void init() throws ServletException {
        try {
            taskInterface = (TaskInterface) Naming.lookup("rmi://localhost/RMIDROP");
            RMIClient c = new RMIClient();
            taskInterface.subscribe(c);
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
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        String verifiedPass;
        RequestDispatcher dispatcher;

        AnswerLogin msg = taskInterface.login(user, pass);

        if (msg.getMessage().equals("Successfully logged in")) {
            HttpSession session = request.getSession(true);
            User userData = new User();
            userData.setUsername(user);
            userData.setPassword(pass);

            session.setAttribute("user", userData);
            dispatcher = request.getRequestDispatcher("/userinterface.jsp");
        } else {
            dispatcher = request.getRequestDispatcher("/invaliduser.html");
        }

        dispatcher.forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
