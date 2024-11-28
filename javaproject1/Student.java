import java.util.Scanner;
public class Student {
    public static void main(String[] args) {
		Scanner dat = new Scanner(System.in);
		System.out.println("Enter your name: ");
		String name = dat.nextLine();
		System.out.println("Your name is: " + name);
		dat.close();
	}
}