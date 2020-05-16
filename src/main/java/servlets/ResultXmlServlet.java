package servlets;

import classes.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


@WebServlet("/result.xml")
public class ResultXmlServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        HttpSession session = req.getSession();
        Customer customer = (Customer)session.getAttribute("user");
        if (customer == null) {
            RequestDispatcher requestDispatcher = context.getRequestDispatcher("/error.jsp");
            requestDispatcher.forward(req, resp);
        }

        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Customer.class); // создаем объект JAXBContext - точку входа для JAXB
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller(); //создание объекта Marshaller, который выполняет сериализацию
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //позволяет запросить, чтобы браузер попросил пользователя сохранить ответ на диск в файле с заданным именем
            resp.setHeader("Content-disposition","attachment; filename = result.xml");
            resp.setContentType("application/xml"); //устанавливает тип содержимого ответа, отправляемого клиенту
            StringWriter writer = new StringWriter(); //результат сериализации пишется в Writer(StringWriter)
            jaxbMarshaller.marshal(customer, writer); //сама сериализация
            PrintWriter respWriter = resp.getWriter();
            respWriter.println(writer.toString());
            writer.close();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
