import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
// Lớp Registration để lưu trữ thông tin đăng ký
class Registration {
    private String name;
    private String mobile;
    private String gender;
    private String dob;
    private String address;

    public Registration(String name, String mobile, String gender, String dob, String address) {
        this.name = name;
        this.mobile = mobile;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Name : " + name + "\nMobile : " + mobile + "\nGender : " + gender +
               "\nDOB : " + dob + "\nAddress : " + address + "\n-------------------------\n";
    }
}

public class Lab10 {
    public static void main(String[] args) {
        // Tạo JFrame
        JFrame frame = new JFrame("Registration Form");
        frame.setSize(700, 500); // Tăng chiều rộng để hiển thị bảng bên phải
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Tạo các thành phần giao diện
        JLabel titleLabel = new JLabel("Registration Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        // Căn giữa titleLabel trong frame có chiều rộng 700
        titleLabel.setBounds(250, 10, 200, 30);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(30, 60, 100, 20);
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 60, 200, 20);

        JLabel mobileLabel = new JLabel("Mobile");
        mobileLabel.setBounds(30, 100, 100, 20);
        JTextField mobileField = new JTextField();
        mobileField.setBounds(150, 100, 200, 20);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setBounds(30, 140, 100, 20);
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        maleButton.setBounds(150, 140, 70, 20);
        femaleButton.setBounds(220, 140, 80, 20);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        JLabel dobLabel = new JLabel("DOB");
        dobLabel.setBounds(30, 180, 100, 20);
        String[] days = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        // hiện năm đến năm hiện tại
        String[] years = {"1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021" , "2022" , "2023", "2024"};
        JComboBox<String> dayCombo = new JComboBox<>(days);
        JComboBox<String> monthCombo = new JComboBox<>(months);
        JComboBox<String> yearCombo = new JComboBox<>(years);
        dayCombo.setBounds(150, 180, 50, 20);
        monthCombo.setBounds(210, 180, 70, 20);
        yearCombo.setBounds(290, 180, 60, 20);

        JLabel addressLabel = new JLabel("Address");
        addressLabel.setBounds(30, 220, 100, 20);
        JTextArea addressArea = new JTextArea();
        addressArea.setBounds(150, 220, 200, 60);

        JCheckBox termsCheckBox = new JCheckBox("Accept Terms And Conditions.");
        termsCheckBox.setBounds(150, 300, 250, 20);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(150, 340, 90, 30);
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(260, 340, 90, 30);

        JLabel resultLabel = new JLabel();
        resultLabel.setBounds(30, 380, 400, 20);

        // Bảng hiển thị kết quả (di chuyển sang phải)
        JTextArea displayArea = new JTextArea();
        displayArea.setBounds(400, 60, 250, 300); // Điều chỉnh vị trí và kích thước
        displayArea.setEditable(false);
        displayArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Thêm viền cho đẹp

        // Thêm hành động cho nút Submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra điều kiện nhập liệu như trước
                String name = nameField.getText();
                String mobile = mobileField.getText();

                if (!name.matches("[a-zA-Z\\s]+")) {
                    JOptionPane.showMessageDialog(frame, "Họ tên không được chứa số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!mobile.matches("\\d+")) {
                    JOptionPane.showMessageDialog(frame, "Số điện thoại chỉ được chứa số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!termsCheckBox.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "Please accept the terms and conditions.");
                    return;
                }

                String gender = maleButton.isSelected() ? "Male" : "Female";
                String dob = dayCombo.getSelectedItem() + "/" + monthCombo.getSelectedItem() + "/" + yearCombo.getSelectedItem();
                String address = addressArea.getText();

                // Tạo đối tượng Registration mới và thêm vào danh sách
                Registration registration = new Registration(name, mobile, gender, dob, address);
                registrations.add(registration);

                // Hiển thị thông tin đăng ký trong displayArea
                displayArea.append(registration.toString());

                resultLabel.setText("Registration Successfully..");
            }
        });

        // Thêm hành động cho nút Reset
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                mobileField.setText("");
                genderGroup.clearSelection();
                dayCombo.setSelectedIndex(0);
                monthCombo.setSelectedIndex(0);
                yearCombo.setSelectedIndex(0);
                addressArea.setText("");
                termsCheckBox.setSelected(false);
                displayArea.setText("");
                resultLabel.setText("");
                registrations.clear(); // Xóa danh sách đăng ký nếu cần
            }
        });

        // Thêm các thành phần vào frame
        frame.add(titleLabel);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(mobileLabel);
        frame.add(mobileField);
        frame.add(genderLabel);
        frame.add(maleButton);
        frame.add(femaleButton);
        frame.add(dobLabel);
        frame.add(dayCombo);
        frame.add(monthCombo);
        frame.add(yearCombo);
        frame.add(addressLabel);
        frame.add(addressArea);
        frame.add(termsCheckBox);
        frame.add(submitButton);
        frame.add(resetButton);
        frame.add(resultLabel);
        frame.add(displayArea);

        // Hiển thị frame
        frame.setVisible(true);
    }

    static List<Registration> registrations = new ArrayList<>();
}
