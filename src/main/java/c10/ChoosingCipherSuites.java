package c10;

public class ChoosingCipherSuites {
    /**
     * Different implementations of the JSSE support different combinations of authentication and encryption algorithms.
     * For instance, the implementation that Oracle bundles with Java 7 only supports 128-bit AES encryption, whereas IAIK's
     * iSaSiLk supports 256-bit AES encryption.
     *
     * The getSupportedCipherSuites() method in SSLSocketFactory tells you which combination of algorithms is available on a given socket:

     public abstract String[] getSupportedCipherSuites()

     * However, not all cipher suites that are understood are necessarily allowed on the connection. Some may be too weak
     * and consequently disabled. The getEnabledCipherSuites() method of SSLSocketFactory tells you which suites this
     * socket is willing to use:

     public abstract String[] getEnabledCipherSuites()

     * The actual suite used is negotiated between the client and server at connection time. It's possible that the client
     * and the server won't agree on any suite. It's also possible that although a suite is enabled on both client and server,
     * one or the other or both won't have the keys and certificates needed to use the suite. In either case, the createSocket()
     * method will throw an SSLException, a subclass of IOException. You can change the suites the client attempts to use via
     * the setEnabledCipherSuites() method:

     public abstract void setEnabledCipherSuites(String[] suites)

     * The argument to this method should be a list of the suites you want to use. Each name must be one of the suites listed
     * by getSupportedCipherSuites(). Otherwise, an IllegalArgumentException will be thrown.
     */

    /**
     * Oracle's JDK 1.7 supports these cipher suites:

     TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256
     TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256
     TLS_RSA_WITH_AES_128_CBC_SHA256
     TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256
     TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256
     TLS_DHE_RSA_WITH_AES_128_CBC_SHA256
     TLS_DHE_DSS_WITH_AES_128_CBC_SHA256
     TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA
     TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA
     TLS_RSA_WITH_AES_128_CBC_SHA
     TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA
     TLS_ECDH_RSA_WITH_AES_128_CBC_SHA
     TLS_DHE_RSA_WITH_AES_128_CBC_SHA
     TLS_DHE_DSS_WITH_AES_128_CBC_SHA
     TLS_ECDHE_ECDSA_WITH_RC4_128_SHA
     TLS_ECDHE_RSA_WITH_RC4_128_SHA
     SSL_RSA_WITH_RC4_128_SHA
     TLS_ECDH_ECDSA_WITH_RC4_128_SHA
     TLS_ECDH_RSA_WITH_RC4_128_SHA
     TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA
     TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA
     SSL_RSA_WITH_3DES_EDE_CBC_SHA
     TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA
     TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA
     SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA
     SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA
     SSL_RSA_WITH_RC4_128_MD5
     TLS_EMPTY_RENEGOTIATION_INFO_SCSV
     TLS_DH_anon_WITH_AES_128_CBC_SHA256
     TLS_ECDH_anon_WITH_AES_128_CBC_SHA
     TLS_DH_anon_WITH_AES_128_CBC_SHA
     TLS_ECDH_anon_WITH_RC4_128_SHA
     SSL_DH_anon_WITH_RC4_128_MD5
     TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA
     SSL_DH_anon_WITH_3DES_EDE_CBC_SHA
     TLS_RSA_WITH_NULL_SHA256
     TLS_ECDHE_ECDSA_WITH_NULL_SHA
     TLS_ECDHE_RSA_WITH_NULL_SHA
     SSL_RSA_WITH_NULL_SHA
     TLS_ECDH_ECDSA_WITH_NULL_SHA
     TLS_ECDH_RSA_WITH_NULL_SHA
     TLS_ECDH_anon_WITH_NULL_SHA
     SSL_RSA_WITH_NULL_MD5
     SSL_RSA_WITH_DES_CBC_SHA
     SSL_DHE_RSA_WITH_DES_CBC_SHA
     SSL_DHE_DSS_WITH_DES_CBC_SHA
     SSL_DH_anon_WITH_DES_CBC_SHA
     SSL_RSA_EXPORT_WITH_RC4_40_MD5
     SSL_DH_anon_EXPORT_WITH_RC4_40_MD5
     SSL_RSA_EXPORT_WITH_DES40_CBC_SHA
     SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA
     SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA
     SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA
     TLS_KRB5_WITH_RC4_128_SHA
     TLS_KRB5_WITH_RC4_128_MD5
     TLS_KRB5_WITH_3DES_EDE_CBC_SHA
     TLS_KRB5_WITH_3DES_EDE_CBC_MD5
     TLS_KRB5_WITH_DES_CBC_SHA
     TLS_KRB5_WITH_DES_CBC_MD5
     TLS_KRB5_EXPORT_WITH_RC4_40_SHA
     TLS_KRB5_EXPORT_WITH_RC4_40_MD5
     TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA
     TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5
     *
     * Each name has an algorithm divided into four parts: protocol, key exchange algorithm, encryption algorithm, and checksum.
     * For example, the name SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA means Secure Sockets Layer Version 3; Diffie-Hellman method
     * for key agreement; no authentication; Data Encryption Standard encryption with 40-bit keys; Cipher Block Chaining,
     * and the Secure Hash Algorithm checksum.
     *
     * By default, the JDK 1.7 implementation enables all the encrypted authenticated suites (the first 28 members of this list).
     * If you want nonauthenticated transactions or authenticated but unencrypted transactions, you must enable those suites
     * explicitly with the setEnabledCipherSuites() method.
     * You should probably avoid any of these suites that contain NULL, ANON, or EXPORT in their names unless you want the NSA
     * to read your messages.
     *
     * TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 is believed to be reasonably secure against all known attacks.
     * TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA256 is even better if you've enabled it. In general, any suite that begins with
     * TLS_ECDHE and ends with SHA256 or SHA384 is the strongest possible encryption widely available today. Most others
     * are subject to attacks of varying levels of severity.
     *
     * Besides key lengths, there's an important difference between DES/AES and RC4-based ciphers. DES and AES are block
     * ciphers (i.e., they encrypt a certain number of bits at a time). DES always encrypts 64 bits. If 64 bits aren't
     * available, the encoder has to pad the input with extra bits. AES can encrypt blocks of 128, 192, or 256 bits, but
     * still has to pad the input if it doesn't come out to an even multiple of the block size. This isn't a problem
     * for file transfer applications such as secure HTTP and FTP, where more or less all the data is available at once.
     * However, it's problematic for user-centered protocols such as chat and Telnet. RC4 is a stream cipher that can
     * encrypt one byte at a time and is more appropriate for protocols that may need to send a single byte at a time.
     *
     * For example, let's suppose that Edgar has some fairly powerful parallel computers at his disposal and can quickly
     * break any encryption that's 64 bits or less and that Gus and Angela know this. Furthermore, they suspect that Edgar
     * can blackmail one of their ISPs or the phone company into letting him tap the line, so they want to avoid anonymous
     * connections that are vulnerable to man-in-the-middle attacks. To be safe, Gus and Angela decide to use only the strongest
     * suite available, which happens to be TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256. This code fragment limits their connection
     * to that one suite:

     String[] strongSuites = {"TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256"};
     socket.setEnabledCipherSuites(strongSuites);

     * If the other side of the connection doesn't support this encryption protocol, the socket will throw an exception
     * when they try to read from or write to it, thus ensuring that no confidential information is accidentally transmitted
     * over a weak channel.
     */
}
