package org.example.t2;

import java.util.concurrent.BlockingQueue;

public class Fizz implements Runnable{

    Data data;
    BlockingQueue<String> resultQueue;
    public Fizz(Data data, BlockingQueue<String> resultQueue) {
        this.data = data;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {
        while (data.isRunning()) {
            int q = data.get();
            try {
                if (q % 3 == 0 && q % 5 != 0) resultQueue.put("fizz");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
