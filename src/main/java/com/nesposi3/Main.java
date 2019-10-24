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
        File f = new File(args[0]);
        try {
            Scanner fileScanner = new Scanner(f);
            while (fileScanner.hasNextLine()) {
                trajectory.add(fileScanner.nextLine());
            }
            vendingMachine.simulate(trajectory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
