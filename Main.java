import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            HomePage hp = new HomePage();
            hp.setVisible(true);

        });
    }
}