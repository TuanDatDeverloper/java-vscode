import java.util.Scanner;

public class TimGiaTriNhoNhat {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Nhập kích thước mảng
        System.out.print("Nhập số lượng phần tử trong mảng: ");
        int n = scanner.nextInt();

        // Tạo mảng để chứa các số nguyên
        int[] mang = new int[n];

        // Nhập các phần tử vào mảng
        System.out.println("Nhập các phần tử của mảng:");
        for (int i = 0; i < n; i++) {
            mang[i] = scanner.nextInt();
        }

        // Tìm giá trị nhỏ nhất trong mảng
        int nhoNhat = mang[0]; // Giả sử phần tử đầu tiên là nhỏ nhất
        for (int i = 1; i < n; i++) {
            if (mang[i] < nhoNhat) {
                nhoNhat = mang[i];
            }
        }

        // In ra giá trị nhỏ nhất
        System.out.println("Giá trị nhỏ nhất trong mảng là: " + nhoNhat);
    }
}
