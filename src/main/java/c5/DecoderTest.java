package c5;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * The corresponding URLDecoder class has a static decode() method that decodes strings encoded in x-www-form-url-encoded
 * format. That is, it converts all plus signs to spaces and all percent escapes to their corresponding character:
 *
 * public static String decode(String s, String encoding) throws UnsupportedEncodingException
 *
 * If you have any doubt about which encoding to use, pick UTF-8. It's more likely to be correct than anything else.
 * An IllegalArgumentException should be thrown if the string contains a percent sign that isn't followed by two hexadecimal
 * digits or decodes into an illegal sequence.
 */
public class DecoderTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String input = "https://www.google.com/" +"search?hl=en&as_q=Java&as_epq=I%2FO";
        String output = URLDecoder.decode(input, "UTF-8");
        System.out.println(output);
    }
}
