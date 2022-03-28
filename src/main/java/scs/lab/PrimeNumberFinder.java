package scs.lab;

public class PrimeNumberFinder implements Runnable{
    public static Monitor m;
    final int ID;
    private final int threadNumber;

    public PrimeNumberFinder(int i, int threadNumber) {
        ID = i;
        this.threadNumber = threadNumber;
    }

    public void run() {
        for(int i=0; i < StartTests.MAX; i++) {
            if(i % threadNumber == ID)
                if(isPrime(i))
                    m.addPrime(i);
        }
    }

    static class Monitor {
        public synchronized void addPrime(int n) {
             StartTests.addPrime(n);
        }
    }

    public static boolean isPrime(int n) {
        if (n == 2 || n == 3 || n == 5) return true;
        if (n <= 1 || (n&1) == 0) return false;

        for (int i = 3; i*i <= n; i += 2)
            if (n % i == 0) return false;

        return true;
    }

}
