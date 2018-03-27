package c9;

public class GettingInformationServerSocket {
    /**
     * The ServerSocket class provides two getter methods that tell you the local address and port occupied by the server socket.
     * These are useful if you've opened a server socket on an anonymous port and/or an unspecified network interface.
     * This would be the case, for one example, in the data connection of an FTP session:
     *
     * public InetAddress getInetAddress()
     *
     * This method returns the address being used by the server (the local host). If the local host has a single IP address
     * (as most do), this is the address returned by InetAddress.getLocalHost(). If the local host has more than one IP address,
     * the specific address returned is one of the host's IP addresses. You can't predict which address you will get. For example:
     *
     * ServerSocket httpd = new ServerSocket(80);
     * InetAddress ia = httpd.getInetAddress();
     *
     * If the ServerSocket has not yet bound to a network interface, this method returns null:
     *
     * public int getLocalPort()
     *
     * The ServerSocket constructors allow you to listen on an unspecified port by passing 0 for the port number.
     * This method lets you find out what port you're listening on. You might use this in a peer-to-peer multisocket
     * program where you already have a means to inform other peers of your location. Or a server might spawn several
     * smaller servers to perform particular operations. The well-known server could inform clients on what ports they
     * can find the smaller servers. Of course, you can also use getLocalPort() to find a nonanonymous port, but why
     * would you need to? RandomPort.java demostrates
     */

    /**
     * As with most Java objects, you can also just print out a ServerSocket using its toString() method. A String returned
     * by a ServerSocket's toString() method looks like this:
     *
     * ServerSocket[addr=0.0.0.0,port=0,localport=5776]
     *
     * addr is the address of the local network interface to which the server socket is bound. This will be 0.0.0.0 if
     * it's bound to all interfaces, as is commonly the case. port is always 0. The localport is the local port on which
     * the server is listening for connections. This method is sometimes useful for debugging, but not much more. Don't
     * rely on it.
     */
}
