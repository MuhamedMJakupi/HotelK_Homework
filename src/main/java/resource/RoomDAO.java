package resource;

import domain.Room;
import domain.RoomType;
import resource.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//getting rooms from another class
public class RoomDAO {

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getString("id"));
                room.setRoomNumber(rs.getString("roomNumber"));
                room.setType(RoomType.valueOf(rs.getString("type")));
                room.setNightlyRate(rs.getBigDecimal("nightlyRate"));
                room.setAvailable(rs.getBoolean("isAvailable"));
                rooms.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }
}
