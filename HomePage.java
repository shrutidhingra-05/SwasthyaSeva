// HomePage.java

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;

public class HomePage extends JFrame {

    public HomePage() {

        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ======================== FOOTER =============================
        JPanel finalBar = new JPanel(new GridLayout(1, 3));
        finalBar.setBackground(new Color(15, 148, 139));
        finalBar.setBorder(new EmptyBorder(6, 10, 6, 10));

        String[] footerItems = {
                "ABOUT US",
                "CONTACT US",
                "FAQs",
                "https://mohfw.gov.in/"
        };

        for (String item : footerItems) {

            JButton footerBtn = new JButton(item);
            footerBtn.setFocusPainted(false);
            footerBtn.setBackground(new Color(15, 148, 139));
            footerBtn.setForeground(Color.WHITE);
            footerBtn.setOpaque(true);
            footerBtn.setContentAreaFilled(true);
            footerBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

            footerBtn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    footerBtn.setBackground(new Color(10, 120, 110));
                }

                public void mouseExited(MouseEvent evt) {
                    footerBtn.setBackground(new Color(15, 148, 139));
                }
            });

            footerBtn.addActionListener(e -> {
                if (item.equals("CONTACT US"))
                    System.out.println("Contact Us clicked");
                else if (item.equals("FAQs"))
                    System.out.println("FAQs clicked");
                else {
                    try {
                        Desktop.getDesktop().browse(new java.net.URI(item));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            finalBar.add(footerBtn);
        }

        // ======================== HEADER =============================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new MatteBorder(4, 0, 0, 0, new Color(180, 0, 200)));
        header.setPreferredSize(new Dimension(1200, 120));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        leftPanel.setBackground(Color.WHITE);

        ImageIcon rawIcon = new ImageIcon(getClass().getResource("logo.png"));
        Image scaledImage = rawIcon.getImage().getScaledInstance(
                160,
                100,
                Image.SCALE_SMOOTH
        );

        JLabel projectLogo = new JLabel(new ImageIcon(scaledImage));

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.WHITE);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("SwasthyaSeva");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel subtitle = new JLabel("Sehat Ka Digital Saathi");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(Color.GRAY);

        textPanel.add(title);
        textPanel.add(subtitle);

        leftPanel.add(projectLogo);
        leftPanel.add(textPanel);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 25));
        rightPanel.setBackground(Color.WHITE);

        JButton benLogin = new PillButton(
                "Beneficiary Login",
                new Color(205, 215, 255),
                new Color(170, 190, 255)
        );

        JButton adminLogin = new PillButton(
                "Admin Login",
                new Color(180, 215, 255),
                new Color(150, 190, 255)
        );

        rightPanel.add(benLogin);
        rightPanel.add(adminLogin);

        benLogin.addActionListener(e -> {
            new Login().setVisible(true);
        });

        adminLogin.addActionListener(e -> {
            new AdminLogin().setVisible(true);
        });

        try {

            ImageIcon gov1 = new ImageIcon(getClass().getResource("Govt 1.png"));
            Image scaled1 = gov1.getImage().getScaledInstance(
                    70,
                    45,
                    Image.SCALE_SMOOTH
            );
            rightPanel.add(new JLabel(new ImageIcon(scaled1)));

            ImageIcon gov2 = new ImageIcon(getClass().getResource("Govt 2.png"));
            Image scaled2 = gov2.getImage().getScaledInstance(
                    90,
                    45,
                    Image.SCALE_SMOOTH
            );
            rightPanel.add(new JLabel(new ImageIcon(scaled2)));

        } catch (Exception e) {

            rightPanel.add(new JLabel("Logo1 missing"));
            rightPanel.add(new JLabel("Logo2 missing"));
        }

        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);        
        // ======================== NAV BAR =============================
        String[] menu = {
                "Home",
                "CGHS At A Glance",
                "New Events",
                "Holidays",
                "Our Doctors",
                "Dispensaries",
                "Indent Medicine Update",
                "Card Renewal"
        };

        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        navBar.setBackground(new Color(0, 190, 170));

        for (String m : menu) {

            JButton navBtn = new JButton(m);
            navBtn.setFocusPainted(false);
            navBtn.setBackground(new Color(0, 190, 170));
            navBtn.setForeground(Color.WHITE);
            navBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            navBtn.addMouseListener(new MouseAdapter() {

                public void mouseEntered(MouseEvent evt) {
                    navBtn.setBackground(new Color(0, 160, 140));
                }

                public void mouseExited(MouseEvent evt) {
                    navBtn.setBackground(new Color(0, 190, 170));
                }
            });

            navBtn.addActionListener(e -> System.out.println(m + " clicked"));

            navBar.add(navBtn);
        }

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(header, BorderLayout.NORTH);
        topContainer.add(navBar, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);

        // ======================== BODY =============================
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Color.WHITE);

        JPanel blueArea = new JPanel(new BorderLayout());
        blueArea.setPreferredSize(new Dimension(1200, 400));
        blueArea.setBackground(new Color(179, 217, 255));

        SlideshowPanel slideshow = new SlideshowPanel();
        blueArea.add(slideshow, BorderLayout.CENTER);

        body.add(Box.createVerticalStrut(30));
        body.add(blueArea);

        // ================ MINISTER CARDS =================
        JPanel cardRow = new JPanel(new GridLayout(1, 3, 30, 0));
        cardRow.setBackground(Color.WHITE);
        cardRow.setBorder(new EmptyBorder(20, 40, 20, 40));

        cardRow.add(createCard(
                "SHRI JAGAT PRAKASH NADDA",
                "Hon'ble Minister of Health & Family Welfare and Chemicals & Fertilizers",
                "jagatprakashnadda.pdf"
        ));

        cardRow.add(createCard(
                "SHRI PRATAPRAO JADHAV",
                "Hon’ble Minister of State (Independent Charge) of Ministry of AYUSH and Minister of State of Ministry of Health & Family Welfare",
                null
        ));

        cardRow.add(createCard(
                "SMT. ANUPRIYA PATEL",
                "Hon'ble Minister of State for Health & Family Welfare and Chemicals and Fertilizers",
                "patel.pdf"
        ));

        body.add(cardRow);

        // ============MISSION & VISION ==================
        body.add(Box.createVerticalStrut(30));

        JPanel mvBand = new JPanel(new GridLayout(1, 2, 20, 20));
        mvBand.setBackground(new Color(203, 229, 255));
        mvBand.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        mvBand.add(createTextTile(
                "Mission",
                "To provide a seamless, transparent, and efficient digital healthcare system that simplifies appointment booking, medicine delivery, and CGHS services for beneficiaries while supporting doctors and administrators with reliable technological solutions."
        ));

        mvBand.add(createTextTile(
                "Vision",
                "To become a trusted and inclusive healthcare technology platform that enhances accessibility, improves service quality, and ensures timely, patient-centric healthcare delivery for every beneficiary."
        ));

        body.add(mvBand);
        body.add(Box.createVerticalStrut(40));
                // ======================== ABOUT =============================
        JPanel aboutPanel = new JPanel(new BorderLayout());
        aboutPanel.setBackground(Color.WHITE);
        aboutPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Heading
        JLabel aboutHeading = new JLabel("ABOUT US");
        aboutHeading.setFont(new Font("Arial", Font.BOLD, 22));
        aboutHeading.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Content
        JTextArea aboutText = new JTextArea(
                "SwasthyaSeva is a digital healthcare platform designed to simplify medical services "
                        + "for beneficiaries through technology. It enables easy appointment booking, medicine "
                        + "tracking, and CGHS card-related services in one place."
                        + "Our goal is to ensure faster, transparent, and hassle-free access to healthcare "
                        + "facilities. We strive to make quality healthcare more accessible, efficient, and "
                        + "user-friendly for all."
        );

        aboutText.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutText.setEditable(false);
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);
        aboutText.setBackground(Color.WHITE);
        aboutText.setBorder(new EmptyBorder(0, 5, 0, 5));

        aboutPanel.add(aboutHeading, BorderLayout.NORTH);
        aboutPanel.add(aboutText, BorderLayout.CENTER);

        body.add(aboutPanel);
        body.add(Box.createVerticalStrut(20));

        aboutText.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutText.setEditable(false);
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);

        aboutPanel.add(aboutText, BorderLayout.CENTER);

        body.add(aboutPanel);

        body.add(Box.createVerticalStrut(20));

        // ======================== FOOTER =============================
        body.add(finalBar);

        JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    // ************************ SLIDESHOW PANEL *************************
    class SlideshowPanel extends JPanel {

        private JLabel imageLabel;
        private JButton leftBtn, rightBtn;
        private Timer timer;
        private int index = 0;

        private String[] images = {
                "theme1.png",
                "theme2.png",
                "theme3.png"
        };

        public SlideshowPanel() {

            setLayout(new BorderLayout());

            JLayeredPane layeredPane = new JLayeredPane();
            add(layeredPane, BorderLayout.CENTER);

            imageLabel = new JLabel();
            imageLabel.setBounds(0, 0, 1200, 400);
            layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);

            leftBtn = makeArrowButton("◀");
            rightBtn = makeArrowButton("▶");

            leftBtn.setBounds(20, 170, 40, 40);
            rightBtn.setBounds(1140, 170, 40, 40);

            layeredPane.add(leftBtn, JLayeredPane.PALETTE_LAYER);
            layeredPane.add(rightBtn, JLayeredPane.PALETTE_LAYER);

            leftBtn.addActionListener(e -> prevImage());
            rightBtn.addActionListener(e -> nextImage());

            timer = new Timer(3000, e -> nextImage());
            timer.start();

            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    resizeComponents(layeredPane);
                    showImage(index);
                }
            });

            showImage(index);
        }
                private JButton makeArrowButton(String text) {

            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 28));
            btn.setForeground(Color.BLACK);
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);

            return btn;
        }

        private void resizeComponents(JLayeredPane pane) {

            int w = getWidth();
            int h = getHeight();

            imageLabel.setBounds(0, 0, w, h);
            leftBtn.setBounds(20, h / 2 - 20, 40, 40);
            rightBtn.setBounds(w - 60, h / 2 - 20, 40, 40);
        }

        private void nextImage() {
            index = (index + 1) % images.length;
            showImage(index);
        }

        private void prevImage() {
            index = (index - 1 + images.length) % images.length;
            showImage(index);
        }

        private void showImage(int i) {

            try {

                ImageIcon raw = new ImageIcon(getClass().getResource(images[i]));

                Image scaled = raw.getImage().getScaledInstance(
                        imageLabel.getWidth(),
                        imageLabel.getHeight(),
                        Image.SCALE_SMOOTH
                );

                imageLabel.setIcon(new ImageIcon(scaled));

            } catch (Exception e) {
                imageLabel.setText("Missing: " + images[i]);
            }
        }
    }

    // ************************ MINISTER CARD ***************************
    private JPanel createCard(String title, String subtitle, String pdfPath) {

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(400, 180));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(255, 153, 204), 3, true),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);

        String clean = title.toLowerCase();
        String imgPath = null;

        if (clean.contains("jagat"))
            imgPath = "jagatparakash.png";

        if (clean.contains("prataprao"))
            imgPath = "Prataprao.png";

        if (clean.contains("anupriya"))
            imgPath = "anupriya.png";

        try {

            ImageIcon original = new ImageIcon(getClass().getResource(imgPath));

            Image scaled = original.getImage().getScaledInstance(
                    120,
                    140,
                    Image.SCALE_SMOOTH
            );

            imgLabel.setIcon(new ImageIcon(scaled));

        } catch (Exception e) {
            imgLabel.setText("No Image");
        }

        JPanel imgPanel = new JPanel(new BorderLayout());
        imgPanel.setBackground(Color.WHITE);
        imgPanel.add(imgLabel, BorderLayout.CENTER);
                JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(0, 15, 0, 10));

        JLabel t = new JLabel("<html><b>" + title + "</b></html>");
        t.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel s = new JLabel("<html>" + subtitle + "</html>");
        s.setFont(new Font("Arial", Font.PLAIN, 12));

        rightPanel.add(t);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(s);
        rightPanel.add(Box.createVerticalGlue());

        rightPanel.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomBar.setBackground(Color.WHITE);

        if (pdfPath != null) {

            JButton viewBtn = new JButton("View Portfolio");
            viewBtn.setFocusPainted(false);
            viewBtn.setForeground(Color.BLACK);
            viewBtn.setBackground(new Color(255, 77, 170));
            viewBtn.setOpaque(true);
            viewBtn.setContentAreaFilled(true);
            viewBtn.setPreferredSize(new Dimension(140, 32));

            viewBtn.addMouseListener(new MouseAdapter() {

                public void mouseEntered(MouseEvent evt) {
                    viewBtn.setBackground(new Color(240, 40, 150));
                }

                public void mouseExited(MouseEvent evt) {
                    viewBtn.setBackground(new Color(255, 77, 170));
                }
            });

            // OPEN PDF FROM PROJECT FOLDER (resources root)
            viewBtn.addActionListener(e -> {

                try {

                    URL pdfURL = getClass().getResource("/CGHSTokenBooking/" + pdfPath);

                    if (pdfURL == null) {
                        JOptionPane.showMessageDialog(card,
                                "PDF not found: " + pdfPath);
                        return;
                    }

                    File pdfFile = new File(pdfURL.toURI());
                    Desktop.getDesktop().open(pdfFile);

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(card,
                            "Unable to open PDF");
                    ex.printStackTrace();
                }
            });

            bottomBar.add(viewBtn);
        }

        rightPanel.add(bottomBar);

        card.add(imgPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.CENTER);

        return card;
    }

    // *********************************************************************
    private JPanel createTextTile(String title, String bodyText) {

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel h = new JLabel(title);
        h.setBorder(new EmptyBorder(8, 8, 0, 8));
        h.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea ta = new JTextArea(bodyText);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setEditable(false);
        ta.setBackground(Color.WHITE);
        ta.setBorder(new EmptyBorder(8, 12, 8, 12));

        p.add(h, BorderLayout.NORTH);
        p.add(ta, BorderLayout.CENTER);

        return p;
    }

    // *********************************************************************
    class PillButton extends JButton {
                public PillButton(String text, Color normal, Color hover) {

            super(text);

            setBackground(normal);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
            setFont(new Font("Arial", Font.BOLD, 14));

            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(hover);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(normal);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No border
        }

        @Override
        public boolean isContentAreaFilled() {
            return false;
        }
    }

    // ************************ MAIN *************************
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new HomePage().setVisible(true);
        });
    }
}