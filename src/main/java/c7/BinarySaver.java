package c7;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * public int getContentLength()
 *
 * The getContentLength() method tells you how many bytes there are in the content. If there is no Content-length header,
 * getContentLength() returns -1. The method throws no exceptions. It is used when you need to know exactly how many bytes
 * to read or when you need to create a buffer large enough to hold the data in advance.
 *
 * Lesson 5 showed how to use the openStream() method of the URL class to download text files from an HTTP server. Although
 * in theory you should be able to use the same method to download a binary file, such as a GIF image or a .class byte code
 * file, in practice this procedure presents a problem.
 * HTTP servers don't always close the connection exactly where the data is finished; therefore, you don't know when to stop
 * reading. To download a binary file, it is more reliable to use a URLConnection's getContentLength() method to find the file's
 * length, then read exactly the number of bytes indicated. This is a program that uses this technique to save a binary file
 * on a disk.
 */
public class BinarySaver
{
    public static void main (String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            try
            {
                URL root = new URL(args[i]);
                saveBinaryFile(root);
            }
            catch (MalformedURLException ex)
            {
                System.err.println(args[i] + " is not URL I understand.");
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
    }
    public static void saveBinaryFile(URL u) throws IOException
    {
        URLConnection uc = u.openConnection();
        String contentType = uc.getContentType();
        int contentLength = uc.getContentLength();
        if (contentType.startsWith("text/") || contentLength == -1 )
        {
            throw new IOException("This is not a binary file.");
        }
        try (InputStream raw = uc.getInputStream())
        {
            InputStream in  = new BufferedInputStream(raw);
            byte[] data = new byte[contentLength];
            int offset = 0;
            while (offset < contentLength)
            {
                int bytesRead = in.read(data, offset, data.length - offset);
                if (bytesRead == -1) break;
                offset += bytesRead;
            }
            if (offset != contentLength)
            {
                throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
            }
            String filename = u.getFile();
            filename = filename.substring(filename.lastIndexOf('/') + 1);
            try (FileOutputStream fout = new FileOutputStream(filename))
            {
                fout.write(data);
                fout.flush();
            }
        }
    }
}