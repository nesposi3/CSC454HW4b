package com.nesposi3;

public class TimePair implements Comparable<TimePair>{
    public double getReal() {
        return real;
    }

    public int getDiscrete() {
        return discrete;
    }

    private double real;
    private int discrete;
    public TimePair(double real){
        this.real = real;
        this.discrete = 0;
    }
    public TimePair(double real, int discrete){
        this.discrete = discrete;
        this.real = real;
    }


    @Override
    public String toString() {
        return "{ "+real + " " + discrete + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().isInstance(this)){
            return false;
        }
        TimePair o = (TimePair) obj;
        return (this.discrete == o.discrete)&&(Double.compare(this.real,o.real)==0);
    }

    @Override
    public int compareTo(TimePair o) {
        //Same real component
        if(Double.compare(real,o.real)==0){
            return Integer.compare(this.discrete , o.discrete);
        }else{
            return Double.compare(real,o.real);
        }
    }
    //TODO Implement this
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public TimePair advanceBy(TimePair disp){
        if(Double.compare(disp.real,0)==0){
            return new TimePair(real,discrete + disp.discrete);
        }else{
            return new TimePair(real+disp.real,0);
        }
    }
}
