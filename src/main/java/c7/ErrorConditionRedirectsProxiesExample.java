package c7;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ErrorConditionRedirectsProxiesExample {

    /**
     * Error conditions
     *
     * On occasion, the server encounters an error but returns useful information in the message body nonetheless. For example,
     * when a client requests a nonexistent page from the www.ibiblio.org website, rather than simply returning a 404 error code,
     * the server sends the search page shown in Figure 7-2 to help the user figure out where the missing page might have gone.
     *
     * The getErrorStream() method returns an InputStream containing this page or null if no error was encountered or no data
     * returned:
     *
     * public InputStream getErrorStream()
     *
     * Generally, you'll invoke getErrorStream() inside a catch block after getInputStream() has failed. Example 7-17
     * demonstrates with a program that reads form the input stream if possible. However, if that fails for any reason,
     * it then reads from the error stream instead.
     */
    public static void main(String[] args) {

        try
        {
            URL u = new URL(args[0]);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            try (InputStream raw = uc.getInputStream())
            {
                printFromStream(raw);
            }
            catch (IOException ex)
            {
                printFromStream(uc.getErrorStream());
            }
        }
            catch (MalformedURLException ex)
        {
            System.err.println(args[0] + " is not a parseable URL");
        }
            catch (IOException ex)
        {
            System.err.println(ex);
        }
    }


    private static void printFromStream(InputStream raw) throws IOException
    {
        try (InputStream buffer = new BufferedInputStream(raw))
        {
            Reader reader = new InputStreamReader(buffer);
            int c;
            while ((c = reader.read()) != -1)
            {
                System.out.print((char) c);
            }
        }
    }
    /**
     * Redirects
     *
     * The 300-level response codes all indicate some sort of redirect; that is, the requested resource is no longer available
     * at the expected location but it may be found at some other location. When encountering such a response, most browsers
     * automatically load the document from its new location. However, this can be a security risk, because it has the potential
     * to move the user from a trusted site to an untrusted one, perhaps without the user even noticing.
     *
     * By default, an HttpURLConnection follows redirects. However, the HttpURLConnection class has two static methods
     * that let you decide whether to follow redirects:
     *
     * public static boolean getFollowRedirects()
     * public static void    setFollowRedirects(boolean follow)
     *
     * The getFollowRedirects() method returns true if redirects are being followed, false if they aren't. With an argument
     * of true, the setFollowRedirects() method makes HttpURLConnection objects follow redirects. With an argument of false,
     * it prevents them from following redirects. Because these are static methods, they change the behavior of all HttpURLConnection
     * objects constructed after the method is invoked. The setFollowRedirects() method may throw a SecurityException if the
     * security manager disallows the change. Applets especially are not allowed to change this value.
     *
     * Java has two methods to configure redirection on an instance-by-instance basis. These are:
     *
     * public boolean getInstanceFollowRedirects()
     * public void    setInstanceFollowRedirects(boolean followRedirects)
     *
     * If setInstanceFollowRedirects() is not invoked on a given HttpURLConnection, that HttpURLConnection simply follows
     * the default behavior as set by the class method HttpURLConnection.setFollowRedirects().
     */

    /**
     * Proxies
     *
     * Many users behind firewalls or using AOL or other high-volume ISPs access the Web through proxy servers. The usingProxy()
     * method tells you whether the particular HttpURLConnection is going through a proxy server:
     *
     * public abstract boolean usingProxy()
     *
     * It returns true if a proxy is being used, false if not. In some contexts, the use of a proxy server may have security implications.
     */

    /**
     * Streaming Mode
     *
     * Every request sent to an HTTP server has an HTTP header. One field in this header is the Content-length (i.e., the number
     * of bytes in the body of the request). The header comes before the body. However, to write the header you need to know
     * the length of the body, which you may not have yet. Normally, the way Java solves this catch-22 is by caching everything
     * you write onto the OutputStream retrieved from the HttpURLConnection until the stream is closed. At that point, it
     * knows how many bytes are in the body so it has enough information to write the Content-length header.
     *
     * This scheme is fine for small requests sent in response to typical web forms. However, it's burdensome for responses
     * to very long forms or some SOAP messages. It's very wasteful and slow for medium or large documents sent with HTTP PUT.
     * It's much more efficient if Java doesn't have to wait for the last byte of data to be written before sending the first
     * byte of data over the network. Java offers two solutions to this problem. If you know the size of your data—for instance,
     * you're uploading a file of known size using HTTP PUT—you can tell the HttpURLConnection object the size of that data.
     * If you don't know the size of the data in advance, you can use chunked transfer encoding instead. In chunked transfer
     * encoding, the body of the request is sent in multiple pieces, each with its own separate content length. To turn on
     * chunked transfer encoding, just pass the size of the chunks you want to the setChunkedStreamingMode() method before you
     * connect the URL:
     *
     * public void setChunkedStreamingMode(int chunkLength)
     *
     * If you do happen to know the size of the request data in advance, you can optimize the connection by providing this
     * information to the HttpURLConnection object. If you do this, Java can start streaming the data over the network
     * immediately. Otherwise, it has to cache everything you write in order to determine the content length, and only
     * send it over the network after you've closed the stream. If you know exactly how big your data is, pass that number
     * to the setFixedLengthStreamingMode() method:
     *
     * public void setFixedLengthStreamingMode(int contentLength)
     * public void setFixedLengthStreamingMode(long contentLength) // Java 7
     *
     * Java will use this number in the Content-length HTTP header field. However, if you then try to write more or less
     * than the number of bytes given here, Java will throw an IOException. Of course, that happens later, when you're
     * writing data, not when you first call this method. The setFixedLengthStreamingMode() method itself will throw an
     * IllegalArgumentException if you pass in a negative number, or an IllegalStateException if the connection is connected
     * or has already been set to chunked transfer encoding. (You can't use both chunked transfer encoding and fixed-length
     * streaming mode on the same request.)"
     *
     * Fixed-length streaming mode is transparent on the server side. Servers neither know nor care how the Content-length
     * was set, as long as it's correct. However, like chunked transfer encoding, streaming mode does interfere with
     * authentication and redirection. If either of these is required for a given URL, an HttpRetryException will be thrown;
     * you have to manually retry. Therefore, don't use this mode unless you really need it.
     */

}
