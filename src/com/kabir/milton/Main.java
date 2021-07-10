package com.kabir.milton;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Words: " + countWords(args[0]));
        System.out.println("Sentences: " + countSentences(args[0]));
        System.out.println("Characters: " + countChar(args[0]));
        System.out.printf("The score is: %.2f\n", countScore(args[0]));
        String st=gradeLevel(args[0]);
        System.out.println("This text should be understood by "+st+" year olds.");
    }

    public static int countSentences(String path) {
        File file = new File(path);
        int countSenteces = 0;
        String regex = "[.!?]+";
        try (Scanner sc = new Scanner(file)) {
            String[] arr = sc.nextLine().split(regex);
            countSenteces = arr.length;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return countSenteces;
    }

    public static int countWords(String path) {
        File file = new File(path);
        int countWord = 0;
        try (Scanner sc = new Scanner(file)) {
            String[] arr = sc.nextLine().split(" ");
            countWord = arr.length;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return countWord;

    }

    public static int countChar(String path) {
        int countChar = 0;
        File file = new File(path);
        try (Scanner sc = new Scanner(file)) {
            countChar = sc.nextLine().replaceAll("\\s", "").split("").length;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return countChar;
    }

    public static double countScore(String path) {
        double characters = countChar(path);
        double sentences = countSentences(path);
        double words = countWords(path);
        double score = 0;
        score = 4.71 * (characters / words) + 0.5 * (words / sentences) - 21.43;
        return score;
    }
    public static String gradeLevel(String path) {
        int score = (int) Math.ceil(countScore(path));
        String st="";
        switch (score) {
            case 1:
                st="5-6";
                break;
            case 2:
                st="6-7";
                break;
            case 3:
                st="7-9";
                break;
            case 4:
                st="9-10";
                break;
            case 5:
                st="10-11";
                break;
            case 6:
                st="11-12";
                break;
            case 7:
                st="12-13";
                break;
            case 8:
                st="13-14";
                break;
            case 9:
                st="14-15";
                break;
            case 10:
                st="15-16";
                break;
            case 11:
                st="16-17";
                break;
            case 12:
                st="17-18";
                break;
            case 13:
                st="18-24";
                break;
            case 14:
                st="24+";
                break;
        }
        return st;
    }
}