package org.example;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

    public class Task_1 {
        public static void main(String[] args) {
           Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                int secondsElapsed = 0;

                @Override
                public void run() {
                    System.out.println("Час, що минув: " + secondsElapsed++ + " секунд");
                }
            }, 0, 1000);

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(() -> System.out.println("Минуло 5 секунд"), 0, 5, TimeUnit.SECONDS);
        }
    }

