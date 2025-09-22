package model;

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String role;
    private int priority;

    public User() {}

    public User(int id, String username, String passwordHash, String role, int priority) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.priority = priority;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
}
