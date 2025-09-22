package model;

public class Student {
    private int id;
    private String name;
    private String rollNo;
    private String department;
    private String email;

    public Student() {}

    public Student(int id, String name, String rollNo, String department, String email) {
        this.id = id;
        this.name = name;
        this.rollNo = rollNo;
        this.department = department;
        this.email = email;
    }

    public Student(String name, String rollNo, String department, String email) {
        this(0, name, rollNo, department, email);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
