import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.io.File;
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

        public Deadline(String description, String by, String mark) {
            super(description);
            this.by = by;
            super.changeDoneStatus(mark);
        }

        public Deadline(String description, String by) {
            this(description, by, "unmark");
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    public static class ToDo extends Task {

        public ToDo(String description, String mark) {
            super(description);
            super.changeDoneStatus(mark);
        }

        public ToDo(String description) {
            this(description, "unmark");
        }
        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    public static class Event extends Task {

        private String from;
        private String to;

        public Event(String description, String from, String to, String mark) {
            super(description);
            this.from = from;
            this.to = to;
            super.changeDoneStatus(mark);
        }

        public Event(String description, String from, String to) {
            this(description, from, to, "unmark");
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + from + ")" + " (to: " + to + ")";
        }
    }

    public static class ToDoException extends IllegalArgumentException {
        ToDoException(String message) {
            super(message);
        }
    }

    public static class DeadlineException extends IllegalArgumentException {
        DeadlineException(String message) {
            super(message);
        }
    }

    public static class EventException extends IllegalArgumentException {
        EventException(String message) {
            super(message);
        }
    }

    public static void handleExcept(String txt) throws IllegalArgumentException {
        switch (txt) {
            case "todo":
                throw new ToDoException("todo requires an task description");
            case "deadline":
                throw new DeadlineException(("deadline requires a end timing"));
            case "event":
                throw new EventException("event requires a start and end timing");
            default:
                throw new IllegalArgumentException("I'm not quite sure what you mean");
        }
    }

    public static Task createTask(String input) {
        String[] keyWord = input.split(" ", 3);
        input = keyWord[0];
        String mark = keyWord[1].equals("1") ? "mark" : "unmark";
        String descript = keyWord[2];

        Task t = null;
        if (input.equals("T")) {
            t = new ToDo(descript, mark);
        }

        if (input.equals("D")) {
            String[]b = descript.split("/");
            t = new Deadline(b[0], b[1].split(" ", 2)[1], mark);
        }

        if (input.equals("E")) {
            String[]b = descript.split("/");
            t = new Event(b[0], b[1].split(" ", 2)[1], b[2].split(" ", 2)[1], mark);
        }

        return t;
    }

    public static void main(String[] args) {
        List<Task> lst = new ArrayList<>();
        try {
            File f = new File("Data.txt");
            Scanner s = new Scanner(f);
            while(s.hasNext()) {
                String t = s.nextLine();
                Task p = createTask(t);
                lst.add(p);
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("There is no existing database, will start with no data");
        }

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
                scan.close();
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

            try {
                String[] keyWord = input.split(" ", 2);
                input = keyWord[0];
                String err = "Error, not enough arguments";
                if (keyWord.length < 2) {
                    handleExcept(input);
                }
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

                if (input.equals("delete")) {
                    int pos = Integer.valueOf(descript) - 1;
                    t = lst.get(pos);
                    lst.remove(pos);
                }
                if (t != null) {
                    String ans = input.equals("delete") ? "\tNoted. I've removed this task:\n"
                            : "\tGot it. I've added this task:\n";
                    if (!input.equals("delete")) {
                        lst.add(t);
                    }
                    String reply = "\t____________________________________________________________\n"
                            + ans
                            + "\t" + t.toString();
                    int siz = lst.size();;
                    System.out.println(reply);
                    System.out.println("\tNow you have " + String.valueOf(siz) + " tasks in the list.\n"
                            + "\t____________________________________________________________\n");
                    continue;
                }

                if (input.equals("mark") || input.equals("unmark")) {
                    int j = Integer.parseInt(descript);
                    Task a = lst.get(j - 1);
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
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
