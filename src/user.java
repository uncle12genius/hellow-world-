import java .util.Scanner;
public class user{
 public static void main  (String[]args){
     Scanner name = new Scanner(System.in);
     System.out.println("enter your name ");

     String userName = name.nextLine();
     System.out.println("username is :"+ userName);

     name.close ();
 }
}
