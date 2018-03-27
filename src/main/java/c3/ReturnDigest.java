package c3;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The ReturnDigest class stores the result of the calculation in the private field digest, which is accessed via getDigest().
 * The main() method in ReturnDigestUserInterface loops through a list of files from the command line. It starts a new ReturnDigest
 * thread for each file and then tries to retrieve the result using getDigest(). However, when you run this program, the result may not be what you expect:
 *
 * Exception in thread "main" java.lang.NullPointerException
 at javax.xml.bind.DatatypeConverterImpl.printHexBinary
 (DatatypeConverterImpl.java:358)
 at javax.xml.bind.DatatypeConverter.printHexBinary(DatatypeConverter.java:560)
 at ReturnDigestUserInterface.main(ReturnDigestUserInterface.java:15)
 */
public class ReturnDigest extends Thread
{
    private String filename;
    private byte[] digest;
    public ReturnDigest(String filename)
    {
        this.filename = filename;
    }
    @Override
    public void run()
    {
        try
        {
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while (din.read() != -1) ; // read entire file
            din.close();
            digest = sha.digest();
        } catch (IOException ex)
        {
            System.err.println(ex);
        } catch (NoSuchAlgorithmException ex)
        {
            System.err.println(ex);
        }
    }
    public byte[] getDigest()
    {
        return digest;
    }
}