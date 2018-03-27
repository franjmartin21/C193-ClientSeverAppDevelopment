package c7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlConnectionExample {
    /**
     * The single constructor for the URLConnection class is protected:
     *
     * protected URLConnection(URL url)
     */
    public static void main(String[] args) {
        try
        {
            URL u = new URL("http://www.overcomingbias.com/");
            URLConnection uc = u.openConnection();
            // read from the URL...
        }
        catch (MalformedURLException ex)
        {
            System.err.println(ex);
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
    /**
     * URLConnection class is declared abstract. However, all but one of its methods are implemented. You may find it
     * convenient or necessary to override other methods in the class; but the single method that subclasses must implement
     * is connect(), which makes a connection to a server and thus depends on the type of service (HTTP, FTP, and so on)
     */

    /**
     * public abstract void connect() throws IOException
     *
     * When a URLConnection is first constructed, it is unconnected; that is, the local and remote host cannot send and
     * receive data. There is no socket connecting the two hosts. The connect() method establishes a connection—normally
     * using TCP sockets but possibly through some other mechanism—between the local and remote host so they can send and
     * receive data. However, getInputStream(), getContent(), getHeaderField(), and other methods that require an open
     * connection will call connect() if the connection isn't yet open. Therefore, you rarely need to call connect() directly.
     */
}
