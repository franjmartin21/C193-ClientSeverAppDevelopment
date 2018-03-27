package c10;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.Socket;

public class CreatingSecureClientSockets {
    /**
     * If you don't care very much about the underlying details, using an encrypted SSL socket to talk to an existing secure
     * server is truly straightforward. Rather than constructing a java.net.Socket object with a constructor, you get one
     * from a javax.net.ssl.SSLSocketFactory using its createSocket() method. SSLSocketFactory is an abstract class that
     * follows the abstract factory design pattern. You get an instance of it by invoking the static SSLSocketFactory.getDefault() method:
     */
    public static void main(String[] args) {
        SocketFactory factory = SSLSocketFactory.getDefault();
        /**
         *
         * This either returns an instance of SSLSocketFactory or throws an InstantiationException if no concrete subclass
         * can be found. Once you have a reference to the factory, use one of these five overloaded createSocket() methods
         * to build an SSLSocket:
         *
         * public abstract Socket createSocket(String host, int port) throws IOException, UnknownHostException
         * public abstract Socket createSocket(InetAddress host, int port) throws IOException
         * public abstract Socket createSocket(String host, int port, InetAddress interface, int localPort) throws IOException, UnknownHostException
         * public abstract Socket createSocket(InetAddress host, int port, InetAddress interface, int localPort) throws IOException, UnknownHostException
         * public abstract Socket createSocket(Socket proxy, String host, int port, boolean autoClose) throws IOException
         */
        try {
            Socket socket = factory.createSocket("login.ibiblio.org", 7000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * The first two methods create and return a socket that's connected to the specified host and port or throw an
         * IOException if they can't connect. The third and fourth methods connect and return a socket that's connected
         * to the specified host and port from the specified local network interface and port. The last createSocket()
         * method, however, is a little different. It begins with an existing Socket object that's connected to a proxy
         * server. It returns a Socket that tunnels through this proxy server to the specified host and port. The autoClose
         * argument determines whether the underlying proxy socket should be closed when this socket is closed. If autoClose
         * is true, the underlying socket will be closed; if false, it won't be.
         *
         * The Socket that all these methods return will really be a javax.net.ssl.SSLSocket, a subclass of java.net.Socket.
         * However, you don't need to know that. Once the secure socket has been created, you use it just like any other socket,
         * through its getInputStream(), getOutputStream(), and other methods. For example, suppose a server that accepts orders
         * is listening on port 7000 of login.ibiblio.org. Each order is sent as an ASCII string using a single TCP connection.
         * The server accepts the order and closes the connection. (I'm leaving out a lot of details that would be necessary
         * in a real-world system, such as the server sending a response code telling the client whether the order was accepted.)
         * The orders that clients send look like this:

         Name: John Smith
         Product-ID: 67X-89
         Address: 1280 Deniston Blvd, NY NY 10003
         Card number: 4000-1234-5678-9017
         Expires: 08/05

         * There's enough information in this message to let someone snooping packets use John Smith's credit card number
         * for nefarious purposes. Consequently, before sending this order, you should encrypt it. The simplest way to do
         * that without burdening either the server or the client with a lot of complicated, error-prone encryption code
         * is to use a secure socket. The following code sends the order over a secure socket:

         SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
         Socket socket = factory.createSocket("login.ibiblio.org", 7000);
         Writer out = new OutputStreamWriter(socket.getOutputStream(), "US-ASCII");
         out.write("Name: John Smith\r\n");
         out.write("Product-ID: 67X-89\r\n");
         out.write("Address: 1280 Deniston Blvd, NY NY 10003\r\n");
         out.write("Card number: 4000-1234-5678-9017\r\n");
         out.write("Expires: 08/05\r\n");
         out.flush();

         * Only the first three statements in the try block are noticeably different from what you'd do with an insecure
         * socket. The rest of the code just uses the normal methods of the Socket, OutputStream, and Writer classes.
         *
         * Reading input is no harder. Example 10-1 is a simple program that connects to a secure HTTP server, sends a
         * simple GET request, and prints out the response.
         */

        /**
         * See HTTPSClient to show how to use it
         */



    }

}
