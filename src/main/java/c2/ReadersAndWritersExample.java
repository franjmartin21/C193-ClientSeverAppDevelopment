package c2;

import java.io.*;

public class ReadersAndWritersExample {

    public void testintWriters() throws IOException {
        /*
        * The write(char[] text, int offset, int length) method is the base method in terms of which the other four write() methods are
        * implemented. A subclass must override at least this method as well as flush() and close(), although most override some of
        * the other write() methods as well in order to provide more efficient implementations. For example, given a Writer object w,
        * you can write the string "Network" like this:
        */
        Writer w = new FileWriter("text.txt");
        char[] network = {'N', 'e', 't', 'w', 'o', 'r', 'k'};
        w.write(network, 0, network.length);

        //The same task can be accomplished with these other methods, as well:
        w.write(network);
        for (int i = 0;  i < network.length;  i++) w.write(network[i]);
        w.write("Network");
        w.write("Network", 0, 7);
        /*
        * Writers may be buffered, either directly by being chained to a BufferedWriter or indirectly because their underlying
        * output stream is buffered. To force a write to be committed to the output medium, invoke the flush() method:
        *
        */
        w.flush();
        /*
        * The close() method behaves similarly to the close() method of OutputStream. close() flushes the writer, then closes
        * the underlying output stream and releases any resources associated with it:
        */
        //public abstract void close() throws IOException
    }

    public static void testingOutputStreamWriter() throws IOException {
        /*
         * This is the single most important Writer class
         * Its constructor specifies the output stream to write to and the encoding to use.
         *
         * If no encoding is specified, the default encoding for the platform is used. In 2013, the default encoding is UTF-8
         * on the Mac and more often than not on Linux
         *
         * this code fragment writes the first few words of Homer's Odyssey in the CP1253 Windows Greek encoding:
         */
        OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream("OdysseyB.txt"), "Cp1253");
        w.write("ἤμος δ΄ ἠριγένεια φάνη ῥοδοδάκτυλος Ἠώς");
        w.close();
    }

    public static void testingReaders(){
        /*
        * read(char[] text, int offset, int length) method is the fundamental method through which the other two read() methods
        * are implemented.
        * The read() method returns a single Unicode character as an int with a value from 0 to 65,535 or -1 on end of stream
        *
        */
    }

    public static void main(String[] args) throws IOException {
        testingOutputStreamWriter();
    }
}
