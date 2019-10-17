package com.nesposi3;

public class VendingMachine {
    private int quarter, dime, nickel,value;
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

    private void lambda() throws CallManagerException{
        int val = this.value;
        while (val >= 100) {
            //vend coffee
            System.out.println("Coffee");
            val -= 100;
        }
        if (this.change) {
            int[] changeBack = this.getChange(val,this.quarter,this.dime,this.nickel);
            int q = changeBack[0];
            int d = changeBack[1];
            int n = changeBack[2];
            String change = "Here is your change:";
            if (q > 0) {
                change += " " + q + " quarters";
            }
            if (d > 0) {
                change += " " + d + " dimes";
            }
            if (n > 0) {
                change += " " + n + " nickels";
            }
            System.out.println(change);
        }
    }

    /**
     * Simulates the discrete event model without needing real-time
     * @param inp The input trajectory
     */
    public void simulate(String[] inp) throws CallManagerException{
        TimePair previousTime = new TimePair(0.0);
        for (int i = 0; i < inp.length ; i++) {
            int q = 0;
            int d = 0;
            int n = 0;
            double timeElapsed;
            String[] splits = inp[i].split(",");
            TimePair timePair = new TimePair(Double.parseDouble(splits[0]));
            timeElapsed = timePair.getReal() - previousTime.getReal();
            char inputChar = splits[1].charAt(0);
            // Run lambda/delta
            lambda();
            if(Double.compare(timeElapsed,timeAdvance())>0){
                // There should be an internal/confluent delta before this input
                if(Double.compare(timeElapsed,timeAdvance())==0){
                    // Input on same time as timeAdvance, confluent case
                    deltaConf(inputChar);
                }else{
                    // Input in-between, internal case
                    deltaInt();
                }
            }else{
                // No waiting, external case
                deltaExt(inputChar);
            }
            previousTime = timePair;
        }
        if(Double.compare(2.0,timeAdvance())==0){
            // Another lambda and internal delta must be executed after input trajectory
            lambda();
            deltaInt();
        }
    }

    /**
     * Internal delta function, runs after 2 seconds of inactivity
     */
    private void deltaInt() throws CallManagerException{
        this.change = true;
        int[] change = this.getChange(this.value,this.quarter,this.dime,this.nickel);
        this.quarter -= change[0];
        this.dime -= change[1];
        this.nickel -= change[2];
        this.value = 0;
        this.change = false;
    }

    /**
     *  @param inp Number of quarters in input
     *
     */
    private void deltaExt(char inp){
        while (this.value >= 100) {
            //vend as many coffees as it can
            this.value -= 100;
        }
        if(inp=='q'){
            this.quarter++;
            this.value+=25;
        }else if(inp=='d'){
            this.dime++;
            this.value+=10;
        }else if(inp=='n'){
            this.nickel++;
            this.value+=5;
        }
    }
    private void deltaConf(int q){
        this.change = true;
        this.change = false;

    }
    private double timeAdvance(){
        if(this.value>0){
            return 2.0;
        }else{
            return INFINITY;
        }
    }
    /**
     * Method that computes change based on value and change combinations
     * @param val Total value to return
     * @param quarter number of quarters
     * @param dime number of dimes
     * @param nickel number of nickels
     * @return Returns an int array of size 3. Index 0 represents quarters, 1 dimes, and 2 nickels
     * @throws CallManagerException
     */
    private int[] getChange(int val,int quarter,int dime, int nickel) throws CallManagerException{
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
