package com.kabir.milton;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner sc=new Scanner(System.in);
        String st=sc.nextLine();
        if(st.length()>100){
            System.out.println("HARD");
        }
        else{
            System.out.println("EASY");
        }
    }
}
