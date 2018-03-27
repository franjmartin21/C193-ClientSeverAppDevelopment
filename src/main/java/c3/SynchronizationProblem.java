package c3;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SynchronizationProblem implements Runnable {

    private String filename;

    public SynchronizationProblem(String filename)
    {
        this.filename = filename;
    }

    /**
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
            byte[] digest = sha.digest();
             // These three lines have a problem, as more than 1 thread could access this code, the order of printing could not
             // be the right one
            System.out.print(filename + ": ");
            System.out.print(DatatypeConverter.printHexBinary(digest));
            System.out.println();
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
    */

    /**
     * Second version of run method using synchronization, this guarantees the right order of printing
     */
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
            byte[] digest = sha.digest();
            /*
             * These three lines have a problem, as more than 1 thread could access this code, the order of printing could not
             * be the right one
             */
            synchronized (System.out) {
                System.out.print(filename + ": ");
                System.out.print(DatatypeConverter.printHexBinary(digest));
                System.out.println();
            }
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


    public static void main(String[] args) {
        for (String filename : args) {
            SynchronizationProblem dr = new SynchronizationProblem(filename);
            Thread t = new Thread(dr);
            t.start();
        }
    }
}
