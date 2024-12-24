
import java.util.ArrayList;
import java.util.Comparator;

public class Student {
    private String id;
    private String name;
    private int mathScore;
    private int englishScore;

    public Student(String id, String name, int mathScore, int englishScore) {
        this.id = id;
        this.name = name;
        this.mathScore = mathScore;
        this.englishScore = englishScore;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMathScore() {
        return mathScore;
    }

    public int getEnglishScore() {
        return englishScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMathScore(int mathScore) {
        this.mathScore = mathScore;
    }

    public void setEnglishScore(int englishScore) {
        this.englishScore = englishScore;
    }

    public double getAverageScore() {
        return (mathScore + englishScore) / 2.0;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Math: %d | English: %d | Average: %.2f",
                id, name, mathScore, englishScore, getAverageScore());
    }
}

class StudentManager {
    private ArrayList<Student> students = new ArrayList<>();

    // Add a student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Display all students
    public void displayAllStudents() {
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Update student information
    public void updateStudent(String id, String newName, int newMathScore, int newEnglishScore) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                s.setName(newName);
                s.setMathScore(newMathScore);
                s.setEnglishScore(newEnglishScore);
                System.out.println("Cập nhật thành công.");
                return;
            }
        }
        System.out.println("Không tìm thấy sinh viên với ID: " + id);
    }

    // Delete a student
    public void deleteStudent(String id) {
        students.removeIf(s -> s.getId().equals(id));
        System.out.println("Đã xóa sinh viên với ID: " + id);
    }

    // Sort students by average score in descending order
    public void sortStudentsByAverageScore() {
        students.sort(Comparator.comparingDouble(Student::getAverageScore).reversed());
        System.out.println("Đã sắp xếp danh sách sinh viên theo điểm trung bình giảm dần.");
    }
}