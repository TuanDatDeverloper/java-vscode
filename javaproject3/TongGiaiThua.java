public class TongGiaiThua {
    public static void main(String[] args) {
        long tong = giaiThua(7) + giaiThua(10) + giaiThua(12) + giaiThua(14);
        System.out.println("S = 7! + 10! + 12! + 14! = " + tong);
    }

    // Phương thức tính giai thừa
    public static long giaiThua(int n) {
        long ketQua = 1;
        for (int i = 2; i <= n; i++) {
            ketQua *= i;
        }
        return ketQua;
    }
}
