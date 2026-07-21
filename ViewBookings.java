
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ViewBookings extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public ViewBookings() {

        setTitle("All Bookings");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        model = new DefaultTableModel(
                new Object[]{
                        "CGHS ID",
                        "Patient",
                        "Doctor",
                        "Date",
                        "Time Slot",
                        "Status"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btns = new JPanel();

        JButton approve = new JButton("Approve");
        JButton reject = new JButton("Reject");
        JButton delete = new JButton("Delete");
        JButton refresh = new JButton("Refresh");

        btns.add(approve);
        btns.add(reject);
        btns.add(delete);
        btns.add(refresh);

        add(btns, BorderLayout.SOUTH);

        loadBookings();

        approve.addActionListener(e -> updateSelectedStatus("Approved"));
        reject.addActionListener(e -> updateSelectedStatus("Rejected"));
        delete.addActionListener(e -> deleteSelected());
        refresh.addActionListener(e -> loadBookings());
    }

    private void loadBookings() {

        model.setRowCount(0);

        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            JOptionPane.showMessageDialog(this, "DB connection failed.");
            return;
        }

        // Correct query – no id column
        String sql =
                "SELECT cghs_id, patient_name, doctor_name, booking_date, time_slot, status " +
                "FROM bookings ORDER BY cghs_id DESC";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getString("cghs_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getDate("booking_date"),
                        rs.getString("time_slot"),
                        rs.getString("status")
                });
            }

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading bookings: " + ex.getMessage()
            );
        }
    }

    private void updateSelectedStatus(String newStatus) {

        int r = table.getSelectedRow();

        if (r == -1) {
            JOptionPane.showMessageDialog(this, "Select a booking first.");
            return;
        }

        // Primary key is VARCHAR
        String cghsId = (String) model.getValueAt(r, 0);

        Connection conn = DBConnection.getConnection();

        String sql =
                "UPDATE bookings SET status=? WHERE cghs_id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setString(2, cghsId);

            ps.executeUpdate();

            model.setValueAt(newStatus, r, 5);

            JOptionPane.showMessageDialog(
                    this,
                    "Status updated to " + newStatus
            );

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Update failed: " + ex.getMessage()
            );
        }
    }

    private void deleteSelected() {

        int r = table.getSelectedRow();

        if (r == -1) {
            JOptionPane.showMessageDialog(this, "Select a booking first.");
            return;
        }

        String cghsId = (String) model.getValueAt(r, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete booking with CGHS ID " + cghsId + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION)
            return;

        Connection conn = DBConnection.getConnection();

        String sql = "DELETE FROM bookings WHERE cghs_id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cghsId);

            ps.executeUpdate();

            model.removeRow(r);

            JOptionPane.showMessageDialog(this, "Deleted.");

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Delete failed: " + ex.getMessage()
            );
        }
    }
}