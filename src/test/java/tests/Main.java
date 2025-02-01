package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void hehe() {
//        try {
            File f = new File("Data.txt");
            System.out.println("Success");
//        } catch (IOException e) {
//            System.out.println("sed");
//        }
    }
    public static void main(String[] args) {
        Main.hehe();
    }
}
