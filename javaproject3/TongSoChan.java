import java.util.Scanner;

public class TongSoChan {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Yêu cầu người dùng nhập vào N
        System.out.print("Nhập vào một số nguyên dương N: ");
        int N = scanner.nextInt();
        
        // Kiểm tra tính hợp lệ của N
        if (N < 2) {
            System.out.println("N phải là số nguyên dương lớn hơn hoặc bằng 2.");
            return;
        }

        // Tính tổng các số chẵn
        int tong = 0;
        for (int i = 2; i <= N; i += 2) {
            tong += i;
        }

        // Xuất kết quả
        System.out.println("Tổng của tất cả các số chẵn từ 2 đến " + N + " là: " + tong);
        
        // Đóng scanner
        scanner.close();
    }
}
