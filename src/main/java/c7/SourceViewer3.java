package c7;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * HTTP 1.0 defined 16 response codes. HTTP 1.1 expanded this to 40 different codes. Although some numbers, notably 404,
 * have become slang almost synonymous with their semantic meaning, most of them are less familiar. The HttpURLConnection
 * \class includes 36 named constants such as HttpURLConnection.OK and HttpURLConnection.NOT_FOUND representing the most
 * common response codes. These are summarized in Table 6-1. Example 7-16 is a revised source viewer program that now includes
 * the response message.
 */
public class SourceViewer3
{
    public static void main (String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            try
            {
                // Open the URLConnection for reading
                URL u = new URL(args[i]);
                HttpURLConnection uc = (HttpURLConnection) u.openConnection();
                int code = uc.getResponseCode();
                String response = uc.getResponseMessage();
                System.out.println("HTTP/1.x " + code + " " + response);
                for (int j = 1; ; j++)
                {
                    String header = uc.getHeaderField(j);
                    String key = uc.getHeaderFieldKey(j);
                    if (header == null || key == null) break;
                    System.out.println(uc.getHeaderFieldKey(j) + ": " + header);
                }
                System.out.println();
                try (InputStream in = new BufferedInputStream(uc.getInputStream()))
                {
                    // chain the InputStream to a Reader
                    Reader r = new InputStreamReader(in);
                    int c;
                    while ((c = r.read()) != -1)
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
}