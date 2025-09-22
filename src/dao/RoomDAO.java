package dao;

import db.DatabaseConnection;
import model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    public boolean insertRoom(Room r) {
        String sql = "INSERT INTO rooms (room_no, capacity, details) VALUES (?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, r.getRoomNo());
            ps.setInt(2, r.getCapacity());
            ps.setString(3, r.getDetails());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateRoom(Room r) {
        String sql = "UPDATE rooms SET room_no=?, capacity=?, details=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, r.getRoomNo());
            ps.setInt(2, r.getCapacity());
            ps.setString(3, r.getDetails());
            ps.setInt(4, r.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteRoom(int id) {
        String sql = "DELETE FROM rooms WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public Room getRoomById(int id) {
        String sql = "SELECT * FROM rooms WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Room(rs.getInt("id"), rs.getString("room_no"), rs.getInt("capacity"), rs.getString("details"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Room(rs.getInt("id"), rs.getString("room_no"), rs.getInt("capacity"), rs.getString("details")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
