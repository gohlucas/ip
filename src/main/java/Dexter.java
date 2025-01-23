import java.util.Scanner;

public class Dexter {
    public static void main(String[] args) {
        String greet = "\t____________________________________________________________\n"
        + "\tHello! I'm Dexter Morgan, ahem...The Bay Harbour Butcher\n"
        + "\tWhat can I do for you?\n"
        + "\tDo you need help with investigating a crime scene?\n"
        + "\t____________________________________________________________\n";

        System.out.println(greet);
        Scanner scan = new Scanner(System.in);
        String altReply = "\t____________________________________________________________\n"
                + "\tBye, Hope to see you again soon!\n"
                + "\t____________________________________________________________\n";
        while(true) {
            String input = scan.nextLine();
            if (input.equals("bye")) {
                System.out.println(altReply);
                break;
            }
            String reply = "\t____________________________________________________________\n"
                    + "\t" + input + "\n"
                    + "\t____________________________________________________________\n";
            System.out.println(reply);
        }
    }
}
