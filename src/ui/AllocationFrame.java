package ui;

import dao.AllocationDAO;
import dao.RoomDAO;
import dao.StudentDAO;
import model.Room;
import model.Student;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AllocationFrame extends Frame {
    private StudentDAO studentDAO = new StudentDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private AllocationDAO allocationDAO = new AllocationDAO();

    public AllocationFrame(Frame parent) {
        super("Allocate Room");
        setSize(560, 320);
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ dispose(); }});

        Panel top = new Panel(new GridLayout(1,2));
        List<Student> students = studentDAO.getAllStudents();
        java.awt.List lstStudents = new java.awt.List();
        for (Student s : students) lstStudents.add(s.getId() + " - " + s.getName() + " (" + s.getRollNo() + ")");
        top.add(new Panel(new BorderLayout()){ { add(new Label("Select Student"), BorderLayout.NORTH); add(lstStudents, BorderLayout.CENTER); }});

        List<Room> rooms = roomDAO.getAllRooms();
        java.awt.List lstRooms = new java.awt.List();
        for (Room r : rooms) lstRooms.add(r.getId() + " - " + r.getRoomNo() + " (cap:" + r.getCapacity()+")");
        top.add(new Panel(new BorderLayout()){ { add(new Label("Select Room"), BorderLayout.NORTH); add(lstRooms, BorderLayout.CENTER); }});

        add(top, BorderLayout.CENTER);

        Panel bottom = new Panel(new FlowLayout());
        Button btnAllot = new Button("Allot");
        Button btnUnassign = new Button("Unassign");
        bottom.add(btnAllot);
        bottom.add(btnUnassign);
        add(bottom, BorderLayout.SOUTH);

        btnAllot.addActionListener(ae -> {
            String sSel = lstStudents.getSelectedItem();
            String rSel = lstRooms.getSelectedItem();
            if (sSel == null || rSel == null) return;
            int sid = Integer.parseInt(sSel.split(" - ")[0]);
            int rid = Integer.parseInt(rSel.split(" - ")[0]);
            boolean ok = allocationDAO.assignRoom(sid, rid);
            Dialog d = new Dialog(this, "Result", true);
            d.setLayout(new FlowLayout());
            d.add(new Label(ok ? "Allotted successfully." : "Allotment failed."));
            Button close = new Button("Close");
            close.addActionListener(e -> d.dispose());
            d.add(close);
            d.setSize(220,120);
            d.setVisible(true);
        });

        btnUnassign.addActionListener(ae -> {
            String sSel = lstStudents.getSelectedItem();
            if (sSel == null) return;
            int sid = Integer.parseInt(sSel.split(" - ")[0]);
            boolean ok = allocationDAO.unassignRoom(sid);
            Dialog d = new Dialog(this, "Result", true);
            d.setLayout(new FlowLayout());
            d.add(new Label(ok ? "Unassigned." : "Nothing to unassign."));
            Button close = new Button("Close");
            close.addActionListener(e -> d.dispose());
            d.add(close);
            d.setSize(220,120);
            d.setVisible(true);
        });
    }
}
