package c5;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlExample {
    /**
     * CREATING NEW URLs
     *
     * Unlike the InetAddress objects in Lesson 4, you can construct instances of java.net.URL. The constructors differ
     * in the information they require:
     *
     * public URL(String url) throws MalformedURLException
     * public URL(String protocol, String hostname, String file) throws MalformedURLException
     * public URL(String protocol, String host, int port, String file) throws MalformedURLException
     * public URL(URL base, String relative) throws MalformedURLException
     *
     * Other than verifying that it recognizes the URL scheme, Java does not check the correctness of the URLs it constructs.
     * The programmer is responsible for making sure that URLs created are valid. For instance, Java does not check that the
     * hostname in an HTTP URL does not contain spaces or that the query string is x-www-form-URL-encoded. It does not check
     * that a mailto URL actually contains an email address. You can create URLs for hosts that don't exist and for hosts
     * that do exist but that you won't be allowed to connect to.
     */
    public static void main(String[] args) {

        try
        {
            long l= 1L;
            URL u = new URL("http://www.audubon.org/");
        }
        catch (MalformedURLException ex)
        {
            System.err.println(ex);
        }

        /**
         * Constructing URL from its component parts
         */
        try
        {
            URL u = new URL("http", "www.eff.org", "/blueribbon.html#intro");
        }
        catch (MalformedURLException ex)
        {
            throw new RuntimeException("shouldn't happen; all VMs recognize http");
        }
        /**
         * For the rare occasions when the default port isn't correct, the next constructor lets you specify the port
         * explicitly as an int.
         */
        try
        {
            URL u = new URL("http", "fourier.dur.ac.uk", 8000, "/~dma3mjh/jsci/");
        }
        catch (MalformedURLException ex)
        {
            throw new RuntimeException("shouldn't happen; all VMs recognize http");
        }

        /**
         * Constructing relative URLs
         */
        try
        {
            URL u1 = new URL("http://www.ibiblio.org/javafaq/index.html");
            URL u2 = new URL (u1, "mailinglists.html");
        }
        catch (MalformedURLException ex)
        {
            System.err.println(ex);
        }

        /**
         * OTHER CLASSES THAT RETURN URLS ARE
         *
         * In applets, getDocumentBase() returns the URL of the page that contains the applet and getCodeBase() returns
         * the URL of the applet .class file.
         *
         * The java.io.File class has a toURL() method that returns a file URL matching the given file.
         *
         * Class loaders are used not only to load classes but also to load resources such as images and audio files.
         * The static ClassLoader.getSystemResource(String name) method returns a URL from which a single resource can be read.
         * The ClassLoader.getSystemResources(String name) method returns an Enumeration containing a list of URLs from
         * which the named resource can be read.
         * The instance method getResource(String name) searches the path used by the referenced class loader for a URL
         * to the named resource
         *
         * the getPage() method of javax.swing.JEditorPane and the getURL() method of java.net.URLConnection.
         */


    }
}
