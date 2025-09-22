package ui;

import dao.UserDAO;
import model.User;
import util.ValidationUtil;

import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends Frame {
    private TextField tfUsername;
    private TextField tfPassword;
    private Button btnLogin;
    private Label lblStatus;
    private UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        super("College Room Allotment - Login");
        setSize(360, 200);
        setLayout(new GridLayout(5, 1));
        addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ dispose(); System.exit(0); }});

        Panel p1 = new Panel(new FlowLayout());
        p1.add(new Label("Username:"));
        tfUsername = new TextField(20);
        p1.add(tfUsername);
        add(p1);

        Panel p2 = new Panel(new FlowLayout());
        p2.add(new Label("Password:"));
        tfPassword = new TextField(20);
        tfPassword.setEchoChar('*');
        p2.add(tfPassword);
        add(p2);

        Panel p3 = new Panel(new FlowLayout());
        btnLogin = new Button("Login");
        p3.add(btnLogin);
        add(p3);

        lblStatus = new Label("", Label.CENTER);
        add(lblStatus);

        btnLogin.addActionListener(e -> doLogin());

        // Enter key handling
        tfPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) doLogin();
            }
        });

        setVisible(true);
    }

    private void doLogin() {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        if (ValidationUtil.isEmpty(username) || ValidationUtil.isEmpty(password)) {
            lblStatus.setText("Please enter username and password.");
            return;
        }

        User user = userDAO.validateUser(username.trim(), password);
        if (user == null) {
            lblStatus.setText("Invalid credentials.");
            return;
        }

        lblStatus.setText("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
        // Redirect by role:
        EventQueue.invokeLater(() -> {
            switch (user.getRole()) {
                case "Admin":
                    new AdminDashboard(user).setVisible(true);
                    break;
                case "HOD":
                    new AdminDashboard(user).setVisible(true); // HOD uses admin panel but limited functionality inside
                    break;
                case "Student":
                    new StudentDashboard(user).setVisible(true);
                    break;
                default:
                    new FacultyDashboard(user).setVisible(true);
            }
            this.setVisible(false);
            this.dispose();
        });
    }

    // For AWT, prefer setVisible(true) in each Frame's constructor
}
