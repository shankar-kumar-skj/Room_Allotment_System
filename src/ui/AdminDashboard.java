package ui;

import dao.AllocationDAO;
import dao.RoomDAO;
import dao.StudentDAO;
import model.Room;
import model.Student;
import model.User;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminDashboard extends Frame {
    private User currentUser;
    private StudentDAO studentDAO = new StudentDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private AllocationDAO allocationDAO = new AllocationDAO();

    public AdminDashboard(User user) {
        super("Admin / HOD Dashboard - " + user.getUsername());
        this.currentUser = user;

        setSize(700, 450);
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ dispose(); System.exit(0); }});

        Panel top = new Panel(new FlowLayout(FlowLayout.LEFT));
        top.add(new Label("Logged in as: " + user.getUsername() + " [" + user.getRole() + "]"));
        add(top, BorderLayout.NORTH);

        Panel center = new Panel(new GridLayout(1,2));

        // Left - Students
        Panel left = new Panel(new BorderLayout());
        left.add(new Label("Students"), BorderLayout.NORTH);
        List<Student> students = studentDAO.getAllStudents();
        java.awt.List lstStudents = new java.awt.List();
        for (Student s : students) lstStudents.add(s.getId() + " - " + s.getName() + " (" + s.getRollNo() + ")");
        left.add(lstStudents, BorderLayout.CENTER);

        Panel leftBtns = new Panel(new FlowLayout());
        Button btnAddStudent = new Button("Add Student");
        Button btnDeleteStudent = new Button("Delete Selected");
        leftBtns.add(btnAddStudent);
        leftBtns.add(btnDeleteStudent);
        left.add(leftBtns, BorderLayout.SOUTH);

        // Right - Rooms
        Panel right = new Panel(new BorderLayout());
        right.add(new Label("Rooms"), BorderLayout.NORTH);
        java.awt.List lstRooms = new java.awt.List();
        for (Room r : roomDAO.getAllRooms()) lstRooms.add(r.getId() + " - " + r.getRoomNo() + " (cap:" + r.getCapacity()+")");
        right.add(lstRooms, BorderLayout.CENTER);

        Panel rightBtns = new Panel(new FlowLayout());
        Button btnAddRoom = new Button("Add Room");
        Button btnDeleteRoom = new Button("Delete Selected");
        Button btnAllot = new Button("Allot Room");
        rightBtns.add(btnAddRoom);
        rightBtns.add(btnDeleteRoom);
        rightBtns.add(btnAllot);
        right.add(rightBtns, BorderLayout.SOUTH);

        center.add(left);
        center.add(right);
        add(center, BorderLayout.CENTER);

        // Button actions
        btnAddStudent.addActionListener(ae -> {
            TextField name = new TextField();
            TextField roll = new TextField();
            TextField dept = new TextField();
            TextField email = new TextField();
            Panel p = new Panel(new GridLayout(4,2));
            p.add(new Label("Name:")); p.add(name);
            p.add(new Label("Roll No:")); p.add(roll);
            p.add(new Label("Department:")); p.add(dept);
            p.add(new Label("Email:")); p.add(email);
            Dialog d = new Dialog(this, "Add Student", true);
            d.setLayout(new BorderLayout());
            d.add(p, BorderLayout.CENTER);
            Button ok = new Button("Save");
            ok.addActionListener(ev -> {
                studentDAO.insertStudent(new Student(name.getText(), roll.getText(), dept.getText(), email.getText()));
                d.dispose();
                this.refresh();
            });
            d.add(ok, BorderLayout.SOUTH);
            d.setSize(400, 250);
            d.setVisible(true);
        });

        btnDeleteStudent.addActionListener(ae -> {
            String sel = lstStudents.getSelectedItem();
            if (sel == null) return;
            int id = Integer.parseInt(sel.split(" - ")[0]);
            studentDAO.deleteStudent(id);
            refresh();
        });

        btnAddRoom.addActionListener(ae -> {
            TextField rno = new TextField();
            TextField cap = new TextField();
            TextField det = new TextField();
            Panel p = new Panel(new GridLayout(3,2));
            p.add(new Label("Room No:")); p.add(rno);
            p.add(new Label("Capacity:")); p.add(cap);
            p.add(new Label("Details:")); p.add(det);
            Dialog d = new Dialog(this, "Add Room", true);
            Button ok = new Button("Save");
            ok.addActionListener(ev -> {
                try {
                    int capacity = Integer.parseInt(cap.getText());
                    roomDAO.insertRoom(new Room(rno.getText(), capacity, det.getText()));
                } catch (NumberFormatException ex) { System.err.println("Invalid capacity"); }
                d.dispose();
                refresh();
            });
            d.add(p, BorderLayout.CENTER);
            d.add(ok, BorderLayout.SOUTH);
            d.setSize(400, 220);
            d.setVisible(true);
        });

        btnDeleteRoom.addActionListener(ae -> {
            String sel = lstRooms.getSelectedItem();
            if (sel == null) return;
            int id = Integer.parseInt(sel.split(" - ")[0]);
            roomDAO.deleteRoom(id);
            refresh();
        });

        btnAllot.addActionListener(ae -> new AllocationFrame(this).setVisible(true));
    }

    private void refresh() {
        // recreate window to refresh lists (quick approach)
        this.dispose();
        new AdminDashboard(currentUser).setVisible(true);
    }
}
