package org.example;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Task_2 {

    public static void main(String[] args) {
        int n=15;
        Buffer buffer = new Buffer(20);

        Thread a = new Thread(new ProducerA(buffer), "Buzz");
        Thread b = new Thread(new ProducerB(buffer), "Fizz");
        Thread c = new Thread(new ProducerC(buffer), "BuzzFizz");
        Thread d = new Thread(new ConsumerD(buffer), "Number");

        a.start();
        b.start();
        c.start();
        d.start();

        for (int i = 1; i <= n; i++) {
            try {
                buffer.produce(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        buffer.stop();
    }

    static class Buffer {
        private final int[] buffer;
        private final String[] str;
        private final int size;
        private final int threads = 3;
        private int counter = 0;
        private int count = 0;

        private final HashSet<String> set = new HashSet<>();
        private boolean consume = false;
        private boolean test = false;
        private boolean produce  = true;
        private boolean stop  = false;


        public Buffer(int size) {
            this.size = size;
            this.buffer = new int[size];
            this.str = new String[size];
        }

        public synchronized void produce(int item) throws InterruptedException {
            while (!produce ) {
                wait();
            }

            buffer[count] = item;
            count++;
            produce = false;
            test = true;
            set.clear();
            notifyAll();
        }

        public synchronized void buzz() throws InterruptedException {
            while (!test || set.contains(Thread.currentThread().getName())) {
                wait();
            }
            set.add(Thread.currentThread().getName());
            if (set.size() == threads){
                test = false;
                consume = true;
            }
            int item = buffer[count-1];
            if (item % 3 == 0 && item % 5 != 0) str[count-1] = "Buzz";
            notifyAll();
        }

        public synchronized void fizz() throws InterruptedException {
            while (!test || set.contains(Thread.currentThread().getName())) {
                wait();
            }
            set.add(Thread.currentThread().getName());
            if (set.size() == threads){
                test = false;
                consume = true;
            }
            int item = buffer[count-1];
            if (item % 3 != 0 && item % 5 == 0) str[count-1] = "Fizz";
            notifyAll();
        }

        public synchronized void buzzFizz() throws InterruptedException {
            while (!test || set.contains(Thread.currentThread().getName())) {
                wait();
            }
            set.add(Thread.currentThread().getName());
            if (set.size() == threads){
                test = false;
                consume = true;
            }
            int item = buffer[count-1];
            if (item % 3 == 0 && item % 5 == 0) str[count-1] = "BuzzFizz";
            notifyAll();
        }

        public synchronized String consume() throws InterruptedException {
            while (!consume ) {
                wait();
            }
            String strItem = str[count - 1];
            int intItem = buffer[count - 1];
//            count--;
            produce = true;
            consume = false;
            String result = (strItem != null && !strItem.isEmpty()) ? strItem : String.valueOf(intItem);
            System.out.print(result + ", ");
            notifyAll();
            return result;
        }

        public void stop() {
            stop = true;
        }
    }

    static class ProducerA implements Runnable {
        private final Buffer buffer;

        public ProducerA(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (!buffer.stop) {
                try {
                    buffer.buzz();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    static class ProducerB implements Runnable {
        private final Buffer buffer;

        public ProducerB(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (!buffer.stop) {
                try {
                    buffer.fizz();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    static class ProducerC implements Runnable {
        private final Buffer buffer;

        public ProducerC(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (!buffer.stop) {
                try {
                    buffer.buzzFizz();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    static class ConsumerD implements Runnable {
        private final Buffer buffer;

        public ConsumerD(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (!buffer.stop) {
                try {
                    buffer.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
