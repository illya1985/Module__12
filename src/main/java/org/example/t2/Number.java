package org.example.t2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Number implements Runnable {
    Data data;
    boolean isFinished;

    final BlockingQueue<String> resultQueue;

    public Number(Data data, BlockingQueue<String> resultQueue) {
        this.data=data;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {
//        synchronized (lock) {
            try {
                while(data.isRunning()){
                    System.out.println(resultQueue.take());
                    data.next();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
        System.out.println("exit");
    }
}
