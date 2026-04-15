package ru.academits.alekseev.lesson_23;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerManager {
    private final int producersCount;
    private final int consumersCount;
    private final int capacity;

    private final List<Thread> threads = new ArrayList<>();
    private final Queue<String> queue = new LinkedList<>();
    private final AtomicInteger currentItemId = new AtomicInteger(1);

    public ProducerConsumerManager(int producersCount, int consumersCount, int capacity) {
        this.producersCount = producersCount;
        this.consumersCount = consumersCount;
        this.capacity = capacity;
    }

    public void start() {
        for (int i = 0; i < producersCount; i++) {
            Thread thread = new Thread(() -> {
                Thread currentThread = Thread.currentThread();

                try {
                    while (!currentThread.isInterrupted()) {
                        Thread.sleep(1000);

                        String item = "Item #" + currentItemId.getAndIncrement();

                        synchronized (queue) {
                            while (queue.size() >= capacity) {
                                queue.wait();
                            }

                            queue.offer(item);

                            System.out.println("Producer #" + currentThread.threadId()
                                    + " created item: " + item
                                    + " Queue size: " + queue.size());

                            queue.notifyAll();
                        }
                    }
                } catch (InterruptedException _) {
                }
            });

            threads.add(thread);
        }

        for (int i = 0; i < consumersCount; i++) {
            Thread thread = new Thread(() -> {
                Thread currentThread = Thread.currentThread();

                try {
                    while (!currentThread.isInterrupted()) {
                        Thread.sleep(1000);

                        synchronized (queue) {
                            while (queue.isEmpty()) {
                                queue.wait();
                            }

                            String item = queue.remove();

                            System.out.println("Consumer #" + currentThread.threadId()
                                    + " received item: " + item
                                    + " Queue size: " + queue.size());

                            queue.notifyAll();
                        }
                    }
                } catch (InterruptedException _) {
                }
            });

            threads.add(thread);
        }

        threads.forEach(Thread::start);
    }
}
