package c7;
import java.io.*;
import java.net.*;

/**
 * Retrieving Specific Header Fields
 *
 * The first six methods request specific, particularly common fields from the header. These are:
 * Content-type
 * Content-length
 * Content-encoding
 * Date
 * Last-modified
 * Expires
 *
 * public String getContentType()
 * The getContentType() method returns the MIME media type of the response body. It relies on the web server to send a
 * valid content type.
 * if the content type is some form of text, this header may also contain a character set part identifying the document's
 * character encoding. For example:
 * -- Content-type: text/html; charset=UTF-8
 * Or:
 * -- Content-Type: application/xml; charset=iso-2022-jp
 */
public class EncodingAwareSourceViewer
{
    public static void main (String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            try
            {
                // set default encoding
                String encoding = "ISO-8859-1";
                URL u = new URL(args[i]);
                URLConnection uc = u.openConnection();
                String contentType = uc.getContentType();
                int encodingStart = contentType.indexOf("charset=");
                if (encodingStart != -1)
                {
                    encoding = contentType.substring(encodingStart + 8);
                }
                InputStream in = new BufferedInputStream(uc.getInputStream());
                Reader r = new InputStreamReader(in, encoding);
                int c;
                while ((c = r.read()) != -1)
                {
                    System.out.print((char) c);
                }
                r.close();
            }
            catch (MalformedURLException ex)
            {
                System.err.println(args[0] + " is not a parseable URL");
            }
            catch (UnsupportedEncodingException ex)
            {
                System.err.println("Server sent an encoding Java does not support: " + ex.getMessage());
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
    }
}
