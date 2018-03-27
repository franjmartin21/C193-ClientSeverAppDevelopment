package c9;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.concurrent.*;
import java.util.logging.*;

/**
 * A Single-File Server
 *
 * Our investigation of HTTP servers begins with a server that always sends out the same file, no matter what the request.
 * It's called SingleFileHTTPServer and is shown in Example 9-10. The filename, local port, and content encoding are read
 * from the command line. If the port is omitted, port 80 is assumed. If the encoding is omitted, ASCII is assumed.
 */
public class SingleFileHTTPServer
{
    private static final Logger logger = Logger.getLogger("SingleFileHTTPServer");
    private final byte[] content;
    private final byte[] header;
    private final int port;
    private final String encoding;
    public SingleFileHTTPServer(String data, String encoding,String mimeType, int port) throws UnsupportedEncodingException
    {
        this(data.getBytes(encoding), encoding, mimeType, port);
    }
    public SingleFileHTTPServer(byte[] data, String encoding, String mimeType, int port)
    {
        this.content = data;
        this.port = port;
        this.encoding = encoding;
        String header = "HTTP/1.0 200 OK\r\n"
                + "Server: OneFile 2.0\r\n"
                + "Content-length: " + this.content.length + "\r\n"
                + "Content-type: " + mimeType + "; charset=" + encoding + "\r\n\r\n";
        this.header = header.getBytes(Charset.forName("US-ASCII"));
    }
    public void start()
    {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        try (ServerSocket server = new ServerSocket(this.port))
        {
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Data to be sent:");
            logger.info(new String(this.content, encoding));
            while (true)
            {
                try
                {
                    Socket connection = server.accept();
                    pool.submit(new HTTPHandler(connection));
                }
                catch (IOException ex)
                {
                    logger.log(Level.WARNING, "Exception accepting connection", ex);
                }
                catch (RuntimeException ex)
                {
                    logger.log(Level.SEVERE, "Unexpected error", ex);
                }
            }
        }
        catch (IOException ex)
        {
            logger.log(Level.SEVERE, "Could not start server", ex);
        }
    }
    private class HTTPHandler implements Callable<Void>
    {
        private final Socket connection;
        HTTPHandler(Socket connection)
        {
            this.connection = connection;
        }
        @Override
        public Void call() throws IOException
        {
            try
            {
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                InputStream in = new BufferedInputStream(connection.getInputStream());
                // read the first line only; that's all we need
                StringBuilder request = new StringBuilder(80);
                while (true)
                {
                    int c = in.read();
                    if (c == '\n' || c == '\n' || c == -1) break;
                    request.append((char) c);
                }
                // If this is HTTP/1.0 or later send a MIME header
                if (request.toString().indexOf("HTTP/") != -1)
                {
                    out.write(header);
                }
                out.write(content);
                out.flush();
            }
            catch (IOException ex)
            {
                logger.log(Level.WARNING, "Error writing to client", ex);
            }
            finally
            {
                connection.close();
            }
            return null;
        }
    }
    public static void main(String[] args)
    {
        // set the port to listen on
        int port;
        try
        {
            port = Integer.parseInt(args[1]);
            if (port < 1 || port > 65535) port = 80;
        }
        catch (RuntimeException ex)
        {
            port = 80;
        }
        String encoding = "UTF-8";
        if (args.length > 2) encoding = args[2];
        try
        {
            Path path = Paths.get(args[0]);
            byte[] data = Files.readAllBytes(path);
            String contentType = URLConnection.getFileNameMap().getContentTypeFor(args[0]);
            SingleFileHTTPServer server = new SingleFileHTTPServer(data, encoding,contentType, port);
            server.start();
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Usage: java SingleFileHTTPServer filename port encoding");
        }
        catch (IOException ex)
        {
            logger.severe(ex.getMessage());
        }
    }
    /**
     * The constructors set up the data to be sent along with an HTTP header that includes information about content length
     * and content encoding. The header and the body of the response are stored in byte arrays in the desired encoding so
     * that they can be blasted to clients very quickly.
     *
     * The SingleFileHTTPServer class holds the content to send, the header to send, and the port to bind to. The start()
     * method creates a ServerSocket on the specified port, then enters an infinite loop that continually accepts connections
     * and processes them.
     *
     * Each incoming socket is processed by a runnable Handler object that is submitted to a thread pool. Thus, one slow
     * client can't starve other clients. Each Handler gets an InputStream from it which it reads the client request.
     * It looks at the first line to see whether it contains the string HTTP. If it sees this string, the server assumes
     * that the client understands HTTP/1.0 or later and therefore sends a MIME header for the file; then it sends the data.
     * If the client request doesn't contain the string HTTP, the server omits the header, sending the data by itself.
     * Finally, the handler closes the connection.
     *
     * The main() method just reads parameters from the command line. The name of the file to be served is read from the
     * first command-line argument. If no file is specified or the file cannot be opened, an error message is printed and
     * the program exits. Assuming the file can be read, its contents are read into the byte array data using the Path and
     * Files classes introduced in Java 7. The URLConnection class makes a reasonable guess about the content type of the
     * file, and that guess is stored in the contentType variable. Next, the port number is read from the second command-line
     * argument. If no port is specified or if the second argument is not an integer from 1 to 65,535, port 80 is used.
     * The encoding is read from the third command-line argument, if present. Otherwise, UTF-8 is assumed. Then these values
     * are used to construct a SingleFileHTTPServer object and start it.
     *
     * The main() method is only one possible interface. You could easily use this class as part of some other program.
     * If you added a setter method to change the content, you could easily use it to provide simple status information
     * about a running server or system. However, that would raise some additional issues of thread safety that Example
     * 9-10 doesn't have to address because the data is immutable.
     */
}