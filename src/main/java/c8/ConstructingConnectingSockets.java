package c8;

import java.io.IOException;
import java.net.*;

public class ConstructingConnectingSockets {
    /**
     * The java.net.Socket class is Java's fundamental class for performing client-side TCP operations. Other client-oriented
     * classes that make TCP network connections such as URL, URLConnection, Applet, and JEditorPane all ultimately end up
     * invoking the methods of this class. This class itself uses native code to communicate with the local TCP stack of
     * the host operating system.
     */

    /**
     * Basic Constructors
     *
     * Each Socket constructor specifies the host and the port to connect to. Hosts may be specified as an InetAddress or a
     * String. Remote ports are specified as int values from 1 to 65535:
     *
     * public Socket(String host, int port) throws UnknownHostException, IOException
     * public Socket(InetAddress host, int port) throws IOException
     *
     * These constructors connect the socket (i.e., before the constructor returns, an active network connection is established
     * to the remote host). If the connection can't be opened for some reason, the constructor throws an IOException or an
     * UnknownHostException. For example:
     */
    public static void main(String[] args) {

        try {
            Socket toOReilly = new Socket("www.oreilly.com", 80);
            // send and receive data...
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        /**
         * Picking a Local Interface to Connect From
         *
         * Two constructors specify both the host and port to connect to and the interface and port to connect from:
         *
         * public Socket(String host, int port, InetAddress interface, int localPort) throws IOException, UnknownHostException
         * public Socket(InetAddress host, int port, InetAddress interface, int localPort) throws IOException
         *
         * This socket connects to the host and port specified in the first two arguments. It connects from the local network
         * interface and port specified by the last two arguments. The network interface may be either physical (e.g., an Ethernet card)
         * or virtual (a multihomed host with more than one IP address). If 0 is passed for the localPort argument, Java chooses
         * a random available port between 1024 and 65535.
         *
         * Selecting a particular network interface from which to send data is uncommon, but a need does come up occasionally.
         * One situation where you might want to explicitly choose the local address would be on a router/firewall that uses dual
         * Ethernet ports. Incoming connections would be accepted on one interface, processed, and forwarded to the local network
         * from the other interface. Suppose you were writing a program to periodically dump error logs to a printer or send them
         * over an internal mail server. You'd want to make sure you used the inward-facing network interface instead of the
         * outward-facing network interface.
         */
        try
        {
            InetAddress inward = InetAddress.getByName("router");
            Socket socket = new Socket("mail", 25, inward, 0);
            // work with the sockets...
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
        /**
         * By passing 0 for the local port number, I say that I don't care which port is used but I do want to use the network
         * interface bound to the local hostname router.
         *
         * This constructor can throw an IOException or an UnknownHostException for the same reasons as the previous constructors.
         * In addition, it throws an IOException (probably a BindException, although again that's just a subclass of IOException
         * and not specifically declared in the throws clause of this method) if the socket is unable to bind to the requested
         * local network interface. For instance, a program running on a.example.com can't connect from b.example.org. You could
         * take deliberate advantage of this to restrict a compiled program to run on only a predetermined host. It would require
         * customizing distributions for each computer and is certainly overkill for cheap products. Furthermore, Java programs
         * are so easy to disassemble, decompile, and reverse engineer that this scheme is far from foolproof. Nonetheless,
         * it might be part of a scheme to enforce a software license.
         */

        /**
         * Constructing Without Connecting
         *
         * All the constructors we've talked about so far both create the socket object and open a network connection to a
         * remote host. Sometimes you want to split those operations. If you give no arguments to the Socket constructor,
         * it has nowhere to connect to:
         *
         * public Socket()
         *
         * You can connect later by passing a SocketAddress to one of the connect() methods. For example:
         */

         try {
             Socket socket = new Socket();
             // fill in socket options
             SocketAddress address = new InetSocketAddress("time.nist.gov", 13);
             socket.connect(address);
             // work with the sockets...
         }
         catch (IOException ex) {
            System.err.println(ex);
         }

        /**
         * You can pass an int as the second argument to specify the number of milliseconds to wait before the connection times out:
         *
         * public void connect(SocketAddress endpoint, int timeout) throws IOException
         */

        /**
         * Socket Addresses
         *
         * The SocketAddress class represents a connection endpoint. It is an empty abstract class with no methods aside
         * from a default constructor. At least theoretically, the SocketAddress class can be used for both TCP and non-TCP sockets.
         * In practice, only TCP/IP sockets are currently supported and the socket addresses you actually use are all instances
         * of InetSocketAddress.
         *
         * The primary purpose of the SocketAddress class is to provide a convenient store for transient socket connection
         * information such as the IP address and port that can be reused to create new sockets, even after the original
         * socket is disconnected and garbage collected. To this end, the Socket class offers two methods that return
         * SocketAddress objects (getRemoteSocketAddress() returns the address of the system being connected to and
         * getLocalSocketAddress() returns the address from which the connection is made:
         *
         * public SocketAddress getRemoteSocketAddress()
         * public SocketAddress getLocalSocketAddress()
         *
         * Both of these methods return null if the socket is not yet connected. For example, first you might connect to
         * Yahoo! then store its address:
         */
        try {
            Socket socket = new Socket("www.yahoo.com", 80);
            SocketAddress yahoo = socket.getRemoteSocketAddress();
            socket.close();
            //Later, you could reconnect to Yahoo! using this address:
            Socket socket2 = new Socket();
            socket2.connect(yahoo);
        } catch(IOException e){
            System.err.println(e);
        }

         /**
          * The InetSocketAddress class (which is the only subclass of SocketAddress in the JDK, and the only subclass
          * I've ever encountered) is usually created with a host and a port (for clients) or just a port (for servers):
          *
          * public InetSocketAddress(InetAddress address, int port)
          * public InetSocketAddress(String host, int port)
          * public InetSocketAddress(int port)
          *
          * You can also use the static factory method InetSocketAddress.createUnresolved() to skip looking up the host in DNS:
          *
          * public static InetSocketAddress createUnresolved(String host, int port)
          *
          * InetSocketAddress has a few getter methods you can use to inspect the object:
          *
          * public final InetAddress getAddress()
          * public final int         getPort()
          * public final String      getHostName()
         */

        /**
         * Proxy Servers
         * The last constructor creates an unconnected socket that connects through a specified proxy server:
         *
         * public Socket(Proxy proxy)
         *
         * Normally, the proxy server a socket uses is controlled by the socksProxyHost and socksProxyPort system properties,
         * and these properties apply to all sockets in the system. However, a socket created by this constructor will use
         * the specified proxy server instead. Most notably, you can pass Proxy.NO_PROXY for the argument to bypass all
         * proxy servers completely and connect directly to the remote host. Of course, if a firewall prevents direct connections,
         * there's nothing Java can do about it; and the connection will fail.
         *
         * To use a particular proxy server, specify it by address. For example, this code fragment uses the SOCKS proxy
         * server at myproxy.example.com to connect to the host login.ibiblio.org:
         */
        try {
            SocketAddress proxyAddress = new InetSocketAddress("myproxy.example.com", 1080);
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, proxyAddress);
            Socket s = new Socket(proxy);
            SocketAddress remote = new InetSocketAddress("login.ibiblio.org", 25);
            s.connect(remote);
        } catch(IOException e){
            System.out.println(e);
        }

        /**
         * SOCKS is the only low-level proxy type Java understands. There's also a high-level Proxy.Type.HTTP that works
         * in the application layer rather than the transport layer and a Proxy.Type.DIRECT that represents proxyless connections.
         */
    }
}
