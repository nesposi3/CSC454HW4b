package com.nesposi3;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        VendingMachine vendingMachine = new VendingMachine();
        Scanner sc = new Scanner(System.in);
        ArrayList<String> trajectory = new ArrayList<>();
        if(args.length>0){
            // File mode
            File f = new File(args[0]);
        }else{

        }
        for(;;){
            String command = sc.nextLine();

            if(command.equals("done")){

            }else if(command.equals("exit")){
                break;
            }
        }
    }

}
