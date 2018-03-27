package c3;

import javax.xml.bind.*; // for DatatypeConverter
public class ReturnDigestUserInterface
{
    /**
     * PROBLEM NULL POINTER:
     * First main method that is going to throw a NullPointerException
    public static void main(String[] args)
    {
        for (String filename : args)
        {
            // Calculate the digest
            ReturnDigest dr = new ReturnDigest(filename);
            dr.start();
            // Now print the result
            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            byte[] digest = dr.getDigest();
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        }
    }
     */

    /**
     * PROBLEM RACE CONDITION:
     * Although it could deliver the result, it depends on what ends first the main Thread or the secundary thread.
    public static void main(String[] args)
    {
        ReturnDigest[] digests = new ReturnDigest[args.length];
        for (int i = 0; i < args.length; i++)
        {
            // Calculate the digest
            digests[i] = new ReturnDigest(args[i]);
            digests[i].start();
        }
        for (int i = 0; i < args.length; i++)
        {
            // Now print the result
            StringBuffer result = new StringBuffer(args[i]);
            result.append(": ");
            byte[] digest = digests[i].getDigest();
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        }
    }

     */
    /**
     * POLLING:
     * The solution most novices adopt is to make the getter method return a flag value (or perhaps throw an exception)
     * until the result field is set. Then the main thread periodically polls the getter method to see whether it's returning
     * something other than the flag value.
     */
    public static void main(String[] args)
    {
        ReturnDigest[] digests = new ReturnDigest[args.length];
        for (int i = 0; i < args.length; i++)
        {
            // Calculate the digest
            digests[i] = new ReturnDigest(args[i]);
            digests[i].start();
        }
        for (int i = 0; i < args.length; i++)
        {
            while (true)
            {
                // Now print the result
                byte[] digest = digests[i].getDigest();
                if (digest != null)
                {
                    StringBuilder result = new StringBuilder(args[i]);
                    result.append(": ");
                    result.append(DatatypeConverter.printHexBinary(digest));
                    System.out.println(result);
                    break;
                }
            }
        }
    }
    /*
     * This solution may work. If it works at all, it gives the correct answers in the correct order irrespective of how fast the
     * individual threads run relative to each other. However, it's doing a lot more work than it needs to.
     * Worse yet, this solution is not guaranteed to work. On some virtual machines, the main thread takes all the time available
     * and leaves no time for the actual worker threads. The main thread is so busy checking for job completion that there's no time
     * left to actually complete the job! Clearly this isn't a good approach.
     */
}

