public class StringOperations {
    public static void main(String[] args) {
        String input = "Write a program in Java easily";

        // a. Đếm số lần xuất hiện của ký tự 'a'
        int countA = countOccurrences(input, 'a');
        System.out.println("Số lần xuất hiện của 'a': " + countA);

        // b. Kiểm tra xem chuỗi có chứa từ "Java" không
        boolean containsJava = input.contains("Java");
        System.out.println("Có chứa 'Java': " + containsJava);

        // c. Kiểm tra xem chuỗi có bắt đầu bằng từ "Write" không
        boolean startsWithWrite = input.startsWith("Write");
        System.out.println("Bắt đầu bằng 'Write': " + startsWithWrite);

        // d. Kiểm tra xem chuỗi có kết thúc bằng từ "easily" không
        boolean endsWithEasily = input.endsWith("easily");
        System.out.println("Kết thúc bằng 'easily': " + endsWithEasily);
    }

    // Phương thức đếm số lần xuất hiện của một ký tự trong chuỗi
    public static int countOccurrences(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }
}
