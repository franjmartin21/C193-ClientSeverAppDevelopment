package c9;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.logging.*;

public class JHTTP
{
    private static final Logger logger = Logger.getLogger(JHTTP.class.getCanonicalName());
    private static final int NUM_THREADS = 50;
    private static final String INDEX_FILE = "index.html";
    private final File rootDirectory;
    private final int port;
    public JHTTP(File rootDirectory, int port) throws IOException
    {
        if (!rootDirectory.isDirectory())
        {
            throw new IOException(rootDirectory + " does not exist as a directory");
        }
        this.rootDirectory = rootDirectory;
        this.port = port;
    }
    public void start() throws IOException
    {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try (ServerSocket server = new ServerSocket(port))
        {
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Document Root: " + rootDirectory);
            while (true)
            {
                try
                {
                    Socket request = server.accept();
                    Runnable r = new RequestProcessor(rootDirectory, INDEX_FILE, request);
                    pool.submit(r);
                }
                catch (IOException ex)
                {
                    logger.log(Level.WARNING, "Error accepting connection", ex);
                }
            }
        }
    }
    public static void main(String[] args)
    {
        // get the Document root
        File docroot;
        try
        {
            docroot = new File(args[0]);
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Usage: java JHTTP docroot port");
            return;
        }
        // set the port to listen on
        int port;
        try
        {
            port = Integer.parseInt(args[1]);
            if (port < 0 || port > 65535) port = 80;
        }
        catch (RuntimeException ex)
        {
            port = 80;
        }
        try
        {
            JHTTP webserver = new JHTTP(docroot, port);
            webserver.start();
        }
        catch (IOException ex)
        {
            logger.log(Level.SEVERE, "Server could not start", ex);
        }
    }
    /**
     * The main() method of the JHTTP class sets the document root directory from args[0]. The port is read from args[1]
     * or 80 is used for a default. Then a new JHTTP object is constructed and started. JHTTP creates a thread pool to
     * handle requests and repeatedly accepts incoming connections. You submit one RequestProcessor thread per incoming
     * connection into the pool.
     *
     * Each connection is handled by the run() method of the RequestProcessor class shown in Example 9-13. It gets input
     * and output streams from the socket and chains them to a reader and a writer. The reader reads the first line of
     * the client request to determine the version of HTTP that the client supports—you want to send a MIME header only
     * if this is HTTP/1.0 or later—and the requested file. Assuming the method is GET, the file that is requested is
     * converted to a filename on the local filesystem. If the file requested is a directory (i.e., its name ends with
     * a slash), you add the name of an index file. You use the canonical path to make sure that the requested file doesn't
     * come from outside the document root directory. Otherwise, a sneaky client could walk all over the local filesystem
     * by including .. in URLs to walk up the directory hierarchy. This is all you'll need from the client, although a
     * more advanced web server, especially one that logged hits, would read the rest of the MIME header the client sends.
     */

    /**
     * This server is functional but still rather austere. Here are a few features that could be added:
     * A server administration interface
     *
     * Support for the Java Servlet API
     * Support for other request methods, such as POST, HEAD, and PUT
     * Support for multiple document roots so individual users can have their own sitesFinally, spend a little time thinking about
     * ways to optimize this server. If you really want to use JHTTP to run a high-traffic site, there are a couple of things
     * that can speed this server up. The first thing to do is implement smart caching. Keep track of the requests you've
     * received and store the data from the most frequently requested files in a Map so that they're kept in memory. Use a
     * low-priority thread to update this cache. You can also try using nonblocking I/O and channels instead of threads and
     * streams. We'll explore this possibility in Lesson 11.
     */
}