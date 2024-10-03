package javaproject2;

public class Bai1project {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 1) {
                sum += i;
            }
        }
        System.out.println("Tổng các số lẻ từ 1 đến 10 là: " + sum);
    }
    
}
