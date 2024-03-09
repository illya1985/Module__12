package org.example.t2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Task_2 {
    private static volatile int q;
    private static final BlockingQueue<String> resultQueue = new LinkedBlockingQueue<>();
    private static int n = 15;
    private static Object lock = new Object();

    static boolean isFinished;

    public static void main(String[] args) {
        Thread threadD = new Thread(Task_2::number);
//        threadD.setDaemon(true);
        threadD.start();
        for (int i = 1; i <= n; i++) {
                q = i;
            System.out.println(q);
            new Thread(Task_2::fizz).start();
            new Thread(Task_2::buzz).start();
            new Thread(Task_2::fizzbuzz).start();
        }
//        System.out.println(resultQueue.size());
        Task_2.isFinished = true;
    }



    public static void fizz() {

        try {
            if (q % 3 == 0 && q % 5 != 0) resultQueue.put("fizz");
            if (q % 3 == 0 && q % 5 != 0) resultQueue.poll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void buzz() {
        try {
            if (q % 5 == 0 && q % 3 != 0) resultQueue.put("buzz");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fizzbuzz() {
        try {
            if (q % 3 == 0 && q % 5 == 0) resultQueue.put("fizzbuzz");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void number() {
        synchronized (lock) {
            try {
                while(!isFinished){
                    System.out.println(resultQueue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("exit");
    }
}

