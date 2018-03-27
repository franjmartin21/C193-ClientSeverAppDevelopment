package c5;
import java.net.*;

/**
 * Equality and Comparison
 *
 * The URL class contains the usual equals() and hashCode() methods. These behave almost as you'd expect. Two URLs are
 * considered equal if and only if both URLs point to the same resource on the same host, port, and path, with the same
 * fragment identifier and query string. However there is one surprise here. The equals() method actually tries to resolve
 * the host with DNS so that, for example, it can tell that http://www.ibiblio.org/ and http://ibiblio.org/ are the same.
 *
 *  Warning
 *  This means that equals() on a URL is potentially a blocking I/O operation! For this reason, you should avoid storing URLs
 *  in data structure that depend on equals() such as java.util.HashMap. Prefer java.net.URI for this, and convert back and
 *  forth from URIs to URLs when necessary.
 *
 *  On the other hand, equals() does not go so far as to actually compare the resources identified by two URLs. For example,
 *  http://www.oreilly.com/ is not equal to http://www.oreilly.com/index.html; and http://www.oreilly.com:80 is not equal
 *  to http://www.oreilly.com/.
 */
public class URLEquality
{
    public static void main (String[] args)
    {
        try
        {
            URL www = new URL ("http://www.ibiblio.org/");
            URL ibiblio = new URL("http://ibiblio.org/");
            if (ibiblio.equals(www))
            {
                System.out.println(ibiblio + " is the same as " + www);
            }
            else
            {
                System.out.println(ibiblio + " is not the same as " + www);
            }
        }
        catch (MalformedURLException ex)
        {
            System.err.println(ex);
        }

        /**
         * URL does not implement Comparable.
         *
         * The URL class also has a sameFile() method that checks whether two URLs point to the same resource:
         *
         * public boolean sameFile(URL other)
         * The comparison is essentially the same as with equals(), DNS queries included, except that sameFile() does not
         * consider the fragment identifier. This sameFile() returns true when comparing http://www.oreilly.com/index.html#p1
         * and http://www.oreilly.com/index.html#q2 while equals() would return false.
         */
        try {
            URL u1 = new URL("http://www.ncsa.uiuc.edu/HTMLPrimer.html#GS");
            URL u2 = new URL("http://www.ncsa.uiuc.edu/HTMLPrimer.html#HD");
            if (u1.sameFile(u2)) {
                System.out.println(u1 + " is the same file as \n" + u2);
            } else {
                System.out.println(u1 + " is not the same file as \n" + u2);
            }
        }catch (MalformedURLException e){
            System.err.println(e);
        }
        /**
         * Conversion
         *
         * URL has three methods that convert an instance to another form: toString(), toExternalForm(), and toURI().
         *
         * Like all good classes, java.net.URL has a toString() method. The String produced by toString() is always an absolute
         * URL, such as http://www.cafeaulait.org/javatutorial.html. It's uncommon to call toString() explicitly. Print
         * statements call toString() implicitly. Outside of print statements, it's more proper to use toExternalForm() instead:
         *
         * public String toExternalForm()
         * The toExternalForm() method converts a URL object to a string that can be used in an HTML link or a web browser's
         * Open URL dialog.
         * The toExternalForm() method returns a human-readable String representing the URL. It is identical to the toString()
         * method. In fact, all the toString() method does is return toExternalForm().
         *
         * Finally, the toURI() method converts a URL object to an equivalent URI object:
         *
         * public URI toURI() throws URISyntaxException
         * We'll take up the URI class shortly. In the meantime, the main thing you need to know is that the URI class provides
         * much more accurate, specification-conformant behavior than the URL class. For operations like absolutization and
         * encoding, you should prefer the URI class where you have the option. You should also prefer the URI class if you
         * need to store URLs in a hashtable or other data structure, since its equals() method is not blocking. The URL
         * class should be used primarily when you want to download content from a server.
         */
    }
}

