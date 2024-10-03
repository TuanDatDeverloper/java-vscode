public class Bai4 {
    public static void main(String[] args) {
        // Kiểm tra các phương thức của lớp String
        String str = "Hello, World!";
        System.out.println(str.length()); // 13
        System.out.println(str.toUpperCase()); // HELLO, WORLD!
        System.out.println(str.substring(7)); // World!
        System.out.println(str.indexOf('o')); // 4
        System.out.println(str.replace('o', '0')); // Hell0, W0rld!

        // Kiểm tra các phương thức của lớp Integer
        Integer num = 10;
        System.out.println(Integer.parseInt("20")); // 20
        System.out.println(Integer.toString(num)); // "10"
        System.out.println(Integer.compare(num, 5)); // 1
        System.out.println(Integer.sum(num, 5)); // 15
        System.out.println(Integer.max(num, 15)); // 15

        // Kiểm tra các phương thức của lớp Math
        System.out.println(Math.abs(-10)); // 10
        System.out.println(Math.sqrt(16)); // 4.0
        System.out.println(Math.pow(2, 3)); // 8.0
        System.out.println(Math.random()); // Số ngẫu nhiên giữa 0.0 và 1.0
        System.out.println(Math.max(10, 20)); // 20

        // Kiểm tra các phương thức của lớp System
        System.out.println(System.currentTimeMillis()); // Thời gian hiện tại tính bằng mili giây
        System.out.println(System.getProperty("os.name")); // Tên hệ điều hành
        System.gc(); // Đề xuất thu gom rác
        System.out.println(System.nanoTime()); // Thời gian hiện tại tính bằng nano giây
        System.out.println(System.lineSeparator()); // Chuỗi phân cách dòng

        // Kiểm tra các phương thức của lớp Character
        char ch = 'A';
        System.out.println(Character.isDigit(ch)); // false
        System.out.println(Character.toLowerCase(ch)); // a
        System.out.println(Character.isLetter(ch)); // true
        System.out.println(Character.toUpperCase(ch)); // A
        System.out.println(Character.isWhitespace(' ')); // true
    }
}
