package c7;
import java.io.*;
import java.net.*;


/**
 * Demonstrates a basic CacheRequest subclass that passes back a ByteArrayOutputStream. Later, the data can be retrieved
 * using the getData() method, a custom method in this subclass just retrieving the data Java wrote onto the OutputStream
 * this class supplied. An obvious alternative strategy would be to store results in files and use a FileOutputStream instead.
 */
public class SimpleCacheRequest extends CacheRequest
{
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    @Override
    public OutputStream getBody() throws IOException
    {
        return out;
    }
    @Override
    public void abort()
    {
        out.reset();
    }
    public byte[] getData()
    {
        if (out.size() == 0) return null;
        else return out.toByteArray();
    }
}
