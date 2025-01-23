import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Dexter {
    public static void main(String[] args) {
        String greet = "\t____________________________________________________________\n"
        + "\tHello! I'm Dexter Morgan, ahem...The Bay Harbour Butcher\n"
        + "\tWhat can I do for you?\n"
        + "\tDo you need help with investigating a crime scene?\n"
        + "\t____________________________________________________________\n";

        System.out.println(greet);
        List<String> lst = new ArrayList<>();
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
            if (input.equals("list")) {
                System.out.println("\t____________________________________________________________\n");
                for (int i = 0; i < lst.size(); i++) {
                    System.out.println("\t" + String.valueOf(i + 1) + ". " + lst.get(i));
                }
                System.out.println("\t____________________________________________________________\n");
                continue;
            }
            lst.add(input);
            String reply = "\t____________________________________________________________\n"
            + "\tadded: " + input + "\n"
            + "\t____________________________________________________________\n";
            System.out.println(reply);
        }
    }
}
