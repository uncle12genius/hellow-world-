import java.util.Scanner;
public class age{
    static void checkAge(int age){

        if(age>=18){
            System.out.println("congratulations you are allowed to vote ");
        }else {
            System.out.println("Sorry ! you are not allowed to vote until you achieve  18 years and above ");
        }

    }
    public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);

        System.out.println(" please enter your age ");
        int age = scanner.nextInt();
        checkAge(age);

        scanner.close();
    }

}