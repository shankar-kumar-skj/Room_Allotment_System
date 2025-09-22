package ui;

import dao.AllocationDAO;
import dao.StudentDAO;
import dao.RoomDAO;
import model.Allocation;
import model.Room;
import model.Student;
import model.User;

import java.awt.*;
import java.awt.event.*;

public class StudentDashboard extends Frame {
    private User currentUser;
    private StudentDAO studentDAO = new StudentDAO();
    private AllocationDAO allocationDAO = new AllocationDAO();
    private RoomDAO roomDAO = new RoomDAO();

    public StudentDashboard(User user) {
        super("Student Dashboard - " + user.getUsername());
        this.currentUser = user;
        setSize(480,300);
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ dispose(); System.exit(0); }});

        // For demo: try to find student by matching username to student's rollno or email. This is simplified.
        // In production map user -> student via a FK in users table (not implemented here).
        Label lbl = new Label("Student info (if mapped):");
        add(lbl, BorderLayout.NORTH);

        TextArea info = new TextArea();
        add(info, BorderLayout.CENTER);

        Button btnRefresh = new Button("Refresh Allocation");
        add(btnRefresh, BorderLayout.SOUTH);

        btnRefresh.addActionListener(ae -> {
            // attempt to find a student by roll/email matching username (best-effort demo)
            // try by email first:
            Student found = null;
            for (Student s : studentDAO.getAllStudents()) {
                if (s.getEmail() != null && s.getEmail().equalsIgnoreCase(user.getUsername())) { found = s; break; }
                if (s.getRollNo() != null && s.getRollNo().equalsIgnoreCase(user.getUsername())) { found = s; break; }
            }
            if (found == null) {
                info.setText("No student profile mapped to user: " + user.getUsername() + "\nAsk admin to map your account.");
                return;
            }
            Allocation alloc = allocationDAO.getAllocationByStudent(found.getId());
            if (alloc == null) {
                info.setText("Student: " + found.getName() + "\nRoll: " + found.getRollNo() + "\nDepartment: " + found.getDepartment() + "\n\nNo allocation assigned.");
            } else {
                Room r = roomDAO.getRoomById(alloc.getRoomId());
                info.setText("Student: " + found.getName() + "\nRoll: " + found.getRollNo() + "\nDepartment: " + found.getDepartment() +
                             "\n\nAllocated Room: " + (r != null ? r.getRoomNo() + " - " + r.getDetails() : "Room data missing") +
                             "\nAllocated On: " + alloc.getAllocatedOn());
            }
        });

        // initial refresh
        btnRefresh.dispatchEvent(new ActionEvent(btnRefresh, ActionEvent.ACTION_PERFORMED, "init"));
        setVisible(true);
    }
}
