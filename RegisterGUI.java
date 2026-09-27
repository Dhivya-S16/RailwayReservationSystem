import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterGUI() {

        setTitle("Railway Reservation System - Register");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back to Login");

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(registerButton);
        panel.add(backButton);

        add(panel);

        registerButton.addActionListener(e -> registerUser());

        backButton.addActionListener(e -> {
            new LoginGUI();
            dispose();
        });

        setVisible(true);
    }

    private void registerUser() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        UserDAO userDAO = new UserDAO();

        boolean success =
                userDAO.registerUser(username, password);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration successful!"
            );

            new LoginGUI();
            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed. Username may already exist."
            );
        }
    }
}