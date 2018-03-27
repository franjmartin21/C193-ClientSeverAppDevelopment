package c7;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * public String getContentEncoding()
 *
 * The getContentEncoding() method returns a String that tells you how the content is encoded. If the content is sent unencoded
 * (as is commonly the case with HTTP servers), this method returns null. It throws no exceptions. The most commonly used
 * content encoding on the Web is probably x-gzip, which can be straightforwardly decoded using a java.util.zip.GZipInputStream.
 *
 *
 * public long getDate()
 *
 * The getDate() method returns a long that tells you when the document was sent, in milliseconds since midnight,
 * Greenwich Mean Time (GMT), January 1, 1970. You can convert it to a java.util.Date. For example:
 * Date documentSent = new Date(uc.getDate());
 * This is the time the document was sent as seen from the server; it may not agree with the time on your local machine.
 * If the HTTP header does not include a Date field, getDate() returns 0.
 *
 *
 * public long getExpiration()
 * Some documents have server-based expiration dates that indicate when the document should be deleted from the cache and
 * reloaded from the server. getExpiration() is very similar to getDate(), differing only in how the return value is
 * interpreted. It returns a long indicating the number of milliseconds after 12:00 A.M., GMT, January 1, 1970, at which
 * the document expires. If the HTTP header does not include an Expiration field, getExpiration() returns 0, which means
 * that the document does not expire and can remain in the cache indefinitely.
 *
 *
 * public long getLastModified()
 * The final date method, getLastModified(), returns the date on which the document was last modified. Again, the date is
 * given as the number of milliseconds since midnight, GMT, January 1, 1970. If the HTTP header does not include a
 * Last-modified field (and many don't), this method returns 0.
 * Example 7-4 reads URLs from the command line and uses these six methods to print their content type, content length,
 * content encoding, date of last modification, expiration date, and current date.
 */
public class HeaderViewer
{
    public static void main(String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            try
            {
                URL u = new URL(args[0]);
                URLConnection uc = u.openConnection();
                System.out.println("Content-type: " + uc.getContentType());
                if (uc.getContentEncoding() != null)
                {
                    System.out.println("Content-encoding: " + uc.getContentEncoding());
                }
                if (uc.getDate() != 0)
                {
                    System.out.println("Date: " + new Date(uc.getDate()));
                }
                if (uc.getLastModified() != 0)
                {
                    System.out.println("Last modified: " + new Date(uc.getLastModified()));
                }
                if (uc.getExpiration() != 0)
                {
                    System.out.println("Expiration date: " + new Date(uc.getExpiration()));
                }
                if (uc.getContentLength() != -1)
                {
                    System.out.println("Content-length: " + uc.getContentLength());
                }
            }
            catch (MalformedURLException ex)
            {
                System.err.println(args[i] + " is not a URL I understand");
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
            System.out.println();
        }
    }
}
