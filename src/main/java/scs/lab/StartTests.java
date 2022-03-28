package scs.lab;

import com.sun.scenario.effect.Merge;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

public class StartTests implements Runnable {
    // for prime number finder
    static final int MAX = 30000000;
    static final int ARRINITSIZE = 30000000;
    static ArrayList<Integer> primes = new ArrayList<Integer>(ARRINITSIZE);
    int noOfThreadsPrimeFinder;

    //for pi calculation
    int noOfThreadsPiCalc;

    //for merge sort
    int noOfThreadsMergeSort;

    String timeTaken;
    ViewFXMLController viewFXMLController;

    public StartTests(int noOfThreadsPrimeFinder, int noOfThreadsPiCalc, int noOfThreadsMergeSort, ViewFXMLController viewFXMLController) {
        this.noOfThreadsPrimeFinder = noOfThreadsPrimeFinder;
        this.noOfThreadsPiCalc = noOfThreadsPiCalc;
        this.noOfThreadsMergeSort = noOfThreadsMergeSort;
        this.viewFXMLController = viewFXMLController;
    }

    @Override
    public void run() {
        startingPrimeNumberFinder();
        Platform.runLater(this::updateGUIPrimeFinder);

        startingPiCalculation();
        Platform.runLater(this::updateGUIPiCalc);

        startingMergeSortCalculation();
        Platform.runLater(this::updateGUIMergeSort);

    }

    private void startingPrimeNumberFinder() {
        System.out.println("Starting prime number finder");
        long beforeTime = currentTimeMillis();
        Thread[] threadsPrimeFinder = new Thread[noOfThreadsPrimeFinder];
        PrimeNumberFinder.m = new PrimeNumberFinder.Monitor();
        for (int i=0; i<noOfThreadsPrimeFinder; i++) {
            threadsPrimeFinder[i] = new Thread(new PrimeNumberFinder(i, noOfThreadsPrimeFinder) );
            threadsPrimeFinder[i].start();
        }

        // wait for threads to finish
        try {
            for (int i=0; i<noOfThreadsPrimeFinder; i++)
                threadsPrimeFinder[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long afterTime = currentTimeMillis();

        timeTaken = (afterTime - beforeTime)/1000 + "." + (afterTime - beforeTime)%1000 + " s";
        System.out.println("Prime number finder ended in : " + timeTaken);
    }

    private void startingPiCalculation() {
        System.out.println("Calculating pi");
        long beforeTime = currentTimeMillis();
        PiCalculation[] threadsPiCalc = new PiCalculation[noOfThreadsPiCalc];
        for (int i = 0; i < noOfThreadsPiCalc; i++) {
            threadsPiCalc[i] = new PiCalculation(noOfThreadsPiCalc, i);
            threadsPiCalc[i].start();
        }
        for (int i = 0; i < noOfThreadsPiCalc; i++) {
            try {
                threadsPiCalc[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double pi = 0;
        for (int i = 0; i < noOfThreadsPiCalc; i++) {
            pi += threadsPiCalc[i].getSum();
        }
        System.out.print("PI/4 = " + pi * 4);
        long afterTime = currentTimeMillis();

        timeTaken = (afterTime - beforeTime)/1000 + "." + (afterTime - beforeTime)%1000 + " s";
        System.out.println("Calculating pi ended in : " + timeTaken);
    }

    private void startingMergeSortCalculation() {
        System.out.println("Starting merge sort multithreaded");
        long beforeTime = currentTimeMillis();
        MergeSort.threadedSort(noOfThreadsMergeSort);
        long afterTime = currentTimeMillis();

        timeTaken = (afterTime - beforeTime)/1000 + "." + (afterTime - beforeTime)%1000 + " s";
        System.out.println("Merge sort ended in : " + timeTaken);
    }

    public synchronized static void addPrime(int n) {
        primes.add(n);
    }

    private void updateGUIPrimeFinder() {
        this.viewFXMLController.updateTimeTakenPrimeFinderLabel(timeTaken);
    }

    private void updateGUIPiCalc() {
        this.viewFXMLController.updateTimeTakenPiCalcLabel(timeTaken);
    }

    private void updateGUIMergeSort() {
        this.viewFXMLController.updateTimeTakenMergeSort(timeTaken);
    }
}
