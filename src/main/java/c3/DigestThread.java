package c3;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  subclass of Thread whose run() method calculates a 256-bit SHA-2 message digest for a specified file.
 *  It does this by reading the file with a DigestInputStream. This filter stream calculates a cryptographic hash function
 *  as it reads the file. When it's finished reading, the hash function is available from the digest() method.
 */
public class DigestThread extends Thread
{
    private String filename;
    public DigestThread(String filename)
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
            while (din.read() != -1) ;
            din.close();
            byte[] digest = sha.digest();
            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
        catch (NoSuchAlgorithmException ex)
        {
            System.err.println(ex);
        }
    }
    public static void main(String[] args)
    {
        for (String filename : args)
        {
            Thread t = new DigestThread(filename);
            t.start();
        }
    }
}