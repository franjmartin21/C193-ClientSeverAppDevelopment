package c5;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlConnectionExample {
    public static void main(String[] args) {
        /**
         * public URLConnection openConnection() throws IOException
         *
         * The openConnection() method opens a socket to the specified URL and returns a URLConnection object.
         * A URLConnection represents an open connection to a network resource. If the call fails, openConnection()
         * throws an IOException. For example:
         */
        try
        {
            URL u = new URL("https://news.ycombinator.com/");
            try
            {
                URLConnection uc = u.openConnection();
                InputStream in = uc.getInputStream();
                // read from the connection...
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
        catch (MalformedURLException ex)
        {
            System.err.println(ex);
        }
        /**
         * The URLConnection gives you access to everything sent by the server: in addition to the document itself in its
         * raw form (e.g., HTML, plain text, binary image data), you can access all the metadata specified by the protocol.
         * For example, if the scheme is HTTP or HTTPS, the URLConnection lets you access the HTTP headers as well as the
         * raw HTML. The URLConnection class also lets you write data to as well as read from a URL-for instance, in order
         * to send email to a mailto URL or post form data. The URLConnection class will be the primary subject of Lesson 7.
         */

    }
}
