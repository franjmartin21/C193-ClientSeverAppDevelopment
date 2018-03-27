package c8;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GettingInformationSocket {
    /**
     * Socket objects have several properties that are accessible through getter methods:
     * Remote address
     * Remote port
     * Local address
     * Local port
     *
     * public InetAddress getInetAddress()
     * public int getPort()
     * public InetAddress getLocalAddress()
     * public int getLocalPort()
     *
     * The getInetAddress() and getPort() methods tell you the remote host and port the Socket is connected to; or, if
     * the connection is now closed, which host and port the Socket was connected to when it was connected. The getLocalAddress()
     * and getLocalPort() methods tell you the network interface and port the Socket is connected from.
     */
    public static void main(String[] args)
    {
        for (String host : args)
        {
            try
            {
                Socket theSocket = new Socket(host, 80);
                System.out.println("Connected to " + theSocket.getInetAddress()
                        + " on port "  + theSocket.getPort() + " from port "
                        + theSocket.getLocalPort() + " of "
                        + theSocket.getLocalAddress());
            }
            catch (UnknownHostException ex)
            {
                System.err.println("I can't find " + host);
            }
            catch (SocketException ex)
            {
                System.err.println("Could not connect to " + host);
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
    /**
     * Closed or Connected?
     *
     * The isClosed() method returns true if the socket is closed, false if it isn't. If you're uncertain about a socket's
     * state, you can check it with this method rather than risking an IOException. For example:
     */
    String host = args[0];
    try {
        Socket socket = new Socket(host, 80);
        if (socket.isClosed()) {
            System.out.println("Socket is closed");
        } else {
            System.out.println("Socket is opened");
        }
    }catch (UnknownHostException ex)
    {
        System.err.println("I can't find " + host);
    }
    catch (SocketException ex)
    {
        System.err.println("Could not connect to " + host);
    }
    catch (IOException ex)
    {
        System.err.println(ex);
    }

    /**
     * However, this is not a perfect test. If the socket has never been connected in the first place, isClosed() returns false,
     * even though the socket isn't exactly open.  The Socket class also has an isConnected() method. The name is a little
     * misleading. It does not tell you if the socket is currently connected to a remote host (like if it is unclosed).
     * Instead, it tells you whether the socket has ever been connected to a remote host. If the socket was able to connect
     * to the remote host at all, this method returns true, even after that socket has been closed. To tell if a socket
     * is currently open, you need to check that isConnected() returns true and isClosed() returns false. For example:
     *
     * boolean connected = socket.isConnected() && ! socket.isClosed();
     */

     /**
      * Finally, the isBound() method tells you whether the socket successfully bound to the outgoing port on the local
      * system. Whereas isConnected() refers to the remote end of the socket, isBound() refers to the local end. This
      * isn't very important yet. Binding will become more important when we discuss server sockets in Lesson 9.
      */

    /**
     * toString()
     *
     * The Socket class overrides only one of the standard methods from java.lang.Object: toString(). The toString()
     * method produces a string that looks like this:
     *
     * Socket[addr=www.oreilly.com/198.112.208.11,port=80,localport=50055]
     *
     * This is useful primarily for debugging. Don't rely on this format; it may change in the future. All parts of this
     * string are accessible directly through other methods (specifically getInetAddress(), getPort(), and getLocalPort()).
     *
     * Note
     *
     * Because sockets are transitory objects that typically last only as long as the connection they represent, there's
     * not much reason to store them in hash tables or compare them to each other. Therefore, Socket does not override
     * equals() or hashCode(), and the semantics for these methods are those of the Object class. Two Socket objects are
     * equal to each other if and only if they are the same object.
      */
    }
}
