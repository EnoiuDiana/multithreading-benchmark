package scs.lab;

import java.lang.System;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class MergeSort{

    private static int size = 2_000_000_0;
    private static Integer[] list = new Integer[size];
    private static Random randomizer = new Random();

    private static class SortThreads extends Thread{
        SortThreads(Integer[] array, int begin, int end){
            super(()->{
                MergeSort.mergeSort(array, begin, end);
            });
            this.start();
        }
    }

    public static void threadedSort(int threads_no){
        for ( int i = 0; i < size; i++ ) {
            list[i] = randomizer.nextInt( 100_000_0 );
        }
        final int length = list.length;
        boolean exact = length%threads_no == 0;
        int maxlim = exact? length/threads_no: length/(threads_no-1);
        maxlim = Math.max(maxlim, threads_no);
        final ArrayList<SortThreads> threads = new ArrayList<>();

        for(int i=0; i < length; i+=maxlim){
            int beg = i;
            int remain = (length)-i;
            int end = remain < maxlim? i+(remain-1): i+(maxlim-1);
            final SortThreads t = new SortThreads(list, beg, end);
            threads.add(t);
        }
        for(Thread t: threads){
            try{
                t.join();
            } catch(InterruptedException ignored){}
        }

        for(int i=0; i < length; i+=maxlim){
            int mid = i == 0? 0 : i-1;
            int remain = (length)-i;
            int end = remain < maxlim? i+(remain-1): i+(maxlim-1);
            merge(list, 0, mid, end);
        }
    }

    // Typical recursive merge sort
    public static void mergeSort(Integer[] array, int begin, int end){
        if (begin<end){
            int mid = (begin+end)/2;
            mergeSort(array, begin, mid);
            mergeSort(array, mid+1, end);
            merge(array, begin, mid, end);
        }
    }

    //Typical 2-way merge
    public static void merge(Integer[] array, int begin, int mid, int end){
        Integer[] temp = new Integer[(end-begin)+1];

        int i = begin, j = mid+1;
        int k = 0;

        while(i<=mid && j<=end){
            if (array[i] <= array[j]){
                temp[k] = array[i];
                i+=1;
            }else{
                temp[k] = array[j];
                j+=1;
            }
            k+=1;
        }

        while(i<=mid){
            temp[k] = array[i];
            i+=1; k+=1;
        }

        while(j<=end){
            temp[k] = array[j];
            j+=1; k+=1;
        }

        for(i=begin, k=0; i<=end; i++,k++){
            array[i] = temp[k];
        }
    }
}
