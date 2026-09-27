import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {

        setTitle("Railway Reservation System - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        JLabel titleLabel =
                new JLabel("Railway Reservation System");

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        JLabel usernameLabel =
                new JLabel("Username:");

        JLabel passwordLabel =
                new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton =
                new JButton("Login");

        JButton registerButton =
                new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);
        panel.add(registerButton);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> {
            new RegisterGUI();
            dispose();
        });

        setVisible(true);
    }

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password."
            );

            return;
        }

        UserDAO userDAO = new UserDAO();

        User user =
                userDAO.loginUser(username, password);

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!"
            );

            new DashboardGUI(user);
            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password."
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                LoginGUI::new
        );
    }
}