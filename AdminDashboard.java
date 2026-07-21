
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {

    // Status Labels
    private JLabel totalBookingsLabel, pendingLabel, completedLabel, cancelledLabel;

    public AdminDashboard() {

        setTitle("Admin Dashboard | SwasthyaSeva");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ================= TOP CONTAINER (Header + Navbar) =================
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(Color.WHITE);
        topContainer.add(createHeader(), BorderLayout.NORTH);
        topContainer.add(createNavBar(), BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);

        // ================= MAIN CONTENT PANEL =================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(220, 235, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Metrics Panel
        JPanel metricsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        metricsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        metricsPanel.setBackground(new Color(220, 235, 255));

        totalBookingsLabel = new JLabel("0", SwingConstants.CENTER);
        pendingLabel = new JLabel("0", SwingConstants.CENTER);
        completedLabel = new JLabel("0", SwingConstants.CENTER);
        cancelledLabel = new JLabel("0", SwingConstants.CENTER);

        metricsPanel.add(createCard(
                "Total Bookings",
                totalBookingsLabel,
                new Color(0, 180, 165)
        ));

        metricsPanel.add(createCard(
                "Pending",
                pendingLabel,
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

        mainPanel.add(metricsPanel, BorderLayout.NORTH);

        // Placeholder Admin Panel Image/Text
        JLabel adminImage = new JLabel("ADMIN PANEL", JLabel.CENTER);
        adminImage.setFont(new Font("Arial", Font.BOLD, 40));
        adminImage.setForeground(new Color(0, 90, 160));

        mainPanel.add(adminImage, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // ================= LOAD METRICS =================
        loadMetrics();

        setVisible(true);
    }

    // ================= HEADER CREATOR =================
    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1200, 110));
        header.setBorder(new MatteBorder(4, 0, 0, 0, new Color(0, 130, 120)));

        // LEFT PANEL
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftPanel.setBackground(Color.WHITE);

        JLabel projectLogo = loadImage("logo.png", 60, 60);

        Box titleBox = Box.createVerticalBox();

        JLabel title = new JLabel("SwasthyaSeva Admin");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.BLACK);

        JLabel subtitle = new JLabel("Admin Panel");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(Color.GRAY);

        titleBox.add(title);
        titleBox.add(subtitle);

        leftPanel.add(projectLogo);
        leftPanel.add(titleBox);

        // CENTER PANEL
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        JLabel welcome = new JLabel("Welcome, Admin", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 26));
        welcome.setForeground(new Color(0, 130, 120));

        centerPanel.add(welcome);

        // RIGHT PANEL
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

    // ================= NAVBAR CREATOR =================
    private JPanel createNavBar() {

        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        navBar.setBackground(new Color(0, 180, 165));

        String[] menu = {
                "View Bookings",
                "Manage Bookings",
                "Generate Reports",
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

                    case "View Bookings" ->
                            new ViewBookings().setVisible(true);

                    case "Manage Bookings" ->
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Manage Bookings Coming Soon!"
                            );

                    case "Generate Reports" ->
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Reports Feature Coming Soon!"
                            );

                    case "Profile" ->
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Admin Profile Coming Soon!"
                            );
                }
            });

            navBar.add(btn);
        }

        // LOGOUT BUTTON
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
            new HomePage().setVisible(true);
        });

        navBar.add(logoutBtn);

        return navBar;
    }

    // ================= METRICS CARD CREATOR =================
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

    // ================= LOAD METRICS =================
    private void loadMetrics() {

        // For now placeholder values; you can replace with DB queries
        totalBookingsLabel.setText("120");
        pendingLabel.setText("45");
        completedLabel.setText("60");
        cancelledLabel.setText("15");
    }

    // ================= IMAGE LOADER =================
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

    // ================= MAIN =================
    public static void main(String[] args) {
        new AdminDashboard().setVisible(true);
    }
}