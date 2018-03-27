package c2;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamExample {

    /**
     * The following code fragment reads 10 bytes from the InputStream in and stores them in the byte array input. However, if end
     * of stream is detected, the loop is terminated early
     * @param in
     * @throws IOException
     */
    public void readInputStream1(InputStream in) throws IOException{
        byte[] input = new byte[10];
        for (int i = 0; i < input.length; i++)
        {
            int b = in.read();
            if (b == -1) break;
            input[i] = (byte) b;
        }
    }

    /**
     * Code like:
     * byte[] input  = new byte[1024];
     * int bytesRead = in.read(input);
     * It attempts to read 1,024 bytes from the InputStream in into the array input. However, if only 512 bytes are available,
     * that's all that will be read, and bytesRead will be set to 512. To guarantee that all the bytes you want are actually
     * read, place the read in a loop that reads repeatedly until the array is filled.
     * @param in
     * @throws IOException
     */
    public void readInputStream2(InputStream in) throws IOException{
        int bytesRead   = 0;
        int bytesToRead = 1024;
        byte[] input    = new byte[bytesToRead];
        while (bytesRead < bytesToRead)
        {
            bytesRead += in.read(input, bytesRead, bytesToRead - bytesRead);
        }
    }

    /**
     * previous code fragment had a bug because it didn't consider the possibility that all 1,024 bytes might never arrive
     * (as opposed to not being immediately available). Fixing that bug requires testing the return value of read() before
     * adding it to bytesRead. For example:
     * @param in
     * @throws IOException
     */
    public void readInputStream3(InputStream in) throws IOException{
        int bytesRead = 0;
        int bytesToRead = 1024;
        byte[] input = new byte[bytesToRead];
        while (bytesRead < bytesToRead)
        {
            int result = in.read(input, bytesRead, bytesToRead - bytesRead);
            if (result == -1) break; // end of stream
            bytesRead += result;
        }
    }

    /**
     * If you do not want to wait until all the bytes you need are immediately available, you can use the available()
     * method to determine how many bytes can be read without blocking. This returns the minimum number of bytes you can read.
     * You may in fact be able to read more, but you will be able to read at least as many bytes as available() suggests
     */
    public void readInputStream4(InputStream in) throws IOException{
        int bytesAvailable = in.available();
        byte[] input = new byte[bytesAvailable];
        int bytesRead = in.read(input, 0, bytesAvailable);
        // continue with rest of program immediately...
    }



}
