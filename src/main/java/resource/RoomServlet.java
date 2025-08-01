package resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Room;
import domain.RoomType;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// getting rooms directly on servlet
@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM room")) {

            while (rs.next()) {
                Room r = new Room();
                r.setId(rs.getString("id"));
                r.setRoomNumber(rs.getString("roomNumber"));
                r.setType(RoomType.valueOf(rs.getString("type")));
                r.setNightlyRate(rs.getBigDecimal("nightlyRate"));
                r.setAvailable(rs.getBoolean("isAvailable"));
                rooms.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        out.print(gson.toJson(rooms));
    }
}
