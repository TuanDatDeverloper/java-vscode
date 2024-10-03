package javaproject2;

public class Bai2project {
    public static void main(String[] args) {
        int a = 4;
        int b = 5;
        int c = 6;
        
        int max = findMax(a, b, c);
        System.out.println("Giá trị lớn nhất trong ba số " + a + ", " + b + ", " + c + " là: " + max);
    }
    
    public static int findMax(int a, int b, int c) {
        int max = a;
        if (b > max) {
            max = b;
        }
        if (c > max) {
            max = c;
        }
        return max;
    }
}