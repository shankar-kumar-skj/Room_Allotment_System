package model;

import java.sql.Timestamp;

public class Allocation {
    private int id;
    private int studentId;
    private int roomId;
    private Timestamp allocatedOn;

    public Allocation() {}

    public Allocation(int id, int studentId, int roomId, Timestamp allocatedOn) {
        this.id = id;
        this.studentId = studentId;
        this.roomId = roomId;
        this.allocatedOn = allocatedOn;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public Timestamp getAllocatedOn() { return allocatedOn; }
    public void setAllocatedOn(Timestamp allocatedOn) { this.allocatedOn = allocatedOn; }
}
