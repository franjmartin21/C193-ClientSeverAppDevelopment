package c10;

public class EventHandlers {
    /**
     * Network communications are slow compared to the speed of most computers. Authenticated network communications are
     * even slower. The necessary key generation and setup for a secure connection can easily take several seconds.
     * Consequently, you may want to deal with the connection asynchronously. JSSE uses the standard Java event model to
     * notify programs when the handshaking between client and server is complete. The pattern is a familiar one. In
     * order to get notifications of handshake-complete events, simply implement the HandshakeCompletedListener interface:

     public interface HandshakeCompletedListener extends java.util.EventListener

     * This interface declares the handshakeCompleted() method:

     public void handshakeCompleted(HandshakeCompletedEvent event)

     * This method receives as an argument a HandshakeCompletedEvent:

     public class HandshakeCompletedEvent extends java.util.EventObject

     * The HandshakeCompletedEvent class provides four methods for getting information about the event:

     public SSLSession getSession()
     public String getCipherSuite()
     public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException
     public SSLSocket getSocket()

     * Particular HandshakeCompletedListener objects register their interest in handshake-completed events from a particular
     * SSLSocket via its addHandshakeCompletedListener() and removeHandshakeCompletedListener() methods:

     public abstract void addHandshakeCompletedListener( HandshakeCompletedListener listener)
     public abstract void removeHandshakeCompletedListener( HandshakeCompletedListener listener) throws IllegalArgumentException
     */
}

