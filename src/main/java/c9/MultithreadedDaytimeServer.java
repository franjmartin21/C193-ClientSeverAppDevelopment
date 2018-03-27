package c9;
import java.net.*;
import java.io.*;
import java.util.Date;

/**
 * Multithreaded servers
 *
 * If the sending of data can take a significant amount of time even when client and server are behaving, you really
 * don't want each connection to wait for the next.
 *
 * The solution here is to give each connection its own thread, separate from the thread that accepts incoming connections
 * into the queue. For instance, Example 9-3 is a daytime server that spawns a new thread to handle each incoming connection.
 * This prevents one slow client from blocking all the other clients. This is a thread per connection design.
 */
public class MultithreadedDaytimeServer
{
    public final static int PORT = 1027;
    public static void main(String[] args)
    {
        try (ServerSocket server = new ServerSocket(PORT))
        {
            while (true)
            {
                try
                {
                    Socket connection = server.accept();
                    Thread task = new DaytimeThread(connection);
                    task.start();
                }
                catch (IOException ex) {}
            }
        }
        catch (IOException ex)
        {
            System.err.println("Couldn't start server");
        }
    }
    private static class DaytimeThread extends Thread
    {
        private Socket connection;
        DaytimeThread(Socket connection)
        {
            this.connection = connection;
        }

        /**
         * uses try-with-resources to autoclose the server socket. However, it deliberately does not use try-with-resources
         * for the client sockets accepted by the server socket. This is because the client socket escapes from the try
         * block into a separate thread. If you used try-with-resources, the main thread would close the socket as soon
         * as it got to the end of the while loop, likely before the spawned thread had finished using it.
         */
        @Override
        public void run()
        {
            try
            {
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();
                out.write(now.toString() +"\r\n");
                out.flush();
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
            finally
            {
                try
                {
                    connection.close();
                }
                catch (IOException e)
                {
                    // ignore;
                }
            }
        }
        /**
         * There's actually a denial-of-service attack on this server though. Because this example spawns a new thread for
         * each connection, numerous roughly simultaneous incoming connections can cause it to spawn an indefinite number
         * of threads. Eventually, the Java virtual machine will run out of memory and crash. A better approach is to use
         * a fixed thread pool as described in Lesson 3 to limit the potential resource usage. Fifty threads should be plenty.
         * PooledDaytimeServer example shouldn't crash no matter what load it's under. It may start refusing connections,
         * but it won't crash.
         */
    }
}