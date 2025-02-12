package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private JButton showPasswordButton;
    private boolean isPasswordVisible = false;

    public LoginFrame() {
        setTitle("Login");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        showPasswordButton = new JButton("\uD83D\uDC41");

        JPanel loginPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Login");
        messageLabel = new JLabel("", SwingConstants.CENTER);

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(showPasswordButton, BorderLayout.EAST);
        add(passwordPanel);

        loginPanel.add(loginButton);
        add(loginPanel);
        add(messageLabel);

        showPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('•');
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void authenticate() {
        JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new DashboardFrame();
    }
}

class DashboardFrame extends JFrame {
    public DashboardFrame() {
        setTitle("Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Welcome to Timetable!");
        add(label);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
