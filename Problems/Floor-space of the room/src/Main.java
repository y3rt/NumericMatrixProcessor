import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        double pi = 3.14;
        Scanner s = new Scanner(System.in);
        String shape = s.nextLine();
        switch (shape){
            case "rectangle":
                double a = s.nextDouble();
                double b = s.nextDouble();
                System.out.println(a * b);
            break;
            case "triangle":
                a = s.nextDouble();
                b = s.nextDouble();
                double c = s.nextDouble();

                double z = (a + b + c) * .5;
                System.out.println(Math.sqrt(z * (z - a) * (z - b) * (z - c)));

            break;
            case "circle":
                double r = s.nextDouble();
                System.out.println(pi * (r * r));
            break;
        }
    }
}
