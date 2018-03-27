package c9;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TimeServer
{
    public final static int PORT = 1026;

    /**
     * Sending binary, nontext data is not significantly harder. You just use an OutputStream that writes a byte array rather
     * than a Writer that writes a String. Example 9-2 demonstrates with an iterative time server that follows the time protocol
     * outlined in RFC 868. When a client connects, the server sends a 4-byte, big-endian, unsigned integer specifying the
     * number of seconds that have passed since 12:00 A.M., January 1, 1900, GMT (the epoch). Once again, the current time
     * is found by creating a new Date object. However, because Java's Date class counts milliseconds since 12:00 A.M.,
     * January 1, 1970, GMT rather than seconds since 12:00 A.M., January 1, 1900, GMT, some conversion is necessary.
     * @param args
     */
    public static void main(String[] args)
    {
        // The time protocol sets the epoch at 1900,
        // the Date class at 1970. This number
        // converts between them.
        long differenceBetweenEpochs = 2208988800L;
        try (ServerSocket server = new ServerSocket(PORT))
        {
            while (true)
            {
                try (Socket connection = server.accept())
                {
                    OutputStream out = connection.getOutputStream();
                    Date now = new Date();
                    long msSince1970 = now.getTime();
                    long secondsSince1970 = msSince1970/1000;
                    long secondsSince1900 = secondsSince1970 + differenceBetweenEpochs;
                    byte[] time = new byte[4];
                    time[0] = (byte) ((secondsSince1900 & 0x00000000FF000000L) >> 24);
                    time[1] = (byte) ((secondsSince1900 & 0x0000000000FF0000L) >> 16);
                    time[2] = (byte) ((secondsSince1900 & 0x000000000000FF00L) >> 8);
                    time[3] = (byte) (secondsSince1900 & 0x00000000000000FFL);
                    out.write(time);
                    out.flush();
                }
                catch (IOException ex)
                {
                    System.err.println(ex.getMessage());
                }
            }
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
}