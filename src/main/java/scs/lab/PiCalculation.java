package scs.lab;

public class PiCalculation extends Thread {

    private final int threadNumber;
    private final int threadID;
    private double sum  = 0;

    public PiCalculation(int threadCount, int threadID) {
        this.threadNumber = threadCount;
        this.threadID = threadID;
    }


    @Override
    public void run() {
        int n = 300000000;
        for (long i = 0; i <= n; i++) {
            if (i % threadNumber == threadID) {
                sum += Math.pow(-1, i) / (2 * i + 1);
            }
        }
    }

    public double getSum() {
        return sum;
    }
}
