package c7;

public class GuessMIMEMediaTypes {
    public static void main(String[] args) {
        /**
         * If this were the best of all possible worlds, every protocol and every server would use standard MIME types to
         * correctly specify the type of file being transferred. Unfortunately, that's not the case. Not only do we have
         * to deal with older protocols such as FTP that predate MIME, but many HTTP servers that should use MIME don't
         * provide MIME headers at all or lie and provide headers that are incorrect (usually because the server has been misconfigured).
         *
         * The URLConnection class provides two static methods to help programs figure out the MIME type of some data;
         * you can use these if the content type just isn't available or if you have reason to believe that the content type
         * you're given isn't correct.
         *
         * The first of these is URLConnection.guessContentTypeFromName():
         *
         * This method tries to guess the content type of an object based upon the extension in the filename portion of
         * the object's URL. It returns its best guess about the content type as a String. This guess is likely to be
         * correct; people follow some fairly regular conventions when thinking up filenames.
         * The guesses are determined by the content-types.properties file, normally located in the jre/lib directory.
         * On Unix, Java may also look at the mailcap file to help it guess.
         * This method is not infallible by any means. For instance, it omits various XML applications such as RDF (.rdf),
         * XSL (.xsl), and so on that should have the MIME type application/xml. It also doesn't provide a MIME type for
         * CSS stylesheets (.css). However, it's a good start.
         *
         * The second MIME type guesser method is URLConnection.guessContentTypeFromStream():
         * public static String guessContentTypeFromStream(InputStream in)
         *
         * This method tries to guess the content type by looking at the first few bytes of data in the stream. For this
         * method to work, the InputStream must support marking so that you can return to the beginning of the stream
         * after the first bytes have been read. Java inspects the first 16 bytes of the InputStream, although sometimes
         * fewer bytes are needed to make an identification. These guesses are often not as reliable as the guesses made
         * by guessContentTypeFromName(). For example, an XML document that begins with a comment rather than an XML
         * declaration would be mislabeled as an HTML file. This method should be used only as a last resort.
         */
    }
}
