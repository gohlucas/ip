package dexter.parser;

import dexter.task.Deadline;
import dexter.task.Event;
import dexter.task.ToDo;
import dexter.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Ingests user input and transmute it into method friendly variables
 */
public class Parser {
    /**
     * Handles safe conversion of date given by user
     * @param input date from user
     * @return Date in method friendly format or prints invalid format statement
     */
    public static LocalDate parseSafely(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid Date Format, YYYY-MM-DD required");
            return null;
        }
    }

    /**
     * Converts user input into a ToDo task
     * @param descript user description of task
     * @param mark placeholder to mark if task is done
     * @return ToDo class to store in TaskList
     */
    public static Task equalsToDo(String descript, String mark) {
        return new ToDo(descript, mark);
    }

    /**
     * Converts user input into a Deadline task
     * @param descript user description of task
     * @param mark placeholder to mark if task is done
     * @return Deadline class to store in TaskList
     */
    public static Task equalsDeadline(String descript, String mark) {
        String[]b = descript.split("/");
        LocalDate ld = Parser.parseSafely(b[1].split(" ", 2)[1]);
        return new Deadline(b[0].strip(), ld, mark);
    }

    /**
     * Converts user input into a Event task
     * @param descript user description of task
     * @param mark placeholder to mark if task is done
     * @return
     */
    public static Task equalsEvent(String descript, String mark) {
        String[]b = descript.split("/");
        String temp = b[1].split(" ", 2)[1].strip();
        int i = temp.lastIndexOf(" ");
        LocalDate ld = Parser.parseSafely(temp.substring(0, i));
        String from = temp.substring(i).strip();
        return new Event(b[0].strip(), ld, from, b[2].split(" ", 2)[1].strip(), mark);
    }
}