package c9;

public class ConstructingServerSockets {
    /**
     * There are four public ServerSocket constructors:
     *
     * public ServerSocket(int port) throws BindException, IOException
     * public ServerSocket(int port, int queueLength) throws BindException, IOException
     * public ServerSocket(int port, int queueLength, InetAddress bindAddress) throws IOException
     * public ServerSocket() throws IOException
     *
     * These constructors specify the port, the length of the queue used to hold incoming connection requests, and the
     * local network interface to bind to. They pretty much all do the same thing, though some use default values for
     * the queue length and the address to bind to.
     *
     * For example, to create a server socket that would be used by an HTTP server on port 80, you would write:
     *
     * ServerSocket httpd = new ServerSocket(80);
     *
     * To create a server socket that would be used by an HTTP server on port 80 and queues up to 50 unaccepted connections at a time:
     *
     * ServerSocket httpd = new ServerSocket(80, 50);
     *
     * If you try to expand the queue past the operating system's maximum queue length, the maximum queue length is used instead.
     *
     * By default, if a host has multiple network interfaces or IP addresses, the server socket listens on the specified
     * port on all the interfaces and IP addresses. However, you can add a third argument to bind only to one particular
     * local IP address. That is, the server socket only listens for incoming connections on the specified address; it
     * won't listen for connections that come in through the host's other addresses.
     *
     * For example, login.ibiblio.org is a particular Linux box in North Carolina. It's connected to the Internet with
     * the IP address 152.2.210.122. The same box has a second Ethernet card with the local IP address 192.168.210.122
     * that is not visible from the public Internet, only from the local network. If, for some reason, you wanted to run
     * a server on this host that only responded to local connections from within the same network, you could create a
     * server socket that listens on port 5776 of 192.168.210.122 but not on port 5776 of 152.2.210.122, like so:
     *
     * InetAddress local = InetAddress.getByName("192.168.210.122");
     * ServerSocket httpd = new ServerSocket(5776, 10, local);
     *
     * In all three constructors, you can pass 0 for the port number so the system will select an available port for you.
     * A port chosen by the system like this is sometimes called an anonymous port because you don't know its number in
     * advance (though you can find out after the port has been chosen). This is often useful in multisocket protocols
     * such as FTP. In passive FTP the client first connects to a server on the well-known port 21, so the server has to
     * specify that port. However, when a file needs to be transferred, the server starts listening on any available port.
     * The server then tells the client what other port it should connect to for data using the command connection already
     * open on port 21. Thus, the data port can change from one session to the next and does not need to be known in advance.
     * (Active FTP is similar except the client listens on an ephemeral port for the server to connect to it, rather than
     * the other way around.)
     *
     * All these constructors throw an IOException, specifically, a BindException, if the socket cannot be created and
     * bound to the requested port. An IOException when creating a ServerSocket almost always means one of two things.
     * Either another server socket, possibly from a completely different program, is already using the requested port,
     * or you're trying to connect to a port from 1 to 1023 on Unix (including Linux and Mac OS X) without root (superuser)
     * privileges.
     *
     * You can take advantage of this to write a variation on the LowPortScanner program of the previous lesson. Rather
     * than attempting to connect to a server running on a given port, you instead attempt to open a server on that port.
     * If it's occupied, the attempt will fail. LocalPortScanner checks for ports on the local machine by attempting to
     * create ServerSocket objects on them and seeing on which ports that fails. If you're using Unix and are not running
     * as root, this program works only for ports 1024 and above.
     */

    /**
     * Constructing Without Binding
     *
     * The noargs constructor creates a ServerSocket object but does not actually bind it to a port, so it cannot initially
     * accept any connections. It can be bound later using the bind() methods:
     *
     * public void bind(SocketAddress endpoint) throws IOException
     * public void bind(SocketAddress endpoint, int queueLength) throws IOException
     *
     * The primary use for this feature is to allow programs to set server socket options before binding to a port.
     * Some options are fixed after the server socket has been bound. The general pattern looks like this:
     *
     * ServerSocket ss = new ServerSocket();
     * // set socket options...
     * SocketAddress  http = new InetSocketAddress(80);
     * ss.bind(http);
     *
     * You can also pass null for the SocketAddress to select an arbitrary port. This is like passing 0 for the port
     * number in the other constructors.
     */
}
