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

public class LoginFrame extends JFrame {
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin, btnRegister;
	private JLabel lblIcon; // Thêm JLabel cho icon

	public LoginFrame() {
		setTitle("Đăng nhập hệ thống");
		setSize(600, 400); // Tăng kích thước cửa sổ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());

		// Thêm icon đại diện
		ImageIcon icon = new ImageIcon("Quanlyinternet/views/resources/icon.png");
		Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		setIconImage(img);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		// Thêm JLabel cho icon
		lblIcon = new JLabel(new ImageIcon(img));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(lblIcon, gbc);

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

		JLabel lblPassword = new JLabel("Mật khẩu:");
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		add(lblPassword, gbc);
		txtPassword = new JPasswordField(15);
		gbc.gridx = 2;
		gbc.anchor = GridBagConstraints.WEST;
		add(txtPassword, gbc);

		btnLogin = new JButton("Đăng nhập");
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(btnLogin, gbc);

		btnRegister = new JButton("Đăng ký");
		gbc.gridx = 2;
		add(btnRegister, gbc);

		// Hành động cho nút Login
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginAction();
			}
		});

		// Hành động cho nút Register
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new RegisterFrame().setVisible(true);
			}
		});
	}

	private void loginAction() {
		String username = txtUsername.getText().trim();
		String password = String.valueOf(txtPassword.getPassword());

		try (Connection conn = DatabaseConnection.getConnection()) {
			String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				// Đăng nhập thành công
				JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
				dispose();
				new MainFrame(username).setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Vui Lòng Nhập Đúng Mật Khẩu");
		}
	}
}