package c5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncoderTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        try
        {
            System.out.println(URLEncoder.encode("This string has spaces", "UTF-8"));
            System.out.println(URLEncoder.encode("This*string*has*asterisks", "UTF-8"));
            System.out.println(URLEncoder.encode("This%string%has%percent%signs","UTF-8"));
            System.out.println(URLEncoder.encode("This+string+has+pluses","UTF-8"));
            System.out.println(URLEncoder.encode("This/string/has/slashes", "UTF-8"));
            System.out.println(URLEncoder.encode("This\"string\"has\"quote\"marks", "UTF-8"));
            System.out.println(URLEncoder.encode("This:string:has:colons", "UTF-8"));
            System.out.println(URLEncoder.encode("This~string~has~tildes", "UTF-8"));
            System.out.println(URLEncoder.encode("This(string)has(parentheses)", "UTF-8"));
            System.out.println(URLEncoder.encode("This.string.has.periods", "UTF-8"));
            System.out.println(URLEncoder.encode("This=string=has=equals=signs", "UTF-8"));
            System.out.println(URLEncoder.encode("This&string&has&ampersands", "UTF-8"));
            System.out.println(URLEncoder.encode("Thiséstringéhasénon-ASCII characters", "UTF-8"));

            /**
             * This code fragment encodes it:
             */
            String query = URLEncoder.encode("https://www.google.com/search?hl=en&as_q=Java&as_epq=I/O", "UTF-8");
            System.out.println(query);
            /*
            Unfortunately, the output is:
            https%3A%2F%2Fwww.google.com%2Fsearch%3Fhl%3Den%26as_q%3DJava%26as_epq%3DI%2FO
             */

            /**
             * The problem is that URLEncoder.encode() encodes blindly. It can't distinguish between special characters
             * used as part of the URL or query string, like / and =, and characters that need to be encoded. Consequently,
             * URLs need to be encoded a piece at a time like this:
             */
            String url = "https://www.google.com/search?";
            url += URLEncoder.encode("hl", "UTF-8");
            url += "=";
            url += URLEncoder.encode("en", "UTF-8");
            url += "&";
            url += URLEncoder.encode("as_q", "UTF-8");
            url += "=";
            url += URLEncoder.encode("Java", "UTF-8");
            url += "&";
            url += URLEncoder.encode("as_epq", "UTF-8");
            url += "=";
            url += URLEncoder.encode("I/O", "UTF-8");
            System.out.println(url);


            /**
             * Using QueryString class to format previous example
             */
            QueryString qs = new QueryString();
            qs.add("hl", "en");
            qs.add("as_q", "Java");
            qs.add("as_epq", "I/O");
            String url2 = "http://www.google.com/search?" + qs;
            System.out.println(url2);

        }
        catch (UnsupportedEncodingException ex)
        {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }
}
