package c7;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class WritingDataServerExample {
    public static void main(String[] args) {
        /**
         * A URLConnection doesn't allow output by default, so you have to call setDoOutput(true) before asking for an
         * output stream. When you set doOutput to true for an http URL, the request method is changed from GET to POST.
         * In Lesson 5, you saw how to send data to server-side programs with GET. However, GET should be limited to safe
         * operations, such as search requests or page navigation, and not used for unsafe operations that create or modify
         * a resource, such as posting a comment on a web page or ordering a pizza. Safe operations can be bookmarked,
         * cached, spidered, prefetched, and so on. Unsafe operations should not be.
         * Once you have an OutputStream, buffer it by chaining it to a BufferedOutputStream or a BufferedWriter. You may
         * also chain it to a DataOutputStream, an OutputStreamWriter, or some other class that's more convenient to use
         * than a raw OutputStream. For example
         */
        try
        {
            URL u = new URL("http://www.somehost.com/cgi-bin/acgi");
            // open the connection and prepare it to POST
            URLConnection uc = u.openConnection();
            uc.setDoOutput(true);
            OutputStream raw = uc.getOutputStream();
            OutputStream buffered = new BufferedOutputStream(raw);
            OutputStreamWriter out = new OutputStreamWriter(buffered, "8859_1");
            out.write("first=Julie&middle=&last=Harting&work=String+Quartet\r\n");
            out.flush();
            out.close();
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
        /**
         * Sending data with POST is almost as easy as with GET. Invoke setDoOutput(true) and use the URLConnection's
         * getOutputStream() method to write the query string rather than attaching it to the URL. Java buffers all the
         * data written onto the output stream until the stream is closed. This enables it to calculate the value for
         * the Content-length header. The complete transaction, including client request and server response.
         */


    }
}
