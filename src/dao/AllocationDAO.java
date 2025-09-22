package dao;

import db.DatabaseConnection;
import model.Allocation;

import java.sql.*;

public class AllocationDAO {
    public boolean assignRoom(int studentId, int roomId) {
        String deleteExisting = "DELETE FROM allocations WHERE student_id = ?";
        String insert = "INSERT INTO allocations (student_id, room_id) VALUES (?, ?)";

        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement psDel = c.prepareStatement(deleteExisting)) {
                psDel.setInt(1, studentId);
                psDel.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(insert)) {
                ps.setInt(1, studentId);
                ps.setInt(2, roomId);
                ps.executeUpdate();
            }
            c.commit();
            c.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try { /* try to rollback if possible */ } catch (Exception ex) {}
            return false;
        }
    }

    public boolean unassignRoom(int studentId) {
        String sql = "DELETE FROM allocations WHERE student_id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public Allocation getAllocationByStudent(int studentId) {
        String sql = "SELECT * FROM allocations WHERE student_id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Allocation(rs.getInt("id"), rs.getInt("student_id"), rs.getInt("room_id"), rs.getTimestamp("allocated_on"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
