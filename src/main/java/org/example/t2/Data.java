package org.example.t2;

import java.util.concurrent.BlockingQueue;

public class Data {

    private final BlockingQueue<String> queue;
    private Integer i;
    private boolean writing = true;
    private boolean running = true;

    public Data(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public boolean isRunning() {
        return running;
    }

    public synchronized Integer get(){
        while (writing) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
//        this.writing = true;

        Integer result = i;
//        notifyAll();
//        try {
//            queue.put(result.toString());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return result;
    }

    public void next(){
        if(true){
            this.writing = true;
            notifyAll();
        }
    }

    public synchronized void send(Integer i){
        while (!writing) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
        this.writing = false;

        this.i = i;
        notifyAll();
    }

    public synchronized void stop(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.running = false;
        notifyAll();
    }


}
