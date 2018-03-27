import java.net.URI;

public class Example {

    public static void main(String[] args) {
        printUriInfo(URI.create("mailto:a@b.com"));
        printUriInfo(URI.create("http://example.com"));
        printUriInfo(URI.create("http://example.com/path"));
        printUriInfo(URI.create("scheme://example.com"));
        printUriInfo(URI.create("scheme:example.com"));
        printUriInfo(URI.create("scheme:example.com/path"));
        printUriInfo(URI.create("path"));
        printUriInfo(URI.create("/path"));
    }

    private static void printUriInfo(URI uri) {
        System.out.println(String.format("Uri [%s]", uri));
        System.out.println(String.format(" is %sopaque", uri.isOpaque() ? "" : "not "));
        System.out.println(String.format(" is %sabsolute", uri.isAbsolute() ? "" : "not "));
        System.out.println(String.format(" Path [%s]", uri.getPath()));
    }
}
