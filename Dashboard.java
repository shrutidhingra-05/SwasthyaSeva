package CGHSTokenBooking;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;

public class Dashboard extends JFrame {

    // Status Labels
    private JLabel docLabel, dateLabel, timeLabel, statusLabel;

    // Metrics Labels
    private JLabel totalLabel, upcomingLabel, completedLabel, cancelledLabel;

    public Dashboard(String refId) {

        setTitle("Dashboard | SwasthyaSeva");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ================ TOP CONTAINER (Header + Navbar) ==================
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(Color.WHITE);

        // ======================== HEADER =============================
        JPanel header = createHeader(refId);
        topContainer.add(header, BorderLayout.NORTH);

        // ======================== NAVBAR =============================
        JPanel navBar = createNavBar(refId);
        topContainer.add(navBar, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);

        // ======================== MIDDLE PANEL =============================
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(new Color(210, 230, 255));

        // ===================== LEFT IMAGE PANEL ==================
        JLabel leftImage = loadImage("db.png", 500, 400);

        JPanel leftImagePanel = new JPanel();
        leftImagePanel.setBackground(new Color(210, 230, 255));
        leftImagePanel.add(leftImage);

        middlePanel.add(leftImagePanel, BorderLayout.CENTER);

        // ======================== METRICS CARDS ==========================
        JPanel metricsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        metricsPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        metricsPanel.setBackground(new Color(210, 230, 255));

        totalLabel = new JLabel("0", SwingConstants.CENTER);
        upcomingLabel = new JLabel("0", SwingConstants.CENTER);
        completedLabel = new JLabel("0", SwingConstants.CENTER);
        cancelledLabel = new JLabel("0", SwingConstants.CENTER);

        metricsPanel.add(createCard(
                "Total Bookings",
                totalLabel,
                new Color(0, 180, 165)
        ));

        metricsPanel.add(createCard(
                "Upcoming",
                upcomingLabel,
                new Color(255, 165, 0)
        ));

        metricsPanel.add(createCard(
                "Completed",
                completedLabel,
                new Color(0, 200, 100)
        ));

        metricsPanel.add(createCard(
                "Cancelled",
                cancelledLabel,
                new Color(200, 50, 50)
        ));

        middlePanel.add(metricsPanel, BorderLayout.NORTH);

        // ======================== STATUS PANEL ==========================
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(0, 180, 165),
                                2,
                                true
                        ),
                        "Your Latest Booking Status",
                        TitledBorder.CENTER,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 16),
                        new Color(0, 100, 100)
                )
        );

        statusPanel.setBackground(Color.WHITE);
        statusPanel.setPreferredSize(new Dimension(400, 220));
        statusPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        // Doctor Panel
        JPanel doctorPanel = createInfoPanel("🩺 Doctor:", "-");
        docLabel = (JLabel) doctorPanel.getComponent(1);
        statusPanel.add(doctorPanel);
        statusPanel.add(Box.createVerticalStrut(10));

        // Date Panel
        JPanel datePanel = createInfoPanel("📅 Date:", "-");
        dateLabel = (JLabel) datePanel.getComponent(1);
        statusPanel.add(datePanel);
        statusPanel.add(Box.createVerticalStrut(10));

        // Time Panel
        JPanel timePanel = createInfoPanel("⏰ Time Slot:", "-");
        timeLabel = (JLabel) timePanel.getComponent(1);
        statusPanel.add(timePanel);
        statusPanel.add(Box.createVerticalStrut(10));

        // Status Panel
        JPanel statusInfoPanel = createInfoPanel("✅ Status:", "-");
        statusLabel = (JLabel) statusInfoPanel.getComponent(1);
        statusPanel.add(statusInfoPanel);
        statusPanel.add(Box.createVerticalStrut(10));

        // Refresh Button
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBackground(new Color(108, 201, 186));
        refreshBtn.setFocusPainted(false);

        refreshBtn.addActionListener(e -> {
            loadBookingStatus(refId);
            loadMetrics(refId);
        });

        statusPanel.add(refreshBtn);

        middlePanel.add(statusPanel, BorderLayout.EAST);

        add(middlePanel, BorderLayout.CENTER);

        // ======================== LOAD DATA =============================
        loadMetrics(refId);
        loadBookingStatus(refId);

        setVisible(true);
    }

    // ======================== HEADER CREATOR ==========================
    private JPanel createHeader(String refId) {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1200, 110));
        header.setBorder(
                new MatteBorder(4, 0, 0, 0, new Color(180, 0, 200))
        );

        // LEFT
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftPanel.setBackground(Color.WHITE);

        JLabel projectLogo = loadImage("logo.png", 60, 60);

        Box titleBox = Box.createVerticalBox();

        JLabel title = new JLabel("SwasthyaSeva");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.BLACK);

        JLabel subtitle = new JLabel("Sehat Ka Digital Saathi");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(Color.GRAY);

        titleBox.add(title);
        titleBox.add(subtitle);

        leftPanel.add(projectLogo);
        leftPanel.add(titleBox);

        // CENTER
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        JLabel welcome = new JLabel(
                "Welcome, " + refId,
                SwingConstants.CENTER
        );

        welcome.setFont(new Font("Arial", Font.BOLD, 26));
        welcome.setForeground(new Color(0, 130, 120));

        centerPanel.add(welcome);

        // RIGHT
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setBackground(Color.WHITE);

        JLabel govLogo1 = loadImage("Govt 1.png", 60, 60);
        JLabel govLogo2 = loadImage("Govt 2.png", 60, 60);

        rightPanel.add(govLogo1);
        rightPanel.add(govLogo2);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(centerPanel, BorderLayout.CENTER);
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }
        // ======================== NAVBAR CREATOR ==========================
    private JPanel createNavBar(String refId) {

        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        navBar.setBackground(new Color(0, 180, 165));

        String[] menu = {
                "Home",
                "Prescription",
                "Past Bookings",
                "Token Booking",
                "Profile"
        };

        for (String item : menu) {

            JButton btn = new JButton(item);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(0, 180, 165));
            btn.setForeground(Color.WHITE);
            btn.setBorder(new EmptyBorder(5, 15, 5, 15));

            btn.addMouseListener(new java.awt.event.MouseAdapter() {

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(0, 150, 140));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(0, 180, 165));
                }
            });

            btn.addActionListener(e -> {

                switch (item) {

                    case "Home":
                        new HomePage().setVisible(true);
                        dispose();
                        break;

                    case "Prescription":
                        JOptionPane.showMessageDialog(
                                null,
                                "Prescription Clicked!"
                        );
                        break;

                    case "Past Bookings":
                        JOptionPane.showMessageDialog(
                                null,
                                "Past Bookings Clicked!"
                        );
                        break;

                    case "Token Booking":
                        new BookingForm(this, refId).setVisible(true);
                        break;

                    case "Profile":
                        JOptionPane.showMessageDialog(
                                null,
                                "Profile Clicked!"
                        );
                        break;
                }
            });

            navBar.add(btn);
        }

        // ---------------- LOGOUT BUTTON -----------------
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBackground(new Color(220, 20, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBorder(new EmptyBorder(5, 15, 5, 15));

        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(new Color(180, 0, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(new Color(220, 20, 60));
            }
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        navBar.add(logoutBtn);

        return navBar;
    }

    // ======================== CREATE METRICS CARD ==========================
    private JPanel createCard(String title, JLabel valueLabel, Color bgColor) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);

        return card;
    }

    // ======================== INFO PANEL CREATOR ==========================
    private JPanel createInfoPanel(String title, String value) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(200, 200, 200),
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(valueLabel, BorderLayout.EAST);

        return panel;
    }

    // ======================== LOAD BOOKING STATUS ==========================
    public void loadBookingStatus(String cghsId) {

        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            statusLabel.setText("DB connection failed");
            return;
        }

        String sql =
                "SELECT doctor_name, booking_date, time_slot, status " +
                "FROM bookings WHERE cghs_id=? ORDER BY booking_date DESC LIMIT 1";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cghsId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    docLabel.setText(rs.getString("doctor_name"));

                    Date d = rs.getDate("booking_date");
                    dateLabel.setText(d != null ? d.toString() : "-");

                    timeLabel.setText(rs.getString("time_slot"));

                    String stat = rs.getString("status");
                    statusLabel.setText(stat);

                    if ("Pending".equalsIgnoreCase(stat))
                        statusLabel.setForeground(Color.ORANGE);
                    else if ("Confirmed".equalsIgnoreCase(stat))
                        statusLabel.setForeground(new Color(0, 128, 0));
                    else if ("Cancelled".equalsIgnoreCase(stat))
                        statusLabel.setForeground(Color.RED);
                    else
                        statusLabel.setForeground(Color.BLACK);

                } else {

                    docLabel.setText("-");
                    dateLabel.setText("-");
                    timeLabel.setText("-");
                    statusLabel.setText("No bookings yet");
                    statusLabel.setForeground(Color.BLACK);
                }
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
            statusLabel.setText("Error");
            statusLabel.setForeground(Color.RED);
        }
    }
        // ======================== LOAD METRICS ==========================
    public void loadMetrics(String cghsId) {

        Connection conn = DBConnection.getConnection();

        if (conn == null)
            return;

        try {

            String totalSQL =
                    "SELECT COUNT(*) AS total FROM bookings WHERE cghs_id=?";

            String upcomingSQL =
                    "SELECT COUNT(*) AS upcoming FROM bookings WHERE cghs_id=? AND status='Pending'";

            String completedSQL =
                    "SELECT COUNT(*) AS completed FROM bookings WHERE cghs_id=? AND status='Completed'";

            String cancelledSQL =
                    "SELECT COUNT(*) AS cancelled FROM bookings WHERE cghs_id=? AND status='Cancelled'";

            PreparedStatement ps = conn.prepareStatement(totalSQL);
            ps.setString(1, cghsId);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                totalLabel.setText(String.valueOf(rs.getInt("total")));

            rs.close();
            ps.close();

            ps = conn.prepareStatement(upcomingSQL);
            ps.setString(1, cghsId);

            rs = ps.executeQuery();

            if (rs.next())
                upcomingLabel.setText(String.valueOf(rs.getInt("upcoming")));

            rs.close();
            ps.close();

            ps = conn.prepareStatement(completedSQL);
            ps.setString(1, cghsId);

            rs = ps.executeQuery();

            if (rs.next())
                completedLabel.setText(String.valueOf(rs.getInt("completed")));

            rs.close();
            ps.close();

            ps = conn.prepareStatement(cancelledSQL);
            ps.setString(1, cghsId);

            rs = ps.executeQuery();

            if (rs.next())
                cancelledLabel.setText(String.valueOf(rs.getInt("cancelled")));

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ======================== IMAGE LOADER =============================
    private JLabel loadImage(String fileName, int width, int height) {

        java.net.URL imgURL = getClass().getResource(fileName);

        if (imgURL == null)
            imgURL = getClass().getResource("/" + fileName);

        if (imgURL == null) {

            System.out.println("Image not found: " + fileName);
            return new JLabel("X");
        }

        ImageIcon icon = new ImageIcon(imgURL);

        Image img = icon.getImage().getScaledInstance(
                width,
                height,
                Image.SCALE_SMOOTH
        );

        return new JLabel(new ImageIcon(img));
    }

    // ======================== MAIN =============================
    public static void main(String[] args) {

        new Dashboard("CGHS1234").setVisible(true);
    }
}