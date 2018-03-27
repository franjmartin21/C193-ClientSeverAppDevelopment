package c9;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;

public class UsingServerSockets {
    private static final int PORT = 1025;
    public static void main(String[] args) {
        /**
         * The ServerSocket class contains everything needed to write servers in Java. It has constructors that create new
         * ServerSocket objects, methods that listen for connections on a specified port, methods that configure the various
         * server socket options, and the usual miscellaneous methods such as toString().
         *
         * In Java, the basic life cycle of a server program is this:
         * 1. A new ServerSocket is created on a particular port using a ServerSocket() constructor.
         * 2. The ServerSocket listens for incoming connection attempts on that port using its accept() method. accept()
         * blocks until a client attempts to make a connection, at which point accept() returns a Socket object connecting
         * the client and the server
         * 3. Depending on the type of server, either the Socket's getInputStream() method, getOutputStream() method, or
         * both are called to get input and output streams that communicate with the client.
         * 4. The server and the client interact according to an agreed-upon protocol until it is time to close the connection.
         * 5. The server, the client, or both close the connection.
         * 6. The server returns to step 2 and waits for the next connection.
         */

        // Implementing your own daytime server is easy. First, create a server socket that listens on port 13:

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server Awaiting in port " + PORT);
            // You want to do this in a loop
            while (true) {
                // Accept the connection
                // We use try with resources to make sure it is closed
                try (Socket connection = server.accept()) {
                    /*
                    The accept() call blocks. That is, the program stops here and waits, possibly for hours or days, until a
                    client connects on port 13. When a client does connect, the accept() method returns a Socket object.
                    Note that the connection is returned a java.net.Socket object, the same as you used for clients in the previous
                    lesson. The daytime protocol requires the server (and only the server) to talk, so get an OutputStream from the
                    socket. Because the daytime protocol requires text, chain this to an OutputStreamWriter:
                     */
                    Writer out = new OutputStreamWriter(connection.getOutputStream());
                    /*
                    Now get the current time and write it onto the stream. The daytime protocol doesn't require any particular format
                    other than that it be human readable, so let Java pick for you:
                     */
                    ZonedDateTime now = ZonedDateTime.now();
                    out.write("The current date and time are: " + now.toString() + "\r\n");
                    out.flush();
                    connection.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
