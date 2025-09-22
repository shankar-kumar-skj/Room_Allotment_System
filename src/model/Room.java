package model;

public class Room {
    private int id;
    private String roomNo;
    private int capacity;
    private String details;

    public Room() {}

    public Room(int id, String roomNo, int capacity, String details) {
        this.id = id;
        this.roomNo = roomNo;
        this.capacity = capacity;
        this.details = details;
    }

    public Room(String roomNo, int capacity, String details) {
        this(0, roomNo, capacity, details);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
