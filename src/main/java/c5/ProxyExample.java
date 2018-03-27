package c5;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

public class ProxyExample {
    /**
     * System Properties
     *
     * For basic operations, all you have to do is set a few system properties to point to the addresses of your local proxy
     * servers. If you are using a pure HTTP proxy, set http.proxyHost to the domain name or the IP address of your proxy
     * server and http.proxyPort to the port of the proxy server (the default is 80). There are several ways to do this,
     * including calling System.setProperty() from within your Java code or using the -D options when launching the program.
     * This example sets the proxy server to 192.168.254.254 and the port to 9000:
     *
     * java -Dhttp.proxyHost=192.168.254.254  -Dhttp.proxyPort=9000 com.domain.Program
     *
     */

    /**
     * If the proxy requires a username and password, you'll need to install an Authenticator, as we'll discuss shortly
     * in Accessing Password-Protected Sites.
     * If you want to exclude a host from being proxied and connect directly instead, set the http.nonProxyHosts system
     * property to its hostname or IP address. To exclude multiple hosts, separate their names by vertical bars. For example,
     * this code fragment proxies everything except java.oreilly.com and xml.oreilly.com:
     *
     * System.setProperty("http.proxyHost", "192.168.254.254");
     * System.setProperty("http.proxyPort", "9000");
     * System.setProperty("http.nonProxyHosts", "java.oreilly.csom|xml.oreilly.com");
     */

    /**
     * You can also use an asterisk as a wildcard to indicate that all the hosts within a particular domain or subdomain
     * should not be proxied. For example, to proxy everything except hosts in the oreilly.com domain:
     *
     * java -Dhttp.proxyHost=192.168.254.254  -Dhttp.nonProxyHosts=*.oreilly.com com.domain.Program
     *
     * If you are using an FTP proxy server, set the ftp.proxyHost, ftp.proxyPort, and ftp.nonProxyHosts properties in the same way.
     */

    /**
     * Java does not support any other application layer proxies, but if you're using a transport layer SOCKS proxy for all
     * TCP connections, you can identify it with the socksProxyHost and socksProxyPort system properties. Java does not
     * provide an option for nonproxying with SOCKS. It's an all-or-nothing decision.
     */

    /**
     * The Proxy Class
     * <p>
     * The Proxy class allows more fine-grained control of proxy servers from within a Java program. Specifically, it allows
     * you to choose different proxy servers for different remote hosts. The proxies themselves are represented by instances
     * of the java.net.Proxy class. There are still only three kinds of proxies, HTTP, SOCKS, and direct connections (no proxy
     * at all), represented by three constants in the Proxy.Type enum:
     * <p>
     * Proxy.Type.DIRECT
     * Proxy.Type.HTTP
     * Proxy.Type.SOCKS
     * <p>
     * Besides its type, the other important piece of information about a proxy is its address and port, given as a SocketAddress
     * object. For example, this code fragment creates a Proxy object representing an HTTP proxy server on port 80 of proxy.example.com:
     */

    public static void main(String[] args) {
        SocketAddress address = new InetSocketAddress("proxy.example.com", 80);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
    }
}