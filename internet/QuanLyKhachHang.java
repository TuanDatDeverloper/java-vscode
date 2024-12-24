package Internet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// Thêm imports cần thiết
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class QuanLyKhachHang extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTimKiem;
    private static final String ICON_PATH = "Internet/resources/";
    private JPanel mainPanel;

    public QuanLyKhachHang() {
        setTitle("Quản Lý Tiền Internet");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Thêm màu nền tối cho toàn bộ frame
        getContentPane().setBackground(new Color(240, 240, 240)); // Màu xám nhạt

        // Add window icon
        ImageIcon windowIcon = new ImageIcon(ICON_PATH + "icon.png");
        setIconImage(windowIcon.getImage());

        // Create main layout với màu nền tối
        mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(240, 240, 240)); // Màu xám nhạt
        
        // Add components in correct order
        createHeaderPanel();    // Top header
        createSearchPanel();    // Search panel
        createTablePanel();     // Table display
        createNavigationPanel();// Left navigation
        createBottomPanel();    // Bottom status
        
        add(mainPanel);
        loadCustomers();
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(51, 51, 51)); // Màu nền tối cho header
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title với màu chữ sáng
        JLabel titleLabel = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(51, 51, 51));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));

        txtTimKiem = createStyledTextField();
        txtTimKiem.setPreferredSize(new Dimension(200, 30));
        
        // Tạo các nút với style mới
        JButton btnTimKiem = createStyledButton("Tìm kiếm", "search.png");
        btnTimKiem.setPreferredSize(new Dimension(120, 30)); // Set specific size for search button only
        JButton btnThem = createStyledButton("Thêm", "add.png");
        JButton btnSua = createStyledButton("Sửa", "edit.png");
        JButton btnXoa = createStyledButton("Xóa", "delete.png");

        searchPanel.add(searchLabel);
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);
        searchPanel.add(btnThem);
        searchPanel.add(btnSua);
        searchPanel.add(btnXoa);

        // Add action listeners
        btnTimKiem.addActionListener(e -> searchCustomer());
        btnThem.addActionListener(e -> showAddDialog());
        btnSua.addActionListener(e -> showEditDialog());
        btnXoa.addActionListener(e -> deleteCustomer());

        mainPanel.add(searchPanel, BorderLayout.NORTH);
    }

    private void createTablePanel() {
        // Table model with columns
        model = new DefaultTableModel(
            new String[]{"ID", "Tên khách hàng", "Địa chỉ", "Số điện thoại", "Gói Internet"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        
        // Style table
        table.setFont(new Font("Arial", Font.BOLD, 12)); // Chữ đậm hơn
        table.setForeground(new Color(33, 33, 33)); // Màu chữ đậm hơn
        table.setBackground(Color.WHITE); // Nền trắng
        
        // Style header
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(new Color(33, 33, 33));
        
        // Style rows
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(232, 242, 254));
        table.setSelectionForeground(new Color(33, 33, 33)); // Màu chữ khi được chọn
        table.setGridColor(new Color(200, 200, 200)); // Màu đường kẻ bảng
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Adjust column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(200);  // Tên
        table.getColumnModel().getColumn(2).setPreferredWidth(200);  // Địa chỉ
        table.getColumnModel().getColumn(3).setPreferredWidth(120);  // SĐT
        table.getColumnModel().getColumn(4).setPreferredWidth(150);  // Gói Internet

        // Tạo scroll pane với nền trắng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setPreferredSize(new Dimension(200, getHeight()));
        navPanel.setBackground(new Color(66, 139, 202));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[][] navItems = {
            {"Tổng quan", "dashboard.png"},
            {"Khách hàng", "users.png"},
            {"Gói cước", "package.png"},
            {"Thống kê", "stats.png"},
            {"Cài đặt", "settings.png"}
        };

        for (String[] item : navItems) {
            JButton navButton = createNavButton(item[0], item[1]);
            navButton.addActionListener(e -> {
                switch(item[0]) {
                    case "Tổng quan":
                        showOverviewDialog();
                        break;
                    case "Khách hàng":
                        showCustomerDialog();
                        break;
                    case "Gói cước":
                        showPackageDialog();
                        break;
                    case "Thống kê":
                        showStatisticsDialog();
                        break;
                    case "Cài đặt":
                        showSettingsDialog();
                        break;
                    // Other cases remain the same
                }
            });
            navPanel.add(navButton);
            navPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        mainPanel.add(navPanel, BorderLayout.WEST);
    }

    private void showOverviewDialog() {
        JDialog dialog = new JDialog(this, "Tổng quan hệ thống", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(new Color(240, 240, 240));

        // Create main panel with padding
        JPanel mainPanel = new JPanel(new GridBagLayout()); // Thay đổi layout thành GridBagLayout
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Panel 1: Tổng số khách hàng
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            String customerQuery = "SELECT COUNT(*) as total FROM KhachHang";
            PreparedStatement pstmt = conn.prepareStatement(customerQuery);
            ResultSet rs = pstmt.executeQuery();
            int totalCustomers = rs.next() ? rs.getInt("total") : 0;
            mainPanel.add(createInfoPanel("Tổng số khách hàng", 
                         String.valueOf(totalCustomers), "users.png"), gbc);

            // Panel 2: Doanh thu
            gbc.gridx = 1;
            String revenueQuery = "SELECT SUM(CASE " +
                                "WHEN goiInternet LIKE '%200.000đ%' THEN 200000 " +
                                "WHEN goiInternet LIKE '%300.000đ%' THEN 300000 " +
                                "WHEN goiInternet LIKE '%500.000đ%' THEN 500000 " +
                                "ELSE 0 END) as total_revenue FROM KhachHang";
            pstmt = conn.prepareStatement(revenueQuery);
            rs = pstmt.executeQuery();
            double revenue = rs.next() ? rs.getDouble("total_revenue") : 0;
            mainPanel.add(createInfoPanel("Doanh thu", 
                         String.format("%,.0f VNĐ", revenue), "money.png"), gbc);

            // Panel 3: Gói cước phổ biến
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2; // Span across 2 columns
            String packageQuery = "SELECT goiInternet, COUNT(*) as count FROM KhachHang " +
                                "GROUP BY goiInternet ORDER BY count DESC LIMIT 1";
            pstmt = conn.prepareStatement(packageQuery);
            rs = pstmt.executeQuery();
            String popularPackage = rs.next() ? rs.getString("goiInternet") : "Không có dữ liệu";
            mainPanel.add(createInfoPanel("Gói cước phổ biến", 
                         popularPackage, "package.png"), gbc);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }

        dialog.add(mainPanel, BorderLayout.CENTER);

        // Add close button
        JButton closeButton = createStyledButton("Đóng", "close.png");
        closeButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createInfoPanel(String title, String value, String iconName) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(new Color(66, 139, 202));
        panel.add(valueLabel, BorderLayout.CENTER);

        // Icon
        try {
            ImageIcon icon = new ImageIcon(ICON_PATH + iconName);
            Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(img));
            panel.add(iconLabel, BorderLayout.EAST);
        } catch (Exception e) {
            System.err.println("Could not load icon: " + iconName);
        }

        return panel;
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    // Utility methods
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return textField;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 12)); // Chữ đậm hơn
        button.setBackground(new Color(41, 128, 185)); // Màu xanh đậm hơn
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(JButton.RIGHT);
        button.setVerticalTextPosition(JButton.CENTER);
        
        // Hiệu ứng hover với màu đậm hơn
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 152, 219)); // Màu xanh sáng khi hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185)); // Màu xanh đậm ban đầu
            }
        });
    }

    private JButton createButtonWithIcon(String text, String iconName) {
        ImageIcon icon = new ImageIcon(ICON_PATH + iconName);
        Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        JButton button = new JButton(text, new ImageIcon(img));
        button.setIconTextGap(8);
        return button;
    }

    private JButton createNavButton(String text, String iconName) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 40));
        button.setMaximumSize(new Dimension(180, 40));
        button.setBackground(new Color(66, 139, 202)); // Màu xanh giống nút đăng nhập
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        // Load and set icon
        try {
            ImageIcon icon = new ImageIcon(ICON_PATH + iconName);
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setIconTextGap(10);
        } catch (Exception e) {
            System.err.println("Could not load icon: " + iconName);
        }

        // Hiệu ứng hover giống nút đăng nhập
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(51, 122, 183)); // Màu hover giống nút đăng nhập
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(66, 139, 202)); // Màu mặc định giống nút đăng nhập
            }
        });

        return button;
    }

    // Thêm phương thức mới để tạo nút với style thống nhất
    private JButton createStyledButton(String text, String iconName) {
        JButton button = new JButton(text);
        // Tăng chiều rộng của nút từ 100 lên 120 pixels
        button.setPreferredSize(new Dimension(120, 30)); // Changed from 100 to 120
        button.setBackground(new Color(66, 139, 202));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));

        // Thêm icon nếu có
        if (iconName != null) {
            try {
                ImageIcon icon = new ImageIcon(ICON_PATH + iconName);
                Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(img));
                button.setIconTextGap(8);
            } catch (Exception e) {
                System.err.println("Could not load icon: " + iconName);
            }
        }

        // Hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(51, 122, 183)); // Màu hover giống đăng nhập
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(66, 139, 202)); // Màu mặc định
            }
        });

        return button;
    }

    // Keep existing methods
    private void loadCustomers() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM KhachHang")) {

            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("ten"),
                        rs.getString("diaChi"),
                        rs.getString("soDienThoai"),
                        rs.getString("goiInternet")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchCustomer() {
        String keyword = txtTimKiem.getText();
        String sql = "SELECT * FROM KhachHang WHERE ten LIKE ? OR soDienThoai LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("ten"),
                        rs.getString("diaChi"),
                        rs.getString("soDienThoai"),
                        rs.getString("goiInternet")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(this, "Thêm khách hàng", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Tạo các components
        JTextField txtTen = new JTextField(20);
        JTextField txtDiaChi = new JTextField(20);
        JTextField txtSoDienThoai = new JTextField(20);
        JComboBox<String> cboGoiInternet = new JComboBox<>(new String[]{
            "Gói Cơ Bản - 200.000đ",
            "Gói Tiêu Chuẩn - 300.000đ",
            "Gói Cao Cấp - 500.000đ"
        });
    
        // Thêm các components vào dialog
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Tên khách hàng:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtTen, gbc);
    
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtDiaChi, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtSoDienThoai, gbc);
    
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Gói Internet:"), gbc);
        gbc.gridx = 1;
        dialog.add(cboGoiInternet, gbc);
    
        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSave = createStyledButton("Lưu", "save.png");
        JButton btnCancel = createStyledButton("Hủy", "cancel.png");
    
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
    
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
    
        // Xử lý sự kiện nút Lưu
        btnSave.addActionListener(e -> {
            // Kiểm tra dữ liệu nhập
            if (txtTen.getText().trim().isEmpty() || 
                txtDiaChi.getText().trim().isEmpty() || 
                txtSoDienThoai.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "Vui lòng nhập đầy đủ thông tin",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Kiểm tra số điện thoại hợp lệ
            if (!txtSoDienThoai.getText().matches("\\d{10}")) {
                JOptionPane.showMessageDialog(dialog,
                    "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Thực hiện thêm vào database
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO KhachHang (ten, diaChi, soDienThoai, goiInternet) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, txtTen.getText().trim());
                pstmt.setString(2, txtDiaChi.getText().trim());
                pstmt.setString(3, txtSoDienThoai.getText().trim());
                pstmt.setString(4, cboGoiInternet.getSelectedItem().toString());
                pstmt.executeUpdate();
    
                JOptionPane.showMessageDialog(dialog,
                    "Thêm khách hàng thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                
                loadCustomers(); // Tải lại dữ liệu bảng
                dialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog,
                    "Lỗi khi thêm khách hàng: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    
        // Xử lý sự kiện nút Hủy
        btnCancel.addActionListener(e -> dialog.dispose());
    
        dialog.setVisible(true);
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa");
            return;
        }

        JDialog dialog = new JDialog(this, "Sửa thông tin khách hàng", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Lấy thông tin khách hàng từ bảng
        int id = (int) model.getValueAt(selectedRow, 0);
        String ten = (String) model.getValueAt(selectedRow, 1);
        String diaChi = (String) model.getValueAt(selectedRow, 2);
        String soDienThoai = (String) model.getValueAt(selectedRow, 3);
        String goiInternet = (String) model.getValueAt(selectedRow, 4);

        // Tạo các components
        JTextField txtTen = new JTextField(ten, 20);
        JTextField txtDiaChi = new JTextField(diaChi, 20);
        JTextField txtSoDienThoai = new JTextField(soDienThoai, 20);
        JComboBox<String> cboGoiInternet = new JComboBox<>(new String[]{
            "Gói Cơ Bản - 200.000đ",
            "Gói Tiêu Chuẩn - 300.000đ",
            "Gói Cao Cấp - 500.000đ"
        });
        cboGoiInternet.setSelectedItem(goiInternet);

        // Thêm các components vào dialog
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Tên khách hàng:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtTen, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtDiaChi, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtSoDienThoai, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Gói Internet:"), gbc);
        gbc.gridx = 1;
        dialog.add(cboGoiInternet, gbc);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSave = createStyledButton("Cập nhật", "save.png");
        JButton btnCancel = createStyledButton("Hủy", "cancel.png");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        // Xử lý sự kiện nút Cập nhật
        btnSave.addActionListener(e -> {
            // Kiểm tra dữ liệu nhập
            if (txtTen.getText().trim().isEmpty() || 
                txtDiaChi.getText().trim().isEmpty() || 
                txtSoDienThoai.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "Vui lòng nhập đầy đủ thông tin",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra số điện thoại hợp lệ
            if (!txtSoDienThoai.getText().matches("\\d{10}")) {
                JOptionPane.showMessageDialog(dialog,
                    "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Thực hiện cập nhật vào database
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE KhachHang SET ten = ?, diaChi = ?, soDienThoai = ?, goiInternet = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, txtTen.getText().trim());
                pstmt.setString(2, txtDiaChi.getText().trim());
                pstmt.setString(3, txtSoDienThoai.getText().trim());
                pstmt.setString(4, cboGoiInternet.getSelectedItem().toString());
                pstmt.setInt(5, id);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(dialog,
                    "Cập nhật thông tin thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                
                loadCustomers(); // Tải lại dữ liệu bảng
                dialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog,
                    "Lỗi khi cập nhật thông tin: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Xử lý sự kiện nút Hủy
        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa");
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa khách hàng này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Xóa khách hàng
                int id = (int) model.getValueAt(selectedRow, 0);
                PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM KhachHang WHERE id = ?"
                );
                pstmt.setInt(1, id);
                pstmt.executeUpdate();

                // Reset auto_increment
                Statement stmt = conn.createStatement();
                stmt.execute("ALTER TABLE KhachHang AUTO_INCREMENT = 1");
                
                loadCustomers(); // Tải lại dữ liệu
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage());
            }
        }
    }

    private void showCustomerDialog() {
        // Tạo dialog hiển thị danh sách khách hàng
        JDialog dialog = new JDialog(this, "Quản lý khách hàng", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        // Panel chứa nút quay lại
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBackground(new Color(51, 51, 51));

        // Tạo nút quay lại với icon
        JButton btnQuayLai = createStyledButton("Quay lại", "back.png");
        buttonPanel.add(btnQuayLai);

        // Tạo bảng khách hàng
        String[] columns = {"ID", "Tên khách hàng", "Địa chỉ", "Số điện thoại", "Gói Internet"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        
        // Style cho bảng
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(232, 242, 254));
        
        // Thêm bảng vào ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Thêm các component vào dialog
        dialog.add(buttonPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Load dữ liệu khách hàng
        loadCustomerData(model);

        // Xử lý sự kiện nút quay lại
        btnQuayLai.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void loadCustomerData(DefaultTableModel model) {
        model.setRowCount(0); // Xóa dữ liệu cũ
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM KhachHang")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("ten"), 
                    rs.getString("diaChi"),
                    rs.getString("soDienThoai"),
                    rs.getString("goiInternet")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showPackageDialog() {
        // Tạo dialog hiển thị thông tin gói cước
        JDialog dialog = new JDialog(this, "Thông tin gói cước", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        // Panel chứa nút quay lại
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBackground(new Color(51, 51, 51));
        JButton btnQuayLai = createStyledButton("Quay lại", "back.png");
        buttonPanel.add(btnQuayLai);

        // Panel chứa thông tin các gói cước
        JPanel packagesPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        packagesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        packagesPanel.setBackground(Color.WHITE);

        // Tạo panel cho từng gói cước
        String[][] packages = {
            {"Gói Cơ Bản", "200.000đ/tháng", "Tốc độ: 30Mbps", "Phù hợp cho hộ gia đình"},
            {"Gói Tiêu Chuẩn", "300.000đ/tháng", "Tốc độ: 60Mbps", "Phù hợp cho văn phòng nhỏ"},
            {"Gói Cao Cấp", "500.000đ/tháng", "Tốc độ: 100Mbps", "Phù hợp cho doanh nghiệp"}
        };

        for (String[] pkg : packages) {
            JPanel packagePanel = createPackagePanel(pkg[0], pkg[1], pkg[2], pkg[3]);
            packagesPanel.add(packagePanel);
        }

        // Thêm các panel vào dialog
        dialog.add(buttonPanel, BorderLayout.NORTH);
        dialog.add(new JScrollPane(packagesPanel), BorderLayout.CENTER);

        // Xử lý sự kiện nút quay lại
        btnQuayLai.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private JPanel createPackagePanel(String name, String price, String speed, String description) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Tiêu đề gói cước
        JLabel titleLabel = new JLabel(name);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(66, 139, 202));

        // Thông tin gói cước
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel priceLabel = new JLabel("Giá: " + price);
        JLabel speedLabel = new JLabel(speed);
        JLabel descLabel = new JLabel(description);

        infoPanel.add(priceLabel);
        infoPanel.add(speedLabel);
        infoPanel.add(descLabel);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(infoPanel, BorderLayout.CENTER);

        return panel;
    }

    // Thêm phương thức showStatisticsDialog() vào class QuanLyKhachHang
    private void showStatisticsDialog() {
        JDialog dialog = new JDialog(this, "Thống kê", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        // Tạo panel chứa các biểu đồ
        JPanel chartsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        chartsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartsPanel.setBackground(Color.WHITE);

        // Tạo biểu đồ tròn thống kê gói cước
        JFreeChart pieChart = createPackagesPieChart();
        ChartPanel pieChartPanel = new ChartPanel(pieChart);

        // Tạo biểu đồ cột thống kê doanh thu theo gói
        JFreeChart barChart = createRevenueBarChart();
        ChartPanel barChartPanel = new ChartPanel(barChart);

        chartsPanel.add(pieChartPanel);
        chartsPanel.add(barChartPanel);

        // Panel chứa nút quay lại
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(51, 51, 51));
        JButton btnQuayLai = createStyledButton("Quay lại", "back.png");
        buttonPanel.add(btnQuayLai);

        dialog.add(buttonPanel, BorderLayout.NORTH);
        dialog.add(chartsPanel, BorderLayout.CENTER);

        btnQuayLai.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private JFreeChart createPackagesPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT goiInternet, COUNT(*) as count FROM KhachHang GROUP BY goiInternet")) {

            while (rs.next()) {
                dataset.setValue(rs.getString("goiInternet"), rs.getInt("count"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart(
            "Thống kê khách hàng theo gói cước",
            dataset,
            true,
            true,
            false
        );

        return chart;
    }

    private JFreeChart createRevenueBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT goiInternet, COUNT(*) * CASE " +
                 "WHEN goiInternet LIKE '%200.000đ%' THEN 200000 " +
                 "WHEN goiInternet LIKE '%300.000đ%' THEN 300000 " +
                 "WHEN goiInternet LIKE '%500.000đ%' THEN 500000 " +
                 "ELSE 0 END as revenue " +
                 "FROM KhachHang GROUP BY goiInternet")) {

            while (rs.next()) {
                dataset.addValue(rs.getDouble("revenue"), "Doanh thu", 
                               rs.getString("goiInternet"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Thống kê doanh thu theo gói cước",
            "Gói cước",
            "Doanh thu (VNĐ)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        return chart;
    }

    private void showSettingsDialog() {
        JDialog dialog = new JDialog(this, "Cài đặt hệ thống", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        // Panel chứa nút quay lại
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        headerPanel.setBackground(new Color(51, 51, 51));
        JButton btnQuayLai = createStyledButton("Quay lại", "back.png");
        headerPanel.add(btnQuayLai);

        // Panel chứa các cài đặt
        JPanel settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        settingsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Thêm nút đăng xuất
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JButton btnDangXuat = createStyledButton("Đăng xuất", "logout.png");
        btnDangXuat.setPreferredSize(new Dimension(200, 40));
        settingsPanel.add(btnDangXuat, gbc);

        // Xử lý sự kiện đăng xuất
        btnDangXuat.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                dialog,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {
                dialog.dispose(); // Đóng dialog cài đặt
                dispose(); // Đóng cửa sổ chính
                new DangNhap().setVisible(true); // Mở cửa sổ đăng nhập
            }
        });

        // Thêm các panel vào dialog
        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(settingsPanel, BorderLayout.CENTER);

        // Xử lý sự kiện nút quay lại
        btnQuayLai.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}
