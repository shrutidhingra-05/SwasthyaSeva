
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {

    private JTextField refIdField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    public Login() {

        setTitle("Login | SwasthyaSeva");
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
        logo.setBounds(150, 20, 180, 140);
        add(logo);

        // ----------- TITLE -----------
        JLabel title = new JLabel("<html><center>SWASTHYA SEVA</center></html>");
        title.setFont(new Font("Poppins", Font.BOLD, 30));
        title.setBounds(50, 135, 320, 70);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        JLabel subtitle = new JLabel("Sehat Ka Digital Saathi");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setBounds(80, 185, 250, 18);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(subtitle);

        // ----------- LOGIN TEXT -----------
        JLabel loginText = new JLabel("Login");
        loginText.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginText.setBounds(170, 250, 100, 30);
        add(loginText);

        JLabel desc = new JLabel("Sign in to continue.");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setForeground(Color.GRAY);
        desc.setBounds(145, 280, 200, 20);
        add(desc);

        // ----------- CGHS FIELD -----------
        JLabel refLabel = new JLabel("CGHS REFERENCE ID");
        refLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        refLabel.setForeground(Color.GRAY);
        refLabel.setBounds(70, 330, 200, 15);
        add(refLabel);

        refIdField = new JTextField();
        refIdField.setBounds(70, 350, 280, 45);
        refIdField.setBackground(new Color(230, 230, 230));
        refIdField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        add(refIdField);

        // ----------- PASSWORD FIELD -----------
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        passLabel.setForeground(Color.GRAY);
        passLabel.setBounds(70, 410, 200, 15);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(70, 430, 280, 45);
        passwordField.setBackground(new Color(230, 230, 230));
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        add(passwordField);

        // ----------- LOGIN BUTTON -----------
        JButton loginBtn = new JButton("Log in");
        loginBtn.setBounds(70, 500, 280, 45);
        loginBtn.setBackground(new Color(108, 201, 186));
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        add(loginBtn);

        // ----------- ERROR LABEL -----------
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setBounds(70, 550, 280, 20);
        errorLabel.setForeground(Color.RED);
        add(errorLabel);

        loginBtn.addActionListener(e -> validateLogin());
    }

    private void validateLogin() {

        String refId = refIdField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (refId.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter CGHS ID and Password");
            return;
        }

        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            errorLabel.setText("Database connection failed");
            return;
        }

        String sql = "SELECT * FROM beneficiaries WHERE cghs_id = ? AND password = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, refId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                dispose();
                new Dashboard(refId).setVisible(true);
            } else {
                errorLabel.setText("Invalid CGHS Reference ID or Password!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            errorLabel.setText("Database error occurred");
        }
    }
}