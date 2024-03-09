package org.example.t2;

import java.util.concurrent.BlockingQueue;

public class Buzz implements Runnable {
    Data data;
    boolean isSleep = true;
    BlockingQueue<Integer> queue;
    BlockingQueue<String> resultQueue;

    public Buzz(Data data, BlockingQueue<String> resultQueue) {
        this.data = data;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {
        while (data.isRunning()) {
            int q = data.get();
            try {
                if (q % 5 == 0 && q % 3 != 0) resultQueue.put("buzz");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
