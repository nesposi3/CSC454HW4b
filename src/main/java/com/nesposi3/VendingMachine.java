package com.nesposi3;

import java.util.List;

public class VendingMachine {
    private int quarter, dime, nickel, value;
    private boolean change;
    private final double INFINITY = Double.POSITIVE_INFINITY;

    public VendingMachine() {
        this.quarter = 0;
        this.dime = 0;
        this.nickel = 0;
        this.value = 0;
    }

    public VendingMachine(int quarter, int dime, int nickel, int value) {
        this.quarter = quarter;
        this.dime = dime;
        this.nickel = nickel;
        this.value = value;
    }

    private String lambda() throws CallManagerException {
        StringBuilder out = new StringBuilder(" {OUT: ");
        int val = this.value;
        while (val >= 100) {
            //vend coffee
            out.append(" COFFEE ");
            val -= 100;
        }
        int[] changeBack = this.getChange(val, this.quarter, this.dime, this.nickel);
        int q = changeBack[0];
        int d = changeBack[1];
        int n = changeBack[2];
        if (q != 0 || d != 0 || n != 0) {
            if (q > 0) {
                out.append(q + " QUARTERS ");
            }
            if (d > 0) {
                out.append(d + " DIMES ");
            }
            if (n > 0) {
                out.append(n + " NICKELS ");
            }
        }
        out.append("}");
        return out.toString();
    }

    /**
     * Simulates the discrete event model without needing real-time
     *
     * @param inp The input trajectory
     */
    public void simulate(List<String> inp,boolean debug) throws CallManagerException {
        TimePair previousTime = new TimePair(0.0);
        TimePair internalTime = previousTime.advanceBy(2.0);
        for (int i = 0; i < inp.size(); i++) {
            double timeElapsed;
            String[] splits = inp.get(i).split(",");
            TimePair timePair = new TimePair(Double.parseDouble(splits[0]));
            timeElapsed = timePair.getReal() - previousTime.getReal();
            internalTime = previousTime.advanceBy(2.0);
            previousTime = timePair;
            char inputChar = splits[1].charAt(0);
            // Run lambda/delta
            if (Double.compare(timeElapsed, timeAdvance()) >= 0) {
                // There should be an internal/confluent delta before this input
                if (Double.compare(timeElapsed, timeAdvance()) == 0) {
                    // Input on same time as timeAdvance, confluent case
                    System.out.println(internalTime + (debug ?("{INP:" + splits[1] + "} "):"")+ lambda());
                    deltaConf(inputChar);
                } else {
                    // Input in-between, internal case
                    System.out.println(internalTime + " " + lambda());
                    deltaInt();
                    // This must also be run for the input after the internal case
                    if(debug){
                        System.out.println(timePair + "{INP:" + inputChar + "} { Delta External }");
                    }
                    deltaExt(inputChar);
                }
            } else {
                // No waiting, external case
                if(debug){
                    System.out.println(timePair + "{INP:" + inputChar + "} { Delta External }");
                }
                deltaExt(inputChar);
            }
        }
        if (Double.compare(2.0, timeAdvance()) == 0) {
            internalTime = previousTime.advanceBy(2.0);
            // Another lambda and internal delta must be executed after input trajectory
            System.out.println(internalTime + lambda());
            deltaInt();
        }
    }

    /**
     * Internal delta function, runs after 2 seconds of inactivity
     */
    private void deltaInt() throws CallManagerException {
        int[] change = this.getChange(this.value, this.quarter, this.dime, this.nickel);
        this.quarter -= change[0];
        this.dime -= change[1];
        this.nickel -= change[2];
        this.value = 0;
    }

    /**
     * @param inp Number of quarters in input
     */
    private void deltaExt(char inp) {
        if (inp == 'q') {
            this.quarter++;
            this.value += 25;
        } else if (inp == 'd') {
            this.dime++;
            this.value += 10;
        } else if (inp == 'n') {
            this.nickel++;
            this.value += 5;
        }
    }

    private void deltaConf(char inp) throws CallManagerException{
        int[] change = this.getChange(this.value, this.quarter, this.dime, this.nickel);
        this.value = 0;
        if(inp == 'q'){
            this.quarter++;
            this.value = 25;
        }else if(inp == 'd'){
            this.dime++;
            this.value = 10;
        }else if(inp=='n'){
            this.nickel++;
            this.value = 5;
        }
        this.quarter -= change[0];
        this.dime -= change[1];
        this.nickel -= change[2];
    }

    private double timeAdvance() {
        if (this.value > 0) {
            return 2.0;
        } else {
            return INFINITY;
        }
    }

    /**
     * Method that computes change based on value and change combinations
     *
     * @param val     Total value to return
     * @param quarter number of quarters
     * @param dime    number of dimes
     * @param nickel  number of nickels
     * @return Returns an int array of size 3. Index 0 represents quarters, 1 dimes, and 2 nickels
     * @throws CallManagerException
     */
    private int[] getChange(int val, int quarter, int dime, int nickel) throws CallManagerException {
        int[] out = new int[3];
        int q = 0;
        int n = 0;
        int d = 0;
        while (q * 25 <= val - 25) {
            q++;
        }
        val -= (q * 25);
        while (d * 10 <= val - 10) {
            d++;
        }
        val -= (d * 10);
        while (n * 5 <= val - 5) {
            n++;
        }
        val -= (n * 5);
        if (val != 0 || (q > quarter) || (d > dime) || (n > nickel)) {
            throw new CallManagerException("Insufficient coinage in vending machine to dispense change");
        }
        out[0] = q;
        out[1] = d;
        out[2] = n;
        return out;
    }


}
