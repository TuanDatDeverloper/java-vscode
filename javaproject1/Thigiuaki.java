import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Thigiuaki {
    public static void main(String[] args) {
        // Hiển thị hộp thoại đăng nhập
        LoginDialog loginDlg = new LoginDialog(null);
        loginDlg.setVisible(true);

        // Kiểm tra đăng nhập thành công
        if (loginDlg.isSucceeded()) {
            // Tiếp tục hiển thị giao diện chính
            JFrame frame = new JFrame("Quản lý tính tiền truy cập Internet");
            frame.setSize(1000, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            String imageUrl = "https://cdn.pixabay.com/photo/2020/09/01/18/13/background-5535928_1280.png";
            ImageIcon backgroundIcon;
            try {
                backgroundIcon = new ImageIcon(new URL(imageUrl));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Không tải được ảnh nền. Sử dụng nền mặc định.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                backgroundIcon = new ImageIcon();
            }
            JLabel backgroundLabel = new JLabel(backgroundIcon);
            backgroundLabel.setLayout(new BorderLayout());
            JPanel panelInput = new JPanel(new GridBagLayout());
            panelInput.setOpaque(false);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel lblCustomerName = new JLabel("Tên khách hàng:");
            JTextField txtCustomerName = new JTextField();
            txtCustomerName.setPreferredSize(new Dimension(200, 25));

            JLabel lblPhone = new JLabel("Số điện thoại:");
            JTextField txtPhone = new JTextField();
            txtPhone.setPreferredSize(new Dimension(200, 25));

            JLabel lblHours = new JLabel("Số giờ truy cập:");
            JTextField txtHours = new JTextField();
            txtHours.setPreferredSize(new Dimension(100, 25));

            JLabel lblDate = new JLabel("Ngày sử dụng:");
            JTextField txtDate = new JTextField();
            txtDate.setPreferredSize(new Dimension(150, 25));

            JLabel lblRate = new JLabel("Đơn giá (VND/giờ):");
            JTextField txtRate = new JTextField("2000");
            txtRate.setPreferredSize(new Dimension(100, 25));

            JLabel lblTax = new JLabel("Thuế VAT (%):");
            JTextField txtTax = new JTextField("10");
            txtTax.setPreferredSize(new Dimension(100, 25));
            gbc.gridx = 0;
            gbc.gridy = 0;
            panelInput.add(lblCustomerName, gbc);
            gbc.gridx = 1;
            panelInput.add(txtCustomerName, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            panelInput.add(lblPhone, gbc);
            gbc.gridx = 1;
            panelInput.add(txtPhone, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            panelInput.add(lblHours, gbc);
            gbc.gridx = 1;
            panelInput.add(txtHours, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            panelInput.add(lblDate, gbc);
            gbc.gridx = 1;
            panelInput.add(txtDate, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            panelInput.add(lblRate, gbc);
            gbc.gridx = 1;
            panelInput.add(txtRate, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            panelInput.add(lblTax, gbc);
            gbc.gridx = 1;
            panelInput.add(txtTax, gbc);

            // Buttons panel
            JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            panelButtons.setOpaque(false);

            JButton btnCalculate = new JButton("Tính Tiền");
            JButton btnClear = new JButton("Clear");
            JButton btnExit = new JButton("Thoát");

            panelButtons.add(btnCalculate);
            panelButtons.add(btnClear);
            panelButtons.add(btnExit);

            // Result panel
            JPanel panelResult = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panelResult.setOpaque(false);

            JLabel lblTotal = new JLabel("Tổng tiền:");
            JTextField txtTotal = new JTextField(15);
            txtTotal.setEditable(false);

            panelResult.add(lblTotal);
            panelResult.add(txtTotal);

            // Customer list panel
            JPanel panelCustomerList = new JPanel(new BorderLayout());
            panelCustomerList.setOpaque(false);
            panelCustomerList.setBorder(BorderFactory.createTitledBorder("Danh sách khách hàng"));

            String[] columnNames = {"Tên khách hàng", "Số điện thoại", "Ngày sử dụng", "Số giờ", "Đơn giá (VND)", "VAT (%)", "Tổng tiền (VND)"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable customerTable = new JTable(tableModel);

            // Đặt chế độ tự động điều chỉnh kích thước cột
            customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

            // Thiết lập chiều rộng các cột
            customerTable.getColumnModel().getColumn(0).setPreferredWidth(150); // Tên khách hàng
            customerTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Số điện thoại
            customerTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Ngày sử dụng
            customerTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Số giờ
            customerTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Đơn giá
            customerTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // VAT
            customerTable.getColumnModel().getColumn(6).setPreferredWidth(150); // Tổng tiền

            customerTable.setRowHeight(25); // Điều chỉnh chiều cao hàng

            JScrollPane scrollPane = new JScrollPane(customerTable);
            scrollPane.setPreferredSize(new Dimension(800, 300)); // Điều chỉnh kích thước bảng

            panelCustomerList.add(scrollPane, BorderLayout.CENTER);

            // Author label
            JLabel lblAuthor = new JLabel("Tác giả: Nguyễn Sỹ Tuấn Đạt");
            lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
            lblAuthor.setFont(new Font("Serif", Font.BOLD, 16));
            lblAuthor.setForeground(Color.BLUE);

            Timer timer = new Timer(500, new ActionListener() {
                private boolean isBlue = true;

                @Override
                public void actionPerformed(ActionEvent e) {
                    lblAuthor.setForeground(isBlue ? Color.RED : Color.BLUE);
                    isBlue = !isBlue;
                }
            });
            timer.start();

            // Add panels to background
            backgroundLabel.add(panelInput, BorderLayout.NORTH);
            backgroundLabel.add(panelButtons, BorderLayout.CENTER);
            backgroundLabel.add(panelResult, BorderLayout.SOUTH);
            backgroundLabel.add(panelCustomerList, BorderLayout.EAST);
            backgroundLabel.add(lblAuthor, BorderLayout.PAGE_END);

            frame.setContentPane(backgroundLabel);

            // Button actions
            btnCalculate.addActionListener(e -> {
                try {
                    String customerName = txtCustomerName.getText();
                    String phone = txtPhone.getText();
                    int hours = Integer.parseInt(txtHours.getText());
                    double rate = Double.parseDouble(txtRate.getText());
                    double taxRate = Double.parseDouble(txtTax.getText());
                    String date = txtDate.getText();

                    if (customerName.isEmpty() || phone.isEmpty() || date.isEmpty()) {
                        throw new IllegalArgumentException("Tên, số điện thoại và ngày không được để trống.");
                    }

                    double subtotal = hours * rate;
                    double tax = subtotal * taxRate / 100;
                    double total = subtotal + tax;

                    txtTotal.setText(String.format("%.2f VND", total));

                    // Add customer data to table
                    tableModel.addRow(new Object[]{customerName, phone, date, hours, rate, taxRate, total});
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnClear.addActionListener(e -> {
                txtCustomerName.setText("");
                txtPhone.setText("");
                txtHours.setText("");
                txtDate.setText("");
                txtRate.setText("2000");
                txtTax.setText("10");
                txtTotal.setText("");
            });

            btnExit.addActionListener(e -> frame.dispose());

            frame.setVisible(true);
        } else {
            System.exit(0);
        }
    }
}

// Thêm lớp LoginDialog
class LoginDialog extends JDialog {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private boolean succeeded;

    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Các thành phần giao diện
        JLabel lblUsername = new JLabel("Username: ");
        txtUsername = new JTextField(20);
        JLabel lblPassword = new JLabel("Password: ");
        txtPassword = new JPasswordField(20);
        JButton btnLogin = new JButton("Login");
        JButton btnCancel = new JButton("Cancel");

        // Thêm các thành phần vào panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblUsername, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblPassword, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtPassword, gbc);

        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);

        // Hành động cho nút Login
        btnLogin.addActionListener(e -> {
            String username = getUsername();
            String password = getPassword();

            // Kiểm tra username và password (ví dụ: hard-coded)
            if (username.equals("admin") && password.equals("password")) {
                succeeded = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginDialog.this,
                        "Invalid username or password",
                        "Login",
                        JOptionPane.ERROR_MESSAGE);
                // Reset username và password
                txtUsername.setText("");
                txtPassword.setText("");
                succeeded = false;
            }
        });

        // Hành động cho nút Cancel
        btnCancel.addActionListener(e -> {
            dispose();
        });

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
