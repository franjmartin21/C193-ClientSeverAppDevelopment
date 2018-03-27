package c10;

public class CreatingSecureServerSockets {
    /**
     * Secure client sockets are only half of the equation. The other half is SSL-enabled server sockets. These are instances
     * of the javax.net.SSLServerSocket class:

     public abstract class SSLServerSocket extends ServerSocket

     * Like SSLSocket, all the constructors in this class are protected and instances are created by an abstract factory
     * class, javax.net.SSLServerSocketFactory:

     public abstract class SSLServerSocketFactory extends ServerSocketFactory

     * Also like SSLSocketFactory, an instance of SSLServerSocketFactory is returned by a static SSLServerSocketFactory.getDefault() method:

     public static ServerSocketFactory getDefault()

     * And like SSLSocketFactory, SSLServerSocketFactory has three overloaded createServerSocket() methods that return instances
     * of SSLServerSocket and are easily understood by analogy with the java.net.ServerSocket constructors:

     public abstract ServerSocket createServerSocket(int port) throws IOException
     public abstract ServerSocket createServerSocket(int port, int queueLength) throws IOException
     public abstract ServerSocket createServerSocket(int port, int queueLength, InetAddress interface) throws IOException
     */

    /**
     * If that were all there was to creating secure server sockets, they would be quite straightforward and simple to use.
     * Unfortunately, that's not all there is to it. The factory that SSLServerSocketFactory.getDefault() returns generally
     * only supports server authentication. It does not support encryption. To get encryption as well, server-side secure
     * sockets require more initialization and setup. Exactly how this setup is performed is implementation dependent.
     * In Sun's reference implementation, a com.sun.net.ssl.SSLContext object is responsible for creating fully configured
     * and initialized secure server sockets. The details vary from JSSE implementation to JSSE implementation, but to create
     * a secure server socket in the reference implementation, you have to:

     1. Generate public keys and certificates using keytool.
     2. Pay money to have your certificates authenticated by a trusted third party such as Comodo.
     3. Create an SSLContext for the algorithm you'll use.
     4. Create a TrustManagerFactory for the source of certificate material you'll be using.
     5. Create a KeyManagerFactory for the type of key material you'll be using.
     6. Create a KeyStore object for the key and certificate database. (Oracle's default is JKS.)
     7. Fill the KeyStore object with keys and certificates; for instance, by loading them from the filesystem using the passphrase they're encrypted with.
     8. Initialize the KeyManagerFactory with the KeyStore and its passphrase.
     9. Initialize the context with the necessary key managers from the KeyManagerFactory, trust managers from the TrustManagerFactory,
     and a source of randomness. (The last two can be null if you're willing to accept the defaults.)
     */

    /**
     *  SecureOrderTaker.java is a program for accepting orders and printing them on System.out. Of course, in a real application,
     *  you'd do something more interesting with the orders.
     */
}
