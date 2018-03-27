package c10;

public class SessionManagement {
    /**
     * SSL is commonly used on web servers, and for good reason. Web connections tend to be transitory; every page requires a
     * separate socket. For instance, checking out of Amazon.com on its secure server requires seven separate page loads,
     * more if you have to edit an address or choose gift wrapping. Imagine if every one of those pages took an extra 10
     * seconds or more to negotiate a secure connection. Because of the high overhead involved in handshaking between two
     * hosts for secure communications, SSL allows sessions to be established that extend over multiple sockets. Different
     * sockets within the same session use the same set of public and private keys. If the secure connection to Amazon.com
     * takes seven sockets, all seven will be established within the same session and use the same keys. Only the first
     * socket within that session will have to endure the overhead of key generation and exchange.
     *
     * As a programmer using JSSE, you don't need to do anything extra to take advantage of sessions. If you open multiple
     * secure sockets to one host on one port within a reasonably short period of time, JSSE will reuse the session's keys
     * automatically. However, in high-security applications, you may want to disallow session-sharing between sockets or
     * force reauthentication of a session. In the JSSE, sessions are represented by instances of the SSLSession interface;
     * you can use the methods of this interface to check the times the session was created and last accessed, invalidate
     * the session, and get various information about the session:

     public byte[] getId()
     public SSLSessionContext getSessionContext()
     public long getCreationTime()
     public long getLastAccessedTime()
     public void invalidate()
     public void putValue(String name, Object value)
     public Object getValue(String name)
     public void removeValue(String name)
     public String[] getValueNames()
     public X509Certificate[] getPeerCertificateChain()
     throws SSLPeerUnverifiedException
     public String getCipherSuite()
     public String getPeerHost()

     * The getSession() method of SSLSocket returns the Session this socket belongs to:

     public abstract SSLSession getSession()

     * However, sessions are a trade-off between performance and security. It is more secure to renegotiate the key for each
     * and every transaction. If you've got really spectacular hardware and are trying to protect your systems from an equally
     * determined, rich, motivated, and competent adversary, you may want to avoid sessions. To prevent a socket from creating
     * a session that passes false to setEnableSessionCreation(), use:

     public abstract void setEnableSessionCreation(boolean allowSessions)

     * The getEnableSessionCreation() method returns true if multisocket sessions are allowed, false if they're not:

     public abstract boolean getEnableSessionCreation()

     * On rare occasions, you may even want to reauthenticate a connection (i.e., throw away all the certificates and keys
     * that have previously been agreed to and start over with a new session). The startHandshake() method does this:

     public abstract void startHandshake() throws IOException

     */
}
