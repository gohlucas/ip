package dexter.ui;

import dexter.parser.Parser;
import dexter.task.Event;
import dexter.task.Task;
import dexter.task.ToDo;
import dexter.task.Deadline;
import dexter.taskList.TaskList;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Serves as the logic behind interactions with the user
 */
public class Ui {
    String LINE = "\t____________________________________________________________\n";

    public class ToDoException extends IllegalArgumentException {
        ToDoException(String message) {
            super(message);
        }
    }

    public class DeadlineException extends IllegalArgumentException {
        DeadlineException(String message) {
            super(message);
        }
    }

    public class EventException extends IllegalArgumentException {
        EventException(String message) {
            super(message);
        }
    }

    /**
     * Handles exceptions from user input
     * @param txt user input of task type
     * @throws IllegalArgumentException
     */
    public void handleExcept(String txt) throws IllegalArgumentException {
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

    /**
     * Creates tasks for processing by filtering by task type
     * @param input user input of task specifications
     * @return task for processing
     */
    public static Task createTask(String input) {
        String[] keyWord = input.split(" ", 3);
        input = keyWord[0];
        String mark = keyWord[1].equals("1") ? "mark" : "unmark";
        String descript = keyWord[2];

        Task t = null;
        if (input.equals("T")) {
            t = Parser.equalsToDo(descript, mark);
        } else if (input.equals("D")) {
            t = Parser.equalsDeadline(descript, mark);
        } else if (input.equals("E")) {
            t = Parser.equalsEvent(descript, mark);
        }
        return t;
    }

    /**
     * Feeds text and reponds to interactions with user
     * @param tasks pre-processes any existing tasks
     * @return list of tasks to be saved
     */
    public TaskList run(TaskList tasks) {
        String greet = LINE + "\tHello! I'm Dexter Morgan, ahem...The Bay Harbour Butcher\n"
                + "\tWhat can I do for you?\n"
                + "\tDo you need help with investigating a crime scene?\n" + LINE;
        System.out.println(greet);

        Scanner scan = new Scanner(System.in);
        String altReply = LINE + "\tBye, Hope to see you again soon!\n" + LINE;
        while (true) {
            String input = scan.nextLine();

            if (input.equals("bye")) {
                System.out.println(altReply);
                scan.close();
                break;
            } else if (input.equals("list")) {
                System.out.println(tasks);
                continue;
            }

            try {
                String[] keyWord = input.split(" ", 2);
                input = keyWord[0];
                if (keyWord.length < 2) {
                    handleExcept(input);
                }
                String descript = keyWord[1];

                Task t = null;
                if (input.equals("todo")) {
                    t = new ToDo(descript);
                } else if (input.equals("deadline")) {
                    // could use ArrayOutOfBoundsException
                    String[] b = descript.split("/");
                    LocalDate ld = Parser.parseSafely(b[1].split(" ", 2)[1]);
                    if (ld == null) {
                        continue;
                    }
                    t = new Deadline(b[0], ld);
                } else if (input.equals("event")) {
                    String[] b = descript.split("/");
                    String temp = b[1].split(" ", 2)[1].strip();
                    int i = temp.lastIndexOf(" ");
                    LocalDate ld = Parser.parseSafely(temp.substring(0, i));
                    if (ld == null) {
                        continue;
                    }
                    String from = temp.substring(i).strip();
                    t = new Event(b[0], ld, from, b[2].split(" ", 2)[1].strip());
                } else if (input.equals("delete")) {
                    int pos = Integer.valueOf(descript) - 1;
                    t = tasks.get(pos);
                    tasks.remove(pos);
                } else if (input.equals("mark") || input.equals("unmark")) {
                    int j = Integer.parseInt(descript);
                    Task a = tasks.get(j - 1);
                    a.negateCurrentStatus(input);
                    String reply = input.equals("mark") ? "Nice! I've marked this task as done:\n"
                            : "Ok, I've marked this task as not done yet:\n";
                    String s = LINE + "\t" + reply + "\n" + "\t" + a.toString() + "\n" + LINE;
                    System.out.println(s);
                    continue;
                } else if (input.equals("due")) {
                    System.out.println(LINE);
                    int i = tasks.size();
                    for (int j = 0; j < i; j++) {
                        Task z = tasks.get(j);
                        LocalDate pp = Parser.parseSafely(descript.strip());
                        if (pp == null) {
                            continue;
                        }
                        if (z.isDue(pp)) {System.out.println("\t" + z);
                        }
                    }
                    System.out.println(LINE);
                    continue;
                } else if (input.equals("find")) {
                     TaskList e = tasks.findKeyword(descript);
                    System.out.println("\t Here are the matching tasks in your list:");
                    System.out.println(e);
                    continue;
                }

                else {
                    handleExcept(" ");
                }

                if (t != null) {
                    String ans = input.equals("delete") ? "\tNoted. I've removed this task:\n"
                            : "\tGot it. I've added this task:\n";
                    if (!input.equals("delete")) {
                        tasks.add(t);
                    }
                    String reply = LINE + ans + "\t" + t.toString();
                    int siz = tasks.size();
                    System.out.println(reply);
                    System.out.println("\tNow you have " + String.valueOf(siz) + " tasks in the list.\n" + LINE);
                    continue;
                }

                String rehash = input + " " + descript;
                String temp = Character.toUpperCase(input.charAt(0)) + " 0 " + descript;
                Task a = createTask(temp);
                tasks.add(a);
                String resp = LINE + "\tadded: " + rehash + "\n" + LINE;
                System.out.println(resp);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return tasks;
    }

    /**
     * Prints error for user
     */
    public void showError() {
        System.out.println("There is no existing database, will start with no data");
    }
}
