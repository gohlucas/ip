package dexter.taskList;

import dexter.task.Task;
import java.util.ArrayList;

public class TaskList {
    String LINE = "\t____________________________________________________________\n";
    private ArrayList<Task> myList;

    public TaskList() {
        this.myList = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> lst) {
        this.myList = new ArrayList<>(lst);
    }

    public void add(Task t) {
        this.myList.add(t);
    }
    public void remove(int i) {
        this.myList.remove(i);
    }

    public Task get(int i) {
        return this.myList.get(i);
    }
    public int size() {
        return this.myList.size();
    }
    public String toSave() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < myList.size(); i++) {
            String s = myList.get(i).getAll() + "\n";
            builder.append(s);
        }
        return builder.toString();
    }
    public TaskList findKeyword(String keyword) {
        ArrayList<Task> keywordList = new ArrayList<>();
        for (int i = 0; i < myList.size(); i++) {
            Task a = this.myList.get(i);
            String b = a.getAll();
            if (b.contains(keyword)) {
                keywordList.add(a);
            }
        }
        return new TaskList(keywordList);
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(LINE);
        for (int i = 0; i < myList.size(); i++) {
            String s = "\t" + String.valueOf(i + 1) + ". " + myList.get(i) + "\n";
            builder.append(s);
        }
        builder.append(LINE);
        return builder.toString();
    }
}
