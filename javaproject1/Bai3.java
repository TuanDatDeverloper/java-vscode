import java.util.Random;

public class Bai3 {
    public static void main(String[] args) {
        Random random = new Random();

        //1. Kiểm tra nextInt()
        int randomInt = random.nextInt();
        System.out.println("Số nguyên ngẫu nhiên: " + randomInt);

        //2. Kiểm tra nextInt(bound)
        int randomIntBound = random.nextInt(100); // Số nguyên ngẫu nhiên từ 0 đến 99
        System.out.println("Số nguyên ngẫu nhiên (0-99): " + randomIntBound);

        //3. Kiểm tra nextDouble()
        double randomDouble = random.nextDouble();
        System.out.println("Số thực ngẫu nhiên: " + randomDouble);

        //4. Kiểm tra nextFloat()
        float randomFloat = random.nextFloat();
        System.out.println("Số thực đơn ngẫu nhiên: " + randomFloat);

        //5. Kiểm tra nextLong()
        long randomLong = random.nextLong();
        System.out.println("Số nguyên dài ngẫu nhiên: " + randomLong);

        //6. Kiểm tra nextBoolean()
        boolean randomBoolean = random.nextBoolean();
        System.out.println("Giá trị boolean ngẫu nhiên: " + randomBoolean);

        //7. Kiểm tra nextGaussian()
        double randomGaussian = random.nextGaussian();
        System.out.println("Giá trị Gaussian ngẫu nhiên: " + randomGaussian);

        //8. Kiểm tra nextBytes()
        byte[] randomBytes = new byte[5];
        random.nextBytes(randomBytes);
        System.out.print("Mảng byte ngẫu nhiên: ");
        for (byte b : randomBytes) {
            System.out.print(b + " ");
        }
        System.out.println();

        //9. Kiểm tra setSeed()
        random.setSeed(12345);
        System.out.println("Số nguyên ngẫu nhiên sau khi setSeed: " + random.nextInt());

        //10. Kiểm tra nextInt() với một khoảng
        int randomIntRange = random.nextInt(50) + 50; // Số nguyên ngẫu nhiên từ 50 đến 99
        System.out.println("Số nguyên ngẫu nhiên (50-99): " + randomIntRange);
    }
}
