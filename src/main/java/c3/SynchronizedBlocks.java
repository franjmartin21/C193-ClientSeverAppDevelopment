package c3;

import java.io.*;
import java.util.Date;

/**
 * This is called LogFile on the book.
 * It shows different ways to use Synchronization
 *
 * uppose your web server keeps a logfile. The logfile may be represented by a class like the one shown in Example 3-11.
 * This class itself doesn't use multiple threads. However, if the web server uses multiple threads to handle incoming connections,
 * then each of those threads will need access to the same logfile and consequently to the same LogFile object.
 */
public class SynchronizedBlocks{
    private Writer out;
    public SynchronizedBlocks (File f) throws IOException
    {
        FileWriter fw = new FileWriter(f);
        this.out = new BufferedWriter(fw);
    }

    /**
     * In this class, the writeEntry() method finds the current date and time, then writes into the underlying file using four separate
     * invocations of out.write(). A problem occurs if two or more threads each have a reference to the same LogFile object and one of
     * those threads interrupts another in the process of writing the data. One thread may write the date and a tab, then the next
     * thread might write three complete entries; then, the first thread could write the message, a carriage return, and a linefeed.
     * @param message
     * @throws IOException
     */
    public void writeEntry(String message) throws IOException
    {
        Date d = new Date();
        out.write(d.toString());
        out.write('\t');
        out.write(message);
        out.write("\r\n");
    }

    /**
     * The first choice is to synchronize on the Writer object out. For example:
     *
     * This works because all the threads that use this LogFile object also use the same out object that's part of that LogFile.
     * It doesn't matter that out is private. Although it is used by the other threads and objects, it's referenced only within the
     * LogFile class. Furthermore, although you're synchronizing here on the out object, it's the writeEntry() method that needs
     * to be protected from interruption
     */
    public void writeEntry2(String message) throws IOException
    {
        synchronized (out)
        {
            Date d = new Date();
            out.write(d.toString());
            out.write('\t');
            out.write(message);
            out.write("\r\n");
        }
    }

    /**
     * The second possibility is to synchronize on the LogFile object itself. This is simple enough to arrange with the this keyword.
     */
    public void writeEntry3(String message) throws IOException
    {
        synchronized (this)
        {
            Date d = new Date();
            out.write(d.toString());
            out.write('\t');
            out.write(message);
            out.write("\r\n");
        }
    }

    /**
     * SYNCHRONIZED METHODS
     * Because synchronizing the entire method body on the object itself is such a common thing to do, Java provides a shortcut.
     * You can synchronize an entire method on the current object (the this reference) by adding the synchronized modifier to the
     * method declaration
     * @throws IOException
     */
    public synchronized void writeEntry4(String message) throws IOException
    {
        Date d = new Date();
        out.write(d.toString());
        out.write('\t');
        out.write(message);
        out.write("\r\n");
    }
    /*
    * Simply adding the synchronized modifier to all methods is not a catchall solution for synchronization problems. For one thing,
    * it exacts a severe performance penalty in many VMs (though more recent VMs have improved greatly in this respect), potentially
    * slowing down your code by a factor of three or more. Second, it dramatically increases the chances of deadlock. Third, and most
    * importantly, it's not always the object itself you need to protect from simultaneous modification or access, and synchronizing on
    * the instance of the method's class may not protect the object you really need to protect. For instance, in this example, what
    * you're really trying to prevent is two threads simultaneously writing onto out. If some other class had a reference to out
    * completely unrelated to the LogFile, this attempt would fail. However, in this example, synchronizing on the LogFile object is
    * sufficient because out is a private instance variable. Because you never expose a reference to this object, there's no way for
    * any other object to invoke its methods except through the LogFile class. Therefore, synchronizing on the LogFile object has the
    * same effect as synchronizing on out.
    */


    public void close() throws IOException
    {
        out.flush();
        out.close();
    }
}
