package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import database.DatabaseConnection;
import javax.swing.ImageIcon;
import java.awt.Image;

public class MainFrame extends JFrame {
    private JPanel contentPanel; // Add this field
    // Add icon paths as constants
    private static final String ICON_PATH = "Quanlyinternet/views/resources/";
    private JPanel mainPanel;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField txtCustomerName, txtPhone, txtEmail, txtSearchCustomer;
    private JComboBox<String> packageCombo;
    private JLabel lblTotalAmount;
    private final String loggedInUser;

    public MainFrame(String username) {
        this.loggedInUser = username;
        setTitle("Quản Lý Internet - " + username);
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add window icon
        ImageIcon windowIcon = new ImageIcon(ICON_PATH + "icon.png");
        setIconImage(windowIcon.getImage());

        // Create main layout
        mainPanel = new JPanel(new BorderLayout(0, 0));
        
        // Initialize content panel with CardLayout
        contentPanel = new JPanel(new CardLayout());
        
        // Create all panels
        JPanel overviewPanel = createOverviewPanel();
        JPanel transactionsPanel = createTransactionsPanel();
        JPanel addMoneyPanel = createAddMoneyPanel();
        JPanel withdrawPanel = createWithdrawPanel();
        JPanel settingsPanel = createSettingsPanel();
        
        // Add panels to content panel with names
        contentPanel.add(overviewPanel, "OVERVIEW");
        contentPanel.add(transactionsPanel, "TRANSACTIONS");
        contentPanel.add(addMoneyPanel, "ADD_MONEY");
        contentPanel.add(withdrawPanel, "WITHDRAW");
        contentPanel.add(settingsPanel, "SETTINGS");
        
        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add components in correct order
        createNavigationPanel();           // Left navigation
        createHeaderPanel();              // Top header
        createInputPanel();               // Input form
        createTablePanel();               // Table display
        createBottomPanel();             // Bottom status bar
        
        // Add the content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        initializeDatabase();
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Title with modern font
        JLabel titleLabel = new JLabel("QUẢN LÝ DỊCH VỤ INTERNET");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // User info panel
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel("Xin chào, " + loggedInUser);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton logoutButton = new JButton("Đăng xuất");
        styleButton(logoutButton);
        
        userPanel.add(userLabel);
        userPanel.add(logoutButton);
        headerPanel.add(userPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createInputPanel() {
        // Create a wrapper panel that takes up 1/4 of the frame
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setPreferredSize(new Dimension(getWidth()/3, getHeight()/3));
        wrapperPanel.setOpaque(false);

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setOpaque(false);

        // Create table for input fields
        String[] columnNames = {"Tên khách hàng", "Số điện thoại", "Email", "Gói cước"};
        DefaultTableModel inputModel = new DefaultTableModel(columnNames, 1);
        JTable inputTable = new JTable(inputModel);
        inputTable.setRowHeight(25);
        
        // Set editors for each column
        txtCustomerName = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();
        packageCombo = new JComboBox<>(new String[]{"Gói Cơ Bản - 50k/tháng", "Gói Cao Cấp - 100k/tháng"});
        
        inputTable.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(txtCustomerName));
        inputTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(txtPhone));
        inputTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(txtEmail));
        inputTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(packageCombo));

        // Style the input table
        inputTable.setFont(new Font("Arial", Font.PLAIN, 12));
        inputTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        inputTable.getTableHeader().setBackground(new Color(240, 240, 240));
        inputTable.setSelectionBackground(new Color(232, 242, 254));

        // Add input table to a scroll pane
        JScrollPane inputScrollPane = new JScrollPane(inputTable);
        inputScrollPane.setPreferredSize(new Dimension(400, 53)); // Height for 1 row + header
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        // Create buttons with icons
        JButton addButton = createButtonWithIcon("Thêm", "add.png");
        JButton updateButton = createButtonWithIcon("Cập nhật", "update.png");
        JButton deleteButton = createButtonWithIcon("Xóa", "delete.png");
        
        addButton.addActionListener(e -> {
            String name = (String) inputTable.getValueAt(0, 0);
            String phone = (String) inputTable.getValueAt(0, 1);
            String email = (String) inputTable.getValueAt(0, 2);
            String selectedPackage = (String) inputTable.getValueAt(0, 3);
            
            if (name == null || phone == null || name.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên và số điện thoại");
                return;
            }
            
            addCustomerToDatabase(name, phone, email, selectedPackage);
            // Clear the input row
            for (int i = 0; i < inputTable.getColumnCount(); i++) {
                inputTable.setValueAt("", 0, i);
            }
        });
        
        styleButton(addButton);
        styleButton(updateButton);
        styleButton(deleteButton);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        inputPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add the input panel to the wrapper panel
        wrapperPanel.add(inputPanel, BorderLayout.CENTER);
        
        // Add the wrapper panel to the top-right corner
        mainPanel.add(wrapperPanel, BorderLayout.EAST);
    }

    private void createTablePanel() {
        // Table model with columns
        String[] columns = {"ID", "Tên khách hàng", "Số điện thoại", "Email", "Gói cước", "Ngày đăng ký"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép edit trực tiếp trên bảng
            }
        };
        customerTable = new JTable(tableModel);
        
        // Style table
        customerTable.setFont(new Font("Arial", Font.PLAIN, 12));
        customerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        customerTable.setRowHeight(25);
        customerTable.setSelectionBackground(new Color(232, 242, 254));
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Điều chỉnh độ rộng các cột
        customerTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        customerTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên
        customerTable.getColumnModel().getColumn(2).setPreferredWidth(100); // SĐT
        customerTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Email
        customerTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Gói cước
        customerTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Ngày đăng ký

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tạo panel chứa bảng
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Thêm vào vị trí CENTER thay vì SOUTH
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Add selection listener
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String id = tableModel.getValueAt(selectedRow, 0).toString();
                    String name = tableModel.getValueAt(selectedRow, 1).toString();
                    String phone = tableModel.getValueAt(selectedRow, 2).toString();
                    String email = tableModel.getValueAt(selectedRow, 3).toString();
                    String packageType = tableModel.getValueAt(selectedRow, 4).toString();
                    
                    // Cập nhật các trường nhập liệu
                    txtCustomerName.setText(name);
                    txtPhone.setText(phone);
                    txtEmail.setText(email);
                    packageCombo.setSelectedItem(packageType);
                }
            }
        });
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Total amount label
        lblTotalAmount = new JLabel("Tổng doanh thu: 0 VNĐ");
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(lblTotalAmount, BorderLayout.WEST);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        txtSearchCustomer = createStyledTextField();
        txtSearchCustomer.setPreferredSize(new Dimension(200, 25));
        JButton searchButton = createButtonWithIcon("Tìm kiếm", "search.png");
        styleButton(searchButton);
        
        // Add search functionality
        searchButton.addActionListener(e -> searchCustomers());
        txtSearchCustomer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchCustomers();
                }
            }
        });
        
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearchCustomer);
        searchPanel.add(searchButton);
        
        bottomPanel.add(searchPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    // Add this new method for search functionality
    private void searchCustomers() {
        String searchTerm = txtSearchCustomer.getText().trim();
        if (searchTerm.isEmpty()) {
            loadCustomersFromDatabase();
            return;
        }

        String sql = "SELECT * FROM customers WHERE " +
                    "LOWER(name) LIKE LOWER(?) OR " +
                    "phone LIKE ? OR " +
                    "LOWER(email) LIKE LOWER(?) OR " +
                    "LOWER(package) LIKE LOWER(?)";
        
        tableModel.setRowCount(0); // Clear existing data

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchTerm + "%";
            // Set parameters for all search conditions
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern); 
            pstmt.setString(4, searchPattern);
            
            ResultSet rs = pstmt.executeQuery();
            boolean found = false;
            
            while (rs.next()) {
                found = true;
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("package"),
                    rs.getTimestamp("register_date")
                };
                tableModel.addRow(row);
            }
            
            if (!found) {
                JOptionPane.showMessageDialog(this, 
                    "Không tìm thấy kết quả cho: " + searchTerm,
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tìm kiếm: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return textField;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setBackground(new Color(66, 139, 202));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(JButton.RIGHT); // Text to right of icon
        button.setVerticalTextPosition(JButton.CENTER); // Center text vertically
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(51, 122, 183));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(66, 139, 202));
            }
        });
    }

    private JButton createButtonWithIcon(String text, String iconName) {
        ImageIcon icon = new ImageIcon(ICON_PATH + iconName);
        // Resize icon to 16x16 pixels
        Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        JButton button = new JButton(text, new ImageIcon(img));
        button.setIconTextGap(8); // Space between icon and text
        return button;
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(189, 189, 189)));
    }

    private void addLabelAndComponent(String labelText, JComponent component, 
            JPanel panel, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(component, gbc);
    }

    private void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            loadCustomersFromDatabase();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Không thể kết nối đến cơ sở dữ liệu: " + e.getMessage(), 
                "Lỗi Database", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCustomersFromDatabase() {
        tableModel.setRowCount(0); // Clear existing data
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("package"),
                    rs.getTimestamp("register_date")
                };
                tableModel.addRow(row);
            }
            updateTotalAmount();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void addCustomerToDatabase(String name, String phone, String email, String selectedPackage) {
        String sql = "INSERT INTO customers (name, phone, email, package) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, selectedPackage);
            pstmt.executeUpdate();

            loadCustomersFromDatabase(); // Refresh table
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm khách hàng: " + e.getMessage());
        }
    }

    private void updateTotalAmount() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(total_amount) as total FROM customers")) {
            
            if (rs.next()) {
                double total = rs.getDouble("total");
                lblTotalAmount.setText(String.format("Tổng doanh thu: %.2f VNĐ", total));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearInputFields() {
        txtCustomerName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        packageCombo.setSelectedIndex(0);
    }

    private void createNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setPreferredSize(new Dimension(200, getHeight()));
        navPanel.setBackground(new Color(51, 51, 51));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        
        String[][] navItems = {
            {"Tổng quan", "dashboard.png"},
            {"Giao dịch", "transactions.png"},
            {"Thêm tiền", "add-money.png"},
            {"Rút tiền", "withdraw.png"},
            {"Cài đặt", "settings.png"}
        };

        for (String[] item : navItems) {
            JButton navButton = createNavButton(item[0], item[1]);
            
            // Add action listener for each button
            navButton.addActionListener(e -> {
                switch(item[0]) {
                    case "Tổng quan":
                        showOverviewPanel();
                        break;
                    case "Giao dịch":
                        showTransactionsPanel();
                        break;
                    case "Thêm tiền":
                        showAddMoneyPanel();
                        break;
                    case "Rút tiền":
                        showWithdrawPanel();
                        break;
                    case "Cài đặt":
                        showSettingsPanel();
                        break;
                }
            });
            
            navPanel.add(navButton);
            navPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        mainPanel.add(navPanel, BorderLayout.WEST);
    }

    private JButton createNavButton(String text, String iconName) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 40));
        button.setMaximumSize(new Dimension(180, 40));
        button.setBackground(new Color(51, 51, 51));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
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

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(75, 75, 75));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(51, 51, 51));
            }
        });

        return button;
    }

    private JPanel createOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add overview components
        JLabel titleLabel = new JLabel("Tổng Quan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        return panel;
    }

    private void showOverviewPanel() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "OVERVIEW");
    }

    private JPanel createTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create transactions table
        String[] columns = {"ID", "Ngày", "Loại", "Số tiền", "Mô tả"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable transactionsTable = new JTable(model);
        panel.add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
        
        return panel;
    }

    private void showTransactionsPanel() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "TRANSACTIONS");
    }

    private JPanel createAddMoneyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add components for adding money
        JTextField amountField = new JTextField(15);
        JButton addButton = new JButton("Thêm tiền");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Số tiền:"), gbc);
        
        gbc.gridx = 1;
        panel.add(amountField, gbc);
        
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);
        
        return panel;
    }

    private void showAddMoneyPanel() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "ADD_MONEY");
    }

    private JPanel createWithdrawPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add components for withdrawing money
        JTextField amountField = new JTextField(15);
        JButton withdrawButton = new JButton("Rút tiền");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Số tiền:"), gbc);
        
        gbc.gridx = 1;
        panel.add(amountField, gbc);
        
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(withdrawButton, gbc);
        
        return panel;
    }

    private void showWithdrawPanel() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "WITHDRAW");
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add settings components
        return panel;
    }

    private void showSettingsPanel() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "SETTINGS");
    }
}