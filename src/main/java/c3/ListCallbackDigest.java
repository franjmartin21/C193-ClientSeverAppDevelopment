package c3;

import javax.xml.bind.DatatypeConverter;

public class ListCallbackDigest implements Runnable {

    private String filename;

    public ListCallbackDigest(String filename)
    {
        this.filename = filename;
    }

    /**
     * Blocking
     *
     * Blocking occurs any time a thread has to stop and wait for a resource it doesn't have. The most common way a thread in a
     * network program will voluntarily give up control of the CPU is by blocking on I/O. Because CPUs are much faster than
     * networks and disks, a network program will often block while waiting for data to arrive from the network or be sent
     * out to the network. Even though it may block for only a few milliseconds, this is enough time for other threads to do
     * significant work.
     */
    @Override
    public void run() {
        /**
         * Yielding
         *
         * The second way for a thread to give up control is to explicitly yield. A thread does this by invoking the static T
         * hread.yield() method. This signals to the virtual machine that it can run another thread if one is ready to run.
         * Some virtual machines, particularly on real-time operating systems, may ignore this hint.
         */
        while (true)
        {
            // Do the thread's work...
            Thread.yield();
        }

        /**
         * Sleeping
         *
         * Sleeping is a more powerful form of yielding. Whereas yielding indicates only that a thread is willing to pause and
         * let other equal-priority threads have a turn, a thread that goes to sleep will pause whether any other thread is
         * ready to run or not. This gives an opportunity to run not only to other threads of the same priority, but also to threads
         * of lower priorities. However, a thread that goes to sleep does hold onto all the locks it's grabbed. Consequently,
         * other threads that need the same locks will be blocked even if the CPU is available. Therefore, try to avoid sleeping
         * threads inside a synchronized method or block.
        while (true)
        {
            if (!getPage("http://www.ibiblio.org/"))
            {
                mailError("webmaster@ibiblio.org");
            }
            try
            {
                Thread.sleep(300000); // 300,000 milliseconds == 5 minutes
            }
            catch (InterruptedException ex)
            {
                break;
            }
         }
         */
    }

    public void addDigestListener(ThreadScheduling threadScheduling) {
    }

    public static void main(String[] args) {
        /**
         * Joining Threads
         *
         * It's not uncommon for one thread to need the result of another thread. For example, a web browser loading an HTML
         * page in one thread might spawn a separate thread to retrieve every image embedded in the page. If the IMG elements
         * don't have HEIGHT and WIDTH attributes, the main thread might have to wait for all the images to load before it
         * can finish by displaying the page. Java provides three join() methods to allow one thread to wait for another thread
         * to finish before continuing.
         *
         * public final void join() throws InterruptedException
         * public final void join(long milliseconds) throws InterruptedException
         * public final void join(long milliseconds, int nanoseconds) throws InterruptedException
         *
         * The first variant waits indefinitely for the joined thread to finish. The second two variants wait for the specified
         * amount of time, after which they continue even if the joined thread has not finished.
        }
        double[] array = new double[10000];                         // 1
        for (int i = 0; i < array.length; i++) {                    // 2
            array[i] = Math.random();                                 // 3
        }                                                           // 4
        SortThread t = new SortThread(array);                       // 5
        t.start();                                                  // 6
        try {                                                       // 7
            t.join();                                                 // 8
            System.out.println("Minimum: " + array[0]);               // 9
            System.out.println("Median: " + array[array.length/2]);   // 10
            System.out.println("Maximum: " + array[array.length-1]);  // 11
        } catch (InterruptedException ex) {                         // 12
        }
         */
        /**
         * A thread that's joined to another thread can be interrupted just like a sleeping thread if some other thread invokes
         * its interrupt() method. The thread experiences this invocation as an InterruptedException. From that point forward,
         * it executes as normal, starting from the catch block that caught the exception. In the preceding example, if the
         * thread is interrupted, it skips over the calculation of the minimum, median, and maximum because they won't be
         * available if the sorting thread was interrupted before it could finish.
         */
        ReturnDigest[] digestThreads = new ReturnDigest[args.length];
        for (int i = 0; i < args.length; i++)
        {
            // Calculate the digest
            digestThreads[i] = new ReturnDigest(args[i]);
            digestThreads[i].start();
        }
        for (int i = 0; i < args.length; i++)
        {
            try
            {
                digestThreads[i].join();
                // Now print the result
                StringBuffer result = new StringBuffer(args[i]);
                result.append(": ");
                byte[] digest = digestThreads[i].getDigest();
                result.append(DatatypeConverter.printHexBinary(digest));
                System.out.println(result);
            }
            catch (InterruptedException ex)
            {
                System.err.println("Thread Interrupted before completion");
            }
        }
        /**
         * Waiting
         *
         * Waiting on an object is one of the lesser-known ways a thread can pause. That's because it doesn't involve any methods
         * in the Thread class. Instead, to wait on a particular object, the thread that wants to pause must first obtain the
         * lock on the object using synchronized and then invoke one of the object's three overloaded wait() methods:
         *
         * public final void wait() throws InterruptedException
         * public final void wait(long milliseconds) throws InterruptedException
         * public final void wait(long milliseconds, int nanoseconds) throws InterruptedException
         */

        /**
         * Notification
         *
         * Notification occurs when some other thread invokes the notify() or notifyAll() method on the object on which the
         * thread is waiting. Both of these methods are in the java.lang.Object class:
         *
         * public final void notify()
         * public final void notifyAll()
         *
         * The notify() method selects one thread more or less at random from the list of threads waiting on the object
         * and wakes it up. The notifyAll() method wakes up every thread waiting on the given object.
         *
         * For example, suppose one thread is reading a JAR archive from a network connection. The first entry in the archive
         * is the manifest file. Another thread might be interested in the contents of the manifest file even before the rest
         * of the archive is available. The interested thread could create a custom ManifestFile object, pass a reference to
         * this object to the thread that would read the JAR archive, and wait on it. The thread reading the archive would first
         * fill the ManifestFile with entries from the stream, then notify the ManifestFile, then continue reading the rest of
         * the JAR archive. When the reader thread notified the ManifestFile, the original thread would wake up and do whatever
         * it planned to do with the now fully prepared ManifestFile object.
         *
         * The first thread works something like this:
         * ManifestFile m = new ManifestFile();
             JarThread    t = new JarThread(m, in);
             synchronized (m)
             {
                t.start();
                try
                {
                    m.wait();
                    // work with the manifest file...
                }
                catch (InterruptedException ex)
                {
                // handle exception...
                }
             }
         *
         * The JarThread class works like this:

             ManifestFile theManifest;
             InputStream in;
             public JarThread(Manifest m, InputStream in)
             {
                 theManifest = m;
                 this.in= in;
             }
             @Override
             public void run()
             {
                synchronized (theManifest)
             {
                // read the manifest from the stream in...
                theManifest.notify();
             }
         */

        /**
         * Finish
         *
         * final way a thread can give up control of the CPU in an orderly fashion is by finishing. When the run() method returns,
         * the thread dies and other threads can take over. In network applications, this tends to occur with threads that wrap a
         * single blocking operation, such as downloading a file from a server, so that the rest of the application won't be blocked.
         */
    }
}
