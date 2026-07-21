
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminLogin extends JFrame {

    private JTextField adminField;
    private JPasswordField passField;
    private JLabel errorLabel;

    public AdminLogin() {

        setTitle("Admin Login | SwasthyaSeva");
        setSize(420, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // ----------- LOGO -----------
        ImageIcon rawIcon = new ImageIcon(getClass().getResource("logo.png"));

        Image img = rawIcon.getImage().getScaledInstance(
                160,
                100,
                Image.SCALE_SMOOTH
        );

        JLabel logo = new JLabel(new ImageIcon(img));
        logo.setBounds(130, 20, 160, 120);
        add(logo);

        // ----------- TITLE -----------
        JLabel title = new JLabel("SWASTHYA SEVA", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 30));
        title.setBounds(50, 130, 320, 40);
        add(title);

        JLabel subtitle = new JLabel("Admin Panel Access", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        subtitle.setBounds(70, 170, 280, 20);
        add(subtitle);

        // ----------- LOGIN TEXT -----------
        JLabel loginText = new JLabel("Admin Login");
        loginText.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginText.setBounds(150, 240, 150, 30);
        add(loginText);

        JLabel desc = new JLabel("Sign in to continue.");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setForeground(Color.GRAY);
        desc.setBounds(145, 270, 200, 20);
        add(desc);

        // ----------- USERNAME -----------
        JLabel userLbl = new JLabel("USERNAME");
        userLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        userLbl.setForeground(Color.GRAY);
        userLbl.setBounds(70, 320, 200, 15);
        add(userLbl);

        adminField = new JTextField();
        adminField.setBounds(70, 340, 280, 45);
        adminField.setBackground(new Color(230, 230, 230));
        adminField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        adminField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(adminField);

        // ----------- PASSWORD -----------
        JLabel passLbl = new JLabel("PASSWORD");
        passLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        passLbl.setForeground(Color.GRAY);
        passLbl.setBounds(70, 400, 200, 15);
        add(passLbl);

        passField = new JPasswordField();
        passField.setBounds(70, 420, 280, 45);
        passField.setBackground(new Color(230, 230, 230));
        passField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        add(passField);

        // ----------- LOGIN BUTTON -----------
        JButton loginBtn = new JButton("Log in");
        loginBtn.setBounds(70, 490, 280, 45);
        loginBtn.setBackground(new Color(108, 201, 186));
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        add(loginBtn);

        // ----------- ERROR LABEL -----------
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setBounds(70, 545, 280, 20);
        errorLabel.setForeground(Color.RED);
        add(errorLabel);

        // ----------- ACTION -----------
        loginBtn.addActionListener(e -> performLogin());
    }

    // ----------- LOGIN LOGIC -----------
    private void performLogin() {

        String username = adminField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password");
            return;
        }

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cghs_db",
                    "root",
                    "Kt@workbench25"
            );

            String sql =
                    "SELECT * FROM Admin WHERE username=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                dispose();
                new AdminDashboard().setVisible(true);
            } else {
                errorLabel.setText("Invalid username or password!");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            errorLabel.setText("Database connection error");
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new AdminLogin().setVisible(true)
        );
    }
}