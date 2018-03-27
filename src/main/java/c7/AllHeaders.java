package c7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * public String getHeaderField(String name)
 *
 * The getHeaderField() method returns the value of a named header field. The name of the header is not case sensitive and
 * does not include a closing colon. For example, to get the value of the Content-type and Content-encoding header fields
 * of a URLConnection object uc, you could write:
 *
 * String contentType = uc.getHeaderField("content-type");
 * String contentEncoding = uc.getHeaderField("content-encoding"));
 *
 *
 * public String getHeaderFieldKey(int n)
 *
 * This method returns the key (i.e., the field name) of the nth header field (e.g., Content-length or Server). The request
 * method is header zero and has a null key. The first header is one. For example, in order to get the sixth key of the
 * header of the URLConnection uc, you would write:
 *
 * String header6 = uc.getHeaderFieldKey(6);
 *
 *
 * public String getHeaderField(int n)
 *
 * This method returns the value of the nth header field. In HTTP, the starter line containing the request method and path
 * is header field zero and the first actual header is one. Example 7-5 uses this method in conjunction with getHeaderFieldKey()
 * to print the entire HTTP header.
 */
public class AllHeaders
{
    public static void main(String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            try
            {
                URL u = new URL(args[i]);
                URLConnection uc = u.openConnection();
                for (int j = 1; ; j++)
                {
                    String header = uc.getHeaderField(j);
                    if (header == null)
                        break;
                    System.out.println(uc.getHeaderFieldKey(j) + ": " + header);
                }
            }
            catch (MalformedURLException ex)
            {
                System.err.println(args[i] + " is not a URL I understand.");
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
            System.out.println();
        }
    }
}
/**
 * public long getHeaderFieldDate(String name, long default)
 * This method first retrieves the header field specified by the name argument and tries to convert the string to a long
 * that specifies the milliseconds since midnight, January 1, 1970, GMT. getHeaderFieldDate() can be used to retrieve a
 * header field that represents a date (e.g., the Expires, Date, or Last-modified headers). To convert the string to an
 * integer, getHeaderFieldDate() uses the parseDate() method of java.util.Date. The parseDate() method does a decent job
 * of understanding and converting most common date formats, but it can be stumped—for instance, if you ask for a header
 * field that contains something other than a date. If parseDate() doesn't understand the date or if getHeaderFieldDate()
 * is unable to find the requested header field, getHeaderFieldDate() returns the default argument. For example:
 *
 * Date expires = new Date(uc.getHeaderFieldDate("expires", 0));
 * long lastModified = uc.getHeaderFieldDate("last-modified", 0);
 * Date now = new Date(uc.getHeaderFieldDate("date", 0));
 *
 * You can use the methods of the java.util.Date class to convert the long to a String.
 *
 *
 * public int getHeaderFieldInt(String name, int default)
 * This method retrieves the value of the header field name and tries to convert it to an int. If it fails, either because
 * it can't find the requested header field or because that field does not contain a recognizable integer, getHeaderFieldInt()
 * returns the default argument. This method is often used to retrieve the Content-length field. For example, to get the
 * content length from a URLConnection uc, you would write:
 *
 * int contentLength = uc.getHeaderFieldInt("content-length", -1);
 *
 * In this code fragment, getHeaderFieldInt() returns -1 if the Content-length header isn't present.
 */
