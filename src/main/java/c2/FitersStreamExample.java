package c2;

import java.io.*;

public class FitersStreamExample {

    public void chainingFilterExample() throws FileNotFoundException {
        FileInputStream fin = new FileInputStream("data.txt");
        BufferedInputStream bis = new BufferedInputStream(fin);
        /*
        * From this point forward, it's possible to use the read() methods of both fin and bin to read data from the file data.txt.
        * However, intermixing calls to different streams connected to the same source may violate several implicit contracts of
        * the filter streams.
        *
        * One way to write your code so that it's at least harder to introduce this sort of bug is to deliberately overwrite the
        * reference to the underlying input stream
        */
        InputStream in2 = new FileInputStream("data.txt");
        in2 = new BufferedInputStream(in2);
        /*
        * you may be able to construct one stream directly inside another. For example:
        */
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream( new FileOutputStream("data.txt")));
        

    }
}
