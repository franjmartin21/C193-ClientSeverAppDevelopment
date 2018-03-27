package c5;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Retrieving Data from a URL
 *
 * public InputStream openStream() throws IOException
 * public URLConnection openConnection() throws IOException
 * public URLConnection openConnection(Proxy proxy) throws IOException
 * public Object getContent() throws IOException
 * public Object getContent(Class[] classes) throws IOException
 *
 * most basic and most commonly used of these methods is openStream(), which returns an InputStream from which you can read
 * the data. If you need more control over the download process, call openConnection() instead.
 * Finally, you can ask the URL for its content with getContent() which may give you a more complete object such as String
 * or an Image. Then again, it may just give you an InputStream anyway.
 */
public class RetrievingDataUrlExample {
    public static void main(String[] args) {
        try {
            System.out.println("*** URL Open Stream");
            URL u = new URL("http://www.lolcats.com");
            InputStream in = u.openStream();
            int c;
            while ((c = in.read()) != -1) System.out.write(c);
            in.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }

        /**
         * We need a better way to close the stream in case of exception
         * Since Java 7 we can use try-with-resources
         */
        try {
            System.out.println("*** URL Open Stream, try-with-resources");
            URL u = new URL("http://www.lolcats.com");
            try (InputStream in = u.openStream()) {
                int c;
                while ((c = in.read()) != -1) System.out.write(c);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }

        /**
         * public final Object getContent() throws IOException
         *
         * The getContent() method is the third way to download data referenced by a URL. The getContent() method retrieves
         * the data referenced by the URL and tries to make it into some type of object. If the URL refers to some kind of
         * text such as an ASCII or HTML file, the object returned is usually some sort of InputStream. If the URL refers
         * to an image such as a GIF or a JPEG file, getContent() usually returns a java.awt.ImageProducer
         */
        try {
            System.out.println("*** getContent()");
            URL u = new URL("http://mesola.obspm.fr/");
            Object o = u.getContent();
        } catch (IOException ex) {
            System.err.println(ex);
        }

        /**
         * public final Object getContent(Class[] classes) throws IOException
         *
         * A URL's content handler may provide different views of a resource. This overloaded variant of the getContent()
         * method lets you choose which class you'd like the content to be returned as. The method attempts to return the
         * URL's content in the first available format. For instance, if you prefer an HTML file to be returned as a String,
         * but your second choice is a Reader and your third choice is an InputStream, write:
         */
        try {
            System.out.println("*** Using getContent to decide the right way to handle it");
            URL u = new URL("http://www.lolcats.com");
            Class<?>[] types = new Class[3];
            types[0] = String.class;
            types[1] = Reader.class;
            types[2] = InputStream.class;
            Object o = u.getContent(types);

            /**
             * If the content handler knows how to return a string representation of the resource, then it returns a String.
             * If it doesn't know how to return a string representation of the resource, then it returns a Reader. And if it
             * doesn't know how to present the resource as a reader, then it returns an InputStream. You have to test for the
             * type of the returned object using instanceof.
             */
            if (o instanceof String)
            {
                System.out.println(o);
            }
            else if (o instanceof Reader)
            {
                int c;
                Reader r = (Reader) o;
                while ((c = r.read()) != -1) System.out.print((char) c);
                r.close();
            }
            else if (o instanceof InputStream)
            {
                int c;
                InputStream in = (InputStream) o;
                while ((c = in.read()) != -1) System.out.write(c);
                in.close();
            }
            else
            {
                System.out.println("Error: unexpected type " + o.getClass());
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
