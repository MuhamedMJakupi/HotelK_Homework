package Service;

import core.Room;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomService extends HotelService {
    private final BigDecimal serviceFee;
    public RoomService(String description, BigDecimal baseCost, BigDecimal serviceFee,String serviceName) {
        super(description, baseCost,serviceName);
        this.serviceFee = serviceFee;
    }

    @Override
    public BigDecimal getCost() {
        if (super.getCost() != null && super.getCost().compareTo(getBaseCost()) != 0) {
            return super.getCost();
        }
        return getBaseCost().add(serviceFee);
    }

    @Override
    public String toString() {
        return "RoomService: " + getDescription() +
                " | Service Fee: " + serviceFee +
                " | Final Cost: " + getCost();
    }

    //12. -Homework 3
    public void completeSteps(char[] steps) {
        int i = 0;
        while (i < steps.length) {
            steps[i] = 'X';
            i++;
        }
    }

    //Homework 6
    private final String jdbcURL = "jdbc:mysql://localhost:3306/hotelkey";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "Mustafa_1903";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public void insert(Room room) {
        String sql = "INSERT INTO room (id, roomNumber, type, nightlyRate, isAvailable) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, room.getRoomID());
            stmt.setString(2, room.getRoomNumber());
            stmt.setString(3, room.getType().toString());
            stmt.setBigDecimal(4, room.getNightlyRate());
            stmt.setBoolean(5, room.isAvailable());

            int rows = stmt.executeUpdate();
            System.out.println(rows + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Room getById(String id) {
        String sql = "SELECT * FROM room WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ) {

            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToRoom(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Room> getAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(mapRowToRoom(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    // Bonus: Update
    public void update(Room room) {
        String sql = "UPDATE room SET roomNumber = ?, type = ?, nightlyRate = ?, isAvailable = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room.getType().toString());
            stmt.setBigDecimal(3, room.getNightlyRate());
            stmt.setBoolean(4, room.isAvailable());
            stmt.setString(5, room.getRoomID());

            int rows = stmt.executeUpdate();
            System.out.println(rows + " row(s) updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Bonus: Delete
    public void deleteById(String id) {
        String sql = "DELETE FROM room WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " row(s) deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Room mapRowToRoom(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String roomNumber = rs.getString("roomNumber");
        String typeStr = rs.getString("type");
        BigDecimal nightlyRate = rs.getBigDecimal("nightlyRate");
        boolean isAvailable = rs.getBoolean("isAvailable");

        core.RoomType type = core.RoomType.valueOf(typeStr);

        Room room = new Room(type, nightlyRate, roomNumber);
        room.setAvailable(isAvailable);

        return room;
    }
}
