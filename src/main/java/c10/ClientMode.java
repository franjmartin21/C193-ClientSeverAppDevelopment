package c10;

public class ClientMode {

    /**
     * It's a rule of thumb that in most secure communications, the server is required to authenticate itself using the
     * appropriate certificate. However, the client is not. That is, when I buy a book from Amazon using its secure server,
     * it has to prove to my browser's satisfaction that it is indeed Amazon and not Joe Random Hacker. However, I do not
     * have to prove to Amazon that I am Elliotte Rusty Harold. For the most part, this is as it should be, because purchasing
     * and installing the trusted certificates necessary for authentication is a fairly user-hostile experience that readers
     * shouldn't have to go through just to buy the latest Nutshell Handbook. However, this asymmetry can lead to credit
     * card fraud. To avoid problems like this, sockets can be required to authenticate themselves. This strategy wouldn't
     * work for a service open to the general public. However, it might be reasonable in certain internal, high-security applications.
     *
     * The setUseClientMode() method determines whether the socket needs to use authentication in its first handshake.
     * The name of the method is a little misleading. It can be used for both client- and server-side sockets. However,
     * when true is passed in, it means the socket is in client mode (whether it's on the client side or not) and will
     * not offer to authenticate itself. When false is passed, it will try to authenticate itself:

     public abstract void setUseClientMode(boolean mode) throws IllegalArgumentException

     * This property can be set only once for any given socket. Attempting to set it a second time throws an IllegalArgumentException.
     * The getUseClientMode() method simply tells you whether this socket will use authentication in its first handshake:

     public abstract boolean getUseClientMode()

     * A secure socket on the server side (i.e., one returned by the accept() method of an SSLServerSocket) uses the
     * setNeedClientAuth() method to require that all clients connecting to it authenticate themselves (or not):

     public abstract void setNeedClientAuth(boolean needsAuthentication) throws IllegalArgumentException

     * This method throws an IllegalArgumentException if the socket is not on the server side.
     * The getNeedClientAuth() method returns true if the socket requires authentication from the client side, false otherwise:

     public abstract boolean getNeedClientAuth()
     */
}
