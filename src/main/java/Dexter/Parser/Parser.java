package Dexter.Parser;

import Dexter.Task.Deadline;
import Dexter.Task.Event;
import Dexter.Task.ToDo;
import Dexter.Task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    public static LocalDate myParser(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid Date Format, YYYY-MM-DD required");
            return null;
        }
    }
    public static Task equalsToDo(String descript, String mark) {
        return new ToDo(descript, mark);
    }
    public static Task equalsDeadline(String descript, String mark) {
        String[]b = descript.split("/");
        LocalDate ld = Parser.myParser(b[1].split(" ", 2)[1]);
        return new Deadline(b[0], ld, mark);
    }
    public static Task equalsEvent(String descript, String mark) {
        String[]b = descript.split("/");
        String temp = b[1].split(" ", 2)[1].strip();
        int i = temp.lastIndexOf(" ");
        LocalDate ld = Parser.myParser(temp.substring(0, i));
        String from = temp.substring(i).strip();
        return new Event(b[0], ld, from, b[2].split(" ", 2)[1].strip(), mark);
    }
}