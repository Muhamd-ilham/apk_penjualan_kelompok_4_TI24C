package view;

import koneksi.KoneksiDB;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormLogin extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public FormLogin() {
        setTitle("Login - Aplikasi Penjualan");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(null); 

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(40, 40, 80, 25);
        add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(130, 40, 150, 25);
        add(txtUsername);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(40, 80, 80, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(130, 80, 150, 25);
        add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(130, 130, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesLogin();
            }
        });
    }

    private void prosesLogin() {
        String user = txtUsername.getText();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong!");
            return;
        }

        try {
            Connection conn = KoneksiDB.getKoneksi();
            // Query untuk mengecek data
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user);
            pst.setString(2, pass);
            
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(this, "Login Berhasil! Selamat datang, " + role);
                
                // Menutup form login dan membuka Menu Utama
                this.dispose(); 
                new MenuUtama().setVisible(true); 

            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            rs.close();
            pst.close();
            conn.close();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormLogin().setVisible(true);
        });
    }
}