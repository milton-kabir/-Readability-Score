package com.kabir.milton;
//package readability;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path="/home/drifter/Downloads/dataset_91065.txt";
//        String path=args[0];
        File file = new File(path);
        String arr="";
        try (Scanner sc = new Scanner(file)) {
            arr = sc.nextLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The text is:");
        System.out.println(arr);
        System.out.println();
//        System.out.println();

        System.out.println("Words: " + countWords(path));
        System.out.println("Sentences: " + countSentences(path));
        System.out.println("Characters: " + countChar(path));
        System.out.println("Syllables: " + countSyllable(path));
        System.out.println("Polysyllables: "+ countPolySyllable(path));
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scanner=new Scanner(System.in);
        String score = scanner.nextLine().toUpperCase();
        DecimalFormat formatter = new DecimalFormat("00.##");
        System.out.println();
//        System.out.println();
        switch (score){
            case "ARI":
                System.out.println("Automated Readability Index: " + formatter.format(automatedReadabilityIndex(path)) + " (about "+ gradeLevel1(path) + " year olds.)");
                break;
            case "FK":
                System.out.println("Flesch–Kincaid readability tests: "+ formatter.format(fleshKincaidReadability(path)) + " (about "+gradeLevel2(path)+" year olds.)");
                break;
            case "SMOG":
                System.out.println("Simple Measure of Gobbledygook: " + formatter.format(smogIndex(path)) +" (about "+gradeLevel3(path)+" year olds.)");
                break;
            case "CL":
                System.out.println("Coleman–Liau index: " + formatter.format(clIndex(path)) + " (about " + gradeLevel4(path) + " year olds).");
                break;
            case "ALL":
                System.out.println("Automated Readability Index: " + formatter.format(automatedReadabilityIndex(path)) + " (about "+ gradeLevel1(path) + " year olds.)");
                System.out.println("Flesch–Kincaid readability tests: "+ formatter.format(fleshKincaidReadability(path)) + " (about "+gradeLevel2(path)+" year olds.)");
                System.out.println("Simple Measure of Gobbledygook: " + formatter.format(smogIndex(path)) +" (about "+gradeLevel3(path)+" year olds.)");
                System.out.println("Coleman–Liau index: " + formatter.format(clIndex(path)) + " (about " + gradeLevel4(path) + " year olds).");
                System.out.println();
//                System.out.println("The text should be understood in average by " + (double)ageTotal / 4 +" year olds.");
                break;
            default:
                break;
        }
//        System.out.println();
        String[] s1=gradeLevel1(path).split("-");
        String[] s2=gradeLevel2(path).split("-");
        String[] s3=gradeLevel3(path).split("-");
        String[] s4=gradeLevel4(path).split("-");
//        System.out.println(s1[0]+" "+s2[0]+" "+s[3]+" "+s4[0]+" ");
        int ag= (Integer.parseInt(s1[0])+Integer.parseInt(s2[0])+Integer.parseInt(s3[0])+Integer.parseInt(s4[0]));
        double age=(double)(ag/4.0);
        System.out.println("This text should be understood in average by "+age+"-year-olds.");

//        System.out.printf("The score is: %.2f\n", countScore(path));
//        String st=gradeLevel(path);
//        System.out.println("This text should be understood by "+st+" year olds.");
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





    public static int countSyllable(String path) {
        int syllableCount = 0;
        int characterCount = 0;
        int polysyllableCount = 0;
        int syllableCountOneWord = 0;
        int count = 0;

        boolean thisVowel = false;
        boolean nextVowel = false;
        String currentCharacter = "";

        List<String> pieces = new ArrayList<>();
        File file = new File(path);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()){
                currentCharacter = sc.next();
                pieces.add(currentCharacter);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (String piece:pieces){
            syllableCountOneWord = 0;

            //If the word is a single character, we add it to the syllable count and we skip it from further calculations.
            if (piece.length() == 1){
                syllableCount++;
                continue;
            }

            for (int i = 0; i < piece.length() - 1; i++) {
                characterCount++;

                //Single is the character which we are using currently for the comparison.
                char single = piece.charAt(i);
                thisVowel = isVowel(single);

                //nextVowel is the character which comes after 'single' character.
                //Loop runs from 0 to length-2. So, we don't get ArrayIndexOutOfBoundsException
                nextVowel = isVowel(piece.charAt(i + 1));
                if (i == piece.length()-2){
                    nextVowel = false;
                }

                //If the current character is a vowel, we are increasing the count.
                //And if the next character is not a vowel then, we are increasing the syllableCount.
                if (thisVowel){
                    count++;
                    if (!nextVowel){
                        syllableCount++;
                        syllableCountOneWord++;
                    }
                }
            }

            //if one word has more than 2 syllable, then the word is polysyllable. So, increasing its count.
            if (syllableCountOneWord > 2){
                polysyllableCount++;
            }

            //If we don't have any vowel, then we are increasing the syllable count by one.
            if (count == 0){
                syllableCount++;
            }else {
                count = 0;
            }


        }
        return syllableCount;
    }
    public static int countPolySyllable(String path) {
        int syllableCount = 0;
        int characterCount = 0;
        int polysyllableCount = 0;
        int syllableCountOneWord = 0;
        int count = 0;

        boolean thisVowel = false;
        boolean nextVowel = false;
        String currentCharacter = "";

        List<String> pieces = new ArrayList<>();
        File file = new File(path);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()){
                currentCharacter = sc.next();
                pieces.add(currentCharacter);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (String piece:pieces){
            syllableCountOneWord = 0;

            //If the word is a single character, we add it to the syllable count and we skip it from further calculations.
            if (piece.length() == 1){
                syllableCount++;
                continue;
            }

            for (int i = 0; i < piece.length() - 1; i++) {
                characterCount++;

                //Single is the character which we are using currently for the comparison.
                char single = piece.charAt(i);
                thisVowel = isVowel(single);

                //nextVowel is the character which comes after 'single' character.
                //Loop runs from 0 to length-2. So, we don't get ArrayIndexOutOfBoundsException
                nextVowel = isVowel(piece.charAt(i + 1));
                if (i == piece.length()-2){
                    nextVowel = false;
                }

                //If the current character is a vowel, we are increasing the count.
                //And if the next character is not a vowel then, we are increasing the syllableCount.
                if (thisVowel){
                    count++;
                    if (!nextVowel){
                        syllableCount++;
                        syllableCountOneWord++;
                    }
                }
            }

            //if one word has more than 2 syllable, then the word is polysyllable. So, increasing its count.
            if (syllableCountOneWord > 2){
                polysyllableCount++;
            }

            //If we don't have any vowel, then we are increasing the syllable count by one.
            if (count == 0){
                syllableCount++;
            }else {
                count = 0;
            }


        }
        return polysyllableCount;
    }

    public static String gradeLevel1(String path) {
        int score = (int) Math.ceil(automatedReadabilityIndex(path));
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
                st="24-100";
                break;
            default:
                st="25-100";
                break;
        }
        return st;
    }
    public static String gradeLevel2(String path) {
        int score = (int) Math.ceil(fleshKincaidReadability(path));
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
                st="24-100";
                break;
            default:
                st="25-100";
                break;
        }
        return st;
    }
    public static String gradeLevel3(String path) {
        int score = (int) Math.ceil(smogIndex(path));
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
                st="24-100";
                break;
            default:
                st="25-100";
                break;
        }
        return st;
    }
    public static String gradeLevel4(String path) {
        int score = (int) Math.ceil(clIndex(path));
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
                st="24-100";
                break;
            default:
                st="25-100";
                break;
        }
        return st;
    }

    public static boolean isVowel(char c){
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y'){
            return true;
        }
        return false;
    }
    public static double automatedReadabilityIndex(String path) {
        double characters = countChar(path);
        double sentences = countSentences(path);
        double words = countWords(path);
        double score = 0;
        score = 4.71 * (characters / words) + 0.5 * (words / sentences) - 21.43;
        return score;
    }
    public static double fleshKincaidReadability(String path){
        double syllables = countSyllable(path);
        double sentences = countSentences(path);
        double words = countWords(path);

        double scoreDecimal = 0.39 * ((double) words/sentences) + 11.8 * ((double) syllables/words) - 15.59;
        return scoreDecimal;
    }

    public static double smogIndex(String path){
        double polysyllables = countPolySyllable(path);
        double sentences = countSentences(path);
        double scoreDecimal = (1.043 * Math.sqrt((polysyllables * (30/(double)sentences))) + 3.1291);
        return scoreDecimal;
    }

    public static double clIndex(String path){
        double letters=countChar(path);
        double words=countWords(path);
        double sentences=countSentences(path);

        double CLI = (0.0588 * ((letters / words) * 100)) - (0.296 * ((sentences / words) * 100)) - 15.8;
        return CLI;
    }

}