package c8;

public class SocketExceptions {
    /**
     * Most methods of the Socket class are declared to throw IOException or its subclass, java.net.SocketException:
     *
     * public class SocketException extends IOException
     *
     * However, knowing that a problem occurred is often not sufficient to deal with the problem. Did the remote host refuse
     * the connection because it was busy? Did the remote host refuse the connection because no service was listening on
     * the port? Did the connection attempt timeout because of network congestion or because the host was down? There are
     * several subclasses of SocketException that provide more information about what went wrong and why:
     *
     * public class BindException extends SocketException
     * public class ConnectException extends SocketException
     * public class NoRouteToHostException extends SocketException
     *
     * A BindException is thrown if you try to construct a Socket or ServerSocket object on a local port that is in use
     * or that you do not have sufficient privileges to use.
     *
     * A ConnectException is thrown when a connection is refused at the remote host, which usually happens because the
     * host is busy or no process is listening on that port.
     *
     * NoRouteToHostException indicates that the connection has timed out.
     *
     * The java.net package also includes ProtocolException, which is a direct subclass of IOException:
     *
     * public class ProtocolException extends IOException
     * This is thrown when data is received from the network that somehow violates the TCP/IP specification.
     *
     * None of these exception classes have any special methods you wouldn't find in any other exception class, but you
     * can take advantage of these subclasses to provide more informative error messages or to decide whether retrying
     * the offending operation is likely to be successful.
     */
}
