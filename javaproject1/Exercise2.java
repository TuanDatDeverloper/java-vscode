public class Exercise2 {
    public static void main(String[] args) {
        String strl = new String ("VKU is one of the best universities in Vietnam");
        String strl2 = new String ("Prof. Phap is an OOP teacher");
        System.out.println("strl contains " + strl.length() + " characters");
        int n=strl.compareTo(strl2);
        if (n>0)
            System.out.println("strl is greater than strl2");
            else
            if (n==0)
            System.out.println("strl is equal to strl2");
            else System.out.println("strl is less than strl2");
            //put your code for other methods from here
            //.......
    }
}