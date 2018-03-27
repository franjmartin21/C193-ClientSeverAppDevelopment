package c8;

import java.io.*;
import java.net.Socket;

public class UsingSockets {

    public static void main(String[] args) {
        /**
         * Now let's see how to retrieve this same data programmatically using sockets. First, open a socket to time.nist.gov on port 13:
         *
         * This doesn't just create the object. It actually makes the connection across the network. If the connection
         * times out or fails because the server isn't listening on port 13, then the constructor throws an IOException,
         * so you'll usually wrap this in a try block. In Java 7, Socket implements Autocloseable so you can use try-with-resources:
         */
        try (Socket socket = new Socket("time.nist.gov", 13))
        {
            /**
             * The next step is optional but highly recommended. Set a timeout on the connection using the setSoTimeout()
             * method. Timeouts are measured in milliseconds, so this statement sets the socket to time out after 15 seconds
             * of nonresponsiveness:
             */
             socket.setSoTimeout(15000);

            /**
             * Setting a timeout on the socket means that each read from or write to the socket will take at most a certain
             * number of milliseconds. If a server hangs while you're connected to it, you will be notified with a SocketTimeoutException.
             * Exactly how long a timeout to set depends on the needs of your application and how responsive you expect
             * the server to be. Fifteen seconds is a long time for a local intranet server to respond, but it's rather
             * short for an overloaded public server like time.nist.gov.
             */

            /**
             * Once you've opened the socket and set its timeout, call getInputStream() to return an InputStream you can
             * use to read bytes from the socket. In general, a server can send any bytes at all; but in this specific case,
             * the protocol specifies that those bytes must be ASCII:
             */
            InputStream in = socket.getInputStream();
            StringBuilder time = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, "ASCII");
            for (int c = reader.read(); c != -1; c = reader.read())
            {
                time.append((char) c);
            }
            System.out.println(time);

            /**
             * Writing to Servers with Sockets
             *
             * Writing to a server is not noticeably harder than reading from one. You simply ask the socket for an output
             * stream as well as an input stream. Although it's possible to send data over the socket using the output stream
             * at the same time you're reading data over the input stream, most protocols are designed so that the client is
             * either reading or writing over a socket, not both at the same time. In the most common pattern, the client
             * sends a request. Then the server responds. The client may send another request, and the server responds again.
             * This continues until one side or the other is done, and closes the connection.
             *
             * One simple bidirectional TCP protocol is dict, defined in RFC 2229. In this protocol, the client opens a socket
             * to port 2628 on the dict server and sends commands such as "DEFINE eng-lat gold". This tells the server to send
             * a definition of the word gold using its English-to-Latin dictionary. (Different servers have different dictionaries
             * installed.) After the first definition is received, the client can ask for another. When it's done it sends the
             * command "quit".
             *
             * DictClient shows how to use that service with Sockets
             */

            /**
             * Half-closed sockets
             *
             * The close() method shuts down both input and output from the socket. On occasion, you may want to shut down
             * only half of the connection, either input or output. The shutdownInput() and shutdownOutput() methods close
             * only half the connection:
             *
             * public void shutdownInput() throws IOException
             * public void shutdownOutput() throws IOException
             *
             * Neither actually closes the socket. Instead, they adjust the stream connected to the socket so that it thinks
             * it's at the end of the stream. Further reads from the input stream after shutting down input return –1.
             * Further writes to the socket after shutting down output throw an IOException.
             *
             * Many protocols, such as finger, whois, and HTTP, begin with the client sending a request to the server, then
             * reading the response. It would be possible to shut down the output after the client has sent the request.
             * For example, this code fragment sends a request to an HTTP server and then shuts down the output, because
             * it won't need to write anything else over this socket:
             */
            try (Socket connection = new Socket("www.oreilly.com", 80))
            {
                Writer out = new OutputStreamWriter(connection.getOutputStream(), "8859_1");
                out.write("GET / HTTP 1.0\r\n\r\n");
                out.flush();
                connection.shutdownOutput();
                // read the response...
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        catch (IOException ex)
        {
            System.err.println("Could not connect to time.nist.gov");
        }
        /**
         * Notice that even though you shut down half or even both halves of a connection, you still need to close the socket
         * when you're through with it. The shutdown methods simply affect the socket's streams. They don't release the
         * resources associated with the socket, such as the port it occupies.
         * The isInputShutdown() and isOutputShutdown() methods tell you whether the input and output streams are open or
         * closed, respectively. You can use these (rather than isConnected() and isClosed()) to more specifically ascertain
         * whether you can read from or write to a socket:
         *
         * public boolean isInputShutdown()
         * public boolean isOutputShutdown()
         */

    }

}
