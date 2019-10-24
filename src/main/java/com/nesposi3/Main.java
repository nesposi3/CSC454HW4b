package com.nesposi3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CallManagerException {
        VendingMachine vendingMachine = new VendingMachine();
        Scanner sc = new Scanner(System.in);
        ArrayList<String> trajectory = new ArrayList<>();
        boolean debug = false;
        File f = new File("traj.txt");
        for (int i = 0; i <args.length ; i++) {
            if(args[i].equals("--debug")|| args[i].equals("-d")){
                debug = true;
            }else{
                f = new File(args[i]);
            }
        }
        try {
            Scanner fileScanner = new Scanner(f);
            while (fileScanner.hasNextLine()) {
                trajectory.add(fileScanner.nextLine());
            }
            vendingMachine.simulate(trajectory,debug);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
