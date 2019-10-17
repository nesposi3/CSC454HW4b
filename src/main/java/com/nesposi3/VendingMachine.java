package com.nesposi3;

public class VendingMachine {
    private int quarter, dime, nickel,value;
    private final int INFINITY = -1;
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

    public String lambda(){
        return "";
    }
    public void delta(String inp) {

    }
    private void deltaInt(){

    }
    private void deltaExt(){

    }
    private void deltaConf(){

    }
    private int timeAdvance(){
        if(this.value>0){
            return 2;
        }else{
            return INFINITY;
        }
    }


}
