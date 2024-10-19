public class Student {
    // Các biến thành viên
    private String name;
    private double javaScore;
    private double cScore;

    // Constructor để khởi tạo đối tượng sinh viên
    public Student(String name, double javaScore, double cScore) {
        this.name = name;
        this.javaScore = javaScore;
        this.cScore = cScore;
    }

    // Phương thức để hiển thị thông tin sinh viên và điểm trung bình
    public void displayInfo() {
        double average = (javaScore + cScore) / 2;
        System.out.println("Tên Sinh Viên: " + name);
        System.out.println("Điểm Java: " + javaScore);
        System.out.println("Điểm C Programming: " + cScore);
        System.out.printf("Điểm Trung Bình: %.2f\n", average);
        System.out.println("-----------------------------");
    }

    public static void main(String[] args) {
        // Tạo đối tượng sinh viên thứ nhất
        Student student1 = new Student("Nguyen Van A", 85.5, 92.0);
        
        // Tạo đối tượng sinh viên thứ hai
        Student student2 = new Student("Nguyen Van B", 78.0, 88.5);
        
        // Hiển thị thông tin cho mỗi sinh viên
        student1.displayInfo();
        student2.displayInfo();
    }
}
