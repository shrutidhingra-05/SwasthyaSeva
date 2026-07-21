
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

//import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class BookingForm extends JFrame {

    private JTextField patientField;
    private JComboBox<String> doctorBox;
    private JSpinner dateSpinner;
    private JComboBox<String> timeBox;
    private JTextField cghsIdField;

    private Dashboard dashboard;
    private String cghsId;

    // ALL AVAILABLE SLOTS
    private final String[] allSlots = {
            "09:00 AM - 09:15 AM",
            "09:15 AM - 09:30 AM",
            "09:30 AM - 09:45 AM",
            "10:00 AM - 10:15 AM",
            "10:15 AM - 10:30 AM",
            "10:30 AM - 10:45 AM",
            "10:45 AM - 11:00 AM",
            "11:00 AM - 11:15 AM",
            "11:15 AM - 11:30 AM",
            "11:30 AM - 11:45 AM",
            "11:45 AM - 12:00 PM",
            "12:00 PM - 12:15 PM",
            "12:15 PM - 12:30 PM",
            "12:30 PM - 12:45 PM",
            "12:45 PM - 01:00 PM"
    };

    public BookingForm(Dashboard dashboard, String cghsId) {

        this.dashboard = dashboard;
        this.cghsId = cghsId;

        setTitle("Book Token");
        setSize(500, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // CGHS ID
        form.add(new JLabel("CGHS ID:"));
        cghsIdField = new JTextField(cghsId);
        cghsIdField.setEditable(false);
        form.add(cghsIdField);

        // Patient Name
        form.add(new JLabel("Patient Name:"));
        patientField = new JTextField();
        form.add(patientField);

        // Doctor
        form.add(new JLabel("Select Doctor:"));
        doctorBox = new JComboBox<>(new String[]{
                "Dr. Sharma (Physician)",
                "Dr. Gupta (ENT)",
                "Dr. Mehta (Dermatologist)",
                "Dr. Singh (Cardiologist)"
        });
        form.add(doctorBox);

        // Date
        form.add(new JLabel("Select Date:"));

        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor =
                new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        dateSpinner.setValue(java.sql.Date.valueOf(LocalDate.now()));

        form.add(dateSpinner);

        // Time Slot
        form.add(new JLabel("Time Slot:"));

        timeBox = new JComboBox<>();
        applySlotRenderer();

        form.add(timeBox);

        add(form, BorderLayout.CENTER);

        // Buttons
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));

        JButton bookBtn = new JButton("Book Token");
        JButton backBtn = new JButton("Back to Home");

        btns.add(bookBtn);
        btns.add(backBtn);

        add(btns, BorderLayout.SOUTH);

        // LOAD INITIALLY
        loadBookedSlots();

        // RELOAD ON DATE CHANGE
        dateSpinner.addChangeListener(e -> loadBookedSlots());

        bookBtn.addActionListener(e -> saveBooking());
        backBtn.addActionListener(e -> dispose());
    }

    // RESET ALL SLOTS
    private void resetSlots() {

        timeBox.removeAllItems();

        for (String slot : allSlots) {
            timeBox.addItem(slot);
        }
    }

    // LOAD BOOKED SLOTS FOR SELECTED DATE
    private void loadBookedSlots() {

        resetSlots();

        Connection conn = DBConnection.getConnection();

        if (conn == null)
            return;

        try {

            String sql =
                    "SELECT time_slot FROM bookings WHERE booking_date = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            java.util.Date d = (java.util.Date) dateSpinner.getValue();
            ps.setDate(1, new Date(d.getTime()));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String bookedSlot = rs.getString("time_slot");

                for (int i = 0; i < timeBox.getItemCount(); i++) {

                    if (timeBox.getItemAt(i).equals(bookedSlot)) {

                        timeBox.removeItemAt(i);
                        timeBox.insertItemAt(bookedSlot + " (BOOKED)", i);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        // GREY OUT BOOKED SLOTS
    private void applySlotRenderer() {

        timeBox.setRenderer(new BasicComboBoxRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(
                        list,
                        value,
                        index,
                        isSelected,
                        cellHasFocus
                );

                if (value != null && value.toString().contains("BOOKED")) {
                    setForeground(Color.GRAY);
                    setEnabled(false);
                } else {
                    setForeground(Color.BLACK);
                    setEnabled(true);
                }

                return this;
            }
        });
    }

    // SAVE BOOKING
    private void saveBooking() {

        String userId = cghsIdField.getText().trim();
        String name = patientField.getText().trim();
        String doctor = (String) doctorBox.getSelectedItem();

        java.util.Date d = (java.util.Date) dateSpinner.getValue();
        Date sqlDate = new Date(d.getTime());

        String slot = (String) timeBox.getSelectedItem();

        if (slot == null || slot.contains("BOOKED")) {

            JOptionPane.showMessageDialog(
                    this,
                    "This slot is already booked!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter patient name.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Connection conn = DBConnection.getConnection();

        if (conn == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database connection error.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String sql =
                "INSERT INTO bookings (cghs_id, patient_name, doctor_name, booking_date, time_slot, status) " +
                "VALUES (?, ?, ?, ?, ?, 'Pending')";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ps.setString(2, name);
            ps.setString(3, doctor);
            ps.setDate(4, sqlDate);
            ps.setString(5, slot);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "✅ Token booked successfully!"
            );

            if (dashboard != null) {
                dashboard.loadBookingStatus(userId);
            }

            dispose();

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error booking token: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
