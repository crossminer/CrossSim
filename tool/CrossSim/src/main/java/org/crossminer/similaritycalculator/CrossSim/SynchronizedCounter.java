package org.crossminer.similaritycalculator.CrossSim;

public class SynchronizedCounter {
    private int c = -1;

    public SynchronizedCounter(int value) {
		this.c=value;
	}
    public SynchronizedCounter() {

  	}

	public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
    	this.increment();
        return c;
    }
}