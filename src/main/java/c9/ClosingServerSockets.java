package c9;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

public class ClosingServerSockets {
    /**
     * If you're finished with a server socket, you should close it, especially if the program is going to continue to run
     * for some time. This frees up the port for other programs that may wish to use it. Closing a ServerSocket should
     * not be confused with closing a Socket. Closing a ServerSocket frees a port on the local host, allowing another server
     * to bind to the port; it also breaks all currently open sockets that the ServerSocket has accepted.
     */

    /**
     * Server sockets are closed automatically when a program dies, so it's not absolutely necessary to close them in
     * programs that terminate shortly after the ServerSocket is no longer needed. Nonetheless, it doesn't hurt. Programmers
     * often follow the same close-if-not-null pattern in a try-finally block that you're already familiar with from
     * streams and client-side sockets:
     */

    public static void main(String[] args) {
        ServerSocket server = null;
        try
        {
            server = new ServerSocket(1029);
            // ... work with the server socket
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally
        {
            if (server != null)
            {
                try
                {
                    server.close();
                }
                catch (IOException ex)
                {
                    // ignore
                }
            }
        }

        /**
         * You can improve this slightly by using the noargs ServerSocket() constructor, which does not throw any exceptions
         * and does not bind to a port. Instead, you call the bind() method to bind to a socket address after the ServerSocket()
         * object has been constructed:
        ServerSocket server2 = new ServerSocket();
        try
        {
            SocketAddress address = new InetSocketAddress(1030);
            server2.bind(address);
            // ... work with the server socket
        }
        finally
        {
            try
            {
                server.close();
            }
            catch (IOException ex)
            {
                // ignore
            }
        }
         */


        /**
         * In Java 7, ServerSocket implements AutoCloseable so you can take advantage of try-with-resources instead:
         */
        try (ServerSocket server3 = new ServerSocket(1031))
        {

        } catch (IOException e){}

        /**
         * After a server socket has been closed, it cannot be reconnected, even to the same port.
         *
         * The isClosed() method returns true if the ServerSocket has been closed, false if it hasn't:
         *
         * public boolean isClosed()
         *
         * ServerSocket objects that were created with the noargs ServerSocket() constructor and not yet bound to a port are
         * not considered to be closed. Invoking isClosed() on these objects returns false. The isBound() method tells you
         * whether the ServerSocket has been bound to a port:
         *
         * public boolean isBound()
         *
         * As with the isBound() method of the Socket class discussed in the Lesson 8, the name is a little misleading.
         * isBound() returns true if the ServerSocket has ever been bound to a port, even if it's currently closed. If
         * you need to test whether a ServerSocket is open, you must check both that isBound() returns true and that
         * isClosed() returns false. For example:
         *
         public static boolean isOpen(ServerSocket ss)
         {
            return ss.isBound() && !ss.isClosed();
         }
         */
    }
}
