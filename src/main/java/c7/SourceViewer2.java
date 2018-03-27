package c7;
import java.io.*;
import java.net.*;
public class SourceViewer2
{
    /**
     * The following is the minimal set of steps needed to retrieve data from a URL using a URLConnection object:
     * Construct a URL object.
     * Invoke the URL object's openConnection() method to retrieve a URLConnection object for that URL.
     * Invoke the URLConnection's getInputStream() method.
     * Read from the input stream using the usual stream API.
     * The getInputStream() method returns a generic InputStream that lets you read and parse the data that the server sends.
     * Example 7-1 uses the getInputStream() method to download a web page.
     * @param args
     */
    public static void main (String[] args)
    {
        if  (args.length > 0)
        {
            try
            {
                // Open the URLConnection for reading
                URL u = new URL(args[0]);
                URLConnection uc = u.openConnection();
                try (InputStream raw = uc.getInputStream())
                {     // autoclose
                    InputStream buffer = new BufferedInputStream(raw);
                    // chain the InputStream to a Reader
                    Reader reader = new InputStreamReader(buffer);
                    int c;
                    while ((c = reader.read()) != -1)
                    {
                        System.out.print((char) c);
                    }
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
    }
    /**
     * The differences between URL and URLConnection aren't apparent with just a simple input stream as in this example.
     * The biggest differences between the two classes are:
     * URLConnection provides access to the HTTP header.
     * URLConnection can configure the request parameters sent to the server.
     * URLConnection can write data to the server as well as read data from the server.
     */
}