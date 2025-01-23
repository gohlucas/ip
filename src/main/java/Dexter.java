import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
// refactor code to reduce repetition
public class Dexter {
    public static class Task {
        private String description;
        private boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        public void changeDoneStatus(String bool) {
            this.isDone = bool.equals("mark");
        }

        @Override
        public String toString() {
            String mark = (this.isDone) ? "X" : " ";
            return "[" + mark + "] " + this.description;
        }
    }

    public static class Deadline extends Task {
        private String by;

        public Deadline(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    public static class ToDo extends Task {

        public ToDo(String description) {
            super(description);
        }
        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    public static class Event extends Task {

        private String from;
        private String to;

        public Event(String description, String from, String to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + from + ")" + " (to: " + to + ")";
        }
    }

    public static void main(String[] args) {
        String greet = "\t____________________________________________________________\n"
        + "\tHello! I'm Dexter Morgan, ahem...The Bay Harbour Butcher\n"
        + "\tWhat can I do for you?\n"
        + "\tDo you need help with investigating a crime scene?\n"
        + "\t____________________________________________________________\n";

        System.out.println(greet);

        List<Task> lst = new ArrayList<>();
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

            String[] keyWord = input.split(" ", 2);
            input = keyWord[0];
            String descript = keyWord[1];
            Task t = null;
            if (input.equals("todo")) {
                t = new ToDo(descript);
            }

            if (input.equals("deadline")) {
                String[]b = descript.split("/");
                t = new Deadline(b[0], b[1].split(" ", 2)[1]);
            }

            if (input.equals("event")) {
                String[]b = descript.split("/");
                t = new Event(b[0], b[1].split(" ", 2)[1], b[2].split(" ", 2)[1]);
            }
            if (t != null) {
                lst.add(t);
                String reply = "\t____________________________________________________________\n"
                        + "\tGot it. I've added this task:\n"
                        + "\t" + t.toString();
                int siz = lst.size();;
                System.out.println(reply);
                System.out.println("\tNow you have " + String.valueOf(siz) + " tasks in the list.\n"
                        + "\t____________________________________________________________\n");
                continue;
            }

            if (input.equals("mark") || input.equals("unmark")) {
                int j = Integer.parseInt(descript);
                Task a = lst.get(j);
                a.changeDoneStatus(input);
                String reply = input.equals("mark") ? "Nice! I've marked this task as done:\n"
                        : "Ok, I've marked this task as not done yet:\n";
                String s = "\t____________________________________________________________\n"
                        + "\t" + reply + "\n"
                        + "\t" + a.toString() + "\n"
                        + "\t____________________________________________________________\n";
                System.out.println(s);
                continue;
            }
            String rehash = input + " " + descript;
            Task a = new Task(rehash);
            lst.add(a);
            String resp = "\t____________________________________________________________\n"
            + "\tadded: " + rehash + "\n"
            + "\t____________________________________________________________\n";
            System.out.println(resp);
        }
    }
}
