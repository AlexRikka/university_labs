package servlets;

import classes.Customer;
import classes.DBConnector;
import classes.Item;

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

@WebServlet("/result")
public class ResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = "/result.jsp";
        ServletContext сontext = getServletContext();
        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) {
            RequestDispatcher requestDispatcher = сontext.getRequestDispatcher("/error.jsp");
            requestDispatcher.forward(req, resp);
        }
        try {
            DBConnector conn = new DBConnector();
            customer.setCustomer_id(conn.getCustomerIdFromDB(customer));
            customer.setCustomerName(conn.getCustomerName(customer));
            System.out.println(customer.getCustomer_id());
            System.out.println(customer.getCustomerName());
            conn.customerOrders(customer); // пользователь получает id своих заказов
            for (int i = 0; i < customer.getLength(); i++) {
                conn.orderItems(customer.getOrderList().get(i)); // заказ получает свое содержимое
            }
            session.setAttribute("user", customer); //помещает в сессию пользователя с полной инф-ей
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            RequestDispatcher requestDispatcher = сontext.getRequestDispatcher(path);
            try {
                requestDispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }

    }
}
