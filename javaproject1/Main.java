import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Hiển thị tất cả sinh viên");
            System.out.println("3. Cập nhật thông tin sinh viên");
            System.out.println("4. Xóa sinh viên");
            System.out.println("5. Sắp xếp sinh viên theo điểm trung bình");
            System.out.println("6. Thoát");
            System.out.print("Chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Nhập ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập tên: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập điểm Toán: ");
                    int math = scanner.nextInt();
                    System.out.print("Nhập điểm Anh: ");
                    int english = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    manager.addStudent(new Student(id, name, math, english));
                    System.out.println("Thêm sinh viên thành công.");
                    break;
                case 2:
                    System.out.println("\n--- Danh Sách Sinh Viên ---");
                    manager.displayAllStudents();
                    break;
                case 3:
                    System.out.print("Nhập ID sinh viên cần cập nhật: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Nhập tên mới: ");
                    String newName = scanner.nextLine();
                    System.out.print("Nhập điểm Toán mới: ");
                    int newMath = scanner.nextInt();
                    System.out.print("Nhập điểm Anh mới: ");
                    int newEnglish = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    manager.updateStudent(updateId, newName, newMath, newEnglish);
                    break;
                case 4:
                    System.out.print("Nhập ID sinh viên cần xóa: ");
                    String deleteId = scanner.nextLine();
                    manager.deleteStudent(deleteId);
                    break;
                case 5:
                    manager.sortStudentsByAverageScore();
                    break;
                case 6:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
