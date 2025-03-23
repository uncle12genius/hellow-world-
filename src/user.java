import java.util.Scanner;
public class user {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("enter your age ?");
        int Age = sc.nextInt();

        if(Age<= 18 ){
            System.out.println("Your are  not allowed to vote ");


        }else {
            System.out.println("Your are allowed to vote ");
        }

        sc.close();
    }

}