package c7;
import c5.QueryString;

import java.io.*;
import java.net.*;

/**
 * FormPoster that uses the URLConnection class and the QueryString class from Lesson 5 to post form data. The constructor
 * sets the URL. The query string is built using the add() method. The post() method actually sends the data to the server
 * by opening a URLConnection to the specified URL, setting its doOutput field to true, and writing the query string on the
 * output stream. It then returns the input stream containing the server's response.
 *
 * The main() method is a simple test for this program that sends the name "Elliotte Rusty Harold" and the email address
 * elharo@biblio.org to the resource at http://www.cafeaulait.org/books/jnp4/postquery.phtml. This resource is a simple
 * form tester that accepts any input using either the POST or GET method and returns an HTML page showing the names and
 * values that were submitted. The data returned is HTML;
 */
public class FormPoster
{
    private URL url;
    // from Lesson 5, Example 5-8
    private QueryString query = new QueryString();

    public FormPoster (URL url)
    {
        if (!url.getProtocol().toLowerCase().startsWith("http"))
        {
            throw new IllegalArgumentException("Posting only works for http URLs");
        }
        this.url = url;
    }
    public void add(String name, String value)
    {
        query.add(name, value);
    }
    public URL getURL()
    {
        return this.url;
    }
    public InputStream post() throws IOException
    {
        // open the connection and prepare it to POST
        URLConnection uc = url.openConnection();
        uc.setDoOutput(true);
        try (OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream(), "UTF-8"))
        {
            // The POST line, the Content-type header,
            // and the Content-length headers are sent by the URLConnection.
            // We just need to send the data
            out.write(query.toString());
            out.write("\r\n");
            out.flush();
        }
        // Return the response
        return uc.getInputStream();
    }
    public static void main(String[] args)
    {
        URL url;
        if (args.length > 0)
        {
            try
            {
                url = new URL(args[0]);
            }
            catch (MalformedURLException ex)
            {
                System.err.println("Usage: java FormPoster url");
                return;
            }
        }
        else
        {
            try
            {
                url = new URL("http://www.cafeaulait.org/books/jnp4/postquery.phtml");
            }
            catch (MalformedURLException ex)
            {
                // shouldn't happen
                System.err.println(ex);
                return;
            }
        }
        FormPoster poster = new FormPoster(url);
        poster.add("name", "Elliotte Rusty Harold");
        poster.add("email", "elharo@ibiblio.org");
        try (InputStream in = poster.post())
        {
            // Read the response
            Reader r = new InputStreamReader(in);
            int c;
            while((c = r.read()) != -1)
            {
                System.out.print((char) c);
            }
            System.out.println();
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
}