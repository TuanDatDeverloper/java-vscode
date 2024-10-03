public class Bai2 {
    public static void main(String[] args) {
        // Tạo một StringBuffer
        StringBuffer sb = new StringBuffer("Nguyễn Sỹ Tuấn Đạt");

        // 1. Append
        sb.append(" World");
        System.out.println("Sau khi append: " + sb);

        // 2. Insert
        sb.insert(5, ",");
        System.out.println("Sau khi insert: " + sb);

        // 3. Replace
        sb.replace(6, 11, "Java");
        System.out.println("Sau khi replace: " + sb);

        // 4. Delete
        sb.delete(5, 6);
        System.out.println("Sau khi delete: " + sb);

        // 5. Reverse
        sb.reverse();
        System.out.println("Sau khi reverse: " + sb);

        // 6. Capacity
        System.out.println("Capacity: " + sb.capacity());

        // 7. Length
        System.out.println("Length: " + sb.length());

        // 8. CharAt
        char ch = sb.charAt(0);
        System.out.println("Ký tự tại chỉ số 0: " + ch);

        // 9. Substring
        String sub = sb.substring(1, 4);
        System.out.println("Substring (1, 4): " + sub);

        // 10. EnsureCapacity
        sb.ensureCapacity(50);
        System.out.println("Dung lượng mới sau ensureCapacity(50): " + sb.capacity());
    }
}
