import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DangNhap extends JFrame {
    // Thêm constant cho đường dẫn icon
    private static final String ICON_PATH = "Internet/resources/";
    
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap, btnDangKy;
    private JLabel lblIcon;

    public DangNhap() {
        // Đổi tiêu đề
        setTitle("Quản Lý Tiền Internet");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Thêm icon
        ImageIcon icon = new ImageIcon(ICON_PATH + "iconlogin.png");
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

        txtTenDangNhap = new JTextField(15);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtTenDangNhap, gbc);

        // Label và PasswordField cho Password
        JLabel lblPassword = new JLabel("Mật khẩu:");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblPassword, gbc);

        txtMatKhau = new JPasswordField(15);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtMatKhau, gbc);

        // Panel cho các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnDangNhap = createStyledButton("Đăng nhập", "login.png");
        btnDangKy = createStyledButton("Đăng ký", "register.png");
        
        buttonPanel.add(btnDangNhap);
        buttonPanel.add(btnDangKy);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Thêm action listeners
        btnDangNhap.addActionListener(e -> login());
        btnDangKy.addActionListener(e -> {
            dispose();
            new DangKy().setVisible(true);
        });
    }

    // Thêm phương thức mới để tạo button có icon
    private JButton createStyledButton(String text, String iconName) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 30)); // Tăng kích thước nút
        
        // Thêm icon
        try {
            ImageIcon icon = new ImageIcon(ICON_PATH + iconName);
            Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
            button.setIconTextGap(8);
        } catch (Exception e) {
            System.err.println("Could not load icon: " + iconName);
        }

        // Style cho button
        button.setBackground(new Color(66, 139, 202));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));

        // Hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(51, 122, 183));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(66, 139, 202));
            }
        });

        return button;
    }

    private void login() {
        String tenDangNhap = txtTenDangNhap.getText();
        String matKhau = new String(txtMatKhau.getPassword());

        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập đầy đủ thông tin đăng nhập.", 
                "Lỗi", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ? AND matKhau = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tenDangNhap);
            pstmt.setString(2, matKhau);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, 
                    "Đăng nhập thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
                new QuanLyKhachHang().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Tên đăng nhập hoặc mật khẩu không đúng.", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi kết nối: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new DangNhap().setVisible(true);
        });
    }
}
