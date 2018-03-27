package c5;

import java.net.URI;
import java.net.URISyntaxException;

public class URIExample {
    public static void main(String[] args)
    {
        /**
         * Constructing a URI
         *
         * public URI(String uri) throws URISyntaxException
         * public URI(String scheme, String schemeSpecificPart, String fragment) throws URISyntaxException
         * public URI(String scheme, String host, String path, String fragment) throws URISyntaxException
         * public URI(String scheme, String authority, String path, String query, String fragment) throws URISyntaxException
         * public URI(String scheme, String userInfo, String host, int port, String path, String query, String fragment) throws URISyntaxException
         */

        /**
         * The first constructor creates a new URI object from any convenient string
         */
        try {
            URI voice = new URI("tel:+1-800-9988-9938");
            URI web = new URI("http://www.xml.com/pub/a/2003/09/17/stax.html#id=_hbc");
            URI book = new URI("urn:isbn:1-565-92870-9");
            System.out.println("All correct URI");
        } catch(URISyntaxException ex){
            System.out.println("Incorrect URI");
        }

        try {
            URI web = new URI(";http://www.xml.com/pub/a/2003/09/17/stax.html#id=_hbc");
        } catch(URISyntaxException ex){
            System.out.println("Incorrect URI");
        }

        /**
         * The second constructor that takes a scheme specific part is mostly used for nonhierarchical URIs. The scheme is
         * the URI's protocol, such as http, urn, tel, and so forth. It must be composed exclusively of ASCII letters and
         * digits and the three punctuation characters +, -, and .. It must begin with a letter.
         */
        try {
            URI absolute = new URI("http", "//www.ibiblio.org", null);
            URI relative = new URI(null, "/javafaq/index.shtml", "today");
        } catch(URISyntaxException ex){
            System.out.println("Incorrect URI");
        }

        /**
         * The third constructor is used for hierarchical URIs such as http and ftp URLs. The host and path together (separated
         * by a /) form the scheme-specific part for this URI. For example:
         */
        try {
            URI today= new URI("http", "www.ibiblio.org", "/javafaq/index.html", "today");
        } catch(URISyntaxException ex){
            System.out.println("Incorrect URI");
        }
        /*
        This produces the URI http://www.ibiblio.org/javafaq/index.html#today.
         */

        /**
         * The fourth constructor is basically the same as the third, with the addition of a query string. For example:
         */
        try {
            URI today = new URI("http", "www.ibiblio.org", "/javafaq/index.html", "referrer=cnet&date=2014-02-23",  "today");
        } catch(URISyntaxException ex){
            System.out.println("Incorrect URI");
        }

        /**
         * The fifth constructor is the master hierarchical URI constructor that the previous two invoke. It divides the
         * authority into separate user info, host, and port parts, each of which has its own syntax rules. For example:
         */
        try {
            URI styles = new URI("ftp", "anonymous:elharo@ibiblio.org","ftp.oreilly.com",  21, "/pub/stylesheet", null, null);
        } catch(URISyntaxException ex){
            System.out.println("Incorrect URI");
        }

        /**
         * URI.create()
         *
         * If you're sure your URIs are legal and do not violate any of the rules, you can use the static factory URI.create()
         * method instead. Unlike the constructors, it does not throw a URISyntaxException. For example, this invocation
         * creates a URI for anonymous FTP access using an email address as password:
         */
        URI styles = URI.create("ftp://anonymous:elharo%40ibiblio.org@ftp.oreilly.com:21/pub/stylesheet");

        /**
         * The Parts of the URI
         *
         * A URI reference has up to three parts: a scheme, a scheme-specific part, and a fragment identifier. The general format is:
         *
         * scheme:scheme-specific-part:fragment
         *
         * public String getScheme()
         * public String getSchemeSpecificPart()
         * public String getRawSchemeSpecificPart()
         * public String getFragment()
         * public String getRawFragment()
         */
        System.out.println(styles.getScheme());
        System.out.println(styles.getSchemeSpecificPart());
        System.out.println(styles.getRawSchemeSpecificPart());
        System.out.println(styles.getFragment());
        System.out.println(styles.getRawFragment());
        /*
        These methods all return null if the particular URI object does not have the relevant component: for example, a
        relative URI without a scheme or an http URI without a fragment identifier.
         */
        /**
         * isAbsolute()
         * A URI that has a scheme is an absolute URI. A URI without a scheme is relative. The isAbsolute() method returns true if the URI is absolute, false if it's relative:
         */
        System.out.println(styles.isAbsolute());

        /**
         * isOpaque()
         * The isOpaque() method returns false if the URI is hierarchical, true if it's not hierarchical—that is, if it's opaque:
         */
        System.out.println(styles.isOpaque());

        /**
         * public String getAuthority()
         * public String getFragment()
         * public String getHost()
         * public String getPath()
         * public String getPort()
         * public String getQuery()
         * public String getUserInfo()
         *
         * These methods all return the decoded parts; in other words, percent escapes, such as %3C, are changed into the
         * characters they represent, such as <. If you want the raw, encoded parts of the URI, there are five parallel
         * getRaw_Foo_() methods:
         *
         * public String getRawAuthority()
         * public String getRawFragment()
         * public String getRawPath()
         * public String getRawQuery()
         * public String getRawUserInfo()
         */


        /**
         * For various technical reasons that don't have a lot of practical impact, Java can't always initially detect
         * syntax errors in the authority component. The immediate symptom of this failing is normally an inability to
         * return the individual parts of the authority, port, host, and user info. In this event, you can call parseServerAuthority()
         * to force the authority to be reparsed:
         * public URI parseServerAuthority() throws URISyntaxException>
         *
         * The original URI does not change (URI objects are immutable), but the URI returned will have separate authority
         * parts for user info, host, and port. If the authority cannot be parsed, a URISyntaxException is thrown.
         */

        /**
         * Resolving Relative URIs
         *
         * The URI class has three methods for converting back and forth between relative and absolute URIs:
         *
         * public URI resolve(URI uri)
         * public URI resolve(String uri)
         * public URI relativize(URI uri)
         */
        try {
            URI absolute = new URI("http://www.example.com/");
            URI relative = new URI("images/logo.png");
            URI resolved = absolute.resolve(relative);
            System.out.println("Resolved: " + resolved);
        } catch(URISyntaxException e){
            System.out.println("Incorrect URI");
        }

        /**
         * If the invoking URI does not contain an absolute URI itself, the resolve() method resolves as much of the URI
         * as it can and returns a new relative URI object as a result. For example, take these three statements:
         */
        try {
            URI top = new URI("javafaq/books/");
            URI resolved = top.resolve("jnp3/examples/07/index.html");
            System.out.println("Resolved: " + resolved);
        } catch(URISyntaxException e){
            System.out.println("Incorrect URI");
        }
        /*
        After they've executed, resolved now contains the relative URI javafaq/books/jnp3/examples/07/index.html with no scheme or authority.
        */

        /**
         * It's also possible to reverse this procedure; that is, to go from an absolute URI to a relative one. The relativize()
         * method creates a new URI object from the uri argument that is relative to the invoking URI. The argument is not changed. For example:
         */
        try {
            URI absolute = new URI("http://www.example.com/images/logo.png");
            URI top = new URI("http://www.example.com/");
            URI relative = top.relativize(absolute);
            System.out.println("Relativiced: " + relative);
        } catch(URISyntaxException e){
            System.out.println("Incorrect URI");
        }


        /**
         * Equality and Comparison
         *
         * URIs are tested for equality pretty much as you'd expect. It's not quite direct string comparison.
         * - Equal URIs must both either be hierarchical or opaque.
         * - The scheme and authority parts are compared without considering case. That is, http and HTTP are the same scheme
         * - www.example.com is the same authority as www.EXAMPLE.com.
         * - The rest of the URI is case sensitive, except for hexadecimal digits used to escape illegal characters.
         * - Escapes are not decoded before comparing. http://www.example.com/A and http://www.example.com/%41 are unequal URIs.
         *
         * The hashCode() method is consistent with equals. Equal URIs do have the same hash code and unequal URIs are fairly
         * unlikely to share the same hash code.
         *
         * The hashCode() method is consistent with equals. Equal URIs do have the same hash code and unequal URIs are fairly unlikely to share the same hash code.
         * - If the schemes are different, the schemes are compared, without considering case.
         * - Otherwise, if the schemes are the same, a hierarchical URI is considered to be less than an opaque URI with the same scheme.
         * - If both URIs are opaque URIs, they're ordered according to their scheme-specific parts.
         * - If both the scheme and the opaque scheme-specific parts are equal, the URIs are compared by their fragments.
         * - If both URIs are hierarchical, they're ordered according to their authority components, which are themselves
         *   ordered according to user info, host, and port, in that order. Hosts are case insensitive.
         * - If the paths are also equal, the query strings are compared.
         * - If the query strings are equal, the fragments are compared.
         */

        /**
         * String Representations
         *
         * public String toString()
         * public String toASCIIString()
         *
         * The toString() method returns an unencoded string form of the URI (i.e., characters like é and \ are not percent escaped).
         * Therefore, the result of calling this method is not guaranteed to be a syntactically correct URI, though it is in
         * fact a syntactically correct IRI. This form is sometimes useful for display to human beings, but usually not for retrieval.
         *
         * The toASCIIString() method returns an encoded string form of the URI. Characters like é and \ are always percent escaped
         * whether or not they were originally escaped. This is the string form of the URI you should use most of the time. Even if
         * the form returned by toString() is more legible for humans, they may still copy and paste it into areas that are not
         * expecting an illegal URI. toASCIIString() always returns a syntactically correct URI.
         */

    }
}
