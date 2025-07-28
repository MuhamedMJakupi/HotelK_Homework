package resource;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//this file and code created to test if Tomcat works, doesn't do anything else.
@WebServlet("/hello") // this mean u have to add this to the end of uel to see the message else it says error bc nothing there!
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("Hello from HotelK!");
        resp.getWriter().write("Testing if Tomcat works!");
    }
}
