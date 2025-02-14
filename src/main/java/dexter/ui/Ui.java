package dexter.ui;

import java.time.LocalDate;

import dexter.parser.Parser;
import dexter.task.Deadline;
import dexter.task.Event;
import dexter.task.Task;
import dexter.task.ToDo;
import dexter.tasklist.TaskList;

/**
 * Serves as the logic behind interactions with the user
 */
public class Ui {
    private static final String LINE_BREAK = "\t____________________________________________________________\n";

    /**
     * Provides custom exception to handle error
     */
    public class ToDoException extends IllegalArgumentException {
        ToDoException(String message) {
            super(message);
        }
    }
    /**
     * Provides custom exception to handle error
     */
    public class DeadlineException extends IllegalArgumentException {
        DeadlineException(String message) {
            super(message);
        }
    }
    /**
     * Provides custom exception to handle error
     */
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
     * Feeds text and responds to interactions with user
     * @param tasks pre-processes any existing tasks
     * @return list of tasks to be saved
     */
    public String run(TaskList tasks, String input) {
        String res;
        //        String greet = LINE_BREAK + "\tHello! I'm Dexter Morgan, ahem...The Bay Harbour Butcher\n"
        //                + "\tWhat can I do for you?\n"
        //                + "\tDo you need help with investigating a crime scene?\n" + LINE_BREAK;
        //        System.out.println(greet);

        String altReply = LINE_BREAK + "\tBye, Hope to see you again soon!\n" + LINE_BREAK;
        while (true) {

            if (input.equals("bye")) {
                res = altReply;
                return res;
            } else if (input.equals("list")) {
                res = tasks.toString();
                return res;
            }

            try {
                String[] keyWord = input.split(" ", 2);
                input = keyWord[0];
                if (keyWord.length < 2) {
                    try {
                        handleExcept(input);
                    } catch (IllegalArgumentException e) {
                        res = e.getMessage();
                        return res;
                    }

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
                        return "";
                    }
                    t = new Deadline(b[0], ld);
                } else if (input.equals("event")) {
                    String[] b = descript.split("/");
                    String temp = b[1].split(" ", 2)[1].strip();
                    int i = temp.lastIndexOf(" ");
                    LocalDate ld = Parser.parseSafely(temp.substring(0, i));
                    if (ld == null) {
                        return "";
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
                    String s = LINE_BREAK + "\t" + reply + "\n" + "\t" + a.toString() + "\n" + LINE_BREAK;
                    res = s;
                    return res;
                } else if (input.equals("due")) {
                    int i = tasks.size();
                    for (int j = 0; j < i; j++) {
                        Task z = tasks.get(j);
                        LocalDate pp = Parser.parseSafely(descript.strip());
                        if (pp == null) {
                            return "";
                        }
                        if (z.isDue(pp)) {
                            res = LINE_BREAK + "\t" + z + LINE_BREAK;
                            return res;
                        }
                    }
                } else if (input.equals("find")) {
                    TaskList e = tasks.findKeyword(descript);
                    res = "\t Here are the matching tasks in your list:" + e;
                    return res;
                } else {
                    try {
                        handleExcept(" ");
                    } catch (IllegalArgumentException e) {
                        res = e.getMessage();
                        return res;
                    }
                }

                if (t != null) {
                    String ans = input.equals("delete") ? "\tNoted. I've removed this task:\n"
                            : "\tGot it. I've added this task:\n";
                    if (!input.equals("delete")) {
                        tasks.add(t);
                    }
                    String reply = LINE_BREAK + ans + "\t" + t;
                    int siz = tasks.size();
                    System.out.println(reply);
                    res = reply + "\tNow you have " + siz + " tasks in the list.\n" + LINE_BREAK;
                    return res;
                }

                String rehash = input + " " + descript;
                String temp = Character.toUpperCase(input.charAt(0)) + " 0 " + descript;
                Task a = createTask(temp);
                tasks.add(a);
                res = LINE_BREAK + "\tadded: " + rehash + "\n" + LINE_BREAK;
                return res;
            } catch (IllegalArgumentException e) {
                return e.getMessage();
            }
        }
    }

    /**
     * Prints error for user
     */
    public void showError() {
        System.out.println("There is no existing database, unable to write to a file, "
                 + "will start with no data");
    }
}
