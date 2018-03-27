package c3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Futures, Callables, and Executors
 * Java 5 introduced a new approach to multithreaded programming that makes it somewhat easier to handle callbacks by hiding
 * the details. Instead of directly creating a thread, you create an ExecutorService that will create threads for you as needed.
 * You submit Callable jobs to the ExecutorService and for each one you get back a Future. At a later point, you can ask the Future
 * for the result of the job. If the result is ready, you get it immediately. If it's not ready, the polling thread blocks until
 * it is ready. The advantage is that you can spawn off many different threads, then get the answers you need in the order you need them.
 */
public class MultithreadedMaxFinder {

    public static int max(int[] data) throws InterruptedException, ExecutionException
    {
        if (data.length == 1)
        {
            return data[0];
        }

        else if (data.length == 0)
        {
            throw new IllegalArgumentException();
        }
        // split the job into 2 pieces
        FindMaxTask task1 = new FindMaxTask(data, 0, data.length/2);
        FindMaxTask task2 = new FindMaxTask(data, data.length/2, data.length);
        // spawn 2 threads
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = service.submit(task1);
        Future<Integer> future2 = service.submit(task2);
        return Math.max(future1.get(), future2.get());
    }
}
