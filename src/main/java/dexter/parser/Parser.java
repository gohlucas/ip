package dexter.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import dexter.task.Deadline;
import dexter.task.Event;
import dexter.task.Task;
import dexter.task.ToDo;
import dexter.tasklist.TaskList;

/**
 * Ingests user input and transmute it into method friendly variables
 */
public class Parser {
    private static final String LINE_BREAK = "\t____________________________________________________________\n";
    /**
     * Handles safe conversion of date given by user
     * @param input date from user
     * @return Date in method friendly format or prints invalid format statement
     */
    public static LocalDate parseSafely(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Converts user input into a ToDo task
     * @param description user description of task
     * @param mark placeholder to mark if task is done
     * @return ToDo class to store in TaskList
     */
    public static Task equalsToDo(String description, String mark) {
        return new ToDo(description, mark);
    }

    /**
     * Converts user input into a Deadline task
     * @param description user description of task
     * @param mark placeholder to mark if task is done
     * @return Deadline class to store in TaskList
     */
    public static Task equalsDeadline(String description, String mark) {
        String[]b = description.split("/");
        LocalDate ld = Parser.parseSafely(b[1].split(" ", 2)[1]);
        return new Deadline(b[0].strip(), ld, mark);
    }

    /**
     * Converts user input into an Event task
     * @param description user description of task
     * @param mark placeholder to mark if task is done
     * @return Event class to store in TaskList
     */
    public static Task equalsEvent(String description, String mark) {
        String[]b = description.split("/");
        String[] temp = b[1].split(" ", 4);
        String[] thirdGrp = b[2].split(" ", 3);
        LocalDate ld = Parser.parseSafely(temp[1]);
        return new Event(b[0].strip(), ld, temp[2], thirdGrp[1], temp[3].strip(), thirdGrp[2], mark);
    }

    /**
     * Provides custom exception to handle error
     */
    public static class ToDoException extends IllegalArgumentException {
        ToDoException(String message) {
            super(message);
        }
    }
    /**
     * Provides custom exception to handle error
     */
    public static class DeadlineException extends IllegalArgumentException {
        DeadlineException(String message) {
            super(message);
        }
    }
    /**
     * Provides custom exception to handle error
     */
    public static class EventException extends IllegalArgumentException {
        EventException(String message) {
            super(message);
        }
    }

    /**
     * Handles exceptions from user input
     * @param txt user input of task type
     * @throws IllegalArgumentException if argument is invalid
     */
    public static void handleExcept(String txt) throws IllegalArgumentException {
        switch (txt) {
        case "todo":
            throw new ToDoException("todo requires an task description");
        case "deadline":
            throw new DeadlineException(("deadline requires a end timing and a prefix of /by"));
        case "event":
            throw new EventException("event requires a start and end timing");
        default:
            throw new IllegalArgumentException("I'm not quite sure what you mean, please try the \"help\" command");
        }
    }

    /**
     * Processes user input to create Task, add to TaskList and return result to User
     * @param tasks TaskList object handling task related operations
     * @param description user's parameters
     * @return string to show success
     * @throws EventException if event is not following format
     */
    public static String eventValidator(TaskList tasks, String description) throws EventException {
        boolean isEvent = description.contains("/from") && description.contains("/to");
        if (!isEvent) {
            handleExcept("event");
        }
        String[] b = description.split("/");
        String[] temp = b[1].split(" ", 4);
        String[] thirdGrp = b[2].split(" ", 3);
        LocalDate ld = Parser.parseSafely(temp[1]);
        String location = temp[3].strip();
        String details = thirdGrp[2].strip();
        if (location.equals("")) {
            return "Invalid Format, Location required";
        }
        if (details.equals("")) {
            return "Invalid Format, Details required";
        }
        if (ld == null) {
            return "Invalid Date Format, YYYY-MM-DD required";
        }
        return taskHandler(tasks, new Event(
                b[0].strip(), ld, temp[2], thirdGrp[1].strip(), location, details));
    }

    /**
     * Processes user input to create Task, add to TaskList and return result to User
     * @param tasks TaskList object handling task related operations
     * @param description user's parameters
     * @return string to show success
     * @throws DeadlineException if deadline is not following format
     */
    public static String deadlineValidator(TaskList tasks, String description) throws DeadlineException {
        boolean isDeadline = description.contains("/by");
        if (!isDeadline) {
            handleExcept("deadline");
        }
        String[] b = description.split("/");
        LocalDate ld = Parser.parseSafely(b[1].split(" ", 2)[1]);
        if (ld == null) {
            return "Invalid Date Format, YYYY-MM-DD required";
        }
        return taskHandler(tasks, new Deadline(b[0], ld));
    }

    /**
     * Processes user input to create Task, add to TaskList and return result to User
     * @param tasks TaskList object handling task related operations
     * @param description user's parameters
     * @return string to show success
     * @throws ToDoException if todo is not following format
     */
    public static String todoValidator(TaskList tasks, String description) throws ToDoException {
        boolean isTodo = !description.trim().isEmpty();
        if (!isTodo) {
            handleExcept("todo");
        }
        return taskHandler(tasks, new ToDo(description));
    }

    /**
     * Handles the taskFinding process
     * @param tasks TaskList object handling task related operations
     * @param description user's parameters
     * @return string of results containing tasks fitting description
     */
    public static String taskFinder(TaskList tasks, String description) {
        TaskList c = tasks.findKeyword(description);
        return "\t Here are the matching tasks in your list: \n" + c;
    }

    /**
     * Handles finding tasks due on specified date
     * @param tasks TaskList object handling task related operations
     * @param description user's parameters
     * @return string of results containing tasks fitting description
     */
    public static String dueFinder(TaskList tasks, String description) {
        int i = tasks.size();
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(LINE_BREAK);
        for (int j = 0; j < i; j++) {
            Task z = tasks.get(j);
            LocalDate pp = Parser.parseSafely(description.strip());
            if (pp == null) {
                return "Invalid Date Format, YYYY-MM-DD required";
            }
            if (z.isDue(pp)) {
                String res = "\t" + z + "\n";
                strBuilder.append(res);
            }
        }
        strBuilder.append(LINE_BREAK);
        return strBuilder.toString();
    }

    /**
     * Handles logic of marking and unmark tasks
     * @param tasks TaskList object handling task related operations
     * @param description user's parameters
     * @param input user input of "mark" or "unmark"
     * @return string showing success
     */
    public static String markHandler(TaskList tasks, String description, String input) {
        int j = Integer.parseInt(description);
        Task a = tasks.get(j - 1);
        if (a == null) {
            String s = "Invalid index, no such task exists. Choose an index within the list";
            return s;
        }
        a.negateCurrentStatus(input);
        String reply = input.equals("mark") ? "Nice! I've marked this task as done:\n"
                : "Ok, I've marked this task as not done yet:\n";
        return LINE_BREAK + "\t" + reply + "\n" + "\t" + a + "\n" + LINE_BREAK;
    }

    /**
     * Handles the prepending and appending of information to Tasks for printing to user
     * @param tasks TaskList object handling task related operations
     * @param t Task to be concatenated into result to print to user
     * @return string of details
     */
    private static String taskHandler(TaskList tasks, Task t) {
        String ans = "\tGot it. I've added this task:\n";
        tasks.add(t);
        String reply = LINE_BREAK + ans + "\t" + t + "\n";
        int siz = tasks.size();
        return reply + "\tNow you have " + siz + " tasks in the list.\n" + LINE_BREAK;
    }

    /**
     * Handles the eventFinding process
     * @return string of results containing events
     */
    public static String eventFinder(TaskList tasks) {
        ArrayList c = tasks.getEvents();
        return "\t Here is the Event List: \n" + c;
    }

    /**
     * Handles deletion process and printing of reply
     * @param pos index of the task to remove in the list
     * @param tasks TaskList object containing list of objects
     * @return reply to user
     */
    public static String deleteHandler(int pos, TaskList tasks) {
        Task t = tasks.get(pos);
        if (t == null) {
            String s = "Invalid index, no such task exists. Choose an index within the list";
            return s;
        }
        tasks.remove(pos);
        String ans = "\tNoted. I've removed this task:\n";
        String reply = LINE_BREAK + ans + "\t" + t + "\n";
        int siz = tasks.size();
        String res = reply + "\tNow you have " + siz + " tasks in the list.\n" + LINE_BREAK;
        return res;
    }
}
