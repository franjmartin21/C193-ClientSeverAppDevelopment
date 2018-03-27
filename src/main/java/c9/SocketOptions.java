package c9;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketOptions {
    /**
     * Socket options specify how the native sockets on which the ServerSocket class relies send and receive data.
     * For server sockets, Java supports three options:
     * SO_TIMEOUT
     * SO_REUSEADDR
     * SO_RCVBUF
     * It also allows you to set performance preferences for the socket's packets.
     */

    /**
     * SO_TIMEOUT
     *
     * SO_TIMEOUT is the amount of time, in milliseconds, that accept() waits for an incoming connection before throwing
     * a java.io.InterruptedIOException. If SO_TIMEOUT is 0, accept() will never time out. The default is to never time out.
     *
     * Setting SO_TIMEOUT is uncommon. You might need it if you were implementing a complicated and secure protocol that
     * required multiple connections between the client and the server where responses needed to occur within a fixed amount
     * of time. However, most servers are designed to run for indefinite periods of time and therefore just use the default
     * timeout value, 0 (never time out). If you want to change this, the setSoTimeout() method sets the SO_TIMEOUT field
     * for this server socket object:
     *
     * public void setSoTimeout(int timeout) throws SocketException
     * public int  getSoTimeout() throws IOException
     *
     * The countdown starts when accept() is invoked. When the timeout expires, accept() throws a SocketTimeoutException,
     * a subclass of IOException. You need to set this option before calling accept(); you cannot change the timeout value
     * while accept() is waiting for a connection. The timeout argument must be greater than or equal to zero; if it isn't,
     * the method throws an IllegalArgumentException. For example:
     */
    private static final int PORT = 1098;

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT))
        {
            server.setSoTimeout(30000); // block for no more than 30 seconds
            try
            {
                Socket s = server.accept();
                printSoTimeout(server);
            }
            catch (SocketTimeoutException ex)
            {
                System.err.println("No connection within 30 seconds");
            }
        }
        catch (IOException ex)
        {
            System.err.println("Unexpected IOException: " + ex);
        }
    }

    /**
     * The getSoTimeout() method returns this server socket's current SO_TIMEOUT value. For example:
     */
    public static void printSoTimeout(ServerSocket server) throws IOException {
        int timeout = server.getSoTimeout();
        if (timeout > 0) {
            System.out.println(server + " will time out after " + timeout + "milliseconds.");
        } else if (timeout == 0) {
            System.out.println(server + " will never time out.");
        } else {
            System.out.println("Impossible condition occurred in " + server);
            System.out.println("Timeout cannot be less than zero.");
        }


        /**
         * SO_REUSEADDR
         *
         * The SO_REUSEADDR option for server sockets is very similar to the same option for client sockets, discussed in the
         * previous lesson. It determines whether a new socket will be allowed to bind to a previously used port while there
         * might still be data traversing the network addressed to the old socket. As you probably expect, there are two methods
         * to get and set this option:
         *
         * public boolean getReuseAddress() throws SocketException
         * public void setReuseAddress(boolean on) throws SocketException
         *
         * The default value is platform dependent. This code fragment determines the default value by creating a new ServerSocket
         * and then calling getReuseAddress():
         *
         * ServerSocket ss = new ServerSocket(10240);
         * System.out.println("Reusable: " + ss.getReuseAddress());
         *
         * On the Linux and Mac OS X boxes where I tested this code, server sockets were reusable by default.
         */

        /**
         * SO_RCVBUF
         *
         * The SO_RCVBUF option sets the default receive buffer size for client sockets accepted by the server socket. It's
         * read and written by these two methods:
         *
         * public int  getReceiveBufferSize() throws SocketException
         * public void setReceiveBufferSize(int size) throws SocketException
         *
         * Setting SO_RCVBUF on a server socket is like calling setReceiveBufferSize() on each individual socket returned by
         * accept() (except that you can't change the receive buffer size after the socket has been accepted). Recall from
         * the previous lesson that this option suggests a value for the size of the individual IP packets in the stream.
         * Faster connections will want to use larger buffers, although most of the time the default value is fine
         *
         * You can set this option before or after the server socket is bound, unless you want to set a receive buffer size
         * larger than 64K. In that case, you must set the option on an unbound ServerSocket before binding it. For example:
         *
         */
        ServerSocket ss = new ServerSocket();
        int receiveBufferSize = ss.getReceiveBufferSize();
        if (receiveBufferSize < 131072) {
            ss.setReceiveBufferSize(131072);
        }
        ss.bind(new InetSocketAddress(8000));
    }
    /**
     * Class of Service
     *
     * As you learned in the previous lesson, different types of Internet services have different performance needs. For
     * instance, live streaming video of sports needs relatively high bandwidth. On the other hand, a movie might still
     * need high bandwidth but be able to tolerate more delay and latency. Email can be passed over low-bandwidth connections
     * and even held up for several hours without major harm.
     *
     * Four general traffic classes are defined for TCP:
     * Low cost
     * High reliability
     * Maximum throughput
     * Minimum delay
     *
     * These traffic classes can be requested for a given Socket. For instance, you can request the minimum delay available
     * at low cost. These measures are all fuzzy and relative, not guarantees of service. Not all routers and native TCP
     * stacks support these classes.
     *
     * The setPerformancePreferences() method expresses the relative preferences given to connection time, latency, and
     * bandwidth for sockets accepted on this server:
     *
     * public void setPerformancePreferences(int connectionTime, int latency, int bandwidth)
     *
     * For instance, by setting connectionTime to 2, latency to 1, and bandwidth to 3, you indicate that maximum bandwidth
     * is the most important characteristic, minimum latency is the least important, and connection time is in the middle:
     *
     * ss.setPerformancePreferences(2, 1, 3);
     *
     * Exactly how any given VM implements this is implementation dependent. The underlying socket implementation is not
     * required to respect any of these requests. They only provide a hint to the TCP stack about the desired policy.
     * Many implementations including Android ignore these values completely.
     */
}
