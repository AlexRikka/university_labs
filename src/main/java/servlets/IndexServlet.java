package servlets;

import classes.DBConnector;
import classes.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/uservalid")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String userValid = (String) context.getAttribute("user_valid");

        String errmsg = "Login to view your order list";
        String required = "background:#ecf0f1;"; //ok
        if (userValid == null) {
            context.setAttribute("err_msg", errmsg);
        }else if (context.getAttribute("user_valid").equals("user_found")){  //для возврата на login page
            context.setAttribute("err_msg", errmsg);
            context.setAttribute("req_check", required);
        }

        String path = "/index.jsp";
        RequestDispatcher requestDispatcher = context.getRequestDispatcher(path);
        requestDispatcher.forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        ServletContext context = getServletContext();
        String emptyfields = "empty_fields";
        String required = "background: #F7D3C5;"; //empty
        String errmsg = "Login to view your order list";
        if (login == null || password == null) {
            context.setAttribute("user_valid", emptyfields);
            context.setAttribute("err_msg", errmsg);
            String path = "/index.jsp";
            RequestDispatcher requestDispatcher = context.getRequestDispatcher(path);
            try {
                requestDispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
        DBConnector conn = new DBConnector();
        Customer newCust = new Customer();
        newCust.setCustomerLogin(login);
        newCust.setCustomerPassword(password);
        try {
            //проверка
            if (conn.authentication(newCust)) {
                context.setAttribute("user_valid", "user_found");
                HttpSession session = req.getSession(); // текущий сеанс, связанный с этим запросом
                session.setAttribute("user", newCust); //добавляет пользователя в сессию
                String path = req.getContextPath() +  "/result";  // root of application +
                try {
                    resp.sendRedirect(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                errmsg = "USER NOT FOUND";
                context.setAttribute("user_valid", "user_not_found");
                context.setAttribute("req_check", required);
                context.setAttribute("err_msg", errmsg);
                String path = "/index.jsp";
                RequestDispatcher requestDispatcher = context.getRequestDispatcher(path);
                try {
                    requestDispatcher.forward(req, resp);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}


