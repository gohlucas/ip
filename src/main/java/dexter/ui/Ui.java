package dexter.ui;

import dexter.parser.Parser;
import dexter.task.Task;
import dexter.tasklist.TaskList;

/**
 * Serves as the logic behind interactions with the user
 */
public class Ui {
    private static final String LINE_BREAK = "\t____________________________________________________________\n";

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

        Task t;
        switch(input) {
        case "T":
            t = Parser.equalsToDo(descript, mark);
            break;
        case "D":
            t = Parser.equalsDeadline(descript, mark);
            break;
        case "E":
            t = Parser.equalsEvent(descript, mark);
            break;
        default:
            t = null;
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
                // could use a placeholder function to remove the arrowhead
                String[] keyWord = input.split(" ", 2);
                input = keyWord[0];
                if (keyWord.length < 2) {
                    try {
                        Parser.handleExcept(input);
                    } catch (IllegalArgumentException e) {
                        res = e.getMessage();
                        return res;
                    }

                }
                String descript = keyWord[1];

                Task t = null;
                switch(input) {
                case "todo":
                    res = Parser.todoValidator(tasks, descript);
                    return res;
                case "deadline":
                    // could use ArrayOutOfBoundsException
                    res = Parser.deadlineValidator(tasks, descript);
                    return res;
                case "event":
                    res = Parser.eventValidator(tasks, descript);
                    return res;
                case "delete":
                    int pos = Integer.valueOf(descript) - 1;
                    t = tasks.get(pos);
                    tasks.remove(pos);
                    String ans = "\tNoted. I've removed this task:\n";
                    String reply = LINE_BREAK + ans + "\t" + t;
                    int siz = tasks.size();
                    res = reply + "\tNow you have " + siz + " tasks in the list.\n" + LINE_BREAK;
                    return res;
                case "mark":
                case "unmark":
                    res = Parser.markHandler(tasks, descript, input);
                    return res;
                case "due":
                    res = Parser.dueFinder(tasks, descript);
                    return res;
                case "find":
                    res = Parser.taskFinder(tasks, descript);
                    return res;
                case "retrieve":
                    res = Parser.eventFinder(tasks);
                    return res;
                default:
                    try {
                        Parser.handleExcept(input);
                    } catch (IllegalArgumentException e) {
                        res = e.getMessage();
                        return res;
                    }
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
