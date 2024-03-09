package org.example.t2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Task_2_1 {

    private static volatile int q;

    private static int n = 15;
    private static Object lock = new Object();


    public static void main(String[] args) {
        final BlockingQueue<String> resultQueue = new LinkedBlockingQueue<>();
        Data data = new Data(resultQueue);
        Thread threadBuzz = new Thread(new Buzz(data, resultQueue));
        Thread threadFizz = new Thread(new Fizz(data, resultQueue));
        Thread threadNumb = new Thread(new Number(data, resultQueue));
        threadBuzz.start();
        threadFizz.start();
        threadNumb.start();
        for (int i = 1; i <= n; i++) {
            data.send(i);
        }
        data.stop();
        System.out.println("main exit");
    }

}
