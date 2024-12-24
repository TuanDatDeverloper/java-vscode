package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.DatabaseConnection;

public class RegisterFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;
    private JLabel lblIcon;

    public RegisterFrame() {
        setTitle("Đăng ký tài khoản");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Thêm icon
        ImageIcon icon = new ImageIcon("Quanlyinternet/views/resources/icon.png");
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        setIconImage(img);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Thêm icon vào giao diện
        lblIcon = new JLabel(new ImageIcon(img));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblIcon, gbc);

        // Label và TextField cho Username
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblUsername, gbc);

        txtUsername = new JTextField(15);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtUsername, gbc);

        // Label và PasswordField cho Password
        JLabel lblPassword = new JLabel("Mật khẩu:");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblPassword, gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtPassword, gbc);

        // Buttons
        btnRegister = new JButton("Đăng ký");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnRegister, gbc);

        btnBack = new JButton("Quay lại");
        gbc.gridx = 2;
        add(btnBack, gbc);

        // Action Listeners
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerAction();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
    }

    private void registerAction() {
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword());

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Kiểm tra username đã tồn tại chưa
            String checkSql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác.");
                return;
            }

            // Tạo tài khoản mới
            String insertSql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'user')";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            dispose();
            new LoginFrame().setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi trong quá trình đăng ký!");
        }
    }
}