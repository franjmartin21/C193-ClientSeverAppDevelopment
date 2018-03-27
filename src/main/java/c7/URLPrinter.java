package c7;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * The URLConnection class has seven protected instance fields that define exactly how the client makes the request to
 * the server. These are:
 *
 * protected URL     url;
 * protected boolean doInput = true;
 * protected boolean doOutput = false;
 * protected boolean allowUserInteraction = defaultAllowUserInteraction;
 * protected boolean useCaches = defaultUseCaches;
 * protected long    ifModifiedSince = 0;
 * protected boolean connected = false;
 */
public class URLPrinter
{
    public static void main(String[] args)
    {
        try
        {
            URL u = new URL("http://www.oreilly.com/");
            URLConnection uc = u.openConnection();
            System.out.println(uc.getFileNameMap());
            System.out.println(uc.getDoInput());
            System.out.println(uc.getDoOutput());
            System.out.println(uc.getAllowUserInteraction());
            System.out.println(uc.getUseCaches());
            System.out.println(uc.getIfModifiedSince());
            /**
             * The url field specifies the URL that this URLConnection connects to. The constructor sets it when the
             * URLConnection is created and it should not change thereafter. You can retrieve the value by calling the
             * getURL() method. Example 7-12 opens a URLConnection to http://www.oreilly.com, gets the URL of that
             * connection, and prints it.
             */
            System.out.println(uc.getURL());

            /**
             * protected boolean connected
             *
             * The boolean field connected is true if the connection is open and false if it's closed. Because the connection
             * has not yet been opened when a new URLConnection object is created, its initial value is false. This variable
             * can be accessed only by instances of java.net.URLConnection and its subclasses.
             *
             * There are no methods that directly read or change the value of connected. However, any method that causes
             * the URLConnection to connect should set this variable to true, including connect(), getInputStream(), and
             * getOutputStream().
             * Any method that causes the URLConnection to disconnect should set this field to false.
             * There are no such methods in java.net.URLConnection, but some of its subclasses, such as java.net.HttpURLConnection,
             * have disconnect() methods.
             */
            //Cannot access it as it is protected System.out.println(uc.connected());

            /**
             * Some URLConnections need to interact with a user. For example, a web browser may need to ask for a username
             * and password. However, many applications cannot assume that a user is present to interact with it.
             * For instance, a search engine robot is probably running in the background without any user to provide a
             * username and password. As its name suggests, the allowUserInteraction field specifies whether user interaction
             * is allowed. It is false by default.
             *
             * This variable is protected, but the public getAllowUserInteraction() method can read its value and the public
             * setAllowUserInteraction() method can change it:
             *
             * public void    setAllowUserInteraction(boolean allowUserInteraction)
             * public boolean getAllowUserInteraction()
             *
             * The value true indicates that user interaction is allowed; false indicates that there is no user interaction.
             * The value may be read at any time but may be set only before the URLConnection is connected. Calling
             * setAllowUserInteraction() when the URLConnection is connected throws an IllegalStateException.
             */
            URL u2 = new URL("http://www.example.com/passwordProtectedPage.html");
            URLConnection uc2 = u.openConnection();
            uc2.setAllowUserInteraction(true);
            InputStream in = uc.getInputStream();

            /**
             * The static methods getDefaultAllowUserInteraction() and setDefaultAllowUserInteraction() determine the default
             * behavior for URLConnection objects that have not set allowUserInteraction explicitly. Because the allowUserInteraction
             * field is static (i.e., a class variable instead of an instance variable), setting it changes the default behavior
             * for all instances of the URLConnection class that are created after setDefaultAllowUserInteraction() is called.
             *
             * For instance, the following code fragment checks to see whether user interaction is allowed by default with
             * getDefaultAllowUserInteraction(). If user interaction is not allowed by default, the code uses setDefaultAllowUserInteraction()
             * to make allowing user interaction the default behavior:
             *
             */
             if (!URLConnection.getDefaultAllowUserInteraction())
             {
                URLConnection.setDefaultAllowUserInteraction(true);
             }

            /**
             * protected boolean doInput
             *
             * A URLConnection can be used for reading from a server, writing to a server, or both. The protected boolean
             * field doInput is true if the URLConnection can be used for reading, false if it cannot be. The default is true.
             * To access this protected variable, use the public getDoInput() and setDoInput() methods:
             *
             * public void    setDoInput(boolean doInput)
             * public boolean getDoInput()
             *
             * For example:
             */

             try{
                URL u3 = new URL("http://www.oreilly.com");
                URLConnection uc3 = u.openConnection();
                if (!uc3.getDoInput())
                {
                    uc3.setDoInput(true);
                }
                // read from the connection...
             }
             catch (IOException ex)
             {
                System.err.println(ex);
             }

            /**
             * protected boolean doOutput
             *
             * Programs can use a URLConnection to send output back to the server. For example, a program that needs to
             * send data to the server using the POST method could do so by getting an output stream from a URLConnection.
             * The protected boolean field doOutput is true if the URLConnection can be used for writing, false if it cannot
             * be; it is false by default. To access this protected variable, use the getDoOutput() and setDoOutput() methods:
             *
             * public void    setDoOutput(boolean dooutput)
             * public boolean getDoOutput()
             *
             * For example:
             */
             try {
                URL u4 = new URL("http://www.oreilly.com");
                URLConnection uc4 = u.openConnection();
                if (!uc4.getDoOutput())
                {
                    uc4.setDoOutput(true);
                }
                // write to the connection...
             }
             catch (IOException ex)
             {
                System.err.println(ex);
             }
            /**
             * When you set doOutput to true for an http URL, the request method is changed from GET to POST. We'll explore
             * this in more detail later in Writing Data to a Server.
             */

            /**
             * protected boolean ifModifiedSince
             *
             * Many clients, especially web browsers and proxies, keep caches of previously retrieved documents. If the
             * user asks for the same document again, it can be retrieved from the cache. However, it may have changed
             * on the server since it was last retrieved. The only way to tell is to ask the server. Clients can include
             * an If-Modified-Since in the client request HTTP header. This header includes a date and time. If the document
             * has changed since that time, the server should send it. Otherwise, it should not. Typically, this time is
             * the last time the client fetched the document. For example, this client request says the document should be
             * returned only if it has changed since 7:22:07 A.M., October 31, 2014, Greenwich Mean Time:
             *
             *

             GET / HTTP/1.1
             Host: login.ibiblio.org:56452
             Accept: text/html, image/gif, image/jpeg, *; q=.2, *\/*; q=.2
             Connection: close
             If-Modified-Since: Fri, 31 Oct 2014 19:22:07 GMT

             *
             * If the document has changed since that time, the server will send it as usual. Otherwise, it replies with a
             * 304 Not Modified message, like this:

             HTTP/1.0 304 Not Modified
             Server: WN/1.15.1
             Date: Sun, 02 Nov 2014 16:26:16 GMT
             Last-modified: Fri, 29 Oct 2004 23:40:06 GMT

             * The client then loads the document from its cache. Not all web servers respect the If-Modified-Since field.
             * Some will send the document whether it's changed or not.
             * The ifModifiedSince field in the URLConnection class specifies the date (in milliseconds since midnight,
             * Greenwich Mean Time, January 1, 1970), which will be placed in the If-Modified-Since header field. Because
             * ifModifiedSince is protected, programs should call the getIfModifiedSince() and setIfModifiedSince() methods
             * to read or modify it:
             *
             * public long getIfModifiedSince()
             * public void setIfModifiedSince(long ifModifiedSince)
             *
             * Example 7-13 prints the default value of ifModifiedSince, sets its value to 24 hours ago, and prints the new
             * value. It then downloads and displays the document-but only if it's been modified in the last 24 hours.
             *
             * Example in file Last24
             */

            /**
             * protected boolean useCaches
             *
             * Some clients, notably web browsers, can retrieve a document from a local cache, rather than retrieving it
             * from a server. Applets may have access to the browser's cache. Standalone applications can use the
             * java.net.ResponseCache class. The useCaches variable determines whether a cache will be used if it's available.
             * The default value is true, meaning that the cache will be used; false means the cache won't be used.
             * Because useCaches is protected, programs access it using the getUseCaches() and setUseCaches() methods:
             *
             * public void    setUseCaches(boolean useCaches)
             * public boolean getUseCaches()
             *
             * This code fragment disables caching to ensure that the most recent version of the document is retrieved by setting useCaches to false:
             */
             try
             {
                 URL u5 = new URL("http://www.sourcebot.com/");
                 URLConnection uc5 = u.openConnection();
                 uc5.setUseCaches(false);
                 // read the document...
             }
             catch (IOException ex)
             {
                 System.err.println(ex);
             }

             /**
              * Two methods define the initial value of the useCaches field, getDefaultUseCaches() and setDefaultUseCaches():
              *
              * public void    setDefaultUseCaches(boolean useCaches)
              * public boolean getDefaultUseCaches()
              *
              * Although nonstatic, these methods do set and get a static field that determines the default behavior for
              * all instances of the URLConnection class created after the change. The next code fragment disables caching
              * by default; after this code runs, URLConnections that want caching must enable it explicitly using setUseCaches(true):
              */
            if (uc.getDefaultUseCaches()) {
                uc.setDefaultUseCaches(false);
            }

            /**
             * Timeouts
             *
             * Four methods query and modify the timeout values for connections; that is, how long the underlying socket
             * will wait for a response from the remote end before throwing a SocketTimeoutException. These are:
             *
             * public void setConnectTimeout(int timeout)
             * public int  getConnectTimeout()
             * public void setReadTimeout(int timeout)
             * public int  getReadTimeout()
             *
             * The setConnectTimeout()/getConnectTimeout() methods control how long the socket waits for the initial connection.
             * The setReadTimeout()/getReadTimeout() methods control how long the input stream waits for data to arrive.
             * All four methods measure timeouts in milliseconds. All four interpret zero as meaning never time out.
             * Both setter methods throw an IllegalArgumentException if the timeout is negative.
             *
             * For example, this code fragment requests a 30-second connect timeout and a 45-second read timeout:
             */
             URL u6 = new URL("http://www.example.org");
             URLConnection uc6 = u.openConnection();
             uc6.setConnectTimeout(30000);
             uc6.setReadTimeout(45000);
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
}