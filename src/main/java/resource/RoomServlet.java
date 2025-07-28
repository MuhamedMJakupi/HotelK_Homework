package resource;

import com.google.gson.Gson;
import domain.Room;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.RoomService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

//not working will check again
@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {

    private final RoomService roomService = new RoomService(
            "Room JSON Service", new BigDecimal("10"), new BigDecimal("2"), "Service");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Room> rooms = roomService.getAll();  // get from DB
        Gson gson = new Gson();
        String json = gson.toJson(rooms);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);

        System.out.println("Rooms found: " + rooms.size());
        rooms.forEach(System.out::println);

        System.out.println("ROOMS FROM DB:");
        rooms.forEach(r -> System.out.println("Room: " + r.getRoomNumber()));


    }


}



