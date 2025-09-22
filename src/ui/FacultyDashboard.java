package ui;

import model.User;

import java.awt.*;
import java.awt.event.*;

public class FacultyDashboard extends Frame {
    private User user;

    public FacultyDashboard(User user) {
        super("Faculty Dashboard - " + user.getUsername());
        this.user = user;
        setSize(500, 300);
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ dispose(); System.exit(0); }});

        add(new Label("Faculty / HOD functions are available in Admin dashboard (limited)."), BorderLayout.CENTER);
        setVisible(true);
    }
}
