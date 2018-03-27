package c3;

import java.io.File;
import java.util.concurrent.*;

/**
 * Example of pools and Executors
 *
 * Suppose you want to gzip every file in the current directory using a java.util.zip.GZIPOutputStream. This is a filter stream
 * that compresses all the data it writes.
 * On the one hand, this is an I/O-heavy operation because all the files have to be read and written. On the other hand, data
 * compression is a very CPU-intensive operation, so you don't want too many threads running at once. This is a good opportunity
 * to use a thread pool. Each client thread will compress files while the main program will determine which files to compress.
 * In this example, the main program is likely to significantly outpace the compressing threads because all it has to do is list
 * the files in a directory. Therefore, it's not out of the question to fill the pool first, then start the threads that compress
 * the files in the pool. However, to make this example as general as possible, you'll allow the main program to run in parallel
 * with the zipping threads.
 */
public class GZipAllFiles
{
    public final static int THREAD_COUNT = 4;
    public static void main(String[] args)
    {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        for (String filename : args)
        {
            File f = new File(filename);
            if (f.exists())
            {
                if (f.isDirectory())
                {
                    File[] files = f.listFiles();
                    for (int i = 0; i < files.length; i++)
                    {
                        if (!files[i].isDirectory())
                        { // don't recurse directories
                            Runnable task = new GZipRunnable(files[i]);
                            pool.submit(task);
                        }
                    }
                }
                else
                {
                    Runnable task = new GZipRunnable(f);
                    pool.submit(task);
                }
            }
        }
        /**
         * Once you have added all the files to the pool, you call pool.shutdown(). Chances are this happens while there's still
         * work to be done. This method does not abort pending jobs. It simply notifies the pool that no further tasks will be
         * added to its internal queue and that it should shut down once it has finished all pending work.
         */
        pool.shutdown();
    }
}